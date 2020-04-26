package com.sefonsoft.oa.domain.statistic.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author xielf
 */
public class CountVo {

  @ApiModelProperty("客户")
  private int customerCount;
  @ApiModelProperty("商机")
  private int bizOpportsCount;
  @ApiModelProperty("项目")
  private int projectCount;
  @ApiModelProperty("合同")
  private int contractCount;
  @ApiModelProperty("金额")
  private BigDecimal sumMoney;


  public BigDecimal getSumMoney() {
    return sumMoney;
  }

  public void setSumMoney(BigDecimal sumMoney) {
    this.sumMoney = sumMoney;
  }

  public int getCustomerCount() {
    return customerCount;
  }

  public void setCustomerCount(int customerCount) {
    this.customerCount = customerCount;
  }

  public int getBizOpportsCount() {
    return bizOpportsCount;
  }

  public void setBizOpportsCount(int bizOpportsCount) {
    this.bizOpportsCount = bizOpportsCount;
  }

  public int getProjectCount() {
    return projectCount;
  }

  public void setProjectCount(int projectCount) {
    this.projectCount = projectCount;
  }

  public int getContractCount() {
    return contractCount;
  }

  public void setContractCount(int contractCount) {
    this.contractCount = contractCount;
  }
}
