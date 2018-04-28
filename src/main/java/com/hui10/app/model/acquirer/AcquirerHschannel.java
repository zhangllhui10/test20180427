package com.hui10.app.model.acquirer;
/**
 * @ClassName: AcquirerHschannel.java
 * @Description:汇拾对应销售渠道的商户号记录表
 * @author zhangll
 * @date 2017年11月15日 下午2:04:37
 */
public class AcquirerHschannel {
	
	private Integer id;
	private String acquirerno;
	private String acquirername;
	private String provinceid;
	private String provincename;
	private String channelmercid;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the acquirerno
	 */
	public String getAcquirerno() {
		return acquirerno;
	}
	/**
	 * @param acquirerno the acquirerno to set
	 */
	public void setAcquirerno(String acquirerno) {
		this.acquirerno = acquirerno;
	}
	/**
	 * @return the acquirername
	 */
	public String getAcquirername() {
		return acquirername;
	}
	/**
	 * @param acquirername the acquirername to set
	 */
	public void setAcquirername(String acquirername) {
		this.acquirername = acquirername;
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
	 * @return the channelmercid
	 */
	public String getChannelmercid() {
		return channelmercid;
	}
	/**
	 * @param channelmercid the channelmercid to set
	 */
	public void setChannelmercid(String channelmercid) {
		this.channelmercid = channelmercid;
	}
}
