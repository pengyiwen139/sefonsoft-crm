package com.sefonsoft.oa.domain.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class SysPermissionUpdateDTO implements Serializable {
    private static final long serialVersionUID = 444392234429652611L;


    /**
     主键
     */
    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty("主键")
    private Long id;

    /**
     菜单编号
     */
    @NotBlank(message = "菜单编号" + NO_PARAM)
    @ApiModelProperty("菜单编号")
    private String menuId;

    /**
     菜单名称
     */
    @NotBlank(message = "菜单名称" + NO_PARAM)
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     父菜单编号，默认为0,0表示顶级节点
     */
    @NotBlank(message = "父菜单编号" + NO_PARAM)
    @ApiModelProperty("父菜单编号，默认为0,0表示顶级节点 ")
    private String parentId;

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
    @ApiModelProperty("菜单状态（0显示 1隐藏） ")
    private String visible;

    /**
     权限标识
     */
    @ApiModelProperty("权限标识")
    private String perms;

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
    @ApiModelProperty(hidden = true)
    private Date modifiedTime;

    /**
     创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}