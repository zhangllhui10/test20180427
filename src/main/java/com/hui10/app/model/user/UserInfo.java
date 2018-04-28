/**
 * 
 */
package com.hui10.app.model.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: UserInfo
 * @Description: 对应数据字典：hui_user_info（用户基本信息表）
 * @author zhangll
 * @date 2016年7月15日 下午6:01:55
 *
 */
public class UserInfo implements Serializable{

    private static final long serialVersionUID = 3390614775500916539L;

    /**
	 * 用户ID
	 */
	private String uid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 身份证
	 */
	private String identity;
	/**
	 * 身份证类型
	 */
	private String idtype;

	private Integer age;

	private Integer sex;
	/**
	 * 注册时间
	 */
	private Date jointime;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 用户总积分
	 */
	private Integer point;
	/**
	 * 状态 0：禁用，1：正常，2：注销
	 */
	private String status;
	/**
	 * 信用分数
	 */
	private Integer creditscore;
	/**
	 * 信用等级
	 */
	private Integer creditgrade;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 头像
	 */
	private String portraiturl;
	/**
	 * 用户所在城市
	 */
	private String city;

	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 扣扣
	 */
	private String qq;
	/**
	 * 学历
	 */
	private String education;
	/**
	 * 爱好
	 */
	private String hobby;
	/**
	 * 头像类型
	 */
	private String imagetype;

	// private UserInviteRecord userInviteRecord;
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getJointime() {
		return jointime;
	}

	public void setJointime(Date jointime) {
		this.jointime = jointime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCreditscore() {
		return creditscore;
	}

	public void setCreditscore(Integer creditscore) {
		this.creditscore = creditscore;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getPortraiturl() {
		return portraiturl;
	}

	public void setPortraiturl(String portraiturl) {
		this.portraiturl = portraiturl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCreditgrade() {
		return creditgrade;
	}

	public void setCreditgrade(int creditgrade) {
		this.creditgrade = creditgrade;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getImagetype() {
		return imagetype;
	}

	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}

	@Override
	public String toString() {
		return "UserInfo [uid=" + uid + ", nickname=" + nickname + ", email=" + email + ", phone=" + phone + ", name=" + name + ", identity=" + identity
				+ ", idtype=" + idtype + ", age=" + age + ", sex=" + sex + ", jointime=" + jointime + ", updatetime=" + updatetime + ", point=" + point
				+ ", status=" + status + ", creditscore=" + creditscore + ", creditgrade=" + creditgrade + ", profession=" + profession + ", portraiturl="
				+ portraiturl + ", city=" + city + ", birthday=" + birthday + ", qq=" + qq + ", education=" + education + ", hobby=" + hobby + ", imagetype="
				+ imagetype + "]";
	}
}
