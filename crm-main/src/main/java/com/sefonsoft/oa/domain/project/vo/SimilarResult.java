package com.sefonsoft.oa.domain.project.vo;

import java.util.List;

import com.sefonsoft.oa.domain.project.ProjectSimilarDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("相似检查结果")
public class SimilarResult {
  
  /**
   * 无相似项目
   */
  public static final Integer NONE = 0;
  
  /**
   * 相似
   */
  public static final Integer SIMILAR = 1;
  
  /**
   * 重复
   */
  public static final Integer DUMPLICATE = 2;
  
  @ApiModelProperty("检查结果，0: 查无结果；1：相似；2：重复")
  private int checkResult;
  
  private List<ProjectSimilarDTO> projects;

  public Integer getCheckResult() {
    return checkResult;
  }

  public void setCheckResult(Integer checkResult) {
    this.checkResult = checkResult;
  }

  public List<ProjectSimilarDTO> getProjects() {
    return projects;
  }

  public void setProjects(List<ProjectSimilarDTO> projects) {
    this.projects = projects;
  }
  
}
