package com.sefonsoft.oa.domain.customer;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * (CustomerInfo)实体类
 *
 * @author Aron
 * @since 2019-11-29 13:23:10
 */
public class CustomerInfoExport {

    /**
     * 客户名称
     */
    @ApiModelProperty(value="单位名称")
    private String customerName;

    /**
     * 企业性质名称
     */
    @ApiModelProperty(value="单位性质名称")
    private String entername;
    /**
     * 可手动输入，默认可选内容：政府、旅游、金融、交通、能源等
     */
    @ApiModelProperty(value="所属行业")
    private String industry;
    /**
     * 省市区
     */
    @ApiModelProperty(value="省市区")
    private String ssq;

    /**
     * 单位地址
     */
    @ApiModelProperty(value="单位地址")
    private String address;
    /**
     * 网址
     */
    @ApiModelProperty(value="网址")
    private String website;

    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String zipCode;

    /**
     * 电话
     */
    @ApiModelProperty(value="单位电话")
    private String telephone;
    /**
     * 传真
     */
    @ApiModelProperty(value="传真")
    private String fax;

    /**
     * 负责人工号
     */
    @ApiModelProperty(value="销售负责人工号,不能为空,example=SF1000")
    private String employeeId;

    @ApiModelProperty(value="销售负责人姓名")
    private String employeeName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="销售负责人所属团队")
    private String deptName;



    /**
     * 联系人
     */
    @ApiModelProperty(value="客户联系人")
    private String contacts;

    /**
     * 性别，1：男，2：女
     */
    @ApiModelProperty(value="性别：1男，2女")
    private Integer sex;
    /**
     * 联系人所属部门
     */
    @ApiModelProperty(value="联系人所属部门")
    private String contactsDepart;

    /**
     * 职务
     */
    @ApiModelProperty("客户联系人职务")
    private String job;
    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系人电话")
    private String contactsTelephone;
    /**
     * 联系邮箱
     */
    @ApiModelProperty(value="联系邮箱")
    private String contactsEmail;


    /**
     * 办公座机
     */
    @ApiModelProperty("办公座机")
    private String officePlane;
    /**
     * 毕业学校
     */
    @ApiModelProperty("毕业学校")
    private String university;
    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String major;
    /**
     * 家庭状况
     */
    @ApiModelProperty("家庭状况")
    private String familyInfo;
    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private Date birthday;
    /**
     * 其他
     */
    @ApiModelProperty("其他")
    private String other;

    /**
     * 创建时间
     */
    //@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSsq() {
        return ssq;
    }

    public void setSsq(String ssq) {
        this.ssq = ssq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getContactsDepart() {
        return contactsDepart;
    }

    public void setContactsDepart(String contactsDepart) {
        this.contactsDepart = contactsDepart;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getContactsTelephone() {
        return contactsTelephone;
    }

    public void setContactsTelephone(String contactsTelephone) {
        this.contactsTelephone = contactsTelephone;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}