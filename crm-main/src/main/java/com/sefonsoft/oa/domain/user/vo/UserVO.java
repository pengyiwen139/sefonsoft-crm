package com.sefonsoft.oa.domain.user.vo;

import java.util.Collections;
import java.util.List;

import com.sefonsoft.oa.domain.user.LoginUserDTO;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户信息  VO
 */
public class UserVO extends LoginUserDTO {

  /**
   * 
   */
  private static final long serialVersionUID = -6171302304350918522L;

  
  /**
   * 前置任务
   */
  @ApiModelProperty("前置任务")
  private List<Pretask> pretasks = Collections.emptyList();


  /**
   * @return the pretasks
   */
  public List<Pretask> getPretasks() {
    return pretasks;
  }


  /**
   * @param pretasks the pretasks to set
   */
  public void setPretasks(List<Pretask> pretasks) {
    this.pretasks = pretasks;
  }
  
  
}
