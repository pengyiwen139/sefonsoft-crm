package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 合同产品视图 VO
 *
 */
public class ContractProductVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -65305987558352531L;

  
  /**
   * 产品ID
   */
  @ApiModelProperty("产品ID")
  private Long productId;   
  
  /**
   * 产品名称
   */
  @ApiModelProperty("产品名称")
  private String productName;

  /**
   * 产品描述
   */
  @ApiModelProperty("产品描述")
  private String productDesc;

  /**
   * 产品数量
   */
  @ApiModelProperty("产品数量")
  private Integer count;

  /**
   * 产品单价
   * 单位（万元）
   */
  @ApiModelProperty("产品单价单位（万元）")
  private BigDecimal price;

  /**
   * 税率
   * 百分整数
   */
  @ApiModelProperty("税率， 百分整数")
  private Integer taxRate;

  /**
   * 小计
   * 单位（万元）
   */
  @ApiModelProperty("小计")
  private BigDecimal amount;
  
  
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


  /**
   * @return the productName
   */
  public String getProductName() {
    return productName;
  }


  /**
   * @param productName the productName to set
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
    result = prime * result + ((count == null) ? 0 : count.hashCode());
    result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
    result = prime * result + ((price == null) ? 0 : price.hashCode());
    result = prime * result + ((productId == null) ? 0 : productId.hashCode());
    result = prime * result + ((productName == null) ? 0 : productName.hashCode());
    result = prime * result + ((taxRate == null) ? 0 : taxRate.hashCode());
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
    ContractProductVO other = (ContractProductVO) obj;
    if (amount == null) {
      if (other.amount != null) {
        return false;
      }
    } else if (!amount.equals(other.amount)) {
      return false;
    }
    if (count == null) {
      if (other.count != null) {
        return false;
      }
    } else if (!count.equals(other.count)) {
      return false;
    }
    if (productDesc == null) {
      if (other.productDesc != null) {
        return false;
      }
    } else if (!productDesc.equals(other.productDesc)) {
      return false;
    }
    if (price == null) {
      if (other.price != null) {
        return false;
      }
    } else if (!price.equals(other.price)) {
      return false;
    }
    if (productId == null) {
      if (other.productId != null) {
        return false;
      }
    } else if (!productId.equals(other.productId)) {
      return false;
    }
    if (productName == null) {
      if (other.productName != null) {
        return false;
      }
    } else if (!productName.equals(other.productName)) {
      return false;
    }
    if (taxRate == null) {
      if (other.taxRate != null) {
        return false;
      }
    } else if (!taxRate.equals(other.taxRate)) {
      return false;
    }
    return true;
  }
  
  
  
}
