package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同详情 VO
 */
@ApiModel("合同详情")
public class ContractDetailVO implements Serializable {

  

  /**
   * 
   */
  private static final long serialVersionUID = -1818773242917835805L;
  
  // 项目信息
  /**
   * 立项编号
   */
  @ApiModelProperty("立项编号")
  private String projectId;
  
  /**
   * 立项名称
   */
  @ApiModelProperty("立项名称")
  private String projectName;
  
  /**
   * 项目大区ID
   */
  @ApiModelProperty("项目大区ID")
  private String projectEmployeeDeptId;
  
  /**
   * 项目大区名称
   */
  @ApiModelProperty("项目大区名称")
  private String projectEmployeeDeptName;
  
  /**
   * 项目负责人工号
   */
  @ApiModelProperty("项目负责人工号")
  private String projectEmployeeId;
  
  /**
   * 项目负责人名称
   */
  @ApiModelProperty("项目负责人名称")
  private String projectEmployeeName;
  

  /**
   * 客户联系人PK
   */
  @ApiModelProperty("客户联系人ID")
  private Long customerContactId;
  
  /**
   * 客户编号
   */
  @ApiModelProperty("客户编号")
  private String customerId;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;
  
  /**
   * 客户联系人名称
   */
  @ApiModelProperty("客户联系人名称")
  private String customerContactName;
  
  /**
   * 客户联系人电话
   */
  @ApiModelProperty("客户联系人电话")
  private String customerContactTelephone;
  
  /**
   * 客户联系人职位
   */
  @ApiModelProperty("客户联系人职位")
  private String customerContactJob;
  
  
  /**
   * 最终客户联系人
   */
  @ApiModelProperty("最终客户联系人ID")
  private Long finalCustomerContactId;
  
  /**
   * 最终客户名称
   */
  @ApiModelProperty("最终客户编号")
  private String finalCustomerId;
  
  /**
   * 最终客户名称
   */
  @ApiModelProperty("最终客户名称")
  private String finalCustomerName;
  
  /**
   * 最终客户联系人名称
   */
  @ApiModelProperty("最终客户联系人名称")
  private String finalCustomerContactName;
  
  /**
   * 最终客户联系人电话
   */
  @ApiModelProperty("最终客户联系人电话")
  private String finalCustomerContactTelephone;
  
  /**
   * 最终客户联系人职位
   */
  @ApiModelProperty("最终客户联系人职位")
  private String finalCustomerContactJob;
  
  
  /**
   * 合同编号
   */
  @ApiModelProperty("合同编号")
  private String contractId;

  /**
   * 合同名称
   */
  @ApiModelProperty("合同名称")
  private String contractName;

  /**
   * 签订日期
   */
  @ApiModelProperty("签订日期")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date contractDate;

  /**
   * 部门编号
   */
  @ApiModelProperty("部门编号")
  private String deptId;
  
  /**
   * 部门名称
   */
  @ApiModelProperty("部门名称")
  private String deptName;
  
  /**
   * 销售负责人工号
   */
  @ApiModelProperty("销售负责人工号")
  private String employeeId;

  /**
   * 销售负责人名称
   */
  @ApiModelProperty("销售负责人名称")
  private String employeeName;

  
  /**
   * 合同性质
   */
  @ApiModelProperty("合同性质")
  private String contractType;
  
  /**
   * 验收条件
   */
  @ApiModelProperty("验收条件")
  private String acceptCondition;
  
  
  /**
   * 产品类别
   */
  @ApiModelProperty("产品类别")
  private String productType;
  
  /**
   * 行业
   */
  @ApiModelProperty("行业")
  private String industry;
  
  /**
   * 地址
   */
  @ApiModelProperty("地址")
  private String address;
  
  /**
   * 合同金额
   */
  @ApiModelProperty("合同金额")
  private BigDecimal contractAmount;
  
  /**
   * 回款金额
   */
  @ApiModelProperty("回款金额")
  private BigDecimal payAmount;
  
//  /**
//   * 未回款金额，自动计算
//   */
//  @ApiModelProperty("未回款金额")
//  private BigDecimal unpayAmount;
  
  /**
   * 发票金额
   */
  @ApiModelProperty("发票金额")
  private BigDecimal invoiceAmount;
  
  /**
   * 是否背靠背
   */
  @ApiModelProperty("是否背靠背")
  private boolean b2b;
  
  
  /**
   * 创建人工号
   */
  @ApiModelProperty("创建人工号")
  private String creator;
  
