package com.sefonsoft.oa.system.emun;

/**
 * @author xielf
 */
public enum OnTimeEnum {

  /**/
  ALL(0,"全部"),
  MONTH(1,"月份"),
  QUARTER(2,"季度"),
  YEAR(3,"年份"),
  WEEK(4,"周"),
  ;

  private int code;

  private String description;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }


  OnTimeEnum(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public static OnTimeEnum convert(int code) {
    for (OnTimeEnum value : values()) {
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }
}
