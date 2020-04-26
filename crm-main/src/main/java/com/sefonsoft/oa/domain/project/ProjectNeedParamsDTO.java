package com.sefonsoft.oa.domain.project;

import com.sefonsoft.oa.entity.product.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/16 19:49
 * @description：添加项目需要的下拉选项
 * @modified By：
 */
@AllArgsConstructor
@ApiModel("添加项目需要的下拉选项")
@Accessors(chain = true)
@Data
public class ProjectNeedParamsDTO {

    /**
     * 项目销售阶段,关承sales_project_stage表spstage_id
     */
    @ApiModelProperty("项目销售阶段")
    private List<SalesProjectStageDTO> salesProjectStageList;

    /**
     客户项目阶段,关联custome_stage表stage_id
     */
    @ApiModelProperty("选择产品需要的产品列表")
    private List<ProductInfo> productInfoList;

}