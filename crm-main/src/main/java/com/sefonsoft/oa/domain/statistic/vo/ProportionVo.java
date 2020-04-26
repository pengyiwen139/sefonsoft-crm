package com.sefonsoft.oa.domain.statistic.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author xielf
 */
public class ProportionVo {

  @ApiModelProperty("客户比例")
  private BigDecimal customerProportion = new BigDecimal("0.0");

  @ApiModelProperty("商机比例")
  private BigDecimal bizOpportsProportion = new BigDecimal("0.0");

  @ApiModelProperty("项目比例")
  private BigDecimal projectProportion = new BigDecimal("0.0");

  @ApiModelProperty("合同比例")
  private BigDecimal contractProportion = new BigDecimal("0.0");


  public BigDecimal getCustomerProportion() {
    return customerProportion;
  }

  public void setCustomerProportion(BigDecimal customerProportion) {
    this.customerProportion = customerProportion;
  }

  public BigDecimal getBizOpportsProportion() {
    return bizOpportsProportion;
  }

  public void setBizOpportsProportion(BigDecimal bizOpportsProportion) {
    this.bizOpportsProportion = bizOpportsProportion;
  }

  public BigDecimal getProjectProportion() {
    return projectProportion;
  }

  public void setProjectProportion(BigDecimal projectProportion) {
    this.projectProportion = projectProportion;
  }

  public BigDecimal getContractProportion() {
    return contractProportion;
  }

  public void setContractProportion(BigDecimal contractProportion) {
    this.contractProportion = contractProportion;
  }
}
