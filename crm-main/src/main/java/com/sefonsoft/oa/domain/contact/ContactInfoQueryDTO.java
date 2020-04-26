package com.sefonsoft.oa.domain.contact;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (ContactInfo)实体类
 *
 * @author Aron
 * @since 2019-11-16 17:26:42
 */
@ApiModel(value = "联系人查询展示类")
public class ContactInfoQueryDTO{

    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private Long id;
    /**
    * 客户编号,索引，关联customer_info表customer_id
    */
    @ApiModelProperty("客户编号，必传")
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
     * 1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它
     */
    @ApiModelProperty("联系人充当角色1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它")
    private String roleName;
    /**
    * 部门
    */
    @ApiModelProperty("客户联系人部门：必填")
    private String deptName;
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


    @ApiModelProperty("办公座机")
    private String officePlane;

    @ApiModelProperty("毕业学校")
    private String university;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("家庭状况")
    private String familyInfo;

    @ApiModelProperty("其他")
    private String other;

    @ApiModelProperty("生日")
    private Date birthday;

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

    private List<String> dataDeptIdList;

    public List<String> getDataDeptIdList() {
        return dataDeptIdList;
    }

    public void setDataDeptIdList(List<String> dataDeptIdList) {
        this.dataDeptIdList = dataDeptIdList;
    }

    public String getOfficePlane() {
        return officePlane;
    }

    public void setOfficePlane(String officePlane) {
        this.officePlane = officePlane;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(String familyInfo) {
        this.familyInfo = familyInfo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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