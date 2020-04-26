package com.sefonsoft.oa.domain.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.entity.project.ProjectAmountDevideRef;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 机会项目新增实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 15:32:44
 */
@ApiModel("机会项目新增实体类")
@Accessors(chain = true)
@Data
public class ProjectInfoGetOneDTO implements Serializable {

    private static final long serialVersionUID = 3566036737876162986L;
    /**
     主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     项目编号,索引+主键，编号规则：前缀+日期+流水（4位）
     */
    @ApiModelProperty(value = "项目编号,索引+主键，编号规则：前缀+日期+流水（4位）")
    private String projectId;

    /**
     项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /**
     是否重大项目,1是，2不是
     */
    @ApiModelProperty(value = "是否重大项目,1是，2不是")
    private Integer hasImportant;
    /**
     销售阶段,关联sales_project_stage表spstage_id
     */
    @ApiModelProperty(value = "销售阶段,关联sales_project_stage表spstage_id")
    private Integer spstageId;
    /**
     项目状态,关联project_status_info表pros_id,后台设置默认为意向
     */
    @ApiModelProperty(value = "项目状态,关联project_status_info表pros_id")
    private Integer prosId;

    /**
     项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他
     */
    @ApiModelProperty(value = "项目所属行业，可自定义输入，也可选择检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他",example = "军工")
    private String industry;
    /**
     客户项目阶段，填字符串，已申请立项，资金到位，等
     */
    @ApiModelProperty(value = "客户项目阶段，填字符串，已申请立项，资金到位，等")
    private String customerProjectPhase;



    /**
     客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty(value = "客户编号,关联customer_info表customer_id")
    private String customerId;
    /**
     指客户方的项目联系人编号
     */
    @ApiModelProperty(value = "指客户方的项目联系人编号")
    private Long contactId;
    /**
     合作伙伴编号，索引，关联customer_info表customer_id
     */
    @ApiModelProperty(value = "合作伙伴编号，索引，关联customer_info表customer_id")
    private String partnerId;
    /**
     合作伙伴的联系人编号
     */
    @ApiModelProperty(value = "合作伙伴的联系人编号")
    private Long partnerContactId;



    /**
     项目总投资金额
     */
    @ApiModelProperty(value = "项目总投资金额")
    private BigDecimal totalInvestment;

    /**
     项目所在城市，根据客户的省市存省市的字符串
     */
    @ApiModelProperty(value = "项目所在城市，根据客户的省市存省市的字符串")
    private String location;


    /**
     预估项目金额，单位：万元
     */
    @ApiModelProperty(value = "预估项目金额，单位：万元")
    private BigDecimal estimateMoney;

