package com.hui10.app.model.lottery;

import java.util.Date;

/**
 * @ClassName: LotteryPast.java
 * @Description:
 * @author wengf
 * @date 2017年10月26日 上午11:28:34
 */
public class LotteryPast {
	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column hui_app_lottery_past.id
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	private String id;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column hui_app_lottery_past.issuenumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	private String issuenumber;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column hui_app_lottery_past.lotterytime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	private Date lotterytime;
	private Date lotteryendtime;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column hui_app_lottery_past.lotterynumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	private String lotterynumber;
	

	private String lotterytype;
	
	private String moneypool;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column hui_app_lottery_past.createtime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	private Date createtime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_lottery_past.id
	 *
	 * @return the value of hui_app_lottery_past.id
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_lottery_past.id
	 *
	 * @param id
	 *            the value for hui_app_lottery_past.id
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_lottery_past.issuenumber
	 *
	 * @return the value of hui_app_lottery_past.issuenumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public String getIssuenumber() {
		return issuenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_lottery_past.issuenumber
	 *
	 * @param issuenumber
	 *            the value for hui_app_lottery_past.issuenumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public void setIssuenumber(String issuenumber) {
		this.issuenumber = issuenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_lottery_past.lotterytime
	 *
	 * @return the value of hui_app_lottery_past.lotterytime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public Date getLotterytime() {
		return lotterytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_lottery_past.lotterytime
	 *
	 * @param lotterytime
	 *            the value for hui_app_lottery_past.lotterytime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public void setLotterytime(Date lotterytime) {
		this.lotterytime = lotterytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_lottery_past.lotterynumber
	 *
	 * @return the value of hui_app_lottery_past.lotterynumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public String getLotterynumber() {
		return lotterynumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_lottery_past.lotterynumber
	 *
	 * @param lotterynumber
	 *            the value for hui_app_lottery_past.lotterynumber
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public void setLotterynumber(String lotterynumber) {
		this.lotterynumber = lotterynumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_lottery_past.createtime
	 *
	 * @return the value of hui_app_lottery_past.createtime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_lottery_past.createtime
	 *
	 * @param createtime
	 *            the value for hui_app_lottery_past.createtime
	 *
	 * @mbg.generated Thu Oct 26 11:27:02 CST 2017
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(String lotterytype) {
		this.lotterytype = lotterytype;
	}

	public Date getLotteryendtime() {
		return lotteryendtime;
	}

	public void setLotteryendtime(Date lotteryendtime) {
		this.lotteryendtime = lotteryendtime;
	}

	public String getMoneypool() {
		return moneypool;
	}

	public void setMoneypool(String moneypool) {
		this.moneypool = moneypool;
	}
	
	

}