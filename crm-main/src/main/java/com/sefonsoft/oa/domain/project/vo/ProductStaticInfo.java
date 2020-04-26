package com.sefonsoft.oa.domain.project.vo;

public class ProductStaticInfo {

  private Integer id;
  
  private String alias;
  
  private String name;
  

  public ProductStaticInfo(Integer id, String alias, String name) {
    super();
    this.id = id;
    this.alias = alias;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  
  
  
}
