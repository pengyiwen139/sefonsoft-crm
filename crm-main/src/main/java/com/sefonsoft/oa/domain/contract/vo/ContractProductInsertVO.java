package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractProductInfo;

import io.swagger.annotations.ApiModelProperty;

public class ContractProductInsertVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -65305987558352531L;

  
  /**
   * 产品ID
   */
  @ApiModelProperty("产品ID")
  @NotNull(message = "产品不能为空")
  private Long productId;

  /**
   * 产品描述
   */
  @ApiModelProperty("产品描述")
  @NotNull(message = "产品描述不能为空")
  private String productDesc;

  /**
   * 产品数量
   */
  @ApiModelProperty("产品数量")
  @NotNull(message = "产品数量不能为空")
  @Min(value = 1, message = "产品数量错误")
  private Integer count;

  /**
   * 产品单价
   * 单位（万元）
   */
  @ApiModelProperty("产品单价单位（万元）")
  @NotNull(message = "产品单价不能为空")
  @Min(value = 0, message = "产品单价错误")
  private BigDecimal price;

  /**
   * 税率
   * 百分整数
   */
  @ApiModelProperty("税率， 百分整数")
  @NotNull(message = "税率不能为空")
  @Min(value = 0, message = "税率错误，应为 [1 - 100] 的整数")
  @Max(value = 100, message = "税率错误，应为 [1 - 100] 的整数")
  private Integer taxRate;

  /**
   * 小计
   * 单位（万元）
   */
  @ApiModelProperty("小计")
  private BigDecimal amount;
  
  
  public ContractProductInfo toContractProductInfo(ContractInfo inf) {
    ContractProductInfo info = new ContractProductInfo();
    info.setContractId(inf.getContractId());
    BeanUtils.copyProperties(this, info);
    return info;
  }


  /**
   * @return the productId
   */
  public Long getProductId() {
    return productId;
  }


  /**
   * @param productId the productId to set
   */
  public void setProductId(Long productId) {
    this.productId = productId;
  }


  /**
   * @return the prductDesc
   */
  public String getProductDesc() {
    return productDesc;
  }


  /**
   * @param prductDesc the prductDesc to set
   */
  public void setProductDesc(String prductDesc) {
    this.productDesc = prductDesc;
  }


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
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }


  /**
   * @param price the price to set
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  /**
   * @return the taxRate
   */
  public Integer getTaxRate() {
    return taxRate;
  }


  /**
   * @param taxRate the taxRate to set
   */
  public void setTaxRate(Integer taxRate) {
    this.taxRate = taxRate;
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
  
  
}
