package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.controller.report.DailyReportController;
import com.sefonsoft.oa.dao.report.DailyReportDao;
import com.sefonsoft.oa.dao.report.WeeklyReportDao;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.report.DailyReportQueryDTO;
import com.sefonsoft.oa.domain.report.WeeklyReportQueryDTO;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.DailyReport;
import com.sefonsoft.oa.entity.report.WeeklyReport;
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
import java.util.*;

/**
 * 周报表(WeeklyReportServiceImpl)表服务实现类
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
@Service("WeeklyReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class WeeklyReportServiceImpl extends Response<Object> implements WeeklyReportService {
    private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);

    @Resource
    private WeeklyReportDao weeklyReportDao;

    @Resource
    private DailyReportDao dailyReportDao;

    @Resource
    private DailyReportService dailyReportService;

    @Resource
    private SysEmployeeService sysEmployeeService;

    @Resource
    private SysDepartmentDao sysDepartmentDao;

    @Resource
    private SysEmployeeDao sysEmployeeDao;


    @Override
    public Response insertCheck(WeeklyReport report) {
        if (!ObjTool.isNotNull(report)) {
            return failure("周报数据缺失");
        }

        if (!ObjTool.isNotNull(report.getStartDate()) || !ObjTool.isNotNull(report.getEndDate())) {
            return failure("未获取到周报日期");
        }

        if (!ObjTool.isNotNull(report.getDeptId())) {
            return failure("部门ID缺失");
        }

        List<String> deptIds = new ArrayList<>();
        deptIds.add(report.getDeptId());
        List<WeeklyReport> wrList = weeklyReportDao.getCondition("", deptIds, report.getStartDate(), report.getEndDate(), 1, 10);
        if (wrList != null && wrList.size() > 0) {
            return failure("周报已存在");
        }

        return null;
    }

    @Override
    public PageableResult getCondition(WeeklyReportQueryDTO weeklyReportQueryDTO, LoginUserDTO loginUser) throws Exception {
        if (!ObjTool.isNotNull(weeklyReportQueryDTO.getPage())) {
            weeklyReportQueryDTO.setPage(1);
        }
        if (!ObjTool.isNotNull(weeklyReportQueryDTO.getLimit())) {
            weeklyReportQueryDTO.setLimit(10);
        }
        //handle employee ids
        List<String> inputDeptIds = weeklyReportQueryDTO.getDeptIds();

        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            //没有权限查看周报
            return null;
        } else {
            if (!CollectionUtils.isEmpty(inputDeptIds)) {
                //判断参数中的部门是否在数据权限列表里
                for (String deptId : inputDeptIds) {
                    if (!dataDeptIdList.contains(deptId)) {
                        logger.info("没有该部门数据权限:" + deptId);
                        return null;
                    }
                }
            } else {
                //根据数据权取去查询
                logger.info("根据数据权取去查询:" + dataDeptIdList);
                dataDeptIdList = new ArrayList<>(dataDeptIdList);
                String mydpid = loginUser.getDeptId();
                if (!dataDeptIdList.contains(mydpid)) {
                    dataDeptIdList.add(mydpid);
                }
                inputDeptIds = dataDeptIdList;
            }

        }

        //handle startday and endday
        String startDay = "";
        String endDay = "";
        if (ObjTool.isNotNull(weeklyReportQueryDTO.getStartDay()) && ObjTool.isNotNull(weeklyReportQueryDTO.getEndDay())) {
            startDay = weeklyReportQueryDTO.getStartDay();
            endDay = weeklyReportQueryDTO.getEndDay();

        } else if (ObjTool.isNotNull(weeklyReportQueryDTO.getYear()) && ObjTool.isNotNull(weeklyReportQueryDTO.getMonth())) {
            String year = weeklyReportQueryDTO.getYear();
            String months = weeklyReportQueryDTO.getMonth();
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

            int dayofweek = dayForWeek(mouth1);
            mouth1 = DateUtils.mouthAddDays(mouth1, -(dayofweek - 1));

            startDay = DateUtils.formatDateMin(mouth1, "yyyy-MM-dd");


            date = month + days;
            mouth1 = DateUtils.StringFormat(date.toString(), "yyyyMMdd");
            endDay = DateUtils.formatDateMin(mouth1, "yyyy-MM-dd");
        }


        logger.info("查询投机倒把:" + startDay, endDay, inputDeptIds);

        //if (inputDeptIds.size() == 0 || "".equals(startDay) || "".equals(endDay)) {
        if (inputDeptIds.size() == 0) {
            logger.info("周报查询条件错误，查询部门列表为空");
            return null;
        }

        List<WeeklyReport> wrList = weeklyReportDao.getCondition(weeklyReportQueryDTO.getKeyWord(), inputDeptIds, startDay, endDay, weeklyReportQueryDTO.getPage(), weeklyReportQueryDTO.getLimit());
        Long totalCount = weeklyReportDao.getConditionTotal(weeklyReportQueryDTO.getKeyWord(), inputDeptIds, startDay, endDay, weeklyReportQueryDTO.getPage(), weeklyReportQueryDTO.getLimit());
        return new PageableResult<>(totalCount, wrList);

//        return wrList;
    }

    public int dayForWeek(Date tmpDate) {
        Calendar cal = Calendar.getInstance();
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WeeklyReport save(WeeklyReport report, LoginUserDTO loginUser) throws Exception {
        Date date = new Date();
        if (ObjTool.isNotNull(report.getId())) {
            //update
        } else {
            logger.debug("新增周报");
            //insert
            //check data permission
            String deptId;
            List<String> dataDeptIdList = loginUser.getDataDeptIdList();
            if (CollectionUtils.isEmpty(dataDeptIdList)) {
                //没有权限查看周报
                return null;
            } else {
                deptId = report.getDeptId();
                if (deptId != null && !"".equals(deptId)) {
                    if (!dataDeptIdList.contains(deptId)) {
                        logger.debug("没有该部门数据权限:" + deptId);
                        return null;
                    }
                } else {
                    //默认使用登录用户的部门id
                    logger.debug("默认使用登录用户的部门id");
                    deptId = loginUser.getDeptId();
                }
            }

            //query daily report  from startDate to endDate
            List<String> depIds = new ArrayList<>();
            depIds.add(deptId);
            DailyReportQueryDTO queryDTO = new DailyReportQueryDTO();
            queryDTO.setStartDay(report.getStartDate())
                    .setEndDay(report.getEndDate())
                    .setDeptIds(depIds)
                    .setLimit(10000);

            PageableResult searchResult = dailyReportService.dailyReportSearch(queryDTO, loginUser);
            List<DailyReport> markReports = new ArrayList<>();
            List<SysEmployeeQueryDTO> employees = sysEmployeeDao.getAllEmployeeInDepts(depIds, report.getStartDate(), report.getEndDate());
            int employeeCount = employees.size();
            int reportCount = 0;
            int readcount = 0;
            Map<String, Boolean> readMap = new HashMap<>();
            if (searchResult != null) {
                if (searchResult.resultList != null && searchResult.resultList.size() != 0) {
                    logger.debug("insert WeeklyInfo into database");
                    reportCount = searchResult.resultList.size();
                    for (DailyReport dr : ((PageableResult<DailyReport>) searchResult).resultList) {
                        if (dr.getReportStatus() == 2) {//已标记
                            readcount = readcount + 1;
                            readMap.put(dr.getEmployeeId(), true);
                            markReports.add(dr);
                        } else if (dr.getReportStatus() == 1) {//已读
                            readcount = readcount + 1;
                            readMap.put(dr.getEmployeeId(), true);
                        }
                    }
                }
            } else {
                logger.debug("没有查询到日报");
            }

            report.setDeptId(deptId);
            report.setEmployeeId(loginUser.getUserId())
                    .setReportTime(report.getStartDate())
                    .setEmployeeCount(employeeCount)
                    .setReportCount(reportCount)
                    .setReadCount(readMap.size())
                    .setCreateTime(date)
                    .setLastTime(date);
            logger.debug("insert weeklyReportDao into database");
            this.weeklyReportDao.insert(report);
            markReports.forEach(dr -> {
                logger.debug("insert:", report.getId(), dr.getId());
                weeklyReportDao.insertWeeklyInfo(report.getId(), dr.getId());
            });
        }


        if (ObjTool.isNotNull(report.getId())) {
            WeeklyReport re = getWeeklyReportDetail(report.getId(), loginUser);
            return re;
        } else {
            return null;
        }

    }

    @Override
    public WeeklyReport getWeeklyReportDetail(Long id, LoginUserDTO loginUser) {
        logger.debug("getWeeklyReportDetail");
        WeeklyReport report = weeklyReportDao.queryById(id);
        List<Long> drIds = weeklyReportDao.queryDailyReportIds(id);
        if (drIds != null && drIds.size() > 0) {
            List<DailyReport> drList = dailyReportDao.queryByIds(drIds);
            report.setDailyReportList(drList);
        }
        return report;
    }


}