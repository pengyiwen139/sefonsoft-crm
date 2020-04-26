package com.sefonsoft.oa.domain.project.vo;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.domain.project.ProductProjectRefUpdateDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("销售/大区总更新项目")
public class SalesUpdateVO implements Serializable {
  private static final long serialVersionUID = -6418798758352090951L;
  
  @ApiModelProperty("项目编号")
  private String projectId;

  @ApiModelProperty("合同编号")
  private String contractId;
  
  @ApiModelProperty("合同名称")
  private String contractName;
  
  @ApiModelProperty("合同阶段")
  private String contractStage;

  @NotNull(message = "销售阶段" + NO_PARAM)
  @ApiModelProperty(value = "销售阶段,关联sales_project_stage表spstage_id", example = "1")
  private Integer spstageId;

  @Size(min = 1, max = 32, message = "客户项目阶段" + MAX_PARAM + ",最大长度为32")
  @NotBlank(message = "客户项目阶段" + NO_PARAM)
  @ApiModelProperty(value = "客户项目阶段，填字符串，已申请立项，资金到位，等", example = "已申请立项")
  private String customerProjectPhase;
  
  
  /**
  预估签约时间
  */
 @NotNull(message = "预估签约时间" + NO_PARAM)
 @ApiModelProperty("预估签约时间 yyyy-MM-dd")
 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
 private Date estimateTime;

 /**
  预估成功率，百分比数字
  */
 @Max(value = 100, message = "预估成功率不能超过100%")
 @NotNull(message = "预估成功率" + NO_PARAM)
 @ApiModelProperty(value = "预估成功率，百分比数字，最大100",example = "90")
 private Integer estimateSuccess;
  
 
 @NotNull(message = "产品信息" + NO_PARAM)
 @ApiModelProperty("添加项目所用的产品信息")
 private List<ProductProjectRefUpdateDTO> productRefInsertDTOList;

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

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

  public String getContractStage() {
    return contractStage;
  }

  public void setContractStage(String contractStage) {
    this.contractStage = contractStage;
  }

  public Integer getSpstageId() {
    return spstageId;
  }

  public void setSpstageId(Integer spstageId) {
    this.spstageId = spstageId;
  }

  public String getCustomerProjectPhase() {
    return customerProjectPhase;
  }

  public void setCustomerProjectPhase(String customerProjectPhase) {
    this.customerProjectPhase = customerProjectPhase;
  }

  /**
   * @return the estimateTime
   */
  public Date getEstimateTime() {
    return estimateTime;
  }

  /**
   * @param estimateTime the estimateTime to set
   */
  public void setEstimateTime(Date estimateTime) {
    this.estimateTime = estimateTime;
  }

  /**
   * @return the estimateSuccess
   */
  public Integer getEstimateSuccess() {
    return estimateSuccess;
  }

  /**
   * @param estimateSuccess the estimateSuccess to set
   */
  public void setEstimateSuccess(Integer estimateSuccess) {
    this.estimateSuccess = estimateSuccess;
  }

  /**
   * @return the productRefInsertDTOList
   */
  public List<ProductProjectRefUpdateDTO> getProductRefInsertDTOList() {
    return productRefInsertDTOList;
  }

  /**
   * @param productRefInsertDTOList the productRefInsertDTOList to set
   */
  public void setProductRefInsertDTOList(List<ProductProjectRefUpdateDTO> productRefInsertDTOList) {
    this.productRefInsertDTOList = productRefInsertDTOList;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
    result = prime * result + ((contractName == null) ? 0 : contractName.hashCode());
    result = prime * result + ((contractStage == null) ? 0 : contractStage.hashCode());
    result = prime * result + ((customerProjectPhase == null) ? 0 : customerProjectPhase.hashCode());
    result = prime * result + ((estimateSuccess == null) ? 0 : estimateSuccess.hashCode());
    result = prime * result + ((estimateTime == null) ? 0 : estimateTime.hashCode());
    result = prime * result + ((productRefInsertDTOList == null) ? 0 : productRefInsertDTOList.hashCode());
    result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
    result = prime * result + ((spstageId == null) ? 0 : spstageId.hashCode());
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
    SalesUpdateVO other = (SalesUpdateVO) obj;
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
    if (contractStage == null) {
      if (other.contractStage != null) {
        return false;
      }
    } else if (!contractStage.equals(other.contractStage)) {
      return false;
    }
    if (customerProjectPhase == null) {
      if (other.customerProjectPhase != null) {
        return false;
      }
    } else if (!customerProjectPhase.equals(other.customerProjectPhase)) {
      return false;
    }
    if (estimateSuccess == null) {
      if (other.estimateSuccess != null) {
        return false;
      }
    } else if (!estimateSuccess.equals(other.estimateSuccess)) {
      return false;
    }
    if (estimateTime == null) {
      if (other.estimateTime != null) {
        return false;
      }
    } else if (!estimateTime.equals(other.estimateTime)) {
      return false;
    }
    if (productRefInsertDTOList == null) {
      if (other.productRefInsertDTOList != null) {
        return false;
      }
    } else if (!productRefInsertDTOList.equals(other.productRefInsertDTOList)) {
      return false;
    }
    if (projectId == null) {
      if (other.projectId != null) {
        return false;
      }
    } else if (!projectId.equals(other.projectId)) {
      return false;
    }
    if (spstageId == null) {
      if (other.spstageId != null) {
        return false;
      }
    } else if (!spstageId.equals(other.spstageId)) {
      return false;
    }
    return true;
  }
  
  
  
  
}
