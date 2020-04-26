package com.sefonsoft.oa.entity.contract;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("合同预测产品金额关联表(ExpectContractProductRef)实体类")
public class ExpectContractProductRef implements Serializable {

    private static final long serialVersionUID = -59259821188601837L;

    @ApiModelProperty(value = "主键")
    private Long id;
    
    @ApiModelProperty(value = "产品编号")
    private Integer productId;
    
    @ApiModelProperty(value = "产品名称")
    private String productName;
    
    @ApiModelProperty(value = "数量")
    private Integer saleCount;
    
    @ApiModelProperty(value = "单价金额，单位万元")
    private Double amount;
    
    @ApiModelProperty(value = "项目编号")
    private Long expectContractId;
    
    @ApiModelProperty(value = "操作员")
    private String operator;
    
    @ApiModelProperty(value = "更新时间")
    private Date lastTime;
    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    


}