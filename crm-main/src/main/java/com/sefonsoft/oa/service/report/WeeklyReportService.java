package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.domain.report.WeeklyReportQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.WeeklyReport;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.response.Response;

/**
 * 财报(WeeklyReportService)表服务接口
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
public interface WeeklyReportService {

    /**
     * 给批量插入的字段做校验
     *
     * @param report
     * @return
     */
    Response insertCheck(WeeklyReport report);

    /**
     * 查询周日报列表
     *
     * @param weeklyReportQueryDTO 查询条件
     * @return
     */
    PageableResult getCondition(WeeklyReportQueryDTO weeklyReportQueryDTO, LoginUserDTO loginUser) throws Exception;


    /**
     * 给批量插入的字段做校验
     *
     * @param report
     * @return
     */
    WeeklyReport save(WeeklyReport report, LoginUserDTO loginUser) throws Exception;

    WeeklyReport getWeeklyReportDetail(Long id, LoginUserDTO loginUser);
}