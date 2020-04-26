package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (SysRole)实体类
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:57
 */
@Data
public class SysRoleQueryDTO implements Serializable {

    private static final long serialVersionUID = -906301893062343464L;

    /**
    主键 
    */
    @ApiModelProperty(value="主键")
    private Long id;
        
    /**
    角色编号 
    */
    @ApiModelProperty(value="角色编号")
    private String roleId;
        
    /**
    角色名称 
    */
    @ApiModelProperty(value="角色名称")
    private String roleName;

    /**
    角色类型
    */
    @ApiModelProperty(value="角色类型，1操作角色类型，2数据角色类型")
    private Integer roleType;

    /**
     是否默认选中，0不选中，1选中
    */
    @ApiModelProperty(value="是否默认选中，0不选中，1选中")
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
     应用名称
     */
    @ApiModelProperty(value="应用名称")
    private String appName;

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

    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
    private Integer rows;

    public Integer getPage() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

}