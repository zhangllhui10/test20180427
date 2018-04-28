package com.hui10.app.dao.user;

import com.hui10.app.model.user.BigprizeHandle;

/**
 * @ClassName: BigprizeHandleDao.java
 * @Description:
 * @author wengf
 * @date 2017年11月15日 下午5:20:48
 */
public interface BigprizeHandleDao {
	
	public int saveHandle(BigprizeHandle handle);
	
	public int updateHandle(BigprizeHandle handle);

}
