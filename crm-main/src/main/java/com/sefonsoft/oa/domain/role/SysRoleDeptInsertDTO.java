package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增数据角色与部门关联实体类
 *
 * @author PengYiWen
 * @since 2019-12-17 20:03:11
 */
@ApiModel("新增数据角色与部门关联实体类")
@Accessors(chain = true)
@Data
public class SysRoleDeptInsertDTO implements Serializable {

    private static final long serialVersionUID = -90215910240728559L;
        
    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    角色编号 
    */
    @ApiModelProperty(hidden = true)
    private String roleId;
        
    /**
    部门编号 
    */
    @ApiModelProperty("部门编号")
    private String deptId;

    @ApiModelProperty(hidden = true)
    private Date createTime;


}