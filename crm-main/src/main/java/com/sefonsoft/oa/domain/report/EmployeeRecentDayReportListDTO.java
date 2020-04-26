package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 封装获取最近添加的日报信息
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("封装获取最近添加的日报信息")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRecentDayReportListDTO implements Serializable {

    private static final long serialVersionUID = -7168116014607211837L;

    /**
     开始日期
    */
    @ApiModelProperty("开始日期")
    private String startDate;

    /**
     结束日期
    */
    @ApiModelProperty("结束日期")
    private String stopDate;

    /**
     周报列表
     */
    @ApiModelProperty("周报列表")
    private List<EmployeeReportDetailDTO> recentlyDayReportList;

}