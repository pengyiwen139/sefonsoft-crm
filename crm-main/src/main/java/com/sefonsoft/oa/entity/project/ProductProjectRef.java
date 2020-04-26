package com.sefonsoft.oa.entity.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (ProductProjectRefInsertDTO)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 17:25:53
 */
@ApiModel("添加项目所用的产品信息")
@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class ProductProjectRef implements Serializable {
    private static final long serialVersionUID = -25698746483899672L;
        
    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     产品编号,索引，关联product_info表product_id
     */
    @ApiModelProperty("产品编号,索引，关联product_info表product_id")
    private Integer productId;

    /**
     产品名称
     */
    @ApiModelProperty("产品名称")
    private String productName;
        
    /**
    销售数量 
    */
    @ApiModelProperty("销售数量")
    private Integer saleCount;
        
    /**
    金额 
    */
    @ApiModelProperty("金额")
    private BigDecimal amount;
        
    /**
    项目编号 
    */
    @ApiModelProperty("项目编号")
    private String projectId;
        
    /**
    关联sys_user表user_id 
    */
    @ApiModelProperty(hidden = true)
    private String operator;

    @ApiModelProperty(hidden = true)
    private Date lastTime;

    @ApiModelProperty(hidden = true)
    private Date createTime;

}