package com.hui10.pc.service.fenrun.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hui10.common.utils.DateUtils;
import com.hui10.pc.dao.fenrun.FenRunDao;
import com.hui10.pc.model.fenrun.FenRun;
import com.hui10.pc.service.fenrun.FenRunService;

/**
 * @ClassName: FenRunServiceImpl.java
 * @Description:
 * @author huangrui
 * @date 2018年4月24日  14:32:11
 */
@Service
public class FenRunServiceImpl implements FenRunService {
	
	private static final String DATE_FORMAT = "yyyy/MM";
	
	@Autowired
	private FenRunDao fenRunDao;

	@Override
	public long queryFenRunOfLastMonth(String merchantno) {
		String result = fenRunDao.queryFenRunOfLastMonth(merchantno);
		return Long.parseLong(result == null ? "0" : result);
	}

	@Override
	public Map<String, Object> queryFenRunList(String merchantno, Integer pageno, Integer pagesize){
		
		//查询分润列表
		List<FenRun> list = fenRunDao.queryFenRunList(merchantno);
		
		//补全缺省月份数据
		List<FenRun> totlelist = new ArrayList<>();
		try {		
			if(list != null && list.size() > 0){
				int size = list.size();
				Date start = DateUtils.getStringToDate(DATE_FORMAT, list.get(size-1).getMonth());
				Date end = DateUtils.getStringToDate(DATE_FORMAT, list.get(0).getMonth());
				while(end.after(start)){
					String month = DateUtils.date2Str(end, DATE_FORMAT);
					FenRun fenren = new FenRun();
					fenren.setMonth(month);
					for(FenRun bean: list){
						if(month.equals(bean.getMonth())){
							fenren.setMoney(bean.getMoney());
							break;
						}
					}
					totlelist.add(fenren);
					end = DateUtils.addOrMinusYear(end, 2, -1);
				}
				totlelist.add(list.get(size-1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		//总条数
		int count = totlelist.size();
	    int startindex = (pageno - 1) * pagesize;
	    int endindex = startindex + pagesize;
	    
	    //整理当前页返回数据
		List<FenRun> resultlist = new ArrayList<>();
	    for(int i=startindex; i<count; i++){
	    	if(i == endindex){
	    		break;
	    	}
	    	resultlist.add(totlelist.get(i));
	    }
	    
	    //查询分润总额
	    String totalStr = fenRunDao.queryFenRunTotalAmount(merchantno);
	    long total = Long.parseLong(totalStr == null ? "0" : totalStr);
	  		
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("count", count);
		map.put("pageno", pageno);
		map.put("pagesize", pagesize);
		map.put("list", resultlist);
		return map;
	}
}
