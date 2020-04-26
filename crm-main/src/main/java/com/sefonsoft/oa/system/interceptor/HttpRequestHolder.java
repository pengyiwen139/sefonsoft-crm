package com.sefonsoft.oa.system.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xielf
 */
public final class HttpRequestHolder {

  private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();

  static void setHttpRequestHolder(HttpServletRequest request){
    local.set(request);
  }
  static void remove(){
    local.remove();
  }
  public static HttpServletRequest currentServletRequest(){
    return local.get();
  }

}
