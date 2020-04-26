package com.sefonsoft.oa.domain.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 派工单审批(WorkorderApprovalDTO)实体类
 *
 * @author Aron
 * @since 2019-12-09 15:21:59
 */
@ApiModel("派工单审批实体类")
@Accessors(chain = true)
@Data
public class WorkorderApprovalDTO implements Serializable {
    private static final long serialVersionUID = -97796079402411493L;

    /**
     * 派工单号
     */
    @ApiModelProperty("派工单号")
    private String pgdId;

    /**
     * 审批对象工号
     */
    @ApiModelProperty(value = "员工工号")
    private List<String> targetEmployeeIdList;

    /**
     * 审批描述
     */
    @ApiModelProperty("审批描述")
    private String approvalDesc;


    /**
     * 状态：1待审批、2已拒绝、3已审批
     */
    @ApiModelProperty(value = "状态：1待审批、2已拒绝、3已审批", example = "2")
    private Integer approvalResult;
}