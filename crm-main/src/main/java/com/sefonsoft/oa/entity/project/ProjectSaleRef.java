package com.sefonsoft.oa.entity.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (ProjectSaleRef)实体类
 *
 * @author PengYiWen
 * @since 2019-11-16 09:46:39
 */
@ApiModel("项目销售关联表实体类")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSaleRef implements Serializable {
    private static final long serialVersionUID = -98676102871573453L;
        
    /**
    自增长 
    */
    @ApiModelProperty("自增长")
    private Long id;
        
    /**
    项目编号,索引，关联project_info表project_id 
    */
    @ApiModelProperty("项目编号,索引，关联project_info表project_id")
    private String projectId;
        
    /**
    负责人工号,索引，关联sys_employee表employee_id 
    */
    @ApiModelProperty("负责人工号,索引，关联sys_employee表employee_id")
    private String employeeId;
        
    /**
    共有人工号,工号逗号分隔，关联sys_employee表employee_id 
    */
    @ApiModelProperty("共有人工号,工号逗号分隔，关联sys_employee表employee_id")
    private String cowner;
        
    /**
    操作员,关联sys_user表user_id 
    */
    @ApiModelProperty("操作员,关联sys_user表user_id")
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