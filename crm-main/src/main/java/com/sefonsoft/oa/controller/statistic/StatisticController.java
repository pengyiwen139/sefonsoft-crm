package com.sefonsoft.oa.controller.statistic;


import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticGroupVo;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsGroupVo;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticGroupVo;
import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author xielf
 */
@RestController
@Api(tags = "统计相关接口")
@RequestMapping("/statistic")
public class StatisticController extends BaseController {

  private ProjectService projectService;
  private BizOpportsService opportsService;
  private CustomerInfoService customerInfoService;
  private StatisticService statisticService;
  private WorkorderInfoService workorderInfoService;


  @Autowired
  public void setStatisticService(StatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @Autowired
  public void setCustomerInfoService(CustomerInfoService customerInfoService) {
    this.customerInfoService = customerInfoService;
  }

  @Autowired
  public void setProjectService(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Autowired
  public void setOpportsService(BizOpportsService opportsService) {
    this.opportsService = opportsService;
  }


  /**
   * 生成当前登录用正确可用数据部门
   *
   * @param loginUserDTO
   * @param statisticsDto
   * @return
   */
  private Set<String> generatorAvailableDeptIds(LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto) {

    //不是领导
    if (!loginUserDTO.getGradeId().equals(Grade.LD.getCode())) {
      //设置 employee 为自己
      statisticsDto.setEmployeeId(loginUserDTO.getUserId());
      return ImmutableSet.of(loginUserDTO.getDeptId());
    }

    //无参数
    if (statisticsDto == null) {
      //获取用户拥有的部门
      List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
      if (dataDeptIdList != null && dataDeptIdList.size() > 0) {
        //自己负责部门和指定销售可联合查
        return ImmutableSet.copyOf(dataDeptIdList);
      }
      //部门为null 设置 employee 为自己
      else {
        //只能查询自己
        statisticsDto.setEmployeeId(loginUserDTO.getUserId());
        return null;
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
      //部门为null 设置 employee 为自己
      else {
        //只能查询自己
        statisticsDto.setEmployeeId(loginUserDTO.getUserId());
        return null;
      }
    }

    //指定部门不是empty
    if (!StringUtils.isEmpty(deptId)) {
      return ImmutableSet.of(deptId);
    }
    return null;
  }

  @ApiOperation("按期数量统(按照指定期限统计数量)")
  @PostMapping("/onTimeNum")
  public Response onTimeNum(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      return success(statisticService.onTimeStatistic(deptIds, loginUserDTO, statisticsDto));
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }

  /**
   * 数量查询
   *
   * @return
   */
  @ApiOperation("数量统计(负责部门总数)")
  @PostMapping("/num")
  public Response count(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      return success(statisticService.countStatistic(deptIds, loginUserDTO, statisticsDto));
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据获取失败");
    }
  }

  /**
   * 数量查询
   *
   * @param statisticsDto
   * @return
   */
  @ApiOperation("比例统计")
  @PostMapping("/proportion")
  @Deprecated
  public Response proportion(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      return success(statisticService.proportionStatistic(deptIds, loginUserDTO, statisticsDto));
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
  @ApiOperation(value = "项目数据统计")
  @PostMapping("/project")
  public Response projectStatistics(@RequestBody CommonStatisticsDto statisticsDto) {


    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      List<ProjectStatisticGroupVo> statisticsVos = projectService.statistic(deptIds, statisticsDto.getOnTime(), statisticsDto.getEmployeeId(), loginUserDTO);
      return success(statisticsVos);
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
  @ApiOperation(value = "商机数据统计")
  @PostMapping("/bizOpports")
  public Response bizStatistics(@RequestBody CommonStatisticsDto statisticsDto) {


    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      List<BizOpportsStatisticGroupVo> statisticsVos = opportsService.statistics(deptIds, statisticsDto.getOnTime(), statisticsDto.getEmployeeId(), loginUserDTO);
      return success(statisticsVos);
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
  @ApiOperation(value = "客户数据统计")
  @PostMapping("/customer")
  public Response customerStatistics(@RequestBody CommonStatisticsDto statisticsDto) {

    try {
      LoginUserDTO loginUserDTO = getLoginDTO();
      Set<String> deptIds = generatorAvailableDeptIds(loginUserDTO, statisticsDto);
      List<CustomerStatisticsGroupVo> statisticsVos = customerInfoService.statistics(deptIds, statisticsDto.getOnTime(), statisticsDto.getEmployeeId(), loginUserDTO);
      return success(statisticsVos);
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
      int count = workorderInfoService.workOrderStatistics(statisticsDTO, loginUserDTO);
      return success(count);
    } catch (MsgException e) {
      return failure(e.getMessage());
    }
  }

}
