package com.sefonsoft.oa.domain.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同性质
 */
@ApiModel("合同性质")
public enum ContractType {
  
  /**
   * 渠道
   */
  @ApiModelProperty("渠道")
  渠道,
  
  
  /**
   * 直签 
   */
  @ApiModelProperty("直签")
  直签;
}
