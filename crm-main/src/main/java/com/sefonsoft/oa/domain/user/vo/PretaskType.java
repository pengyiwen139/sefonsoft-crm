package com.sefonsoft.oa.domain.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("前置任务类型")
public enum PretaskType {
  
  /**
   * 首次登录确认用户名&邮箱
   */
  @ApiModelProperty("首次登录确认用户名&邮箱")
  CHECK_PROFILE;

}
