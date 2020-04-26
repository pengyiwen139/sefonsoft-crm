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
@ApiModel("新增编辑页面所需数据（负责人为当前用户的立项通过项目list）")
public class ExpectContractNeedProjectDTO implements Serializable {

    private static final long serialVersionUID = -595751521706431368L;

    @ApiModelProperty(value = "立项项目id", example = "SFWY-202001-0055")
    private String projectId;

    @ApiModelProperty(value = "立项项目名称", example = "滕21aa59e5a99ae6eb278b")
    private String projectName;

    @ApiModelProperty(value = "签订甲方id，即客户编号", example = "KH202001090005")
    private String signCustomerId;

    @ApiModelProperty(value = "签订甲方名称，即客户名称", example = "山东c6bb42656a平台")
    private String signCustomerName;

    @ApiModelProperty(value = "行业，可选项参考project_info的industry字段，下拉选项", example = "公安")
    private String industry;

    @ApiModelProperty(value = "销售负责人所在省区", example = "四川省/成都市/天府新区")
    private String employeeLocation;

    @ApiModelProperty(value = "用户所在地", example = "四川省/成都市/天府新区")
    private String location;

    @ApiModelProperty(value = "最终客户编号，只可随着立项项目的改变而改变", example = "KH202001090005")
    private String finalCustomerId;

    @ApiModelProperty(value = "最终客户名称，只可随着立项项目的改变而改变", example = "山东c6bb42656a平台")
    private String finalCustomerName;

    @ApiModelProperty(value = "预估签约时间，只要展示年月", example = "2019-12")
    private String estimateTime;

    @ApiModelProperty(value = "预估签约金额", example = "520.20")
    private Double estimateMoney;

    @ApiModelProperty(value = "成单概率 1-100", example = "100")
    private Integer estimateSuccess;

    @ApiModelProperty(value = "是否重大项目：1是，2不是", example = "1")
    private Integer hasImportant;

}