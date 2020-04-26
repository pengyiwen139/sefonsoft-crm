package com.sefonsoft.oa.system.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/23 21:05
 * @description：当浏览器请求的路径404,或者405，则默认跳转到主页面
 * @modified By：
 */
@Configuration
public class ErrorPageConfig {
    /**
     * SpringBoot2.0以上版本WebServerFactoryCustomizer代替之前版本的EmbeddedWebServerFactoryCustomizerAutoConfiguration
     *
     * @return
     */

    //@Bean必须加上
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
//        //第一种：java7 常规写法
//        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
//            @Override
//            public void customize(ConfigurableWebServerFactory factory) {
//                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//                factory.addErrorPages(errorPage404);
//            }
//        };

        //第二种写法：java8 lambda写法
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            ErrorPage errorPage405 = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/index.html");
            factory.addErrorPages(errorPage404);
            factory.addErrorPages(errorPage405);
        });
    }

}