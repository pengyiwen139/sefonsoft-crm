package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/12/3 15:12
 * @description：销售项目阶段
 * @modified By：
 */
@ApiModel("销售项目阶段实体类")
@Data
public class SalesProjectStageDTO {

    @ApiModelProperty("编号")
    private Integer spstageId;

    @ApiModelProperty("销售项目阶段名称")
    private String spstageName;

}