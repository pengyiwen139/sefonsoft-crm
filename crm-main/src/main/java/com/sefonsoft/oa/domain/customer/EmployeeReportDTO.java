package com.sefonsoft.oa.domain.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * 周日报表(EmployeeReportInfo)实体类
 *
 * @author Aron
 * @since 2019-12-11 15:21:59
 */
@ApiModel("客户跟踪项目展示类")
@Accessors(chain = true)
public class EmployeeReportDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;


    /**
     * 客户编号
     */
    @ApiModelProperty(value="客户编号,必传字段")
    private String customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String customerName;

    /**
    员工工号 
    */
    @ApiModelProperty("销售工号,必传字段")
    private String employeeId;

    /**
     员工
     */
    @ApiModelProperty("销售姓名")
    private String employeeName;

    /**
     项目编号
     */
    @ApiModelProperty("项目编号")
    private String projectId;

    /**
     项目名称
     */
    @ApiModelProperty("项目名称(展示项)")
    private String projectName;


    /**
     * 联系人id
     */
    @ApiModelProperty("联系人id")
    private Integer contactId;
    /**
     * 联系人名字
     */
    @ApiModelProperty("联系人名字")
    private String contactName;
        
    /**
    报表类型：1日报，2周报 
    */
    @ApiModelProperty("报表类型：1日报，2周报")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @ApiModelProperty("报告日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date reportTime;
        
    /**
    操作类型,1线下，2点后，3操作 
    */
    @ApiModelProperty("操作类型,1线下拜访，2电话回访，,必传字段")
    private Integer operationType;


    /**
    跟进详情 
    */
    @ApiModelProperty("跟进详情")
    private String followDetails;
        
    /**
    下步计划 
    */
    @ApiModelProperty("下步计划")
    private String nextStepPlan;
        
    /**
    操作员 
    */
    @ApiModelProperty("操作员")
    private String operator;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty("最后操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
    private Integer rows;


    public Integer getOffset() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFollowDetails() {
        return followDetails;
    }

    public void setFollowDetails(String followDetails) {
        this.followDetails = followDetails;
    }

    public String getNextStepPlan() {
        return nextStepPlan;
    }

    public void setNextStepPlan(String nextStepPlan) {
        this.nextStepPlan = nextStepPlan;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}