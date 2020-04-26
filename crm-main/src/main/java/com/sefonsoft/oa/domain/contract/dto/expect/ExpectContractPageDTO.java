package com.sefonsoft.oa.domain.contract.dto.expect;

import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName: ExpectContractPageDTO
 * @author: Peng YiWen
 * @date: 2020/4/21  17:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("预估合同列表页面数据（统计数据和列表数据）")
public class ExpectContractPageDTO implements Serializable {

    private static final long serialVersionUID = -9094833638304539047L;

    @ApiModelProperty("合同预测列表")
    private PageResponse pageResponse;

    @ApiModelProperty("合同预测列表页统计图封装数据")
    private ExpectContractChartDTO expectContractChartDTO;

}