  /**
   * 创建人名称
   */
  @ApiModelProperty("创建人名称")
  private String creatorName;
  
  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date createTime;
  
  /**
   * 修改人工号
   */
  @ApiModelProperty("修改人工号")
  private String operator;
  
  /**
   * 修改人名称
   */
  @ApiModelProperty("修改人名称")
  private String operatorName;
  
  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date lastTime;

  
  
  /**
   * @return the finalCustomerId
   */
  public String getFinalCustomerId() {
    return finalCustomerId;
  }

  /**
   * @param finalCustomerId the finalCustomerId to set
   */
  public void setFinalCustomerId(String finalCustomerId) {
    this.finalCustomerId = finalCustomerId;
  }

  /**
   * @return the contractId
   */
  public String getContractId() {
    return contractId;
  }

  /**
   * @param contractId the contractId to set
   */
  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  /**
   * @return the contractName
   */
  public String getContractName() {
    return contractName;
  }

  /**
   * @param contractName the contractName to set
   */
  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  /**
   * @return the contractDate
   */
  public Date getContractDate() {
    return contractDate;
  }

  /**
   * @param contractDate the contractDate to set
   */
  public void setContractDate(Date contractDate) {
    this.contractDate = contractDate;
  }

  /**
   * @return the deptId
   */
  public String getDeptId() {
    return deptId;
  }

  /**
   * @param deptId the deptId to set
   */
  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  /**
   * @return the deptName
   */
  public String getDeptName() {
    return deptName;
  }

  /**
   * @param deptName the deptName to set
   */
  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  /**
   * @return the employeeId
   */
  public String getEmployeeId() {
    return employeeId;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @return the employeeName
   */
  public String getEmployeeName() {
    return employeeName;
  }

  /**
   * @param employeeName the employeeName to set
   */
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the customerId
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * @param customerId the customerId to set
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * @return the productType
   */
  public String getProductType() {
    return productType;
  }

  /**
   * @param productType the productType to set
   */
  public void setProductType(String productType) {
    this.productType = productType;
  }

  /**
   * @return the projectId
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @param projectId the projectId to set
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  /**
   * @return the contractAmount
   */
  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  /**
   * @param contractAmount the contractAmount to set
   */
  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

  /**
   * @return the payAmount
   */
  public BigDecimal getPayAmount() {
    return payAmount;
  }

  /**
   * @param payAmount the payAmount to set
   */
  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
  }

  /**
   * @return the unpayAmount
   */
  public BigDecimal getUnpayAmount() {
    
    BigDecimal ia = contractAmount == null ? BigDecimal.ZERO : contractAmount;
    BigDecimal pa = payAmount == null ? BigDecimal.ZERO : payAmount;
    
    return ia.subtract(pa);
  }

//  /**
//   * @param unpayAmount the unpayAmount to set
//   */
//  public void setUnpayAmount(BigDecimal unpayAmount) {
//    this.unpayAmount = unpayAmount;
//  }

  /**
   * @return the invoiceAmount
   */
  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  /**
   * @param invoiceAmount the invoiceAmount to set
   */
  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
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
   * @return the creatorName
   */
  public String getCreatorName() {
    return creatorName;
  }

  /**
   * @param creatorName the creatorName to set
   */
  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
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
   * @return the operator
   */
  public String getOperator() {
    return operator;
  }

  /**
   * @param operator the operator to set
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * @return the operatorName
   */
  public String getOperatorName() {
    return operatorName;
  }

  /**
   * @param operatorName the operatorName to set
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
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

  /**
   * @return the projectName
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * @param projectName the projectName to set
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * @return the projectEmployeeDeptId
   */
  public String getProjectEmployeeDeptId() {
    return projectEmployeeDeptId;
  }

  /**
   * @param projectEmployeeDeptId the projectEmployeeDeptId to set
   */
  public void setProjectEmployeeDeptId(String projectEmployeeDeptId) {
    this.projectEmployeeDeptId = projectEmployeeDeptId;
  }

  /**
   * @return the projectEmployeeDeptName
   */
  public String getProjectEmployeeDeptName() {
    return projectEmployeeDeptName;
  }

  /**
   * @param projectEmployeeDeptName the projectEmployeeDeptName to set
   */
  public void setProjectEmployeeDeptName(String projectEmployeeDeptName) {
    this.projectEmployeeDeptName = projectEmployeeDeptName;
  }

  /**
   * @return the projectEmployeeId
   */
  public String getProjectEmployeeId() {
    return projectEmployeeId;
  }

