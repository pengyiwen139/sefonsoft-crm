package com.sefonsoft.oa.entity.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (CustomerInfo)实体类
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@ApiModel(value = "企业性质")
@Data
public class EnterpriseType implements Serializable {

    private static final long serialVersionUID = 156089027666833349L;

    /**
    主键
    */
    @ApiModelProperty("主键")
    private Integer enterId;

    /**
    1国有 2合作 3合资 4独资 5集体 6私营 7个体 8报关 9其它
    */
    @ApiModelProperty("1国有 2合作 3合资 4独资 5集体 6私营 7个体 8报关 9其它")
    private String enterName;

}