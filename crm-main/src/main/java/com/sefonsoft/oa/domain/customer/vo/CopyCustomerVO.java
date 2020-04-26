package com.sefonsoft.oa.domain.customer.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("复制客户")
public class CopyCustomerVO implements Serializable {
  
  private static final long serialVersionUID = -6222691047543414439L;
  
  
  @ApiModelProperty("需要复制的客户ID")
  @NotEmpty
  private List<String> customerIds;


  /**
   * 需要复制的客户ID
   */
  public List<String> getCustomerIds() {
    return customerIds;
  }


  /**
   * 需要复制的客户ID
   */
  public void setCustomerIds(List<String> customerIds) {
    this.customerIds = customerIds;
  }


}