  /**
   * @param projectEmployeeId the projectEmployeeId to set
   */
  public void setProjectEmployeeId(String projectEmployeeId) {
    this.projectEmployeeId = projectEmployeeId;
  }

  /**
   * @return the projectEmployeeName
   */
  public String getProjectEmployeeName() {
    return projectEmployeeName;
  }

  /**
   * @param projectEmployeeName the projectEmployeeName to set
   */
  public void setProjectEmployeeName(String projectEmployeeName) {
    this.projectEmployeeName = projectEmployeeName;
  }

  /**
   * @return the cpk
   */
  public Long getCustomerContactId() {
    return customerContactId;
  }

  /**
   * @param cpk the cpk to set
   */
  public void setCustomerContactId(Long cpk) {
    this.customerContactId = cpk;
  }

  /**
   * @return the customerContactName
   */
  public String getCustomerContactName() {
    return customerContactName;
  }

  /**
   * @param customerContactName the customerContactName to set
   */
  public void setCustomerContactName(String customerContactName) {
    this.customerContactName = customerContactName;
  }

  /**
   * @return the customerContactTelephone
   */
  public String getCustomerContactTelephone() {
    return customerContactTelephone;
  }

  /**
   * @param customerContactTelephone the customerContactTelephone to set
   */
  public void setCustomerContactTelephone(String customerContactTelephone) {
    this.customerContactTelephone = customerContactTelephone;
  }

  /**
   * @return the customerContactJob
   */
  public String getCustomerContactJob() {
    return customerContactJob;
  }

  /**
   * @param customerContactJob the customerContactJob to set
   */
  public void setCustomerContactJob(String customerContactJob) {
    this.customerContactJob = customerContactJob;
  }

  /**
   * @return the fcpk
   */
  public Long getFinalCustomerContactId() {
    return finalCustomerContactId;
  }

  /**
   * @param fcpk the fcpk to set
   */
  public void setFinalCustomerContactId(Long finalCustomerContactId) {
    this.finalCustomerContactId = finalCustomerContactId;
  }

  /**
   * @return the finalCustomerName
   */
  public String getFinalCustomerName() {
    return finalCustomerName;
  }

  /**
   * @param finalCustomerName the finalCustomerName to set
   */
  public void setFinalCustomerName(String finalCustomerName) {
    this.finalCustomerName = finalCustomerName;
  }

  /**
   * @return the finalCustomerContactName
   */
  public String getFinalCustomerContactName() {
    return finalCustomerContactName;
  }

  /**
   * @param finalCustomerContactName the finalCustomerContactName to set
   */
  public void setFinalCustomerContactName(String finalCustomerContactName) {
    this.finalCustomerContactName = finalCustomerContactName;
  }

  /**
   * @return the finalCustomerContactTelephone
   */
  public String getFinalCustomerContactTelephone() {
    return finalCustomerContactTelephone;
  }

  /**
   * @param finalCustomerContactTelephone the finalCustomerContactTelephone to set
   */
  public void setFinalCustomerContactTelephone(String finalCustomerContactTelephone) {
    this.finalCustomerContactTelephone = finalCustomerContactTelephone;
  }

  /**
   * @return the finalCustomerContactJob
   */
  public String getFinalCustomerContactJob() {
    return finalCustomerContactJob;
  }

  /**
   * @param finalCustomerContactJob the finalCustomerContactJob to set
   */
  public void setFinalCustomerContactJob(String finalCustomerContactJob) {
    this.finalCustomerContactJob = finalCustomerContactJob;
  }

  /**
   * @return the contractType
   */
  public String getContractType() {
    return contractType;
  }

  /**
   * @param contractType the contractType to set
   */
  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  /**
   * @return the acceptCondition
   */
  public String getAcceptCondition() {
    return acceptCondition;
  }

  /**
   * @param acceptCondition the acceptCondition to set
   */
  public void setAcceptCondition(String acceptCondition) {
    this.acceptCondition = acceptCondition;
  }

  /**
   * @return the industry
   */
  public String getIndustry() {
    return industry;
  }

  /**
   * @param industry the industry to set
   */
  public void setIndustry(String industry) {
    this.industry = industry;
  }
  
  

  /**
   * @return the b2b
   */
  public boolean isB2b() {
    return b2b;
  }

