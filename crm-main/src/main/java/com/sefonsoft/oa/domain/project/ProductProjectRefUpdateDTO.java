package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (ProductProjectRefInsertDTO)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 17:25:53
 */
@ApiModel("修改项目所用的产品信息")
@Accessors(chain = true)
@Data
public class ProductProjectRefUpdateDTO implements Serializable {

    /**
    主键 
    */
    @ApiModelProperty(hidden = true)
    private Long id;
        
    /**
    产品编号,索引，关联product_info表product_id
    */
    @NotNull(message = "产品编号" + NO_PARAM)
    @ApiModelProperty("产品编号,索引，关联product_info表product_id")
    private Integer productId;
        
    /**
    销售数量 
    */
    @Max(value = 9999, message = "销售数量最大不能超过9999")
    @NotNull(message = "销售数量" + NO_PARAM)
    @ApiModelProperty("销售数量")
    private Integer saleCount;
        
    /**
    金额 
    */
    @Max(value = 999999, message = "金额最大不能超过999999万元")
    @NotNull(message = "金额" + NO_PARAM)
    @ApiModelProperty("金额")
    private BigDecimal amount;
        
    /**
    项目编号 
    */
    @NotBlank(message = "项目编号" + NO_PARAM)
    @Size(min = 1, max = 16, message = "项目编号"+MAX_PARAM+"，最大为16位")
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