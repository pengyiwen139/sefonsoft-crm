package com.sefonsoft.oa.domain.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractPayInfo;
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

@ApiModel("修改回款传输数据")
public class UpdateContractPayInfoDTO {

  @ApiModelProperty(value = "回款信息ID", required = true)
  private Integer id;

  @ApiModelProperty("合同编号")
  private String contractId;

  @ApiModelProperty("金额")
  private BigDecimal amount;

  @ApiModelProperty("回款日期")
  @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE, timezone = "UTC+8")
  private Date payDate;


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

  @Override
  public String toString() {
    return "UpdateContractPayInfoDTO{" +
        "id=" + id +
        ", contractId='" + contractId + '\'' +
        ", amount=" + amount +
        ", payDate=" + payDate +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateContractPayInfoDTO that = (UpdateContractPayInfoDTO) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getContractId(), that.getContractId()) &&
        Objects.equals(getAmount(), that.getAmount()) &&
        Objects.equals(getPayDate(), that.getPayDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getContractId(), getAmount(), getPayDate());
  }

  public ContractPayInfo toContractPayInfo(LoginUserDTO loginUser) {

    Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    ContractPayInfo info = new ContractPayInfo();
    info.setOperatorName(loginUser.getEmployeeName());
    info.setOperator(loginUser.getUserId());
    info.setLastTime(now);
    info.setContractId(this.contractId);
    info.setId(this.id);
    info.setPayDate(this.payDate);
    info.setAmount(this.amount);

    return info;
  }
}
