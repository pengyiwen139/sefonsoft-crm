package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (ProjectSaleRef)实体类
 *
 * @author PengYiWen
 * @since 2019-11-16 09:46:39
 */
@ApiModel("项目销售关联表更新实体类")
@Accessors(chain = true)
@Data
@Getter
public class ProjectSaleRefUpdateDTO implements Serializable {
    private static final long serialVersionUID = -98676102871573453L;

    /**
    项目编号,索引，关联project_info表project_id 
    */
    @NotNull(message = "项目编号列表" + NO_PARAM)
    @ApiModelProperty("项目编号列表，数组")
    private List<String> projectIdList;
        
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
    @ApiModelProperty(hidden = true)
    private Date lastTime;

}