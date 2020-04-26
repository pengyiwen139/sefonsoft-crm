package com.sefonsoft.oa.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 周日报表添加实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("日报表添加实体类")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportStatistics implements Serializable {

    private static final long serialVersionUID = 1982827395679856204L;

    /**
     * 员工工号
     */
    @ApiModelProperty(hidden = true)
    private String employeeId;

    /**
     * 员工姓名
     */
    @ApiModelProperty(value = "员工姓名", example = "employeeName")
    private String employeeName;


    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", example = "deptName")
    private String deptName;

    /**
     * 统计数量
     */
    @ApiModelProperty(value = "统计数量", example = "1")
    private Integer count;

}