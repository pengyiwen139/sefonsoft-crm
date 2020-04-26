package com.sefonsoft.oa.domain.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author xielf
 */
@ApiModel
public class CustomerSaleDto implements Serializable {


  @ApiModelProperty(value="客户编号")
  private String customerId;

  @ApiModelProperty("变更员工ID")
  private String[] employeeId;

  @ApiModelProperty("旧员工ID")
  private String[] oldEmployeeId;

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String[] getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String[] employeeId) {
    this.employeeId = employeeId;
  }

  public String[] getOldEmployeeId() {
    return oldEmployeeId;
  }

  public void setOldEmployeeId(String[] oldEmployeeId) {
    this.oldEmployeeId = oldEmployeeId;
  }

  public static void main(String[] args) throws JsonProcessingException {
    System.out.println(new ObjectMapper().writeValueAsString(new CustomerSaleDto()));
  }
}
