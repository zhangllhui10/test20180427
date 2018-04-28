package com.hui10.app.service.user.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.pay.CMBCUtil;
import com.hui10.app.common.pay.PaySdkUtil;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.dao.user.MediumHandleDao;
import com.hui10.app.dao.user.WithdrawRecordDao;
import com.hui10.app.model.enums.ExtractCashStatusEnum;
import com.hui10.app.model.enums.ExtractCashStatusEnums;
import com.hui10.app.model.enums.IDCardCheckStatusEnums;
import com.hui10.app.model.enums.MessageTypeEnum;
import com.hui10.app.model.enums.PrizeBonusStatusEnum;
import com.hui10.app.model.enums.PrizeLevelEnum;
import com.hui10.app.model.enums.PrizeWinStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.model.user.WithdrawRecord;
import com.hui10.app.service.prize.LotteryPrizeService;
import com.hui10.app.service.user.MediumHandleService;
import com.hui10.app.service.user.UserBankCardService;
import com.hui10.app.service.user.UserInfoCacheService;
import com.hui10.app.service.user.WithDrawService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: WithDrawServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 下午4:48:53
 */
@Service
public class WithDrawServiceImpl implements WithDrawService {
	
	
	@Autowired
	private UserBankCardService userBankCardService;
	
	@Autowired
	private WithdrawRecordDao withdrawRecordDao;
	
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	
	@Autowired
	private PrizeDao prizeDao;
	
	@Autowired
	private MediumHandleService mediumHandleService;
	
	@Autowired
	private MediumHandleDao mediumHandleDao;

	@Autowired
    private UserInfoCacheService userInfoCacheService;
	
	@Autowired
	com.hui10.app.service.message.MessageService messageService;
	
	
	private Logger logger = LoggerFactory.getLogger(WithDrawServiceImpl.class);

	@Override
	public int withDraw(String uid, String orderid, String bankcardid) {
		int prizeCount = 0;
		WithdrawRecord record = new WithdrawRecord();
		record.setUid(uid);
		LotteryOrder order = lotteryPrizeService.selectPrizeOrder(orderid, record.getUid());
		if (checkAvaliableMoney(record, orderid, order)) {//检查可提取金额
			UserBankCard bankCard = userBankCardService.validateUserBindCard(uid, bankcardid);
			record.setPayeeno(bankCard.getCardno());
			record.setPayeename(bankCard.getCardholder());
			record.setDestinationname(bankCard.getCardname());
			record.setPayeebank(bankCard.getCardname());
			record.setOrderid(orderid);
			CMBCUtil.doesItExist(bankCard.getCardname());
			record.setDestinationcode(CMBCUtil.CMBCMap.get(bankCard.getCardname()));
			Map<String, String> resultMap = PaySdkUtil.extractCash(record);
			record.setTradeno(resultMap.get("trade_no"));
			withdrawRecordDao.saveRecord(record);
			//更新订单
	   	 	prizeDao.updateBonusStatus(PrizeBonusStatusEnum.DRAWING.getCode(), record.getOrderid(), new Date(), order.getWinprize());
	   	 	userInfoCacheService.updateUserLastUsedBankCard(uid, bankCard, true);
		}
		prizeCount = prizeDao.queryPrizeCount(uid);

        // 更新用户最近用卡信息

		return prizeCount;
	}
	
