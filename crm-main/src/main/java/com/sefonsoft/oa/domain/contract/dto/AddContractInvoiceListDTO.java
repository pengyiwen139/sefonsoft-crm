package com.sefonsoft.oa.domain.contract.dto;

import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Objects;

/**
 * @author xielf
 */
@ApiModel("添加发票传输数据信息")
public class AddContractInvoiceListDTO {

  List<AddContractInvoiceItemDTO> items;

  public List<AddContractInvoiceItemDTO> getItems() {
    return items;
  }

  public void setItems(List<AddContractInvoiceItemDTO> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "AddContractInvoiceListDTO{" +
        "items=" + items +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddContractInvoiceListDTO that = (AddContractInvoiceListDTO) o;
    return Objects.equals(getItems(), that.getItems());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getItems());
  }
}
