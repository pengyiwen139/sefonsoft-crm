package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("收款比例信息")
public class ContractPayStageVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3100918450166693885L;
  
  /**
   * 支付比例
   * 百分整数
   */
  @ApiModelProperty("支付比例，百分整数")
  private Integer ratio;

  /**
   * 支付金额
   * （万元）
   */
  @ApiModelProperty("支付金额")
  private BigDecimal amount;

  /**
   * 回款条件
   */
  @ApiModelProperty("回款条件")
  private String payCondition;

  

  /**
   * @return the ratio
   */
  public Integer getRatio() {
    return ratio;
  }



  /**
   * @param ratio the ratio to set
   */
  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }



  /**
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }



  /**
   * @param amount the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }



  /**
   * @return the payCondition
   */
  public String getPayCondition() {
    return payCondition;
  }



  /**
   * @param payCondition the payCondition to set
   */
  public void setPayCondition(String payCondition) {
    this.payCondition = payCondition;
  }



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
    result = prime * result + ((payCondition == null) ? 0 : payCondition.hashCode());
    result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
    return result;
  }



  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ContractPayStageVO other = (ContractPayStageVO) obj;
    if (amount == null) {
      if (other.amount != null) {
        return false;
      }
    } else if (!amount.equals(other.amount)) {
      return false;
    }
    if (payCondition == null) {
      if (other.payCondition != null) {
        return false;
      }
    } else if (!payCondition.equals(other.payCondition)) {
      return false;
    }
    if (ratio == null) {
      if (other.ratio != null) {
        return false;
      }
    } else if (!ratio.equals(other.ratio)) {
      return false;
    }
    return true;
  }
  
  

}
