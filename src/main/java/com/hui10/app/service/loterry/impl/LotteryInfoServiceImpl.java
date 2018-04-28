package com.hui10.app.service.loterry.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.lottery.LotteryInfoDao;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.enums.PrizeFileStatusEnum;
import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.model.lottery.LotteryOpenVo;
import com.hui10.app.model.lottery.LotteryRegion;
import com.hui10.app.model.prize.LotteryPrizeFileInfo;
import com.hui10.app.model.system.City;
import com.hui10.app.service.loterry.LotteryInfoService;
import com.hui10.app.service.system.CityService;
import com.hui10.common.utils.HttpUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-11-09 10:46
 **/
@Service
public class LotteryInfoServiceImpl implements LotteryInfoService {


    @Autowired
    private LotteryInfoDao lotteryInfoDao;
    @Autowired
	private PrizeDao prizeDao;
    @Autowired
    private CityService cityService;
    private static final Logger logger = LoggerFactory.getLogger(LotteryInfoServiceImpl.class);
    @Override
    public List<LotteryOpenVo> getLotteryOpenVo() {
        List<LotteryInfo> lotteryInfos = lotteryInfoDao.selectList();
        if (lotteryInfos == null || lotteryInfos.isEmpty()) {
            throw new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
        }
        List<LotteryOpenVo> lotteryOpenVos = new ArrayList<>();
        for (LotteryInfo lotteryInfo : lotteryInfos) {
            if ("10001".equals(lotteryInfo.getLotterycode())) {
                String gamecode = lotteryInfo.getLotterycode();
                lotteryOpenVos.add(getDoubleLotteryDate(gamecode));
            }
        }
        if (lotteryOpenVos == null || lotteryOpenVos.isEmpty()) {
            throw new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
        }
        return lotteryOpenVos;
    }


