package com.hui10.app.service.acquirer.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.encrypt.AesUtils;
import com.hui10.app.common.utils.UnionPayUtils;
import com.hui10.app.dao.acquirer.AcquirerInfoDao;
import com.hui10.app.dao.system.PictureDao;
import com.hui10.app.model.acquirer.AcquirerApply;
import com.hui10.app.model.acquirer.AcquirerInfo;
import com.hui10.app.model.acquirer.AcquirerLottery;
import com.hui10.app.model.acquirer.SettlementInfo;
import com.hui10.app.model.system.Picture;
import com.hui10.app.service.acquirer.AcquirerInfoService;
import com.hui10.common.cache.CacheManager;
import com.hui10.common.utils.DateUtils;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;



/**
 * @ClassName: AcquirerInfoServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午2:53:01
 */
@Service
public class AcquirerInfoServiceImpl implements AcquirerInfoService {
	/**
	 * TOKEN过期时间
	 */
	private final static int TOKEN_EXPIRED_TIME = 7 * 24 * 60 * 60 ;

	@Autowired
	private AcquirerInfoDao acquirerInfoDao;
	@Autowired
	private PictureDao pictureDao;
	@Autowired
	private CacheManager cacheManager;
	
	private static final Logger logger = LoggerFactory.getLogger(AcquirerInfoServiceImpl.class);
	@Override
	public void createAcquirerApply(String authorization, String paramsBody) {
		logger.debug("***********authorization=" + authorization + "\n" + "paramsBody=" + paramsBody);
		// 校验正确性
		AcquirerApply acquirerApply = JSON.parseObject(paramsBody, AcquirerApply.class);
		UnionPayUtils.check(authorization, acquirerApply.getApplyid());
		acquirerApply.setCreatetime(new Date());
		acquirerApply.setModifytime(new Date());
		Boolean flag = false;
		AcquirerInfo acInfo = acquirerInfoDao.queryAcquirerInfo(acquirerApply.getAcquirerno());
		if (null == acInfo) {
			flag = true;
			// 存储销售渠道信息
			AcquirerInfo acquirerInfo = new AcquirerInfo();
			acquirerInfo.setAcquirerid(UUIDUtil.getUUID());
			acquirerInfo.setAcquirerno(acquirerApply.getAcquirerno());
			acquirerInfo.setAcquirername(acquirerApply.getAcquirername());
			acquirerInfo.setBankaccount(acquirerApply.getBankaccount());
			acquirerInfo.setAccountname(acquirerApply.getAccountname());
			acquirerInfo.setCreater(acquirerApply.getCreater());
			acquirerInfo.setCreatetime(new Date());
			acquirerInfo.setModifier(acquirerApply.getModifier());
			acquirerInfo.setModifytime(new Date());
			acquirerInfo.setBankcode(acquirerApply.getBankcode());
			acquirerInfo.setBankname(acquirerApply.getBankname());
			acquirerInfo.setBranchcode(acquirerApply.getBranchcode());
			acquirerInfo.setBranchname(acquirerApply.getBranchname());
			acquirerInfo.setAccounttype(acquirerApply.getAccounttype());
			acquirerInfo.setFinancephone(acquirerApply.getFinancephone());
			acquirerInfo.setPaycommission(acquirerApply.getPaycommission());
			acquirerInfoDao.addAcquirer(acquirerInfo);
		} else {
			// 更新支付手续费
			AcquirerInfo acInfoNew = new AcquirerInfo();
			acInfoNew.setAcquirerid(acInfo.getAcquirerid());
			acInfoNew.setPaycommission(acquirerApply.getPaycommission());
			acInfoNew.setModifier(acquirerApply.getModifier());
			acInfoNew.setModifytime(new Date());
			acquirerInfoDao.modifyAcquirer(acInfoNew);
		}
		// 销售渠道入网申请信息入库
		acquirerInfoDao.addAcquirerApply(acquirerApply);
		// 渠道销售彩票信息入库
		List<AcquirerLottery> lotterylist = acquirerApply.getLotterylist();
		for (int i = 0; i < lotterylist.size(); i++) {
			AcquirerLottery lottery = lotterylist.get(i);
			lottery.setApplyid(acquirerApply.getApplyid());
			lottery.setAcquirerno(acquirerApply.getAcquirerno());
			lottery.setId(UUIDUtil.getUUID());
		}
		acquirerInfoDao.addAcquirerLotterys(lotterylist);
		// 图片信息
		List<Picture> picList = new ArrayList<Picture>();
		List<Map<String, String>> totleList = new ArrayList<Map<String, String>>();
		totleList.addAll(acquirerApply.getProtocolphotolist());
		totleList.addAll(acquirerApply.getFormphotolist());
		for (Map<String, String> map : totleList) {
			Picture pic = new Picture();
			String picture = map.get("picture");
			try {
				byte[] picByte = picture.getBytes("utf-8");
				pic.setPicture(Base64.decodeBase64(picByte));
			} catch (IOException e) {
				e.printStackTrace();
			}
			pic.setId(map.get("id"));
			pic.setMd5(map.get("md5"));

			picList.add(pic);
		}
		if (!picList.isEmpty()) {
			List<Picture> existList = pictureDao.queryListById(picList);
			if(!existList.isEmpty()){
				for(int i = 0;i< picList.size();i++){
					Picture p1  =  picList.get(i);
					for(Picture p2 : existList){
						if(p1.getId().equals(p2.getId())){
							picList.remove(p1);
							i--;
						}
					}
				}
			}
			if(!picList.isEmpty()){
				pictureDao.insertPictureList(picList);
			}
		}
		if (flag) {
			//新增时同步给支付系统
			SettlementInfo settlementInfo = new SettlementInfo();
			settlementInfo.setProfit_participant_id(acquirerApply.getAcquirerno());
			settlementInfo.setProfit_participant_name(acquirerApply.getAcquirername());
			settlementInfo.setProfit_participant_contact(acquirerApply.getFinancephone() == null ? "" : acquirerApply.getFinancephone());
			settlementInfo.setProfit_participant_phone(acquirerApply.getFinancephone() == null ? "" : acquirerApply.getFinancephone());
			settlementInfo.setMerc_bank_head_name(acquirerApply.getBankname());
			settlementInfo.setMerc_bank_head_code(acquirerApply.getBankcode());
			settlementInfo.setMerc_bank_branch_name(acquirerApply.getBranchname());
			settlementInfo.setMerc_bank_branch_code(acquirerApply.getBranchcode());
			settlementInfo.setMerc_account_open_type(acquirerApply.getAccounttype());
			settlementInfo.setMerc_account_name(acquirerApply.getAccountname());
			settlementInfo.setMerc_account_no(acquirerApply.getBankaccount());
			settlementInfo.setProfit_participant_financial_contact(acquirerApply.getFinancephone() == null ? "" : acquirerApply.getFinancephone());
			settlementInfo.setProfit_participant_financial_phone(acquirerApply.getFinancephone() == null ? "" : acquirerApply.getFinancephone());
			//新增字段从配置文件里取
			settlementInfo.setProfit_settle_date(PropertiesUtils.get("profit_settle_date_ch"));
			settlementInfo.setProfit_floor_amount(PropertiesUtils.get("profit_floor_amount_ch"));
			settlementInfo.setChl_fee_settle_date(PropertiesUtils.get("chl_fee_settle_date_ch"));
			settlementInfo.setChl_fee_floor_amount(PropertiesUtils.get("chl_fee_floor_amount_ch"));
			UnionPayUtils.settlementInfoInsert(settlementInfo);
		}
	}

