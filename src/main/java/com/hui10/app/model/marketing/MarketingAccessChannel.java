package com.hui10.app.model.marketing;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: MarketingAccessChannel.java
 * @Description:
 * @author zhangll
 * @date 2018年3月9日 下午4:20:41
 */
public class MarketingAccessChannel {
	private String channelid;
	@NotBlank
	private String gatewayid;
	/**弃用**/
	private String code;
	@NotBlank
	private String channelname;
	private String channelcode;
	private String status;
	private Date createtime;
	private Date updatetime;
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the channelname
	 */
	public String getChannelname() {
		return channelname;
	}
	/**
	 * @param channelname the channelname to set
	 */
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	/**
	 * @return the channelcode
	 */
	public String getChannelcode() {
		return channelcode;
	}
	/**
	 * @param channelcode the channelcode to set
	 */
	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
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
	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
