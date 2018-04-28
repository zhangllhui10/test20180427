package com.hui10.pc.dao.merchant;

import java.util.List;

import com.hui10.app.model.merchant.MerchantInfo;

/**
 * @ClassName: MerchantPCDao.java
 * @Description:
 * @author zhangll
 * @date 2018年4月17日 下午7:36:47
 */
public interface MerchantPCDao {
	
	 /**
	  *  根据联系电话查找商户信息
	  * @param phone
	  * @return
	  * @user zhangll
	  * @date 2018年4月17日 下午7:44:02
	  */
	List<MerchantInfo> getMerchantInfoList(MerchantInfo info);
	
	
	

}
