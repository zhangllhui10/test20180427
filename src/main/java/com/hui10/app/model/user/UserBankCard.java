package com.hui10.app.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @ClassName: UserBankCard.java
 * @Description:
 * @author wengf
 * @date 2017年10月17日 下午2:51:16
 */
public class UserBankCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9195067341930592794L;

	private String id;
	private String uid;
    @NotNull(message = "银行卡号不可为空")
    @Range(message = "银行卡格式不正确")
    @Length(min = 16, max = 19, message = "银行卡格式不正确")
	private String cardno;
	private String cardname;
	private String cardholder;
	private String reservephone;
	private Date createtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public String getCardholder() {
		return cardholder;
	}

	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}

	public String getReservephone() {
		return reservephone;
	}

	public void setReservephone(String reservephone) {
		this.reservephone = reservephone;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
