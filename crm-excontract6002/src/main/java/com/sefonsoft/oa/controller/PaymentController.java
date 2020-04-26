package com.sefonsoft.oa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pengYiWen
 * @Description: controller
 * @date 2020/3/2717:53
 */
@RestController
@Slf4j
@RefreshScope
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${reportDayLastHours}")
    private String reportDayLastHours;

//    @GetMapping(value = "/payment/get/{id}")
//    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
//    {
//            return new CommonResult(200,"查询成功,serverPort:  "+serverPort,payment);
//    }

    @RequestMapping(value = "/payment/timeout")
    public String test()
    {
//        int k = 1/0;
        return serverPort+"-"+reportDayLastHours;
    }



}