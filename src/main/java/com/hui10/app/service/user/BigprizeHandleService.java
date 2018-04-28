package com.hui10.app.service.user;

import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.model.user.BigprizeHandle;

/**
 * @ClassName: BigPrizeHandle.java
 * @Description:
 * @author wengf
 * @date 2017年11月16日 下午2:40:36
 */
public interface BigprizeHandleService {
	
	public int saveHandle(String uid, MultipartFile screenshort,MultipartFile photo, BigprizeHandle handle);


}
