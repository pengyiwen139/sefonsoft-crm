package com.sefonsoft.oa.dao.common;

import com.sefonsoft.oa.domain.common.IndustryVo;
import com.sefonsoft.oa.entity.IndustryInfo;

import java.util.List;

/**
 * @author xielf
 */
public interface IndustryInfoDao {


  int deleteByPrimaryKey(Integer id);

  int insert(IndustryInfo record);

  int insertSelective(IndustryInfo record);

  IndustryInfo selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(IndustryInfo record);

  int updateByPrimaryKey(IndustryInfo record);

  List<IndustryVo> findAllIndustry();

  IndustryInfo findIndustryByName(String industryName);
}