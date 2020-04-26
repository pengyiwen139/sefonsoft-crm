package com.sefonsoft.oa.domain.contract.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author xielf
 */
@ApiModel
public class ListContractInvoiceByContractIdDTO {

  @ApiModelProperty(value = "合同编号", required = true)
  @NotNull(message = "合同编号不能为空")
  private String contractId;


  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }


  @Override
  public String toString() {
    return "ListContractInvoiceByContractIdDTO{" +
        "contractId='" + contractId + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListContractInvoiceByContractIdDTO that = (ListContractInvoiceByContractIdDTO) o;
    return Objects.equals(getContractId(), that.getContractId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContractId());
  }
}
