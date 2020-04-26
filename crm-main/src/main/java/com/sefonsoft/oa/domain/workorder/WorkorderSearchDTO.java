package com.sefonsoft.oa.domain.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * 派工单查询(WorkorderSearchDTO)实体类
 *
 * @author Aron
 * @since 2019-12-09 15:21:59
 */
@ApiModel("派工单查询实体类")
@Accessors(chain = true)
@Data
public class WorkorderSearchDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;

    /**
     * 派工单号
     */
    @ApiModelProperty("派工单号")
    private String pgdId;

    /**
     * 销售员工工号
     */
    @ApiModelProperty("销售员工工号")
    private List<String> saleEmployeeIdList;

    /**
     * 售前员工工号
     */
    @ApiModelProperty("售前员工工号")
    private List<String> preEmployeeIdList;

    /**
     * 销售部门编号
     */
    @ApiModelProperty(value = "销售部门编号")
    private List<String> deptIds;

    /**
     * 售前部门编号
     */
    @ApiModelProperty(value = "售前部门编号")
    private List<String> preDeptIds;


    /**
     * 销售评价状态,0待评价，1已评价
     */
    @ApiModelProperty("销售评价状态,0待评价，1已评价")
    private Integer saleCommentStatus;

    /**
     * 状态：1待审批、2已拒绝、3已审批, 4已完成， 5已反馈
     */
    @ApiModelProperty("状态：1待审批、2已拒绝、3已审批, 4已完成， 5已反馈，如[1，2，3，4, 5]")
    private List<Integer> approvalStatusList;

    /**
     * 项目阶段
     */
    @ApiModelProperty("项目阶段")
    private List<String> projectPhaseList;

    /**
     * 项目行业
     */
    @ApiModelProperty("项目行业")
    private List<String> projectIndustryList;

    /**
     * 涉及产品
     */
    @ApiModelProperty("涉及产品")
    private String productInfo;

    /**
     * 搜索关键词
     */
    @ApiModelProperty("搜索关键词")
    private String keyWord;

    /**
     * 开始日期
     */
    @ApiModelProperty("2020-02-23")
    private String startDate;

    /**
     * 开始日期
     */
    @ApiModelProperty("2020-02-23")
    private String endDate;


    /**
     * 页数
     */
    @ApiModelProperty(value = "页数", example = "1")
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value = "每页个数", example = "10")
    @Min(value = 0, message = "limit" + MIN_PARAM + 0)
    private Integer limit;

}