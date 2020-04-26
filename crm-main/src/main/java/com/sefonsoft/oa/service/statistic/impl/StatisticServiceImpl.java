package com.sefonsoft.oa.service.statistic.impl;

import com.google.common.collect.ImmutableList;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.contract.ContractInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.project.ProjectDao;
import com.sefonsoft.oa.dao.report.DailyReportDao;
import com.sefonsoft.oa.dao.workorder.WorkorderInfoDao;
import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.statistic.vo.*;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.statistic.StatisticService;
import com.sefonsoft.oa.system.emun.OnTimeEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * @author xielf
 */
@Service
public class StatisticServiceImpl implements StatisticService {

  private ProjectDao projectDao;

  private BizOpportsDao bizOpportsDao;

  private CustomerInfoDao customerInfoDao;

  private ContractInfoDao contractDao;

  private WorkorderInfoDao workorderInfoDao;

  private DailyReportDao dailyReportDao;


  @Autowired
  public void setContractDao(ContractInfoDao contractDao) {
    this.contractDao = contractDao;
  }

  @Autowired
  public void setWorkorderInfoDao(WorkorderInfoDao workorderInfoDao) {
    this.workorderInfoDao = workorderInfoDao;
  }

  @Autowired
  public void setDailyReportDao(DailyReportDao dailyReportDao) {
    this.dailyReportDao = dailyReportDao;
  }

  @Autowired
  public void setProjectDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Autowired
  public void setBizOpportsDao(BizOpportsDao bizOpportsDao) {
    this.bizOpportsDao = bizOpportsDao;
  }

