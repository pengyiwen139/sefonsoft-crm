package com.sefonsoft.oa.domain.customer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * @author xielf
 */
@ApiModel(value = "客户查询展示类")
public class CustomerInfoQueryVo implements Serializable {

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
    
    /**
     * 员工名称
     */
    @ApiModelProperty("员工名称")
    private String employeeName;

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


    @ApiModelProperty(value="操作员")
    private String operatorName;
    /**
     * 最后操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="最后操作时间")
    private Date lastTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间维度_开始创建时间")
    private Date createStartTime;
    /**
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    private List<EmployeeVo> employeeList;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="负责人部门名称 ,example=产品部")
    private List<String> deptNameList;
    
    /**
     * 性别，0：男，1：女
     */
    @ApiModelProperty("性别，0：男；1：女")
    private Integer sex;

    
    @ApiModelProperty("数据来源：0：手动录入；1：导入；2：资源池")
    private Integer importType = 0;

    /**
     * 可编辑
     */
    @ApiModelProperty("针对普通销售是否可编辑：0：不可编辑；1：可编辑；")
    private Integer editable = 0;

    @Data
    public static class EmployeeVo{
        String employeeId;
        String employeeName;
    }


    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public List<EmployeeVo> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeVo> employeeList) {
        this.employeeList = employeeList;
    }

    public List<String> getDeptNameList() {
        return deptNameList;
    }

    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
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

    public Integer getSex() {
      return sex;
    }

    public void setSex(Integer sex) {
      this.sex = sex;
    }

    public String getEmployeeName() {
      return employeeName;
    }

    public void setEmployeeName(String employeeName) {
      this.employeeName = employeeName;
    }

    
    
    

    /**
     * 数据来源：0：手动录入；1：导入；2：资源池
     * @return
     */
    public Integer getImportType() {
      return importType;
    }

    /**
     * 数据来源：0：手动录入；1：导入；2：资源池
     * @param importType
     */
    public void setImportType(Integer importType) {
      this.importType = importType;
    }
    
    

    /**
     * 针对普通销售是否可编辑：0：不可编辑；1：可编辑；
     * @return
     */
    public Integer getEditable() {
      return editable;
    }

    /**
     * 针对普通销售是否可编辑：0：不可编辑；1：可编辑；
     * @param editable
     */
    public void setEditable(Integer editable) {
      this.editable = editable;
    }

}