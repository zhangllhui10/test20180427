package com.hui10.app.service.user.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.dao.user.MediumHandleDao;
import com.hui10.app.model.enums.IDCardCheckStatusEnums;
import com.hui10.app.model.enums.PrizeBonusStatusEnum;
import com.hui10.app.model.enums.PrizeLevelEnum;
import com.hui10.app.model.enums.PrizeWinStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.service.prize.LotteryPrizeService;
import com.hui10.app.service.user.MediumHandleService;
import com.hui10.app.service.user.UserBankCardService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: UserIdCardServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年10月23日 下午5:37:52
 */
@Service
public class MediumHandleServiceImpl implements MediumHandleService {
	
	@Autowired
	private MediumHandleDao mediumHandleDao;
	
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	
	@Autowired
	private PrizeDao prizeDao;
	
	@Autowired
	private LotteryOrderDao lotteryOrderDao;
	
	@Autowired
	private UserBankCardService userBankCardService;


	@Override
	public int bindIdCard(String uid, String orderid,  MultipartFile faceSide, MultipartFile backSide, String name, String idcardno, String proxyname, String bankcardid) throws IOException {
		if (!StringUtils.equals(name, proxyname)) {
			throw new CommonException(ICommon.PROXYNAME_ERROR, PropertiesUtils.get(ICommon.PROXYNAME_ERROR));
		}
		if(!(idcardno.length()== 18 || idcardno.length() == 15)){
			throw new CommonException(ICommon.IDCARD_LENGTH_ERROR, PropertiesUtils.get(ICommon.IDCARD_LENGTH_ERROR));
		}
		LotteryOrder order = lotteryPrizeService.selectPrizeOrder(orderid, uid);
		List<MediumHandle> cards = mediumHandleDao.queryHandles(uid, orderid);
		if (!cards.isEmpty()) {
			throw new CommonException(ICommon.ALREDY_UPLOAD_ERROR, PropertiesUtils.get(ICommon.ALREDY_UPLOAD_ERROR));
		}
		MediumHandle handle = new MediumHandle();
		long prizeAmount = order.getWinprize();
		int result = 0;
		if (prizeAmount <= 0 || !StringUtils.equals(order.getWinstatus()+"", PrizeWinStatusEnum.WIN.getCode()) 
				|| StringUtils.equals(order.getBonusstatus()+"", PrizeBonusStatusEnum.DRAW.getCode())) {
			throw new CommonException(ICommon.PRIZE_AMOUNT_ZERO_ERROR, PropertiesUtils.get(ICommon.PRIZE_AMOUNT_ZERO_ERROR));
		}else if (StringUtils.equals(order.getPrizelevel(), PrizeLevelEnum.BIG.getCode())) {
			throw new CommonException(ICommon.PRIZE_AMOUNT_TOO_BIGER_ERROR, PropertiesUtils.get(ICommon.PRIZE_AMOUNT_TOO_BIGER_ERROR));
		}else if (StringUtils.equals(order.getPrizelevel(), PrizeLevelEnum.MEDIUM.getCode())) {
			UserBankCard bankCard = userBankCardService.validateUserBindCard(uid, bankcardid);
			handle.setId(UUIDUtil.getUUID());
			handle.setUid(uid);
			handle.setOrderid(orderid);
			handle.setCreatetime(new Date());
			handle.setUpdatetime(new Date());
			handle.setName(name);
			handle.setFaceside(faceSide.getBytes());
			handle.setBackside(backSide.getBytes());
			handle.setStatus(IDCardCheckStatusEnums.NO_VALID.getCode());
			handle.setBankno(bankCard.getCardno());
			handle.setBankname(bankCard.getCardname());
			handle.setIdcardno(idcardno);
			result = mediumHandleDao.saveMediumHandle(handle);
			//更新订单
	   	 	prizeDao.updateBonusStatus(PrizeBonusStatusEnum.CER_CHECKING.getCode(), orderid, new Date(), null);

		}else if(StringUtils.equals(order.getPrizelevel(), PrizeLevelEnum.SMALL.getCode())) {
			throw new CommonException(ICommon.LITTL_PRIZ_ERROR, PropertiesUtils.get(ICommon.LITTL_PRIZ_ERROR));
		}
		
		return result;
	}


	@Override
	public int cancelAppOrder(String uid, String orderid) {
		LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderIdUid(uid, orderid);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        lotteryOrder.setStatus(OrderStatusEnum.CANCEL.getState());
		return lotteryOrderDao.update(lotteryOrder);
	}


	@Override
	public int updateHandle(MediumHandle handle) {
		
		return mediumHandleDao.updateMediumHandle(handle);
	}


	@Override
	public MediumHandle getById(String id) {
		MediumHandle handle = mediumHandleDao.getById(id);
		if (null == handle) {
			throw new CommonException(ICommon.HANDLE_NOT_FOND, PropertiesUtils.get(ICommon.HANDLE_NOT_FOND));
		}
		return handle;
	}


}
