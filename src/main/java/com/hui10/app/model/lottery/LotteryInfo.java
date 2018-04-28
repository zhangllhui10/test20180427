package com.hui10.app.model.lottery;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class LotteryInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.lotterycode
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
	@NotBlank(message="彩票编号不能为空")
	@Length(max=64,min=1,message="彩票编号长度不能超过64位")
    private String lotterycode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.lotteryname
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
	@NotBlank(message="彩票名称不能为空")
    private String lotteryname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.lotteryicon
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
	
    private String lotteryicon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.price
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    @NotNull(message="单价不能为空")
    private Long price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.createtime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.creater
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    private String creater;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.modifytime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    private Date modifytime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hui_app_lottery_info.modifier
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    private String modifier;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.lotterycode
     *
     * @return the value of hui_app_lottery_info.lotterycode
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    @NotNull(message="销售区域不能为空")
    private String provinceids;
    private String operator;
    List<LotteryRegion> regionlist;
    
    public String getLotterycode() {
        return lotterycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.lotterycode
     *
     * @param lotterycode the value for hui_app_lottery_info.lotterycode
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setLotterycode(String lotterycode) {
        this.lotterycode = lotterycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.lotteryname
     *
     * @return the value of hui_app_lottery_info.lotteryname
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public String getLotteryname() {
        return lotteryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.lotteryname
     *
     * @param lotteryname the value for hui_app_lottery_info.lotteryname
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setLotteryname(String lotteryname) {
        this.lotteryname = lotteryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.lotteryicon
     *
     * @return the value of hui_app_lottery_info.lotteryicon
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public String getLotteryicon() {
        return lotteryicon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.lotteryicon
     *
     * @param lotteryicon the value for hui_app_lottery_info.lotteryicon
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setLotteryicon(String lotteryicon) {
        this.lotteryicon = lotteryicon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.price
     *
     * @return the value of hui_app_lottery_info.price
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public Long getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.price
     *
     * @param price the value for hui_app_lottery_info.price
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.createtime
     *
     * @return the value of hui_app_lottery_info.createtime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.createtime
     *
     * @param createtime the value for hui_app_lottery_info.createtime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.creater
     *
     * @return the value of hui_app_lottery_info.creater
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.creater
     *
     * @param creater the value for hui_app_lottery_info.creater
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.modifytime
     *
     * @return the value of hui_app_lottery_info.modifytime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.modifytime
     *
     * @param modifytime the value for hui_app_lottery_info.modifytime
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hui_app_lottery_info.modifier
     *
     * @return the value of hui_app_lottery_info.modifier
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hui_app_lottery_info.modifier
     *
     * @param modifier the value for hui_app_lottery_info.modifier
     *
     * @mbg.generated Thu Oct 26 15:13:25 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

	/**
	 * @return the provinceids
	 */
	public String getProvinceids() {
		return provinceids;
	}

	/**
	 * @param provinceids the provinceids to set
	 */
	public void setProvinceids(String provinceids) {
		this.provinceids = provinceids;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the regionlist
	 */
	public List<LotteryRegion> getRegionlist() {
		return regionlist;
	}

	/**
	 * @param regionlist the regionlist to set
	 */
	public void setRegionlist(List<LotteryRegion> regionlist) {
		this.regionlist = regionlist;
	}
}