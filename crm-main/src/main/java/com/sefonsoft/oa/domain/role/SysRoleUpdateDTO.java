package com.sefonsoft.oa.domain.role;

import com.sefonsoft.oa.domain.permission.SysPermissionInsertDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysRole)实体类
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:57
 */
@Data
@Accessors(chain = true)
public class SysRoleUpdateDTO implements Serializable {
    private static final long serialVersionUID = -98798343619575790L;
        
    /**
    主键 
    */
    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty(value="主键")
    private Long id;
        
    /**
    角色编号 
    */
    @NotBlank(message = "角色编号" + NO_PARAM)
    @ApiModelProperty(value="角色编号")
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
    @NotBlank(message = "应用编号" + NO_PARAM)
    @ApiModelProperty(value="应用编号")
    private String appId;

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

    @NotNull(message = "权限子集集合" + NO_PARAM)
    @ApiModelProperty("权限子集集合")
    List<SysPermissionInsertDTO> permissionList;

}