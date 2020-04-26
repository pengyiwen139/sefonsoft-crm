package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * 周日报表(EmployeeReportInfo)实体类
 *
 * @author Aron
 * @since 2019-12-09 15:21:59
 */
@ApiModel("周日报表查询实体类")
@Accessors(chain = true)
public class EmployeeReportQueryDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;

    /**
     * 当前用户的数据角色下的部门编号列表
     */
    @ApiModelProperty(value="数据角色下的部门编号列表")
    private List<String> dataDeptIdList;


    List<EmployeeReportDetailDTO> EmployeeReportDetail;

    /**
     * 员工对应职系
     */
    @ApiModelProperty("员工对应职系")
    private String gradeId;
    /**
     * 年份
     */
    @ApiModelProperty("年份2019")
    private String year;
    /**
     * 月份
     */
    @ApiModelProperty("月份，1，2...")
    private String month;
        
    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    员工工号 
    */
    @ApiModelProperty("员工工号")
    private String employeeId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String employeeName;

    /**
     * 部门编号
     */
    @ApiModelProperty(value="部门编号")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String deptName;
        
    /**
    报表类型：1日报，2周报 
    */
    @ApiModelProperty("报表类型：1日报，2周报")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @ApiModelProperty("报告日期")
    private String reportTime;
        
    /**
    操作类型,1线下，2点后，3操作 
    */
    @ApiModelProperty("操作类型,1线下，2点后，3操作")
    private Integer operationType;
        
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
    联系人id
    */
    @ApiModelProperty("联系人id")
    private Long contactId;

    /**
     指客户方的项目联系人
     */
    @ApiModelProperty("联系人名字")
    private String contactName;

    /**
     指客户方的项目联系人手机
     */
    @ApiModelProperty("联系人手机")
    private String contactTel;

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
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
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

    public List<EmployeeReportDetailDTO> getEmployeeReportDetail() {
        return EmployeeReportDetail;
    }

    public void setEmployeeReportDetail(List<EmployeeReportDetailDTO> employeeReportDetail) {
        EmployeeReportDetail = employeeReportDetail;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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

    public List<String> getDataDeptIdList() {
        return dataDeptIdList;
    }

    public void setDataDeptIdList(List<String> dataDeptIdList) {
        this.dataDeptIdList = dataDeptIdList;
    }
}