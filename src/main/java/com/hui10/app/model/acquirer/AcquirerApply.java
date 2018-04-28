package com.hui10.app.model.acquirer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * 销售渠道入网申请信息模型
 * 
 * @author huangrui
 */
public class AcquirerApply implements Serializable {

	private static final long serialVersionUID = -2675768795518012264L;

	private String applyid; // 销售渠道入网申请ID

	@NotNull(message = "收单机构名称不能为空")
	private String acquirername; // 收单机构名称

	@NotNull(message = "收单机构编号不能为空")
	private String acquirerno; // 收单机构编号

	@NotNull(message = "协议名称不能为空")
	private String protocolname; // 协议名称

	@NotNull(message = "协议编号不能为空")
	private String protocolno; // 协议编号

	@NotNull(message = "彩票发行方不能为空")
	private String issuer; // 彩票发行方

	@NotNull(message = "彩票提供方不能为空")
	private String provider; // 彩票提供方

	@NotNull(message = "省份编号不能为空")
	private String provinceid; // 省份编号

	@NotNull(message = "省份名称不能为空")
	private String provincename; // 省份名称

	@NotNull(message = "三方协议扫描件不能为空")
	private String protocolphoto; // 三方协议扫描件

	@NotNull(message = "业务入网申请表扫描件不能为空")
	private String formphoto; // 业务入网申请表扫描件

	@NotNull(message = "协议生效时间不能为空")
	private Date starttime; // 协议生效时间

	@NotNull(message = "协议截止时间不能为空")
	private Date endtime; // 协议截止时间

	@NotNull(message = "结算卡账户不能为空")
	private String bankaccount; // 结算卡账户

	@NotNull(message = "结算卡账户名不能为空")
	private String accountname; // 结算卡账户名

	private String salestatus; // 销售状态（0 待启用/1 启用/2 停用/3 终止）

	private String auditstatus; // 审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）

	private String auditreason; // 审核拒绝理由

	private Date createtime; // 创建时间

	private String creater; // 创建人

	private Date modifytime; // 修改时间

	private String modifier; // 修改人

	private int merchantcount; // 商户个数

	@NotNull(message = "销售彩种不能为空")
	private List<AcquirerLottery> lotterylist; // 销售彩票列表

	private List<Map<String, String>> formphotolist; // 销售渠道申请表扫描件列表

	private List<Map<String, String>> protocolphotolist; // 销售渠道三方协议扫描件列表
	
	@NotNull(message = "总行名称不能为空")
	private String bankname;                            //开户总行名称
	
	@NotNull(message = "总行编码不能为空")
	private String bankcode;                            //开户总行编码
	
	@NotNull(message = "支行名称不能为空")
	private String branchname;                          //开户支行名称
	
	@NotNull(message = "支行编码不能为空")
	private String branchcode;                          //开户支行编码
	
	private String channelmercno;                       //收单机构分配汇拾商户号
	private String accounttype;//结算卡账户类型（1 对公账户/2 对私账户）,
	private String financephone;//财务手机号,
	private Float  paycommission;//支付手续费,


	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid;
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

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	public String getProtocolphoto() {
		return protocolphoto;
	}

	public void setProtocolphoto(String protocolphoto) {
		this.protocolphoto = protocolphoto;
	}

	public String getFormphoto() {
		return formphoto;
	}

	public void setFormphoto(String formphoto) {
		this.formphoto = formphoto;
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

	public String getSalestatus() {
		return salestatus;
	}

	public void setSalestatus(String salestatus) {
		this.salestatus = salestatus;
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

	public int getMerchantcount() {
		return merchantcount;
	}

	public void setMerchantcount(int merchantcount) {
		this.merchantcount = merchantcount;
	}

	public List<AcquirerLottery> getLotterylist() {
		return lotterylist;
	}

	public void setLotterylist(List<AcquirerLottery> lotterylist) {
		this.lotterylist = lotterylist;
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
	 * @return the channelmercno
	 */
	public String getChannelmercno() {
		return channelmercno;
	}

	/**
	 * @param channelmercno the channelmercno to set
	 */
	public void setChannelmercno(String channelmercno) {
		this.channelmercno = channelmercno;
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
}
