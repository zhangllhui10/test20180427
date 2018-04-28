package com.hui10.app.service.marketing.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.DoubleColorBall;
import com.hui10.app.common.lottery.HttpUtil;
import com.hui10.app.common.lottery.LotteryIwtUtil;
import com.hui10.app.common.lottery.NumberUtils;
import com.hui10.app.common.lottery.dto.LotterySaleDto;
import com.hui10.app.common.utils.BetUtil;
import com.hui10.app.dao.marketing.PromotionRecordDao;
import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.dao.order.TicketDao;
import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.marketing.LotteryReceiveRecordInfo;
import com.hui10.app.model.marketing.LotteryReceiveRecordVo;
import com.hui10.app.model.marketing.enums.BetTimeTypeEnum;
import com.hui10.app.model.marketing.enums.MarketingResourceEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.Ticket;
import com.hui10.app.model.order.enums.LotteryBetTypeEnum;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.order.enums.SourceEnum;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.service.marketing.GivingRecordService;
import com.hui10.app.service.marketing.ReceiveLotteryService;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;
import com.poslot.model.lottery.BetFactory;

/**
 * @ClassName: ReceiveLotteryServiceImpl.java
 * @Description:
 * @author huangrui
 * @date 2018年3月8日 10:16:09
 */
@Service
public class ReceiveLotteryServiceImpl implements ReceiveLotteryService {
	
	private static final Logger logger = Logger.getLogger(ReceiveLotteryServiceImpl.class);
		
	@Autowired
	private GivingRecordService givingRecordService;
	
	@Autowired
	private PromotionRecordDao promotionRecordDao;
	
	@Autowired
	private LotteryOrderSerice lotteryOrderSerice;
	
	@Autowired
	private LotteryOrderDao lotteryOrderDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
    private LotteryIwtUtil lotteryIwtUtil;
	
	@Value("${givelottery.hcb.promotionid}")
	private String blcpromotionid;
	
	@Value("${lottery_jackpot}")
	private String url;
	
	/**
     * 固定投注站
     */
    private static String STATION_NO = PropertiesUtils.get("STATION_NO");
    private static String STATION_PROVINCE = PropertiesUtils.get("STATION_PROVINCE");
    
    private static int LOTTERY_BIG_AMOUNT = Integer.parseInt(PropertiesUtils.get("LOTTERY_BIG_AMOUNT"));

    private static int LOTTERY_BIG_MULTIPLE = Integer.parseInt(PropertiesUtils.get("LOTTERY_BIG_MULTIPLE"));
	
	@Override
	public List<LotteryGivingRecordInfo> findNotReceivedLotteries(String uid) {
		return promotionRecordDao.findNotReceivedRecords(uid);
	}
	
