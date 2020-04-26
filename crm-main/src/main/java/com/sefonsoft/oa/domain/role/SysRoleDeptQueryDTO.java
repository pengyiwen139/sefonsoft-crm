package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 查询数据角色与部门关联实体类
 *
 * @author PengYiWen
 * @since 2019-12-17 20:03:11
 */
@ApiModel("查询数据角色与部门关联实体类")
@Accessors(chain = true)
@Data
public class SysRoleDeptQueryDTO implements Serializable {

    private static final long serialVersionUID = -2851039929301068220L;

    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    角色编号 
    */
    @ApiModelProperty("角色编号")
    private String roleId;
        
    /**
    部门编号
    */
    @ApiModelProperty("部门编号")
    private String deptId;

}