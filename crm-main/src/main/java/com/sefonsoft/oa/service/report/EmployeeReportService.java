package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.domain.report.EmployeeReportListDTO;
import com.sefonsoft.oa.domain.report.EmployeeReportQueryDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;

import java.util.List;
import java.util.Map;

/**
 * 周日报表(EmployeeReportInfo)表服务接口
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
public interface EmployeeReportService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmployeeReportInfo queryById(Long id);


    /**
     * 查询周日报列表
     *
     * @param employeeReportQueryDTO 查询条件
     * @return
     */
    Map<Integer, EmployeeReportListDTO> getCondition(EmployeeReportQueryDTO employeeReportQueryDTO)throws Exception;



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