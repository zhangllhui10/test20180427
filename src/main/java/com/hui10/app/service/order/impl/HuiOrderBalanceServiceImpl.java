package com.hui10.app.service.order.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.FtpUtil;
import com.hui10.app.dao.order.HuiOrderBalanceDao;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.OrderAccount;
import com.hui10.app.model.order.OrderAccountBalanceDetail;
import com.hui10.app.model.order.OrderAccountBalanceDetailVo;
import com.hui10.app.model.order.OrderAccountBalanceInfo;
import com.hui10.app.model.order.OrderAccountBalanceInfoVo;
import com.hui10.app.model.order.enums.OrderAccountBalanceResourceEnum;
import com.hui10.app.model.order.enums.OrderAccountBalanceStatusEnum;
import com.hui10.app.service.order.HuiOrderBalanceService;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

@Service
public class HuiOrderBalanceServiceImpl implements HuiOrderBalanceService{
	
	private static final Logger logger = Logger.getLogger(HuiOrderBalanceServiceImpl.class);
	
	@Autowired
	private LotteryOrderSerice lotteryOrderSerice;
	
	@Autowired
	private HuiOrderBalanceDao huiOrderBalanceDao;
	
	@Value("${sftp.account.file.name}")
	private String filename;
	
	@Value("${sftp.account.local.path}")
	private String localpath;
	
	@Value("${sftp.account.remote.path}")
	private String remotepath;
	
	@Value("${sftp.host}")
	private String host;
	
	@Value("${sftp.port}")
	private String port;
	
	@Value("${sftp.username}")
	private String username;
	
	@Value("${sftp.password}")
	private String password;

	@Override
	public OrderAccountBalanceInfo findOneInfo(String accountid) {
		return huiOrderBalanceDao.findOneInfo(accountid);
	}

