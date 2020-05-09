package com.sefonsoft.oa.search.controller;

import com.alibaba.fastjson.JSON;
import com.sefonsoft.oa.search.config.MySearchConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

import static com.sefonsoft.oa.search.config.MySearchConfiguration.COMMON_OPTIONS;

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
        IndexResponse index = client.index(indexRequest, COMMON_OPTIONS);
        return index.toString();
    }


    @AllArgsConstructor
    @Data
    class TestUser{
        String username;
        String id;
        String sex;
}


    /**
     * 复杂搜索查询
     * @return
     * @throws IOException
     */
    @GetMapping("/search/insert/dificult")
    public Object query() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");
        //指定DSL，检索条件
        //sourceBuilder 封装的条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //按照年龄的值分布进行聚合
        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        TermsAggregationBuilder size = AggregationBuilders.terms("ageAgg").field("age").size(10);
        searchSourceBuilder.aggregation(size);
        //计算平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        searchSourceBuilder.aggregation(balanceAvg);

        System.out.println("检索条件："+searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);

        //执行搜索
        SearchResponse searchResponse = client.search(searchRequest, COMMON_OPTIONS);

        //分析结果
        System.out.println(searchResponse.toString());
        return searchResponse.toString();
    }






}