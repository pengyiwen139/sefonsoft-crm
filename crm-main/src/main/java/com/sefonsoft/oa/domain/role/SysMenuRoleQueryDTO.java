package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 新增数据角色实体类
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:57
 */
@Data
@ApiModel("查询菜单角色实体类")
@Accessors(chain = true)
public class SysMenuRoleQueryDTO implements Serializable {


    private static final long serialVersionUID = -584972287305534423L;
    /**
     主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "操作角色类型,1操作类型，2数据类型")
    private Integer roleType;

    /**
    角色编号 
    */
    @ApiModelProperty(value = "角色编号")
    private String roleId;

    /**
    角色名称
    */
    @ApiModelProperty(value="角色名称")
    private String roleName;

    /**
     是否默认选中，0不选中，1选中
     */
    @ApiModelProperty("是否默认选中，0不选中，1选中")
    private Integer hasChecked;
        
    /**
    角色描述 
    */
    @ApiModelProperty(value="角色描述")
    private String remark;
        
    /**
    排序号 
    */
    @ApiModelProperty(value="排序号")
    private Integer orderNum;

    /**
     应用编号
     */
    @ApiModelProperty(value="应用编号")
    private String appId;

    /**
    操作时间 
    */
    @ApiModelProperty("操作时间")
    private String modifiedTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("部门集合")
    private List<SysMenuRoleRefQueryDTO> permissionList;

}