package com.hui10.app.model.acquirer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 销售渠道销售彩票信息模型
 * 
 * @author huangrui
 */
public class AcquirerLottery implements Serializable {

	private static final long serialVersionUID = 1377529083592414822L;

	private String id; // 唯一ID

	private String applyid; // 销售渠道入网申请ID

	private String acquirerno; // 销售渠道编号

	@NotNull(message = "彩种名称不能为空")
	private String lotteryname; // 彩种名称

	@NotNull(message = "彩票类型编号不能为空")
	private String lotterycode; // 彩票类型编号

	@NotNull(message = "银联分润比例不能为空")
	private Float unionpayprop; // 银联分润比例

	@NotNull(message = "销售渠道分润比例不能为空")
	private Float acquirerprop; // 销售渠道分润比例

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}

	public String getAcquirerno() {
		return acquirerno;
	}

	public void setAcquirerno(String acquirerno) {
		this.acquirerno = acquirerno;
	}

	public String getLotteryname() {
		return lotteryname;
	}

	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}

	public String getLotterycode() {
		return lotterycode;
	}

	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}

	public Float getUnionpayprop() {
		return unionpayprop;
	}

	public void setUnionpayprop(Float unionpayprop) {
		this.unionpayprop = unionpayprop;
	}

	public Float getAcquirerprop() {
		return acquirerprop;
	}

	public void setAcquirerprop(Float acquirerprop) {
		this.acquirerprop = acquirerprop;
	}

}
