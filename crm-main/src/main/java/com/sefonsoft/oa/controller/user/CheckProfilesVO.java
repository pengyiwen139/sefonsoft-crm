package com.sefonsoft.oa.controller.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("确认基础信息")
public class CheckProfilesVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8660534784400658493L;
  
  @ApiModelProperty(hidden = true)
  @JsonIgnore
  private String userId;

  @ApiModelProperty("邮箱")
  @NotNull(message = "邮箱不能为空")
  private String email;
  
  @ApiModelProperty("电话")
  @NotNull(message = "电话号码不能为空")
  private String tel;

  
  
  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the tel
   */
  public String getTel() {
    return tel;
  }

  /**
   * @param tel the tel to set
   */
  public void setTel(String tel) {
    this.tel = tel;
  }
  
  
  
}
