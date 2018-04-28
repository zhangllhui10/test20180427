package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.List;

public class OrderAccountBalanceDetailVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7046105743270803091L;

	private int count;
	
	private List<OrderAccountBalanceDetail> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OrderAccountBalanceDetail> getList() {
		return list;
	}

	public void setList(List<OrderAccountBalanceDetail> list) {
		this.list = list;
	}
	
}
