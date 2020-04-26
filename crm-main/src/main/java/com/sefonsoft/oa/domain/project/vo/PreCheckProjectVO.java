package com.sefonsoft.oa.domain.project.vo;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sefonsoft.oa.domain.project.ProductProjectRefInsertDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("预检查传参")
public class PreCheckProjectVO implements Serializable {

  private static final long serialVersionUID = 2984581359714503618L;

  /**
   * 项目编号
   */
  @ApiModelProperty(value = "项目编号", example = "如果是更新项目请传项目ID，其他传 NULL")
  private String projectId;
  
  /**
   * 项目名称
   */
  @Size(min = 1, max = 64, message = "项目名称" + MAX_PARAM + ",最大长度为64")
  @NotBlank(message = "项目名称" + NO_PARAM)
  @ApiModelProperty(value = "项目名称", example = "测试项目名称1")
  private String projectName;

  /**
   * 客户编号,索引，关联customer_info表customer_id
   */
  @NotNull(message = "客户编号" + NO_PARAM)
  @Size(max = 16, message = "客户编号" + MAX_PARAM + ",最大长度为16")
  @ApiModelProperty(value = "客户编号,关联customer_info表customer_id", example = "KH201911290001")
  private String customerId;

  @NotNull(message = "产品信息" + NO_PARAM)
  @ApiModelProperty("添加项目所用的产品信息")
  private List<ProductProjectRefInsertDTO> productRefInsertDTOList;

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public List<ProductProjectRefInsertDTO> getProductRefInsertDTOList() {
    return productRefInsertDTOList;
  }

  public void setProductRefInsertDTOList(List<ProductProjectRefInsertDTO> productRefInsertDTOList) {
    this.productRefInsertDTOList = productRefInsertDTOList;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
  
  
  
}
