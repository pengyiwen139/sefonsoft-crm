package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (ProjectAmountDevideRefInsertDTO)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 17:22:07
 */
@ApiModel("添加项目业绩分隔所需实体类")
@Accessors(chain = true)
@Data
public class ProjectAmountDevideRefInsertDTO implements Serializable {

    private static final long serialVersionUID = 170839028233232217L;
        
    /**
    自增长 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    表project_info字段project_id 
    */
    @ApiModelProperty(hidden = true)
    private String projectId;
        
    /**
    销售人工号 
    */
    @ApiModelProperty("销售人工号")
    private String employeeId;
        
    /**
    业绩分割 
    */
    @Max(value = 100, message = "业绩分隔不能超过100%")
    @NotNull(message = "业绩分割" + NO_PARAM)
    @ApiModelProperty(value = "业绩分割,最大100，但是总和不能超过100",example = "50")
    private Integer performanceDivision;
        
    /**
    销售金额,单位：万元 
    */
    @ApiModelProperty("销售金额,单位：万元")
    private BigDecimal divisionAmount;
        
    /**
    操作员,关联sys_user表user_id 
    */
    @ApiModelProperty(hidden = true)
    private String operator;

    @ApiModelProperty(hidden = true)
    private Date lastTime;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    public ProjectAmountDevideRefInsertDTO(String projectId, String employeeId, Integer performanceDivision, BigDecimal divisionAmount, String operator, Date lastTime, Date createTime) {
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.performanceDivision = performanceDivision;
        this.divisionAmount = divisionAmount;
        this.operator = operator;
        this.lastTime = lastTime;
        this.createTime = createTime;
    }
}