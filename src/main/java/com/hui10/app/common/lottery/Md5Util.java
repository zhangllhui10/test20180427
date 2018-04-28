package com.hui10.app.common.lottery;

import org.apache.commons.codec.digest.DigestUtils;

import com.hui10.common.utils.MD5Util;

/**
 * @ClassName: Md5Util.java
 * @Description:
 * @author yangcb
 * @date 2017年1月9日 下午6:24:25
 */
public class Md5Util extends MD5Util{

	
	
	public static  String getSignature(String prefix,String content,String suffix){
		StringBuffer sb=new StringBuffer(128);
		sb.append(prefix);
		sb.append(content);
		sb.append(suffix);
		
		return DigestUtils.md5Hex(sb.toString());		
	}
	
	
	
	
	
	
	
}
