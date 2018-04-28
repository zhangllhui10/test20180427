package com.hui10.app.service.marketing.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.HttpUtil;
import com.hui10.app.common.utils.LotteryRandomGeneratorUtil;
import com.hui10.app.dao.marketing.PromotionRecordDao;
import com.hui10.app.dao.merchant.MerchantInfoDao;
import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingGroup;
import com.hui10.app.model.marketing.enums.GivingRecordStatusEnum;
import com.hui10.app.model.marketing.enums.MarketingIsrandomEnum;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.service.marketing.GivingRecordService;
import com.hui10.app.service.marketing.MarketingService;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;
import com.poslot.model.lottery.BetFactory;

@Service
public class GivingRecordServiceImpl implements GivingRecordService{
	
	private static final Logger logger = Logger.getLogger(GivingRecordServiceImpl.class);
	
	@Autowired
	private PromotionRecordDao promotionRecordDao;
	
	@Autowired
	private MarketingService marketingService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	
	@Value("${givelottery.hcb.promotionid}")
	private String blcpromotionid;
	
	@Value("${lotter_blc_give}")
	private String url;
	
	@Override
	public List<LotteryGivingRecordInfo> createGivingRecord(String uid, String channelid, String acquireno, String serialno, String stationno, String 
			lotterycode, long money, String cityid, String merchantno, String orderno) {
		
		logger.debug("==============创建赠送记录开始============");
		//判断用户是否已经注册
		userInfoService.queryUserInfoByUid(uid);
		
		MerchantInfo merchantInfo = null;
		if (null != merchantno) {
			merchantInfo = merchantInfoDao.queryMerchantInfo(merchantno);
			if (null != merchantInfo) {
				acquireno = merchantInfo.getAcquirerno();
				stationno = merchantInfo.getStationno();
				cityid = merchantInfo.getCityid();
			}
		}
		//查找当前进行的营销活动
		logger.debug("==============查询当前正在进行的营销活动============");
		List<Marketing> marketings = marketingService.findCurrMarketings(lotterycode);
		
		List<LotteryGivingRecordInfo> records = new ArrayList<>();
		
		for (Marketing marketing : marketings) {
			try {
				logger.debug("==============处理营销活动"+marketing.getMarketingid()+"开始============");
				List<MarketingGroup> list = marketingService.findMarketingGroups(marketing.getMarketingid(), channelid);
				
				if (list.size() == 0) {
					logger.debug("==============营销活动"+marketing.getMarketingid()+"未找到属于channelid"+channelid+"的营销活动，不赠送彩票============");
					continue;
				}
				
				for (MarketingGroup marketingGroup : list) {
					if (marketingGroup.getMoney() > money) {
						logger.debug("==============消费金额不满足营销，不赠送============");
						continue;
					}
					boolean city = true;//默认地域有限制
					boolean merchant = true;//默认商户有限制
					boolean send = true;//默认送
					int canSend = 0;
					//确定要赠送，查看是否有地域或是商户限制
					if (marketingGroup.getCityids().size() == 0) {
						city = false;
						logger.debug("==============营销活动无城市限制============");
					}
					if (marketingGroup.getMerchantnos().size() == 0) {
						merchant = false;
						logger.debug("==============营销活动无商户限制============");
					}
					if (city && merchant) {
						logger.debug("==============营销既有城市也有商户限制============");
						if (marketingGroup.getCityids().contains(cityid) && marketingGroup.getCityids().contains(cityid.substring(0, 2))) {
							send = false;
							logger.debug("==============不满足城市限制，不赠送============");
						}
						if (!marketingGroup.getMerchantnos().contains(merchantno)) {
							send = false;
							logger.debug("==============不满足商户限制，不赠送============");
						}
					}else if (!city && merchant) {
						logger.debug("==============营销活动没有城市限制但是有商户限制============");
						if (!marketingGroup.getMerchantnos().contains(merchantno)) {
							send = false;
							logger.debug("==============不满足商户限制，不赠送============");
						}
					}else if (city && !merchant) {
						logger.debug("==============营销有城市限制但是无商户限制============");
						if (marketingGroup.getCityids().contains(cityid) && marketingGroup.getCityids().contains(cityid.substring(0, 2))) {
							send = false;
							logger.debug("==============不满足城市限制，不赠送============");
						}
					}
					if (send) {
						logger.debug("==============满足营销规则，赠送============");
						boolean record = false;
						int daySended = 0;
						if (null != marketing.getBetnumberrule()) {
							logger.debug("==============每日赠送彩票数有限制"+marketing.getBetnumberrule()+"============");
							daySended = userDaySendedLotteryNumber(uid, marketing.getMarketingid());
							//判断今天是否已经送够
							logger.debug("===============用户uid"+uid+"已经在营销活动marketingid"+marketing.getMarketingid()+"中获取"+daySended+"张彩票");
							if(daySended >= marketing.getBetnumberrule()) {
								logger.debug("===============用户uid"+uid+"今日赠送的彩票已达到上限");
								continue;
							}else {
								canSend = marketing.getBetnumberrule() - daySended;//当天还能赠送个数
								if (marketingGroup.getBetnumber() < canSend) {
									canSend = marketingGroup.getBetnumber();
								}
								record = true;
							}
						}else {
							canSend = marketingGroup.getBetnumber();
						}
						logger.debug("===============用户uid"+uid+"获取新增彩票"+canSend+"张");
						
						if (record) {
							for (int i = 0; i < canSend; i++) {
								LotteryGivingRecordInfo recordInfo = new LotteryGivingRecordInfo();
								recordInfo.setId(UUIDUtil.getUUID());
								recordInfo.setCreatetime(new Date());
								recordInfo.setUpdatetime(recordInfo.getCreatetime());
								recordInfo.setAcquirerno(acquireno);
								recordInfo.setGivetime(recordInfo.getCreatetime());
								recordInfo.setChannelid(channelid);
								int days = marketing.getDays();
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(new Date());
								calendar.add(Calendar.DATE, days);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String date = sdf.format(calendar.getTime());
								sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								try {
									recordInfo.setDeadlinetime(sdf.parse(date + " 23:59:59"));
								} catch (ParseException e) {
									recordInfo.setDeadlinetime(null);;
								}
								recordInfo.setLotterycode(lotterycode);
								recordInfo.setMarketingid(marketing.getMarketingid());
								recordInfo.setMerchantno(merchantno);
								recordInfo.setOrderid(null);
								recordInfo.setSerialno(serialno);
								recordInfo.setStationno(stationno);
								recordInfo.setStationprovince(cityid.substring(0,2));
								recordInfo.setStatus(GivingRecordStatusEnum.NOT_RECEIVE.getState());
								recordInfo.setUid(uid);
								recordInfo.setOrderno(orderno);
								recordInfo.setBettimetype(marketing.getBettimetype());
								recordInfo.setResource(marketing.getResource());
								records.add(recordInfo);
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				logger.error(e);
				logger.error("==================营销活动"+marketing.getMarketingid()+"赠送失败===============");
			}
		}
		if (records.size() > 0) {
			promotionRecordDao.addBatchGivingRecords(records);
		}
		logger.debug("==============创建赠送记录结束============");
		return records;
		
	}
	
	public LotteryGivingRecordInfo queryLotteryGivingRecord(String id) {
		LotteryGivingRecordInfo lotteryGivingRecordInfo = promotionRecordDao.queryLotteryGivingRecord(id);
		if(null == lotteryGivingRecordInfo) {
			throw new CommonException(ICommon.GIVING_RECORD_NOT_EXIST, PropertiesUtils.get(ICommon.GIVING_RECORD_NOT_EXIST));
		}
		return lotteryGivingRecordInfo;
	}
	
	@Override
	public LotteryGivingRecordInfo receivingUpdate(String uid, String recordid, String orderid) {
		LotteryGivingRecordInfo lotteryGivingRecordInfo = this.queryLotteryGivingRecord(recordid);
		
		UserInfo userInfo = userInfoService.queryUserInfoByUid(uid);
		String betdetail = null;
		Marketing marketing = marketingService.findOneMarketing(lotteryGivingRecordInfo.getMarketingid());
		
		//彩票已过期
		if (GivingRecordStatusEnum.EXPIRED.getState().equals(lotteryGivingRecordInfo.getStatus())) {
			throw new CommonException(ICommon.LOTTERY_HAS_EXPIRED, PropertiesUtils.get(ICommon.LOTTERY_HAS_EXPIRED));
		}
		
		if (lotteryGivingRecordInfo.getDeadlinetime().getTime() < System.currentTimeMillis()) {
			throw new CommonException(ICommon.LOTTERY_HAS_EXPIRED, PropertiesUtils.get(ICommon.LOTTERY_HAS_EXPIRED));
		}
		
		//彩票已被领取
		if (GivingRecordStatusEnum.ALREADY_RECEIVE.getState().equals(lotteryGivingRecordInfo.getStatus())) {
			throw new CommonException(ICommon.LOTTERY_ALREADY_RECEIVE, PropertiesUtils.get(ICommon.LOTTERY_ALREADY_RECEIVE));
		}
		
		if (null != marketing.getResource()) {
			//调英泰接口获取彩票
			try {
				
				Map<String, String> params = new HashMap<>();
				params.put("promotionId", blcpromotionid);
				params.put("channel", "HUI10");
				params.put("userphone", userInfo.getPhone());
				params.put("givenum", "1");
				/**
				 * 签名
				 */
				String signature = DigestUtils.md5Hex(Constants.LOTTERY_MD5_PREFIX + params.toString() + Constants.LOTTERY_MD5_SUFFIX);
				params.put("signature", signature);
				
				String transSerialNumber = UUIDUtil.getUUID();
				
				String fullurl = String.format(PropertiesUtils.get("lottery_url"), url);
				String result = HttpUtil.getInstance().doPost(transSerialNumber, fullurl, params);
				
				JSONObject jsonObject = JSONObject.parseObject(result);
				if ("9999".equals(jsonObject.getString("result"))) {
					throw new CommonException(ICommon.REQUEST_BLC_TIMEOUT, 
							String.format(PropertiesUtils.get(ICommon.REQUEST_BLC_TIMEOUT), "{" + jsonObject.getString("resultDesc") + "}"));
				}else {
					String promotionticket = jsonObject.getString("promotionTicket");
					JSONObject request_result = JSONObject.parseObject(promotionticket);
					if (null != request_result) {
						betdetail = request_result.getString("betdetail");
						String orderno = request_result.getString("promotionticketid");
						if (null != betdetail && BetFactory.getBet(betdetail).size() != 0) {
							this.updateGivingRecord(uid, recordid, orderid, betdetail);
							lotteryGivingRecordInfo.setPoslotorderno(orderno);
						}
					}
				}
			}catch (Exception e) {
				logger.error("=========================请求超时==================="+e);
				throw new CommonException(ICommon.REQUEST_BLC_TIMEOUT, 
						String.format(PropertiesUtils.get(ICommon.REQUEST_BLC_TIMEOUT), "{" + e.getMessage() + "}"));
			}
		}else {
			if (marketing.getIsrandom().equals(MarketingIsrandomEnum.RANDOM.getCode())) {
				//随机生成一注彩票
				betdetail = LotteryRandomGeneratorUtil.generateRandomLottery(lotteryGivingRecordInfo.getLotterycode());
				//更新记录 不更新receivenum
				this.updateGivingRecord(uid, recordid, orderid, betdetail);
			}else {
				if (null != marketing.getBetlimit()) {
					if (marketing.getReceivenum() >= marketing.getBetlimit()) {
						throw new CommonException(ICommon.LOTTERY_HAS_BEEN_RECEIVED_OUT, PropertiesUtils.get(ICommon.LOTTERY_HAS_BEEN_RECEIVED_OUT));
					}
				}
				//从本地对应池子里拿一注彩票
				betdetail = "奖池彩票";
				//更新receivenum字段
				int count = this.updateGivingRecordAndMarketing(uid, recordid, orderid, betdetail, marketing.getReceivenum());
				if (count == 0) {
					throw new CommonException(ICommon.LOTTERY_GIVING_RECORD_UPDATE_FAIL, PropertiesUtils.get(ICommon.LOTTERY_GIVING_RECORD_UPDATE_FAIL));
				}
			}
		}
		
		lotteryGivingRecordInfo.setBetdetail(betdetail);
		lotteryGivingRecordInfo.setOrderid(orderid);
		lotteryGivingRecordInfo.setBettimetype(marketing.getBettimetype());
		lotteryGivingRecordInfo.setResource(marketing.getResource());
		return lotteryGivingRecordInfo;
	}
	
	@Override
	public int updateGivingRecord(String uid, String recordid, String orderid, String betdetail) {
		return promotionRecordDao.updateGivingRecord(uid, recordid, orderid, betdetail);
	}
	
	@Override
	public int updateGivingRecordAndMarketing(String uid, String recordid, String orderid, String betdetail,
			int receivenum) {
		return promotionRecordDao.updateGivingRecordAndMarketing(uid, recordid, orderid, betdetail, receivenum);
	}
	
	@Override
	public int updateGivingRecordStatus(String recordid, String status) {
		return promotionRecordDao.updateGivingRecordStatus(recordid, status);
	}
	
	@Override
	public int updateExpireTickets() {
		return promotionRecordDao.updateExpireTickets();
	}
	
	@Override
	public int userDaySendedLotteryNumber(String uid, String marketingid) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String t = sdf.format(today);
		
		Date daystart = null;
		Date dayend = null;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			daystart = sdf.parse(t+" 00:00:00");
			dayend = sdf.parse(t+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return promotionRecordDao.userDaySendedLotteryNumber(uid, marketingid, daystart, dayend);
	}

}
