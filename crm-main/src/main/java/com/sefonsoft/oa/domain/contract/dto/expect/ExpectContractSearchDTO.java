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
@ApiModel("合同预测列表查询请求封装类")
public class ExpectContractSearchDTO implements Serializable {

    private static final long serialVersionUID = -5685896487856355006L;

//    @Min(value = 0, message = "page" + MIN_PARAM + 0)
//    @NotNull(message = "page" + NO_PARAM)
    private Integer page;

//    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
//    @NotNull(message = "rows" + NO_PARAM)
    private Integer rows;

    public Integer getPage() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;

    /**
     * 上下半年
     */
    @ApiModelProperty("上下半年，上半年1，下半年2")
    private String halfYear;

    /**
     * 季度
     */
    @ApiModelProperty("季度，1一季度，2二季度，3三季度，4四季度")
    private String quarter;

    /**
     * 月份
     */
    @ApiModelProperty("月份，1一月，2二月......")
    private String month;

    @ApiModelProperty(value = "数据权限部门数组", example = "BM0020")
    private List<String> dataDeptIdList;

    @ApiModelProperty(value = "项目名称、销售姓名、签订甲方、最终用户名称模糊匹配查询传入此参数中", example = "模糊查询条件")
    private String uniQueryName;

    @ApiModelProperty(value = "销售负责人id", example = "SF3585")
    private String employeeId;

    @ApiModelProperty(value = "是否重大项目：1是，2不是", example = "1")
    private Integer hasImportant;

    @ApiModelProperty(value = "合同类型包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同", example = "1")
    private Integer expectContractType;

    @ApiModelProperty(value = "合同性质类型，1直签，2渠道", example = "1")
    private Integer contractNatureType;

}