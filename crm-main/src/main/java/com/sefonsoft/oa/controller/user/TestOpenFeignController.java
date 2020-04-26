package com.sefonsoft.oa.controller.user;

import com.sefonsoft.oa.feign.Order11mainOpenFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestOpenFeignController {

    @Resource
    private Order11mainOpenFeignService feinService;

    @RequestMapping("/openFeign/test")
    public String testOpenFeign() {
        return feinService.test();
    }

}