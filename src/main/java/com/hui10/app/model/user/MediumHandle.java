package com.hui10.app.model.user;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class MediumHandle {
	
	@NotNull
    private String id;

    private String uid;

    private String name;
    @NotNull
    private String orderid;
    @NotNull
    private String status;

    private Date createtime;

    private Date updatetime;
    @NotNull
    private String department;
    @NotNull
    private String position;
    @NotNull
    private String handler;

    private String syshandler;

    private String bankno;

    private String bankname;
    
   private byte[] faceside;

   private byte[] backside;

   private byte[] screenshort;
   private String idcardno;
   
   private Long sendprize;
	
	public Long getSendprize() {
		return sendprize;
	}

	public void setSendprize(Long sendprize) {
		this.sendprize = sendprize;
	}

   public byte[] getFaceside() {
       return faceside;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column hui_app_user_winorder_medium_handle.faceside
    *
    * @param faceside the value for hui_app_user_winorder_medium_handle.faceside
    *
    * @mbg.generated Wed Nov 15 14:50:41 CST 2017
    */
   public void setFaceside(byte[] faceside) {
       this.faceside = faceside;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column hui_app_user_winorder_medium_handle.backside
    *
    * @return the value of hui_app_user_winorder_medium_handle.backside
    *
    * @mbg.generated Wed Nov 15 14:50:41 CST 2017
    */
   public byte[] getBackside() {
       return backside;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column hui_app_user_winorder_medium_handle.backside
    *
    * @param backside the value for hui_app_user_winorder_medium_handle.backside
    *
    * @mbg.generated Wed Nov 15 14:50:41 CST 2017
    */
   public void setBackside(byte[] backside) {
       this.backside = backside;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column hui_app_user_winorder_medium_handle.screenshort
    *
    * @return the value of hui_app_user_winorder_medium_handle.screenshort
    *
    * @mbg.generated Wed Nov 15 14:50:41 CST 2017
    */
   public byte[] getScreenshort() {
       return screenshort;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column hui_app_user_winorder_medium_handle.screenshort
    *
    * @param screenshort the value for hui_app_user_winorder_medium_handle.screenshort
    *
    * @mbg.generated Wed Nov 15 14:50:41 CST 2017
    */
   public void setScreenshort(byte[] screenshort) {
       this.screenshort = screenshort;
   }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.id
     *
     * @return the value of hui_app_user_winorder_medium_handle.id
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.id
     *
     * @param id the value for hui_app_user_winorder_medium_handle.id
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.uid
     *
     * @return the value of hui_app_user_winorder_medium_handle.uid
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.uid
     *
     * @param uid the value for hui_app_user_winorder_medium_handle.uid
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.name
     *
     * @return the value of hui_app_user_winorder_medium_handle.name
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.name
     *
     * @param name the value for hui_app_user_winorder_medium_handle.name
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.orderid
     *
     * @return the value of hui_app_user_winorder_medium_handle.orderid
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.orderid
     *
     * @param orderid the value for hui_app_user_winorder_medium_handle.orderid
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.status
     *
     * @return the value of hui_app_user_winorder_medium_handle.status
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.status
     *
     * @param status the value for hui_app_user_winorder_medium_handle.status
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.createtime
     *
     * @return the value of hui_app_user_winorder_medium_handle.createtime
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.createtime
     *
     * @param createtime the value for hui_app_user_winorder_medium_handle.createtime
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.updatetime
     *
     * @return the value of hui_app_user_winorder_medium_handle.updatetime
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.updatetime
     *
     * @param updatetime the value for hui_app_user_winorder_medium_handle.updatetime
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.department
     *
     * @return the value of hui_app_user_winorder_medium_handle.department
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getDepartment() {
        return department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.department
     *
     * @param department the value for hui_app_user_winorder_medium_handle.department
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.position
     *
     * @return the value of hui_app_user_winorder_medium_handle.position
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.position
     *
     * @param position the value for hui_app_user_winorder_medium_handle.position
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.handler
     *
     * @return the value of hui_app_user_winorder_medium_handle.handler
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getHandler() {
        return handler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.handler
     *
     * @param handler the value for hui_app_user_winorder_medium_handle.handler
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setHandler(String handler) {
        this.handler = handler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.syshandler
     *
     * @return the value of hui_app_user_winorder_medium_handle.syshandler
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getSyshandler() {
        return syshandler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.syshandler
     *
     * @param syshandler the value for hui_app_user_winorder_medium_handle.syshandler
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setSyshandler(String syshandler) {
        this.syshandler = syshandler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.bankno
     *
     * @return the value of hui_app_user_winorder_medium_handle.bankno
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getBankno() {
        return bankno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.bankno
     *
     * @param bankno the value for hui_app_user_winorder_medium_handle.bankno
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_user_winorder_medium_handle.bankname
     *
     * @return the value of hui_app_user_winorder_medium_handle.bankname
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public String getBankname() {
        return bankname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_user_winorder_medium_handle.bankname
     *
     * @param bankname the value for hui_app_user_winorder_medium_handle.bankname
     *
     * @mbg.generated Wed Nov 15 14:50:41 CST 2017
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
    
    
}