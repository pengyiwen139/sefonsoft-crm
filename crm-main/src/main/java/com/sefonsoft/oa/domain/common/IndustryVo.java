package com.sefonsoft.oa.domain.common;

import java.util.Objects;

/**
 * @author xielf
 */
public class IndustryVo {


  private Integer id;

  private String industryName;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIndustryName() {
    return industryName;
  }

  public void setIndustryName(String industryName) {
    this.industryName = industryName;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndustryVo that = (IndustryVo) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getIndustryName(), that.getIndustryName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getIndustryName());
  }


}
