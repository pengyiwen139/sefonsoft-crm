package com.sefonsoft.oa.domain.project.vo;

/**
 * 申请信息
 */
public class ProjectExcelApplyVO {

  /**
   * 项目总情况
   */
  private String projectSituation;
  
  /**
   * 最终用户关系分析
   */
  private String userRelationAnalysis;
  
  /**
   * 参与集成商情况分析
   */
  private String integratorSituation;
  
  /**
   * 竞争对手情况分析
   */
  private String competeOpponentSnalysis;
  
  /**
   * 项目运作策略
   */
  private String projectRunStrategy;

  public String getProjectSituation() {
    return projectSituation;
  }

  public void setProjectSituation(String projectSituation) {
    this.projectSituation = projectSituation;
  }

  public String getUserRelationAnalysis() {
    return userRelationAnalysis;
  }

  public void setUserRelationAnalysis(String userRelationAnalysis) {
    this.userRelationAnalysis = userRelationAnalysis;
  }

  public String getIntegratorSituation() {
    return integratorSituation;
  }

  public void setIntegratorSituation(String integratorSituation) {
    this.integratorSituation = integratorSituation;
  }

  public String getCompeteOpponentSnalysis() {
    return competeOpponentSnalysis;
  }

  public void setCompeteOpponentSnalysis(String competeOpponentSnalysis) {
    this.competeOpponentSnalysis = competeOpponentSnalysis;
  }

  public String getProjectRunStrategy() {
    return projectRunStrategy;
  }

  public void setProjectRunStrategy(String projectRunStrategy) {
    this.projectRunStrategy = projectRunStrategy;
  }

}
