package com.hui10.app.model.user;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class BigprizeHandle {
	private String id;

	@NotNull
	private String department;

	@NotNull
	private String position;

	@NotNull
	private String handler;

	@NotNull
	private String winnername;
	@NotNull
	private Date handletime;

	@NotNull
	private String orderid;

	private byte[] screenshort;
	private byte[] winnerphoto;

	private String syshandler;
	private Date createtime;
	private Date updatetime;
	
	private Long sendprize;
	
	public Long getSendprize() {
		return sendprize;
	}

	public void setSendprize(Long sendprize) {
		this.sendprize = sendprize;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.screenshort
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.screenshort
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public byte[] getScreenshort() {
		return screenshort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.screenshort
	 *
	 * @param screenshort
	 *            the value for
	 *            hui_app_user_winorder_bigprize_handle.screenshort
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setScreenshort(byte[] screenshort) {
		this.screenshort = screenshort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.winnerphoto
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.winnerphoto
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public byte[] getWinnerphoto() {
		return winnerphoto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.winnerphoto
	 *
	 * @param winnerphoto
	 *            the value for
	 *            hui_app_user_winorder_bigprize_handle.winnerphoto
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setWinnerphoto(byte[] winnerphoto) {
		this.winnerphoto = winnerphoto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column hui_app_user_winorder_bigprize_handle.id
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.id
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column hui_app_user_winorder_bigprize_handle.id
	 *
	 * @param id
	 *            the value for hui_app_user_winorder_bigprize_handle.id
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.department
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.department
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.department
	 *
	 * @param department
	 *            the value for hui_app_user_winorder_bigprize_handle.department
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.position
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.position
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.position
	 *
	 * @param position
	 *            the value for hui_app_user_winorder_bigprize_handle.position
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.handler
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.handler
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getHandler() {
		return handler;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.handler
	 *
	 * @param handler
	 *            the value for hui_app_user_winorder_bigprize_handle.handler
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.winnername
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.winnername
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getWinnername() {
		return winnername;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.winnername
	 *
	 * @param winnername
	 *            the value for hui_app_user_winorder_bigprize_handle.winnername
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setWinnername(String winnername) {
		this.winnername = winnername;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.handletime
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.handletime
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public Date getHandletime() {
		return handletime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.handletime
	 *
	 * @param handletime
	 *            the value for hui_app_user_winorder_bigprize_handle.handletime
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setHandletime(Date handletime) {
		this.handletime = handletime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.orderid
	 *
	 * @return the value of hui_app_user_winorder_bigprize_handle.orderid
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * hui_app_user_winorder_bigprize_handle.orderid
	 *
	 * @param orderid
	 *            the value for hui_app_user_winorder_bigprize_handle.orderid
	 *
	 * @mbg.generated Wed Nov 15 14:50:41 CST 2017
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSyshandler() {
		return syshandler;
	}

	public void setSyshandler(String syshandler) {
		this.syshandler = syshandler;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}