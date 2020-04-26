package com.sefonsoft.oa.domain.project;

import java.util.Arrays;

public enum CustomerStage {

  
  项目规划(1),
  方案设计(2),
  客户申报立项(3),
  客户方已立项成功(4),
  招标准备(5),
  招标过程(6),
  中标公示(7),
  合同流程(8),
  合同已签项目实施(9);
  
  private final int code;
  
  private CustomerStage(int code) {
    this.code = code;
  }
  
  public int code() {
    return code;
  }
  
  
}
