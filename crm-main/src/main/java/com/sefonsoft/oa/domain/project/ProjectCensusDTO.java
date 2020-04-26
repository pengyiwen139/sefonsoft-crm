package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/13 14:39
 * @description：包含项目阶段金额，阶段个数，阶段id，阶段名称
 * @modified By：
 */
@Accessors(chain = true)
@Data
@ApiModel(value = "包含项目阶段金额，阶段个数，阶段id，阶段名称")
@AllArgsConstructor
public class ProjectCensusDTO {

    @ApiModelProperty(hidden = true)
    private Integer stageId;

    @ApiModelProperty("阶段名称")
    private String stageName;

    @ApiModelProperty("包含项目阶段金额")
    private BigDecimal projectAmountSum;

    @ApiModelProperty("阶段个数")
    private Integer projectCount;

}
