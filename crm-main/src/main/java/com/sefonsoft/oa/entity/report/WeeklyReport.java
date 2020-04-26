package com.sefonsoft.oa.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * 周报表添加实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("周报表添加实体类")
@Accessors(chain = true)
@Data
public class WeeklyReport implements Serializable {

    private static final long serialVersionUID = 1982827395679856204L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 员工工号
     */
    @ApiModelProperty(hidden = true)
    private String employeeId;

    /**
     * 部门id
     */
    @ApiModelProperty(hidden = true)
    private String deptId;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", example = "deptName")
    private String deptName;

    /**
     * 员工姓名
     */
    @ApiModelProperty(value = "员工姓名", example = "employeeName")
    private String employeeName;

    /**
     * 部门总员工数
     */
    @NotNull(message = "部门总员工数" + NO_PARAM)
    @ApiModelProperty(value = "部门总员工数")
    private Integer employeeCount;

    /**
     * 本周总日报数
     */
    @NotNull(message = "本周总日报数" + NO_PARAM)
    @ApiModelProperty(value = "本周总日报数")
    private Integer reportCount;

    /**
     * 已读日报数
     */
    @NotNull(message = "已读日报数" + NO_PARAM)
    @ApiModelProperty(value = "已读日报数")
    private Integer readCount;

    /**
     * 报告日期
     */
    @NotNull(message = "报告日期" + NO_PARAM)
    @ApiModelProperty(value = "报告日期")
    private String reportTime;

    /**
     * 周报开始日期
     */
    @NotNull(message = "周报开始日期" + NO_PARAM)
    @ApiModelProperty(value = "周报开始日期")
    private String startDate;

    /**
     * 周报结束日期
     */
    @NotNull(message = "周报结束日期" + NO_PARAM)
    @ApiModelProperty(value = "周报结束日期")
    private String endDate;

    /**
     摘要
     */
    @ApiModelProperty(value = "摘要",example = "digest")
    private String digest;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "desc")
    private String desc;

    /**
     * 最后操作时间
     */
    @ApiModelProperty(hidden = true)
    private Date lastTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;


    /**
     * 包含的日报列表
     */
    @ApiModelProperty("包含的日报列表")
    private List<DailyReport> dailyReportList;

}