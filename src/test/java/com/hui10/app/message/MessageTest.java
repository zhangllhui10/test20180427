package com.hui10.app.message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.hui10.app.TestBase;
import com.hui10.app.model.enums.MessageTypeEnum;
import com.hui10.app.service.message.MessageService;

/**
 * @ClassName: UserInfoTest.java
 * @Description:
 * @author zhangll
 * @date 2017年10月25日 下午2:40:08
 */
public class MessageTest extends TestBase {
	
	@Autowired
	private MessageService messageService;

	@Test
	public void test_001() {
		messageService.addPushMessage(null, null, MessageTypeEnum.BET_NOTIFY.getCode());
		//messageService.addPushMessage("2017070614570131250117933", "1", MessageTypeEnum.WIN_NOTIFY.getCode());
		//messageService.addPushMessage("2017070614570131250117933", "1", MessageTypeEnum.SEND_SUCCESS_NOTIFY.getCode());
		//messageService.addPushMessage("2017022112515770143918301", "1", MessageTypeEnum.SEND_FAIL_NOTIFY.getCode());
	}
}
