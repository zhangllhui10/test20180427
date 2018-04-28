package com.hui10.app.model.user;

import java.util.List;

/**
 * @ClassName: UserSimpleInfo.java
 * @Description:
 * @author zhangll
 * @date 2017年10月25日 下午1:53:55
 */
public class UserSimpleInfo {
	/**
	 * 用户ID
	 */
	private String uid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String portraiturl;

	private List<UserBankCard> cardlist;
	/******* 性别：1男性，2女性，0是未知 ******/
	private Integer sex;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the portraiturl
	 */
	public String getPortraiturl() {
		return portraiturl;
	}

	/**
	 * @param portraiturl
	 *            the portraiturl to set
	 */
	public void setPortraiturl(String portraiturl) {
		this.portraiturl = portraiturl;
	}

	/**
	 * @return the cardlist
	 */
	public List<UserBankCard> getCardlist() {
		return cardlist;
	}

	/**
	 * @param cardlist the cardlist to set
	 */
	public void setCardlist(List<UserBankCard> cardlist) {
		this.cardlist = cardlist;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}
