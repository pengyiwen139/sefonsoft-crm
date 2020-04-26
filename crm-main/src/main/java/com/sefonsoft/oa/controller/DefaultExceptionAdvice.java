package com.sefonsoft.oa.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;

@ControllerAdvice
public class DefaultExceptionAdvice {
  
  @ExceptionHandler(MsgException.class)
  @ResponseBody
  public Response<?> msgEx(MsgException ex) {
    Response<?> response = new Response<>();
    response.failure(ex.getMessage());
    return response;
  }

}