	@Override
	public String downloadCSV(String accounttime) {
		filename = filename + accounttime + ".csv";
        File dirFile = new File(localpath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //如果已经存在则先删除
        File contentFile = new File(localpath, filename);
        if (contentFile.isFile() && contentFile.exists()) {
			contentFile.delete();
		}
        try {
        		FtpUtil.sshDownloadFtp(host, username, password, 
        			Integer.parseInt(port), filename, remotepath, localpath);
		} catch (Exception e) {
			logger.error("====================下载文件"+filename+"失败==============="+e);
			throw new CommonException(ICommon.DOWNLOAD_FILE_FROM_BLC_FAILED, PropertiesUtils.get(ICommon.DOWNLOAD_FILE_FROM_BLC_FAILED));
		}
        return localpath+filename;
	}

	@Override
	public int addOneInfo(OrderAccountBalanceInfo orderAccountBalanceInfo) {
		return huiOrderBalanceDao.addOneInfo(orderAccountBalanceInfo);
	}

	@Override
	public int addDetails(List<OrderAccountBalanceDetail> orderAccountBalanceDetails) {
		return huiOrderBalanceDao.addDetails(orderAccountBalanceDetails);
	}

	@Override
	public int accountBalance() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String accounttime = sdf.format(date);
        String filePath = this.downloadCSV(accounttime);
        
        File file = new File(filePath);
        
        Map<String, OrderAccount> baolecai = null;
        try {
        		baolecai = this.generateMapFromFile(file);
		} catch (Exception e) {
			logger.error("====================文件生成map失败==============="+e);
			throw new CommonException(ICommon.GENERATE_ACCOUNT_FILE_FAILED, PropertiesUtils.get(ICommon.GENERATE_ACCOUNT_FILE_FAILED));
		}
        List<LotteryOrder> hui10 = lotteryOrderSerice.queryPreDayLotteryOrder();
        
        List<OrderAccountBalanceDetail> unmatcheds = new ArrayList<>();
        
        String accountid = UUIDUtil.getUUID();
        Date createtime = new Date();
        Long orderamount = 0l;
        
        for (LotteryOrder hui : hui10) {
			if (baolecai.containsKey(hui.getOrderid())) {
				OrderAccount orderAccount = baolecai.get(hui.getOrderid());
				//宝乐彩和汇彩宝都包含此订单 但是订单金额不匹配
				if(hui.getOrderamount() != orderAccount.getOrderamount()){
					OrderAccountBalanceDetail unmatched = new OrderAccountBalanceDetail();
					unmatched.setAccountid(accountid);
					unmatched.setOrdertime(hui.getOrdertime());
					unmatched.setHuiorderamount((long)hui.getOrderamount());
					unmatched.setHuiorderid(hui.getOrderid());
					unmatched.setOrderamount(baolecai.get(hui.getOrderid()).getOrderamount());
					unmatched.setOrderid(baolecai.get(hui.getOrderid()).getOrderid());
					unmatched.setResource(OrderAccountBalanceResourceEnum.BOTH.getState());
					unmatcheds.add(unmatched);
				}
				baolecai.remove(orderAccount.getHuiorderid());
			}else{
				//宝乐彩没有此订单 但是汇彩宝有
				OrderAccountBalanceDetail unmatched = new OrderAccountBalanceDetail();
				unmatched.setAccountid(accountid);
				unmatched.setOrdertime(hui.getOrdertime());
				unmatched.setHuiorderamount((long)hui.getOrderamount());
				unmatched.setHuiorderid(hui.getOrderid());
				unmatched.setOrderamount(0l);
				unmatched.setOrderid(null);
				unmatched.setResource(OrderAccountBalanceResourceEnum.HUI10.getState());
				unmatcheds.add(unmatched);
			}
			orderamount += hui.getOrderamount();
		}
        
        //map中剩余订单为宝乐彩拥有订单 但是汇彩宝不包含
        for (Map.Entry<String, OrderAccount> entry : baolecai.entrySet()) {
        		OrderAccount orderAccount = entry.getValue();
        		OrderAccountBalanceDetail unmatched = new OrderAccountBalanceDetail();
			unmatched.setAccountid(accountid);
			unmatched.setOrdertime(null);
			unmatched.setHuiorderamount(0l);
			unmatched.setHuiorderid(null);
			unmatched.setOrderamount(orderAccount.getOrderamount());
			unmatched.setOrderid(orderAccount.getOrderid());
			unmatched.setResource(OrderAccountBalanceResourceEnum.BAOLECAI.getState());
			unmatcheds.add(unmatched);
		}
        
        OrderAccountBalanceInfo orderAccountBalanceInfo = new OrderAccountBalanceInfo();
        
        orderAccountBalanceInfo.setAccountid(accountid);
        orderAccountBalanceInfo.setAccounttime(sdf.format(date));
        orderAccountBalanceInfo.setCreatetime(createtime);
        orderAccountBalanceInfo.setOrderamount(orderamount);
        orderAccountBalanceInfo.setOrdertotal(hui10.size());
        orderAccountBalanceInfo.setCreatetime(new Date());
        if(unmatcheds.size() == 0){
        		orderAccountBalanceInfo.setAccountstatus(OrderAccountBalanceStatusEnum.NO.getState());
        }else{
        		orderAccountBalanceInfo.setAccountstatus(OrderAccountBalanceStatusEnum.YES.getState());
        		this.addDetails(unmatcheds);
        }
        return this.addOneInfo(orderAccountBalanceInfo);
	}

