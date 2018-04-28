package com.hui10.app.service.system.impl;

import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.service.system.SystemService;
import com.hui10.common.cache.CacheManager;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年10月26日 10:47
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private CacheManager cacheManager;

    private int width = 90;// 定义图片的width
    private int height = 25;// 定义图片的height
    private int codeCount = 4;// 定义图片上显示验证码的个数
    private int xx = 15;
    private int fontHeight = 20;
    private int codeY = 18;
    private String codeKey = "code";
    char[] codeSequence = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z' ,'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };


    @Override
    public void generateCaptcha(HttpServletRequest req, HttpServletResponse resp, String identity) throws IOException {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(new Color(250,250,210));
        gd.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Arial", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length-1)]);
            if (Constants.test_code) {
				code = String.valueOf(i + 1);
			}
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(200);
            green = random.nextInt(200);
            blue = random.nextInt(200);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        // 将PC端id与验证码关联存入缓存
        saveIdentityCode(identity, randomCode.toString());
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute(codeKey, randomCode.toString());
        // 禁止图像缓存。
        // Set to expire far in the past.
        resp.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        resp.setHeader("Pragma", "no-cache");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    private void saveIdentityCode(String identity, String code) {
        cacheManager.put(Constants.APP_IDENTITY + identity, code, Constants.PCCODE_EXPIRED);
    }

    @Override
    public void checkCaptcha(String identity, String code) {
        if (StringUtils.isBlank(identity)) {
            throw new CommonException(ICommon.IDENTITY_EMPTY,PropertiesUtils.get(ICommon.IDENTITY_EMPTY));
        }
        if (!cacheManager.exist(Constants.APP_IDENTITY + identity)) {
            throw new CommonException(ICommon.CAPTCHA_ERROR, PropertiesUtils.get(ICommon.CAPTCHA_ERROR));
        }
        String captcha = (String) cacheManager.get(Constants.APP_IDENTITY + identity);
        if (StringUtils.isBlank(captcha) || !code.equalsIgnoreCase(captcha)) {
            throw new CommonException(ICommon.CAPTCHA_ERROR, PropertiesUtils.get(ICommon.CAPTCHA_ERROR));
        } else {
            cacheManager.delete(Constants.APP_IDENTITY + identity);
        }
    }

}
