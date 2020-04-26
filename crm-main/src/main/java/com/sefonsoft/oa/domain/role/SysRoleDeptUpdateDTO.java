package com.sefonsoft.oa.domain.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 修改数据角色与部门关联实体类
 *
 * @author PengYiWen
 * @since 2019-12-17 20:03:11
 */
@ApiModel("修改数据角色与部门关联实体类")
@Accessors(chain = true)
@Data
public class SysRoleDeptUpdateDTO implements Serializable {

    private static final long serialVersionUID = 72058827111286977L;

    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    角色编号 
    */
    @Size(max = 32, min = 1, message = "角色编号长度超出范围，长度应该小于32个字符")
    @ApiModelProperty(hidden = true)
    private String roleId;
        
    /**
    部门编号
    */
    @Size(max = 32, min = 1, message = "部门编号长度超出范围，长度应该小于32个字符")
    @ApiModelProperty("部门编号")
    private String deptId;

    @ApiModelProperty(hidden = true)
    private Date createTime;


}