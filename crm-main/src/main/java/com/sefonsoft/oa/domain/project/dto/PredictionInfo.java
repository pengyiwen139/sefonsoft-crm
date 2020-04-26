package com.sefonsoft.oa.domain.project.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 按概率，预估成功数量和金额
 */
@ApiModel("预测信息")
public class PredictionInfo implements Serializable {

  private static final long serialVersionUID = 3165191678151012499L;
  
  /**
   * 总数量
   */
  @ApiModelProperty("数量")
  private Integer count;
  
  /**
   * 总金额
   */
  @ApiModelProperty("金额")
  private BigDecimal money;
  
  /**
   * 概率（维度）
   */
  @ApiModelProperty("概率（维度）")
  private Integer sucess;

  /**
   * 月份（维度）
   */
  @ApiModelProperty("月份（维度）")
  private String month;
  
  /**
   * @return the count
   */
  public Integer getCount() {
    return count;
  }

  /**
   * @param count the count to set
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * @return the money
   */
  public BigDecimal getMoney() {
    return money;
  }

  /**
   * @param money the money to set
   */
  public void setMoney(BigDecimal money) {
    this.money = money;
  }

  /**
   * @return the sucess
   */
  public Integer getSucess() {
    return sucess;
  }

  /**
   * @param sucess the sucess to set
   */
  public void setSucess(Integer sucess) {
    this.sucess = sucess;
  }

  /**
   * @return the month
   */
  public String getMonth() {
    return month;
  }

  /**
   * @param month the month to set
   */
  public void setMonth(String month) {
    this.month = month;
  }

}
