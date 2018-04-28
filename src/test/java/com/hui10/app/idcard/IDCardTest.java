package com.hui10.app.idcard;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.dao.user.MediumHandleDao;
import com.hui10.app.service.user.UserOrderService;

/**
 * @ClassName: IDCardTest.java
 * @Description:
 * @author wengf
 * @date 2017年11月9日 上午10:37:57
 */
public class IDCardTest extends TestBase{
	
	@Autowired
	private MediumHandleDao idcardDao;
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Test
	public void test00001_getPic() throws Exception{
//		String uid = "2017101718263829896919896";
//		String orderid = "201710240245418809730000007041";
//		List<UserIdcard> idcards = idcardDao.queryCards(uid, orderid);
//		for (UserIdcard userIdcard : idcards) {
//			byte[] buf = userIdcard.getFaceside();
//			ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
////			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\test.jpg"));
//			byte[] buff = new byte[1024];
//			while (inputStream.read(buff)>0) {
//				fileOutputStream.write(buff);
//				
//			}
//			fileOutputStream.close();
//			inputStream.close();
//		}
	}
	@Test
	public void test00002_phone(){
		String phone = userOrderService.queryPhoneByOrderid("201710250247359709710000008481");
		System.out.println(phone);
	}

}
