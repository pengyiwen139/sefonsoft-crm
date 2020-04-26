package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * contract_pay_stage
 *
 * @author
 */
public class ContractPayStage implements Serializable {
  private Integer id;

  /**
   * 合同编号
   */
  private String contractId;

  /**
   * 支付比例
   * 百分整数
   */
  private Integer ratio;

  /**
   * 支付金额
   * （万元）
   */
  private BigDecimal amount;

  /**
   * 回款条件
   */
  private String payCondition;


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

  public Integer getRatio() {
    return ratio;
  }

  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getPayCondition() {
    return payCondition;
  }

  public void setPayCondition(String payCondition) {
    this.payCondition = payCondition;
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
    ContractPayStage other = (ContractPayStage) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
        && (this.getPayCondition() == null ? other.getPayCondition() == null : this.getPayCondition().equals(other.getPayCondition()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    result = prime * result + ((getPayCondition() == null) ? 0 : getPayCondition().hashCode());
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
    sb.append(", ratio=").append(ratio);
    sb.append(", amount=").append(amount);
    sb.append(", payCondition=").append(payCondition);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}