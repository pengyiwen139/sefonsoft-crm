package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.dao.report.EmployeeReportInfoDao;
import com.sefonsoft.oa.domain.report.EmployeeRecentDayReportListDTO;
import com.sefonsoft.oa.domain.report.EmployeeReporInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.EmployeeReportDetailDTO;
import com.sefonsoft.oa.domain.report.ReportDaySignCountDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.constant.ReportConstant.*;
import static com.sefonsoft.oa.system.utils.DateUtils.*;

/**
 * 周日报表(EmployeeReportInfo)表服务实现类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
@Service("employeeReportInfoService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeReportInfoServiceImpl extends Response<Object> implements EmployeeReportInfoService {
    @Resource
    private EmployeeReportInfoDao employeeReportInfoDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EmployeeReportInfo queryById(Long id) {
        return this.employeeReportInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<EmployeeReportInfo> queryAllByLimit(int offset, int limit) {
        return this.employeeReportInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param employeeReportInfo 实例对象
     * @return 实例对象
     */
    @Override
    public EmployeeReportInfo insert(EmployeeReportInfo employeeReportInfo) {
        this.employeeReportInfoDao.insert(employeeReportInfo);
        return employeeReportInfo;
    }

    /**
     * 修改数据
     * @return 实例对象
     */
    @Override
    public boolean update(List<EmployeeReporInsertOrUpdateInfo> list, LoginUserDTO loginUser) {
        Date date = new Date();
        List<EmployeeReporInsertOrUpdateInfo> updateList = new ArrayList<>();
        List<EmployeeReporInsertOrUpdateInfo> insertList = new ArrayList<>();
        List<Long> needDeleteIdList = new ArrayList<>();
        List<Long> needUpdateList = new ArrayList<>();

        list.forEach(reportInfo -> {
            //有传id的视为需要修改的内容，无id的视为需要新增的内容
            if (ObjTool.isNotNull(reportInfo.getId())) {
                updateList.add(reportInfo);
            } else {
                insertList.add(reportInfo);
            }
        });

        if (ObjTool.isNotNull(updateList)) {
            updateList.forEach(updateReportInfo -> updateReportInfo.setOperator(loginUser.getUserId()).setLastTime(date));
        }
        if (ObjTool.isNotNull(insertList)) {
            insertList.forEach(insertReportInfo -> insertReportInfo.setOperator(loginUser.getUserId())
                                                                   .setEmployeeId(loginUser.getUserId())
                                                                   .setCreateTime(date)
                                                                   .setLastTime(date));
        }

        boolean batchInsertFlag = false;
        boolean batchUpdateFlag = false;

        //处理需要删除的周日报
        //1.查询当天当人所有的日报/周报的Id
        Integer reportType = list.get(0).getReportType();
        Date reportTime = list.get(0).getReportTime();
        List<Long> toDayAllReportIdByTypeList = employeeReportInfoDao.getLoginUserToDayReportIdByReportType(loginUser.getUserId(), list.get(0).getReportType(), list.get(0).getReportTime());

        if (ObjTool.isNotNull(toDayAllReportIdByTypeList,updateList)) {
            List<Long> updateIdList = updateList.stream().map(EmployeeReporInsertOrUpdateInfo::getId).collect(Collectors.toList());
            if (toDayAllReportIdByTypeList.size() > updateIdList.size()) {
                //在toDayAllReportIdByTypeList中去除需要不需要删除的周报、日报编号id
                toDayAllReportIdByTypeList.removeIf(id -> {
                    boolean flag = false;
                    for (Long updateId : updateIdList) {
                        if (id.equals(updateId)) {
                            flag = true;
                        }
                    }
                    return flag;
                });
                if (ObjTool.isNotNull(toDayAllReportIdByTypeList)) {
                    employeeReportInfoDao.batchDeleteByIdList(toDayAllReportIdByTypeList);
                }
            }

        }

        //既有需要修改的，又有需要新增的周日报
        if (ObjTool.isNotNull(insertList) && ObjTool.isNotNull(updateList)) {
            for (EmployeeReporInsertOrUpdateInfo updateReportInfo : updateList) {
                batchUpdateFlag = employeeReportInfoDao.update(updateReportInfo);
            }
            batchInsertFlag = employeeReportInfoDao.batchInsert(insertList);
            return batchInsertFlag && batchUpdateFlag;
        }

        //只有需要新增的周日报
        if (ObjTool.isNotNull(insertList) && !ObjTool.isNotNull(updateList)) {
            return employeeReportInfoDao.batchInsert(insertList);
        }

        //只有需要修改的周日报
        if (ObjTool.isNotNull(updateList) && !ObjTool.isNotNull(insertList)) {
            for (EmployeeReporInsertOrUpdateInfo updateReportInfo : updateList) {
                batchUpdateFlag = employeeReportInfoDao.update(updateReportInfo);
            }
            return batchUpdateFlag;
        }

        return false;

    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.employeeReportInfoDao.deleteById(id) > 0;
    }

    /**
     * 批量插入周日报表
     * @param list
     * @param loginUser
     * @return
     */
    @Override
    public boolean batchInsert(List<EmployeeReporInsertOrUpdateInfo> list, LoginUserDTO loginUser) {
        Date date = new Date();
        list.forEach(report -> report.setOperator(loginUser.getUserId()).setEmployeeId(loginUser.getUserId()).setCreateTime(date).setLastTime(date));
        return employeeReportInfoDao.batchInsert(list);
    }


    /**
     * 给批量插入的字段做校验
     *
     * @param list
     * @return
     */
    @Override
    public Response batchInsertCheck(List<EmployeeReporInsertOrUpdateInfo> list) {
        if (!ObjTool.isNotNull(list) || list.size() == 0) {
            return failure("请填写周日报数据");
        }
        for (EmployeeReporInsertOrUpdateInfo reporInsertInfo : list) {
            Long contactId = reporInsertInfo.getContactId();
            String followDetails = reporInsertInfo.getFollowDetails();
            String nextStepPlan = reporInsertInfo.getNextStepPlan();
            Integer operationType = reporInsertInfo.getOperationType();
            String projectId = reporInsertInfo.getProjectId();
            Date reportTime = reporInsertInfo.getReportTime();
            if (!ObjTool.isNotNull(projectId)) {
                return failure("未获取到项目信息");
            }
            if (!ObjTool.isNotNull(contactId)) {
                return failure("未获取到联系人信息");
            }
            if (!ObjTool.isNotNull(followDetails)) {
                return failure("请填写跟进详情");
            }
            if (!ObjTool.isNotNull(nextStepPlan)) {
                return failure("请填写下步计划");
            }
            if (!ObjTool.isNotNull(operationType)) {
                return failure("未获取到操作类型");
            }

            if (!ObjTool.isNotNull(reportTime)) {
                return failure("未获取到报告日期");
            }
        }
        return null;
    }

    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    @Override
    public List<EmployeeReportDetailDTO> getOne(String reportTime, String userId) {
        return employeeReportInfoDao.getOneDayDetailList(reportTime, userId);
    }

    /**
     * 获取最近添加的日报信息
     * @param loginUser
     * @return
     */
    @Override
    public EmployeeRecentDayReportListDTO getRecentDayList(LoginUserDTO loginUser, Date reportTime) {
        EmployeeRecentDayReportListDTO employeeRecentDayReportListDTO = new EmployeeRecentDayReportListDTO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取上一次填写周报的日期
        Date startDate = employeeReportInfoDao.getRecentlyWeekReportDay(loginUser.getUserId(), WEEK_TYPE_REPORT, OPERATION_OPERATE_REPORT, reportTime);
        int dateInterValDay = 0;
        if (ObjTool.isNotNull(startDate)) {
            dateInterValDay = getDateInterValDay(startDate, reportTime);
            if (dateInterValDay > 7) {
                //把开始时间设置为当前日期的七天之前
                startDate = getPastDate(7, reportTime);
            }
        } else {
            //把开始时间设置为当前日期的七天之前
            startDate = getPastDate(7, reportTime);
        }
        String startDateStr = sdf.format(startDate);
        String endDateStr = sdf.format(reportTime);
        List<String> projectIdList = new ArrayList<>();
        if (ObjTool.isNotNull(startDateStr, endDateStr)) {
            //获取当前用户填写周报需要的最近填写的日报信息
            projectIdList = employeeReportInfoDao.getRecentDayProjectIdList(startDateStr, endDateStr, loginUser.getUserId(), DAY_TYPE_REPORT, OPERATION_OPERATE_REPORT);
            List<EmployeeReportDetailDTO> needDayReportList = new ArrayList<>();
            if (ObjTool.isNotNull(projectIdList)) {
                for (String projectId : projectIdList) {
                    //获取单个项目的限定时间下最新的日报数据
                    List<EmployeeReportDetailDTO> list = employeeReportInfoDao.getRecentDayReportByProjectId(startDateStr, endDateStr, loginUser.getUserId(), projectId, DAY_TYPE_REPORT, OPERATION_OPERATE_REPORT);
                    if (ObjTool.isNotNull(list)) {
                        EmployeeReportDetailDTO detailDTO = list.get(list.size() - 1);
                        needDayReportList.add(detailDTO);
                    }
                }
            }
            if (ObjTool.isNotNull(needDayReportList)) {
                employeeRecentDayReportListDTO.setStartDate(startDateStr).setStopDate(endDateStr).setRecentlyDayReportList(needDayReportList);
            }
        }
        if (!ObjTool.isNotNull(projectIdList)) {
            employeeRecentDayReportListDTO.setStopDate(sdf.format(reportTime)).setStartDate(sdf.format(getPastDate(7, reportTime)));
        }
        return employeeRecentDayReportListDTO;
    }

    /**
     * 获取当天管辖的部门下的填写日报统计
     * @param loginUser
     * @return
     */
    @Override
    public ReportDaySignCountDTO getSignCount(LoginUserDTO loginUser, String reportTime) {
        ReportDaySignCountDTO reportDaySignCountDTO = new ReportDaySignCountDTO();
        //获取当天管辖下的部门所有人的总个数
        Integer sumCount = employeeReportInfoDao.getSumCount(loginUser.getDataDeptIdList());
        //获取当天管辖下的部门所有人已经填写日报的个数
        Integer signedCount = employeeReportInfoDao.getSignedCount(loginUser.getDataDeptIdList(), loginUser.getDeptId(), reportTime, DAY_TYPE_REPORT, OPERATION_OPERATE_REPORT);
        if (ObjTool.isNotNull(sumCount, signedCount)) {
            int unSignedCount = sumCount - signedCount;
            if (unSignedCount < 0) {
                return null;
            } else {
                reportDaySignCountDTO.setSumCount(sumCount).setSignedCount(signedCount).setUnSignedCount(unSignedCount);
            }
        }
        return reportDaySignCountDTO;
    }

    /**
     * 限制日报在指定的某一天的某个小时后不能填写
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

}