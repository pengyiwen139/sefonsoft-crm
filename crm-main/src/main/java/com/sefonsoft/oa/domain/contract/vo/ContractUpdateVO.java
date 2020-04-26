package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.contract.ContractAcceptCondition;
import com.sefonsoft.oa.domain.contract.ContractType;
import com.sefonsoft.oa.entity.contract.ContractInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 更新合同 VO
 */
@ApiModel("更新合同VO")
public class ContractUpdateVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9062824170807887604L;

  /**
   * 合同名称
   */
  @ApiModelProperty("合同名称")
  @NotEmpty(message = "合同名称不能为空")
  private String contractName;

  /**
   * 签订日期
   */
  @ApiModelProperty("签订日期")
  @NotNull(message = "签订日期不能为空")
  @JsonFormat(timezone = "GMT+8", pattern = "")
  private Date contractDate;

  /**
   * 销售负责人工号
   */
  @ApiModelProperty("销售负责人工号")
  @NotEmpty(message = "销售负责人工号不能为空")
  private String employeeId;

  /**
   * 客户编号
   */
  @ApiModelProperty("客户编号")
  @NotEmpty(message = "客户编号不能为空")
  private String customerId;

  /**
   * 所属行业
   */
  @ApiModelProperty("所属行业 ")
  @NotEmpty(message = "所属行业 不能为空")
  private String industry;

  /**
   * 地址
   */
  @ApiModelProperty("地址")
  @NotEmpty(message = "地址不能为空")
  private String address;

  /**
   * 最终客户
   */
  @ApiModelProperty("最终客户")
  @NotEmpty(message = "最终客户不能为空")
  private String finalCustomerId;

  /**
   * 产品类别
   */
  @ApiModelProperty("产品类别")
  @NotEmpty(message = "产品类别不能为空")
  private String productType;

  /**
   * 立项编号
   */
  @ApiModelProperty("立项编号")
  @NotEmpty(message = "立项编号不能为空")
  private String projectId;

  /**
   * 验收条件 固定选项为合同签订、到货验收单、验收报告、初验报告、终验报告、安装调试报告
   */
  @ApiModelProperty("验收条件")
  @NotNull(message = "验收条件不能为空")
  private ContractAcceptCondition acceptCondition;

  /**
   * 是否背靠背 否、是
   */
  @ApiModelProperty("是否背靠背")
  @NotNull(message = "是否背靠背不能为空")
  private Boolean b2b;

  /**
   * 合同性质 渠道，直签
   */
  @ApiModelProperty("合同性质")
  @NotNull(message = "合同性质不能为空")
  private ContractType contractType;
  
  
  /**
   * 销售信息
   */
  @NotEmpty(message = "销售信息不能为空")
  @Valid
  private List<ContractSaleInsertVO> sales;
  
  /**
   * 产品信息
   */
  @NotEmpty(message = "产品信息不能为空")
  @Valid
  private List<ContractProductInsertVO> products;
  
  
  /**
   * 收款比例
   */
  @NotEmpty(message = "收款比例不能为空")
  @Valid
  private List<ContractPayStageInsertVO> payStages;

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

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
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
   * @return the sales
   */
  public List<ContractSaleInsertVO> getSales() {
    return sales;
  }

  /**
   * @param sales the sales to set
   */
  public void setSales(List<ContractSaleInsertVO> sales) {
    this.sales = sales;
  }

  /**
   * @return the products
   */
  public List<ContractProductInsertVO> getProducts() {
    return products;
  }

  /**
   * @param products the products to set
   */
  public void setProducts(List<ContractProductInsertVO> products) {
    this.products = products;
  }

  /**
   * @return the payStages
   */
  public List<ContractPayStageInsertVO> getPayStages() {
    return payStages;
  }

  /**
   * @param payStages the payStages to set
   */
  public void setPayStages(List<ContractPayStageInsertVO> payStages) {
    this.payStages = payStages;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((acceptCondition == null) ? 0 : acceptCondition.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((b2b == null) ? 0 : b2b.hashCode());
    result = prime * result + ((contractDate == null) ? 0 : contractDate.hashCode());
    result = prime * result + ((contractName == null) ? 0 : contractName.hashCode());
    result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
    result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
    result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    result = prime * result + ((finalCustomerId == null) ? 0 : finalCustomerId.hashCode());
    result = prime * result + ((industry == null) ? 0 : industry.hashCode());
    result = prime * result + ((productType == null) ? 0 : productType.hashCode());
    result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ContractUpdateVO other = (ContractUpdateVO) obj;
    if (acceptCondition == null) {
      if (other.acceptCondition != null)
        return false;
    } else if (!acceptCondition.equals(other.acceptCondition))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (b2b == null) {
      if (other.b2b != null)
        return false;
    } else if (!b2b.equals(other.b2b))
      return false;
    if (contractDate == null) {
      if (other.contractDate != null)
        return false;
    } else if (!contractDate.equals(other.contractDate))
      return false;
    if (contractName == null) {
      if (other.contractName != null)
        return false;
    } else if (!contractName.equals(other.contractName))
      return false;
    if (contractType == null) {
      if (other.contractType != null)
        return false;
    } else if (!contractType.equals(other.contractType))
      return false;
    if (customerId == null) {
      if (other.customerId != null)
        return false;
    } else if (!customerId.equals(other.customerId))
      return false;
    if (employeeId == null) {
      if (other.employeeId != null)
        return false;
    } else if (!employeeId.equals(other.employeeId))
      return false;
    if (finalCustomerId == null) {
      if (other.finalCustomerId != null)
        return false;
    } else if (!finalCustomerId.equals(other.finalCustomerId))
      return false;
    if (industry == null) {
      if (other.industry != null)
        return false;
    } else if (!industry.equals(other.industry))
      return false;
    if (productType == null) {
      if (other.productType != null)
        return false;
    } else if (!productType.equals(other.productType))
      return false;
    if (projectId == null) {
      if (other.projectId != null)
        return false;
    } else if (!projectId.equals(other.projectId))
      return false;
    return true;
  }

  public ContractInfo toContractInfo() {
    
    ContractInfo ci = new ContractInfo();
    BeanUtils.copyProperties(this, ci);
    return ci;
  }

}
