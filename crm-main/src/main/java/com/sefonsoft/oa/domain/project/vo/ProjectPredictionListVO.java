package com.sefonsoft.oa.domain.project.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("项目预测")
public class ProjectPredictionListVO implements Serializable {

  private static final long serialVersionUID = -2029061525658940747L;

  /**
   * 项目编号
   */
  @ApiModelProperty("项目编号,索引+主键，编号规则：前缀+日期+流水（4位）")
  private String projectId;

  /**
   * 项目名称
   */
  @ApiModelProperty("项目名称(展示项)")
  private String projectName;
  /**
   * 客户编号,索引，关联customer_info表customer_id
   */
  @ApiModelProperty("客户编号,索引，关联customer_info表customer_id")
  private String customerId;
  /**
   * 客户名称
   */
  @ApiModelProperty(value = "客户名称(展示项)")
  private String customerName;
  /**
   * 填字符串，已申请立项，资金到位，等
   */
  @ApiModelProperty("客户项目阶段，已申请立项，资金到位，等(展示项)")
  private String customerProjectPhase;

  /**
   * 预估项目金额，单位：万元
   */
  @ApiModelProperty("预估项目金额，单位：万元(展示项)")
  private BigDecimal estimateMoney;

  /**
   * 预估签约时间
   */
  @ApiModelProperty("预估签约时间(展示项)")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date estimateTime;

  /**
   * 百分比数字
   */
  @ApiModelProperty("百分比数字(展示项)")
  private Integer estimateSuccess;

  /**
   * 客户编号
   */
  @ApiModelProperty("传员工工号/返回负责人工号(必传)")
  private String employeeId;

  /**
   * 销售负责人名称
   */
  @ApiModelProperty(value = "销售负责人(展示项)")
  private String employeeName;
  /**
   * 部门编号
   */
  @ApiModelProperty("传部门编号/返回负责人部门编号")
  private String deptId;

  /**
   * 部门名称
   */
  @ApiModelProperty(value = "负责人部门(展示项)")
  private String deptName;

  /**
   * @return the projectId
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @param projectId the projectId to set
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  /**
   * @return the projectName
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * @param projectName the projectName to set
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * @return the customerId
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * @param customerId the customerId to set
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * @return the customerProjectPhase
   */
  public String getCustomerProjectPhase() {
    return customerProjectPhase;
  }

  /**
   * @param customerProjectPhase the customerProjectPhase to set
   */
  public void setCustomerProjectPhase(String customerProjectPhase) {
    this.customerProjectPhase = customerProjectPhase;
  }

  /**
   * @return the estimateMoney
   */
  public BigDecimal getEstimateMoney() {
    return estimateMoney;
  }

  /**
   * @param estimateMoney the estimateMoney to set
   */
  public void setEstimateMoney(BigDecimal estimateMoney) {
    this.estimateMoney = estimateMoney;
  }

  /**
   * @return the estimateTime
   */
  public Date getEstimateTime() {
    return estimateTime;
  }

  /**
   * @param estimateTime the estimateTime to set
   */
  public void setEstimateTime(Date estimateTime) {
    this.estimateTime = estimateTime;
  }

  /**
   * @return the estimateSuccess
   */
  public Integer getEstimateSuccess() {
    return estimateSuccess;
  }

  /**
   * @param estimateSuccess the estimateSuccess to set
   */
  public void setEstimateSuccess(Integer estimateSuccess) {
    this.estimateSuccess = estimateSuccess;
  }

  /**
   * @return the employeeId
   */
  public String getEmployeeId() {
    return employeeId;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @return the employeeName
   */
  public String getEmployeeName() {
    return employeeName;
  }

  /**
   * @param employeeName the employeeName to set
   */
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  /**
   * @return the deptId
   */
  public String getDeptId() {
    return deptId;
  }

  /**
   * @param deptId the deptId to set
   */
  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  /**
   * @return the deptName
   */
  public String getDeptName() {
    return deptName;
  }

  /**
   * @param deptName the deptName to set
   */
  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  
}
