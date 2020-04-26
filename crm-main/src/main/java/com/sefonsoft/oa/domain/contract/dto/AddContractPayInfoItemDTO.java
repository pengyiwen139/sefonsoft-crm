package com.sefonsoft.oa.domain.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractPayInfo;
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
@ApiModel("添加合同回款传输信息")
public class AddContractPayInfoItemDTO {


  @ApiModelProperty(value = "合同编号", required = true)
  @NotNull(message = "合同编号不能为空")
  private String contractId;

  @ApiModelProperty(value = "金额", required = true)
  @NotNull(message = "回款金额不能为空")
  private BigDecimal amount;

  @ApiModelProperty(value = "回款时间", required = true)
  @NotNull(message = "回款时间不能为空")
  @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE, timezone = "UTC+8")
  private Date payDate;


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

  @Override
  public String toString() {
    return "AddContractPayInfoDTO{" +
        "contractId='" + contractId + '\'' +
        ", amount=" + amount +
        ", payDate=" + payDate +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddContractPayInfoItemDTO that = (AddContractPayInfoItemDTO) o;
    return Objects.equals(getContractId(), that.getContractId()) &&
        Objects.equals(getAmount(), that.getAmount()) &&
        Objects.equals(getPayDate(), that.getPayDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContractId(), getAmount(), getPayDate());
  }

  /**
   * 转换回款信息
   *
   * @return
   */
  public ContractPayInfo toContractPayInfo(LoginUserDTO loginUser) {

    final Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    ContractPayInfo contractPayInfo = new ContractPayInfo();
    contractPayInfo.setAmount(this.amount);
    contractPayInfo.setContractId(this.contractId);
    contractPayInfo.setPayDate(this.payDate);

    contractPayInfo.setLastTime(now);
    contractPayInfo.setOperator(loginUser.getUserId());
    contractPayInfo.setOperatorName(loginUser.getEmployeeName());

    contractPayInfo.setCreateTime(now);
    contractPayInfo.setCreator(loginUser.getUserId());
    contractPayInfo.setCreatorName(loginUser.getEmployeeName());

    return contractPayInfo;
  }
}
