package com.hui10.app.model.merchant;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 商户销售彩票信息模型
 * @author huangrui
 */
public class MerchantLottery implements Serializable{

	private static final long serialVersionUID = 1377529083592414822L;

	private String id;                       //唯一ID
	
	private String applyid;                  //渠道商户申请入网ID
	
	private String merchantid;               //商户ID
	
	@NotNull(message = "彩种名称不能为空")
	private String lotteryname;              //彩种名称
	
	@NotNull(message = "彩票类型编号不能为空")
	private String lotterycode;              //彩票类型编号
		
	@NotNull(message = "商户分润比例不能为空")
	private Float merchantprop;              //商户分润比例

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

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
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

	public Float getMerchantprop() {
		return merchantprop;
	}

	public void setMerchantprop(Float merchantprop) {
		this.merchantprop = merchantprop;
	}	
}