	@Override
	public String loginAcquire(String acquirerno, String acquirername) {
		AcquirerInfo info = acquirerInfoDao.getAcquirerInfo(acquirerno, acquirername);
		String token = null;
		if (null == info) {
			throw new CommonException(ICommon.ACQUIRER_NOT_EXIST, PropertiesUtils.get(ICommon.ACQUIRER_NOT_EXIST));
		} else {
			token = setToken(info);
		}

		return token;
	}

	@Override
	public AcquirerInfo checkTokenAcquire(String token) {
		AcquirerInfo info = null;
		if (cacheManager.exist(token)) {
			info = (AcquirerInfo) cacheManager.get(token);
			if (null == info) {
				throw new CommonException(ICommon.VALIDATE_TOKEN_FAIL, PropertiesUtils.get(ICommon.VALIDATE_TOKEN_FAIL));
			}
		} else {
			throw new CommonException(ICommon.VALIDATE_TOKEN_FAIL, PropertiesUtils.get(ICommon.VALIDATE_TOKEN_FAIL));
		}
		return info;
	}
	
	
	private String setToken(AcquirerInfo info) {
		String acquirerno = info.getAcquirerno();
		deleteToken( acquirerno);
		String token = generateToken(info.getAcquirerno());
		cacheManager.put(acquirerno, token,TOKEN_EXPIRED_TIME);
		cacheManager.put(token, info,TOKEN_EXPIRED_TIME);
		return token;
	}
	
	
	private void deleteToken(String acquirerno) {
		if (cacheManager.exist(acquirerno)) {
			String token = (String)cacheManager.get(acquirerno);
			if (cacheManager.exist(token)) {
				cacheManager.delete(token);
			}
		}
	}
	
	private  String generateToken(String acquirerno) {
		String token = null;
		StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(acquirerno).append("-");
        stringBuilder.append(DateUtils.getCurrentTimestamp()).append("-");
        stringBuilder.append(new Random().nextInt(10000000)).append("-");
        stringBuilder.append(new Random().nextInt(10000000)).append("-");
        token = AesUtils.encrypt(stringBuilder.toString());
		return token;
	}

	@Override
	public void logout(String token) {
		AcquirerInfo info = 	checkTokenAcquire(token);
		deleteToken(info.getAcquirerno());
	}

	@Override
	public String login(String acquirerno, String acquirername) {
		AcquirerInfo info = acquirerInfoDao.getAcquirerInfoLogin(acquirerno, acquirername);
		String token = null;
		if (null == info) {
			throw new CommonException(ICommon.ACQUIRER_NOT_EXIST, PropertiesUtils.get(ICommon.ACQUIRER_NOT_EXIST));
		} else {
			token = setToken(info);
		}

		return token;
	}
}
