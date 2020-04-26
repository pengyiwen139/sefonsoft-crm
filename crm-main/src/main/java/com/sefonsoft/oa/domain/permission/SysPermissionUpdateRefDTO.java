package com.sefonsoft.oa.domain.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysPermission)实体类
 *
 * @author PengYiWen
 * @since 2019-11-09 11:37:15
 */
@Data
@Accessors(chain = true)
public class SysPermissionUpdateRefDTO implements Serializable {

    private static final long serialVersionUID = 6654351572760686824L;

    /**
     主键
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty("菜单编号")
    @Size(max = 32, min = 1, message = "菜单编号长度超出范围，长度应该小于32个字符")
    @NotBlank(message = "菜单编号" + NO_PARAM)
    private String menuId;

    /**
     角色编号
     */
    @Size(max = 32, min = 1, message = "角色编号长度超出范围，长度应该小于32个字符")
    @ApiModelProperty(hidden = true)
    private String roleId;

    /**
     创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}