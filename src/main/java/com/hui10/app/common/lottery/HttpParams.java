package com.hui10.app.common.lottery;


import com.hui10.common.utils.PropertiesUtils;

import java.util.Date;

/**
 * @ClassName: HttpParams.java
 * @Description:
 * @author yangcb
 * @date 2017年1月10日 上午10:52:11
 */
public class HttpParams<T> {
	private Long timestamp;
	private String channelId;
	private String transSerialNumber;
	private String signature;
	private T transData;

	private static String CHANNEL_ID= PropertiesUtils.get("LOTTERY_CHANNEL_ID");

	
	
	public HttpParams(Long timestamp, String channelId, String transSerialNumber, T transData) {
		super();
		this.timestamp = timestamp;
		this.channelId = channelId;
		this.transSerialNumber = transSerialNumber;
		this.transData = transData;
	}

	public HttpParams(Long timestamp, String channelId, String transSerialNumber, String signature, T transData) {
		super();
		this.timestamp = timestamp;
		this.channelId = channelId;
		this.transSerialNumber = transSerialNumber;
		this.signature = signature;
		this.transData = transData;
	}

	public HttpParams(){

		Date date=new Date();
		this.timestamp=date.getTime();
		this.channelId=CHANNEL_ID;
		this.signature="null";
	}

	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getTransSerialNumber() {
		return transSerialNumber;
	}
	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public T getTransData() {
		return transData;
	}
	public void setTransData(T transData) {
		this.transData = transData;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	
	
}
