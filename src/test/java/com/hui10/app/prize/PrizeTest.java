package com.hui10.app.prize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.TestBase;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.model.enums.PrizeFileStatusEnum;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.prize.LotteryPrizeFileContent;
import com.hui10.app.model.prize.LotteryPrizeFileInfo;
import com.hui10.app.service.prize.LotteryPrizeAsyncService;
import com.hui10.app.service.prize.LotteryPrizeService;
import com.hui10.common.utils.PropertiesUtils;

/**
 * @ClassName: PrizeTest.java
 * @Description:
 * @author zhangll
 * @date 2017年10月19日 下午12:45:52
 */
public class PrizeTest extends TestBase {
	@Autowired
	private PrizeDao prizeDao;
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	@Autowired
	private LotteryPrizeAsyncService lotteryPrizeAsyncService;
	private String gamecode = "10002";
	private String issuenumber = "2017046";
	private Long lotterytime = 1508752820346L;
	private String filecheckcode = "cb768fa585556d7d1580dbab32bffb45";

	@Test
	public void updatePrizeFile_001() {
		LotteryPrizeFileInfo lotteryPrizeFileInfo = new LotteryPrizeFileInfo();
		lotteryPrizeFileInfo.setStatus(PrizeFileStatusEnum.TREATED.getCode());
		lotteryPrizeFileInfo.setGamecode(gamecode);
		lotteryPrizeFileInfo.setIssuenumber(issuenumber);
		lotteryPrizeFileInfo.setFilecheckcode(filecheckcode);
		lotteryPrizeFileInfo.setUpdatetime(new Date());
		prizeDao.updatePrizeFile(lotteryPrizeFileInfo);
	}

	@Test
	public void updateLotteryOrder_001() {

		List<LotteryPrizeFileContent> lotteryPrizeList = new ArrayList<LotteryPrizeFileContent>();

		LotteryPrizeFileContent lotteryPrize = new LotteryPrizeFileContent();
		lotteryPrize.setPrizelevel("1");
		lotteryPrize.setOrderNo("12345654321");
		lotteryPrize.setWinPrize(2000000l);

		LotteryPrizeFileContent lotteryPrize1 = new LotteryPrizeFileContent();
		lotteryPrize1.setPrizelevel("2");
		lotteryPrize1.setOrderNo("1234321");
		lotteryPrize1.setWinPrize(200l);

		LotteryPrizeFileContent lotteryPrize2 = new LotteryPrizeFileContent();
		lotteryPrize2.setPrizelevel("3");
		lotteryPrize2.setOrderNo("1234321");
		lotteryPrize2.setWinPrize(200l);

		lotteryPrizeList.add(lotteryPrize);
		lotteryPrizeList.add(lotteryPrize1);
		lotteryPrizeList.add(lotteryPrize2);
		prizeDao.updateLotteryOrder(lotteryPrizeList);
	}

	@Test
	public void treatePrize_ORDINARY() {
		String localPath = PropertiesUtils.get("ftp.local.path");
		lotteryPrizeService.treatePrize_Order(gamecode, issuenumber, lotterytime, filecheckcode,"10002_2017046.txt", localPath,OrderTypeEnum.ORDINARY.getCode(),null);

	}
	
	@Test
	public void treatePrize_GIVING() {
		String localPath = PropertiesUtils.get("ftp.local.path");
		lotteryPrizeService.treatePrize_Order(gamecode, issuenumber, lotterytime, filecheckcode,"10002_2017046.txt", localPath,OrderTypeEnum.GIVING.getCode(),"Junit_001");

	}

	@Test
	public void winPrizeNotify_001() {
	}

	// 定时任务
	@Test
	public void winPrizeNotifyJob_test001() {
		lotteryPrizeService.winPrizeNotifyJob();
	}

	@Test
	public void winPrizeNotify_tesst001() {
		JSONObject transData = new JSONObject();
		transData.put("lotteryno", "2018026");
		transData.put("lotterytype", "10001");
		transData.put("filemd5", "filemd5");
		transData.put("lotterytime", "1512011457168");

		lotteryPrizeAsyncService.winPrizeNotify(transData);
	}
}
