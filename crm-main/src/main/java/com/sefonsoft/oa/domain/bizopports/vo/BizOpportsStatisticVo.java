package com.sefonsoft.oa.domain.bizopports.vo;


/**
 * 商机统计
 * @author xielf
 */
public class BizOpportsStatisticVo {

  private int count;
  private String date;


  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "BizOpportsStatisticVo{" +
        "count=" + count +
        ", date='" + date + '\'' +
        '}';
  }
}
