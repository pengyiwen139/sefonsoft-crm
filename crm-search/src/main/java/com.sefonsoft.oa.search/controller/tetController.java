package com.sefonsoft.oa.search.controller;

import com.alibaba.fastjson.JSON;
import com.sefonsoft.oa.search.config.MySearchConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: tetController
 * @author: Peng YiWen
 * @date: 2020/5/3  17:45
 */
@RestController
public class tetController {

    @Resource
    private RestHighLevelClient client;

    @GetMapping("/search/test")
    public void contentLoads() {
        System.out.println(client);
    }


    @GetMapping("/search/insert")
    public Object insert() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
        TestUser user = new TestUser("彭易文","91","男");
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);

        //执行操作
        IndexResponse index = client.index(indexRequest, MySearchConfiguration.COMMON_OPTIONS);
        return index.toString();
    }


    @AllArgsConstructor
    @Data
    class TestUser{
        String username;
        String id;
        String sex;
}








}