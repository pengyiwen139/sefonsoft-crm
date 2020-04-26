package com.sefonsoft.oa.domain.statistic;

/**
 * @author xielf
 *
 * 键值对
 *
 */
public class GroupTuple {

  private String value1;
  private String value2;

  private GroupTuple(String v1, String v2) {
    this.value1 = v1;
    this.value2 = v2;
  }

  public static GroupTuple of(String v1, String v2){
    return new GroupTuple(v1,v2);
  }


  public String getValue1() {
    return value1;
  }

  public void setValue1(String value1) {
    this.value1 = value1;
  }

  public String getValue2() {
    return value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }
}
