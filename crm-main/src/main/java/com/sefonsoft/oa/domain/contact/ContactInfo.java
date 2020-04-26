package com.sefonsoft.oa.domain.contact;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * (ContactInfo)实体类
 *
 * @author Aron
 * @since 2019-11-16 17:26:42
 */
@Deprecated
public class ContactInfo implements Serializable {
    private static final long serialVersionUID = -86229930101467649L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long ids;
    /**
     * 客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty("客户编号")
    private String customerId;
    /**
     * 姓名
     */
    @ApiModelProperty("客户姓名：必填")
    private String contactName;
    /**
     * 性别，1男, 2女
     */
    @ApiModelProperty("性别，1男, 2女")
    private Integer contactSex;
    /**
     * 1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它
     */
    @ApiModelProperty("联系人充当角色1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它")
    private Integer role;
    /**
     * 部门
     */
    @ApiModelProperty("客户联系人部门：必填")
    private String deptNames;
    /**
     * 职务
     */
    @ApiModelProperty("客户联系人职务：必填")
    private String job;
    /**
     * 电话
     */
    @ApiModelProperty("客户联系人电话：必填")
    private String telphone;
    /**
     * 邮箱
     */
    @ApiModelProperty("客户联系人邮箱：必填")
    private String email;
    /**
     * 工号，索引，关联sys_employee表employee_id
     */
    @ApiModelProperty("共有人或者负责人工号，不能为空")
    private String employeeId;
    /**
     * 操作员，关联sys_user表user_id
     */
    @ApiModelProperty("操作员，不能为空")
    private String operator;

    /**
     * 最后操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty("最后操作时间")
    private Date lastTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;


    private String officePlane;


    public String getOfficePlane() {
        return officePlane;
    }

    public void setOfficePlane(String officePlane) {
        this.officePlane = officePlane;
    }

    public Long getId() {
      return ids;
    }
    
    public void setId(Long ids) {
      this.ids = ids;
    }
    
    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getContactSex() {
        return contactSex;
    }

    public void setContactSex(Integer contactSex) {
        this.contactSex = contactSex;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

}