package com.sefonsoft.oa.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * op_logs
 *
 * @author
 */
public class OpLogs implements Serializable {

  private Integer id;

  /**
   * 操作类型
   * 1 增
   * 2 删
   * 3 改
   * 4 导入
   * 5 导出
   */
  private Short opType;

  /**
   * 模块
   */
  private String opModule;

  /**
   * 操作人
   */
  private String employeeId;

  /**
   * 0失败，1成功
   */
  private Short opResult;

  /**
   * 操作时间
   */
  private Date opTime;

  /**
   * 详细
   */
  private String opDetail;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Short getOpType() {
    return opType;
  }

  public void setOpType(Short opType) {
    this.opType = opType;
  }

  public String getOpModule() {
    return opModule;
  }

  public void setOpModule(String opModule) {
    this.opModule = opModule;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public Short getOpResult() {
    return opResult;
  }

  public void setOpResult(Short opResult) {
    this.opResult = opResult;
  }

  public Date getOpTime() {
    return opTime;
  }

  public void setOpTime(Date opTime) {
    this.opTime = opTime;
  }

  public String getOpDetail() {
    return opDetail;
  }

  public void setOpDetail(String opDetail) {
    this.opDetail = opDetail;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    OpLogs other = (OpLogs) that;
    return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        && (this.getOpType() == null ? other.getOpType() == null : this.getOpType().equals(other.getOpType()))
        && (this.getOpModule() == null ? other.getOpModule() == null : this.getOpModule().equals(other.getOpModule()))
        && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
        && (this.getOpResult() == null ? other.getOpResult() == null : this.getOpResult().equals(other.getOpResult()))
        && (this.getOpTime() == null ? other.getOpTime() == null : this.getOpTime().equals(other.getOpTime()))
        && (this.getOpDetail() == null ? other.getOpDetail() == null : this.getOpDetail().equals(other.getOpDetail()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getOpType() == null) ? 0 : getOpType().hashCode());
    result = prime * result + ((getOpModule() == null) ? 0 : getOpModule().hashCode());
    result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
    result = prime * result + ((getOpResult() == null) ? 0 : getOpResult().hashCode());
    result = prime * result + ((getOpTime() == null) ? 0 : getOpTime().hashCode());
    result = prime * result + ((getOpDetail() == null) ? 0 : getOpDetail().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", opType=").append(opType);
    sb.append(", opModule=").append(opModule);
    sb.append(", employeeId=").append(employeeId);
    sb.append(", opResult=").append(opResult);
    sb.append(", opTime=").append(opTime);
    sb.append(", opDetail=").append(opDetail);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}