package com.sefonsoft.oa.system.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/22 16:28
 * @description：登录状态配置拦截路径，具体拦截配置在loginInterceptor类中
 * @modified By：
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 这个方法是用来配置静态资源的，比如html，js，css，等等
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//         addPathPatternstterns("/**") 表示拦截所有的请求，
//         excludePathPatterns("/login", "/register") 表示除了登录与注册之外，因为登录注册不需要登录也可以访问

       registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/login",
                "/manifest.json","/service-worker.js","/static/**","/index.html","/sms/**",
                "/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/**","/captcha"

       ,"/doc.html");
    }
}