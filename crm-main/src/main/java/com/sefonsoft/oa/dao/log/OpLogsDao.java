package com.sefonsoft.oa.dao.log;

import com.sefonsoft.oa.entity.OpLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OpLogsDao {

    int deleteByPrimaryKey(Integer id);

    int insert(OpLogs record);

    int insertSelective(OpLogs record);

    OpLogs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpLogs record);

    int updateByPrimaryKeyWithBLOBs(OpLogs record);

    int updateByPrimaryKey(OpLogs record);

    List<OpLogs> findAll();

}