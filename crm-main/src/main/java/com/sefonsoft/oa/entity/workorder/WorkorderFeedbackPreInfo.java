package com.sefonsoft.oa.entity.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
@ApiModel("工单基础信息实体")
@Data
public class WorkorderFeedbackPreInfo {


    /**
     * 主键
     */
    private Long id;

    /**
     * 派工单编号：前缀+日期+流水（4位）
     */
    @ApiModelProperty("派工单编号：前缀+日期+流水（4位）")
    private String pgdId;

    /**
     * 员工id
     */
    @ApiModelProperty("员工id")
    private String employeeId;

    /**
     * 创建人名字
     */
    @ApiModelProperty("创建人名字")
    private String employeeName;

    /**
     * 部门id
     */
    @ApiModelProperty("创建人id")
    private String deptId;

    /**
     * 部门名字
     */
    @ApiModelProperty("部门名字")
    private String deptName;

    /**
     * 售前工作时长
     */
    @ApiModelProperty("售前工作时长")
    private String spendTime;

    /**
     * 工作内容
     */
    @ApiModelProperty("工作内容")
    private String workConcent;

    /**
     * 需求一致性：1一致，2偏差
     */
    @ApiModelProperty("需求一致性：1一致，2偏差")
    private String requireConsistency;


    /**
     * 能力匹配度：1符合、2不符合
     */
    @ApiModelProperty("能力匹配度：1符合、2不符合")
    private String capabilityMatching;

    /**
     * 商务关系：1好、2中、3差
     */
    @ApiModelProperty("商务关系：1好、2中、3差")
    private String businessRelation;


    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String evaluationDesc;

    /**
     * 评价状态：0待评价，1已评价
     */
    @ApiModelProperty("评价状态：0待评价，1已评价")
    private Integer preCommentStatus;

    /**
     * 是否废弃
     */
    @ApiModelProperty("是否废弃")
    private Integer isDiscard;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date modifiedTime;


    /**
     * 派工单信息
     */
    @ApiModelProperty("派工单信息")
    private WorkorderInfo workorderInfo;




}
