package com.sefonsoft.oa.entity.sysemployee;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysEmployee)实体类
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
@ApiModel(value = "员工类")
@Data
public class SysEmployee implements Serializable {
    private static final long serialVersionUID = 997840320378431576L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;
    /**
     * 工号
     */
    @NotBlank(message = "员工工号" + NO_PARAM)
    @ApiModelProperty(value="员工工号")
    private String employeeId;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名" + NO_PARAM)
    @ApiModelProperty(value="姓名")
    private String employeeName;
    /**
     * 性别，1：男，2：女
     */
    @NotNull(message = "性别" + NO_PARAM)
    @ApiModelProperty(value="性别")
    private Integer sex;
    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String tel;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * 在职状态，1在职，2离职
     */
    @ApiModelProperty(value="在职状态")
    private Integer jobStatus;
    /**
     * 部门编号
     */
    @NotBlank(message = "部门编号" + NO_PARAM)
    @ApiModelProperty(value="部门编号")
    private String deptId;

    /**
    * 职系编号
    */
    @NotBlank(message = "职系编号" + NO_PARAM)
    @ApiModelProperty(value="职系编号")
    private String gradeId;
    /**
    * 岗位编号
    */
    @NotBlank(message = "职系编号" + NO_PARAM)
    @ApiModelProperty(value="岗位编号")
    private String stationId;
    /**
    * 操作人员用户编号，关联sys_user的业务外键
    */
    @ApiModelProperty(value="操作人员用户编号")
    private String operatorUserId;
    /**
    * 直管领导编号,关联sys_employee表的业务外键
    */
    @ApiModelProperty(value="直管领导编号")
    private String directLeaderId;
    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
    * 操作时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="操作时间")
    private Date modifiedTime;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty("数据角色编号列表")
    private List<String> dataRoleIdList;

    @ApiModelProperty("操作角色编号列表")
    private List<String> menuRoleIdList;

    @ApiModelProperty("是否开通登录账号，0不开通，1开通")
    private Integer hasMakeAccount;

}