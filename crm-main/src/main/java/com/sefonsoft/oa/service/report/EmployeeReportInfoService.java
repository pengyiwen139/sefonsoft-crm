package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.domain.report.EmployeeRecentDayReportListDTO;
import com.sefonsoft.oa.domain.report.EmployeeReporInsertOrUpdateInfo;
import com.sefonsoft.oa.domain.report.EmployeeReportDetailDTO;
import com.sefonsoft.oa.domain.report.ReportDaySignCountDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import com.sefonsoft.oa.system.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 周日报表(EmployeeReportInfo)表服务接口
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
public interface EmployeeReportInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmployeeReportInfo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<EmployeeReportInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param employeeReportInfo 实例对象
     * @return 实例对象
     */
    EmployeeReportInfo insert(EmployeeReportInfo employeeReportInfo);

    /**
     * 修改数据
     * @return 实例对象
     */
    boolean update(List<EmployeeReporInsertOrUpdateInfo> list, LoginUserDTO loginUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);


    /**
     * 批量插入周日报表
     * @param list
     * @param loginUser
     * @return
     */
    boolean batchInsert(List<EmployeeReporInsertOrUpdateInfo> list, LoginUserDTO loginUser);

    /**
     * 给批量插入的字段做校验
     * @param list
     * @return
     */
    Response batchInsertCheck(List<EmployeeReporInsertOrUpdateInfo> list);

    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    List<EmployeeReportDetailDTO> getOne(String reportTime, String userId);

    /**
     * 获取最近添加的日报信息
     * @param loginUser
     * @return
     */
    EmployeeRecentDayReportListDTO getRecentDayList(LoginUserDTO loginUser, Date reportTime);

    /**
     * 获取当天管辖的部门下的填写日报统计
     * @param loginUser
     * @return
     */
    ReportDaySignCountDTO getSignCount(LoginUserDTO loginUser, String reportTime);

    /**
     * 限制日报在指定的某一天的某个小时后不能填写
     * @param reportDayLastHours
     * @return
     */
    Response ifItsTime2AddReport(Integer reportDayLastHours, Date reportTime);

    HashMap<String,String> getMostEarlyAddDateTime(Integer reportDayLastHours, LoginUserDTO loginUser, SimpleDateFormat sdf);
}