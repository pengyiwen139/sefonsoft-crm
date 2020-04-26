package com.sefonsoft.oa.domain.contract.dto;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author xielf
 */
@ApiModel("添加合同回款传输信息")
public class AddContractPayInfoListDTO {


  List<AddContractPayInfoItemDTO> items;


  public List<AddContractPayInfoItemDTO> getItems() {
    return items;
  }

  public void setItems(List<AddContractPayInfoItemDTO> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "AddContractPayInfoListDTO{" +
        "items=" + items +
        '}';
  }
}
