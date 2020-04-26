package com.sefonsoft.oa.entity.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (ProjectAmountDevideRefInsertDTO)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 17:22:07
 */
@ApiModel("")
@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class ProjectAmountDevideRef implements Serializable {
    private static final long serialVersionUID = 170839028233232217L;
        
    /**
    自增长 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    表project_info字段project_id 
    */
    @ApiModelProperty("表project_info字段project_id")
    private String projectId;
        
    /**
    销售人工号 
    */
    @ApiModelProperty("销售人工号")
    private String employeeId;

    /**
     销售人名称
     */
    @ApiModelProperty("销售人名称")
    private String employeeName;

    /**
    业绩分割 
    */
    @ApiModelProperty("业绩分割")
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

}