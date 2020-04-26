package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractPayStage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("收款比例信息")
public class ContractPayStageInsertVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3100918450166693885L;
  
  /**
   * 支付比例
   * 百分整数
   */
  @ApiModelProperty("支付比例，百分整数")
  @Min(value = 1, message = "支付比例应为 [1 - 100] 的整数")
  private Integer ratio;

  /**
   * 支付金额
   * （万元）
   */
  @ApiModelProperty("支付金额")
  @Min(value = 0, message = "支付金额错误")
  private BigDecimal amount;

  /**
   * 回款条件
   */
  @ApiModelProperty("回款条件")
  @NotEmpty(message = "回款条件不能为空")
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



  public ContractPayStage toContractPayStage(ContractInfo inf) {
    
    ContractPayStage payStage = new ContractPayStage();
    
    payStage.setContractId(inf.getContractId());
    
    BeanUtils.copyProperties(this, payStage);
    
    return payStage;
  }

}
