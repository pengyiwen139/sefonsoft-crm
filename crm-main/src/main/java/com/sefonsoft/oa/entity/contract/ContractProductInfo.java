package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * contract_product_info
 *
 * @author
 */
public class ContractProductInfo implements Serializable {
  private Integer id;

  /**
   * 合同编号
   */
  private String contractId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品描述
   */
  private String productDesc;

  /**
   * 产品数量
   */
  private Integer count;

  /**
   * 产品单价
   * 单位（万元）
   */
  private BigDecimal price;

  /**
   * 税率
   * 百分整数
   */
  private Integer taxRate;

  /**
   * 小计
   * 单位（万元）
   */
  private BigDecimal amount;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String prductDesc) {
    this.productDesc = prductDesc;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(Integer taxRate) {
    this.taxRate = taxRate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    ContractProductInfo other = (ContractProductInfo) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
        && (this.getProductDesc() == null ? other.getProductDesc() == null : this.getProductDesc().equals(other.getProductDesc()))
        && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
        && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
        && (this.getTaxRate() == null ? other.getTaxRate() == null : this.getTaxRate().equals(other.getTaxRate()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
    result = prime * result + ((getProductDesc() == null) ? 0 : getProductDesc().hashCode());
    result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
    result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
    result = prime * result + ((getTaxRate() == null) ? 0 : getTaxRate().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", contractId=").append(contractId);
    sb.append(", productId=").append(productId);
    sb.append(", prductDesc=").append(productDesc);
    sb.append(", count=").append(count);
    sb.append(", price=").append(price);
    sb.append(", taxRate=").append(taxRate);
    sb.append(", amount=").append(amount);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }

}