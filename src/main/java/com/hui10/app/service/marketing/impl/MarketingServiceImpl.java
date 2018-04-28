package com.hui10.app.service.marketing.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.marketing.MarketingAccessGatewayDao;
import com.hui10.app.dao.marketing.MarketingAccessGroupDao;
import com.hui10.app.dao.marketing.MarketingDao;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingAccessGroup;
import com.hui10.app.model.marketing.MarketingGroup;
import com.hui10.app.model.marketing.enums.BetTimeTypeEnum;
import com.hui10.app.model.marketing.enums.IndefiniteEnum;
import com.hui10.app.model.marketing.enums.MarketingIsrandomEnum;
import com.hui10.app.model.marketing.enums.MarketingStatusEnum;
import com.hui10.app.service.marketing.MarketingService;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.app.service.system.CityService;
import com.hui10.common.utils.DateUtils;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: MarketingServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 上午9:39:24
 */
@Service
public class MarketingServiceImpl implements MarketingService {
	
	@Autowired
	private CityService cityService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MarketingAccessGroupDao marketingAccessGroupDao;
	@Autowired
	private MarketingDao marketingDao;
	@Autowired
	private MarketingAccessGatewayDao marketingAccessGatewayDao;
	
	private static final String LOTTERYCODE = "10001";
	private static final String SPLIT = ",";
	
	@Override
	public Marketing findOneMarketing(String marketingid) {
		Marketing marketing = marketingDao.selectById(marketingid);
		if (null == marketing) {
			throw new CommonException(ICommon.MARKETING_ILLEGAL, PropertiesUtils.get(ICommon.MARKETING_ILLEGAL));
		}
		return marketing;
	}
	

	@Override
	public void addMarketing(Marketing marketing, String accessjson) {
		if(IndefiniteEnum.YES.getState().equals(marketing.getIndefinite())) {
			marketing.setEndtime(null);
		}else if (IndefiniteEnum.NO.getState().equals(marketing.getIndefinite())) {
			if (null == marketing.getStarttime() || null == marketing.getEndtime()) {
				throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "营销活动开始时间和结束时间不能为空"));
			}
			
