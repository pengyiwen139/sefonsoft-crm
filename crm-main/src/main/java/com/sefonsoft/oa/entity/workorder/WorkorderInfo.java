package com.sefonsoft.oa.entity.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
public class WorkorderInfo {

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
     * 创建人id
     */
    @ApiModelProperty("创建人id")
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
     * 项目id
     */
    @ApiModelProperty("项目id")
    private String projectId;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    private String projectName;

    /**
     * 商机id
     */
    @ApiModelProperty("商机id:与biz_opports表中的id关联")
    private String bizOpportsId;

    /**
     * 项目阶段
     */
    @ApiModelProperty("项目阶段")
    private String projectPhase;

    /**
     * 项目行业
     */
    @ApiModelProperty("项目行业")
    private String projectIndustry;

    /**
     * 涉及产品
     */
    @ApiModelProperty("涉及产品")
    private String productInfoList;

    /**
     * 派工单摘要
     */
    @ApiModelProperty("自定义派工单摘要")
    private String digest;

    /**
     * 技术接口人姓名
     */
    @ApiModelProperty("技术接口人姓名")
    private String connectorName;
    /**
     * 技术接口人职位
     */
    @ApiModelProperty("技术接口人职位")
    private String connectorJob;
    /**
     * 技术接口人联系方式
     */
    @ApiModelProperty("技术接口人联系方式")
    private String connectorTel;

    /**
     * 申请资源类型：1售前派工单。
     */
    @ApiModelProperty("申请资源类型：1售前派工单。")
    private int applyType;

    /**
     * 支持单位
     */
    @ApiModelProperty("支持单位")
    private String supportedUnit;

    /**
     * 需求方式,与需求方式：demand_way_info对应
     */
    @ApiModelProperty("需求方式,与需求方式：demand_way_info对应")
    private int demandWayId;

    /**
     * 交流地点
     */
    @ApiModelProperty("交流地点")
    private String location;

    /**
     * 交流时间
     */
    @ApiModelProperty("交流时间")
    private String communicationTime;


    /**
     * 目标与内容
     */
    @ApiModelProperty("目标与内容")
    private String contentGoal;
    /**
     * 本次支持的对象规模和人员构成
     */
    @ApiModelProperty("本次支持的对象规模和人员构成")
    private String objectSize;
    /**
     * 本次支持的对象技术能力评估,与object_ability_info对应
     */
    @ApiModelProperty("本次支持的对象技术能力评估,与object_ability_info对应")
    private int objectAbilityId;
    /**
     * 派工单备注
     */
    @ApiModelProperty("派工单备注")
    private String remark;
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
     * 是否删除：0未删除 1已删除
     */
    @ApiModelProperty("是否删除：0未删除 1已删除")
    private long isDelete;

//    /**
//     * 派工单商机关联基本信息
//     */
//    @ApiModelProperty("派工单商机关联基本信息")
//    private BizOpportInfo bizOpportInfo;

//    /**
//     * 审批人
//     */
//    @ApiModelProperty("审批人")
//    private WorkorderApprovalInfo approvals;

    /**
     * 审批人
     */
    @ApiModelProperty("派工单审批人")
    private String approvalEmployeeId;

    /**
     * 状态：1待审批、2已拒绝、3已审批, 4已完成， 5已反馈
     */
    @ApiModelProperty("状态：1待审批、2已拒绝、3已审批, 4已完成， 5已反馈")
    private int approvalStatus;
    /**
     * 审批描述
     */
    @ApiModelProperty("派工单审批描述")
    private String approvalDesc;

    /**
     * 售前列表
     */
    private List<WorkorderFeedbackPreInfo> workorderFeedbackPreInfo;

    /**
     * 销售
     */
    private List<WorkorderFeedbackSaleInfo> workorderFeedbackSaleInfo;


}
