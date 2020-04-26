package com.sefonsoft.oa.domain.head;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/13 13:50
 * @description：首页查询参数
 * @modified By：
 */
@ApiModel(value = "首页查询参数")
@Accessors(chain = true)
@Data
public class HeadQueryDTO {

    @ApiModelProperty("年份")
    private String year;

    @ApiModelProperty("上下半年，上半年1，下半年2")
    private String halfYear;

    @ApiModelProperty("季度，1一季度，2二季度，3三季度，4四季度")
    private String quarter;

    @ApiModelProperty("月份，1一月，2二月......")
    private String month;

    @ApiModelProperty("部门编号")
    private String deptId;

    @ApiModelProperty("员工编号")
    private String employeeId;

}