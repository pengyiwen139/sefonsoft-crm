package com.sefonsoft.oa.system.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RedissonConfig
 * @author: Peng YiWen
 * @date: 2020/5/26  11:00
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() {
        Config config = null;
        //1创建配置
        config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.10:6379");
        //2根据config创建出re'di's'son'Client实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}