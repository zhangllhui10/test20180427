package com.hui10.app.encrypt;

import com.hui10.app.common.encrypt.AesUtils;

/**
 * @ClassName: EncryptTeset.java
 * @Description:
 * @author wengf
 * @date 2017年11月7日 下午4:14:16
 */
public class EncryptTeset {
	
	public static void main(String[] args) throws Exception {
		String ent = AesUtils.encrypt("wenwen", "testkey");
		System.out.println(ent);
		String dt = AesUtils.decrypt(ent,"testkey");
		System.out.println(dt);
	}

}
