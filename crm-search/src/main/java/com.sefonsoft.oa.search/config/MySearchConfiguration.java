package com.sefonsoft.oa.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: MySearchConfiguration
 * @author: Peng YiWen
 * @date: 2020/5/2  16:36
 */
@SpringBootConfiguration
public class MySearchConfiguration {

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }


    @Bean
    public RestHighLevelClient getConnection() {
        RestClientBuilder builder = null;
        builder = RestClient.builder(new HttpHost("192.168.56.10", 9200, "http"));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http")));
        return restHighLevelClient;
    }



























}
