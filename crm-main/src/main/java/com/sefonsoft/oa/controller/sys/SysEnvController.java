package com.sefonsoft.oa.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sefonsoft.oa.entity.sys.SysEnvEntity;
import com.sefonsoft.oa.service.sys.SysEnvService;
import com.sefonsoft.oa.system.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@RestController
@Api(tags = "系统参数")
@RequestMapping("/sysEnv")
@SuppressWarnings("unchecked")
public class SysEnvController {

  @Autowired
  SysEnvService sysEnvService;
  
  @ApiModelProperty("查询所有参数")
  @GetMapping
  public Response<List<SysEnvEntity>> listEnv() {
    List<SysEnvEntity> result = sysEnvService.getAll();
    return new Response<List<SysEnvEntity>>().success(result);
  }
  
  @ApiModelProperty("更新参数")
  @PostMapping("/{name}")
  public Response<String> setEnv(
      @PathVariable(name = "name")  String name, 
      @RequestParam(name = "value") String value) {
    sysEnvService.setEnv(name, value);
    return new Response<String>().success("更新成功");
  }
  
  
}
