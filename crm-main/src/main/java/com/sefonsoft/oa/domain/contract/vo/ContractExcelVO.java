package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.BeanUtils;

import com.sefonsoft.oa.entity.contract.ContractInfo;

/**
 * 导入导出合同 
 */
public class ContractExcelVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9062824170807887604L;

  
  /**
   * 合同编号 
   */
  @NotEmpty(message = "合同编号不能为空")
  private String contractId;
  
  /**
   * 合同名称
   */
  @NotEmpty(message = "合同名称不能为空")
  private String contractName;
  
  
  

  public ContractInfo toContractInfo() {
    ContractInfo inf = new ContractInfo();
    BeanUtils.copyProperties(this, inf);
    return inf;
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

  
  
  
  

}
