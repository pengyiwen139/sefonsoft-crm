package com.sefonsoft.oa.domain.importexport;

import java.io.Serializable;

/**
 * 导入结果
 */
public class ImportErrorMessage implements Serializable {

  private static final long serialVersionUID = -4749281538527701272L;

  /**
   * Sheet
   */
  private String sheet;
  
  /**
   * 行
   */
  private Integer row;
  
  /**
   * 错误信息
   */
  private String message;

  public String getSheet() {
    return sheet;
  }

  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

  public Integer getRow() {
    return row;
  }

  public void setRow(Integer row) {
    this.row = row;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  
  @Override
  public String toString() {
    return String.format("无法添加 Sheet：%s，Row：%s 记录，ERROR：%s",sheet, row,message);
  }
  
  
}
