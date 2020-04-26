package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (ProjectCheckInfoInsertDTO)实体类
 *
 * @since 2019-12-04 17:23:21
 */
@ApiModel("项目信息过期实体类")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOverdueInfoDTO implements Serializable {

    /**
     主键
     */
    @ApiModelProperty("项目主键，project_info表id")
    private Long id;
    /**
     项目编号，索引，关联project_info表project_id
     */
    @ApiModelProperty("项目编号，索引，project_info表project_id")
    @NotBlank(message = "项目编号" + NO_PARAM)
    private String projectId;
    /**
     审核人工号，索引，关联sys_employee表employee_id
     */
    @ApiModelProperty("销售人员员工编号，索引，关联sys_employee表employee_id")
    @NotBlank(message = "员工编号" + NO_PARAM)
     private String employeeId;
    
    @ApiModelProperty("项目状态")
    private Integer propsId;
    /**
     审核状态,1未审核，2审核通过，0已驳回
     */
    @ApiModelProperty("审核状态数组,1未审核，2审核通过，0已驳回")
    private Integer[] checkStatuses;
    /**
     创建时间
     */
    @ApiModelProperty("项目审批通过时间")
    private Date projectCreateTime;
    
    /**
     * 个人逾期时间
     */
    private Date checkDate;
    /**
     创建时间
     */
    @ApiModelProperty("项目创建时间")
    private Date createTime;
    /**
     项目过期记录总数
     */
    @ApiModelProperty("项目过期记录总数")
    private int projectOverdueCount;

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

    public Integer getOffset() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

    /**
     过期项目标示,0未过期，1过期
     */
    @ApiModelProperty("过期项目标示,0未过期，1过期，2正常跟进")
    private Integer overTimeFlag;
    
    /**
     * 更新时，检查 overTimeFlag 标志位是否已被更改
     */
    @ApiModelProperty(hidden = true)
    private Integer checkOverTimeFlag;
    
    /**
     * 在职状态，1在职，2离职
     */
    @ApiModelProperty(value="在职状态")
    private Integer jobStatus;
    
    @ApiModelProperty(value="修改时间")
    private Date lastTime;
}