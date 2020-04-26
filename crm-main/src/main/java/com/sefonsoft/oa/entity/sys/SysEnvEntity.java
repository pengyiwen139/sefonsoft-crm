package com.sefonsoft.oa.entity.sys;

import java.io.Serializable;

/**
 * 系统配置实体
 */
public class SysEnvEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6291582528814381710L;
  
  /**
   * 配置名称
   */
  private String configName;
  
  /**
   * 配置值
   */
  private String configValue;
  
  /**
   * 默认配置值
   */
  private String defaultValue;
  
  /**
   * 配置描述
   */
  private String configDes;

  public String getConfigName() {
    return configName;
  }

  public void setConfigName(String configName) {
    this.configName = configName;
  }

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getConfigDes() {
    return configDes;
  }

  public void setConfigDes(String configDes) {
    this.configDes = configDes;
  }

}
