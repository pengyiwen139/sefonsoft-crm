package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.*;

/**
 * 机会项目新增实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 15:32:44
 */
@ApiModel("机会项目新增实体类")
@Accessors(chain = true)
@Data
public class ProjectInfoUpdateDTO implements Serializable {

    private static final long serialVersionUID = 3312249498957307637L;
    /**
     主键
     */
    @NotNull(message = "主键" + NO_PARAM)
    @ApiModelProperty("主键"+NO_PARAM)
    private Long id;

    /**
     项目编号,索引+主键，编号规则：前缀+日期+流水（4位）
     */
    @Size(min = 1, max = 16, message = "项目编号必须小于16位")
    @NotBlank(message = "项目编号" + NO_PARAM)
    @ApiModelProperty(value = "项目编号,索引+主键，编号规则：前缀+日期+流水（4位）")
    private String projectId;

    /**
     项目名称
     */
    @Size(min = 1, max = 64, message = "项目名称"+MAX_PARAM+",最大长度为64")
    @NotBlank(message = "项目名称" + NO_PARAM)
    @ApiModelProperty(value = "项目名称", example = "测试项目名称1")
    private String projectName;

    /**
     是否重大项目,1是，2不是
     */
    @Max(value = 2, message = "是否为重大项目"+MAX_PARAM+",最大值为2")
    @Min(value = 1, message = "是否为重大项目"+MIN_PARAM+",最小值为1")
    @NotNull(message = "是否为重大项目" + NO_PARAM)
    @ApiModelProperty(value = "是否重大项目,1是，2不是", example = "2")
    private Integer hasImportant;
    /**
     销售阶段,关联sales_project_stage表spstage_id
     */
    @NotNull(message = "销售阶段" + NO_PARAM)
    @ApiModelProperty(value = "销售阶段,关联sales_project_stage表spstage_id",example = "1")
    private Integer spstageId;
//    /**
//     项目状态,关联project_status_info表pros_id,后台设置默认为意向
//     */
//    @NotNull(message = "项目状态编号" + NO_PARAM)
//    @ApiModelProperty(value = "项目状态编号,关联project_status_info表pros_id")
//    private Integer prosId;

    /**
     项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他
     */
    @Size(min = 1, max = 32, message = "项目所属行业"+MAX_PARAM+",最大长度为32")
    @NotBlank(message = "项目所属行业" + NO_PARAM)
    @ApiModelProperty(value = "项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他",example = "军工")
    private String industry;
    /**
     客户项目阶段，填字符串，已申请立项，资金到位，等
     */
    @Size(min = 1, max = 32, message = "客户项目阶段"+MAX_PARAM+",最大长度为32")
    @NotBlank(message = "客户项目阶段" + NO_PARAM)
    @ApiModelProperty(value = "客户项目阶段，填字符串，已申请立项，资金到位，等",example = "已申请立项")
    private String customerProjectPhase;



    /**
     客户编号,索引，关联customer_info表customer_id
     */
    @Size(max = 16, message = "客户编号"+MAX_PARAM+",最大长度为16")
    @ApiModelProperty(value = "客户编号,关联customer_info表customer_id",example = "KH201911290001")
    private String customerId;
    /**
     指客户方的项目联系人编号
     */
    @ApiModelProperty(value = "指客户方的项目联系人编号",example = "413")
    private Long contactId;
    /**
     合作伙伴编号，索引，关联customer_info表customer_id
     */
    @Size(max = 16, message = "合作伙伴编号"+MAX_PARAM+",最大长度为16")
    @ApiModelProperty(value = "合作伙伴编号，索引，关联customer_info表customer_id",example = "KH201911290013")
    private String partnerId;
    /**
     合作伙伴的联系人编号
     */
    @ApiModelProperty(value = "合作伙伴的联系人编号",example = "426")
    private Long partnerContactId;



    /**
     项目总投资金额
     */
    @Max(value = 999999, message = "项目总投资金额"+MAX_PARAM+",最大值为999999万元")
    @NotNull(message = "项目总投资金额" + NO_PARAM)
    @ApiModelProperty(value = "项目总投资金额",example = "900")
    private BigDecimal totalInvestment;

    /**
     项目所在城市，根据客户的省市存省市的字符串
     */
    @ApiModelProperty(value = "项目所在城市,不需修改，传什么到前台，就传什么到后台，根据客户的省市存省市的字符串")
    private String location;


    /**
     预估项目金额，单位：万元
     */
    @Max(value = 999999, message = "预估项目金额"+MAX_PARAM+",最大值为999999万元")
    @NotNull(message = "预估项目金额" + NO_PARAM)
    @ApiModelProperty(value = "预估项目金额，单位：万元",example = "800")
    private BigDecimal estimateMoney;

