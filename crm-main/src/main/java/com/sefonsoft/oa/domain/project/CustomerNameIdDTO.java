package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (CustomerInfo)客户的名称和编号
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@Data
@ApiModel(value ="客户实体类")
public class CustomerNameIdDTO implements Serializable {
    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号,不能为空")
    private String customerId;

    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称,不能为空")
    private String customerName;
}