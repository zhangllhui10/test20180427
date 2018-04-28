package com.hui10.app.model.merchant;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: MerchantApply.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午3:05:11
 */
public class MerchantApply {

	private String applyid;                                //渠道商户入网申请ID
	
	private String merchantid;                             //商户ID
	
	private String merchantno;                             //商户编号
	
	@NotNull(message = "商户名称不能为空")
	private String merchantname;                           //商户名称
	
	@NotNull(message = "商户名称缩写不能为空")
	private String merchantshort;                          //商户简称

	@NotNull(message = "省份编号不能为空")
	private String provinceid;                             //省份编号
	
	@NotNull(message = "省份名称不能为空")
	private String provincename;                           //省份名称
	
	@NotNull(message = "城市编号不能为空")
	private String cityid;                                 //城市编号
	
	@NotNull(message = "城市名称不能为空")
	private String cityname;                               //城市名称
	
	@NotNull(message = "商户所属行业编号不能为空")
	private String industryno;                             //商户所属行业编号
	
	@NotNull(message = "商户所属行业名称不能为空")
	private String industryname;                           //商户所属行业名称
	
	@NotNull(message = "联系人不能为空")
	private String contactperson;                          //联系人
	
	@NotNull(message = "联系电话不能为空")
	private String contactnumber;                          //联系电话
	
	@NotNull(message = "商户法人不能为空")
	private String legalperson;                            //商户法人
	
	@NotNull(message = "法人联系电话不能为空")
	private String legalnumber;                            //法人联系电话
	
	@NotNull(message = "法人联系地址不能为空")
	private String legaladdress;                           //法人联系地址
	
	@NotNull(message = "渠道商户企业营业执照扫描件不能为空")
	private String legalphoto;                             //渠道商户企业营业执照扫描件
	
	@NotNull(message = "商户地址不能为空")
	private String address;                                //商户地址
	
	@NotNull(message = "结算卡账户不能为空")
	private String bankaccount;                            //结算卡账户
	
	@NotNull(message = "结算卡账户名不能为空")
	private String accountname;                            //结算卡账户名
	
	@NotNull(message = "所属销售渠道编号不能为空")
	private String acquirerno;                             //销售渠道编号
	
	private String acquirername;                           //销售渠道名称
	
	@NotNull(message = "贷记卡品牌费率不能为空")
	private Float cardrate;                                //贷记卡品牌费率
	
	@NotNull(message = "支付手续费不能为空")
	private Float paycommission;                           //支付手续费
	
	@NotNull(message = "渠道商户申请表扫描件不能为空")
	private String formphoto;                              //渠道商户申请表扫描件
	
	@NotNull(message = "协议名称不能为空")
	private String protocolname;                           //协议名称
	
	@NotNull(message = "协议编号不能为空")
	private String protocolno;                             //协议编号
	
	@NotNull(message = "协议生效时间不能为空")
	private Date starttime;                                //协议生效时间
	
	@NotNull(message = "协议截止时间不能为空")
	private Date endtime;                                  //协议截止时间
	
	@NotNull(message = "渠道商户四方协议扫描件不能为空")
	private String protocolphoto;                          //渠道商户四方协议扫描件
	
	private String auditstatus;                            //入网审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）
	
	private String auditreason;                            //业务审核拒绝理由
	
	private String salestatus;                             //销售状态（0 待启用/1 启用/2 停用/3 终止）

	private String lotterystatus;                          //彩票审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）
	
	private String lotteryreason;                          //彩票审核拒绝理由
	
	private Date createtime;                               //创建时间
	
	private String creater;                                //创建人
	
	private Date modifytime;                               //修改时间
	
	private String modifier;                               //修改人
	
	@NotNull(message = "销售彩种不能为空")
	private List<MerchantLottery> lotterylist;             //渠道商户销售彩票列表
	
	private String stationno;                              //投注站编号
	
	private List<Map<String, String>> legalphotolist;                  //渠道商户企业营业执照扫描件列表
	 
