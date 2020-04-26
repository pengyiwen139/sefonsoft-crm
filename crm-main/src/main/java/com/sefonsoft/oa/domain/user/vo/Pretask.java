package com.sefonsoft.oa.domain.user.vo;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("前置任务")
public class Pretask implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8628199023353624331L;
  
  @ApiModelProperty(hidden = true)
  @JsonIgnore
  private String userId;

  @ApiModelProperty("任务类型")
  private PretaskType taskType;
  
  @ApiModelProperty("阻塞任务")
  private boolean block;
  
  @ApiModelProperty("扩展参数")
  private String args;
  
  /**
   * @return the taskType
   */
  public PretaskType getTaskType() {
    return taskType;
  }

  /**
   * @param taskType the taskType to set
   */
  public void setTaskType(PretaskType taskType) {
    this.taskType = taskType;
  }

  /**
   * @return the args
   */
  public String getArgs() {
    return args;
  }

  /**
   * @param args the args to set
   */
  public void setArgs(String args) {
    this.args = args;
  }

  /**
   * @return the block
   */
  public boolean isBlock() {
    return block;
  }

  /**
   * @param block the block to set
   */
  public void setBlock(boolean block) {
    this.block = block;
  }

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

  
  
}
