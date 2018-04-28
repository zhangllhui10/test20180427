package com.hui10.app.service.adnormalorder.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.dao.adnormalorder.AdnormalOrderDao;
import com.hui10.app.model.adnormalorder.AdnormalOrderInfo;
import com.hui10.app.model.enums.AdnormalOrderStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.service.adnormalorder.AdnormalOrderSerice;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

@Service
public class AdnormalOrderSericeImpl implements AdnormalOrderSerice {
	
	@Autowired
    private AdnormalOrderDao adnormalOrderDao;

	@Override
	public String addAdnormalOrder(String orderid, String outtradeno, String userphone, String bankcardid, String username) {
		
		//校验订单信息是否存在
		LotteryOrder lotteryOrder = adnormalOrderDao.queryLotteryOrderInfo(orderid, userphone);
		if(lotteryOrder == null){
			throw new CommonException(ICommon.ORDER_INFO_NOT_EXIST, PropertiesUtils.get(ICommon.ORDER_INFO_NOT_EXIST));
		}
		if(StringUtils.isNotBlank(lotteryOrder.getOuttradeno()) && !lotteryOrder.getOuttradeno().equals(outtradeno)){
			throw new CommonException(ICommon.PAY_ORDERNO_ERROR, PropertiesUtils.get(ICommon.PAY_ORDERNO_ERROR));
		}
		
		//校验绑定银行卡是否正确
		if(StringUtils.isNoneBlank(bankcardid)){
			UserBankCard userBankCard = adnormalOrderDao.queryUserBankCard(bankcardid, userphone);
			if(userBankCard == null){
				throw new CommonException(ICommon.BANK_CARD_ERROR, PropertiesUtils.get(ICommon.BANK_CARD_ERROR));
			}
		}
		
		//校验支付订单号是否处理过
		AdnormalOrderInfo adnormalOrder = adnormalOrderDao.queryAdnormalOrderByOuttradeno(outtradeno);
		if(adnormalOrder != null){
			throw new CommonException(ICommon.PAY_ORDERNO_HAVA_EXIST, PropertiesUtils.get(ICommon.PAY_ORDERNO_HAVA_EXIST));
		}
		
		Date date = new Date();
		
		//数据入库
		AdnormalOrderInfo adnormalOrderInfo = new AdnormalOrderInfo();
		adnormalOrderInfo.setId(UUIDUtil.getUUID());
		adnormalOrderInfo.setOrderid(orderid);
		adnormalOrderInfo.setOuttradeno(outtradeno);
		adnormalOrderInfo.setUserphone(userphone);
		adnormalOrderInfo.setStationno(lotteryOrder.getStationno());
		adnormalOrderInfo.setSerialno(lotteryOrder.getSerialno());
		adnormalOrderInfo.setBankcardid(bankcardid);
		adnormalOrderInfo.setSubmitter(username);
		adnormalOrderInfo.setSubmittime(date);
		adnormalOrderInfo.setCreatetime(date);
		adnormalOrderInfo.setUpdatetime(date);
		adnormalOrderInfo.setStatus(AdnormalOrderStatusEnum.UNTREATED.getCode());
		adnormalOrderDao.insert(adnormalOrderInfo);
		
		return adnormalOrderInfo.getId();
	}

	@Override
	public String modifyAdnormalOrder(String id, String bankcardid, long amount, String status, String username) {
		
		//校验异常订单信息是否存在
		AdnormalOrderInfo adnormalOrderInfo = adnormalOrderDao.queryAdnormalOrderById(id);
		if(adnormalOrderInfo == null){
			throw new CommonException(ICommon.ORDER_INFO_NOT_EXIST, PropertiesUtils.get(ICommon.ORDER_INFO_NOT_EXIST));
		}
		//校验状态
		if(AdnormalOrderStatusEnum.getEnum(status) == null){
			throw new CommonException(ICommon.STATUS_ERROR, PropertiesUtils.get(ICommon.STATUS_ERROR));
		}
		
		if(AdnormalOrderStatusEnum.PAY_MONEY.getCode().equals(status)){
			if(StringUtils.isBlank(bankcardid)){
				throw new CommonException(ICommon.BANK_CARD_NOT_NULL, PropertiesUtils.get(ICommon.BANK_CARD_NOT_NULL));
			}
			if(amount <= 0){
				throw new CommonException(ICommon.AMOUNT_ERROR, PropertiesUtils.get(ICommon.AMOUNT_ERROR));
			}
		}
		
		if(amount < 0){
			throw new CommonException(ICommon.AMOUNT_ERROR, PropertiesUtils.get(ICommon.AMOUNT_ERROR));
		}
		
		//校验绑定银行卡
		if(StringUtils.isNotBlank(bankcardid)){
			UserBankCard userBankCard = adnormalOrderDao.queryUserBankCard(bankcardid, adnormalOrderInfo.getUserphone());
			if(userBankCard == null){
				throw new CommonException(ICommon.BANK_CARD_ERROR, PropertiesUtils.get(ICommon.BANK_CARD_ERROR));
			}
		}
			
		Date date = new Date();
		
		adnormalOrderInfo = new AdnormalOrderInfo();
		adnormalOrderInfo.setId(id);
		adnormalOrderInfo.setBankcardid(bankcardid);
		adnormalOrderInfo.setAmount(amount);
		adnormalOrderInfo.setStatus(status);
		adnormalOrderInfo.setHandler(username);
		adnormalOrderInfo.setHandletime(date);
		adnormalOrderInfo.setUpdatetime(date);
		adnormalOrderDao.update(adnormalOrderInfo);
		
		return id;
	}
}
