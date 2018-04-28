package com.hui10.app.model.lottery;

import java.util.Date;

/**
 * @ClassName: LotteryRegion.java
 * @Description:彩票销售区域表
 * @author zhangll
 * @date 2018年1月2日 下午2:33:30
 */
public class LotteryRegion {
	private String id;
	private String lotterycode;
	private String provinceid;
	private String provincename;
	private String lotteryname;
	private Date createtime;
	private String creater;
	private Date modifytime;
	private String modifier;
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
	 * @return the provincename
	 */
	public String getProvincename() {
		return provincename;
	}
	/**
	 * @param provincename the provincename to set
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	/**
	 * @return the lotteryname
	 */
	public String getLotteryname() {
		return lotteryname;
	}
	/**
	 * @param lotteryname the lotteryname to set
	 */
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
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
	 * @return the creater
	 */
	public String getCreater() {
		return creater;
	}
	/**
	 * @param creater the creater to set
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	/**
	 * @return the modifytime
	 */
	public Date getModifytime() {
		return modifytime;
	}
	/**
	 * @param modifytime the modifytime to set
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	

}
