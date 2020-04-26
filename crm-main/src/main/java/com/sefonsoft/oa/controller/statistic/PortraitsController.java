package com.sefonsoft.oa.controller.statistic;


import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticGroupVo;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsGroupVo;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticGroupVo;
import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO;
import com.sefonsoft.oa.domain.statistic.vo.PortraitsListVo;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.service.project.ProjectService;
import com.sefonsoft.oa.service.statistic.StatisticService;
import com.sefonsoft.oa.service.workorder.WorkorderInfoService;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author xielf
 */
@RestController
@Api(tags = "画像")
@RequestMapping("/portraits")
public class PortraitsController extends BaseController {

  private StatisticService statisticService;


  @Autowired
  public void setStatisticService(StatisticService statisticService) {
    this.statisticService = statisticService;
  }


  /**
   * 生成当前登录用正确可用数据部门
   *
   * @param loginUserDTO
   * @param statisticsDto
   * @return
   */
  private Set<String> generatorAvailableDeptIds(LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto) {

    //无参数
    if (statisticsDto == null) {

      //获取用户拥有的部门
      List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
      if (dataDeptIdList != null && dataDeptIdList.size() > 0) {
        //自己负责部门和指定销售可联合查
        return ImmutableSet.copyOf(dataDeptIdList);
      }
    }

    String deptId = statisticsDto.getDeptId();
    String employeeId = statisticsDto.getEmployeeId();

    //都为null时
    if (StringUtils.isEmpty(deptId) && StringUtils.isEmpty(employeeId)) {

      //获取用户拥有的部门
      List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
      if (dataDeptIdList != null && dataDeptIdList.size() > 0) {
        //自己负责部门和指定销售可联合查
        return ImmutableSet.copyOf(dataDeptIdList);
      }
    }

    //指定部门不是empty
    if (!StringUtils.isEmpty(deptId)) {
      return ImmutableSet.of(deptId);
    }
    return null;
  }


  /**
   * 项目统计
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation(value = "综合")
  @PostMapping("/comprehensive")
  public Response comprehensive(@RequestBody CommonStatisticsDto statisticsDto) {


    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      List<PortraitsListVo> portraitsList = statisticService.portraitsStatistic(statisticsDto.getEmployeeId(), loginUserDTO.getUserId(), deptIds);
      return success(portraitsList);
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }


  /**
   * 合同统计
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation(value = "合同统计")
  @PostMapping("/contract")
  public Response contract(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }


  /**
   * 项目统计
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation(value = "项目统计")
  @PostMapping("/project")
  public Response project(@RequestBody CommonStatisticsDto statisticsDto) {


    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }


  /**
   * 商机统计
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation(value = "商机统计")
  @PostMapping("/bizOpports")
  public Response bizOpports(@RequestBody CommonStatisticsDto statisticsDto) {


    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }


  /**
   * 客户统计
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation(value = "客户统计")
  @PostMapping("/customer")
  public Response customer(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }

  /**
   * 工单统计
   *
   * @param statisticsDTO
   * @return
   */
  @ApiOperation("工单统计")
  @PostMapping("/workerOrder")
  public Response workerOrder(@RequestBody WorkOrderStatisticsDTO statisticsDTO) {
    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    }
  }

  /**
   * 日报统计
   *
   * @param statisticsDTO
   * @return
   */
  @ApiOperation("日报统计")
  @PostMapping("/daily")
  public Response daily(@RequestBody WorkOrderStatisticsDTO statisticsDTO) {
    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    }
  }

}
