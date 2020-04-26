package com.sefonsoft.oa.entity.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据角色与部门关联实体类
 *
 * @author PengYiWen
 * @since 2019-12-18 15:27:20
 */
@ApiModel("数据角色与部门关联实体类")
@Accessors(chain = true)
@Data
public class SysRoleDept implements Serializable {

    private static final long serialVersionUID = 581382913470901217L;
        
    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    角色编号 
    */
    @ApiModelProperty("角色编号")
    private String roleId;
        
    /**
    部门编号 
    */
    @ApiModelProperty("部门编号")
    private String deptId;

    private Date createTime;

}