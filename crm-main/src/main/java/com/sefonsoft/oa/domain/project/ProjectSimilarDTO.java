package com.sefonsoft.oa.domain.project;

import com.sefonsoft.oa.entity.product.ProductInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/12/23 15:21
 * @description：存储项目名称，最终客户，产品相似的项目
 */
@Data
@Accessors(chain = true)
public class ProjectSimilarDTO {

    @ApiModelProperty("相似的项目编号")
    private String projectId;

    @ApiModelProperty("相似的项目名称")
    private String projectName;

    @ApiModelProperty("相似的最终客户编号")
    private String customerId;

    @ApiModelProperty("相似的最终客户名称")
    private String customerName;

    @ApiModelProperty("相同的产品")
    private List<ProductInfo> productInfoList;

}