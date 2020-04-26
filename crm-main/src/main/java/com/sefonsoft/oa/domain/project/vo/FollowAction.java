package com.sefonsoft.oa.domain.project.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sefonsoft.oa.domain.common.YNFlag;

import io.swagger.annotations.ApiModelProperty;

public class FollowAction {
  
  @NotNull
  @ApiModelProperty("是否跟进/认领项目")
  YNFlag follow;
  
  @ApiModelProperty("指定认领人员，如果没有传值那么默认自己认领")
  String employeeId;

  @NotEmpty
  @ApiModelProperty("项目ID列表")
  List<String> projectIds;

  public YNFlag getFollow() {
    return follow;
  }

  public void setFollow(YNFlag follow) {
    this.follow = follow;
  }

  public List<String> getProjectIds() {
    return projectIds;
  }

  public void setProjectIds(List<String> projectIds) {
    this.projectIds = projectIds;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }
}
