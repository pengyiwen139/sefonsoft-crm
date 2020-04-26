package com.sefonsoft.oa.domain.customer.vo;

import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticVo;

import java.util.List;

/**
 * 客户统计
 * @author xielf
 */
public class CustomerStatisticsGroupVo {

  private List<CustomerStatisticsVo> categoryAndData;
  private String name;


  public List<CustomerStatisticsVo> getCategoryAndData() {
    return categoryAndData;
  }

  public void setCategoryAndData(List<CustomerStatisticsVo> categoryAndData) {
    this.categoryAndData = categoryAndData;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
