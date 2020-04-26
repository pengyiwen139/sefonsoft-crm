package com.sefonsoft.oa.controller.common;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.emun.ResponseMessage;
import com.sefonsoft.oa.system.interceptor.HttpRequestHolder;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.CookieUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

/**
 *
 * @author xielf
 */
public class BaseController{

  public static final String VERIFY_CODE = "VERIFY_CODE";

  public static final String ERROR_NUM = "ERROR_NUM";

  /**
   * get login user info
   *
   * @return
   */
  protected LoginUserDTO getLoginDTO() {

    HttpServletRequest request = HttpRequestHolder.currentServletRequest();
    HttpSession session = request.getSession(false);

    if (ObjTool.isNotNull(request)) {
      String loginUserId = CookieUtil.getLoginUserIdByCookie(request);
      if (ObjTool.isNotNull(loginUserId,session)) {
        Object sessionAttribute = session.getAttribute(USER_INFO + loginUserId);
        if (ObjTool.isNotNull(sessionAttribute) && sessionAttribute instanceof LoginUserDTO) {
          return (LoginUserDTO) sessionAttribute;
        }
      }
    }
    return null;
  }

  /**
   * success no data
   * @return
   */
  protected Response success() {
    return new Response<>().success();
  }

  /**
   * failure no data
   * @return
   */
  protected Response failure() {
    return new Response<>().failure();
  }

  /**
   * failure and message
   * @param message
   * @return
   */
  protected Response failure(String message) {
    return new Response().failure(message);
  }

  /**
   * failure message and data
   * @param message
   * @param data
   * @return
   */
  protected Response failure(String message, Object data) {
    return new Response<>().failure(message, data);
  }

  protected Response failure(ResponseMessage responseMessage) {
    return new Response<>().failure(responseMessage);
  }

  /**
   * failure code and message
   * @param code
   * @param message
   * @return
   */
  protected Response failure(int code, String message) {
    return new Response<>().failure(code, message);
  }

  /**
   * failure validation message
   * @param result
   * @return
   */
  protected Response failure(BindingResult result) {
    return new Response<>().failure(result);
  }

  /**
   * success and data
   * @param data
   * @return
   */
  protected Response success(Object data) {
    return new Response<>().success(data);
  }


  protected Response warning(String message, Object data) {
    return new Response<>().warning(message, data);
  }

}
