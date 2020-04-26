package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 周日报表(EmployeeReportInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("周日报表(EmployeeReportInfo)列表实体类")
@Accessors(chain = true)
@Data
public class EmployeeReportListDTO implements Serializable {

    private static final long serialVersionUID = -3719393364984287824L;

    /**
    员工工号 
    */
    @ApiModelProperty("员工工号")
    private String employeeId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String employeeName;

    /**
     * 部门编号
     */
    @ApiModelProperty(value="部门编号")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String deptName;

    /**
    报告日期 
    */
    @ApiModelProperty("报告日期")
    private String reportTime;

    /**
     日报列表
     */
    @ApiModelProperty("日报列表")
    private List<EmployeeReportDetailDTO> dayReportList;

    /**
     周报列表
     */
    @ApiModelProperty("周报列表")
    private List<EmployeeReportDetailDTO> weekReportList;


}