package com.sefonsoft.oa.domain.workorder;

import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 派工单销售售前匹配表(WorkOrderMatchConfigDTO)实体类
 *
 * @author Aron
 * @since 2019-12-09 15:21:59
 */
@ApiModel("派工单销售售前匹配表实体类")
@Accessors(chain = true)
@Data
public class WorkOrderMatchConfigDTO {

    /**
     * 售前部门部门列表
     */
    @ApiModelProperty("售前部门部门列表")
    private SysDepartment preDepartment;

    /**
     * 对应销售部门列表
     */
    @ApiModelProperty("对应销售部门列表")
    private List<SysDepartment> saleDepartmentList;

}



