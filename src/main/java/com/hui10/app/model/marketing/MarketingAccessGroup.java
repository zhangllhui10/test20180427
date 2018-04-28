package com.hui10.app.model.marketing;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class MarketingAccessGroup {
    private String groupid;
    @NotNull
    private String gatewayid;//接入通道（途径）ID
    @NotNull
    private String channelid;//接入渠道ID
    @NotBlank
    private String merchantname;
    @NotNull
    private Long money;
    @NotNull
    private Integer betnumber;
    @NotNull
    private String marketingid;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

     

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname == null ? null : merchantname.trim();
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getBetnumber() {
        return betnumber;
    }

    public void setBetnumber(Integer betnumber) {
        this.betnumber = betnumber;
    }

    public String getMarketingid() {
        return marketingid;
    }

    public void setMarketingid(String marketingid) {
        this.marketingid = marketingid == null ? null : marketingid.trim();
    }

	/**
	 * @return the gatewayid
	 */
	public String getGatewayid() {
		return gatewayid;
	}

	/**
	 * @param gatewayid the gatewayid to set
	 */
	public void setGatewayid(String gatewayid) {
		this.gatewayid = gatewayid;
	}

	/**
	 * @return the channelid
	 */
	public String getChannelid() {
		return channelid;
	}

	/**
	 * @param channelid the channelid to set
	 */
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
}