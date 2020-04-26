package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * contract_sales_info
 *
 * @author
 */
public class ContractSalesInfo implements Serializable {

  private Integer id;

  /**
   * 合同编号
   */
  private String contractId;

  /**
   * 销售PK
   */
  private Long employeePk;

  /**
   * 销售ID
   */
  private String employeeId;

  /**
   * 销售名称
   */
  private String employeeName;

  /**
   * 比例
   * 百分整数
   */
  private Integer ratio;

  /**
   * 金额
   * 单位（万元）
   */
  private BigDecimal amount;

  /**
   * 操作员ID
   */
  private String operator;

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

  public Long getEmployeePk() {
    return employeePk;
  }

  public void setEmployeePk(Long employeePk) {
    this.employeePk = employeePk;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
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

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
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
    ContractSalesInfo other = (ContractSalesInfo) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getEmployeePk() == null ? other.getEmployeePk() == null : this.getEmployeePk().equals(other.getEmployeePk()))
        && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
        && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
        && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
        && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getEmployeePk() == null) ? 0 : getEmployeePk().hashCode());
    result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
    result = prime * result + ((getEmployeeName() == null) ? 0 : getEmployeeName().hashCode());
    result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
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
    sb.append(", employeePk=").append(employeePk);
    sb.append(", employeeId=").append(employeeId);
    sb.append(", employeeName=").append(employeeName);
    sb.append(", ratio=").append(ratio);
    sb.append(", amount=").append(amount);
    sb.append(", operator=").append(operator);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}