    private LotteryOpenVo getDoubleLotteryDate(String gamecode) {
        Date now = new Date();
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int day = 0;
        switch (week) {
            case 0:
                break;
            case 1:
                day += 1;
                week += 1;
                break;
            case 2:
                break;
            case 3:
                day += 1;
                week += 1;
                break;
            case 4:
                break;
            case 5:
                day += 2;
                week = 0;
                break;
            case 6:
                day += 1;
                week = 0;
                break;
        }
        LotteryOpenVo lotteryOpenVo = new LotteryOpenVo();
        if (day == 0) {
            lotteryOpenVo.setFlag(true);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, day);
            lotteryOpenVo.setFlag(false);
        }
        lotteryOpenVo.setGamecode(gamecode);
        lotteryOpenVo.setOpendate(cal.getTime());
        lotteryOpenVo.setWeek(weekDays[week]);
        return lotteryOpenVo;
    }


    @Override
    public Map<String, Object> queryLotteryInfoByCode(Map<String,String> lotteryInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String lotterycode = lotteryInfo.get("lotterycode");
        @SuppressWarnings("unused")
		String authcode = lotteryInfo.get("authcode");
        if(StringUtils.isBlank(lotterycode)){
        	return map;
        }
        List<LotteryInfo> list = lotteryInfoDao.queryLotteryInfoByCode(lotterycode.trim());
        if (!list.isEmpty()) {
            LotteryInfo info = list.get(0);
            map.put("lotterycode", info.getLotterycode());
            map.put("lotteryname", info.getLotteryname());
            map.put("price", info.getPrice());
        }
        return map;
    }


	@Override
	public LotteryInfo lotteryInfoInsert(JSONObject transData) {
		String lotteryType = transData.getString("lotterytype");// 彩票类型
		String lotteryNo = transData.getString("lotteryno");// 彩票期号
		Long lotteryTime = transData.getLong("lotterytime");// 开奖时间
		String fileCheckCode = transData.getString("filemd5");// 文件校验码
		LotteryInfo info = getLotteryInfo(lotteryType);
		if (null != info) {
			logger.debug("-----本地存在此彩种-----文件记录入库--------");
			LotteryPrizeFileInfo lpf_info = prizeDao.queryPrizeFileByCode(lotteryType, lotteryNo, fileCheckCode);
			if (null == lpf_info) {
				savePrizefile(lotteryType, lotteryNo, lotteryTime, fileCheckCode, null);
			}

			return info;
		}
		return null;
	}


	@Override
	public void addOrUpdateLotteryInfo(LotteryInfo lotteryInfo, boolean isadd) {
		ValidatorUtils.checkBean(lotteryInfo, ICommon.PARAMETER_ERR);
		Date date = new Date();
		lotteryInfo.setModifytime(date);
		lotteryInfo.setModifier(lotteryInfo.getOperator());
		List<LotteryInfo> list = null;
		if (true == isadd) {
			list = lotteryInfoDao.queryLotteryListByNameOrCode(lotteryInfo.getLotterycode(), lotteryInfo.getLotteryname());
			if (null != list && list.size() > 0) {
				// 彩票编号或者名称不可以重复
				throw new CommonException(ICommon.LOTTERY_NAME_OR_CODE_REPEAT, PropertiesUtils.get(ICommon.LOTTERY_NAME_OR_CODE_REPEAT));
			}
			lotteryInfo.setCreatetime(date);
			lotteryInfo.setCreater(lotteryInfo.getOperator());
			lotteryInfoDao.insertLotteryInfo(lotteryInfo);
			dealLotteryRegion(lotteryInfo, true);
		} else {
			list = lotteryInfoDao.queryLotteryListByName(lotteryInfo.getLotterycode(), lotteryInfo.getLotteryname());
			if (null != list && list.size() > 0) {
				// 彩票编号或者名称不可以重复
				throw new CommonException(ICommon.LOTTERY_NAME_OR_CODE_REPEAT, PropertiesUtils.get(ICommon.LOTTERY_NAME_OR_CODE_REPEAT));
			}
			lotteryInfoDao.updateLotteryInfo(lotteryInfo);
			dealLotteryRegion(lotteryInfo, false);
		}
		String unionpay_url = Constants.UNIONPAY_URL;
		String unionpay_lottery_receive = Constants.LOTTERY_RECEIVE;
		try {

			String response = HttpUtil.getInstance().postBody(unionpay_url + unionpay_lottery_receive, JSONObject.toJSONString(lotteryInfo),
					DigestUtils.md5Hex(Constants.UNIONPAY_MD5_PREFIX + lotteryInfo.getLotterycode() + Constants.UNIONPAY_MD5_SUFFIX));
			logger.info("-----彩票内容管理，前置系统返回=" + response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_ADD_EDIT_FAILURE, PropertiesUtils.get(ICommon.LOTTERY_ADD_EDIT_FAILURE));
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_ADD_EDIT_FAILURE, PropertiesUtils.get(ICommon.LOTTERY_ADD_EDIT_FAILURE));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_ADD_EDIT_FAILURE, PropertiesUtils.get(ICommon.LOTTERY_ADD_EDIT_FAILURE));
		}catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_ADD_EDIT_FAILURE, PropertiesUtils.get(ICommon.LOTTERY_ADD_EDIT_FAILURE));
		}

	}
	
	private void dealLotteryRegion(LotteryInfo lotteryInfo, boolean isadd) {
		if (false == isadd) {
			lotteryInfoDao.deleteLotteryRegion(lotteryInfo.getLotterycode());
		}

		String[] provinceids = lotteryInfo.getProvinceids().split(",");
		List<LotteryRegion> list = new ArrayList<LotteryRegion>();
		Date date = lotteryInfo.getModifytime();
		for (String provinceid : provinceids) {
			LotteryRegion region = new LotteryRegion();
			region.setId(UUIDUtil.getUUID());
			region.setCreater(lotteryInfo.getOperator());
			region.setCreatetime(date);
			region.setLotterycode(lotteryInfo.getLotterycode());
			region.setLotteryname(lotteryInfo.getLotteryname());
			region.setModifier(lotteryInfo.getOperator());
			region.setModifytime(date);
			region.setProvinceid(provinceid);
			City city = cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
			region.setProvincename(city.getProvincename());
			list.add(region);
		}
		lotteryInfo.setRegionlist(list);
		lotteryInfoDao.insertLotterRegion(list);
	}


	@Override
	public LotteryInfo lotteryInfoInsertMarket(JSONObject transData) {
		String lotteryType = transData.getString("lotterytype");// 彩票类型
		String lotteryNo = transData.getString("lotteryno");// 彩票期号
		Long lotteryTime = transData.getLong("lotterytime");// 开奖时间
		String fileCheckCode = transData.getString("filemd5");// 文件校验码
		String promotionId = transData.getString("promotionid");
		LotteryInfo info = getLotteryInfo(lotteryType);
		if (null != info) {
			logger.debug("-----本地存在此彩种-----文件记录入库--------");
			LotteryPrizeFileInfo lpf_info = prizeDao.queryPrizeFileByCodeAndPromotionid(lotteryType, lotteryNo, fileCheckCode, promotionId);
			if (null == lpf_info) {
				savePrizefile(lotteryType, lotteryNo, lotteryTime, fileCheckCode, promotionId);
			}

			return info;
		}
		return null;
	}
	
	private LotteryInfo getLotteryInfo(String lotterytype){
		return lotteryInfoDao.selectByCode(lotterytype);
	}
	
	private void savePrizefile(String lotteryType,String lotteryNo,Long lotteryTime,String fileCheckCode,String promotionId){
		LotteryPrizeFileInfo prizeFileInfo = new LotteryPrizeFileInfo();
		Date date = new Date(lotteryTime);
		Date now = new Date();
		prizeFileInfo.setPrizefileid(UUIDUtil.getUUID());
		prizeFileInfo.setGamecode(lotteryType);
		prizeFileInfo.setIssuenumber(lotteryNo);
		prizeFileInfo.setLotterytime(date);
		prizeFileInfo.setFilecheckcode(fileCheckCode);
		prizeFileInfo.setStatus(PrizeFileStatusEnum.UNTREATED.getCode());
		prizeFileInfo.setCreatetime(now);
		prizeFileInfo.setUpdatetime(now);
		prizeFileInfo.setPromotionid(null == promotionId ? "" : promotionId);
		prizeDao.insertPrizeFile(prizeFileInfo);
	}

}
