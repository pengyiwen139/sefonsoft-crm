package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (ProjectCheckInfoInsertDTO)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 17:23:21
 */
@ApiModel("项目审核信息新增实体类")
@Accessors(chain = true)
@Data
public class ProjectCheckInfoInsertDTO implements Serializable {
    private static final long serialVersionUID = 540720404032774821L;

    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    项目编号，索引，关联project_info表project_id 
    */
    @ApiModelProperty(hidden = true)
    private String projectId;
        
    /**
    审核人工号，索引，关联sys_employee表employee_id 
    */
    @ApiModelProperty(hidden = true)
    private String employeeId;
        
    /**
    审核意见 
    */
    @ApiModelProperty(hidden = true)
    private String opinion;
        
    /**
    审核状态,1未审核，2审核通过，3已驳回 
    */
    @ApiModelProperty("审核状态,1未审核，2审核通过，3已驳回")
    private Integer checkStatus;
        
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