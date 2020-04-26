package com.sefonsoft.oa.domain.contract.dto.expect;

import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

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
@ApiModel("合同预测列表实体类")
public class ExpectContractQueryDTO implements Serializable {

    private static final long serialVersionUID = 4292684876703180061L;

    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @ApiModelProperty("合同预测产品金额实体列表")
    private List<ExpectContractProductRef> contractProductRefList;

    @ApiModelProperty(value = "立项项目id", example = "SFWY-202001-0055")
    private String projectId;

    @ApiModelProperty(value = "立项项目名称", example = "滕21aa59e5a99ae6eb278b")
    private String projectName;

    @ApiModelProperty(value = "销售负责人id", example = "SF3585")
    private String employeeId;

    @ApiModelProperty(value = "销售负责人名称", example = "黎79")
    private String employeeName;

    @ApiModelProperty(value = "部门id", example = "BM0020")
    private String deptId;

    @ApiModelProperty(value = "部门名称", example = "交通事业部")
    private String deptName;

    @ApiModelProperty(value = "签订甲方id，即客户编号", example = "KH202001090005")
    private String signCustomerId;

    @ApiModelProperty(value = "签订甲方名称，即客户名称", example = "山东c6bb42656a平台")
    private String signCustomerName;

    @ApiModelProperty(value = "行业，可选项参考project_info的industry字段，下拉选项", example = "公安")
    private String industry;

    @ApiModelProperty(value = "合同性质类型，1直签，2渠道", example = "1")
    private Integer contractNatureType;

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

    @ApiModelProperty(value = "合同类型：必填，下拉选择框，包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同", example = "1")
    private Integer expectContractType;

    @ApiModelProperty(value = "成单概率 1-100", example = "100")
    private Integer estimateSuccess;

    @ApiModelProperty(value = "是否重大项目：1是，2不是", example = "1")
    private Integer hasImportant;

    @ApiModelProperty(value = "所需公司支持")
    private String otherSupport;

    @ApiModelProperty(value = "操作人id", hidden = true)
    private String operator;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date lastTime;

}