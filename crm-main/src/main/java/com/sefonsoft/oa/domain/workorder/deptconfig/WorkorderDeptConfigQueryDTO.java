package com.sefonsoft.oa.domain.workorder.deptconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.Date;
import java.io.Serializable;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (WorkorderDeptConfigQueryDTO)实体类
 *
 * @author makejava
 * @since 2020-04-02 09:28:02
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("派工单部门关联配置query实体类")
public class WorkorderDeptConfigQueryDTO implements Serializable {

    private static final long serialVersionUID = 3970950191340739799L;

    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
    * 关联类型，1：售前派工单;2：交付派工单(预留)
    */
    @ApiModelProperty(value="关联类型，1：售前派工单;2：交付派工单(预留)")
    private Integer deptType;

    /**
    * 源部门编号（销售）
    */
    @ApiModelProperty(value="源部门编号（销售）")
    private String deptSource;

    /**
    * 源部门名称（销售）
    */
    @ApiModelProperty(value="源部门名称（销售）")
    private String deptSourceName;

    /**
    * 关联部门编号（售前）
    */
    @ApiModelProperty(value="关联部门编号（售前）")
    private String deptDes;

    /**
    * 关联部门名称（售前）
    */
    @ApiModelProperty(value="关联部门名称（售前）")
    private String deptDesName;

    /**
    * 操作人id
    */
    @ApiModelProperty(value="操作人id")
    private String operatorEmployeeId;

    /**
    * 修改时间
    */
    @ApiModelProperty(value="修改时间")
    private Date modifiedTime;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
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