			if (marketing.getStarttime().compareTo(marketing.getEndtime()) > 0) {
				throw new CommonException(ICommon.MARKETING_STARTTIME_ENDTIME, String.format(PropertiesUtils.get(ICommon.MARKETING_STARTTIME_ENDTIME), 
						"开始时间不能大于结束时间"));
			}
		}else {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "无结束期参数错误"));
		}
		
		if (null == MarketingIsrandomEnum.getByCode(marketing.getIsrandom())) {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "彩票是否随机参数错误"));
		}
		
		marketing.setStarttime(DateUtils.dayStart(marketing.getStarttime()));
		marketing.setEndtime(DateUtils.dayEnd(marketing.getEndtime()));
		
		BetTimeTypeEnum betTimeTypeEnum = BetTimeTypeEnum.findByState(marketing.getBettimetype());
		if (null == betTimeTypeEnum) {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "投注时间类型错误"));
		}
		
		if (BetTimeTypeEnum.SPECIFIC == betTimeTypeEnum) {
			if (null == marketing.getBettime()) {
				throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "指定时间不能为空"));
			}
			if (marketing.getBettime().getTime() <= System.currentTimeMillis()) {
				throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "投注时间不能小于当前时间"));
			}
		}
		
		checkMarketingName(marketing.getMarketingname(), null);
		
		// 声明初始化需要的变量
		List<MarketingAccessGroup> marketingAccessGroupList = new ArrayList<MarketingAccessGroup>();
		List<JSONObject> group_city = new ArrayList<JSONObject>();
		List<String> provinceidList = new ArrayList<String>();// 地域限制是省份的
		List<String> cityidList = new ArrayList<String>();// 地域限制是具体市的
		List<JSONObject> group_merchant = new ArrayList<JSONObject>();
		List<String> merchantidList = new ArrayList<String>();
		Set<String> marketingNameList = new HashSet<String>();// 接入途径组的接入方式
		String marketingid = UUIDUtil.getUUID();
		Date date = new Date();

		JSONArray array = JSONArray.parseArray(accessjson);// 接入活动组json字符串
		if(array.size() < 1){
			//请至少选择一种接入途径
			throw new CommonException(ICommon.MARKETING_ACCESS_GROUP_NOT_NULL, PropertiesUtils.get(ICommon.MARKETING_ACCESS_GROUP_NOT_NULL));
		}
		for (Object object : array) {
			MarketingAccessGroup group = new MarketingAccessGroup();
			JSONObject obj = (JSONObject) object;
			String groupid = UUIDUtil.getUUID();
			group.setGroupid(groupid);
			group.setGatewayid(obj.getString("gatewayid"));
			group.setChannelid(obj.getString("channelid"));
			group.setMerchantname(obj.getString("merchantname"));
			group.setBetnumber(obj.getInteger("betnumber"));
			group.setMarketingid(marketingid);
			group.setMoney(obj.getLong("money") == null ? 0 : obj.getLong("money") * 100);
			ValidatorUtils.checkBean(group, ICommon.PARAMETER_ERR);
			marketingAccessGroupList.add(group);
			marketingNameList.add(obj.getString("merchantname"));
			String citys = obj.getString("citys");// 省编号有四位，有两位
			if (StringUtils.isNotBlank(citys)) {
				String[] citys1 = citys.split(SPLIT);
				for (String c : citys1) {
					JSONObject group_c = new JSONObject();
					group_c.put("id", UUIDUtil.getUUID());
					group_c.put("groupid", groupid);
					group_c.put("cityid", c);
					group_city.add(group_c);
					if (c.length() == 2) {
						provinceidList.add(c);
					} else {
						cityidList.add(c);
					}
				}
			}

			String merchants = obj.getString("merchants");
			if (StringUtils.isNotBlank(merchants)) {
				String[] merchants1 = merchants.split(SPLIT);
				for (String m : merchants1) {
					JSONObject group_m = new JSONObject();
					group_m.put("id", UUIDUtil.getUUID());
					group_m.put("groupid", groupid);
					group_m.put("merchantno", m);
					group_merchant.add(group_m);
					merchantidList.add(m);
				}
			}
		}
		// 验证城市编号是否正确
		if (provinceidList != null && provinceidList.size() > 0)
			cityService.getCityByIds(provinceidList, CityTypeEnum.PROVINCE);
		if (cityidList != null && cityidList.size() > 0)
			cityService.getCityByIds(cityidList, CityTypeEnum.CITY);
		// 验证商户合法性
		if (null != merchantidList && merchantidList.size() > 0)
			merchantInfoService.queryMerchantByMerchantNo(merchantidList);
		// TODO 验证彩种合法性
		if (!marketing.getLotterycode().equals(LOTTERYCODE)) {
			throw new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
		}
		// 接入途径组与城市关联表
		groupCity(group_city, false,null);
		// 接入途径组与商户关联表
		groupMerchant(group_merchant, false,null);
		// 接入途径组
		accessGroup(marketingAccessGroupList, false);
		// 营销活动表
		marketing.setMarketingid(marketingid);
		marketing.setCreatetime(date);
		marketing.setUpdatetime(date);
		if (marketing.getResource().trim().equals("")) {
			marketing.setResource(null);
		}
		marketingDao.insert(marketing);
	}
	
	@Override
	public void updateMarketing(Marketing marketing, String accessjson) {
		if (null == marketing.getMarketingid() || marketing.getMarketingid().trim().equals("")) {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "营销活动id不能为空"));
		}
		
		if(IndefiniteEnum.YES.getState().equals(marketing.getIndefinite())) {
			marketing.setEndtime(null);
		}else if (IndefiniteEnum.NO.getState().equals(marketing.getIndefinite())) {
			if (null == marketing.getStarttime() || null == marketing.getEndtime()) {
				throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "营销活动开始时间和结束时间不能为空"));
			}
			
			if (marketing.getStarttime().compareTo(marketing.getEndtime()) > 0) {
				throw new CommonException(ICommon.MARKETING_STARTTIME_ENDTIME, String.format(PropertiesUtils.get(ICommon.MARKETING_STARTTIME_ENDTIME), 
						"开始时间不能大于结束时间"));
			}
		}else {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "无结束期参数错误"));
		}
		
		if (null == MarketingIsrandomEnum.getByCode(marketing.getIsrandom())) {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "彩票是否随机参数错误"));
		}
		
		marketing.setStarttime(DateUtils.dayStart(marketing.getStarttime()));
		marketing.setEndtime(DateUtils.dayEnd(marketing.getEndtime()));
		
		BetTimeTypeEnum betTimeTypeEnum = BetTimeTypeEnum.findByState(marketing.getBettimetype());
		if (null == betTimeTypeEnum) {
			throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "投注时间类型错误"));
		}
		
		if (BetTimeTypeEnum.SPECIFIC == betTimeTypeEnum) {
			if (null == marketing.getBettime()) {
				throw new CommonException(ICommon.PARAMETER_ERR, String.format(PropertiesUtils.get(ICommon.PARAMETER_ERR), "指定时间不能为空"));
			}
		}
		
		marketing.setStarttime(DateUtils.dayStart(marketing.getStarttime()));
		marketing.setEndtime(DateUtils.dayEnd(marketing.getEndtime()));
		
		checkMarketingName(marketing.getMarketingname(), marketing.getMarketingid());
		
		String marketingid = marketing.getMarketingid();
		Date date = new Date();
		List<MarketingAccessGroup> marketingAccessGroupList = new ArrayList<MarketingAccessGroup>();
		List<JSONObject> group_city = new ArrayList<JSONObject>();// 地域信息
		List<String> provinceidList = new ArrayList<String>();// 地域限制是省份的
		List<String> cityidList = new ArrayList<String>();// 地域限制是具体市的
		List<JSONObject> group_merchant = new ArrayList<JSONObject>();
		List<String> merchantidList = new ArrayList<String>();
		//Set<String> marketingNameList = new HashSet<String>();// 接入途径组的接入方式

		JSONArray array = JSONArray.parseArray(accessjson);// 接入活动组json字符串
		if(array.size() < 1){
			//请至少选择一种接入途径
			throw new CommonException(ICommon.MARKETING_ACCESS_GROUP_NOT_NULL, PropertiesUtils.get(ICommon.MARKETING_ACCESS_GROUP_NOT_NULL));
		}
		for (Object object : array) {
			MarketingAccessGroup group = new MarketingAccessGroup();
			JSONObject obj = (JSONObject) object;
			String groupid = UUIDUtil.getUUID();
			group.setGroupid(groupid);
			group.setGatewayid(obj.getString("gatewayid"));
			group.setChannelid(obj.getString("channelid"));
			group.setMerchantname(obj.getString("merchantname"));
			group.setBetnumber(obj.getInteger("betnumber"));
			group.setMarketingid(marketingid);
			group.setMoney(obj.getLong("money") == null ? 0 : obj.getLong("money") * 100);
			ValidatorUtils.checkBean(group, ICommon.PARAMETER_ERR);
			marketingAccessGroupList.add(group);
			if (null == marketingAccessGatewayDao.selectByPrimaryKey(obj.getString("gatewayid"))) {
				throw new CommonException(ICommon.MARKETING_ACCESSPROUP_ACCESSSETINGID_ILLEGAL,
						PropertiesUtils.get(ICommon.MARKETING_ACCESSPROUP_ACCESSSETINGID_ILLEGAL));
			}
			String citys = obj.getString("citys");// 省编号有四位，有两位
			if (StringUtils.isNotBlank(citys)) {
				String[] citys1 = citys.split(SPLIT);
				for (String c : citys1) {
					JSONObject group_c = new JSONObject();
					group_c.put("id", UUIDUtil.getUUID());
					group_c.put("groupid", groupid);
					group_c.put("cityid", c);
					group_city.add(group_c);
					if (c.length() == 2) {
						provinceidList.add(c);
					} else {
						cityidList.add(c);
					}
				}
			}

			String merchants = obj.getString("merchants");
			if (StringUtils.isNotBlank(merchants)) {
				String[] merchants1 = merchants.split(SPLIT);
				for (String m : merchants1) {
					JSONObject group_m = new JSONObject();
					group_m.put("id", UUIDUtil.getUUID());
					group_m.put("groupid", groupid);
					group_m.put("merchantno", m);
					group_merchant.add(group_m);
					merchantidList.add(m);
				}
			}
		}
		// 验证城市编号是否正确
		if (provinceidList != null && provinceidList.size() > 0)
			cityService.getCityByIds(provinceidList, CityTypeEnum.PROVINCE);
		if (cityidList != null && cityidList.size() > 0)
			cityService.getCityByIds(cityidList, CityTypeEnum.CITY);
		// 验证商户合法性
		if (null != merchantidList && merchantidList.size() > 0)
			merchantInfoService.queryMerchantByMerchantNo(merchantidList);
		// TODO 验证彩种合法性
		if (!marketing.getLotterycode().equals(LOTTERYCODE)) {
			throw new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
		}
		// lotteryInfoService.getLotteryOpenVo();
		// 接入途径组与城市关联表
		groupCity(group_city, true,marketing.getMarketingid());
		// 接入途径组与商户关联表
		groupMerchant(group_merchant, true,marketing.getMarketingid());
		// 接入途径组
		accessGroup(marketingAccessGroupList, true);
		// 营销活动表
		marketing.setUpdatetime(date);
		if (null != marketing.getResource() && marketing.getResource().trim().equals("")) {
			marketing.setResource(null);
		}
		marketingDao.updateById(marketing);

	}
	/**
	 * 
	 * @param group_city
	 * @param isDelete 更新时删除
	 * @param marketingid
	 * @user zhangll
	 * @date 2018年3月9日 下午5:29:30
	 */
	private void groupCity(List<JSONObject> group_city, boolean isDelete, String marketingid) {
		if (isDelete) {
			marketingAccessGroupDao.deleteBatchCityByMarketingId(marketingid);
		}
		if (group_city != null && group_city.size() > 0) {
			// 接入途径组与城市关联表
			marketingAccessGroupDao.insertBatchGroupCity(group_city);
		}
	}
	
	private void groupMerchant(List<JSONObject> group_merchant, boolean isDelete, String marketingid) {
		if (isDelete) {
			marketingAccessGroupDao.deleteBatchMerchantByMarketingId(marketingid);
		}
		if (group_merchant != null && group_merchant.size() > 0) {
			marketingAccessGroupDao.insertBatchGroupMerchant(group_merchant);
		}

	}
	
	private void accessGroup(List<MarketingAccessGroup> marketingAccessGroupList,boolean isDelete){
		if(isDelete){
			marketingAccessGroupDao.deleteBatchGroupById(marketingAccessGroupList);
		}
		marketingAccessGroupDao.insertBatch(marketingAccessGroupList);
	}
	
	
	@Override
	public void updateMarketingStatus(String marketingid, String status) {
		if (null == MarketingStatusEnum.getByCode(status)) {
			throw new CommonException(ICommon.MARKETING_STATUS_ILLEGAL, PropertiesUtils.get(ICommon.MARKETING_STATUS_ILLEGAL));
		}
		Marketing m = marketingDao.selectById(marketingid);
		if (null == m) {
			throw new CommonException(ICommon.MARKETING_ILLEGAL, PropertiesUtils.get(ICommon.MARKETING_ILLEGAL));
		}
		if (status.equals(m.getStatus())) {
			throw new CommonException(ICommon.MARKETING_STATUS_SAME,
					PropertiesUtils.get(ICommon.MARKETING_STATUS_SAME, MarketingStatusEnum.getByCode(status).getDesc()));
		}
		m.setStatus(status);
		m.setUpdatetime(new Date());
		marketingDao.updateById(m);
	}
	
	@Override
	public List<Marketing> findCurrMarketings(String lotterycode) {
		return marketingDao.findCurrMarketings(lotterycode);
	}
	
	@Override
	public List<MarketingGroup> findMarketingGroups(String marketingid, String channelid) {
		return marketingDao.findMarketingGroups(marketingid, channelid);
	}
	
	private void checkMarketingName(String marketingName,String marketingId){
		Marketing mar = marketingDao.selectByMarketingname(marketingName, marketingId);
		if (null != mar) {
			throw new CommonException(ICommon.MARKETING_NAME_EXIST, PropertiesUtils.get(ICommon.MARKETING_NAME_EXIST));
		}
	}
}
