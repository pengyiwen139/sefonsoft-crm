package com.sefonsoft.oa.system.config;

import com.sefonsoft.oa.system.interceptor.WebConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置自定义的参数解析器
 */
@Component
class LoginWebConfigurer extends WebMvcConfigurerAdapter {

	Logger logger= LoggerFactory.getLogger(WebConfigurer.class);


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}

	/**
	 * 配置用户登录信息的自定义参数解析器
	 * @return
	 */
	@Bean
	public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
		return new CurrentUserMethodArgumentResolver();
	}

}