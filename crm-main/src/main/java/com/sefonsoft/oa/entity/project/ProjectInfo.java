package com.sefonsoft.oa.entity.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (ProjectInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 15:32:44
 */
@ApiModel("机会项目表实体类")
@Accessors(chain = true)
@Data
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = 967132421858988808L;
        
    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    项目编号,索引+主键，编号规则：前缀+日期+流水（4位） 
    */
    @ApiModelProperty("项目编号,索引+主键，编号规则：前缀+日期+流水（4位）")
    private String projectId;
        
    /**
    项目名称 
    */
    @ApiModelProperty("项目名称")
    private String projectName;
        
    /**
    是否重大项目,1是，2不是 
    */
    @ApiModelProperty("是否重大项目,1是，2不是")
    private Integer hasImportant;
    /**
     销售阶段,关联sales_project_stage表spstage_id
     */
    @ApiModelProperty("销售阶段,关联sales_project_stage表spstage_id")
    private Integer spstageId;
    /**
     项目状态,关联project_status_info表pros_id
     */
    @ApiModelProperty("项目状态,关联project_status_info表pros_id")
    private Integer prosId;

    /**
     项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他
     */
    @ApiModelProperty("项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他")
    private String industry;
    /**
     客户项目阶段，填字符串，已申请立项，资金到位，等
     */
    @ApiModelProperty("客户项目阶段，填字符串，已申请立项，资金到位，等")
    private String customerProjectPhase;



    /**
    客户编号,索引，关联customer_info表customer_id 
    */
    @ApiModelProperty("客户编号,索引，关联customer_info表customer_id")
    private String customerId;
    /**
     指客户方的项目联系人编号
     */
    @ApiModelProperty("指客户方的项目联系人编号")
    private Long contactId;
    /**
     合作伙伴编号，索引，关联customer_info表customer_id
     */
    @ApiModelProperty("合作伙伴编号，索引，关联customer_info表customer_id")
    private String partnerId;
    /**
     合作伙伴的联系人编号
     */
    @ApiModelProperty("合作伙伴的联系人编号")
    private Long partnerContactId;


    /**
    项目总投资金额 
    */
    @ApiModelProperty("项目总投资金额")
    private String totalInvestment;

    /**
    项目所在城市，根据客户的省市存省市的字符串 
    */
    @ApiModelProperty("项目所在城市，根据客户的省市存省市的字符串")
    private String location;

        
    /**
    预估项目金额，单位：万元 
    */
    @ApiModelProperty("预估项目金额，单位：万元")
    private BigDecimal estimateMoney;
        
    /**
    预估签约时间 
    */
    @ApiModelProperty("预估签约时间")
    private Object estimateTime;
        
    /**
    百分比数字 
    */
    @ApiModelProperty("百分比数字")
    private Integer estimateSuccess;
        
    /**
    项目总情况 
    */
    @ApiModelProperty("项目总情况")
    private String projectSituation;
        
    /**
    最终用户关系分析 
    */
    @ApiModelProperty("最终用户关系分析")
    private String userRelationAnalysis;
        
    /**
    参与集成商情况分析 
    */
    @ApiModelProperty("参与集成商情况分析")
    private String integratorSituation;
        
    /**
    竞争对手情况分析 
    */
    @ApiModelProperty("竞争对手情况分析")
    private String competeOpponentAnalysis;
        
    /**
    项目运作策略 
    */
    @ApiModelProperty("项目运作策略")
    private String projectRunStrategy;

    /**
    操作员,关联sys_user表user_id 
    */
    @ApiModelProperty("操作员,关联sys_user表user_id")
    private String operator;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty("最后操作时间")
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     审核时间
     */
    @ApiModelProperty("审核时间")
    private Date checkDate;

}