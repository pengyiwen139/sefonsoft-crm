package com.sefonsoft.oa.system.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/26 11:25
 * @description：获取cookie中的信息工具类
 * @modified By：
 */
public class CookieUtil {

    /**
     * 根据HttpServletRequest获取登录后的userId(从cookie中获取)
     * @param request
     * @return
     */
    public static String getLoginUserIdByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userId = "";
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if ("userId".equals(name)) {
                userId = cookie.getValue()+"";
            }
        }
        return userId;
    }

}