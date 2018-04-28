package com.hui10.app.model.acquirer;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售渠道信息模型
 * 
 * @author huangrui
 */
public class AcquirerInfo implements Serializable {

	private static final long serialVersionUID = -2675768795518012264L;

	private String acquirerid; // 收单机构ID

	private String acquirername; // 收单机构名称

	private String acquirerno; // 收单机构编号

	private String bankaccount; // 结算卡账户

	private String accountname; // 结算卡账户名

	private String secretkey; // 秘钥（审批通过之后分配）

	private Date createtime; // 创建时间

	private String creater; // 创建人

	private Date modifytime; // 修改时间

	private String modifier; // 修改人
	
	private String bankname;                            //开户总行名称
	
	private String bankcode;                            //开户总行编码
	
	private String branchname;                          //开户支行名称
	
	private String branchcode;                          //开户支行编码
	
	private String accounttype;//结算卡账户类型（1 对公账户/2 对私账户）,
	private String financephone;//财务手机号,
	private Float  paycommission;//支付手续费,
	
	private String id;

	public String getAcquirerid() {
		return acquirerid;
	}

	public void setAcquirerid(String acquirerid) {
		this.acquirerid = acquirerid;
	}

	public String getAcquirername() {
		return acquirername;
	}

	public void setAcquirername(String acquirername) {
		this.acquirername = acquirername;
	}

	public String getAcquirerno() {
		return acquirerno;
	}

	public void setAcquirerno(String acquirerno) {
		this.acquirerno = acquirerno;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return the bankname
	 */
	public String getBankname() {
		return bankname;
	}

	/**
	 * @param bankname the bankname to set
	 */
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	/**
	 * @return the bankcode
	 */
	public String getBankcode() {
		return bankcode;
	}

	/**
	 * @param bankcode the bankcode to set
	 */
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	/**
	 * @return the branchname
	 */
	public String getBranchname() {
		return branchname;
	}

	/**
	 * @param branchname the branchname to set
	 */
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	/**
	 * @return the branchcode
	 */
	public String getBranchcode() {
		return branchcode;
	}

	/**
	 * @param branchcode the branchcode to set
	 */
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	/**
	 * @return the accounttype
	 */
	public String getAccounttype() {
		return accounttype;
	}

	/**
	 * @param accounttype the accounttype to set
	 */
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	/**
	 * @return the financephone
	 */
	public String getFinancephone() {
		return financephone;
	}

	/**
	 * @param financephone the financephone to set
	 */
	public void setFinancephone(String financephone) {
		this.financephone = financephone;
	}

	/**
	 * @return the paycommission
	 */
	public Float getPaycommission() {
		return paycommission;
	}

	/**
	 * @param paycommission the paycommission to set
	 */
	public void setPaycommission(Float paycommission) {
		this.paycommission = paycommission;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
