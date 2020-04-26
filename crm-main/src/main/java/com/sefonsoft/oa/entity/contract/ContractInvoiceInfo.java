package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * contract_invoice_info
 *
 * @author
 */
public class ContractInvoiceInfo implements Serializable {
  private Integer id;

  /**
   * 合同编号
   */
  private String contractId;

  /**
   * 开票日期
   */
  private Date invoiceDate;

  /**
   * 票据单号
   */
  private String invoiceNo;

  /**
   * 快递单号
   */
  private String expressNo;

  /**
   * 开票金额
   */
  private BigDecimal amount;

  /**
   * 开票员工
   */
  private String invoiceEmployee;

  /**
   * 开票员工姓名
   */
  private String invoiceEmployeeName;

  /**
   * 操作员
   */
  private String operator;

  /**
   * 操作员姓名
   */
  private String operatorName;

  private Date lastTime;

  private Date createTime;

  private String creator;

  private String creatorName;

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

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public String getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(String invoiceNo) {
    this.invoiceNo = invoiceNo;
  }

  public String getExpressNo() {
    return expressNo;
  }

  public void setExpressNo(String expressNo) {
    this.expressNo = expressNo;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getInvoiceEmployee() {
    return invoiceEmployee;
  }

  public void setInvoiceEmployee(String invoiceEmployee) {
    this.invoiceEmployee = invoiceEmployee;
  }

  public String getInvoiceEmployeeName() {
    return invoiceEmployeeName;
  }

  public void setInvoiceEmployeeName(String invoiceEmployeeName) {
    this.invoiceEmployeeName = invoiceEmployeeName;
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
    ContractInvoiceInfo other = (ContractInvoiceInfo) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getInvoiceDate() == null ? other.getInvoiceDate() == null : this.getInvoiceDate().equals(other.getInvoiceDate()))
        && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
        && (this.getExpressNo() == null ? other.getExpressNo() == null : this.getExpressNo().equals(other.getExpressNo()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
        && (this.getInvoiceEmployee() == null ? other.getInvoiceEmployee() == null : this.getInvoiceEmployee().equals(other.getInvoiceEmployee()))
        && (this.getInvoiceEmployeeName() == null ? other.getInvoiceEmployeeName() == null : this.getInvoiceEmployeeName().equals(other.getInvoiceEmployeeName()))
        && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
        && (this.getOperatorName() == null ? other.getOperatorName() == null : this.getOperatorName().equals(other.getOperatorName()))
        && (this.getLastTime() == null ? other.getLastTime() == null : this.getLastTime().equals(other.getLastTime()))
        && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
        && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
        && (this.getCreatorName() == null ? other.getCreatorName() == null : this.getCreatorName().equals(other.getCreatorName()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getInvoiceDate() == null) ? 0 : getInvoiceDate().hashCode());
    result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
    result = prime * result + ((getExpressNo() == null) ? 0 : getExpressNo().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    result = prime * result + ((getInvoiceEmployee() == null) ? 0 : getInvoiceEmployee().hashCode());
    result = prime * result + ((getInvoiceEmployeeName() == null) ? 0 : getInvoiceEmployeeName().hashCode());
    result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
    result = prime * result + ((getOperatorName() == null) ? 0 : getOperatorName().hashCode());
    result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
    result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
    result = prime * result + ((getCreatorName() == null) ? 0 : getCreatorName().hashCode());
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
    sb.append(", invoiceDate=").append(invoiceDate);
    sb.append(", invoiceNo=").append(invoiceNo);
    sb.append(", expressNo=").append(expressNo);
    sb.append(", amount=").append(amount);
    sb.append(", invoiceEmployee=").append(invoiceEmployee);
    sb.append(", invoiceEmployeeName=").append(invoiceEmployeeName);
    sb.append(", operator=").append(operator);
    sb.append(", operatorName=").append(operatorName);
    sb.append(", lastTime=").append(lastTime);
    sb.append(", createTime=").append(createTime);
    sb.append(", creator=").append(creator);
    sb.append(", creatorName=").append(creatorName);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}