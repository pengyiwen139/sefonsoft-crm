package com.sefonsoft.oa.domain.workorder.vo;

import java.util.Date;

/**
 * @author xielf
 */
public class WorkorderFeedbackSaleInfoVo {


  /**
   * 主键
   */
  private Long id;

  /**
   * 派工单id
   */
  private String pgdId;

  /**
   * 销售id
   */
  private String employeeId;

  /**
   * 售前支持员工id
   */
  private String targetEmployeeId;

  /**
   * 售前支持员工名字
   */
  private String targetEmployeeName;

  /**
   * 时间遵守5优秀、4良好、3合格、2较差、1很差
   */
  private Integer timeComply;

  /**
   * 服务态度,5优秀、4良好、3合格、2较差、1很差
   */
  private Integer service;

  /**
   * 技术水平, 5优秀、4良好、3合格、2较差、1很差
   */
  private Integer technology;

  /**
   * 案例掌握, 5优秀、4良好、3合格、2较差、1很差
   */
  private Integer caseGrasp;

  /**
   * 客户引导, 5优秀、4良好、3合格、2较差、1很差
   */
  private Integer customerLead;

  /**
   * 客户反馈, 5优秀、4良好、3合格、2较差、1很差
   */
  private Integer customerFeedback;

  /**
   * 其它
   */
  private String other;

  /**
   * 评价状态：0待评价，1已评价
   */
  private Integer saleCommentStatus;

  /**
   * 最后修改时间
   */
  private Date modifiedTime;

  /**
   * 创建时间
   */
  private Date createTime;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPgdId() {
    return pgdId;
  }

  public void setPgdId(String pgdId) {
    this.pgdId = pgdId;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getTargetEmployeeId() {
    return targetEmployeeId;
  }

  public void setTargetEmployeeId(String targetEmployeeId) {
    this.targetEmployeeId = targetEmployeeId;
  }

  public String getTargetEmployeeName() {
    return targetEmployeeName;
  }

  public void setTargetEmployeeName(String targetEmployeeName) {
    this.targetEmployeeName = targetEmployeeName;
  }

  public Integer getTimeComply() {
    return timeComply;
  }

  public void setTimeComply(Integer timeComply) {
    this.timeComply = timeComply;
  }

  public Integer getService() {
    return service;
  }

  public void setService(Integer service) {
    this.service = service;
  }

  public Integer getTechnology() {
    return technology;
  }

  public void setTechnology(Integer technology) {
    this.technology = technology;
  }

  public Integer getCaseGrasp() {
    return caseGrasp;
  }

  public void setCaseGrasp(Integer caseGrasp) {
    this.caseGrasp = caseGrasp;
  }

  public Integer getCustomerLead() {
    return customerLead;
  }

  public void setCustomerLead(Integer customerLead) {
    this.customerLead = customerLead;
  }

  public Integer getCustomerFeedback() {
    return customerFeedback;
  }

  public void setCustomerFeedback(Integer customerFeedback) {
    this.customerFeedback = customerFeedback;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  public Integer getSaleCommentStatus() {
    return saleCommentStatus;
  }

  public void setSaleCommentStatus(Integer saleCommentStatus) {
    this.saleCommentStatus = saleCommentStatus;
  }

  public Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
