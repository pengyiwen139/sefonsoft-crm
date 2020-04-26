package com.sefonsoft.oa.domain.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * op_logs
 *
 * @author
 */
public class OpLogsVo implements Serializable {

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

  private String employeeName;

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


  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OpLogsVo opLogsVo = (OpLogsVo) o;
    return Objects.equals(getId(), opLogsVo.getId()) &&
        Objects.equals(getOpType(), opLogsVo.getOpType()) &&
        Objects.equals(getOpModule(), opLogsVo.getOpModule()) &&
        Objects.equals(getEmployeeId(), opLogsVo.getEmployeeId()) &&
        Objects.equals(getOpResult(), opLogsVo.getOpResult()) &&
        Objects.equals(getOpTime(), opLogsVo.getOpTime()) &&
        Objects.equals(getOpDetail(), opLogsVo.getOpDetail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getOpType(), getOpModule(), getEmployeeId(), getOpResult(), getOpTime(), getOpDetail());
  }

  @Override
  public String toString() {
    return "OpLogsVo{" +
        "id=" + id +
        ", opType=" + opType +
        ", opModule='" + opModule + '\'' +
        ", employeeId='" + employeeId + '\'' +
        ", opResult=" + opResult +
        ", opTime=" + opTime +
        ", opDetail='" + opDetail + '\'' +
        '}';
  }
}