package com.sefonsoft.oa.domain.contract.dto.expect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.List;

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
@ApiModel("新增编辑页面所需数据（负责人为当前用户的立项通过项目list和当前用户的客户list）")
public class ExpectContractNeedParamDTO implements Serializable {

    private static final long serialVersionUID = 8248516427737111799L;

    @ApiModelProperty(value = "当前用户的客户list")
    private List<ExpectContractNeedProjectDTO> projectList;

    @ApiModelProperty(value = "负责人为当前用户的立项通过项目list")
    private List<ExpectContractNeedCustomerDTO> customerList;

}