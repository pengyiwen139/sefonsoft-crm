package com.sefonsoft.oa.domain.project.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.sefonsoft.oa.domain.project.dto.PredictionInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("项目预测统计")
public class ProjectPredictionStatsVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4176281842385146345L;

  
  /**
   * 总数量
   */
  @ApiModelProperty("总数量")
  private Integer totalCount;
  
  /**
   * 总金额
   */
  @ApiModelProperty("总金额")
  private BigDecimal totalEstimateMoney;
  
  /**
   * 按概率
   */
  @ApiModelProperty("按成功率预测信息")
  private List<PredictionInfo> sucessInfo;
  
  /**
   * 按月
   */
  @ApiModelProperty("按月预测信息")
  private List<PredictionInfo> monthInfo;

  /**
   * @return the totalCount
   */
  public Integer getTotalCount() {
    return totalCount;
  }

  /**
   * @param totalCount the totalCount to set
   */
  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  /**
   * @return the totalEstimateMoney
   */
  public BigDecimal getTotalEstimateMoney() {
    return totalEstimateMoney;
  }

  /**
   * @param totalEstimateMoney the totalEstimateMoney to set
   */
  public void setTotalEstimateMoney(BigDecimal totalEstimateMoney) {
    this.totalEstimateMoney = totalEstimateMoney;
  }

  /**
   * @return the sucessInfo
   */
  public List<PredictionInfo> getSucessInfo() {
    return sucessInfo;
  }

  /**
   * @param sucessInfo the sucessInfo to set
   */
  public void setSucessInfo(List<PredictionInfo> sucessInfo) {
    this.sucessInfo = sucessInfo;
  }

  /**
   * @return the monthInfo
   */
  public List<PredictionInfo> getMonthInfo() {
    return monthInfo;
  }

  /**
   * @param monthInfo the monthInfo to set
   */
  public void setMonthInfo(List<PredictionInfo> monthInfo) {
    this.monthInfo = monthInfo;
  }
  
  
}
