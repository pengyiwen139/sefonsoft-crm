package com.sefonsoft.oa.domain.contract.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.Objects;

/**
 * @author xielf
 */
@ApiModel
public class ListContractPayByContractIdDTO {


  @ApiParam(value = "合同编号", required = true)
  private String contractId;


  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  @Override
  public String toString() {
    return "ListContractPayByContractIdDTO{" +
        "contractId='" + contractId + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListContractPayByContractIdDTO that = (ListContractPayByContractIdDTO) o;
    return Objects.equals(getContractId(), that.getContractId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContractId());
  }
}
