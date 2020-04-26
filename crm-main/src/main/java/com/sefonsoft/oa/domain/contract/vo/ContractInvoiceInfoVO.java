package com.sefonsoft.oa.domain.contract.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xielf
 */
public class ContractInvoiceInfoVO {

  @ApiModelProperty("发票ID")
  private Integer id;

  /**
   * 合同编号
   */
  @ApiModelProperty("合同编号")
  private String contractId;

  /**
   * 开票日期
   */
  @ApiModelProperty("开票日期")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date invoiceDate;

  /**
   * 票据单号
   */
  @ApiModelProperty("票据单号")
  private String invoiceNo;

  /**
   * 快递单号
   */
  @ApiModelProperty("快递单号")
  private String expressNo;

  /**
   * 开票金额
   */
  @ApiModelProperty("开票金额")
  private BigDecimal amount;

  /**
   * 开票员工
   */
  @ApiModelProperty("开票员工")
  private String invoiceEmployee;

  /**
   * 开票员工姓名
   */
  @ApiModelProperty("开票员工姓名")
  private String invoiceEmployeeName;

  /**
   * 操作员
   */
  @ApiModelProperty("修改人工号")
  private String operator;

  /**
   * 操作员姓名
   */
  @ApiModelProperty("修改人")
  private String operatorName;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date createTime;


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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "ContractInvoiceInfoVO{" +
        "id=" + id +
        ", contractId='" + contractId + '\'' +
        ", invoiceDate=" + invoiceDate +
        ", invoiceNo='" + invoiceNo + '\'' +
        ", expressNo='" + expressNo + '\'' +
        ", amount=" + amount +
        ", invoiceEmployee='" + invoiceEmployee + '\'' +
        ", invoiceEmployeeName='" + invoiceEmployeeName + '\'' +
        ", operator='" + operator + '\'' +
        ", operatorName='" + operatorName + '\'' +
        ", createTime=" + createTime +
        '}';
  }
}
