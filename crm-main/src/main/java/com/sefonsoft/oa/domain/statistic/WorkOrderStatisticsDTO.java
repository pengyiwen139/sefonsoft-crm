package com.sefonsoft.oa.domain.statistic;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author xielf
 */
@ApiModel
public class WorkOrderStatisticsDTO {


  public final static int SALE = 1;
  public final static int PRE = 2;

  @ApiModelProperty("员工类型(1、销售    2、售前)")
  private Integer employeeType;


  public Integer getEmployeeType() {
    return employeeType;
  }

  public void setEmployeeType(Integer employeeType) {
    this.employeeType = employeeType;
  }

  @Override
  public String toString() {
    return "WorkerStatisticsDTO{" +
        "employeeType=" + employeeType +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WorkOrderStatisticsDTO that = (WorkOrderStatisticsDTO) o;
    return Objects.equals(getEmployeeType(), that.getEmployeeType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmployeeType());
  }
}
