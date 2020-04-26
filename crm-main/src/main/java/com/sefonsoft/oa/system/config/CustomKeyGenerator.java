package com.sefonsoft.oa.system.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CustomKeyGenerator implements KeyGenerator {

  @Override
  public Object generate(Object target, Method method, Object... params) {
    StringBuilder sb = new StringBuilder();
    for (Object param : params) {
      sb.append(param.toString());
    }
    return sb.toString();
  }
}