  @Autowired
  public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
    this.customerInfoDao = customerInfoDao;
  }

  @Override
  public CountVo countStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto) {

    CountVo countVo = new CountVo();

    countVo.setContractCount(0);

    countVo.setBizOpportsCount(bizOpportsDao.statisticCount(deptIds, statisticsDto.getEmployeeId()));

    countVo.setCustomerCount(customerInfoDao.statisticCount(deptIds, statisticsDto.getEmployeeId()));

    countVo.setProjectCount(projectDao.statisticCount(deptIds, statisticsDto.getEmployeeId()));

    countVo.setSumMoney(projectDao.statisticMoney(deptIds, statisticsDto.getEmployeeId()));

    return countVo;
  }

  @Override
  public CountVo onTimeStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto) {

    OnTimeEnum onTimeEnum = OnTimeEnum.convert(statisticsDto.getOnTime());
    if (onTimeEnum == null) {
      throw new MsgException("时间类型有误");
    }
    LocalDate finalDate;
    String endTime = null;
    String startTime = null;
    DateTimeFormatter endFormatter;
    switch (onTimeEnum) {
      case ALL:
        return countStatistic(deptIds, loginUserDTO, statisticsDto);
      case MONTH:
        startTime = DateTimeFormatter.ofPattern(DateUtils.MONTH_FIRST_DAY_PATTERN).format(LocalDate.now());
        break;
      case QUARTER:
        startTime = DateUtils.getPatternOfQuarter().format(LocalDate.now());
        break;
      case YEAR:
        startTime = DateTimeFormatter.ofPattern(DateUtils.YEAR_FIRST_DAY_PATTERN).format(LocalDate.now());
        break;
      case WEEK:
        LocalDate now = LocalDate.now().with(DayOfWeek.MONDAY);
        startTime = DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(now);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + onTimeEnum);
    }

    endFormatter = DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN);
    endTime = endFormatter.format(LocalDate.now());

    CountVo countVo = new CountVo();
    countVo.setProjectCount(projectDao.onTimeStatisticCount(deptIds, startTime, endTime, statisticsDto.getEmployeeId()));
    countVo.setBizOpportsCount(bizOpportsDao.onTimeStatisticCount(deptIds, startTime, endTime, statisticsDto.getEmployeeId()));
    countVo.setCustomerCount(customerInfoDao.onTimeStatisticCount(deptIds, startTime, endTime, statisticsDto.getEmployeeId()));
    countVo.setSumMoney(projectDao.onTimeStatisticMoney(deptIds, startTime, endTime, statisticsDto.getEmployeeId()));
    return countVo;
  }


  @Deprecated
  @Override
  public ProportionVo proportionStatistic(Set<String> deptIds, LoginUserDTO loginUserDTO, CommonStatisticsDto statisticsDto) {

    CountVo countVo = countStatistic(deptIds, loginUserDTO, statisticsDto);
    ProportionVo proportionVo = new ProportionVo();
    int biz = bizOpportsDao.totalCount();
    if (biz != 0) {
      proportionVo.setBizOpportsProportion(
          new BigDecimal(countVo.getBizOpportsCount())
              .divide(new BigDecimal(biz), 1, BigDecimal.ROUND_HALF_UP)
              .multiply(new BigDecimal("100")));
    }
    int customer = customerInfoDao.totalCount();
    if (customer != 0) {
      proportionVo.setCustomerProportion(
          new BigDecimal(countVo.getCustomerCount())
              .divide(new BigDecimal(customer), 1, BigDecimal.ROUND_HALF_UP)
              .multiply(new BigDecimal("100")));
    }
    int project = projectDao.totalCount();
    if (project != 0) {
      proportionVo.setProjectProportion(new BigDecimal(countVo.getProjectCount())
          .divide(new BigDecimal(project), 1, BigDecimal.ROUND_HALF_UP)
          .multiply(new BigDecimal("100")));
    }
    return proportionVo;
  }

  @Override
  public List<PortraitsListVo> portraitsStatistic(String assginedEmployeeId, String currentEmployeeId, Set<String> deptIds) {

    PortraitsListVo contract = contractPortraits();
    PortraitsListVo project = projectPortraits();
    PortraitsListVo bizOpports = bizOpportsPortraits();
    PortraitsListVo customer = customerPortraits();
    PortraitsListVo workerOrder = workOrderPortraits();
    PortraitsListVo daily = dailyPortraits();

    return ImmutableList.of(contract, project, bizOpports, customer, workerOrder, daily);
  }

  private PortraitsListVo contractPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();

    PortraitsVo p1 = new PortraitsVo();
    p1.setK("任务");
    p1.setV("");

    PortraitsVo p2 = new PortraitsVo();
    p2.setK("已签");
    p2.setV("");

    PortraitsVo p3 = new PortraitsVo();
    p3.setK("回款");
    p3.setV("");


    listVo.setPortraitsList(ImmutableList.of(p1, p2, p3));
    listVo.setType(PortraitsTypeEnum.CONTRACT);
    return listVo;
  }

  private PortraitsListVo projectPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();
    final int totalAmount = projectDao.totalAmount();
    PortraitsVo p1 = new PortraitsVo();
    p1.setK("所有立项金额");
    p1.setV(String.valueOf(totalAmount));

    PortraitsVo p2 = new PortraitsVo();
    final int totalCount = projectDao.totalCount();
    p2.setK("立项数");
    p2.setV(String.valueOf(totalCount));

    PortraitsVo p3 = new PortraitsVo();
    p3.setK("所有重大项目");
    p3.setV("");


    listVo.setPortraitsList(ImmutableList.of(p1, p2, p3));
    listVo.setType(PortraitsTypeEnum.PROJECT);
    return listVo;
  }

  private PortraitsListVo bizOpportsPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();

    PortraitsVo p1 = new PortraitsVo();
    final int bizCount = bizOpportsDao.totalCount();
    p1.setK("商机总数");
    p1.setV(String.valueOf(bizCount));

    PortraitsVo p2 = new PortraitsVo();
    final int bizProCount = bizOpportsDao.findBizOpportsCountByStatus(2);
    p2.setK("已立项商机");
    p2.setV(String.valueOf(bizProCount));


    listVo.setPortraitsList(ImmutableList.of(p1, p2));
    listVo.setType(PortraitsTypeEnum.BIZ_OPPORTS);
    return listVo;
  }

  private PortraitsListVo customerPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();

    PortraitsVo p1 = new PortraitsVo();
    final int totalCount = customerInfoDao.totalCount();
    p1.setK("客户总数");
    p1.setV(String.valueOf(totalCount));

    PortraitsVo p2 = new PortraitsVo();
    p2.setK("创建客户");
    p2.setV("");

    PortraitsVo p3 = new PortraitsVo();
    p3.setK("认领客户");
    p3.setV("");


    listVo.setPortraitsList(ImmutableList.of(p1, p2, p3));
    listVo.setType(PortraitsTypeEnum.CUSTOMER);
    return listVo;
  }

  private PortraitsListVo workOrderPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();
    final int totalCount = workorderInfoDao.totalCount();
    PortraitsVo p1 = new PortraitsVo();
    p1.setK("申请派工单");
    p1.setV(String.valueOf(totalCount));

    PortraitsVo p2 = new PortraitsVo();
    p2.setK("关联立项派工单");
    p2.setV("");


    listVo.setPortraitsList(ImmutableList.of(p1, p2));
    listVo.setType(PortraitsTypeEnum.WORK_ORDER);
    return listVo;
  }

  private PortraitsListVo dailyPortraits() {

    PortraitsListVo listVo = new PortraitsListVo();

    PortraitsVo p1 = new PortraitsVo();
    p1.setK("日报应填");
    p1.setV("");

    PortraitsVo p2 = new PortraitsVo();
    p2.setK("日报缺填");
    p2.setV("");


    listVo.setPortraitsList(ImmutableList.of(p1, p2));
    listVo.setType(PortraitsTypeEnum.DAILY);
    return listVo;
  }

}
