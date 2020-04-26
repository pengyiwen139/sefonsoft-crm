package com.sefonsoft.oa.domain.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xielf
 */
@ApiModel
public class UpdateSalesFeedbackDTO {


//  /**
//   * 评论列表
//   */
//  @ApiModelProperty("评论列表")
//  private List<SalesFeedback> salesFeedbackList;

//  public class SalesFeedback {

    @ApiModelProperty("id")
    private long id;

    @ApiModelProperty("派工单ID")
    private String pgdId;

    @ApiModelProperty("时间遵守5优秀、4良好、3合格、2较差、1很差")
    private int timeComply;

    @ApiModelProperty("服务态度,5优秀、4良好、3合格、2较差、1很差")
    private int service;

    @ApiModelProperty("技术水平, 5优秀、4良好、3合格、2较差、1很差")
    private int technology;

    @ApiModelProperty("案例掌握, 5优秀、4良好、3合格、2较差、1很差")
    private int caseGrasp;

    @ApiModelProperty("客户引导, 5优秀、4良好、3合格、2较差、1很差")
    private int customerLead;

    @ApiModelProperty("客户反馈, 5优秀、4良好、3合格、2较差、1很差")
    private int customerFeedback;

    @ApiModelProperty("其它")
    private String other;

    @ApiModelProperty("售前支持员工id")
    private String targetEmployeeId;

    @ApiModelProperty("售前支持员工id")
    private String targetEmployeeName;


    @Override
    public String toString() {
      return super.toString() +
          "AddSalesFeedbackDTO{" +
          "pgdId='" + pgdId + '\'' +
          ", timeComply=" + timeComply +
          ", service=" + service +
          ", technology=" + technology +
          ", caseGrasp=" + caseGrasp +
          ", customerLead=" + customerLead +
          ", customerFeedback=" + customerFeedback +
          ", other='" + other + '\'' +
          ", targetEmployeeId='" + targetEmployeeId + '\'' +
          '}';
    }

    public long getId() {
        return this.id;
    }

    public String getPgdId() {
        return this.pgdId;
    }

    public int getTimeComply() {
        return this.timeComply;
    }

    public int getService() {
        return this.service;
    }

    public int getTechnology() {
        return this.technology;
    }

    public int getCaseGrasp() {
        return this.caseGrasp;
    }

    public int getCustomerLead() {
        return this.customerLead;
    }

    public int getCustomerFeedback() {
        return this.customerFeedback;
    }

    public String getOther() {
        return this.other;
    }

    public String getTargetEmployeeId() {
        return this.targetEmployeeId;
    }

    public String getTargetEmployeeName() {
        return this.targetEmployeeName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPgdId(String pgdId) {
        this.pgdId = pgdId;
    }

    public void setTimeComply(int timeComply) {
        this.timeComply = timeComply;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setTechnology(int technology) {
        this.technology = technology;
    }

    public void setCaseGrasp(int caseGrasp) {
        this.caseGrasp = caseGrasp;
    }

    public void setCustomerLead(int customerLead) {
        this.customerLead = customerLead;
    }

    public void setCustomerFeedback(int customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setTargetEmployeeId(String targetEmployeeId) {
        this.targetEmployeeId = targetEmployeeId;
    }

    public void setTargetEmployeeName(String targetEmployeeName) {
        this.targetEmployeeName = targetEmployeeName;
    }


//  }
//
//  public List<SalesFeedback> getSalesFeedbackList() {
//    return salesFeedbackList;
//  }
//
//  public void setSalesFeedbackList(List<SalesFeedback> salesFeedbackList) {
//    this.salesFeedbackList = salesFeedbackList;
//  }
//
//  @Override
//  public String toString() {
//    return super.toString() +
//        "AddSalesFeedbackDTO{" +
//        "salesFeedback=" + salesFeedbackList +
//        '}';
//  }
}
