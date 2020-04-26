package com.sefonsoft.oa.dao.report;

import com.sefonsoft.oa.domain.report.EmployeeReportQueryDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;

import java.util.List;

/**
 * 周日报表(EmployeeReportInfo)表数据库访问层
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
public interface EmployeeReportDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmployeeReportInfo queryById(Long id);

    /**
     * 查询为填写日报列表
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    List<EmployeeReportQueryDTO> noFillReport(EmployeeReportQueryDTO employeeReportQueryDTO);

    /**
     * 查询为填写日报员工总数
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    int noFillReportTotal(EmployeeReportQueryDTO employeeReportQueryDTO);

    /**
     * 查询为填写日报列表
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    List<EmployeeReportQueryDTO> noFillReports(EmployeeReportQueryDTO employeeReportQueryDTO);

    /**
     * 查询为填写日报员工总数
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    int noFillReportTotals(EmployeeReportQueryDTO employeeReportQueryDTO);



}