package com.sefonsoft.oa.domain.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractInvoiceInfo;
import com.sefonsoft.oa.system.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author xielf
 */
@ApiModel("添加发票传输数据信息")
public class AddContractInvoiceItemDTO {


  @ApiModelProperty(value = "合同编号", required = true)
  @NotNull(message = "合同编号不能为空")
  private String contractId;

  @ApiModelProperty(value = "发票日期", required = true)
  @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE, timezone = "UTC+8")
  @NotNull(message = "发票日期不能为空")
  private Date invoiceDate;

  @ApiModelProperty(value = "发票编号", required = true)
  @NotNull(message = "发票编号不能为空")
  private String invoiceNo;

  @ApiModelProperty(value = "快递单号", required = true)
  @NotNull(message = "发票快递单号不能为空")
  private String expressNo;

  @ApiModelProperty(value = "发票金额", required = true)
  @NotNull(message = "发票金额不能为空")
  private BigDecimal amount;

  @ApiModelProperty(value = "开票人ID", required = true)
  @NotNull(message = "开票人不能为空")
  private String invoiceEmployee;


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

  @Override
  public String toString() {
    return "AddContractInvoiceInfoDTO{" +
        "contractId='" + contractId + '\'' +
        ", invoiceDate=" + invoiceDate +
        ", invoiceNo='" + invoiceNo + '\'' +
        ", expressNo='" + expressNo + '\'' +
        ", amount=" + amount +
        ", invoiceEmployee='" + invoiceEmployee + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddContractInvoiceItemDTO that = (AddContractInvoiceItemDTO) o;
    return Objects.equals(getContractId(), that.getContractId()) &&
        Objects.equals(getInvoiceDate(), that.getInvoiceDate()) &&
        Objects.equals(getInvoiceNo(), that.getInvoiceNo()) &&
        Objects.equals(getExpressNo(), that.getExpressNo()) &&
        Objects.equals(getAmount(), that.getAmount()) &&
        Objects.equals(getInvoiceEmployee(), that.getInvoiceEmployee());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContractId(), getInvoiceDate(), getInvoiceNo(), getExpressNo(), getAmount(), getInvoiceEmployee());
  }

  /**
   * 转换合同信息
   * @return
   */
  public ContractInvoiceInfo toContractInvoiceInfo(LoginUserDTO loginUser){

    final Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    ContractInvoiceInfo invoiceInfo = new ContractInvoiceInfo();

    invoiceInfo.setAmount(this.amount);
    invoiceInfo.setContractId(this.contractId);
    invoiceInfo.setExpressNo(this.expressNo);
    invoiceInfo.setInvoiceDate(this.invoiceDate);
    invoiceInfo.setInvoiceNo(this.invoiceNo);
    invoiceInfo.setInvoiceEmployee(this.getInvoiceEmployee());

    invoiceInfo.setOperator(loginUser.getUserId());
    invoiceInfo.setOperatorName(loginUser.getEmployeeName());
    invoiceInfo.setLastTime(now);

    invoiceInfo.setCreator(loginUser.getUserId());
    invoiceInfo.setCreatorName(loginUser.getEmployeeName());
    invoiceInfo.setCreateTime(now);

    return invoiceInfo;

  }
}
