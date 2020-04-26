package com.sefonsoft.oa.domain.workorder.deptconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (WorkorderDeptConfigQueryDTO)实体类
 *
 * @author Peng YiWen
 * @since 2020-04-01 17:25:20
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("派工单部门关联配置update实体类")
public class WorkorderDeptConfigUpdateDTO implements Serializable {

    private static final long serialVersionUID = -6675239639976288219L;

    /**
     * 主键
     */
    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
    * 关联类型，1：售前派工单2：交付派工单(预留)
    */
    @NotNull(message = "关联类型" + NO_PARAM)
    @Max(value = 5, message = "关联类型" + MAX_PARAM + 5)
    @ApiModelProperty(value="关联类型")
    private Integer deptType;

    /**
    * 源部门编号（销售）
    */
    @NotBlank(message = "源部门编号" + NO_PARAM)
    @Size(max = 32, message = "角色描述最长为128字符")
    @ApiModelProperty(value="源部门编号")
    private String deptSource;

    /**
    * 关联部门编号（售前）
    */
    @NotBlank(message = "关联部门编号" + NO_PARAM)
    @Size(max = 32, message = "关联部门编号最长为32字符")
    @ApiModelProperty(value="关联部门编号")
    private String deptDes;

    /**
    * 操作人id
    */
    @ApiModelProperty(hidden = true, value = "操作人id")
    private String operatorEmployeeId;

    /**
    * 修改时间
    */
    @ApiModelProperty(hidden = true, value = "修改时间")
    private Date modifiedTime;

    /**
    * 创建时间
    */
    @ApiModelProperty(hidden = true, value = "创建时间")
    private Date createTime;

}