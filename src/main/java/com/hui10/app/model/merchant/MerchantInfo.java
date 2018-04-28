package com.hui10.app.model.merchant;

import java.io.Serializable;
import java.util.Date;

/**
 * 渠道商户信息模型
 * @author huangrui
 */
public class MerchantInfo implements Serializable{
	
	private static final long serialVersionUID = -2675768795518012264L;

	private String merchantid;                             //商户ID
	
	private String merchantno;                             //商户编号
	
	private String merchantname;                           //商户名称
	
	private String merchantshort;                          //商户简称

	private String provinceid;                             //省份编号
	
	private String provincename;                           //省份名称
	
	private String cityid;                                 //城市编号
	
	private String cityname;                               //城市名称
	
	private String industryno;                             //商户所属行业编号
	
	private String industryname;                           //商户所属行业名称
	
	private String contactperson;                          //联系人
	
	private String contactnumber;                          //联系电话
	
	private String legalperson;                            //商户法人
	
	private String legalnumber;                            //法人联系电话
	
	private String legaladdress;                           //法人联系地址
	
	private String legalphoto;                             //渠道商户企业营业执照扫描件
	
	private String address;                                //商户地址
	
	private String bankaccount;                            //结算卡账户
	
	private String accountname;                            //结算卡账户名
	
	private String acquirerno;                             //销售渠道编号
	
	private String stationno;                              //投注站编号
	
	private String status;                                 //状态（0 待启用/1 启用）所有审批通过后启用  
		
	private Date createtime;                               //创建时间
	
	private String creater;                                //创建人
	
	private Date modifytime;                               //修改时间
	
	private String modifier;                               //修改人
	
	private int terminalcount;                             //POS终端总数
	private String bankname;                               //开户总行名称
	
	private String bankcode;                               //开户总行编码
	
	private String branchname;                             //开户支行名称
	
	private String branchcode;                             //开户支行编码
	private String paymerchantno;                          //机构录入商户号
	
	private String accounttype;//结算卡账户类型（1 对公账户/2 对私账户）
	private String financephone;//财务手机号

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getMerchantno() {
		return merchantno;
	}

	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getMerchantshort() {
		return merchantshort;
	}

	public void setMerchantshort(String merchantshort) {
		this.merchantshort = merchantshort;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getIndustryno() {
		return industryno;
	}

	public void setIndustryno(String industryno) {
		this.industryno = industryno;
	}

	public String getIndustryname() {
		return industryname;
	}

	public void setIndustryname(String industryname) {
		this.industryname = industryname;
	}

	public String getContactperson() {
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}

	public String getLegalnumber() {
		return legalnumber;
	}

	public void setLegalnumber(String legalnumber) {
		this.legalnumber = legalnumber;
	}

	public String getLegaladdress() {
		return legaladdress;
	}

	public void setLegaladdress(String legaladdress) {
		this.legaladdress = legaladdress;
	}

	public String getLegalphoto() {
		return legalphoto;
	}

	public void setLegalphoto(String legalphoto) {
		this.legalphoto = legalphoto;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getAcquirerno() {
		return acquirerno;
	}

	public void setAcquirerno(String acquirerno) {
		this.acquirerno = acquirerno;
	}

	public String getStationno() {
		return stationno;
	}

	public void setStationno(String stationno) {
		this.stationno = stationno;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTerminalcount() {
		return terminalcount;
	}

	public void setTerminalcount(int terminalcount) {
		this.terminalcount = terminalcount;
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
	 * @return the paymerchantno
	 */
	public String getPaymerchantno() {
		return paymerchantno;
	}

	/**
	 * @param paymerchantno the paymerchantno to set
	 */
	public void setPaymerchantno(String paymerchantno) {
		this.paymerchantno = paymerchantno;
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
}
