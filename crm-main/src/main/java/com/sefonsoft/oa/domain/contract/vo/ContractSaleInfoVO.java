package com.sefonsoft.oa.domain.contract.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractSalesInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("合同销售信息")
public class ContractSaleInfoVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1744640514615332202L;


  /**
   * 销售ID
   */
  @ApiModelProperty("销售工号")
  private String employeeId;

  /**
   * 销售名称
   */
  @ApiModelProperty("销售名称")
  private String employeeName;

  /**
   * 比例
   * 百分整数
   */
  @ApiModelProperty("比例")
  private Integer ratio;

  /**
   * 金额
   * 单位（万元）
   */
  @ApiModelProperty("金额，单位（万元）")
  private BigDecimal amount;


  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public Integer getRatio() {
    return ratio;
  }

  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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
    ContractSalesInfo other = (ContractSalesInfo) that;
    return (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
        && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()))
        && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
    result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
    result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", employeeId=").append(employeeId);
    sb.append(", ratio=").append(ratio);
    sb.append(", amount=").append(amount);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
  
  
  public ContractSalesInfo toContractSalesInfo(ContractInfo ci) {
    ContractSalesInfo csi = new ContractSalesInfo();
    csi.setContractId(ci.getContractId());
    csi.setOperator(ci.getOperator());
    BeanUtils.copyProperties(this, csi);
    return csi;
  }
}
