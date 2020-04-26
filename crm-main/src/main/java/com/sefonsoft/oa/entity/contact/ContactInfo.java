package com.sefonsoft.oa.entity.contact;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (ContactInfo)实体类
 *
 * @author Aron
 * @since 2019-11-16 17:26:42
 */
@Data
@Accessors(chain = true)
public class ContactInfo implements Serializable {
    private static final long serialVersionUID = -86229930101467649L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty("客户编号")
    private String customerId;
    /**
     * 导入类型
     */
    @ApiModelProperty("导入类型")
    private long importType;
    /**
     * 姓名
     */
    @ApiModelProperty("客户姓名：必填")
    private String contactName;
    /**
     * 性别，1男, 2女
     */
    @ApiModelProperty("性别，1男, 2女")
    private Integer contactSex;
    /**
     * 1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它
     */
    @ApiModelProperty("联系人充当角色1关键决策人 2技术负责人 3直接用户 4核心支持者 5其它")
    private Integer role;
    /**
     * 部门
     */
    @ApiModelProperty("客户联系人部门：必填")
    private String deptName;
    /**
     * 职务
     */
    @NotBlank(message = "职务" + NO_PARAM)
    @ApiModelProperty("客户联系人职务：必填")
    private String job;
    /**
     * 电话
     */
    @NotBlank(message = "联系人电话" + NO_PARAM)
    @ApiModelProperty("客户联系人电话：必填")
    private String telphone;
    /**
     * 邮箱
     */
//    @NotBlank(message = "邮箱" + NO_PARAM)
    @ApiModelProperty("客户联系人邮箱：必填")
    private String email;
    /**
     * 工号，索引，关联sys_employee表employee_id
     */
    @ApiModelProperty("共有人或者负责人工号，不能为空")
    private String employeeId;
    /**
     * 操作员，关联sys_user表user_id
     */
    @ApiModelProperty("操作员，不能为空")
    private String operator;

    /**
     * 最后操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty("最后操作时间")
    private Date lastTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("办公座机")
    private String officePlane;

    @ApiModelProperty("毕业学校")
    private String university;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("家庭状况")
    private String familyInfo;

    @ApiModelProperty("其他")
    private String other;

    @ApiModelProperty("生日")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;


    
    
    /**
     * 兼容 Aron 大神代码
     * @return
     */
    public Long getIds() {
      return id;
    }
  
    /**
     * 兼容 Aron 大神代码
     * @return
     */
    public void setIds(Long ids) {
      this.id = ids;
    }
    
    
    /**
     * 兼容 Aron 大神代码
     * @return
     */
    public String getDeptNames() {
        return deptName;
    }

    /**
     * 兼容 Aron 大神代码
     * @return
     */
    public void setDeptNames(String deptNames) {
        this.deptName = deptNames;
    }



}