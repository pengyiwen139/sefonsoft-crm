package com.sefonsoft.oa.domain.contract.dto.expect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;


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
@ApiModel("合同预测列表页统计图封装数据")
public class ExpectContractChartDTO implements Serializable {

    private static final long serialVersionUID = -806045317499137866L;

    @ApiModelProperty("预估业绩金额总数")
    private BigDecimal expectSumAmount;

    @ApiModelProperty("每月的预估合同金额和实际合同金额，此处会按照月份顺序排列")
    private List<ExpectContractRealConstractPair> expectContractRealConstractPairs;

}