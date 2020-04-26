package com.sefonsoft.oa.entity.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限实体类
 *
 * @author PengYiWen
 * @since 2019-12-17 19:07:47
 */
@ApiModel("权限实体类")
@Accessors(chain = true)
@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 878831296479504876L;
        
    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    菜单编号 
    */
    @ApiModelProperty("菜单编号")
    private String menuId;
        
    /**
    菜单名称 
    */
    @ApiModelProperty("菜单名称")
    private String menuName;
        
    /**
    父菜单编号，默认为0,0表示顶级节点 
    */
    @ApiModelProperty("父菜单编号，默认为0,0表示顶级节点")
    private String parentId;
        
    /**
    权限标识 
    */
    @ApiModelProperty("权限标识")
    private String perms;
        
    /**
    是否默认选中，0不选中，1选中 
    */
    @ApiModelProperty("是否默认选中，0不选中，1选中")
    private Integer hasChecked;
        
    /**
    显示顺序 
    */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
        
    /**
    请求地址 
    */
    @ApiModelProperty("请求地址")
    private String url;
        
    /**
    菜单类型（M目录 C菜单 F按钮） 
    */
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;
        
    /**
    菜单状态（0显示 1隐藏） 
    */
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;
        
    /**
    应用编号 
    */
    @ApiModelProperty("应用编号")
    private String appId;
        
    /**
    菜单图标 
    */
    @ApiModelProperty("菜单图标")
    private String icon;
        
    /**
    描述 
    */
    @ApiModelProperty("描述")
    private String remark;
        
    /**
    操作时间 
    */
    @ApiModelProperty("操作时间")
    private Date modifiedTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

}