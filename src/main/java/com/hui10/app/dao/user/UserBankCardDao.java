package com.hui10.app.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.user.UserBankCard;


/**
 * @ClassName: UserBankcardDao.java
 * @Description:
 * @author wengf
 * @date 2017年10月17日 下午6:12:35
 */
public interface UserBankCardDao {

	UserBankCard queryUserBankcard(UserBankCard userBankcard);

	int insertBankcardInfo(UserBankCard userBankcard);

	int deleteBankcardInfo(@Param("id") String id);
	
	List<UserBankCard> queryBankCards(@Param("uid")String uid);

}
