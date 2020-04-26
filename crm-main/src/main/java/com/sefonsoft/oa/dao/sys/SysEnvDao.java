package com.sefonsoft.oa.dao.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sefonsoft.oa.entity.sys.SysEnvEntity;

@Mapper
public interface SysEnvDao {

  @Select("select * from sys_env")
  public List<SysEnvEntity> getAll();

  @Insert("insert ignore sys_env(config_name, config_value, default_value, config_des) "
      + "value(#{name}, #{value}, #{defaultValue}, #{desc})")
  public boolean insertIngore(
      @Param("name") String name,
      @Param("value") String value, 
      @Param("defaultValue") String defaultValue, 
      @Param("desc") String desc);
  
  @Update("update sys_env set config_value = #{value} where config_name = #{name}")
  public boolean update(@Param("name") String name, @Param("value") String value);

  @Select("select config_name, config_value from sys_env where config_name = #{name}")
  public Map<String, String> getConfig(@Param("name") String name);
}