package com.sefonsoft.oa.domain.user;

import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.system.emun.Grade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysUser)实体类
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
@ApiModel(value = "用户信息")
@Data
@Accessors(chain = true)
public class LoginUserDTO implements Serializable {

    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private String id;

    /**
    * 工牌号
    */
    @NotBlank(message = "用户名" + NO_PARAM)
    @ApiModelProperty(value="用户编号工牌号")
    private String userId;

    /**
    * 密码，不低于6位
    */
    @NotBlank(message = "密码" + NO_PARAM)
    @ApiModelProperty(value="密码，默认123456")
    private String password;

    @ApiModelProperty(value="是否修改过密码，1未修改，2已修改")
    private String hasChangePwd;

    /**
    * 员工姓名
    */
    @ApiModelProperty(value="员工姓名")
    private String employeeName;

    /**
    * 部门编号
    */
    @ApiModelProperty(value="部门编号")
    private String deptId;

    /**
    * 部门名称
    */
    @ApiModelProperty(value="部门名称")
    private String deptName;

    /**
    * 在职状态，1在职，2离职
    */
    @ApiModelProperty(value="在职状态，1在职，2离职")
    private Integer jobStatus;

    /**
     * 性别，1：男，2：女
     */
    @ApiModelProperty(value="性别，1：男，2：女")
    private Integer sex;

    /**
    * 职系编号
    */
    @ApiModelProperty(value="职系编号，为ZX0000的时候为管理者，为XS0001的时候是销售")
    private String gradeId;

    /**
     * 操作角色编号列表
     */
    @ApiModelProperty(value="操作角色编号列表")
    private List<SysRole> menuRoleList;

    /**
     * 数据角色编号列表
     */
    @ApiModelProperty(value="数据角色编号列表")
    private List<SysRole> dataRoleList;

    /**
    * 当前用户的菜单列表
    */
    @ApiModelProperty(value="菜单列表")
    private List<String> menuList;

    /**
     * 当前用户的数据角色下的部门编号列表
     */
    @ApiModelProperty(value="当前用户的数据角色下的部门编号列表")
    private List<String> dataDeptIdList;

    @ApiModelProperty("验证码")
    private String verifyCode;

    /**
     * 是否是领导
     * @return
     */
    public boolean isLD() {
      return this.gradeId == null ?
          false : Grade.LD.getCode().equalsIgnoreCase(this.gradeId);
    }

}