	@Override
	public void downloadFile(String accountid, HttpServletResponse response) {
		OrderAccountBalanceInfo orderAccountBalanceInfo = this.findOneInfo(accountid);
		String accounttime = orderAccountBalanceInfo.getAccounttime();
		OrderAccountBalanceDetailVo orderAccountBalanceDetailVo = this.queryAccountBalanceDetails(accountid, null, null);
		List<OrderAccountBalanceDetail> orderlist = orderAccountBalanceDetailVo.getList();
		
		StringBuilder sBuilder = new StringBuilder();
		int count = 0;
		try {
			response.reset();  
		    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			sBuilder.append("对账编号,宝乐彩订单号,汇彩宝订单号,宝乐彩订单金额,汇彩宝订单金额,订单来源,订单时间\n");
			//加上UTF-8文件的标识字符  解决乱码问题
			//outputStream.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });
			outputStream.write(sBuilder.toString().getBytes());
			count = sBuilder.toString().getBytes().length;
			for (OrderAccountBalanceDetail orderAccountBalanceDetail : orderlist) {
				StringBuilder sb = new StringBuilder();
				sb.append(orderAccountBalanceDetail.getAccountid());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getOrderid());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getHuiorderid());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getOrderamount());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getHuiorderamount());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getResource());
				sb.append(",");
				sb.append(orderAccountBalanceDetail.getOrdertime());
				sb.append("\n");
				count += sb.toString().getBytes().length;
				outputStream.write(sb.toString().getBytes());
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename + accounttime+ ".txt", "UTF-8") + "\"");  
		    response.addHeader("Content-Length", "" + count);  
		    response.setContentType("application/octet-stream;charset=UTF-8");  
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			logger.error("=============下载失败==========="+e);
			throw new CommonException(ICommon.DOWNLOAD_FILE_FROM_HCB_FAILED, PropertiesUtils.get(ICommon.DOWNLOAD_FILE_FROM_HCB_FAILED));
		}
	}

	@Override
	public Map<String, OrderAccount> generateMapFromFile(File file) throws Exception {
		FileInputStream in = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        Map<String, OrderAccount> map = new HashMap<>();
        try {
              if(file.exists()){
	            	  in = new FileInputStream(file);
	            	  reader = new InputStreamReader(in, "UTF-8");
	            	  bufferedReader = new BufferedReader(reader);
	            	  String line = null;
	            	  int i = 0;
	            	  while((line = bufferedReader.readLine()) != null){
	            		  i++;
	            		  if(i>1){
	            			  String array[] = line.trim().split(",");
	            			  OrderAccount orderAccount = new OrderAccount();
	            			  orderAccount.setOrderid(array[0]);
	            			  orderAccount.setHuiorderid(array[1]);
	            			  orderAccount.setOrderamount(Long.parseLong(array[2]));
	            			  map.put(orderAccount.getHuiorderid(), orderAccount);
	            		  }
	              }
              }
		} finally{
			try {
				in.close();
	            reader.close();
	            bufferedReader.close();
            } catch (Exception e2) {
            		logger.error("====================关闭流失败==============="+e2);
            }
		}
		return map;
	}

	@Override
	public OrderAccountBalanceInfoVo queryAccountBalanceInfo(String status, String starttime, String endtime,
			Integer pageno, Integer pagesize) {
		if(null == pagesize || pagesize == 0){
			pagesize = Constants.DEFAULT_PAGESIZE;
		}
		if(null == pageno || pageno == 0){
			pageno = Constants.DEFAULT_PAGENO;
		}
		int count = this.queryAccountBalanceInfoCount(status, starttime, endtime);
		OrderAccountBalanceInfoVo orderAccountBalanceInfoVo = new OrderAccountBalanceInfoVo();
		if(count > 0){
			orderAccountBalanceInfoVo.setTotal(count);
			int index = (pageno-1)*pagesize;
			orderAccountBalanceInfoVo.setList(huiOrderBalanceDao.queryAccountBalanceInfo(status, starttime, endtime, index, pagesize));
		}else{
			orderAccountBalanceInfoVo.setTotal(0);
			orderAccountBalanceInfoVo.setList(new ArrayList<>());
		}
		return orderAccountBalanceInfoVo;
	}

	@Override
	public int queryAccountBalanceInfoCount(String status, String starttime, String endtime) {
		return huiOrderBalanceDao.queryAccountBalanceInfoCount(status, starttime, endtime);
	}

	@Override
	public OrderAccountBalanceDetailVo queryAccountBalanceDetails(String accountid, Integer pageno, Integer pagesize) {
		if(null == pagesize || pagesize == 0){
			pagesize = Constants.DEFAULT_PAGESIZE;
		}
		if(null == pageno || pageno == 0){
			pageno = Constants.DEFAULT_PAGENO;
		}
		int count = this.queryAccountBalanceDetailsCount(accountid);
		OrderAccountBalanceDetailVo orderAccountBalanceDetailVo = new OrderAccountBalanceDetailVo();
		if(count > 0){
			int index = (pageno-1)*pagesize;
			List<OrderAccountBalanceDetail> list = huiOrderBalanceDao.queryAccountBalanceDetails(accountid, index, pagesize);
			if(list.size()>0){
				orderAccountBalanceDetailVo.setCount(count);
				orderAccountBalanceDetailVo.setList(list);
			}
		}else{
			orderAccountBalanceDetailVo.setCount(0);
			orderAccountBalanceDetailVo.setList(new ArrayList<>());
		}
		return orderAccountBalanceDetailVo;
	}

	@Override
	public int queryAccountBalanceDetailsCount(String accountid) {
		return huiOrderBalanceDao.queryAccountBalanceDetailsCount(accountid);
	}

}
