package com.sefonsoft.oa.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sefonsoft.oa.domain.contract.ContractAcceptCondition;
import com.sefonsoft.oa.domain.contract.ContractType;

/**
 * contract_info
 *
 * @author
 */
public class ContractInfo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -7220788320809755825L;

  /**
   * 合同编号
   * 格式为【WY+年月日+大区缩写+2位流水】，【WY】为固定前缀，【年月日】为当天日期，【大区】缩写根据选中的销售负责人所在大区的拼音缩写，2位流水号从1开始
   */
  private String contractId;

  /**
   * 合同名称
   */
  private String contractName;

  /**
   * 签订日期
   */
  private Date contractDate;

  /**
   * 员工PK
   */
  private Long employeePk;

  /**
   * 销售负责人工号
   */
  private String employeeId;

  /**
   * 销售负责人名称
   */
  private String employeeName;

  /**
   * 部门编号
   */
  private String deptId;

  /**
   * 客户PK
   */
  private Long customerPk;

  /**
   * 客户编号
   */
  private String customerId;

  /**
   * 客户名称
   */
  private String customerName;
  

  /**
   * 客户联系人编号
   */
  private Long customerContactId;

  

  /**
   * 所属行业
   * 安监,博物,部委,财政,畜牧,传媒,地震,电力,电网,发改委,法院,法制办,防震减灾,港口,工会,工商 ,工信部,公安 ,管委会,广电 ,规划局,国企 ,国土,国资委 ,海关,航空 ,航运,环保,环境,机场 ,纪检委,监狱 ,检察,交通 ,教育,戒毒,金融,经信,军工,勘测,科研,口岸,粮食,林业,旅游,煤监,民政,能源,农业,企业,气象,人社,商委,审计,食药监,水利,水文,水务,税务,司法,体育,统计,卫计委,文旅,武警,物流,消防,烟草,研究院,药监局,医疗,应急,园区,运营商,政法委,政府,质监,住建,组织部,其他
   */
  private String industry;

  /**
   * 地址
   */
  private String address;

  /**
   * 最终客户ID
   */
  private String finalCustomerId;
  

  /**
   * 最终客户联系人编号
   */
  private Long finalCustomerContactId;


  /**
   * 产品类别
   */
  private String productType;

  /**
   * 立项编号
   */
  private String projectId;

  /**
   * 合同金额
   * 单位为万元
   */
  private BigDecimal contractAmount;

  /**
   * 验收条件
   * 固定选项为合同签订、到货验收单、验收报告、初验报告、终验报告、安装调试报告
   */
  private ContractAcceptCondition acceptCondition;

  /**
   * 是否背靠背
   * 否、是
   */
  private Boolean b2b;

  /**
   * 合同性质
   * 渠道，直签
   */
  private ContractType contractType;

  
  /**
   * 创建人工号
   */
  private String creator;
  
  /**
   * 创建时间
   */
  private Date createTime;
  
  /**
   * 修改人工号
   */
  private String operator;
  
  /**
   * 修改时间
   */
  private Date lastTime;
  

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getContractName() {
    return contractName;
  }

  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  public Date getContractDate() {
    return contractDate;
  }

  public void setContractDate(Date contractDate) {
    this.contractDate = contractDate;
  }

  public Long getEmployeePk() {
    return employeePk;
  }

  public void setEmployeePk(Long employeePk) {
    this.employeePk = employeePk;
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

  public Long getCustomerPk() {
    return customerPk;
  }

  public void setCustomerPk(Long customerPk) {
    this.customerPk = customerPk;
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

  public String getFinalCustomerId() {
    return finalCustomerId;
  }

  public void setFinalCustomerId(String finalCustomerId) {
    this.finalCustomerId = finalCustomerId;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

  public ContractAcceptCondition getAcceptCondition() {
    return acceptCondition;
  }

  public void setAcceptCondition(ContractAcceptCondition acceptCondition) {
    this.acceptCondition = acceptCondition;
  }

  public Boolean getB2b() {
    return b2b;
  }

  public void setB2b(Boolean b2b) {
    this.b2b = b2b;
  }

  public ContractType getContractType() {
    return contractType;
  }

  public void setContractType(ContractType contractType) {
    this.contractType = contractType;
  }

  
  
  
  
  /**
   * @return the customerContactId
   */
  public Long getCustomerContactId() {
    return customerContactId;
  }

  /**
   * @param customerContactId the customerContactId to set
   */
  public void setCustomerContactId(Long customerContactId) {
    this.customerContactId = customerContactId;
  }

  /**
   * @return the finalCustomerContactId
   */
  public Long getFinalCustomerContactId() {
    return finalCustomerContactId;
  }

  /**
   * @param finalCustomerContactId the finalCustomerContactId to set
   */
  public void setFinalCustomerContactId(Long finalCustomerContactId) {
    this.finalCustomerContactId = finalCustomerContactId;
  }

  /**
   * @return the creator
   */
  public String getCreator() {
    return creator;
  }

  /**
   * @param creator the creator to set
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @return the modified
   */
  public String getOperator() {
    return operator;
  }

  /**
   * @param modified the modified to set
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * @return the lastTime
   */
  public Date getLastTime() {
    return lastTime;
  }

  /**
   * @param lastTime the lastTime to set
   */
  public void setLastTime(Date lastTime) {
    this.lastTime = lastTime;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    ContractInfo other = (ContractInfo) that;
    return (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
        && (this.getContractName() == null ? other.getContractName() == null : this.getContractName().equals(other.getContractName()))
        && (this.getContractDate() == null ? other.getContractDate() == null : this.getContractDate().equals(other.getContractDate()))
        && (this.getEmployeePk() == null ? other.getEmployeePk() == null : this.getEmployeePk().equals(other.getEmployeePk()))
        && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
        && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
        && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
        && (this.getCustomerPk() == null ? other.getCustomerPk() == null : this.getCustomerPk().equals(other.getCustomerPk()))
        && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
        && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
        && (this.getIndustry() == null ? other.getIndustry() == null : this.getIndustry().equals(other.getIndustry()))
        && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
        && (this.getFinalCustomerId() == null ? other.getFinalCustomerId() == null : this.getFinalCustomerId().equals(other.getFinalCustomerId()))
        && (this.getProductType() == null ? other.getProductType() == null : this.getProductType().equals(other.getProductType()))
        && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
        && (this.getContractAmount() == null ? other.getContractAmount() == null : this.getContractAmount().equals(other.getContractAmount()))
        && (this.getAcceptCondition() == null ? other.getAcceptCondition() == null : this.getAcceptCondition().equals(other.getAcceptCondition()))
        && (this.getB2b() == null ? other.getB2b() == null : this.getB2b().equals(other.getB2b()))
        && (this.getContractType() == null ? other.getContractType() == null : this.getContractType().equals(other.getContractType()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
    result = prime * result + ((getContractName() == null) ? 0 : getContractName().hashCode());
    result = prime * result + ((getContractDate() == null) ? 0 : getContractDate().hashCode());
    result = prime * result + ((getEmployeePk() == null) ? 0 : getEmployeePk().hashCode());
    result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
    result = prime * result + ((getEmployeeName() == null) ? 0 : getEmployeeName().hashCode());
    result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
    result = prime * result + ((getCustomerPk() == null) ? 0 : getCustomerPk().hashCode());
    result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
    result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
    result = prime * result + ((getIndustry() == null) ? 0 : getIndustry().hashCode());
    result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
    result = prime * result + ((getFinalCustomerId() == null) ? 0 : getFinalCustomerId().hashCode());
    result = prime * result + ((getProductType() == null) ? 0 : getProductType().hashCode());
    result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
    result = prime * result + ((getContractAmount() == null) ? 0 : getContractAmount().hashCode());
    result = prime * result + ((getAcceptCondition() == null) ? 0 : getAcceptCondition().hashCode());
    result = prime * result + ((getB2b() == null) ? 0 : getB2b().hashCode());
    result = prime * result + ((getContractType() == null) ? 0 : getContractType().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", contractId=").append(contractId);
    sb.append(", contractName=").append(contractName);
    sb.append(", contractDate=").append(contractDate);
    sb.append(", employeePk=").append(employeePk);
    sb.append(", employeeId=").append(employeeId);
    sb.append(", employeeName=").append(employeeName);
    sb.append(", deptId=").append(deptId);
    sb.append(", customerPk=").append(customerPk);
    sb.append(", customerId=").append(customerId);
    sb.append(", customerName=").append(customerName);
    sb.append(", industry=").append(industry);
    sb.append(", address=").append(address);
    sb.append(", finalCustomerId=").append(finalCustomerId);
    sb.append(", productType=").append(productType);
    sb.append(", projectId=").append(projectId);
    sb.append(", contractAmount=").append(contractAmount);
    sb.append(", acceptCondition=").append(acceptCondition);
    sb.append(", b2b=").append(b2b);
    sb.append(", contractType=").append(contractType);
    sb.append(", creator=").append(creator);
    sb.append(", createTime=").append(createTime);
    sb.append(", operator=").append(operator);
    sb.append(", lastTime=").append(lastTime);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}