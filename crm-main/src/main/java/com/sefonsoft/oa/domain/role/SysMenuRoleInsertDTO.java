package com.sefonsoft.oa.domain.role;

import com.sefonsoft.oa.domain.permission.SysPermissionInsertDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.PermissionConstant.DEFAULT_APP_ID;
import static com.sefonsoft.oa.system.constant.PermissionConstant.MENU_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysRole)实体类
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:57
 */
@Data
@ApiModel("新增操作角色实体类")
@Accessors(chain = true)
public class SysMenuRoleInsertDTO implements Serializable {

    private static final long serialVersionUID = -98798343619575790L;
        
    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(value = "操作角色类型", hidden = true)
    private Integer roleType = MENU_ROLE_TYPE;

    /**
    角色编号 
    */
    @ApiModelProperty(value = "角色编号", hidden = true)
    private String roleId;
        
    /**
    角色名称 
    */
    @NotBlank(message = "角色名称" + NO_PARAM)
    @ApiModelProperty(value="角色名称")
    private String roleName;
        
    /**
    角色描述 
    */
    @Size(max = 128, message = "角色描述最长为128字符")
    @ApiModelProperty(value="角色描述")
    private String remark;

    /**
     是否默认选中，0不选中，1选中
     */
    @ApiModelProperty("是否默认选中，0不选中，1选中")
    private Integer hasChecked;

    /**
    排序号 
    */
    @ApiModelProperty(value="排序号")
    private Integer orderNum;

    /**
     应用编号
     */
    @ApiModelProperty(value="应用编号", hidden = true)
    private String appId = DEFAULT_APP_ID;

    /**
    操作时间 
    */
    @ApiModelProperty(hidden = true)
    private Date modifiedTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @Size(min = 1, message = "菜单" + NO_PARAM)
    @NotNull(message = "菜单集合" + NO_PARAM)
    @ApiModelProperty("菜单集合")
    List<SysPermissionInsertDTO> permissionList;

}