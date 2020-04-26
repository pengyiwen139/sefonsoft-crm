package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.domain.report.*;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.DailyReport;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日报表(DailyReportService)表服务接口
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
public interface DailyReportService {

    /**
     * 日报统计查询
     *
     * @param dailyReportQueryDTO 查询条件
     * @param loginUser           当前用户
     * @return
     */
    Object statistics(DailyReportQueryDTO dailyReportQueryDTO, LoginUserDTO loginUser) ;

    /**
     * 日报搜索
     *
     * @param dailyReportQueryDTO 查询条件
     * @param loginUser           当前用户
     * @return
     */
    PageableResult dailyReportSearch(DailyReportQueryDTO dailyReportQueryDTO, LoginUserDTO loginUser) throws Exception;
    
    /**
     * 查询周日报列表
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    Map<Integer, DailyReport> getCondition(EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception;


    DailyReport getDailyReportDetail(String reportTime, String userId);

    /**
     * 限制日报在指定的某一天的某个小时后不能填写
     *
     * @param reportDayLastHours
     * @return
     */
    Response ifItsTime2AddReport(Integer reportDayLastHours, Date reportTime);

    /**
     * 给批量插入的字段做校验
     *
     * @param report
     * @return
     */
    Response insertCheck(DailyReportInsertOrUpdateInfo report);

    /**
     * 新增数据
     *
     * @param employeeReportInfo 实例对象
     * @return 实例对象
     */
    DailyReportInsertOrUpdateInfo save(DailyReportInsertOrUpdateInfo employeeReportInfo, LoginUserDTO loginUser);

    HashMap<String, String> getMostEarlyAddDateTime(Integer reportDayLastHours, LoginUserDTO loginUser, SimpleDateFormat sdf);


}