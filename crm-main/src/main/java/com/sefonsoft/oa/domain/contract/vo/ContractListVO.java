package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同列表 VO
 */
@ApiModel("合同列表 VO")
public class ContractListVO implements Serializable {

  
  /**
   * 
   */
  private static final long serialVersionUID = -5817286706597570609L;

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
   * 地址
   */
  @ApiModelProperty("地址")
  private String address;
  

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
   * 产品类别
   */
  @ApiModelProperty("产品类别")
  private String productType;

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
  
  /**
   * 发票金额
   */
  @ApiModelProperty("发票金额")
  private BigDecimal invoiceAmount;

  /**
   * 操作员
   */
  @ApiModelProperty("操作员工号")
  private String operator;
  
  /**
   * 操作员名称
   */
  @ApiModelProperty("操作员名称")
  private String operatorName;
  
  @ApiModelProperty("操作时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date lastTime;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((contractAmount == null) ? 0 : contractAmount.hashCode());
    result = prime * result + ((contractDate == null) ? 0 : contractDate.hashCode());
    result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
    result = prime * result + ((contractName == null) ? 0 : contractName.hashCode());
    result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
    result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
    result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
    result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
    result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
    result = prime * result + ((invoiceAmount == null) ? 0 : invoiceAmount.hashCode());
    result = prime * result + ((lastTime == null) ? 0 : lastTime.hashCode());
    result = prime * result + ((operator == null) ? 0 : operator.hashCode());
    result = prime * result + ((operatorName == null) ? 0 : operatorName.hashCode());
    result = prime * result + ((payAmount == null) ? 0 : payAmount.hashCode());
    result = prime * result + ((productType == null) ? 0 : productType.hashCode());
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
    ContractListVO other = (ContractListVO) obj;
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
    return true;
  }

 
  
}