    /**
     预估签约时间
     */
    @NotNull(message = "预估签约时间" + NO_PARAM)
    @ApiModelProperty("预估签约时间")
    private Date estimateTime;

    /**
     预估成功率，百分比数字
     */
    @Max(value = 100, message = "预估成功率不能超过100%")
    @NotNull(message = "预估成功率" + NO_PARAM)
    @ApiModelProperty(value = "预估成功率，百分比数字，最大100",example = "90")
    private Integer estimateSuccess;

    /**
     项目总情况
     */
    @Size(min = 1, max = 512, message = "项目总情况"+MAX_PARAM+",最大长为512字")
    @NotBlank(message = "项目总情况" + NO_PARAM)
    @ApiModelProperty(value = "项目总情况",example = "项目总情况测试内容")
    private String projectSituation;

    /**
     最终用户关系分析
     */
    @Size(min = 1, max = 512, message = "最终用户关系分析"+MAX_PARAM+",最大长为512字")
    @NotBlank(message = "最终用户关系分析" + NO_PARAM)
    @ApiModelProperty(value = "最终用户关系分析",example = "最终用户关系分析测试内容")
    private String userRelationAnalysis;

    /**
     参与集成商情况分析
     */
    @Size(min = 1, max = 512, message = "参与集成商情况分析"+MAX_PARAM+",最大长为512字")
    @NotBlank(message = "参与集成商情况分析" + NO_PARAM)
    @ApiModelProperty(value = "参与集成商情况分析",example = "参与集成商情况分析测试内容")
    private String integratorSituation;

    /**
     竞争对手情况分析
     */
    @Size(min = 1, max = 512, message = "竞争对手情况分析"+MAX_PARAM+",最大长为512字")
    @NotBlank(message = "竞争对手情况分析" + NO_PARAM)
    @ApiModelProperty(value = "竞争对手情况分析",example = "竞争对手情况分析测试内容")
    private String competeOpponentAnalysis;

    /**
     项目运作策略
     */
    @Size(min = 1, max = 512, message = "项目运作策略"+MAX_PARAM+",最大长为512字")
    @NotBlank(message = "项目运作策略" + NO_PARAM)
    @ApiModelProperty(value = "项目运作策略",example = "项目运作策略测试内容")
    private String projectRunStrategy;

    @NotNull(message = "产品信息" + NO_PARAM)
    @ApiModelProperty("添加项目所用的产品信息")
    private List<ProductProjectRefUpdateDTO> productRefInsertDTOList;

    @NotNull(message = "业绩分隔" + NO_PARAM)
    @ApiModelProperty("添加项目业绩分隔所需信息")
    private List<ProjectAmountDevideRefUpdateDTO> projectAmountDevideRefInsertDTOList;


    @NotBlank(message = "负责人工号" + NO_PARAM)
    @ApiModelProperty(value = "负责人工号", example = "SF1579")
    private String employeeId;

//    @ApiModelProperty(value = "合作伙伴名称")
//    private String partnerName;
//
//    @ApiModelProperty(value = "最终客户名称")
//    private String customerName;
//
//    @ApiModelProperty(value = "客户联系人名称")
//    private String contactName;
//
//    @ApiModelProperty(value = "客户联系人电话")
//    private String contactJob;
//
//    @ApiModelProperty(value = "客户联系人部门名称")
//    private String contactDeptName;
//
//    @ApiModelProperty(value = "客户联系人电话")
//    private String contactTel;
//
//    @ApiModelProperty(value = "合作伙伴联系人名称")
//    private String partnerContactName;
//
//    @ApiModelProperty(value = "合作伙伴联系人部门名称")
//    private String partnerContactDeptName;
//
//    @ApiModelProperty(value = "合作伙伴联系人电话")
//    private String partnerContactTel;

    @ApiModelProperty(value = "过期项目标示,0未过期，1过期")
    private Integer overTimeFlag;

    /**
     操作员,关联sys_user表user_id
     */
    @ApiModelProperty(hidden = true)
    private String operator;

    /**
     最后操作时间
     */
    @ApiModelProperty(hidden = true)
    private Date lastTime;

    /**
     创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "是否校验相似相似,0否，1是")
    private Integer checkSimilarFlag;

    @ApiModelProperty(value = "0无相似的项目，1有相似的项目", hidden = true)
    private Integer hasSimilarType;

    @ApiModelProperty(value = "商机ID，只有在大区总驳回时可以修改")
    private Long bizOpportId;
}