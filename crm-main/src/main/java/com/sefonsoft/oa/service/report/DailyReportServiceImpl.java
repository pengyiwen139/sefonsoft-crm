package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.controller.report.DailyReportController;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.project.ProjectInfoDao;
import com.sefonsoft.oa.dao.report.DailyReportDao;
import com.sefonsoft.oa.dao.report.DailyReportInfoDao;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.project.ProjectInfoUpdateDTO;
import com.sefonsoft.oa.domain.report.*;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.DailyReport;
import com.sefonsoft.oa.entity.report.DailyReportInfo;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sefonsoft.oa.system.utils.DateUtils.addDateHours;
import static com.sefonsoft.oa.system.utils.DateUtils.getPastDate;

/**
 * 周日报表(DailyReportServiceImpl)表服务实现类
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
@Service("dailyReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DailyReportServiceImpl extends Response<Object> implements DailyReportService {
    private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);


    @Resource
    private DailyReportDao dailyReportDao;

    @Resource
    private DailyReportInfoDao dailyReportInfoDao;

    @Resource
    private BizOpportsDao bizOpportsDao;

    @Resource
    private SysEmployeeDao sysEmployeeDao;

    @Resource
    private SysDepartmentDao sysDepartmentDao;

    @Resource
    private ProjectInfoDao projectInfoDao;

    /**
     * 服务对象
     */
    @Resource
    private SysEmployeeService sysEmployeeService;


    //日报信息类型
    public static final int DAILY_REPORT_INFO_TYPE_CUSTOMER = 1; //官房
    public static final int DAILY_REPORT_INFO_TYPE_OPPORTS = 2;//商机
    public static final int DAILY_REPORT_INFO_TYPE_PROJECT = 3;//项目
    public static final int DAILY_REPORT_INFO_TYPE_OTHER = 4;//日常


    /**
     * 日报搜索
     *
     * @param dailyReportQueryDTO 查询条件
     * @param loginUser           当前用户
     * @return
     */
    @Override
    public PageableResult dailyReportSearch(DailyReportQueryDTO dailyReportQueryDTO, LoginUserDTO loginUser) throws Exception {
        if (!ObjTool.isNotNull(dailyReportQueryDTO.getPage())) {
            dailyReportQueryDTO.setPage(1);
        }
        if (!ObjTool.isNotNull(dailyReportQueryDTO.getLimit())) {
            dailyReportQueryDTO.setLimit(10);
        }

        //handle startday and endday
        String startDay = "";
        String endDay = "";
        if (ObjTool.isNotNull(dailyReportQueryDTO.getStartDay()) && ObjTool.isNotNull(dailyReportQueryDTO.getEndDay())) {
            startDay = dailyReportQueryDTO.getStartDay();
            endDay = dailyReportQueryDTO.getEndDay();

        } else if (ObjTool.isNotNull(dailyReportQueryDTO.getYear()) && ObjTool.isNotNull(dailyReportQueryDTO.getMonth())) {
            String year = dailyReportQueryDTO.getYear();
            String months = dailyReportQueryDTO.getMonth();
            int m = Integer.parseInt(months);
            //月份小于10
            if (m < 10) {
                months = "0" + months;
            }

            String monthStr = year + months;
            Integer month = Integer.valueOf(monthStr) * 100;
            Date mouth = DateUtils.StringFormat(monthStr, "yyyyMM");
            //当月天数
            Integer days = DateUtils.getDayOfMonth(mouth);
            Integer date = month + 1;
            Date mouth1 = DateUtils.StringFormat(date.toString(), "yyyyMMdd");

            startDay = DateUtils.formatDateMin(mouth1, "yyyy-MM-dd");

            date = month + days;
            mouth1 = DateUtils.StringFormat(date.toString(), "yyyyMMdd");
            endDay = DateUtils.formatDateMin(mouth1, "yyyy-MM-dd");
        }

        //handle employee ids
        List<String> users = new LinkedList<>();
        List<String> inputDeptIds = dailyReportQueryDTO.getDeptIds();


        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            if (CollectionUtils.isEmpty(inputDeptIds)) {
                //默认只能查询自己
                users.add(loginUser.getUserId());
            } else {
                logger.info("没有该部门数据权限");
                return null;
            }

        } else {
            if (ObjTool.isNotNull(dailyReportQueryDTO.getEmployeeId()) && !dailyReportQueryDTO.getEmployeeId().equals("")) {
                //只查询单个人的日报
                users.add(dailyReportQueryDTO.getEmployeeId());
            } else {
                //按部门条件查询
                if (!CollectionUtils.isEmpty(inputDeptIds)) {
                    //判断参数中的部门是否在数据权限列表里
                    for (String deptId : inputDeptIds) {
                        if (!dataDeptIdList.contains(deptId)) {
                            logger.info("没有该部门数据权限:" + deptId);
                            return null;
                        }
                    }
                } else {
                    //所传部门为空，查询数据限的所有数据
                    //根据数据权取去查询
                    dataDeptIdList = new ArrayList<>(dataDeptIdList);
                    String mydpid = loginUser.getDeptId();
                    if (!dataDeptIdList.contains(mydpid)) {
                        dataDeptIdList.add(mydpid);
                    }
                    inputDeptIds = dataDeptIdList;
                }


                List<SysEmployeeQueryDTO> employees = sysEmployeeDao.getAllEmployeeInDepts(inputDeptIds, startDay, endDay);
                employees.forEach(employee -> {
                    users.add(employee.getEmployeeId());
                });
            }
        }




        List<DailyReport> list = this.dailyReportDao.search(dailyReportQueryDTO.getKeyWord(), startDay, endDay, users, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());
        Long totalCount = this.dailyReportDao.searchTotal(dailyReportQueryDTO.getKeyWord(), startDay, endDay, users, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());
        list.forEach(report -> {
            report.setDailyReportInfoList(dailyReportInfoDao.queryByReportId(report.getId()));
        });
        return new PageableResult<>(totalCount, list);
    }

    /**
     * 日报统计查询
     *
     * @param dailyReportQueryDTO 查询条件
     * @param loginUser           当前用户
     * @return
     */
    public Object statistics(DailyReportQueryDTO dailyReportQueryDTO, LoginUserDTO loginUser) {
        if (!ObjTool.isNotNull(dailyReportQueryDTO.getPage())) {
            dailyReportQueryDTO.setPage(1);
        }
        //handle department ids
        List<String> inputDeptIds = dailyReportQueryDTO.getDeptIds();


        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            //无数据权限
            return null;
        } else {
            //判断数据权限中是否包含该部门, 默认只人蚁一个部门（大区）的统计
            if (!dataDeptIdList.contains(inputDeptIds.get(0))) {
                logger.info("没有该部门的数据权限");
                return null;
            }
        }


        //handle startday and endday
        String startDay = "";
        String endDay = "";
        if (ObjTool.isNotNull(dailyReportQueryDTO.getStartDay()) && ObjTool.isNotNull(dailyReportQueryDTO.getEndDay())) {
            startDay = dailyReportQueryDTO.getStartDay();
            endDay = dailyReportQueryDTO.getEndDay();

        }

        logger.debug("统计条件:", inputDeptIds.get(0), startDay, endDay);

        List<DailyReportInfo> dailyReport = dailyReportDao.statisticsDailly(startDay, endDay, inputDeptIds, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());
        List<DailyReportInfo> customer = dailyReportDao.statistics(DAILY_REPORT_INFO_TYPE_CUSTOMER, startDay, endDay, inputDeptIds, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());
        List<DailyReportInfo> bizopports = dailyReportDao.statistics(DAILY_REPORT_INFO_TYPE_OPPORTS, startDay, endDay, inputDeptIds, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());
        List<DailyReportInfo> project = dailyReportDao.statistics(DAILY_REPORT_INFO_TYPE_PROJECT, startDay, endDay, inputDeptIds, dailyReportQueryDTO.getPage(), dailyReportQueryDTO.getLimit());

        Map<String, List<DailyReportInfo>> statisticsResult = new HashMap<>();
        statisticsResult.put("dailyReport", dailyReport);
        statisticsResult.put("customer", customer);
        statisticsResult.put("bizopports", bizopports);
        statisticsResult.put("project", project);
        return statisticsResult;
    }


    @Override
    public Map<Integer, DailyReport> getCondition(EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception {

        String employeeId = employeeReportQueryDTO.getEmployeeId();
        String year = employeeReportQueryDTO.getYear();
        String months = employeeReportQueryDTO.getMonth();
        int m = Integer.parseInt(months);
        //月份小于10
        if (m < 10) {
            months = "0" + months;
        }

        String monthStr = year + months;
        Integer month = Integer.valueOf(monthStr) * 100;
        Date mouth = DateUtils.StringFormat(monthStr, "yyyyMM");
        //当月天数
        Integer days = DateUtils.getDayOfMonth(mouth);
        Map<Integer, DailyReport> employeeReportMap = new HashMap<Integer, DailyReport>();
        //当月到当前天数放每天日周报
        for (int i = 1; i <= days; i++) {
            Integer date = month + i;
            if (DateUtils.getCourrentDateTimeKey().intValue() < date.intValue()) {
                break;
            }
            Date mouth1 = DateUtils.StringFormat(date.toString(), "yyyyMMdd");

            String rpd = DateUtils.formatDateMin(mouth1, "yyyy-MM-dd");
            List<DailyReport> list = dailyReportDao.getOneDayDetail(rpd, employeeId);


            if (list.size() > 0) {
                employeeReportMap.put(date, list.get(0));
            } else {
                employeeReportMap.put(date, null);
            }
        }

        return employeeReportMap;
    }

    /**
     * 给批量插入的字段做校验
     *
     * @param reporInsertInfo
     * @return
     */
    @Override
    public Response insertCheck(DailyReportInsertOrUpdateInfo reporInsertInfo) {
        if (!ObjTool.isNotNull(reporInsertInfo.getId())) {
            if (!ObjTool.isNotNull(reporInsertInfo)) {
                return failure("请填写日报数据");
            }

            if (!ObjTool.isNotNull(reporInsertInfo.getDailyReportInfoList())) {
                return failure("未获取到日报信息");
            } else {
                for (DailyReportInfoInsertOrUpdateInfo info : reporInsertInfo.getDailyReportInfoList()) {
                    if (!ObjTool.isNotNull(info.getContent()) || info.getContent().equals("")) {
                        return failure("未获取到日报信息内容");
                    }
                }
            }

            Date reportTime = reporInsertInfo.getReportTime();
            if (!ObjTool.isNotNull(reportTime)) {
                return failure("未获取到报告日期");
            }
        }

//        ifItsTime2AddReport(reportDayLastHours, reportTime);
        return null;
    }

    /**
     * 限制日报在指定的某一天的某个小时后不能填写
     *
     * @param reportDayLastHours
     * @return
     */
    @Override
    public Response ifItsTime2AddReport(Integer reportDayLastHours, Date reportTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reportDate = null;
        try {
            Date date = new Date();
            reportDate = sdf.parse(sdf.format(reportTime));
            Date dateLimit = addDateHours(reportDate, 24 + reportDayLastHours);
            boolean before = dateLimit.before(date);
            if (before) {
                return failure("当前填写的日报超过限定时间，无法填写");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存昌报
     *
     * @param dailyReport 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DailyReportInsertOrUpdateInfo save(DailyReportInsertOrUpdateInfo dailyReport, LoginUserDTO loginUser) {
        Date date = new Date();
        if (ObjTool.isNotNull(dailyReport.getId())) {
            logger.debug("更新日报");

            //update
            dailyReport.setReportStatus(1);
//            dailyReport.setLastTime(date);
//            this.dailyReportDao.update(dailyReport);
            List<DailyReportInfoInsertOrUpdateInfo> list = dailyReport.getDailyReportInfoList();
            if (list != null) {
                for (DailyReportInfoInsertOrUpdateInfo reportInfo : list) {
                    if (ObjTool.isNotNull(reportInfo.getId()) && reportInfo.getId() != 0) {
                        reportInfo.setReportId(dailyReport.getId());
                        reportInfo.setLastTime(date);
                        logger.debug("更新数据库日报信息");
                        this.dailyReportInfoDao.update(reportInfo);
                    }
                }
            }

            logger.debug("update dailyReport status");
            //update dailyReport status
            DailyReport temp = dailyReportDao.queryById(dailyReport.getId());
            List<DailyReportInfo> driList = temp.getDailyReportInfoList();
            if (list != null) {
                boolean isMark = false;
                for (DailyReportInfo info : driList) {
                    if (info.getStatus() == 2) {
                        isMark = true;
                        break;
                    }
                }
                if (isMark) {
                    dailyReport.setReportStatus(2);
                } else {
                    dailyReport.setReportStatus(1);
                }
            }
            logger.debug("dailyReport status：" + dailyReport.getReportStatus());
            dailyReport.setLastTime(date);
            logger.debug("update dailyReport in database");
            this.dailyReportDao.update(dailyReport);

        } else {
            logger.debug("insert new daily report");
            //insert
            dailyReport.setEmployeeId(loginUser.getUserId())
                    .setCreateTime(date)
                    .setLastTime(date)
                    .setReportStatus(0);

            this.dailyReportDao.insert(dailyReport);
            List<DailyReportInfoInsertOrUpdateInfo> list = dailyReport.getDailyReportInfoList();
            if (list != null) {
                list.forEach(reportInfo -> {
                    reportInfo.setReportId(dailyReport.getId());
                    reportInfo.setCreateTime(date)
                            .setLastTime(date)
                            .setStatus(1);
                    logger.debug("insert new daily report into database");
                    this.dailyReportInfoDao.insert(reportInfo);


//                    if (reportInfo.getInfoType() == DAILY_REPORT_INFO_TYPE_OPPORTS) {
//                        //商机，需要追加商机描述
//                        //如果是商机，则把内容追加到商机表中
//                        if (ObjTool.isNotNull(reportInfo.getExternalId()) && !reportInfo.getExternalId().equals("")) {
//                            Long id = Long.parseLong(reportInfo.getExternalId());
//                            BizOpports bizOpports = bizOpportsDao.queryById(id);
//                            bizOpports.setDesc(bizOpports.getDesc() + "\n" + reportInfo.getContent());
//                            bizOpports.setModifiedTime(date);
//                            bizOpportsDao.updateDesc(id, bizOpports.getDesc());
//                        }
//
//                    } else
                    if (reportInfo.getInfoType() == DAILY_REPORT_INFO_TYPE_PROJECT) {
                        logger.debug("update project spstage");
                        //项目，查亲项目跟踪进度
                        String[] temp = reportInfo.getExternalId().split("_");
                        logger.debug("getExternalId : " + reportInfo.getExternalId());
                        logger.debug("split ExternalId : " + temp);
                        if (temp.length >= 3) {
                            ProjectInfoUpdateDTO projectInfoUpdateDTO = new ProjectInfoUpdateDTO();
                            projectInfoUpdateDTO.setProjectId(temp[0]);
                            int spStageId = Integer.parseInt(temp[1]);
                            projectInfoUpdateDTO.setSpstageId(spStageId);
                            projectInfoUpdateDTO.setCustomerProjectPhase(temp[2]);
                            projectInfoUpdateDTO.setLastTime(date);
                            logger.debug("update project spstage into database");
                            projectInfoDao.update(projectInfoUpdateDTO);
                        }

                    }
                });
            }
        }

//        DailyReport temp = dailyReportDao.queryById(dailyReport.getId());
        return dailyReport;
    }


    @Override
    public HashMap<String, String> getMostEarlyAddDateTime(Integer reportDayLastHours, LoginUserDTO loginUser, SimpleDateFormat sdf) {
        HashMap<String, String> dateTimeMap = new HashMap<>(2);
        Date date = new Date();
        if (!ObjTool.isNotNull(reportDayLastHours)) {
            //没有需要次日或者次次日填写日报的需求，则直接返回当天的日期和当天晚上12点的时刻
            dateTimeMap.put("minReportDate", sdf.format(date));
            dateTimeMap.put("maxReportTime", "24:00:00");
            return dateTimeMap;
        } else {
            Integer hours = reportDayLastHours % 24;
            if (hours.equals(0)) {
                Integer days = reportDayLastHours / 24;
                Date pastDate = getPastDate(days, date);
                dateTimeMap.put("minReportDate", sdf.format(pastDate));
                dateTimeMap.put("maxReportTime", "24:00:00");
            } else {
                reportDayLastHours = reportDayLastHours + 24;
                Integer days = reportDayLastHours / 24;
                Date pastDate = getPastDate(days, date);
                dateTimeMap.put("minReportDate", sdf.format(pastDate));
                dateTimeMap.put("maxReportTime", hours + ":00:00");
            }
            return dateTimeMap;
        }
    }

    @Override
    public DailyReport getDailyReportDetail(String reportTime, String userId) {
        logger.debug("getDailyReportDetail");
//        return dailyReportDao.getOneDayDetail(reportTime, userId);
        List<DailyReport> list = dailyReportDao.getOneDayDetail(reportTime, userId);


        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


}