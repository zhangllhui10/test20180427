package com.hui10.app.service.merchant.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.UnionPayUtils;
import com.hui10.app.dao.acquirer.AcquirerInfoDao;
import com.hui10.app.dao.merchant.MerchantInfoDao;
import com.hui10.app.dao.system.PictureDao;
import com.hui10.app.model.acquirer.AcquirerApply;
import com.hui10.app.model.acquirer.SettlementInfo;
import com.hui10.app.model.enums.ApplyTypeEnum;
import com.hui10.app.model.enums.MerApplyLotteryStatusEnum;
import com.hui10.app.model.merchant.MerchantApply;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.app.model.merchant.MerchantLottery;
import com.hui10.app.model.merchant.MerchantTerminal;
import com.hui10.app.model.system.Picture;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.app.service.station.StationService;
import com.hui10.common.utils.DateUtils;
import com.hui10.common.utils.HttpUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: MerchantInfoServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午2:51:34
 */
@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {
	private static final Logger logger = Logger.getLogger(MerchantInfoServiceImpl.class);

	@Autowired
	private MerchantInfoDao merchantInfoDao;

	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private AcquirerInfoDao acquirerInfoDao;
	
	@Autowired
	private StationService stationService;
	
	private final static int NEARBY_DISTANCE = 100000;

	@Override
	public void createMerchantApply(String authorization, String paramsBody) {
		//logger.info("********merchant authorization=" + authorization + "\n" + "********paramsBody=" + paramsBody);
		// 解析JSON串
		MerchantApply merchantApply = JSONObject.parseObject(paramsBody, MerchantApply.class);
		//校验正确性
		UnionPayUtils.check(authorization, merchantApply.getApplyid());
		// 补全渠道商户入网申请信息
		// merchantApply.setApplyid(UUIDUtil.getUUID());
		merchantApply.setCreatetime(new Date());
		merchantApply.setModifytime(new Date());
		Boolean flag = false;
		MerchantInfo mer = merchantInfoDao.queryMerchantInfo(merchantApply.getMerchantno());
		if (null == mer) {
			flag = true;
			// 渠道商户信息入库
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setMerchantid(merchantApply.getMerchantid());
			merchantInfo.setMerchantname(merchantApply.getMerchantname());
			merchantInfo.setMerchantshort(merchantApply.getMerchantshort());
			merchantInfo.setAccountname(merchantApply.getAccountname());
			merchantInfo.setAcquirerno(merchantApply.getAcquirerno());
			merchantInfo.setAddress(merchantApply.getAddress());
			merchantInfo.setBankaccount(merchantApply.getBankaccount());
			merchantInfo.setCityid(merchantApply.getCityid());
			merchantInfo.setCityname(merchantApply.getCityname());
			merchantInfo.setContactnumber(merchantApply.getContactnumber());
			merchantInfo.setContactperson(merchantApply.getContactperson());
			merchantInfo.setIndustryname(merchantApply.getIndustryname());
			merchantInfo.setIndustryno(merchantApply.getIndustryno());
			merchantInfo.setLegaladdress(merchantApply.getLegaladdress());
			merchantInfo.setLegalnumber(merchantApply.getLegalnumber());
			merchantInfo.setLegalperson(merchantApply.getLegalperson());
			merchantInfo.setLegalphoto(merchantApply.getLegalphoto());
			merchantInfo.setProvinceid(merchantApply.getProvinceid());
			merchantInfo.setProvincename(merchantApply.getProvincename());
			merchantInfo.setCreater(merchantApply.getCreater());
			merchantInfo.setCreatetime(new Date());
			merchantInfo.setModifier(merchantApply.getCreater());
			merchantInfo.setModifytime(new Date());
			merchantInfo.setBankaccount(merchantApply.getBankaccount());
			merchantInfo.setBankcode(merchantApply.getBankcode());
			merchantInfo.setBankname(merchantApply.getBankname());
			merchantInfo.setBranchcode(merchantApply.getBranchcode());
			merchantInfo.setBranchname(merchantApply.getBranchname());
			merchantInfo.setMerchantno(merchantApply.getMerchantno());
			merchantInfo.setPaymerchantno(merchantApply.getPaymerchantno());
			merchantInfo.setAccounttype(merchantApply.getAccounttype());
			merchantInfo.setFinancephone(merchantApply.getFinancephone());
			merchantInfo.setStatus(merchantApply.getStatus());
			merchantInfoDao.addMerchant(merchantInfo);
		} else {
			
		}
		// 渠道商户入网申请信息入库
		merchantInfoDao.addMerchantApply(merchantApply);
		// 渠道商户销售彩票信息入库
		List<MerchantLottery> lotterylist = merchantApply.getLotterylist();
		for (int i = 0; i < lotterylist.size(); i++) {
			MerchantLottery lottery = lotterylist.get(i);
			lottery.setApplyid(merchantApply.getApplyid());
			lottery.setMerchantid(merchantApply.getMerchantid());
			lottery.setId(UUIDUtil.getUUID());
		}
		merchantInfoDao.addMerchantLotterys(lotterylist);
		// 图片信息
		List<Picture> picList = new ArrayList<Picture>();
		
		List<Map<String, String>> totleList = new ArrayList<Map<String, String>>();
		totleList.addAll(merchantApply.getProtocolphotolist());
		totleList.addAll(merchantApply.getFormphotolist());
		totleList.addAll(merchantApply.getLegalphotolist());
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
			SettlementInfo settlementInfo = new SettlementInfo();
			settlementInfo.setProfit_participant_id(merchantApply.getMerchantno());
			settlementInfo.setProfit_participant_name(merchantApply.getMerchantname());
			settlementInfo.setProfit_participant_contact(merchantApply.getContactperson() == null ? "" : merchantApply.getContactperson());
			settlementInfo.setProfit_participant_phone(merchantApply.getContactnumber() == null ? "" : merchantApply.getContactnumber());
			settlementInfo.setMerc_bank_head_name(merchantApply.getBankname());
			settlementInfo.setMerc_bank_head_code(merchantApply.getBankcode());
			settlementInfo.setMerc_bank_branch_name(merchantApply.getBranchname());
			settlementInfo.setMerc_bank_branch_code(merchantApply.getBranchcode());
			settlementInfo.setMerc_account_open_type(merchantApply.getAccounttype());
			settlementInfo.setMerc_account_name(merchantApply.getAccountname());
			settlementInfo.setMerc_account_no(merchantApply.getBankaccount());
			settlementInfo.setProfit_participant_financial_contact(merchantApply.getFinancephone() == null ? "" : merchantApply.getFinancephone());
			settlementInfo.setProfit_participant_financial_phone(merchantApply.getFinancephone() == null ? "" : merchantApply.getFinancephone());
			//新增字段，从配置文件里取
			settlementInfo.setProfit_settle_date(PropertiesUtils.get("profit_settle_date_m"));
			settlementInfo.setProfit_floor_amount(PropertiesUtils.get("profit_floor_amount_m"));
			settlementInfo.setChl_fee_settle_date(PropertiesUtils.get("chl_fee_settle_date_m"));
			settlementInfo.setChl_fee_floor_amount(PropertiesUtils.get("chl_fee_floor_amount_m"));
			UnionPayUtils.settlementInfoInsert(settlementInfo);
		}
	}

	@Override
	public void checkMerLotteryStatus(String applyid, String merchantid, String lotterystatus, String lotteryreason) {
		if (null == MerApplyLotteryStatusEnum.getDesc(lotterystatus)) {
			throw new CommonException(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR));
		}
		MerchantApply merApply = merchantInfoDao.queryMerchantApplyInfo(applyid);
		if (null == merApply) {
			throw new CommonException(ICommon.MERCHANT_APPLY_NOT_EXIST, PropertiesUtils.get(ICommon.MERCHANT_APPLY_NOT_EXIST));
		}
		if (!merchantid.equals(merApply.getMerchantid())) {
			throw new CommonException(ICommon.MERCHANT_APPLY_MISMATCHING, PropertiesUtils.get(ICommon.MERCHANT_APPLY_MISMATCHING));
		}
		if (MerApplyLotteryStatusEnum.AUDIT_PASS.getCode().equals(merApply.getLotterystatus())) {
			throw new CommonException(ICommon.MERCHANT_APPLY_MANAGE,
					PropertiesUtils.get(ICommon.MERCHANT_APPLY_MANAGE, MerApplyLotteryStatusEnum.getDesc(merApply.getLotterystatus()).getDesc()));
		}
		String station_number = null;
		
		logger.debug("=======================merApply的stationno为" + merApply.getStationno() + "===========");
		if (StringUtils.isBlank(merApply.getStationno())) {
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setMerchantid(merchantid);
			merchantInfo.setModifytime(new Date());
			if (MerApplyLotteryStatusEnum.AUDIT_PASS.getCode().equals(lotterystatus)) {
				merchantInfo.setStatus("1");
				station_number = stationService.distributeStationCode(merApply.getMerchantno(), merApply.getProvinceid(), merApply.getCityid());
				merchantInfo.setStationno(station_number);
			} else {
				merchantInfo.setStatus("0");
			}
			merchantInfoDao.modifyMerchant(merchantInfo);
		} else {
			station_number = merApply.getStationno();
		}
		MerchantApply merchantApply = new MerchantApply();
		merchantApply.setApplyid(applyid);
		merchantApply.setLotterystatus(lotterystatus);
		merchantApply.setLotteryreason(lotteryreason);
		merchantApply.setModifytime(new Date());
		if (MerApplyLotteryStatusEnum.AUDIT_PASS.getCode().equals(lotterystatus)) {
			merchantApply.setSalestatus("1");
		} else {
			merchantApply.setSalestatus("0");
		}
		merchantInfoDao.modifyMerchantApply(merchantApply);
		JSONObject parameters = new JSONObject();
		parameters.put("applyid", applyid);
		parameters.put("merchantid", merchantid);
		parameters.put("lotterystatus", lotterystatus);
		parameters.put("lotteryreason", lotteryreason);
		parameters.put("stationno", station_number);
		parameters.put("timestamp", DateUtils.getCurrentTimestamp());
		logger.info("***********post_parameters=" + parameters.toJSONString());
		try {
			String unionpay_url = Constants.UNIONPAY_URL;
			String unionpay_merchant_lottery_notify = Constants.UNIONPAY_MERCHANT_LOTTERY_NOTIFY;
			String response = HttpUtil.getInstance().postBody(unionpay_url + unionpay_merchant_lottery_notify, parameters.toJSONString(),
					getSignature(parameters.toJSONString()));
			logger.info("******response=" + response);

		} catch (ClientProtocolException e) {
			throw new CommonException(ICommon.MERCHANT_APPLY_SUCCESS, PropertiesUtils.get(ICommon.MERCHANT_APPLY_SUCCESS));
		} catch (IOException e) {
			throw new CommonException(ICommon.MERCHANT_APPLY_SUCCESS, PropertiesUtils.get(ICommon.MERCHANT_APPLY_SUCCESS));
		} catch (URISyntaxException e) {
			throw new CommonException(ICommon.MERCHANT_APPLY_SUCCESS, PropertiesUtils.get(ICommon.MERCHANT_APPLY_SUCCESS));
		}

	}

	private String getSignature(String parameters) {
		return DigestUtils.md5Hex(Constants.UNIONPAY_MD5_PREFIX + parameters + Constants.UNIONPAY_MD5_SUFFIX);
	}

	@Override
	public void manageMerTerminal(String authorization, String paramsBody) {
		logger.info("********manageMerTerminal:authorization=" + authorization + "\n" + "********paramsBody=" + paramsBody);
		// 解析JSON串
		JSONObject map = JSONObject.parseObject(paramsBody);
		// 校验正确性
		UnionPayUtils.check(authorization, (String)map.get("merchantid"));
		String deleteStr = JSONObject.toJSONString(map.getJSONArray("deletelist"));
		List<String> deleteTerList = JSONArray.parseArray(deleteStr, String.class);
		if (null != deleteTerList && !deleteTerList.isEmpty()) {
			String id = deleteTerList.get(0);
			merchantInfoDao.deleteMerchantTerminal(id);
		}

		List<MerchantTerminal> insertTerList = JSONObject.parseArray(JSONObject.toJSONString(map.getJSONArray("insertlist")), MerchantTerminal.class);//(insertStr, MerchantTerminal.class);
		if (null != insertTerList && !insertTerList.isEmpty()) {
			for (MerchantTerminal merchantTerminal : insertTerList) {
				merchantTerminal.setCreatetime(new Date());
				merchantTerminal.setModifytime(new Date());
			}
			merchantInfoDao.addMerchantTerminals(insertTerList);
		}

	}

	@Override
	public void updateApplySaleStatus(String authorization, String paramsBody) {
		logger.info("********updateApplySaleStatus:authorization=" + authorization + "\n" + "********paramsBody=" + paramsBody);
		// 校验正确性
		UnionPayUtils.check(authorization, paramsBody);
		JSONObject map = JSONObject.parseObject(paramsBody);

		String applyid = map.getString("applyid");
		String applytype = map.getString("applytype");
		String salestatus = map.getString("salestatus");
		if (ApplyTypeEnum.ACQUIRER_APPLY.getCode().equals(applytype)) {
			AcquirerApply aa = new AcquirerApply();
			aa.setSalestatus(salestatus);
			aa.setApplyid(applyid);
			aa.setModifytime(new Date());
			acquirerInfoDao.modifyAcquirerApply(aa);
		} else {
			MerchantApply ma = new MerchantApply();
			ma.setApplyid(applyid);
			ma.setSalestatus(salestatus);
			ma.setModifytime(new Date());
			merchantInfoDao.modifyMerchantApply(ma);
		}
	}
	
	@Override
	public List<Map<String, String>> findMerchantsNearBy(Double longitude, Double latitude) {
		return merchantInfoDao.findMerchantsNearBy(longitude, latitude, NEARBY_DISTANCE);
	}
	
	@Override
	public int updateLocation(String merchantid, Double longitude, Double latitude) {
		return merchantInfoDao.updateLocation(merchantid, longitude, latitude);
	}

	@Override
	public List<MerchantInfo> queryMerchantByMerchantNo(List<String> merchantnoList) {
		List<MerchantInfo> list = merchantInfoDao.queryMerchantByIds(merchantnoList);
		if (null == list || list.size() != merchantnoList.size()) {
			throw new CommonException(ICommon.MERCHANT_NOT_EXIST, PropertiesUtils.get(ICommon.MERCHANT_NOT_EXIST));
		}
		return list;
	}
	
	@Override
	public int updateMerchantStationCode(String merchantno, String stationcode) {
		return merchantInfoDao.updateMerchantStationCode(merchantno, stationcode);
	}
	
	@Override
	public MerchantInfo findMerchantInfoByMerchantNo(String merchantno) {
		MerchantInfo merchantInfo = merchantInfoDao.findMerchantInfoByMerchantNo(merchantno);
		if (null == merchantInfo) {
			throw new CommonException(ICommon.MERCHANT_NOT_EXIST, PropertiesUtils.get(ICommon.MERCHANT_NOT_EXIST));
		}
		return merchantInfo;
	}
}
