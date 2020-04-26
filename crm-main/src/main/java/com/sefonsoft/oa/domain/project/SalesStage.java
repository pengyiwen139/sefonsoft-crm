package com.sefonsoft.oa.domain.project;

public enum SalesStage {
  
  项目作废0(0),
  初期沟通10(1),
  方案交流30(2),
  客户立项50(3),
  招投标中70(4),
  合同谈判90(5),
  合同落地100(6);
  
  private int code;

  SalesStage(int code) {
    this.code = code;
  }
  
  public int code() {
    return this.code;
  }
  
  @Override
  public String toString() {
    return super.toString() + "%";
  }
}
