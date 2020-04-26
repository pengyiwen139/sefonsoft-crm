package com.sefonsoft.oa.domain.workorder.deptconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: WorkorderDeptOperateParams
 * @author: Peng YiWen
 * @date: 2020/4/2  14:45
 */
@ApiModel("添加和编辑界面需要的部门参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkorderDeptOperateParams implements Serializable {

    private static final long serialVersionUID = 1036921583544546389L;

    /**
     * 源部门列表（所有销售部门）
     */
    @ApiModelProperty(value="源部门列表（所有销售部门）")
    private List<DeptNamePair> deptSourceList;

    /**
     * 关联部门列表（售前）
     */
    @ApiModelProperty(value="关联部门（售前）")
    private List<DeptNamePair> deptDesList;

}

