package com.hui10.app.service.acquirer;

import com.hui10.app.model.acquirer.AcquirerInfo;

/**
 * @ClassName: AcquirerInfoService.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午2:52:10
 */
public interface AcquirerInfoService {
	
	public void createAcquirerApply(String authorization, String paramsBody);
	
	public String loginAcquire(String acquirerno,String acquirername);
	
	public AcquirerInfo checkTokenAcquire(String token);
	
	public void logout(String token);
	
	public String login(String acquirerno,String acquirername);
	
	

}
