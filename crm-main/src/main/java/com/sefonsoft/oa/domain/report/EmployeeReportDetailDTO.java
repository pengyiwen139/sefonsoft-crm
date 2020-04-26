package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 周日报表(EmployeeReportInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("周日报表(EmployeeReportInfo)实体类")
@Accessors(chain = true)
@Data
public class EmployeeReportDetailDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;
        
    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
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
    报表类型：1日报，2周报 
    */
    @ApiModelProperty("报表类型：1日报，2周报")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @ApiModelProperty("报告日期")
    private String reportTime;
        
    /**
     操作类型,1线下，2电话，3操作
    */
    @ApiModelProperty("操作类型,1线下，2电话，3操作")
    private Integer operationType;
        
    /**
    项目编号 
    */
    @ApiModelProperty("项目编号")
    private String projectId;

    /**
     项目名称
     */
    @ApiModelProperty("项目名称(展示项)")
    private String projectName;

    /**
    联系人 
    */
    @ApiModelProperty("联系人")
    private Long contactId;

    /**
     指客户方的项目联系人
     */
    @ApiModelProperty("联系人名字")
    private String contactName;

    /**
    跟进详情 
    */
    @ApiModelProperty("跟进详情")
    private String followDetails;
        
    /**
    下步计划 
    */
    @ApiModelProperty("下步计划")
    private String nextStepPlan;

    /**
     联系人电话
    */
    @ApiModelProperty("联系人电话")
    private String telphone;

    /**
    操作员 
    */
    @ApiModelProperty("操作员")
    private String operator;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty("最后操作时间")
    private String lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    private String createTime;

}