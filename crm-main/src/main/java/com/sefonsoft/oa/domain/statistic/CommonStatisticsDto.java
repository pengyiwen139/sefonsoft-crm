package com.sefonsoft.oa.domain.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author xielf
 */
@ApiModel("统计数据提交参数")
public class CommonStatisticsDto {


  @ApiModelProperty("查询的指定的用户的ID，注：非领导用户传入无效")
  private String employeeId;

  // 时间类型取值，0-总数，1-月，2-季度，3-年，4-周
  @ApiModelProperty("时间类型取值，0 所有，1-月，2-季度，3-年，4-周")
  private int onTime;

  @ApiModelProperty("指定大区，不传则查询拥有权限大区（注：非领导用户传入无效）")
  private String deptId;


  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public int getOnTime() {
    return onTime;
  }

  public void setOnTime(int onTime) {
    this.onTime = onTime;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }
}
