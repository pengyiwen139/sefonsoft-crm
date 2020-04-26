package com.sefonsoft.oa.entity.project;

import java.io.Serializable;
import java.sql.Date;

/**
 *认领资源池日志
 */
public class ProjectAcquireLog implements Serializable {

  private static final long serialVersionUID = 7695475107516650395L;

  /**
   * 认领人
   */
  private String employeeId;
  
  /**
   * 认领次数
   */
  private Integer acquireCount;
  
  /**
   * 认领时间
   */
  private Date acquireTime;
  
  

  public ProjectAcquireLog() {
  }
  
  public ProjectAcquireLog(String employeeId, Integer acquireCount, Date acquireTime) {
    super();
    this.employeeId = employeeId;
    this.acquireCount = acquireCount;
    this.acquireTime = acquireTime;
  }


  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public Date getAcquireTime() {
    return acquireTime;
  }

  public void setAcquireTime(Date acquireTime) {
    this.acquireTime = acquireTime;
  }

  public Integer getAcquireCount() {
    return acquireCount;
  }

  public void setAcquireCount(Integer acquireCount) {
    this.acquireCount = acquireCount;
  }
  
  
}
