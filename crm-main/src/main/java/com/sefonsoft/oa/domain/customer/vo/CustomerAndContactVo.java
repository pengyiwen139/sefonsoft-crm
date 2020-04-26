package com.sefonsoft.oa.domain.customer.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xielf
 */
public class CustomerAndContactVo {


  private String customerName;
  private String address;
  private String industry;
  private String contactName;
  private String[] employeeIds;
  private String telephone;


  public String[] getEmployeeIds() {
    return employeeIds;
  }

  public void setEmployeeIds(String[] employeeIds) {
    this.employeeIds = employeeIds;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CustomerAndContactVo that = (CustomerAndContactVo) o;
    return Objects.equals(getCustomerName(), that.getCustomerName()) &&
        Objects.equals(getAddress(), that.getAddress()) &&
        Objects.equals(getIndustry(), that.getIndustry()) &&
        Objects.equals(getContactName(), that.getContactName()) &&
        Arrays.equals(getEmployeeIds(), that.getEmployeeIds()) &&
        Objects.equals(getTelephone(), that.getTelephone());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getCustomerName(), getAddress(), getIndustry(), getContactName(), getTelephone());
    result = 31 * result + Arrays.hashCode(getEmployeeIds());
    return result;
  }
}
