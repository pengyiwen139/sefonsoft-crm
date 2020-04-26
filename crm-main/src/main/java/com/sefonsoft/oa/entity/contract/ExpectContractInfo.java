package com.sefonsoft.oa.entity.contract;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


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
@ApiModel("合同预测实体类")
public class ExpectContractInfo implements Serializable {
    private static final long serialVersionUID = -72083011222692731L;
    @ApiModelProperty(value = "${column.comment}")
    private Long id;
    
    @ApiModelProperty(value = "立项项目id")
    private String projectId;
    
    @ApiModelProperty(value = "立项项目名称")
    private String projectName;
    
    @ApiModelProperty(value = "销售负责人id")
    private String employeeId;
    
    @ApiModelProperty(value = "销售负责人名称")
    private String employeeName;
    
    @ApiModelProperty(value = "部门id")
    private String deptId;
    
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    
    @ApiModelProperty(value = "签订甲方id，即客户编号")
    private String signCustomerId;
    
    @ApiModelProperty(value = "签订甲方名称，即客户名称")
    private String signCustomerName;
    
    @ApiModelProperty(value = "行业，可选项参考project_info的industry字段，下拉选项")
    private String industry;
    
    @ApiModelProperty(value = "合同性质类型，1直签，2渠道")
    private Integer contractNatureType;
    
    @ApiModelProperty(value = "销售负责人所在省区")
    private String employeeLocation;

    @ApiModelProperty(value = "用户所在地")
    private String location;

    @ApiModelProperty(value = "最终客户编号，只可随着立项项目的改变而改变")
    private String finalCustomerId;
    
    @ApiModelProperty(value = "最终客户名称，只可随着立项项目的改变而改变")
    private String finalCustomerName;
    
    @ApiModelProperty(value = "预估签约时间，只要展示年月")
    private String estimateTime;
    
    @ApiModelProperty(value = "预估签约金额")
    private Double estimateMoney;
    
    @ApiModelProperty(value = "合同类型：必填，下拉选择框，包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同")
    private Integer expectContractType;
    
    @ApiModelProperty(value = "成单概率 1-100")
    private Integer estimateSuccess;
    
    @ApiModelProperty(value = "是否重大项目：1是，2不是")
    private Integer hasImportant;
    
    @ApiModelProperty(value = "所需公司支持")
    private String otherSupport;
    
    @ApiModelProperty(value = "操作人id")
    private String operator;
    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(value = "更新时间")
    private Date lastTime;
    


}