    /**
     预估签约时间
     */
    @ApiModelProperty("预估签约时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date estimateTime;

    /**
     预估成功率，百分比数字
     */
    @ApiModelProperty(value = "预估成功率，百分比数字，最大100")
    private Integer estimateSuccess;

    /**
     项目总情况
     */
    @ApiModelProperty(value = "项目总情况")
    private String projectSituation;

    /**
     最终用户关系分析
     */
    @ApiModelProperty(value = "最终用户关系分析")
    private String userRelationAnalysis;

    /**
     参与集成商情况分析
     */
    @ApiModelProperty(value = "参与集成商情况分析")
    private String integratorSituation;

    /**
     竞争对手情况分析
     */
    @ApiModelProperty(value = "竞争对手情况分析")
    private String competeOpponentAnalysis;

    /**
     项目运作策略
     */
    @ApiModelProperty(value = "项目运作策略")
    private String projectRunStrategy;

    @ApiModelProperty("添加项目所用的产品信息")
    private List<ProductProjectRef> productProjectRefList;

    @ApiModelProperty("添加项目业绩分隔所需信息")
    private List<ProjectAmountDevideRef> projectAmountDevideRefList;


    @ApiModelProperty(value = "负责人工号")
    private String employeeId;

    /**
     操作员,关联sys_user表user_id
     */
    @ApiModelProperty("操作人")
    private String operator;

    /**
     最后操作时间
     */
    @ApiModelProperty("更新时间")
    private Date lastTime;

    /**
     创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty(value = "合作伙伴名称")
    private String partnerName;

    @ApiModelProperty(value = "最终客户名称")
    private String customerName;


    @ApiModelProperty(value = "客户联系人名称")
    private String contactName;

    @ApiModelProperty(value = "客户联系人职称")
    private String contactJob;

    @ApiModelProperty(value = "客户联系人部门名称")
    private String contactDeptName;

    @ApiModelProperty(value = "客户联系人电话")
    private String contactTel;

    @ApiModelProperty(value = "合作伙伴联系人名称")
    private String partnerContactName;

    @ApiModelProperty(value = "合作伙伴联系人部门名称")
    private String partnerContactDeptName;

    @ApiModelProperty(value = "合作伙伴联系人电话")
    private String partnerContactTel;

    @ApiModelProperty(value = "0无相似的项目，1有相似的项目", hidden = true)
    private Integer hasSimilarType;

    @ApiModelProperty(value = "过期项目标示,0未过期，1过期")
    private Integer overTimeFlag;

    @ApiModelProperty(value = "是否校验相似相似,0否，1是")
    private Integer checkSimilarFlag;
    
    @ApiModelProperty(value = "关联商机 ID")
    private Long bizOpportId;
    
    @ApiModelProperty(value = "审批状态")
    private Integer checkStatus;

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
      return serialVersionUID;
    }

    /**
     * @return the id
     */
    public Long getId() {
      return id;
    }

    /**
     * @return the projectId
     */
    public String getProjectId() {
      return projectId;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
      return projectName;
    }

    /**
     * @return the hasImportant
     */
    public Integer getHasImportant() {
      return hasImportant;
    }

    /**
     * @return the spstageId
     */
    public Integer getSpstageId() {
      return spstageId;
    }

    /**
     * @return the prosId
     */
    public Integer getProsId() {
      return prosId;
    }

    /**
     * @return the industry
     */
    public String getIndustry() {
      return industry;
    }

    /**
     * @return the customerProjectPhase
     */
    public String getCustomerProjectPhase() {
      return customerProjectPhase;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
      return customerId;
    }

    /**
     * @return the contactId
     */
    public Long getContactId() {
      return contactId;
    }

    /**
     * @return the partnerId
     */
    public String getPartnerId() {
      return partnerId;
    }

    /**
     * @return the partnerContactId
     */
    public Long getPartnerContactId() {
      return partnerContactId;
    }

    /**
     * @return the totalInvestment
     */
    public BigDecimal getTotalInvestment() {
      return totalInvestment;
    }

    /**
     * @return the location
     */
    public String getLocation() {
      return location;
    }

    /**
     * @return the estimateMoney
     */
    public BigDecimal getEstimateMoney() {
      return estimateMoney;
    }

    /**
     * @return the estimateTime
     */
    public Date getEstimateTime() {
      return estimateTime;
    }

    /**
     * @return the estimateSuccess
     */
    public Integer getEstimateSuccess() {
      return estimateSuccess;
    }

    /**
     * @return the projectSituation
     */
    public String getProjectSituation() {
      return projectSituation;
    }

    /**
     * @return the userRelationAnalysis
     */
    public String getUserRelationAnalysis() {
      return userRelationAnalysis;
    }

    /**
     * @return the integratorSituation
     */
    public String getIntegratorSituation() {
      return integratorSituation;
    }

    /**
     * @return the competeOpponentAnalysis
     */
    public String getCompeteOpponentAnalysis() {
      return competeOpponentAnalysis;
    }

    /**
     * @return the projectRunStrategy
     */
    public String getProjectRunStrategy() {
      return projectRunStrategy;
    }

    /**
     * @return the productProjectRefList
     */
    public List<ProductProjectRef> getProductProjectRefList() {
      return productProjectRefList;
    }

    /**
     * @return the projectAmountDevideRefList
     */
    public List<ProjectAmountDevideRef> getProjectAmountDevideRefList() {
      return projectAmountDevideRefList;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
      return employeeId;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
      return operator;
    }

    /**
     * @return the lastTime
     */
    public Date getLastTime() {
      return lastTime;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
      return createTime;
    }

    /**
     * @return the partnerName
     */
    public String getPartnerName() {
      return partnerName;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
      return contactName;
    }

    /**
     * @return the contactJob
     */
    public String getContactJob() {
      return contactJob;
    }

    /**
     * @return the contactDeptName
     */
    public String getContactDeptName() {
      return contactDeptName;
    }

    /**
     * @return the contactTel
     */
    public String getContactTel() {
      return contactTel;
    }

    /**
     * @return the partnerContactName
     */
    public String getPartnerContactName() {
      return partnerContactName;
    }

    /**
     * @return the partnerContactDeptName
     */
    public String getPartnerContactDeptName() {
      return partnerContactDeptName;
    }

    /**
     * @return the partnerContactTel
     */
    public String getPartnerContactTel() {
      return partnerContactTel;
    }

    /**
     * @return the hasSimilarType
     */
    public Integer getHasSimilarType() {
      return hasSimilarType;
    }

    /**
     * @return the overTimeFlag
     */
    public Integer getOverTimeFlag() {
      return overTimeFlag;
    }

    /**
     * @return the checkSimilarFlag
     */
    public Integer getCheckSimilarFlag() {
      return checkSimilarFlag;
    }

    /**
     * @return the bizOpportId
     */
    public Long getBizOpportId() {
      return bizOpportId;
    }

    /**
     * @return the checkStatus
     */
    public Integer getCheckStatus() {
      return checkStatus;
    }
    
    
    

}