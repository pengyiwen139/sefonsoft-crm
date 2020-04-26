package com.sefonsoft.oa.domain.bizopports.vo;


import java.util.List;

/**
 * 商机统计
 * @author xielf
 */
public class BizOpportsStatisticGroupVo {

  private List<BizOpportsStatisticVo> categoryAndData;
  private String name;


  public List<BizOpportsStatisticVo> getCategoryAndData() {
    return categoryAndData;
  }

  public void setCategoryAndData(List<BizOpportsStatisticVo> categoryAndData) {
    this.categoryAndData = categoryAndData;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "BizOpportsStatisticGroupVo{" +
        "categoryAndData=" + categoryAndData +
        ", name='" + name + '\'' +
        '}';
  }
}
