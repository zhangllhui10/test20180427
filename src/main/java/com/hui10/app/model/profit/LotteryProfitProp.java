package com.hui10.app.model.profit;

import java.util.Date;

/**
 * @ClassName: LotteryProfitProp.java
 * @Description:
 * @author zhangll
 * @date 2017年10月27日 下午4:33:00
 */
public class LotteryProfitProp {
	
	private String id;
	private String lotterycode;
	private String provinceid;
	/**
	 * 总分润比例
	 */
	private Float totalprop;
	/**
	 * 彩票销售状态：0 待启用/1 启用/2 停用/3 终止
	 */
	private String status;
	private Date  createtime;
	private Date updatetime;
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
	/**
	 * @return the lotterycode
	 */
	public String getLotterycode() {
		return lotterycode;
	}
	/**
	 * @param lotterycode the lotterycode to set
	 */
	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}
	/**
	 * @return the provinceid
	 */
	public String getProvinceid() {
		return provinceid;
	}
	/**
	 * @param provinceid the provinceid to set
	 */
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * @return the totalprop
	 */
	public Float getTotalprop() {
		return totalprop;
	}
	/**
	 * @param totalprop the totalprop to set
	 */
	public void setTotalprop(Float totalprop) {
		this.totalprop = totalprop;
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
