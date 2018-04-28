package com.hui10.app.controller.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.service.system.SystemService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年10月26日 10:41
 */
@Controller
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 生成图片验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/v*/system/captcha", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response, String identity) throws ServletException, IOException {
        if (StringUtils.isBlank(identity)) {
            throw new CommonException(ICommon.IDENTITY_EMPTY,PropertiesUtils.get(ICommon.IDENTITY_EMPTY));
        }
        systemService.generateCaptcha(request, response, identity);
    }
}
