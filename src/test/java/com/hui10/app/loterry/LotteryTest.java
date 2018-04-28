package com.hui10.app.loterry;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.service.loterry.LotteryInfoService;

/**
 * @ClassName: Lottery.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 上午10:18:38
 */
public class LotteryTest extends TestBase{
	@Autowired
	LotteryInfoService lotteryInfoService;
	public static void main(String[] args) throws IOException {
		String test = "----元";
		String[] tests = test.split("元");
		System.out.println(tests);
		
//		 String dateStr = "2008-1-1 1:21:28";
//		 String dateStr2 = "2010-1-2 1:21:28";
//		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		 SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		 try
//		 {
//		  Date date2 = format.parse(dateStr2);
//		  Date date = format.parse(dateStr);
//		System.out.println(differentDays(date, date2));
//		 }catch(Exception e){
//			 
//		 }
		// String pastNumber = "2017126";
		// String ON = pastNumber.substring(2, pastNumber.length());
		// System.out.println(ON);
		// String url = "http://kaijiang.500.com/ssq.shtml";
		// Document doc = Jsoup.connect(url).get();
		// Elements redballs = doc.select(".cfont2");
		// for (Element element : redballs) {
		// System.out.println(element.text());
		// }

		// String url = "http://kaijiang.500.com/shtml/ssq/17127.shtml";
		// Document doc = Jsoup.connect(url).get();
		// Elements redballs = doc.select(".ball_red");
		// for (Element element : redballs) {
		// System.out.println(element.text());
		// }
		// Elements bullball = doc.select(".ball_blue");
		//
		// System.out.println(bullball.get(0).text());
		//
		// Elements dElements = doc.select(".span_right");
		// String ud = dElements.get(0).text();
		// String[] udS = ud.split(" ");
		// for (int i = 0; i < udS.length; i++) {
		// String dst = udS[i];
		// String source = dst.substring(dst.indexOf("：")+1, dst.length());
		// SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		// try {
		// Date date = format.parse(source);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// String str = " 2017年10月29日 ";

	}

	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
	@Test
	public void queryLotteryInfoByname_test001() {
		Map<String, String> lotteryname = new HashMap<String, String>();
		lotteryname.put("lotterycode", "123456");
		lotteryname.put("authcode", null);
		Map<String, Object> map = lotteryInfoService.queryLotteryInfoByCode(lotteryname);
		if(null == map){
			System.out.println("map is null");
		}else{
		System.out.println("*************" + map.get("price"));
		}
	}
	@Test
	public void addOrUpdateLotteryInfo_test_001(){
		LotteryInfo lotteryInfo = new LotteryInfo();
		lotteryInfo.setLotterycode("100049");
		lotteryInfo.setLotteryname("3Dddd");
		lotteryInfo.setProvinceids("12");
		lotteryInfo.setPrice(200L);
		lotteryInfo.setOperator("7777777");
		lotteryInfoService.addOrUpdateLotteryInfo(lotteryInfo,false);
	}

}
