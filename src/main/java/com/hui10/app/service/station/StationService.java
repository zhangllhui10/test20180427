package com.hui10.app.service.station;

public interface StationService {
	
	/**
	 * @param merchantno
	 * @param provinceid
	 * @param cityid
	 * @return
	 */
	String distributeStationCode(String merchantno, String provinceid, String cityid);
}