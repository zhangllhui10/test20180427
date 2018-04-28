package com.hui10.app.service.user.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.dao.user.BigprizeHandleDao;
import com.hui10.app.model.enums.PrizeBonusStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.user.BigprizeHandle;
import com.hui10.app.service.prize.LotteryPrizeService;
import com.hui10.app.service.user.BigprizeHandleService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: BigprizeHandleServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年11月16日 下午2:53:15
 */
@Service
public class BigprizeHandleServiceImpl implements BigprizeHandleService {
	
	
	@Autowired
	private BigprizeHandleDao bigprizeHandleDao;
	
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	
	@Autowired
	private PrizeDao prizeDao;

	@Override
	public int saveHandle(String uid, MultipartFile screenshort, MultipartFile photo, BigprizeHandle handle) {
		handle.setId(UUIDUtil.getUUID());
		handle.setSyshandler(uid);
		handle.setCreatetime(new Date());
		handle.setUpdatetime(new Date());
		handle.setHandletime(new Date());
		ValidatorUtils.checkBean(handle, com.hui10.common.constants.ICommon.PARAM_ERROR);
		try {
			handle.setScreenshort(screenshort.getBytes());
			if (null != photo) {
				handle.setWinnerphoto(photo.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LotteryOrder order = lotteryPrizeService.selectAdutingOrder(handle.getOrderid());
		if (!StringUtils.equals(order.getBonusstatus()+"", PrizeBonusStatusEnum.OFF_LINE_DRAW.getCode())) {
			throw new CommonException(ICommon.PRIZE_AMOUNT_IS_ZERO_ERROR, PropertiesUtils.get(ICommon.PRIZE_AMOUNT_IS_ZERO_ERROR));
		}
		prizeDao.updateBonusStatus(PrizeBonusStatusEnum.DRAW.getCode(), order.getOrderid(), new Date(), handle.getSendprize());
		return bigprizeHandleDao.saveHandle(handle);
	}

}
