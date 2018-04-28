package com.hui10.app.model.order;

import java.util.List;

public class OrderAccountBalanceInfoVo {
	
	private int total;
	
	private List<OrderAccountBalanceInfo> list;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<OrderAccountBalanceInfo> getList() {
		return list;
	}

	public void setList(List<OrderAccountBalanceInfo> list) {
		this.list = list;
	}

}
