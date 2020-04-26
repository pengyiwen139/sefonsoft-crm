package com.sefonsoft.oa.dao.report;

import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.entity.report.WeeklyReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 周报表(WeeklyReportDao)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
public interface WeeklyReportDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WeeklyReport queryById(Long id);


    /**
     * 通过ID查询对应日报id列表
     *
     * @param id 主键
     * @return 实例对象
     */
    List<Long> queryDailyReportIds(Long id);

    /**
     * 新增数据
     *
     * @param report 实例对象
     * @return 影响行数
     */
    int insert(WeeklyReport report);


    int insertWeeklyInfo(Long weeklyReportId, Long dailyReportId);

    List<WeeklyReport> getCondition(@Param("keyword") String keyword, @Param("depIds") List<String> depIds, @Param("startday") String startday, @Param("endday") String endday, @Param("page") Integer page, @Param("limit") Integer limit);

    Long getConditionTotal(@Param("keyword") String keyword, @Param("depIds") List<String> depIds, @Param("startday") String startday, @Param("endday") String endday, @Param("page") Integer page, @Param("limit") Integer limit);
}