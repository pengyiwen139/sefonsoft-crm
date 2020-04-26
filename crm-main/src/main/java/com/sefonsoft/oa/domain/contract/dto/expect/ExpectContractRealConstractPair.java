package com.sefonsoft.oa.domain.contract.dto.expect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: ExpectContractRealConstractPair
 * @author: Peng YiWen
 * @date: 2020/4/21  17:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("每月的预估合同金额和实际合同金额的键值对")
public class ExpectContractRealConstractPair implements Serializable {

    private static final long serialVersionUID = -4740883701253476798L;

    @ApiModelProperty("每月的预估合同金额")
    private BigDecimal expectContractAmount;

    @ApiModelProperty("每月的实际合同金额")
    private BigDecimal realContractAmount;

    @ApiModelProperty("当前年月")
    private String currentMonthTime;

}