	@Override
	public LotteryReceiveRecordVo findReceivedLotteries(String uid, Integer pageno, Integer pagesize, String winstatus) {
		
		LotteryReceiveRecordVo result = new LotteryReceiveRecordVo();
		if (null == pageno || pageno <= 0) {
			pageno = Constants.DEFAULT_PAGENO;
		}
		
		if (null == pagesize || pagesize <= 0) {
			pagesize = Constants.DEFAULT_PAGESIZE;
		}
		
		int count = promotionRecordDao.findReceivedLotteriesCount(uid);
		
		if (count == 0) {
			result.setCount(0);
			result.setList(new ArrayList<>(0));
		}else {
			List<LotteryReceiveRecordInfo> list = promotionRecordDao.findReceivedLotteries(uid, (pageno-1)*pagesize, pagesize, winstatus);
			
			for (LotteryReceiveRecordInfo lotteryReceiveRecordInfo : list) {
				if (OrderStatusEnum.PAID.getState() != lotteryReceiveRecordInfo.getStatus()) {
					lotteryReceiveRecordInfo.setBetdetail(null);
				}
			}
			result.setList(list);
			result.setCount(count);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> findMarketingPoolAndCarousel() {
		Map<String, String> params = new HashMap<>();
		params.put("promotionId", blcpromotionid);
		params.put("channel", "HUI10");
		/**
		 * 签名
		 */
		String signature = DigestUtils.md5Hex(Constants.LOTTERY_MD5_PREFIX + params.toString() + Constants.LOTTERY_MD5_SUFFIX);
		params.put("signature", signature);
		
		String transSerialNumber = UUIDUtil.getUUID();
		
		try {
			String fullurl = String.format(PropertiesUtils.get("lottery_url"), url);
			String result = HttpUtil.getInstance().doPost(transSerialNumber, fullurl, params);
			
			logger.debug("===============报文相应信息"+result+"=============");
			
			JSONObject jsonObject = JSONObject.parseObject(result);
			
			if ("9999".equals(jsonObject.getString("result"))) {
				throw new CommonException(ICommon.REQUEST_BLC_FAILED, 
						String.format(PropertiesUtils.get(ICommon.REQUEST_BLC_FAILED), "{" + jsonObject.getString("resultDesc") + "}"));
			}
			
			result = jsonObject.getString("jackpot");
			
			JSONObject jsonResult = JSONObject.parseObject(result);
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("marketingpool", jsonResult.get("marketingpool"));
			map.put("openlottery", jsonResult.get("openlottery"));
			map.put("promotionticketlist", jsonResult.get("promotionticketlist"));
			return map;
		} catch (Exception e) {
			logger.error("=========================请求超时==================="+e);
			throw new CommonException(ICommon.REQUEST_BLC_TIMEOUT, 
					String.format(PropertiesUtils.get(ICommon.REQUEST_BLC_TIMEOUT), "{" + e.getMessage() + "}"));
		}
	}
	
	@Override
	public String receiveLottery(String id, String uid) {
		
		//生成订单号
		String orderid = UUIDUtil.getUUID();
		
		//领取彩票
		LotteryGivingRecordInfo lotteryGivingRecord  = givingRecordService.receivingUpdate(uid, id, orderid);

		//生成订单
		try {
			this.generateGivingOrder(lotteryGivingRecord);
		} catch (CommonException e) {
			e.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_RECEIVED_FAIL, PropertiesUtils.get(ICommon.LOTTERY_RECEIVED_FAIL));
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new CommonException(ICommon.LOTTERY_RECEIVED_FAIL, PropertiesUtils.get(ICommon.LOTTERY_RECEIVED_FAIL));
		}
		
		return orderid;
	}
	
	@Override
	public void updateOrderStatus(JSONObject transData) {
		lotteryOrderSerice.updateOrderStatus(transData);
	}

	@Override
	public void generateGivingOrder(LotteryGivingRecordInfo lotteryGivingRecord) {
		//获取基础信息
        String uid = lotteryGivingRecord.getUid();
        String lotterycode = lotteryGivingRecord.getLotterycode();
        String betDetail = lotteryGivingRecord.getBetdetail();
        String stationno = lotteryGivingRecord.getStationno();
        String stationprovince = lotteryGivingRecord.getStationprovince();
        String merchantno = lotteryGivingRecord.getMerchantno();
        String acquirerno = lotteryGivingRecord.getAcquirerno();
        String serialno = lotteryGivingRecord.getSerialno();
        String orderid = lotteryGivingRecord.getOrderid();
        String orderno = lotteryGivingRecord.getPoslotorderno();

        if (StringUtils.isBlank(stationno)) {
            stationno = STATION_NO;
        }
        if (StringUtils.isBlank(stationprovince)) {
            stationprovince = STATION_PROVINCE;
        }

        //查询用户手机号
        UserInfo userInfo = userInfoService.queryUserInfoByUid(uid);
        String userphone = userInfo.getPhone();

        //整理订单信息
        LotteryOrder lotteryOrder = new LotteryOrder();
        lotteryOrder.setOrderid(orderid);  
        lotteryOrder.setGamecode(lotterycode);
        lotteryOrder.setCreatedate(new Date());
        lotteryOrder.setSource(SourceEnum.APP.getState());
        lotteryOrder.setUserphone(userphone);    
        lotteryOrder.setOrdertype(OrderTypeEnum.GIVING.getCode());
        lotteryOrder.setUid(uid);
        lotteryOrder.setMerchantno(merchantno);
        lotteryOrder.setAcquirerno(acquirerno);
        lotteryOrder.setSerialno(serialno);
        lotteryOrder.setStationno(stationno);
        lotteryOrder.setStationprovince(stationprovince);
        lotteryOrder.setPaytime(new Date());
        lotteryOrder.setOrderno(orderno);

        //整理订单详情
        JSONArray jsonBetArray = JSON.parseArray(betDetail);
        long orderamount = 0;
        for (int i = 0; i < jsonBetArray.size(); i++) {
            JSONObject jsonObject = jsonBetArray.getJSONObject(i);
            String betType = jsonObject.getString("bettype");
            String codeDetail = null;
            if (LotteryBetTypeEnum.DANTUO.getState().equals(betType)) {
                String redfix = jsonObject.getString("redfix");
                String reddrag = jsonObject.getString("reddrag");
                String blue = jsonObject.getString("blue");
                codeDetail = redfix + "|" + reddrag + "|" + blue;
            } else if (LotteryBetTypeEnum.SINGLE.getState().equals(betType)) {
                String red = jsonObject.getString("red");
                String blue = jsonObject.getString("blue");
                codeDetail = red + "|" + blue;
            }

            //判断是否超出最大倍数
            int multiple = jsonObject.getIntValue("multiple");
            if (multiple > LOTTERY_BIG_MULTIPLE) {
                throw new CommonException(ICommon.LOTTERY_BIG_MULTIPLE, PropertiesUtils.get(ICommon.LOTTERY_BIG_MULTIPLE));
            }
            if (multiple < 1) {
                throw new CommonException(ICommon.LOTTERY_MIN_MULTIPLE, PropertiesUtils.get(ICommon.LOTTERY_MIN_MULTIPLE));
            }
            int size = checkCodeDetail(betType, codeDetail);
            long amount = 200 * multiple * size;
            orderamount += amount;
        }

        if (orderamount > LOTTERY_BIG_AMOUNT) {
            throw new CommonException(ICommon.LOTTERY_BIG_AMOUNT_ERROR, PropertiesUtils.get(ICommon.LOTTERY_BIG_AMOUNT_ERROR));
        }

        int betNumber = BetUtil.getBetNumber(BetFactory.getBet(betDetail)); // 总注数

        lotteryOrder.setRemark(betDetail);
        lotteryOrder.setOrderamount(Integer.parseInt(String.valueOf(orderamount)));
        lotteryOrder.setBetnumber(betNumber);

        //插入订单信息
        lotteryOrderDao.insert(lotteryOrder);

        //判断是否是定期开奖，若不是，则直接投注
        if(MarketingResourceEnum.LOCAL.getState().equals(lotteryGivingRecord.getResource()) && 
        		BetTimeTypeEnum.BET_IMMEDIATE.getState().equals(lotteryGivingRecord.getBettimetype())){
        	//查询当前期号
            LotterySaleDto lotterySaleDto = lotteryIwtUtil.getLotterySaleDto(stationno, stationprovince, 2, lotterycode);
            String issuenumber = lotterySaleDto.getIssuenumber();
            logger.debug("获取当前期号："+issuenumber);
            
        	//订单投注
        	Map<String, Object> result = lotteryIwtUtil.betOrder(stationno, userphone, orderid, stationprovince, lotterycode, issuenumber, betDetail);

        	int status = Integer.parseInt(result.get("orderStatus").toString());
        	if(status == OrderStatusEnum.FAILBILL.getState()){
        		throw new CommonException(ICommon.LOTTERY_RECEIVED_FAIL, PropertiesUtils.get(ICommon.LOTTERY_RECEIVED_FAIL));
        	}

        	orderno = result.get("orderNo").toString();
        	Date ordertime = new Date(Long.parseLong(result.get("orderTime").toString()));

        	//保存出票信息
        	List<Ticket> ticketlist = (List<Ticket>) result.get("ticketList");
        	if(ticketlist != null){
    			for(int i = 0; i < ticketlist.size(); i++){
    	           Ticket ticket = ticketlist.get(i);
    	           ticket.setOrderno(orderid);
    	           ticket.setBuid(uid);
    	        }
    	        ticketDao.batchInsertTicket(ticketlist);
    		}

    		//更新订单
    		LotteryOrder order = new LotteryOrder();
            order.setOrderid(orderid);
            order.setOrderno(orderno);
            order.setOrdertime(ordertime);
            order.setStatus(status);
            order.setUpdatedate(new Date());
            order.setIssuenumber(issuenumber);
            order.setLotterytime(lotterySaleDto.getLotteryendtime());
            lotteryOrderDao.update(order);
        }
	}
	
	/**
     * 校验投注是否合法
     *
     * @param betType
     * @param code
     * @return
     */
    private int checkCodeDetail(String betType, String code) {

        String[] array = code.split("\\|");

        String redCode = array[0];

        String[] redNumbers = redCode.split(",");
        int red = redNumbers.length;
        // 判断红球号码是否有效
        int[] invalidNumbers = NumberUtils.checkInvalidNumbers(convertArray(redNumbers), DoubleColorBall.CANDIDATE_NUMBERS_RED);
        if (invalidNumbers.length > 0) {
            throw new CommonException(999, "双色球投注：红球号码中下列号码为无效号码，"
                    + NumberUtils.unionNumbers(invalidNumbers, ","));
        }
        // 判断红球号码是否有重复
        int[] repeatNumbers = NumberUtils.checkRepeatNumbers(invalidNumbers);
        if (repeatNumbers != null && repeatNumbers.length > 0) {
            throw new CommonException(999, "双色球投注：红球号码中出现重复，下列号码重复，"
                    + NumberUtils.unionNumbers(repeatNumbers, ","));
        }

        int size = 1;
        if (LotteryBetTypeEnum.DANTUO.getState().equals(betType)) {
            if (red > 5 || red < 1) {
                throw new CommonException(999, "双色球胆拖投注：红球号码必须是1-5个");
            }
            String[] tuoArray = array[1].split(",");
            int tuoCount = tuoArray.length;
            for (int j = 0; j < tuoCount; j++) {
                for (int m = 0; m < redNumbers.length; m++) {
                    if (tuoArray[j].equals(redNumbers[m])) {
                        throw new CommonException(999, "双色球投注,胆拖有重复！");
                    }
                }
            }
            if (red + tuoCount < 6) {
                throw new CommonException(999, "请至少选择6个红球（胆码+拖码）！");
            }
            String[] blueNumbers = array[2].split(",");
            int blueCount = blueNumbers.length;
            if (blueCount < 1 || blueCount > 8) {
                throw new CommonException(999, "双色球胆拖投注：蓝球号码必须是1-8个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(blueNumbers), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }

            if (tuoCount == 1) {
                size = count(6, blueCount);
            } else {
                size = calculateDantuoAmount(red, tuoCount, blueCount);
            }

        } else if (LotteryBetTypeEnum.MULTIPLE.getState().equals(betType)) {

            if (red < 6 || red > 20) {
                throw new CommonException(999, "双色球投注：红球号码必须是6-20个");
            }
            int blue = array[1].split(",").length;

            if (blue < 1) {
                throw new CommonException(999, "双色球复式投注：蓝球号码必须是1-16个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(array[1].split(",")), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }
            size = count(red, blue);
        } else {
            if (red != 6) {
                throw new CommonException(999, "双色球：红球号码必须是6个");
            }

            if (array.length < 2) {
                throw new CommonException(999, "双色球：请正确选择投注号码");
            }

            String[] blueCodeArray = array[1].split(",");
            if (blueCodeArray.length != 1) {
                throw new CommonException(999, "双色球：蓝球号码必须是1个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(blueCodeArray), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }
        }
        return size;
    }
    
    private int[] convertArray(String[] numbers) {

        int[] array = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            array[i] = Integer.parseInt(numbers[i]);
        }
        return array;
    }
    
    /***
     * 计算胆拖总注数
     */

    private int calculateDantuoAmount(int danCount, int tuoCount, int blueCount) {
        if (danCount < 1 || tuoCount < 2 || blueCount < 1) {
            return 1;
        }

        return numerator(tuoCount, tuoCount, 6 - danCount) / fib(6 - danCount) * blueCount;
    }

    private int numerator(int m, int m2, int n) {
        if (m < (m2 - n + 1)) {  // m*(m-1)*(m-2)*(m-3)*(m-n+1)
            return 1;
        }
        return m * numerator(m - 1, m2, n);
    }


    private int fib(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * fib(n - 1);

    }


    private int count(int redBall, int blueBall) {
        int p = 1;
        int c = 1;
        for (int i = 1; i <= 6; i++) {
            c = c * i;
        }
        for (int j = (redBall - 5); j <= redBall; j++) {
            p = p * j;
        }
        return p / c * blueBall;
    }
	
}
