package com.sefonsoft.oa.domain.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractInvoiceInfo;
import com.sefonsoft.oa.system.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author xielf
 */

@ApiModel("修改发票传输数据")
public class UpdateContractInvoiceInfoDTO {

  @ApiModelProperty(value = "发票ID", required = true)
  private Integer id;

  @ApiModelProperty("合同编号")
  private String contractId;

  @ApiModelProperty("发票日期")
  @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE, timezone = "UTC+8")
  private Date invoiceDate;

  @ApiModelProperty("发票编号")
  private String invoiceNo;

  @ApiModelProperty("快递单号")
  private String expressNo;

  @ApiModelProperty("发票金额")
  private BigDecimal amount;

  @ApiModelProperty(value = "开票人ID", required = true)
  private String invoiceEmployee;

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

  public String getInvoiceEmployee() {
    return invoiceEmployee;
  }

  public void setInvoiceEmployee(String invoiceEmployee) {
    this.invoiceEmployee = invoiceEmployee;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateContractInvoiceInfoDTO that = (UpdateContractInvoiceInfoDTO) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getContractId(), that.getContractId()) &&
        Objects.equals(getInvoiceDate(), that.getInvoiceDate()) &&
        Objects.equals(getInvoiceNo(), that.getInvoiceNo()) &&
        Objects.equals(getExpressNo(), that.getExpressNo()) &&
        Objects.equals(getAmount(), that.getAmount()) &&
        Objects.equals(getInvoiceEmployee(), that.getInvoiceEmployee());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getContractId(), getInvoiceDate(), getInvoiceNo(), getExpressNo(), getAmount(), getInvoiceEmployee());
  }

  @Override
  public String toString() {
    return "UpdateContractInvoiceInfoDTO{" +
        "id=" + id +
        ", contractId='" + contractId + '\'' +
        ", invoiceDate=" + invoiceDate +
        ", invoiceNo='" + invoiceNo + '\'' +
        ", expressNo='" + expressNo + '\'' +
        ", amount=" + amount +
        ", invoiceEmployee='" + invoiceEmployee + '\'' +
        '}';
  }

  public ContractInvoiceInfo toContractInvoiceInfo(LoginUserDTO loginUser) {

    final Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    ContractInvoiceInfo info = new ContractInvoiceInfo();
    info.setInvoiceEmployee(this.invoiceEmployee);
    info.setInvoiceNo(this.invoiceNo);
    info.setInvoiceDate(this.invoiceDate);
    info.setExpressNo(this.expressNo);
    info.setAmount(this.amount);
    info.setContractId(this.contractId);
    info.setId(this.id);

    info.setLastTime(now);
    info.setOperator(loginUser.getUserId());
    info.setOperatorName(loginUser.getEmployeeName());

    return info;
  }
}
