package com.sefonsoft.oa.domain.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (CustomerInfo)实体类
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@ApiModel(value = "客户查询展示类")
public class CustomerInfoQueryDTO implements Serializable {
    private static final long serialVersionUID = -85446373477015119L;

    @ApiModelProperty(value = "客户联系人相关信息，如：job职业")
    private List<ContactInfo> contactList;

    /**
     * 员工对应职系
     */
    @ApiModelProperty(value = "员工对应职系,必须传" , hidden = true)

    private String gradeId;

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;
    /**
     * 上下半年
     */
    @ApiModelProperty("上下半年，上半年1，下半年2")
    private String halfYear;
    /**
     * 季度
     */
    @ApiModelProperty("季度，1一季度，2二季度，3三季度，4四季度")
    private String quarter;
    /**
     * 月份
     */
    @ApiModelProperty("月份，1一月，2二月......")
    private String month;
    /**
     * 部门编号
     */
    @ApiModelProperty("传部门编号/返回负责人部门编号")
    private String deptId;
    /**
     * 客户编号
     */
    @ApiModelProperty("传员工工号/返回负责人工号(必传)")
    private String employeeId;

    @ApiModelProperty(value="主键")
    private Long id;
    /**
     * 客户编号
     */
    @ApiModelProperty(value="客户编号")
    private String customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String customerName;
    /**
     * 国家
     */
    @ApiModelProperty(value="国家")
    private String countryNum;
    /**
     * 省份
     */
    @ApiModelProperty(value="省份")
    private String provinceNum;
    /**
     * 市
     */
    @ApiModelProperty(value="城市")
    private String cityNum;
    /**
     * 区县
     */
    @ApiModelProperty(value="区县")
    private String district;
    /**
     * 可手动输入，默认可选内容：政府、旅游、金融、交通、能源等
     */
    @ApiModelProperty(value="所属行业")
    private String industry;
    /**
     * 关联enterprise_type表enter_id
     */
    @ApiModelProperty(value="企业性质")
    private Integer enterId;

    /**
     * 企业性质名称
     */
    @ApiModelProperty(value="企业性质名称")
    private String entername;
    /**
     * 是否上市,1上市,2未上市
     */
    @ApiModelProperty(value="是否上市")
    private Integer isListed;
    /**
     * 公司网址
     */
    @ApiModelProperty(value="公司网站")
    private String website;
    /**
     * 详细地址
     */
    @ApiModelProperty(value="具体详细地址")
    private String address;
    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String zipCode;
    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String telephone;
    /**
     * 传真
     */
    @ApiModelProperty(value="传真")
    private String fax;
    /**
     * 联系人
     */
    @ApiModelProperty(value="客户联系人")
    private String contacts;
    /**
     * 所属部门
     */
    @ApiModelProperty(value="客户联系人所属部门")
    private String contactsDepart;

    /**
     * 联系电话
     */
    @ApiModelProperty(value="客户联系人所属电话门")
    private String contactsTelephone;
    /**
     * 联系邮箱
     */
    @ApiModelProperty(value="客户联系邮箱")
    private String contactsEmail;
    /**
     * 操作员,关联sys_user表user_id
     */
    @ApiModelProperty(value="操作员")
    private String operator;
    /**
     * 最后操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="最后操作时间")
    private Date lastTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "时间维度_开始创建时间")
    private Date createStartTime;
    /**
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "时间维度_截至创建时间")
    private Date createEndTime;
    /**
     * 共有人工号,工号逗号分隔，关联sys_employee表employee_id
     */
    @ApiModelProperty(value="共有人工号逗号分隔 ,example=SF10001,SF10002,SF10003")
    private String cowner;
    /**
     * 姓名
     */
    @ApiModelProperty(value="负责人姓名")
    private String employeeName;

//    /**
//     * 部门编号
//     */
//    @ApiModelProperty(value="负责人部门编号 ,example='BM0003'")
//    private String deptId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value="负责人部门名称 ,example=产品部")
    private String deptName;

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

    /**
     * 每页个数
     */
    @ApiModelProperty(value = "数据权限部门列表", example = "[BM0006]")
    private List<String> dataDeptIdList;

    private Integer scope = 1;

    /**
     * 查询范围，默认为 1 <br>
     * 查询范围，1：全部；2：大区 ；3自己
     * @return
     */
    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
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

    public String getCowner() {
        return cowner;
    }

    public void setCowner(String cowner) {
        this.cowner = cowner;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCountryNum() {
        return countryNum;
    }

    public void setCountryNum(String countryNum) {
        this.countryNum = countryNum;
    }

    public String getProvinceNum() {
        return provinceNum;
    }

    public void setProvinceNum(String provinceNum) {
        this.provinceNum = provinceNum;
    }

    public String getCityNum() {
        return cityNum;
    }

    public void setCityNum(String cityNum) {
        this.cityNum = cityNum;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getEnterId() {
        return enterId;
    }

    public void setEnterId(Integer enterId) {
        this.enterId = enterId;
    }

    public Integer getIsListed() {
        return isListed;
    }

    public void setIsListed(Integer isListed) {
        this.isListed = isListed;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsDepart() {
        return contactsDepart;
    }

    public void setContactsDepart(String contactsDepart) {
        this.contactsDepart = contactsDepart;
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


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHalfYear() {
        return halfYear;
    }

    public void setHalfYear(String halfYear) {
        this.halfYear = halfYear;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public List<ContactInfo> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactInfo> contactList) {
        this.contactList = contactList;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public List<String> getDataDeptIdList() {
        return dataDeptIdList;
    }

    public void setDataDeptIdList(List<String> dataDeptIdList) {
        this.dataDeptIdList = dataDeptIdList;
    }

    @Override
    public String toString() {
        return "CustomerInfoQueryDTO{" +
                "contactList=" + contactList +
                ", gradeId='" + gradeId + '\'' +
                ", year='" + year + '\'' +
                ", halfYear='" + halfYear + '\'' +
                ", quarter='" + quarter + '\'' +
                ", month='" + month + '\'' +
                ", deptId='" + deptId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", id=" + id +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", countryNum='" + countryNum + '\'' +
                ", provinceNum='" + provinceNum + '\'' +
                ", cityNum='" + cityNum + '\'' +
                ", district='" + district + '\'' +
                ", industry='" + industry + '\'' +
                ", enterId=" + enterId +
                ", entername='" + entername + '\'' +
                ", isListed=" + isListed +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsDepart='" + contactsDepart + '\'' +
                ", contactsTelephone='" + contactsTelephone + '\'' +
                ", contactsEmail='" + contactsEmail + '\'' +
                ", operator='" + operator + '\'' +
                ", lastTime=" + lastTime +
                ", createTime=" + createTime +
                ", createStartTime=" + createStartTime +
                ", createEndTime=" + createEndTime +
                ", cowner='" + cowner + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", page=" + page +
                ", rows=" + rows +
                ", dataDeptIdList=" + dataDeptIdList +
                '}';
    }
}