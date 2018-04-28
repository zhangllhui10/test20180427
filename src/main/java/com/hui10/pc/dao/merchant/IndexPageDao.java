package com.hui10.pc.dao.merchant;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IndexPageDao {
	
	Map<String, Object> findSale(@Param("merchantno")String merchantno, @Param("begin")Date begin, @Param("end")Date end);
	
	List<Map<String, Object>> findTodaySale(@Param("merchantno")String merchantno, @Param("begin")Date begin, @Param("end")Date end);
	
}
