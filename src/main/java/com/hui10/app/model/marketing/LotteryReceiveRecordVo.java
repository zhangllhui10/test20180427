package com.hui10.app.model.marketing;

import java.io.Serializable;
import java.util.List;

public class LotteryReceiveRecordVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3530244983774598482L;
	
	private int count;
	
	private List<LotteryReceiveRecordInfo> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<LotteryReceiveRecordInfo> getList() {
		return list;
	}

	public void setList(List<LotteryReceiveRecordInfo> list) {
		this.list = list;
	}

}
