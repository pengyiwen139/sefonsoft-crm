package com.sefonsoft.oa.domain.sysemployee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (SysEmployee)实体类
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
@ApiModel(value = "员工展示类")
@Data
public class SysEmployeeQueryDTO implements Serializable {
    private static final long serialVersionUID = 997840320378431576L;

    /**
     * 当前用户的数据角色下的部门编号列表
     */
    @ApiModelProperty(value="数据角色下的部门编号列表")
    private List<String> dataDeptIdList;
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Long id;
    /**
    * 工号
    */
    @ApiModelProperty(value="员工工号")
    private String employeeId;
    /**
    * 用户编号
    */
    @ApiModelProperty(value="用户编号")
    private String userId;
    /**
    * 姓名
    */
    @ApiModelProperty(value="姓名")
    private String employeeName;
    /**
    * 性别，1：男，2：女
    */
    @ApiModelProperty(value="性别")
    private Integer sex;
    /**
    * 电话
    */
    @ApiModelProperty(value="电话")
    private String tel;
    /**
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
    * 在职状态，1在职，2离职
    */
    @ApiModelProperty(value="在职状态")
    private Integer jobStatus;
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
    * 职系编号
    */
    @ApiModelProperty(value="职系编号",example="XS0001")
    private String gradeId;

    /**
     * 职系名称
     */
    @ApiModelProperty(value="职系名称")
    private String gradeName;

    /**
    * 岗位编号
    */
    private String stationId;
    /**
     *  岗位名称
     */
    @ApiModelProperty(value="岗位名称",example="工程师")
    private String stationName;
    /**
    * 操作人员用户编号，关联sys_user的业务外键
    */
    @ApiModelProperty(value="操作人员用户编号")
    private String operatorUserId;
    /**
    * 直管领导编号,关联sys_employee表的业务外键
    */
    @ApiModelProperty(value="直管领导编号")
    private String directLeaderId;
    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
    * 操作时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="操作时间")
    private Date modifiedTime;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty("数据角色编号列表")
    private List<String> dataRoleIdList;

    @ApiModelProperty("操作角色编号列表")
    private List<String> menuRoleIdList;

    @ApiModelProperty("是否开通登录账号，0不开通，1开通")
    private Integer hasMakeAccount;

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


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<String> getDataRoleIdList() {
        return dataRoleIdList;
    }

    public void setDataRoleIdList(List<String> dataRoleIdList) {
        this.dataRoleIdList = dataRoleIdList;
    }

    public List<String> getMenuRoleIdList() {
        return menuRoleIdList;
    }

    public void setMenuRoleIdList(List<String> menuRoleIdList) {
        this.menuRoleIdList = menuRoleIdList;
    }

    public List<String> getDataDeptIdList() {
        return dataDeptIdList;
    }

    public void setDataDeptIdList(List<String> dataDeptIdList) {
        this.dataDeptIdList = dataDeptIdList;
    }
}