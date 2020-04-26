package com.sefonsoft.oa.domain.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysPermission)实体类
 *
 * @author PengYiWen
 * @since 2019-11-09 11:37:15
 */
@Data
@Accessors(chain = true)
public class SysPermissionInsertDTO implements Serializable {

    private static final long serialVersionUID = 444392234429652611L;

    /**
    菜单编号 
    */
    @ApiModelProperty("菜单编号")
    private String menuId;

    /**
     角色编号
    */
    @ApiModelProperty(value = "角色编号", hidden = true)
    private String roleId;

    /**
     创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}