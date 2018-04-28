package com.hui10.app.service.main.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.utils.StringFormat;
import com.hui10.app.dao.main.MainDao;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.model.banner.BannerInfo;
import com.hui10.app.model.enums.*;
import com.hui10.app.model.lottery.LotteryOpenVo;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.model.main.HomeLottery;
import com.hui10.app.model.main.HomePage;
import com.hui10.app.model.message.MessageInfo;
import com.hui10.app.model.order.enums.LotteryBetTypeEnum;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.model.user.UserInfoCache;
import com.hui10.app.model.user.WithdrawRecord;
import com.hui10.app.service.banner.BannerService;
import com.hui10.app.service.loterry.LotteryInfoService;
import com.hui10.app.service.loterry.LotteryPastService;
import com.hui10.app.service.main.MainService;
import com.hui10.app.service.message.MessageService;
import com.hui10.app.service.user.UserInfoCacheService;
import com.hui10.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: MainServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 下午2:17:33
 */
@Service
public class MainServiceImpl implements MainService{
	
	@Autowired
	private LotteryPastService lotteryPastService;
	
	@Autowired
	private LotteryInfoService lotteryInfoService;

    @Autowired
    private UserInfoCacheService userInfoCacheService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private MessageService messageService;

	@Autowired
	private PrizeDao prizeDao;

    @Autowired
    private MainDao mainDao;

	@Override
	public HomePage getPage(String uid) {
		List<LotteryPast> pasts = this.gePasts();
		int prizeCount = prizeDao.queryPrizeCount(uid);
		List<LotteryOpenVo> lotteryOpenVos = lotteryInfoService.getLotteryOpenVo();
		HomePage page = new HomePage();
		page.setLotteryOpenVos(lotteryOpenVos);
		page.setPrizeCount(prizeCount);
		page.setLotteryPast(pasts);
		return page;
	}

	@Override
	public List<LotteryPast> gePasts() {
		LotteryPast past = new LotteryPast();
		past.setLotterytype(Constants.DEFUALT_LOTTERY_TYPE_CODE);
		List<LotteryPast> pasts = lotteryPastService.queryPast(past);
		return pasts;
	}

    @Override
    public JSONObject queryHome(String uid) {

	    // 营销活动轮播图
        List<BannerInfo> bannerInfoList = bannerService.queryBannerList(null);
        List<JSONObject> bannerJsonList = new ArrayList<>();
        for (BannerInfo bannerInfo : bannerInfoList) {
            JSONObject bannerJson = new JSONObject();
            bannerJson.put("id", bannerInfo.getId());
            bannerJson.put("title", bannerInfo.getTitle());
            bannerJson.put("linkurl", bannerInfo.getLinkurl());
            bannerJson.put("picurl", bannerInfo.getPicurl());
            bannerJson.put("sort", bannerInfo.getSort());
            bannerJson.put("position", bannerInfo.getPosition());
            bannerJsonList.add(bannerJson);
        }

        List<HomeLottery> resultList = new ArrayList<HomeLottery>();
        boolean winPrizeFlag = false; // 是否有中奖未领取订单
        long winTotalAmount = 0; // 累计中奖金额
        int waitBettingNum = 0; // 待投订单数量
        int winPrizeNum= 0; // 待投订单数量
        List<MessageInfo> messageInfoList = null;

        if (StringUtils.isNotBlank(uid)) {

            Date systemDate = new Date();

            winTotalAmount = mainDao.queryWinTotalAmountByUid(uid);
            waitBettingNum = mainDao.queryWaitBettingNum(uid, systemDate);
            winPrizeNum = mainDao.queryWinPrizeNum(uid);

            int homeSize = Constants.HOME_LIST_SIZE;// 首页显示条数

            List<HomeLottery> lotteryList = mainDao.queryHomeOrders(uid, homeSize, systemDate);

            List<HomeLottery> homeLotteryList = new ArrayList<>();
            for (HomeLottery homeLottery : lotteryList) {
                // 过滤非赠送的过期订单（已超过投注截至时间的未支付订单）
                if (OrderTypeEnum.ORDINARY.getCode().equals(homeLottery.getOrdertype())
                        && systemDate.after(homeLottery.getLotterytime())
                        && homeLottery.getStatus() == OrderStatusEnum.NOPAY.getState()) {
                    continue;
                }
                // 过滤失败订单
                if (homeLottery.getStatus() == OrderStatusEnum.FAILBILL.getState()) {
                    continue;
                }
                homeLottery.setCodedetail(getFirstCodetail(homeLottery.getRemark()));
                resetOrderStatus(homeLottery, systemDate, winPrizeFlag);
                homeLotteryList.add(homeLottery);
            }

            resultList.addAll(homeLotteryList);

            // 消息中心
            messageInfoList = messageService.queryMessageList(uid, null, homeSize);

        }

        JSONObject cardJson = getUserLastUsedCard(uid, winPrizeFlag);


	    JSONObject homeJson = new JSONObject();
	    homeJson.put("wintotalamount", winTotalAmount);
	    homeJson.put("messagelist", messageInfoList);
	    homeJson.put("lotterylist", resultList);
	    homeJson.put("bannerlist", bannerJsonList);
	    homeJson.put("waitbettingnum", waitBettingNum);
	    homeJson.put("winprizenum", winPrizeNum);
	    homeJson.put("bankcard", cardJson);
	    homeJson.put("uid", uid);
        return homeJson;
    }

