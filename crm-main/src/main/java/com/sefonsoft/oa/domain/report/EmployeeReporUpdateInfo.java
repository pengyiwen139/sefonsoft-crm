package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 周日报表修改实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("周日报表修改实体类")
@Accessors(chain = true)
@Data
public class EmployeeReporUpdateInfo implements Serializable {

    private static final long serialVersionUID = 623869457134878634L;

    /**
    主键 
    */
    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty(value = "主键")
    private Long id;
        
    /**
    员工工号 
    */
    @Size(min = 1, max = 16, message = "员工编号最长不能超过16位")
    @NotBlank(message = "主键" + NO_PARAM)
    @ApiModelProperty(value = "员工工号")
    private String employeeId;
        
    /**
    报表类型：1日报，2周报 
    */
    @NotNull(message = "报表类型" + NO_PARAM)
    @ApiModelProperty(value = "报表类型：1日报，2周报", example = "1")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @NotNull(message = "报告日期" + NO_PARAM)
    @ApiModelProperty(value = "报告日期")
    private Date reportTime;
        
    /**
    操作类型,1线下，2电话，3操作
    */
    @NotNull(message = "操作类型" + NO_PARAM)
    @ApiModelProperty(value = "操作类型,1线下，2电话，3操作",example = "2")
    private Integer operationType;
        
    /**
    项目编号 
    */
    @NotBlank(message = "项目编号" + NO_PARAM)
    @Size(min = 1, max = 32, message = "项目编号长度超出范围，长度应该小于32个字符")
    @ApiModelProperty(value = "项目编号", example = "WY201912040003")
    private String projectId;
        
    /**
    联系人 
    */
    @NotNull(message = "联系人" + NO_PARAM)
    @ApiModelProperty(value = "联系人", example = "417")
    private Long contactId;

    /**
     跟进详情
     */
    @ApiModelProperty(value = "跟进详情", example = "跟进详情示例")
    @NotBlank(message = "跟进详情" + NO_PARAM)
    private String followDetails;

    /**
    下步计划 
    */
    @ApiModelProperty(value = "下步计划", example = "下步计划示例")
    private String nextStepPlan;
        
    /**
    操作员 
    */
    @ApiModelProperty(hidden = true)
    private String operator;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty(hidden = true)
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}