	/**
	 * 检查可以提取的金额
	 * 
	 * @user wengf
	 * @date 2017年10月19日 下午5:11:11
	 */
	private boolean checkAvaliableMoney(WithdrawRecord record, String orderid, LotteryOrder order){
		Date lotterytime = order.getLotterytime();
		Date now = new Date();
		//中奖但是未领取过期
		if (UserOrderServiceImpl.differentDays(lotterytime, now) >= 60 && order.getWinstatus() == 2 && order.getBonusstatus() == 1) {
			throw new CommonException(ICommon.PRIZE_DRAW_EXPIRE, PropertiesUtils.get(ICommon.PRIZE_DRAW_EXPIRE));
		}
		long prizeAmount = order.getWinprize();
		if (prizeAmount <= 0 || !StringUtils.equals(order.getWinstatus()+"", PrizeWinStatusEnum.WIN.getCode()) 
				|| StringUtils.equals(order.getBonusstatus()+"", PrizeBonusStatusEnum.DRAW.getCode())) {
			throw new CommonException(ICommon.PRIZE_AMOUNT_IS_ZERO_ERROR, PropertiesUtils.get(ICommon.PRIZE_AMOUNT_IS_ZERO_ERROR));
		}else if (StringUtils.equals(order.getPrizelevel(), PrizeLevelEnum.MEDIUM.getCode())) {
			throw new CommonException(ICommon.PRIZE_AMOUNT_BIG_ERROR, PropertiesUtils.get(ICommon.PRIZE_AMOUNT_BIG_ERROR));
		}else if (StringUtils.equals(order.getPrizelevel(), PrizeLevelEnum.BIG.getCode())) {
			//大奖 更新订单线下走流程
       	 	prizeDao.updateBonusStatus(PrizeBonusStatusEnum.OFF_LINE_DRAW.getCode(), orderid, new Date(), null);
       	 	return false;
		}
		BigDecimal withDrawMoney = new BigDecimal(prizeAmount);
		double cash = withDrawMoney.divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN).doubleValue();
		record.setId(UUIDUtil.getUUID());
		record.setCreatetime(new Date());
		record.setStatus(ExtractCashStatusEnum.EXTRACT_APPLY.getState());
		record.setFee(Constants.WITHDRAW_FEE);
		record.setCash(cash);
		return true;
	}
	
	@Override
	public void timetaskQueryExtractStatus() {
	        String status = ExtractCashStatusEnum.EXTRACT_APPLY.getState();
	        String bonusstatus = "";
	        List<WithdrawRecord> records = withdrawRecordDao.queryDoingPayOrder(status);
	        for (WithdrawRecord record : records) {
	        	if (StringUtils.isBlank(record.getTradeno())) {
	                continue;
	            }
	        	 Map<String, String> orderMap = PaySdkUtil.getQueryOrderInfo(record.getTradeno());
	        	 if (ExtractCashStatusEnums.DOING.getCode().equals(orderMap.get("trade_sts"))) {
		                continue;
		            } else  if (ExtractCashStatusEnums.SUCCESS.getCode().equals(orderMap.get("trade_sts"))) {
		            	record.setStatus(ExtractCashStatusEnum.EXTRACT_SUCCESS.getState());
		            	bonusstatus = PrizeBonusStatusEnum.DRAW.getCode();//领奖成功
		            	messageService.addPushMessage(record.getUid(), record.getOrderid(), MessageTypeEnum.SEND_SUCCESS_NOTIFY.getCode());
		            } else  if (ExtractCashStatusEnums.FAILED.getCode().equals(orderMap.get("trade_sts"))) {
		            	record.setStatus(ExtractCashStatusEnum.EXTRACT_FAIL.getState());
		            	bonusstatus = PrizeBonusStatusEnum.DRAW_ERROR.getCode();//领奖失败,可能是卡不对
		            	messageService.addPushMessage(record.getUid(), record.getOrderid(), MessageTypeEnum.SEND_FAIL_NOTIFY.getCode());
		            }
	        	 record.setUpdatetime(new Date());
	        	 withdrawRecordDao.updateRecord(record);
	        	 //更新订单
	        	 prizeDao.updateBonusStatus(bonusstatus, record.getOrderid(), new Date(), null);
			}
	}

	@Override
	public int auditMediumHandle(String auditor,  MediumHandle mediumHandle, MultipartFile screenshort) {
		ValidatorUtils.checkBean(mediumHandle, com.hui10.common.constants.ICommon.PARAM_ERROR);
		if (null == IDCardCheckStatusEnums.getByCode(mediumHandle.getStatus().trim())) {
			throw new CommonException(ICommon.IDCARD_STATUS_ERROR, PropertiesUtils.get(ICommon.IDCARD_STATUS_ERROR));
		}
		LotteryOrder order = lotteryPrizeService.selectAdutingOrder(mediumHandle.getOrderid());
		MediumHandle handle = mediumHandleService.getById(mediumHandle.getId());
		//如果状态是审核成功、审核失败，则原来必须是待审核状态
		if ((StringUtils.equals(mediumHandle.getStatus(), IDCardCheckStatusEnums.CER_CHECK_OK.getCode()) || 
				StringUtils.equals(mediumHandle.getStatus(), IDCardCheckStatusEnums.CER_CHECK_FAIL.getCode())) 
				&& !StringUtils.equals(order.getBonusstatus()+"", IDCardCheckStatusEnums.NO_VALID.getCode())) {
			throw new CommonException(ICommon.IDCARD_STATUS_ERROR, PropertiesUtils.get(ICommon.IDCARD_STATUS_ERROR));
		//如果是领奖、领奖失败，则原来必须是审核成功
		}else if ((StringUtils.equals(mediumHandle.getStatus(), IDCardCheckStatusEnums.DRAW.getCode())
				|| StringUtils.equals(mediumHandle.getStatus(), IDCardCheckStatusEnums.DRAW_FAIL.getCode())) 
				&& !StringUtils.equals(order.getBonusstatus()+"", IDCardCheckStatusEnums.CER_CHECK_OK.getCode())) {
			throw new CommonException(ICommon.IDCARD_STATUS_ERROR, PropertiesUtils.get(ICommon.IDCARD_STATUS_ERROR));
		}
		if (mediumHandle.getSendprize()!=null && mediumHandle.getSendprize() > order.getWinprize()) {
			throw new CommonException(ICommon.WIN_PRIZE_ERROR, PropertiesUtils.get(ICommon.IDCARD_STATUS_ERROR));
		}
		handle.setStatus(mediumHandle.getStatus());
		handle.setHandler(mediumHandle.getHandler());
		handle.setSyshandler(auditor);
		handle.setUpdatetime(new Date());
		handle.setBackside(null);
		handle.setFaceside(null);
		handle.setDepartment(mediumHandle.getDepartment());
		handle.setPosition(mediumHandle.getPosition());
		handle.setId(UUIDUtil.getUUID());
		if (null != screenshort) {
			try {
				handle.setScreenshort(screenshort.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		mediumHandleDao.saveMediumHandle(handle);
		return prizeDao.updateBonusStatus(mediumHandle.getStatus(), order.getOrderid(), new Date(), mediumHandle.getSendprize());
	}

	

}




