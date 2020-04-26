package com.sefonsoft.oa.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 周日报表(EmployeeReportInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("周日报表(EmployeeReportInfo)实体类")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReportInfo implements Serializable {
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
    报表类型：1日报，2周报 
    */
    @ApiModelProperty("报表类型：1日报，2周报")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @ApiModelProperty("报告日期")
    private Date reportTime;
        
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
    联系人 
    */
    @ApiModelProperty("联系人")
    private Long contactId;

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
    操作员 
    */
    @ApiModelProperty("操作员")
    private String operator;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty("最后操作时间")
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

}