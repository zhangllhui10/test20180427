package com.hui10.app.service.user;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.model.user.MediumHandle;

/**
 * @ClassName: UserIdCardService.java
 * @Description:
 * @author wengf
 * @date 2017年10月23日 下午5:35:52
 */
public interface MediumHandleService {
	
	public int bindIdCard(String uid, String orderid, MultipartFile faceSide, MultipartFile backSide, String name, String idcardno, String proxyname, String bankcardid)throws IOException;
	
	public int cancelAppOrder(String uid, String orderid);
	
	public int updateHandle(MediumHandle handle);
	
	public MediumHandle getById(String id);


}
