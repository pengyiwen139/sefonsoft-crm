package com.sefonsoft.oa.dao.project;


import java.sql.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.sefonsoft.oa.entity.project.ProjectAcquireLog;

@Mapper
public interface ProjectAcquireCountDao {

  /**
   * 新增认领次数
   */
  @Update(
      "  update project_acquire_count set " + 
      "    acquire_count = case when acquire_time < #{date} then 1 else acquire_count + 1 end," + 
      "    acquire_time = #{date}" + 
      "  where" + 
      "    employee_id = #{empId}" + 
      "    AND case when acquire_time < #{date} then true else acquire_count < #{max} end")
  @ResultType(value = Integer.class)
  boolean acquireOne(@Param("empId") String empId, @Param("date") Date date, @Param("max") int max);
  
  
  /**
   * 新增申请，如果数据库已有此人记录，则什么都不做
   */
  @Insert("insert ignore into project_acquire_count(employee_id, acquire_count, acquire_time) values (#{log.employeeId}, #{log.acquireCount}, #{log.acquireTime})")
  boolean insertIgnore(@Param("log") ProjectAcquireLog log);
  
}
