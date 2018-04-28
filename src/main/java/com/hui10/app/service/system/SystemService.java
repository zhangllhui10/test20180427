package com.hui10.app.service.system;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年10月26日 10:46
 */
public interface SystemService {

    /**
     * 生成图片验证码
     * @param req
     * @param resp
     * @param identity
     */
    void generateCaptcha(HttpServletRequest req, HttpServletResponse resp, String identity) throws IOException;

    /**
     * 验证图片验证码
     * @param identity
     * @param code
     */
    public void checkCaptcha(String identity, String code);
}
