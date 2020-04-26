package com.sefonsoft.oa.feign;

import com.sefonsoft.oa.entity.feign.CommonResult;
import com.sefonsoft.oa.entity.feign.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pengYiWen
 * @Description:
 * @date 2020/4/422:26
 */
@Component
@FeignClient(value = "crm-excontract", fallback = fallbackImpl.class)
public interface Order11mainOpenFeignService {

    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @RequestMapping(value = "/payment/timeout")
    String test();

}