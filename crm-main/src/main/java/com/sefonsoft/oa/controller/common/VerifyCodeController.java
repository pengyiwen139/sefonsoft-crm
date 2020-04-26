package com.sefonsoft.oa.controller.common;


import com.sefonsoft.oa.system.utils.VerifyCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author xielf
 */
@Controller
@RequestMapping("captcha")
public class VerifyCodeController extends BaseController {


  @GetMapping
  @ApiOperation("验证码")
  public StreamingResponseBody verifyCode(HttpSession session, HttpServletResponse response) {

    VerifyCode verifyCode = new VerifyCode();
    final BufferedImage image = verifyCode.getImage();
    session.setAttribute(VERIFY_CODE, verifyCode.getText());

    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setContentType("image/jpeg");

    return out -> verifyCode.output(image, out);
  }
}
