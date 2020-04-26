package com.sefonsoft.oa.entity.workorder;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel("派工单部门关联实体类")
public class WorkorderDeptConfig implements Serializable {

    private static final long serialVersionUID = 3970950191340739799L;

    /**
    * 主键
    */
    private Long id;
    /**
    * 关联类型，1：售前派工单;2：交付派工单(预留)
    */
    private Integer deptType;
    /**
    * 源部门编号（销售）
    */
    private String deptSource;
    /**
    * 关联部门编号（售前）
    */
    private String deptDes;
    /**
    * 操作人id
    */
    private String operatorEmployeeId;
    /**
    * 修改时间
    */
    private Date modifiedTime;
    /**
    * 创建时间
    */
    private Date createTime;

}