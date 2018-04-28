package com.hui10.app.controller.message;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.message.MessageInfo;
import com.hui10.app.service.message.MessageService;
import com.poslot.common.model.Result;

/**
 * @ClassName: MessageController.java
 * @Description:消息查询
 * @author huangrui
 * @date 2018年4月8日 20:01:21
 */
@Controller
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 查询消息列表
     * @param lastid 最后一条消息ID
     * @param pagesize 每页条数
     * @return
     */
    @RequestMapping(value = "/v*/message/querylist", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<List<MessageInfo>> queryMessageList(@RequestParam String token, @RequestParam Integer pagesize, String lastid) {
    	String uid = this.checkCUserToken(token);
        Result<List<MessageInfo>> result = new Result<List<MessageInfo>>();
        result.setResult(messageService.queryMessageList(uid, lastid, pagesize));
        return result;
    }
}
