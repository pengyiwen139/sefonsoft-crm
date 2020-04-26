package com.sefonsoft.oa.domain.workorder.deptconfig;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: DeptNamePair
 * @author: Peng YiWen
 * @date: 2020/4/2  15:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("部门编号名称pair")
public class DeptNamePair implements Serializable {
    private static final long serialVersionUID = -5716009591258235104L;
    String deptId;
    String deptName;
}