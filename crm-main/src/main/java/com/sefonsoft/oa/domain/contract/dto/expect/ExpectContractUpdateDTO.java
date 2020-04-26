package com.sefonsoft.oa.domain.contract.dto.expect;

import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
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
@ApiModel("合同预测修改实体类")
public class ExpectContractUpdateDTO implements Serializable {

    private static final long serialVersionUID = 2713953367188653658L;

    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty(value = "主键")
    private Long id;

    @NotNull(message = "产品信息" + NO_PARAM)
    @ApiModelProperty("合同预测产品金额实体列表")
    private List<ExpectContractProductRef> contractProductRefList;

    @Size(min = 1, max = 16, message = "项目编号"+MAX_PARAM+",最大长度为16")
    @NotBlank(message = "立项项目id" + NO_PARAM)
    @ApiModelProperty(value = "立项项目id", example = "SFWY-202001-0055")
    private String projectId;

    @Size(min = 1, max = 64, message = "项目名称"+MAX_PARAM+",最大长度为64")
    @NotBlank(message = "项目名称" + NO_PARAM)
    @ApiModelProperty(value = "立项项目名称", example = "1滕21aa59e5a99ae6eb278b")
    private String projectName;

    @NotBlank(message = "负责人工号" + NO_PARAM)
    @Size(min = 1, max = 16, message = "负责人工号"+MAX_PARAM+",最大长为16字")
    @ApiModelProperty(value = "销售负责人id", example = "SF3585")
    private String employeeId;

    @NotBlank(message = "销售负责人名称" + NO_PARAM)
    @Size(min = 1, max = 32, message = "负责人工号"+MAX_PARAM+",最大长为32字")
    @ApiModelProperty(value = "销售负责人名称", example = "黎79")
    private String employeeName;

    @NotBlank(message = "部门id" + NO_PARAM)
    @Size(min = 1, max = 32, message = "部门id"+MAX_PARAM+",最大长为32")
    @ApiModelProperty(value = "部门id", example = "BM0020")
    private String deptId;

    @NotBlank(message = "部门名称" + NO_PARAM)
    @Size(min = 1, max = 32, message = "部门名称"+MAX_PARAM+",最大长为32")
    @ApiModelProperty(value = "部门名称", example = "交通事业部")
    private String deptName;

    @NotBlank(message = "签订甲方id" + NO_PARAM)
    @Size(min = 1, max = 16, message = "签订甲方id"+MAX_PARAM+",最大长为16")
    @ApiModelProperty(value = "签订甲方id，即客户编号", example = "KH202001090005")
    private String signCustomerId;

    @NotBlank(message = "签订甲方名称" + NO_PARAM)
    @Size(min = 1, max = 64, message = "签订甲方id"+MAX_PARAM+",最大长为64")
    @ApiModelProperty(value = "签订甲方名称，即客户名称", example = "山东c6bb42656a平台")
    private String signCustomerName;

    @NotBlank(message = "行业" + NO_PARAM)
    @Size(min = 1, max = 32, message = "签订甲方id"+MAX_PARAM+",最大长为32")
    @ApiModelProperty(value = "行业，可选项参考project_info的industry字段，下拉选项", example = "公安")
    private String industry;

    @NotNull(message = "合同性质类型"+NO_PARAM)
    @ApiModelProperty(value = "合同性质类型，1直签，2渠道", example = "1")
    private Integer contractNatureType;

    @NotBlank(message = "销售负责人所在省区" + NO_PARAM)
    @Size(min = 1, max = 32, message = "销售负责人所在省区"+MAX_PARAM+",最大长为32")
    @ApiModelProperty(value = "销售负责人所在省区", example = "四川省/成都市/天府新区")
    private String employeeLocation;

    @NotBlank(message = "用户所在地" + NO_PARAM)
    @Size(min = 1, max = 32, message = "用户所在地"+MAX_PARAM+",最大长为32")
    @ApiModelProperty(value = "用户所在地", example = "四川省/成都市/天府新区")
    private String location;

    @NotBlank(message = "最终客户编号" + NO_PARAM)
    @Size(min = 1, max = 16, message = "最终客户编号"+MAX_PARAM+",最大长为16")
    @ApiModelProperty(value = "最终客户编号，只可随着立项项目的改变而改变", example = "KH202001090005")
    private String finalCustomerId;

    @NotBlank(message = "最终客户名称" + NO_PARAM)
    @Size(min = 1, max = 64, message = "最终客户名称"+MAX_PARAM+",最大长为64")
    @ApiModelProperty(value = "最终客户名称，只可随着立项项目的改变而改变", example = "山东c6bb42656a平台")
    private String finalCustomerName;

    @NotBlank(message = "预估签约时间" + NO_PARAM)
    @Size(min = 1, max = 64, message = "预估签约时间"+MAX_PARAM+",最大长为64")
    @ApiModelProperty(value = "预估签约时间，只要展示年月", example = "2019-12")
    private String estimateTime;

    @NotNull(message = "预估签约金额"+NO_PARAM)
    @ApiModelProperty(value = "预估签约金额", example = "520.20")
    private Double estimateMoney;

    @NotNull(message = "合同类型"+NO_PARAM)
    @Max(value = 10, message = "合同类型"+MAX_PARAM+",最大长为10")
    @ApiModelProperty(value = "合同类型：必填，下拉选择框，包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同", example = "1")
    private Integer expectContractType;

    @NotNull(message = "成单概率"+NO_PARAM)
    @Max(value = 100, message = "成单概率"+MAX_PARAM+",最大长为100")
    @ApiModelProperty(value = "成单概率 1-100", example = "100")
    private Integer estimateSuccess;

    @NotNull(message = "是否重大项目"+NO_PARAM)
    @Max(value = 2, message = "是否重大项目"+MAX_PARAM+",最大长为2")
    @ApiModelProperty(value = "是否重大项目：1是，2不是", example = "1")
    private Integer hasImportant;

    @Size(max = 512, message = "所需公司支持"+MAX_PARAM+",最大长为512")
    @ApiModelProperty(value = "所需公司支持")
    private String otherSupport;

    @ApiModelProperty(value = "操作人id" ,hidden = true)
    private String operator;
    
    @ApiModelProperty(value = "创建时间" ,hidden = true)
    private Date createTime;
    
    @ApiModelProperty(value = "更新时间" ,hidden = true)
    private Date lastTime;
    
}