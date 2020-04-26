package com.sefonsoft.oa.domain.contract.dto.expect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * (ExpectContractInfo)实体类
 *
 * @author makejava
 * @since 2020-04-17 16:34:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("新增编辑页面所需数据（当前用户的客户list）")
public class ExpectContractNeedCustomerDTO implements Serializable {

    private static final long serialVersionUID = -7601191287555174305L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "客户编号")
    private String customerId;

    @ApiModelProperty(value = "立项项目名称")
    private String customerName;

}