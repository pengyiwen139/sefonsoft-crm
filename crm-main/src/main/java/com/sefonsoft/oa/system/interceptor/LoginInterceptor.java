package com.sefonsoft.oa.system.interceptor;

import com.sefonsoft.oa.system.utils.CookieUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/22 16:30
 * @description：登录状态拦截器
 * @modified By：
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    public static ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();

    /**
     * 这个方法是在访问接口之前执行的，我们只需要在这里写验证登录状态的业务逻辑，就可以在用户调用指定接口之前验证登录状态了
     * 基本逻辑是如果没有session或者session中没有相关用户的信息，就报401的错误
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession(false);
//        if(session==null){
//            response.setStatus(401);
//            return false;
//        }else{
//            String userId = CookieUtil.getLoginUserIdByCookie(request);
//            String myid = session.getId();
//
//            Object attribute = session.getAttribute(USER_INFO + userId);
//            if (!ObjTool.isNotNull(attribute)) {
//                response.setStatus(401);
//                return false;
//            }
//
//            if (!myid.equals(userSessionMap.get(userId))) {
//              session.invalidate();
//              response.setStatus(401);
//              return false;
//            }
//        }
//        HttpRequestHolder.setHttpRequestHolder(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HttpRequestHolder.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}