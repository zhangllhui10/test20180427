package com.hui10.pc.service.merchant.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui10.app.model.notice.SystemNoticeInfo;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.app.service.notice.SystemNoticeService;
import com.hui10.common.utils.DateUtils;
import com.hui10.pc.dao.merchant.IndexPageDao;
import com.hui10.pc.model.merchant.IndexPage;
import com.hui10.pc.model.merchant.MyMerchant;
import com.hui10.pc.model.merchant.TodaySale;
import com.hui10.pc.model.merchant.TodaySaleList;
import com.hui10.pc.model.merchant.YesterdaySale;
import com.hui10.pc.service.fenrun.FenRunService;
import com.hui10.pc.service.merchant.IndexPageService;

@Service
public class IndexPageServiceImpl implements IndexPageService{
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private IndexPageDao indexPageDao;
	
	@Autowired
	private SystemNoticeService systemNoticeService;
	
	@Autowired
	private FenRunService fenRunService;
	
	private static final String[] HOURS= {
		"00","01","02","03","04","05","06","07","08",
		"09","10","11","12","13","14","15","16",
		"17","18","19","20","21","22","23"
	};
	
	private static final int NOTICE_COUNT = 3;

	@Override
	public IndexPage findIndexPage(String merchantno) {
		
		IndexPage indexPage = new IndexPage();
		
		merchantInfoService.findMerchantInfoByMerchantNo(merchantno);
		
		//查找总销量
		Map<String, Object> sale = indexPageDao.findSale(merchantno, null, null);
		
		MyMerchant myMerchant = new MyMerchant();
		
		int salecount = sale.get("salecount") == null ? 0 : Integer.parseInt(sale.get("salecount").toString());
		
		long salemoney = sale.get("salemoney") == null ? 0l : Long.parseLong(sale.get("salemoney").toString());
		
		myMerchant.setSalecount(salecount);
		myMerchant.setSalemoney(salemoney);
		
		indexPage.setMymerchant(myMerchant);
		
		//查找今日销量
		sale = indexPageDao.findSale(merchantno, DateUtils.dayStart(new Date()), DateUtils.dayEnd(new Date()));
		
		TodaySale todaySale = new TodaySale();
		
		salecount = sale.get("salecount") == null ? 0 : Integer.parseInt(sale.get("salecount").toString());
		
		salemoney = sale.get("salemoney") == null ? 0l : Long.parseLong(sale.get("salemoney").toString());
		
		todaySale.setSalecount(salecount);
		todaySale.setSalemoney(salemoney);
		
		List<Map<String, Object>> list =  indexPageDao.findTodaySale(merchantno, DateUtils.dayStart(new Date()), DateUtils.dayEnd(new Date()));
		
		List<TodaySaleList> todaySaleLists = new ArrayList<>();
		
		int index = 0;//lis指针
		
		for (int i = 0; i < HOURS.length; i++) {
			TodaySaleList todaySaleList = new TodaySaleList();
			
			todaySaleList.setHour(HOURS[i]);
			if (list.size() > 0) {
				if (Integer.parseInt(HOURS[i]) < Integer.parseInt(list.get(index).get("ordertime").toString())) {
					todaySaleList.setSalecount(0);
					todaySaleList.setSalemoney(0l);
				}else if (Integer.parseInt(HOURS[i]) == Integer.parseInt(list.get(index).get("ordertime").toString())) {
					int count = list.get(index).get("salecount") == null ? 0 : Integer.parseInt(list.get(index).get("salecount").toString());
					long money = list.get(index).get("salemoney") == null ? 0l : Long.parseLong(list.get(index).get("salemoney").toString());
					todaySaleList.setSalecount(count);
					todaySaleList.setSalemoney(money);
					if (index != list.size() -1) {
						index ++;
					}
				}else {
					todaySaleList.setSalecount(0);
					todaySaleList.setSalemoney(0l);
				}
			}else {
				todaySaleList.setSalecount(0);
				todaySaleList.setSalemoney(0l);
			}
			todaySaleLists.add(todaySaleList);
		}
		
		todaySaleLists.get(0).setHour("24");
		
		TodaySaleList temp = todaySaleLists.get(0);
		
		todaySaleLists.remove(0);
		
		todaySaleLists.add(temp);
		
		todaySale.setList(todaySaleLists);
		
		indexPage.setTodaysale(todaySale);
		
		
		//查找昨天销量
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DATE, -1);
		
		sale = indexPageDao.findSale(merchantno, DateUtils.dayStart(calendar.getTime()), DateUtils.dayEnd(calendar.getTime()));
		
		YesterdaySale yesterdaySale = new YesterdaySale();
		
		salecount = sale.get("salecount") == null ? 0 : Integer.parseInt(sale.get("salecount").toString());
		
		salemoney = sale.get("salemoney") == null ? 0l : Long.parseLong(sale.get("salemoney").toString());
		
		yesterdaySale.setSalecount(salecount);
		yesterdaySale.setSalemoney(salemoney);
		
		indexPage.setYesterdaysale(yesterdaySale);
		
		//查询公告
		List<SystemNoticeInfo> notices = systemNoticeService.querySystemNoticeList(NOTICE_COUNT);
		indexPage.setNotices(notices);
		
		//查找分润
		indexPage.setFenrun(fenRunService.queryFenRunOfLastMonth(merchantno));
		return indexPage;
		
	}
}
