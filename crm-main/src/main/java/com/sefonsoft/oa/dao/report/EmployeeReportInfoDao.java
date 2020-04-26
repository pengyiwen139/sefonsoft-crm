package com.sefonsoft.oa.dao.report;

import com.sefonsoft.oa.domain.report.EmployeeReporInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.EmployeeReportDetailDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 周日报表(EmployeeReportInfo)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
public interface EmployeeReportInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmployeeReportInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<EmployeeReportInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param employeeReportInfo 实例对象
     * @return 对象列表
     */
    List<EmployeeReportInfo> queryAll(EmployeeReportInfo employeeReportInfo);

    /**
     * 新增数据
     *
     * @param employeeReportInfo 实例对象
     * @return 影响行数
     */
    int insert(EmployeeReportInfo employeeReportInfo);

    /**
     * 修改数据
     *
     * @param reporUpdateInfo 实例对象
     * @return 影响行数
     */
    boolean update(EmployeeReporInsertOrUpdateInfo reporUpdateInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量插入周日报表
     * @param list
     * @return
     */
    boolean batchInsert(@Param("list") List<EmployeeReporInsertOrUpdateInfo> list);

    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    List<EmployeeReportDetailDTO> getOneDayDetailList(@Param("reportTime") String reportTime, @Param("userId") String userId);

    /**
     * 获取上一次填写周报的日期
     * @param userId
     * @param dayTypeReport
     * @param operationOperateReport
     * @return
     */
    Date getRecentlyWeekReportDay(@Param("userId") String userId, @Param("dayTypeReport") int dayTypeReport, @Param("operationOperateReport") int operationOperateReport, @Param("date") Date date);

    /**
     * 获取当前用户填写周报需要的最近填写的日报信息
     * @param startDateStr
     * @param endDateStr
     * @param userId
     * @return
     */
    List<EmployeeReportDetailDTO> getRecentDayList(@Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr, @Param("userId") String userId);

    /**
     * 获取开始时间到结束时间的当前用户的项目编号列表
     * @param startDateStr
     * @param endDateStr
     * @param userId
     * @param dayTypeReport
     * @param operationOperateReport
     * @return
     */
    List<String> getRecentDayProjectIdList(@Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr, @Param("userId") String userId, @Param("dayTypeReport") int dayTypeReport, @Param("operationOperateReport") int operationOperateReport);

    /**
     * 获取单个项目的限定时间下最新的日报数据
      * @param startDateStr
     * @param endDateStr
     * @param userId
     * @param projectId
     * @param dayTypeReport
     * @param operationOperateReport
     * @return
     */
    List<EmployeeReportDetailDTO> getRecentDayReportByProjectId(@Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr, @Param("userId") String userId, @Param("projectId") String projectId, @Param("dayTypeReport") int dayTypeReport, @Param("operationOperateReport") int operationOperateReport);

    /**
     * 获取当天管辖下的部门所有人的总个数
     * @param dataDeptIdList
     * @return
     */
    Integer getSumCount(@Param("dataDeptIdList") List<String> dataDeptIdList);

    /**
     * 获取当天管辖下的部门所有人已经填写日报的个数
     * @param deptId
     * @param reportTime
     * @param dayTypeReport
     * @param operationOperateReport
     * @return
     */
    Integer getSignedCount(@Param("dataDeptIdList") List<String> dataDeptIdList, @Param("deptId") String deptId, @Param("reportTime") String reportTime, @Param("dayTypeReport") int dayTypeReport, @Param("operationOperateReport") int operationOperateReport);

    /**
     * 查询当天当人所有的日报/周报的Id
     * @param userId
     * @param reportType
     * @param reportTime
     * @return
     */
    List<Long> getLoginUserToDayReportIdByReportType(@Param("userId") String userId, @Param("reportType") Integer reportType, @Param("reportTime") Date reportTime);

    void batchDeleteByIdList(@Param("idList") List<Long> toDayAllReportIdByTypeList);
}