    @Override
    public void resetOrderStatus(HomeLottery homeLottery, Date systemDate, boolean winPrizeFlag) {

        // 出票失败
        if (homeLottery.getStatus() == OrderStatusEnum.FAILBILL.getState()) {
            homeLottery.setStatus(HomeLotteryStatusEnum.TICKET_FAIL.getState());
            return;
        }

        // 取消
        if (homeLottery.getStatus() == OrderStatusEnum.CANCEL.getState()) {
            homeLottery.setStatus(HomeLotteryStatusEnum.CANCLE.getState());
            return;
        }

        // 待支付
        if (OrderTypeEnum.ORDINARY.getCode().equals(homeLottery.getOrdertype())
                && homeLottery.getLotterytime().after(systemDate)
                && homeLottery.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            homeLottery.setStatus(HomeLotteryStatusEnum.WAIT_PAY.getState());
            return;
        }

        // 如果是赠送订单并且是待投注状态不展示注码
        if (OrderTypeEnum.GIVING.getCode().equals(homeLottery.getOrdertype())
                && homeLottery.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            homeLottery.setCodedetail("*,*,*,*,*,*|*");
            return;
        }

        // 待开奖（出票成功并且未开奖）
        if (homeLottery.getStatus() == OrderStatusEnum.PAID.getState()
                && homeLottery.getLotterystatus().equals(LotteryStatusEnum.NO_OPEN.getCode())) {
            homeLottery.setStatus(HomeLotteryStatusEnum.WAIT_OPEN.getState());
            return;
        }

        // 已中奖未领取奖金
        if (homeLottery.getWinstatus().equals(PrizeWinStatusEnum.WIN.getCode())
                && homeLottery.getBonusstatus().equals(PrizeBonusStatusEnum.NOT_DRAW.getCode())) {
            homeLottery.setStatus(HomeLotteryStatusEnum.WINNING.getState());
            winPrizeFlag = true;
            return;
        }

        // 已中奖已领取奖金
        if (homeLottery.getWinstatus().equals(PrizeWinStatusEnum.WIN.getCode())
                && !homeLottery.getBonusstatus().equals(PrizeBonusStatusEnum.NOT_DRAW.getCode())) {
            homeLottery.setStatus(HomeLotteryStatusEnum.WINNING.getState());
            // 中中等奖
            if (PrizeLevelEnum.MEDIUM.getCode().equals(homeLottery.getPrizelevel())) {
                MediumHandle mediumHandle = mainDao.queryWinOrderMedium(homeLottery.getOrderid());
                if (null == mediumHandle) return;
                homeLottery.setBankcardno(StringFormat.formatCardNumber(mediumHandle.getBankno()));
                return;
            } else if (PrizeLevelEnum.SMALL.getCode().equals(homeLottery.getPrizelevel())) { // 中小奖
                WithdrawRecord withdrawRecord = mainDao.querySmallBouns(homeLottery.getOrderid());
                if (null == withdrawRecord) return;
                homeLottery.setBankcardno(StringFormat.formatCardNumber(withdrawRecord.getPayeeno()));
                return;
            }
        }

        // 未中奖
        if (homeLottery.getStatus() == OrderStatusEnum.PAID.getState()
                && homeLottery.getLotterystatus().equals(LotteryStatusEnum.HAVE_OPE.getCode())
                && homeLottery.getWinstatus().equals(PrizeWinStatusEnum.NOT_WIN.getCode())) {
            homeLottery.setStatus(HomeLotteryStatusEnum.NOT_WINNING.getState());
            return;
        }

    }


    public JSONObject getUserLastUsedCard(String uid, boolean winPrizeFlag) {

	    if (!winPrizeFlag) {
	        return null;
        }

        UserInfoCache userInfoCache = userInfoCacheService.getUserInfoCache(uid);
        UserBankCard userBankCard = null != userInfoCache ? userInfoCache.getUserBankCard() : null;
        JSONObject cardJson = null;
        if (null != userBankCard) {
            cardJson = new JSONObject();
            cardJson.put("bankid", userBankCard.getId());
            cardJson.put("bankname", userBankCard.getCardname());
            cardJson.put("cardno", StringFormat.formatCardNumber(userBankCard.getCardno()));
        }
        return cardJson;

    }

    @Override
    public String getFirstCodetail(String remark) {
        JSONArray jsonArray = JSONArray.parseArray(remark);
        String codeDetail = null;
        for (int i=0; i<jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            if (LotteryBetTypeEnum.DANTUO.getState().equals(job.getString("bettype"))) {
                String redfix = job.getString("redfix");
                String reddrag = job.getString("reddrag");
                String blue = job.getString("blue");
                codeDetail = redfix + "|" + reddrag + "|" + blue;
                break;
            } else if (LotteryBetTypeEnum.SINGLE.getState().equals(job.getString("bettype"))) {
                String red = job.getString("red");
                String blue = job.getString("blue");
                codeDetail = red + "|" + blue;
                break;
            }
        }
        return codeDetail;
    }
}
