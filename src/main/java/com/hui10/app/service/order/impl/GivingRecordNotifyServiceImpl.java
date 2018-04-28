package com.hui10.app.service.order.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.SourceEnum;
import com.hui10.app.service.marketing.GivingRecordService;
import com.hui10.app.service.marketing.ReceiveLotteryService;
import com.hui10.app.service.order.GivingRecordNotifyService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;

@Service
public class GivingRecordNotifyServiceImpl implements GivingRecordNotifyService{
	
	private static final Logger logger = Logger.getLogger(GivingRecordNotifyServiceImpl.class);
	
	@Autowired
	private GivingRecordService givingRecordService;
	
	@Autowired
	private ReceiveLotteryService receiveLotteryService;

	@Override
	@Async
	public void notifyGivingRecord(LotteryOrder order) {
		 // 渠道id
        String channelId = null;
        if (SourceEnum.POS.getState() == order.getSource() || SourceEnum.APP.getState() == order.getSource()) {
            channelId = PropertiesUtils.get("givelottery.pos.channelid");
        } 
        List<LotteryGivingRecordInfo> records = givingRecordService.createGivingRecord(order.getUid(), channelId, order.getAcquirerno(), order.getSerialno()
                , order.getStationno(), order.getGamecode(), order.getOrderamount(), order.getStationprovince()
                , order.getMerchantno(), order.getOrderid());
        
        
		logger.debug("==============领取赠送记录开始============");
		
		//生成订单号
		for (LotteryGivingRecordInfo lotteryGivingRecordInfo : records) {
			try {
				String orderid = UUIDUtil.getUUID();
				//领取彩票
				LotteryGivingRecordInfo lotteryGivingRecord  = givingRecordService.receivingUpdate(order.getUid(), lotteryGivingRecordInfo.getId(), orderid);
				//生成订单
				receiveLotteryService.generateGivingOrder(lotteryGivingRecord);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("==================赠送的彩票"+lotteryGivingRecordInfo.getId()+"领取失败===================",e);
			}
		}
		logger.debug("==============领取赠送记录结束============");

	}

}
