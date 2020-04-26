package com.sefonsoft.oa.entity.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (ProductInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 19:31:28
 */
@ApiModel("产品实体类")
@Accessors(chain = true)
@Data
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -45872807220827695L;
        
    /**
    产品编号，主键 
    */
    @ApiModelProperty("产品编号，主键")
    private Integer productId;
        
    /**
    产品名称 
    */
    @ApiModelProperty("产品名称")
    private String productName;
        
    /**
    产品简称 
    */
    @ApiModelProperty("产品简称")
    private String productAbbreviation;
        
    /**
    产品版本号 
    */
    @ApiModelProperty("产品版本号")
    private String productVersion;

}