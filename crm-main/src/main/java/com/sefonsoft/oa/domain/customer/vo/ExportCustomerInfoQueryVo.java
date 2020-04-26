package com.sefonsoft.oa.domain.customer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.system.utils.StringUtils;

/**
 * @author xielf
 */
@ApiModel(value = "客户查询展示类")
public class ExportCustomerInfoQueryVo implements Serializable {

  private static final long serialVersionUID = -85446373477015119L;


  private Long id;
  /**
   * 客户编号
   */
  private String customerId;

  /**
   * 客户名称
   */
  private String customerName;

  /**
   * 省市区
   */
  private String provincial;

  /**
   * 企业性质名称
   */
  private String enterName;

  /**
   * 行业
   */
  private String industry;

  /**
   * 详细地址
   */
  private String address;


  private String telephone;


  /**
   * 邮编
   */
  private String zipCode;


  /**
   * 公司网址
   */
  private String website;


  private String fax;

  /***
   * =================以下为联系人信息
   */
  
  private Long contactId;

  /**
   * 联系人
   */
  private String contactName;

  /**
   * 性别
   */
  private String gender;


  /**
   * 所属部门
   */
  private String deptName;

  /**
   * 职务
   */
  private String job;

  /**
   * 联系人手机号码
   */
  private String contactTelphone;
  /**
   * 邮箱
   */
  private String contactEmail;

  /**
   * 办公座机
   */
  private String officePlane;

  /**
   * 大学
   */
  private String university;

  /**
   * 主修课
   */
  private String major;
  /**
   * 生日
   */
  private String birthday;

  /**
   * 家庭情况
   */
  private String familyInfo;
  /**
   * 其他
   */
  private String other;

  /**
   * 销售负责人
   */
  private List<EmployeeVo> employeeVoList;
  
  private String operator;


  public String getOfficePlane() {
    return officePlane;
  }

  public void setOfficePlane(String officePlane) {
    this.officePlane = officePlane;
  }

  public List<EmployeeVo> getEmployeeVoList() {
    return employeeVoList;
  }

  public void setEmployeeVoList(List<EmployeeVo> employeeVoList) {
    this.employeeVoList = employeeVoList;
  }

  public static class EmployeeVo {

    String employeeId;
    String employeeName;

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
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
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

  public String getProvincial() {
    return provincial;
  }

  public void setProvincial(String provincial) {
    this.provincial = provincial;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getEnterName() {
    return enterName;
  }

  public void setEnterName(String enterName) {
    this.enterName = enterName;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
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

  public String getContactTelphone() {
    return contactTelphone;
  }

  public void setContactTelphone(String contactTelphone) {
    this.contactTelphone = contactTelphone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
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

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
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

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  @SuppressWarnings("restriction")
  public CustomerInfo toCustomerInfo() {
    
    CustomerInfo ci = new CustomerInfo();
    
    ci.setAddress(this.address);
    
    String[] provs = provincial.split("/");
    ci.setProvinceNum(provs[0].trim());
    ci.setCityNum(provs[1].trim());
    if (provs.length == 3) {
      ci.setDistrict(provs[2].trim());
    }
    
    ContactInfo cti = new ContactInfo();
    
    if (this.birthday != null) {
      cti.setBirthday(StringUtils.correctDatetime(this.birthday).getValue());
    }
    cti.setContactName(this.contactName);
    cti.setContactSex("男".equals(this.gender) ? 1: 2);
    cti.setCustomerId(this.customerId);
    cti.setDeptName(this.deptName);
    cti.setEmail(this.contactEmail);
    cti.setFamilyInfo(this.familyInfo);
    cti.setJob(this.job);
    cti.setMajor(this.major);
    cti.setOfficePlane(this.officePlane);
    cti.setTelphone(this.contactTelphone);
    cti.setOther(this.other);
    cti.setUniversity(this.university);
    ci.setContactList(Arrays.asList(cti));
    
    ci.setContactsDepart(deptName);
    ci.setContactsEmail(contactEmail);
    ci.setContactsTelephone(cti.getTelphone());
    ci.setCountryNum("中国");
    ci.setCustomerName(customerName);
    ci.setDeptName(deptName);
    ci.setEntername(this.enterName);
    ci.setFax(this.fax);
    ci.setIndustry(this.industry);
    ci.setTelephone(this.telephone);
    ci.setWebsite(this.website);
    ci.setZipCode(this.zipCode);
    
    return ci;
  }

  public Long getContactId() {
    return contactId;
  }

  public void setContactId(Long contactId) {
    this.contactId = contactId;
  }


  @Override
  public String toString() {
    return "ExportCustomerInfoQueryVo{" +
        "id=" + id +
        ", customerId='" + customerId + '\'' +
        ", customerName='" + customerName + '\'' +
        ", provincial='" + provincial + '\'' +
        ", enterName='" + enterName + '\'' +
        ", industry='" + industry + '\'' +
        ", address='" + address + '\'' +
        ", telephone='" + telephone + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", website='" + website + '\'' +
        ", fax='" + fax + '\'' +
        ", contactId=" + contactId +
        ", contactName='" + contactName + '\'' +
        ", gender='" + gender + '\'' +
        ", deptName='" + deptName + '\'' +
        ", job='" + job + '\'' +
        ", contactTelphone='" + contactTelphone + '\'' +
        ", contactEmail='" + contactEmail + '\'' +
        ", officePlane='" + officePlane + '\'' +
        ", university='" + university + '\'' +
        ", major='" + major + '\'' +
        ", birthday='" + birthday + '\'' +
        ", familyInfo='" + familyInfo + '\'' +
        ", other='" + other + '\'' +
        ", employeeVoList=" + employeeVoList +
        ", operator='" + operator + '\'' +
        '}';
  }
}