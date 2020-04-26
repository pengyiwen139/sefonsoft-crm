package com.sefonsoft.oa.feign;

import com.sefonsoft.oa.entity.feign.CommonResult;
import com.sefonsoft.oa.entity.feign.Payment;
import org.springframework.stereotype.Component;

/**
 * @ClassName: fallbackImpl
 * @author: Peng YiWen
 * @date: 2020/4/26  10:17
 */
@Component
public class fallbackImpl implements Order11mainOpenFeignService {
    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return null;
    }

    @Override
    public String test() {
        return "GlobalTimeOutFeign";
    }
}
