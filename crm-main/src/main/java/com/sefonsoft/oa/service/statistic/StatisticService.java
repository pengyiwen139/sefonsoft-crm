package com.sefonsoft.oa.service.statistic;

import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.statistic.vo.CountVo;
import com.sefonsoft.oa.domain.statistic.vo.PortraitsListVo;
import com.sefonsoft.oa.domain.statistic.vo.ProportionVo;
import com.sefonsoft.oa.domain.user.LoginUserDTO;

import java.util.List;
import java.util.Set;

/**
 * @author xielf
 */
public interface StatisticService {


  /**
   * 数量统计
   *
   * @param deptIds
   * @return
   */
  CountVo countStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto);

  /**
   * 按期限数量统计
   *
   * @param deptIds
   * @return
   */
  CountVo onTimeStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto);

  /**
   * 比例统计
   *
   * @param deptIds
   * @param statisticsDto
   * @return
   */
  @Deprecated
  ProportionVo proportionStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto);

  /**
   * 画像统计
   *
   * @return
   */
  List<PortraitsListVo> portraitsStatistic(String assginedEmployeeId, String currentEmployeeId, Set<String> deptIds);

}
