package com.sefonsoft.oa.dao.report;

import com.sefonsoft.oa.domain.report.DailyReportInfoInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.DailyReportInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.EmployeeReporInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.EmployeeReportDetailDTO;
import com.sefonsoft.oa.entity.report.DailyReport;
import com.sefonsoft.oa.entity.report.DailyReportInfo;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日报表(DailyReportDao)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
public interface DailyReportDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DailyReport queryById(Long id);

    /**
     * 通过ID批量查询
     *
     * @param id 主键
     * @return 实例对象
     */
    List<DailyReport> queryByIds(@Param("ids") List<Long> ids);

    /**
     * 日报搜索
     *
     * @param keyword  关键词
     * @param startday 开始时间
     * @param endday   结束时间
     * @param users    用户
     * @param page     用户
     * @param limit    用户
     * @return 实例对象
     */
    List<DailyReport> search(@Param("keyword") String keyword, @Param("startday") String startday, @Param("endday") String endday, @Param("users") List<String> users, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 日报搜索总数
     *
     * @param keyword  关键词
     * @param startday 开始时间
     * @param endday   结束时间
     * @param users    用户
     * @param page     用户
     * @param limit    用户
     * @return 实例对象
     */
    Long searchTotal(@Param("keyword") String keyword, @Param("startday") String startday, @Param("endday") String endday, @Param("users") List<String> users, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    List<DailyReport> getOneDayDetail(@Param("reportTime") String reportTime, @Param("userId") String userId);

    /**
     * 新增数据
     *
     * @param dailyReport 实例对象
     * @return 影响行数
     */
    int insert(DailyReportInsertOrUpdateInfo dailyReport);

    /**
     * 修改数据
     *
     * @param reporUpdateInfo 实例对象
     * @return 影响行数
     */
    boolean update(DailyReportInsertOrUpdateInfo reporUpdateInfo);


    /**
     * 新增日报信息数据
     *
     * @param reportInfo 实例对象
     * @return 影响行数
     */
    int insertReportInfo(DailyReportInfoInsertOrUpdateInfo reportInfo);


    /**
     * 日报内容统计查询
     *
     * @param infoType 日报信息类型
     * @param startday 开始时间
     * @param endday   结束时间
     * @param deptIds  部门
     * @param page     页
     * @param limit    页大小
     * @return 实例对象
     */
    List<DailyReportInfo> statistics(@Param("infoType") Integer infoType, @Param("startday") String startday, @Param("endday") String endday, @Param("deptIds") List<String> deptIds, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 日报统计查询
     *
     * @param infoType 日报信息类型
     * @param startday 开始时间
     * @param endday   结束时间
     * @param deptIds  部门
     * @param page     页
     * @param limit    页大小
     * @return 实例对象
     */
    List<DailyReportInfo> statisticsDailly( @Param("startday") String startday, @Param("endday") String endday, @Param("deptIds") List<String> deptIds, @Param("page") Integer page, @Param("limit") Integer limit);


}