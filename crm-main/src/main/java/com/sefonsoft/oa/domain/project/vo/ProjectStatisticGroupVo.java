package com.sefonsoft.oa.domain.project.vo;

import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsVo;

import java.util.List;

/**
 * @author xielf
 */
public class ProjectStatisticGroupVo {


  private List<ProjectStatisticVo> categoryAndData;
  private String name;


  public List<ProjectStatisticVo> getCategoryAndData() {
    return categoryAndData;
  }

  public void setCategoryAndData(List<ProjectStatisticVo> categoryAndData) {
    this.categoryAndData = categoryAndData;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
