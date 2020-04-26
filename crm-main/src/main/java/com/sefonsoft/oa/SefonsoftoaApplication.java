package com.sefonsoft.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author ：Aron
 * @version : 1.0
 * @description： SpringBoot启动类
 * @date ：2019/10/28
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.sefonsoft.oa.dao.*")
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.sefonsoft.oa.feign")
public class SefonsoftoaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SefonsoftoaApplication.class)
				.run(args);
		System.out.println("  .   ____          _            __ _ _\n" +
				" /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\\n" +
				"( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\\n" +
				" \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )\n" +
				"  '  |____| .__|_| |_|_| |_\\__, | / / / /\n" +
				" =========|_|==============|___/=/_/_/_/\n" +
				"启动成功");
	}

}