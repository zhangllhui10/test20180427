package com.hui10.pc.model.merchant;

import java.io.Serializable;
import java.util.List;

import com.hui10.app.model.notice.SystemNoticeInfo;

public class IndexPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 420174509054893534L;
	
	private MyMerchant mymerchant;
	
	private TodaySale todaysale;
	
	private YesterdaySale yesterdaysale;
	
	private List<SystemNoticeInfo> notices;
	
	private long fenrun;

	public MyMerchant getMymerchant() {
		return mymerchant;
	}

	public void setMymerchant(MyMerchant mymerchant) {
		this.mymerchant = mymerchant;
	}

	public TodaySale getTodaysale() {
		return todaysale;
	}

	public void setTodaysale(TodaySale todaysale) {
		this.todaysale = todaysale;
	}

	public YesterdaySale getYesterdaysale() {
		return yesterdaysale;
	}

	public void setYesterdaysale(YesterdaySale yesterdaysale) {
		this.yesterdaysale = yesterdaysale;
	}

	public List<SystemNoticeInfo> getNotices() {
		return notices;
	}

	public void setNotices(List<SystemNoticeInfo> notices) {
		this.notices = notices;
	}

	public long getFenrun() {
		return fenrun;
	}

	public void setFenrun(long fenrun) {
		this.fenrun = fenrun;
	}

}
