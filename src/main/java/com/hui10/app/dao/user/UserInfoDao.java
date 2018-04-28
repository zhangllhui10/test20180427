package com.hui10.app.dao.user;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.user.UserInfo;
import com.hui10.app.model.user.UserSimpleInfo;


/**
 * @author zhangll
 *
 */
public interface UserInfoDao {

	public Integer insertUserInfo(@Param("uid") String uid, @Param("phone") String phone, @Param("jointime") Date jointime,
			@Param("nickname") String nickname,@Param("regtype") String regtype,@Param("regip") String regip,@Param("regposition") String regposition,
			@Param("platfrom") String platfrom);
	
	public String queryUserByPhone(@Param("phone") String phone,@Param("status") String status);
	
	public Map<String,String> isExistUserByUid(@Param("uid") String uid);
	
	public UserSimpleInfo queryUserSimpleInfo(@Param("uid") String uid);
	
	public UserInfo queryUserInfoByUid(@Param("uid")String uid);
	
	public int updateUserInfosByUid(UserInfo usinfo);
	
}
