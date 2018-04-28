package com.hui10.app.model.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 城市实体
 * @author fanzy
 * @time 2017年3月27日上午10:20:35
 */
public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5981647261033225414L;
	
	private String provinceid;
	private String cityid;
	private String districtid;
	private String provincename;
	private String cityname;
	private String districtname;
	private String type;
	private int sort;
	private String status;
	private Date createtime;
	private Date updatetime;
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
	 * @return the cityid
	 */
	public String getCityid() {
		return cityid;
	}
	/**
	 * @param cityid the cityid to set
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	/**
	 * @return the districtid
	 */
	public String getDistrictid() {
		return districtid;
	}
	/**
	 * @param districtid the districtid to set
	 */
	public void setDistrictid(String districtid) {
		this.districtid = districtid;
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
	 * @return the cityname
	 */
	public String getCityname() {
		return cityname;
	}
	/**
	 * @param cityname the cityname to set
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	/**
	 * @return the districtname
	 */
	public String getDistrictname() {
		return districtname;
	}
	/**
	 * @param districtname the districtname to set
	 */
	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
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