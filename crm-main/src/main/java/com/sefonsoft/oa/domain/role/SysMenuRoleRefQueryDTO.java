package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 查询菜单角色关联实体类
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:57
 */
@Data
@ApiModel("查询菜单角色关联实体类")
@Accessors(chain = true)
public class SysMenuRoleRefQueryDTO implements Serializable {

    private static final long serialVersionUID = -7373474194993419729L;

    /**
     主键
     */
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("菜单编号")
    private String menuId;

    /**
     角色编号
     */
    @ApiModelProperty("角色编号")
    private String roleId;

}