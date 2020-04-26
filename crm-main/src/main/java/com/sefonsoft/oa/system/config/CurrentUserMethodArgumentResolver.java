package com.sefonsoft.oa.system.config;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.exception.UnauthorizedException;
import com.sefonsoft.oa.system.utils.CookieUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

/**
 * 自定义的参数解析器
 * 增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 * @author pengyiwen
 * @since 2019-11-26
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    Logger logger = LoggerFactory.getLogger(CurrentUserMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUserDTO.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);
        LoginUserDTO loginUserDTO = null;

        if (ObjTool.isNotNull(request)) {
            String loginUserId = CookieUtil.getLoginUserIdByCookie(request);
            if (ObjTool.isNotNull(loginUserId,session)) {
                Object sessionAttribute = session.getAttribute(USER_INFO + loginUserId);
                if (ObjTool.isNotNull(sessionAttribute) && sessionAttribute instanceof LoginUserDTO) {
                    loginUserDTO = (LoginUserDTO) sessionAttribute;
                    if (ObjTool.isNotNull(loginUserDTO)) {
                        return loginUserDTO;
                    }
                }
            }
        }

        if (null == loginUserDTO) {
            throw new UnauthorizedException("获取用户信息失败");
        }
        return loginUserDTO;
    }

}