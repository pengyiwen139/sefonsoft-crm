package com.sefonsoft.oa.domain.customer.vo;

/**
 * @author xielf
 */
public class ExportCustomerInfoQueryVoP extends ExportCustomerInfoQueryVo {


  private String employeeId;
  private String employeeName;
  private String customerContact;


  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getCustomerContact() {
    return customerContact;
  }

  public void setCustomerContact(String customerContact) {
    this.customerContact = customerContact;
  }
}