	private List<Map<String, String>> formphotolist;                   //渠道商户申请表扫描件列表
	
	private List<Map<String, String>> protocolphotolist;               //渠道商户四方协议扫描件列表
	
	@NotNull(message = "商户号不能为空")
	private String paymerchantno;  
	
	@NotNull(message = "总行名称不能为空")
	private String bankname;                               //开户总行名称
	
	@NotNull(message = "总行编码不能为空")
	private String bankcode;                               //开户总行编码
	
	@NotNull(message = "支行名称不能为空")
	private String branchname;                             //开户支行名称
	
	@NotNull(message = "支行编码不能为空")
	private String branchcode;                             //开户支行编码
	
	private String accounttype;//结算卡账户类型（1 对公账户/2 对私账户）
	private String financephone;//财务手机号
	
	private String status;                                 //状态（0 待启用/1 启用）

	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}

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

	public Float getCardrate() {
		return cardrate;
	}

	public void setCardrate(Float cardrate) {
		this.cardrate = cardrate;
	}

	public Float getPaycommission() {
		return paycommission;
	}

	public void setPaycommission(Float paycommission) {
		this.paycommission = paycommission;
	}

	public String getFormphoto() {
		return formphoto;
	}

	public void setFormphoto(String formphoto) {
		this.formphoto = formphoto;
	}

	public String getProtocolname() {
		return protocolname;
	}

	public void setProtocolname(String protocolname) {
		this.protocolname = protocolname;
	}

	public String getProtocolno() {
		return protocolno;
	}

	public void setProtocolno(String protocolno) {
		this.protocolno = protocolno;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getProtocolphoto() {
		return protocolphoto;
	}

	public void setProtocolphoto(String protocolphoto) {
		this.protocolphoto = protocolphoto;
	}

	public String getAuditstatus() {
		return auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	public String getAuditreason() {
		return auditreason;
	}

	public void setAuditreason(String auditreason) {
		this.auditreason = auditreason;
	}

	public String getSalestatus() {
		return salestatus;
	}

	public void setSalestatus(String salestatus) {
		this.salestatus = salestatus;
	}

	public String getLotterystatus() {
		return lotterystatus;
	}

	public void setLotterystatus(String lotterystatus) {
		this.lotterystatus = lotterystatus;
	}

	public String getLotteryreason() {
		return lotteryreason;
	}

	public void setLotteryreason(String lotteryreason) {
		this.lotteryreason = lotteryreason;
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

	public List<MerchantLottery> getLotterylist() {
		return lotterylist;
	}

	public void setLotterylist(List<MerchantLottery> lotterylist) {
		this.lotterylist = lotterylist;
	}

	public String getAcquirername() {
		return acquirername;
	}

	public void setAcquirername(String acquirername) {
		this.acquirername = acquirername;
	}

	public String getStationno() {
		return stationno;
	}

	public void setStationno(String stationno) {
		this.stationno = stationno;
	}
	/**
	 * @return the legalphotolist
	 */
	public List<Map<String, String>> getLegalphotolist() {
		return legalphotolist;
	}

	/**
	 * @param legalphotolist the legalphotolist to set
	 */
	public void setLegalphotolist(List<Map<String, String>> legalphotolist) {
		this.legalphotolist = legalphotolist;
	}

	/**
	 * @return the formphotolist
	 */
	public List<Map<String, String>> getFormphotolist() {
		return formphotolist;
	}

	/**
	 * @param formphotolist the formphotolist to set
	 */
	public void setFormphotolist(List<Map<String, String>> formphotolist) {
		this.formphotolist = formphotolist;
	}

	/**
	 * @return the protocolphotolist
	 */
	public List<Map<String, String>> getProtocolphotolist() {
		return protocolphotolist;
	}

	/**
	 * @param protocolphotolist the protocolphotolist to set
	 */
	public void setProtocolphotolist(List<Map<String, String>> protocolphotolist) {
		this.protocolphotolist = protocolphotolist;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
