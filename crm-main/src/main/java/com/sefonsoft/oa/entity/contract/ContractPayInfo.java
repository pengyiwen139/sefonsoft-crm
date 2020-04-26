package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * contract_pay_info
 *
 * @author
 */
public class ContractPayInfo implements Serializable {
  private Integer id;

  /**
   * 合同编号
   */
  private String contractId;

  /**
   * 支付金额
   * （万元）
   */
  private BigDecimal amount;

  /**
   * 回款时间
   */
  private Date payDate;

  private Date createTime;

  private String creator;

  private String creatorName;

  /**
   * 操作员工工号
   */
  private String operator;

  private String operatorName;

  private Date lastTime;

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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Date getLastTime() {
    return lastTime;
  }

  public void setLastTime(Date lastTime) {
    this.lastTime = lastTime;
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
    ContractPayInfo other = (ContractPayInfo) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
        && (this.getPayDate() == null ? other.getPayDate() == null : this.getPayDate().equals(other.getPayDate()))
        && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
        && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
        && (this.getCreatorName() == null ? other.getCreatorName() == null : this.getCreatorName().equals(other.getCreatorName()))
        && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
        && (this.getOperatorName() == null ? other.getOperatorName() == null : this.getOperatorName().equals(other.getOperatorName()))
        && (this.getLastTime() == null ? other.getLastTime() == null : this.getLastTime().equals(other.getLastTime()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    result = prime * result + ((getPayDate() == null) ? 0 : getPayDate().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
    result = prime * result + ((getCreatorName() == null) ? 0 : getCreatorName().hashCode());
    result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
    result = prime * result + ((getOperatorName() == null) ? 0 : getOperatorName().hashCode());
    result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
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
    sb.append(", amount=").append(amount);
    sb.append(", payDate=").append(payDate);
    sb.append(", createTime=").append(createTime);
    sb.append(", creator=").append(creator);
    sb.append(", creatorName=").append(creatorName);
    sb.append(", operator=").append(operator);
    sb.append(", operatorName=").append(operatorName);
    sb.append(", lastTime=").append(lastTime);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}