package com.sefonsoft.oa.domain.common;

import io.swagger.annotations.ApiModelProperty;

public enum YNFlag {
  @ApiModelProperty("是")
  yes,
  @ApiModelProperty("否")
  no;
  
  public boolean isYes() {
    return this == yes;
  }
  
  public boolean isNo() {
    return this == no;
  }
}
