package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * 周报列表(WeeklyReportQueryDTO)实体类
 *
 * @author Aron
 * @since 2019-12-09 15:21:59
 */
@ApiModel("日报表查询实体类")
@Accessors(chain = true)
@Data
public class WeeklyReportQueryDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;

    /**
     * 员工对应职系
     */
    @ApiModelProperty("搜索关键词")
    private String keyWord;

    /**
     * 年份
     */
    @ApiModelProperty("年份2019")
    private String year;
    /**
     * 月份
     */
    @ApiModelProperty("月份，1，2...")
    private String month;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    private List<String> deptIds;

    /**
     * 开始日期
     */
    @ApiModelProperty("2020-02-23")
    private String startDay;

    /**
     * 开始日期
     */
    @ApiModelProperty("2020-02-23")
    private String endDay;


    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @Min(value = 0, message = "limit" + MIN_PARAM + 0)
    private Integer limit;



}