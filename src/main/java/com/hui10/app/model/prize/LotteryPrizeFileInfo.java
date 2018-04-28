package com.hui10.app.model.prize;

import java.util.Date;

/**
 * @ClassName: PrizefileInfo.java
 * @Description:
 * @author zhangll
 * @date 2017年10月18日 下午3:56:32
 */
public class LotteryPrizeFileInfo {
	
	private String prizefileid;
	private String gamecode;
	private String issuenumber;
	private Date lotterytime;
	/**文件校验码**/
	private String filecheckcode;
	/**文件是否已处理的状态，0-未处理，1-已处理**/
	private String status;
	private Date createtime;
	private Date updatetime;
	/**营销活动文件的序号**/
	private String promotionid;
	/**
	 * @return the prizefileid
	 */
	public String getPrizefileid() {
		return prizefileid;
	}
	/**
	 * @param prizefileid the prizefileid to set
	 */
	public void setPrizefileid(String prizefileid) {
		this.prizefileid = prizefileid;
	}
	
	/**
	 * @return the gamecode
	 */
	public String getGamecode() {
		return gamecode;
	}
	/**
	 * @param gamecode the gamecode to set
	 */
	public void setGamecode(String gamecode) {
		this.gamecode = gamecode;
	}
	/**
	 * @return the issuenumber
	 */
	public String getIssuenumber() {
		return issuenumber;
	}
	/**
	 * @param issuenumber the issuenumber to set
	 */
	public void setIssuenumber(String issuenumber) {
		this.issuenumber = issuenumber;
	}
	/**
	 * @return the lotterytime
	 */
	public Date getLotterytime() {
		return lotterytime;
	}
	/**
	 * @param lotterytime the lotterytime to set
	 */
	public void setLotterytime(Date lotterytime) {
		this.lotterytime = lotterytime;
	}
	/**
	 * @return the filecheckcode
	 */
	public String getFilecheckcode() {
		return filecheckcode;
	}
	/**
	 * @param filecheckcode the filecheckcode to set
	 */
	public void setFilecheckcode(String filecheckcode) {
		this.filecheckcode = filecheckcode;
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
	/**
	 * @return the number
	 */
	public String getPromotionid() {
		return promotionid;
	}
	/**
	 * @param promotionid the number to set
	 */
	public void setPromotionid(String promotionid) {
		this.promotionid = promotionid;
	}
}
