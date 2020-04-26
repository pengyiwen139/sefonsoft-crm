package com.sefonsoft.oa.service.common;


import com.sefonsoft.oa.dao.common.IndustryInfoDao;
import com.sefonsoft.oa.domain.common.IndustryVo;
import com.sefonsoft.oa.entity.IndustryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xielf
 */
@Service
public class IndustryService {

  private IndustryInfoDao industryInfoDao;

  @Autowired
  public void setIndustryInfoDao(IndustryInfoDao industryInfoDao) {
    this.industryInfoDao = industryInfoDao;
  }

  /**
   * 行业列表
   *
   * @return
   */
  public List<IndustryVo> industryList() {
    return industryInfoDao.findAllIndustry();
  }

}
