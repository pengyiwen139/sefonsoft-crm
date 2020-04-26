package com.sefonsoft.oa.domain.project.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 导入导出 产品 VO
 */
public class ProductExcelVO implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -8507243881861935377L;
  
  private Integer id;

  private Integer saleCount = 0;
  
  private BigDecimal amount = BigDecimal.ZERO;
  
  private BigDecimal saleAmount = BigDecimal.ZERO;

  public Integer getSaleCount() {
    return saleCount;
  }

  public void setSaleCount(Integer saleCount) {
    this.saleCount = saleCount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getSaleAmount() {
    return saleAmount;
  }

  public void setSaleAmount(BigDecimal saleAmount) {
    this.saleAmount = saleAmount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