  /**
   * @param b2b the b2b to set
   */
  public void setB2b(boolean b2b) {
    this.b2b = b2b;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((acceptCondition == null) ? 0 : acceptCondition.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((contractAmount == null) ? 0 : contractAmount.hashCode());
    result = prime * result + ((contractDate == null) ? 0 : contractDate.hashCode());
    result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
    result = prime * result + ((contractName == null) ? 0 : contractName.hashCode());
    result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
    result = prime * result + ((customerContactId == null) ? 0 : customerContactId.hashCode());
    result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
    result = prime * result + ((creator == null) ? 0 : creator.hashCode());
    result = prime * result + ((creatorName == null) ? 0 : creatorName.hashCode());
    result = prime * result + ((customerContactJob == null) ? 0 : customerContactJob.hashCode());
    result = prime * result + ((customerContactName == null) ? 0 : customerContactName.hashCode());
    result = prime * result + ((customerContactTelephone == null) ? 0 : customerContactTelephone.hashCode());
    result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
    result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
    result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
    result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
    result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
    result = prime * result + ((finalCustomerContactId == null) ? 0 : finalCustomerContactId.hashCode());
    result = prime * result + ((finalCustomerContactJob == null) ? 0 : finalCustomerContactJob.hashCode());
    result = prime * result + ((finalCustomerContactName == null) ? 0 : finalCustomerContactName.hashCode());
    result = prime * result + ((finalCustomerContactTelephone == null) ? 0 : finalCustomerContactTelephone.hashCode());
    result = prime * result + ((finalCustomerName == null) ? 0 : finalCustomerName.hashCode());
    result = prime * result + ((industry == null) ? 0 : industry.hashCode());
    result = prime * result + ((invoiceAmount == null) ? 0 : invoiceAmount.hashCode());
    result = prime * result + ((lastTime == null) ? 0 : lastTime.hashCode());
    result = prime * result + ((operator == null) ? 0 : operator.hashCode());
    result = prime * result + ((operatorName == null) ? 0 : operatorName.hashCode());
    result = prime * result + ((payAmount == null) ? 0 : payAmount.hashCode());
    result = prime * result + ((productType == null) ? 0 : productType.hashCode());
    result = prime * result + ((projectEmployeeDeptId == null) ? 0 : projectEmployeeDeptId.hashCode());
    result = prime * result + ((projectEmployeeDeptName == null) ? 0 : projectEmployeeDeptName.hashCode());
    result = prime * result + ((projectEmployeeId == null) ? 0 : projectEmployeeId.hashCode());
    result = prime * result + ((projectEmployeeName == null) ? 0 : projectEmployeeName.hashCode());
    result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ContractDetailVO other = (ContractDetailVO) obj;
    if (acceptCondition == null) {
      if (other.acceptCondition != null) {
        return false;
      }
    } else if (!acceptCondition.equals(other.acceptCondition)) {
      return false;
    }
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    if (contractAmount == null) {
      if (other.contractAmount != null) {
        return false;
      }
    } else if (!contractAmount.equals(other.contractAmount)) {
      return false;
    }
    if (contractDate == null) {
      if (other.contractDate != null) {
        return false;
      }
    } else if (!contractDate.equals(other.contractDate)) {
      return false;
    }
    if (contractId == null) {
      if (other.contractId != null) {
        return false;
      }
    } else if (!contractId.equals(other.contractId)) {
      return false;
    }
    if (contractName == null) {
      if (other.contractName != null) {
        return false;
      }
    } else if (!contractName.equals(other.contractName)) {
      return false;
    }
    if (contractType == null) {
      if (other.contractType != null) {
        return false;
      }
    } else if (!contractType.equals(other.contractType)) {
      return false;
    }
    if (customerContactId == null) {
      if (other.customerContactId != null) {
        return false;
      }
    } else if (!customerContactId.equals(other.customerContactId)) {
      return false;
    }
    if (createTime == null) {
      if (other.createTime != null) {
        return false;
      }
    } else if (!createTime.equals(other.createTime)) {
      return false;
    }
    if (creator == null) {
      if (other.creator != null) {
        return false;
      }
    } else if (!creator.equals(other.creator)) {
      return false;
    }
    if (creatorName == null) {
      if (other.creatorName != null) {
        return false;
      }
    } else if (!creatorName.equals(other.creatorName)) {
      return false;
    }
    if (customerContactJob == null) {
      if (other.customerContactJob != null) {
        return false;
      }
    } else if (!customerContactJob.equals(other.customerContactJob)) {
      return false;
    }
    if (customerContactName == null) {
      if (other.customerContactName != null) {
        return false;
      }
    } else if (!customerContactName.equals(other.customerContactName)) {
      return false;
    }
    if (customerContactTelephone == null) {
      if (other.customerContactTelephone != null) {
        return false;
      }
    } else if (!customerContactTelephone.equals(other.customerContactTelephone)) {
      return false;
    }
    if (customerId == null) {
      if (other.customerId != null) {
        return false;
      }
    } else if (!customerId.equals(other.customerId)) {
      return false;
    }
    if (customerName == null) {
      if (other.customerName != null) {
        return false;
      }
    } else if (!customerName.equals(other.customerName)) {
      return false;
    }
    if (deptId == null) {
      if (other.deptId != null) {
        return false;
      }
    } else if (!deptId.equals(other.deptId)) {
      return false;
    }
    if (deptName == null) {
      if (other.deptName != null) {
        return false;
      }
    } else if (!deptName.equals(other.deptName)) {
      return false;
    }
    if (employeeId == null) {
      if (other.employeeId != null) {
        return false;
      }
    } else if (!employeeId.equals(other.employeeId)) {
      return false;
    }
    if (employeeName == null) {
      if (other.employeeName != null) {
        return false;
      }
    } else if (!employeeName.equals(other.employeeName)) {
      return false;
    }
    if (finalCustomerContactId == null) {
      if (other.finalCustomerContactId != null) {
        return false;
      }
    } else if (!finalCustomerContactId.equals(other.finalCustomerContactId)) {
      return false;
    }
    if (finalCustomerContactJob == null) {
      if (other.finalCustomerContactJob != null) {
        return false;
      }
    } else if (!finalCustomerContactJob.equals(other.finalCustomerContactJob)) {
      return false;
    }
    if (finalCustomerContactName == null) {
      if (other.finalCustomerContactName != null) {
        return false;
      }
    } else if (!finalCustomerContactName.equals(other.finalCustomerContactName)) {
      return false;
    }
    if (finalCustomerContactTelephone == null) {
      if (other.finalCustomerContactTelephone != null) {
        return false;
      }
    } else if (!finalCustomerContactTelephone.equals(other.finalCustomerContactTelephone)) {
      return false;
    }
    if (finalCustomerName == null) {
      if (other.finalCustomerName != null) {
        return false;
      }
    } else if (!finalCustomerName.equals(other.finalCustomerName)) {
      return false;
    }
    if (industry == null) {
      if (other.industry != null) {
        return false;
      }
    } else if (!industry.equals(other.industry)) {
      return false;
    }
    if (invoiceAmount == null) {
      if (other.invoiceAmount != null) {
        return false;
      }
    } else if (!invoiceAmount.equals(other.invoiceAmount)) {
      return false;
    }
    if (lastTime == null) {
      if (other.lastTime != null) {
        return false;
      }
    } else if (!lastTime.equals(other.lastTime)) {
      return false;
    }
    if (operator == null) {
      if (other.operator != null) {
        return false;
      }
    } else if (!operator.equals(other.operator)) {
      return false;
    }
    if (operatorName == null) {
      if (other.operatorName != null) {
        return false;
      }
    } else if (!operatorName.equals(other.operatorName)) {
      return false;
    }
    if (payAmount == null) {
      if (other.payAmount != null) {
        return false;
      }
    } else if (!payAmount.equals(other.payAmount)) {
      return false;
    }
    if (productType == null) {
      if (other.productType != null) {
        return false;
      }
    } else if (!productType.equals(other.productType)) {
      return false;
    }
    if (projectEmployeeDeptId == null) {
      if (other.projectEmployeeDeptId != null) {
        return false;
      }
    } else if (!projectEmployeeDeptId.equals(other.projectEmployeeDeptId)) {
      return false;
    }
    if (projectEmployeeDeptName == null) {
      if (other.projectEmployeeDeptName != null) {
        return false;
      }
    } else if (!projectEmployeeDeptName.equals(other.projectEmployeeDeptName)) {
      return false;
    }
    if (projectEmployeeId == null) {
      if (other.projectEmployeeId != null) {
        return false;
      }
    } else if (!projectEmployeeId.equals(other.projectEmployeeId)) {
      return false;
    }
    if (projectEmployeeName == null) {
      if (other.projectEmployeeName != null) {
        return false;
      }
    } else if (!projectEmployeeName.equals(other.projectEmployeeName)) {
      return false;
    }
    if (projectId == null) {
      if (other.projectId != null) {
        return false;
      }
    } else if (!projectId.equals(other.projectId)) {
      return false;
    }
    if (projectName == null) {
      if (other.projectName != null) {
        return false;
      }
    } else if (!projectName.equals(other.projectName)) {
      return false;
    }
    return true;
  }
  
  
  
}
