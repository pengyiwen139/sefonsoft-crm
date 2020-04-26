package com.sefonsoft.oa.domain.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.entity.project.ProjectAmountDevideRef;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

/**
 * (ProjectInfo)实体类
 *
 * @author Aron
 * @since 2019-12-03 14:24:49
 */
@ApiModel("项目导出类")
public class ProjectInfoQueryExpDTO implements Serializable {
    private static final long serialVersionUID = 766071304935980918L;

    /**
     * 当前用户的数据角色下的部门编号列表
     */
    @ApiModelProperty(value="数据角色下的部门编号列表")
    private List<String> dataDeptIdList;

    /**
     * 员工对应职系
     */
    @ApiModelProperty("员工对应职系,必须传")
    private String gradeId;
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
    /**
     * 部门编号
     */
    @ApiModelProperty("传部门编号/返回负责人部门编号")
    private String deptId;
    /**
     * 客户编号
     */
    @ApiModelProperty("传员工工号/返回负责人工号(必传)")
    private String employeeId;
        
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
    @ApiModelProperty("项目名称(展示项)")
    private String projectName;


    /**
     项目状态,关联project_status_info表pros_id
     */
    @ApiModelProperty("项目状态,关联project_status_info表pros_id")
    private Integer prosId;

    /**
     项目状态名称
     */
    @ApiModelProperty("项目状态名称(展示项)")
    private String prosName;

    /**
     '审核状态,1未审核，2审核通过，0驳回',
     */
    @ApiModelProperty("审核状态,1未审核，2审核通过，0驳回(展示项)(审核时候修改状态)")
    private Integer checkStatus;

    /**
     客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty("客户编号,索引，关联customer_info表customer_id")
    private String customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称(展示项)")
    private String customerName;

    /**
     检察院，海关，粮食，邮政，气象，国土，组织，交通，扶贫，消防，知识产权，商务，地铁，电力，石油，新闻传媒，金融，工商，法院，公安，安监，税务，工业经济，工信厅/经信委/大数据局，发改，地下管廊，城管，财政，环保，检验检疫，政法委，人社，旅游，广电，无线电，统计，质监，民政，公共资源，应急，审计，园区，水利，人防，建设，监狱，机关事务管理局，纪检监察，军工，院校，运营商，信访，司法，林业，农业，烟草，企业，卫生疾控，医院，设计院/研究院，规划，电子政务，其他
     */
    @ApiModelProperty("所属行业(展示项)")
    private String industry;

    /**
    是否重大项目,1是，2不是 
    */
    @ApiModelProperty("是否重大项目,1是，2否(展示项)")
    private Integer hasImportant;

    /**
     项目总投资金额
     */
    @ApiModelProperty("项目总投资金额万元(展示项)")
    private BigDecimal totalInvestment;

    /**
     填字符串，已申请立项，资金到位，等
     */
    @ApiModelProperty("客户项目阶段，已申请立项，资金到位，等(展示项)")
    private String customerProjectPhase;

    /**
     项目所在城市，根据客户的省市存省市的字符串
     */
    @ApiModelProperty("项目所在城市(展示项)")
    private String location;

    /**
    指客户方的项目联系人 
    */
    @ApiModelProperty("指客户方的项目联系人id")
    private Long contactId;

    /**
     指客户方的项目联系人
     */
    @ApiModelProperty("指客户方的项目联系人(展示项)")
    private String contacts;
        
    /**
    指客户方的项目联系人部门 
    */
    @ApiModelProperty("指客户方的项目联系人部门(展示项)")
    private String contactDeptName;
        
    /**
    指客户方的项目联系人职务 
    */
    @ApiModelProperty("指客户方的项目联系人职务(展示项)")
    private String contactJob;
        
    /**
    指客户方的项目联系人手机 
    */
    @ApiModelProperty("指客户方的项目联系人手机(展示项)")
    private String contactTel;


    /**
    合作伙伴编号，索引，关联customer_info表customer_id 
    */
    @ApiModelProperty("合作伙伴编号，索引，关联customer_info表customer_id")
    private String partnerId;

    /**
     合作伙名字
     */
    @ApiModelProperty("合作伙伴名称(展示项)")
    private String partnerName;
        
    /**
    指客户方的合作伙伴部门 
    */
    @ApiModelProperty("指客户方的合作伙伴部门(展示项)")
    private String customerDeptName;

    /**
     合作伙伴联系人id
     */
    @ApiModelProperty("客户方合作伙伴联系人id ")
    private Long partnerContactId;
    /**
    指客户的合作伙伴联系人 
    */
    @ApiModelProperty("指客户的合作伙伴联系人(展示项)")
    private String customerContact;
        
    /**
    指客户的合作伙伴联系人手机 
    */
    @ApiModelProperty("指客户的合作伙伴联系人手机(展示项)")
    private String customerContactTel;


    /**
     指客户的合作伙伴联系人职务
     */
    @ApiModelProperty("指客户的合作伙伴联系人职务(展示项)")
    private String customerContactJob;

    /**
     * 销售负责人名称
     */
    @ApiModelProperty(value="销售负责人(展示项)")
    private String employeeName;

    /**
     销售阶段,关联sales_project_stage表spstage_id
     */
    @ApiModelProperty("销售阶段,关联sales_project_stage表spstage_id")
    private Integer spstageId;

    /**
     销售阶段名称
     */
    @ApiModelProperty("销售阶段名称(展示项)")
    private String spstageName;
    /**
     * 部门名称
     */
    @ApiModelProperty(value="负责人部门(展示项)")
    private String deptName;
        
    /**
    预估项目金额，单位：万元 
    */
    @ApiModelProperty("预估项目金额，单位：万元(展示项)")
    private BigDecimal estimateMoney;
        
    /**
    预估签约时间 
    */
    @ApiModelProperty("预估签约时间(展示项)")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date estimateTime;
        
    /**
    百分比数字 
    */
    @ApiModelProperty("百分比数字(展示项)")
    private Integer estimateSuccess;


    /**
     最后操作时间
     */
    @ApiModelProperty("最后操作时间(展示项)")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /**
     创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
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
     疑似相似项目
     */
    @ApiModelProperty("疑似相似项目，0无相似的项目，1有相似的项目'")
    private Integer hasSimilarType;
    /**
     过期项目标示,0未过期，1过期
     */
    @ApiModelProperty("过期项目标示,0未过期，1过期且被移动至部门资源池，2过期且被移动至公司资源池")
    private Integer overTimeFlag;

    /**
     审核意见
     */
    @ApiModelProperty("审核意见(审核时候填意见)")
    private String opinion;

    /**
     项目所用的产品信息列表
     */
    @ApiModelProperty("项目所用的产品信息列表")
    private List<ProductProjectRef> productProjectRefList;

    /**
     产品名称
     */
    @ApiModelProperty("产品名称")
    private String productName;

    /**
     销售数量
     */
    @ApiModelProperty("销售数量")
    private Integer saleCount;

    /**
     金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;

    /**
     项目所用的产品信息列表
     */
    @ApiModelProperty("项目业绩分隔所需信息列表")
    private List<ProjectAmountDevideRef> projectAmountDevideRefList;

    /**
     相似项目列表，只展示相似的信息，其他信息不返回
     */
    @ApiModelProperty("相似项目列表，只展示相似的信息，其他信息不返回")
    private List<ProjectSimilarDTO> similarProjectList;


    /**
     * 在职状态，1在职，2离职
     */
    @ApiModelProperty(value="在职状态")
    private Integer jobStatus;

    /**
     创建时间
     */
    @ApiModelProperty("项目创建时间")
    private Date projectCreateTime;

    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
    private Integer rows;
    
    /**
     * 删除标识
     */
    @ApiModelProperty(hidden = true)
    private int isDelete;

    public List<ProjectSimilarDTO> getSimilarProjectList() {
        return similarProjectList;
    }

    public void setSimilarProjectList(List<ProjectSimilarDTO> similarProjectList) {
        this.similarProjectList = similarProjectList;
    }

    public Integer getOffset() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<ProductProjectRef> getProductProjectRefList() {
        return productProjectRefList;
    }

    public void setProductProjectRefList(List<ProductProjectRef> productProjectRefList) {
        this.productProjectRefList = productProjectRefList;
    }

    public List<ProjectAmountDevideRef> getProjectAmountDevideRefList() {
        return projectAmountDevideRefList;
    }

    public void setProjectAmountDevideRefList(List<ProjectAmountDevideRef> projectAmountDevideRefList) {
        this.projectAmountDevideRefList = projectAmountDevideRefList;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHalfYear() {
        return halfYear;
    }

    public void setHalfYear(String halfYear) {
        this.halfYear = halfYear;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProsName() {
        return prosName;
    }

    public void setProsName(String prosName) {
        this.prosName = prosName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSpstageName() {
        return spstageName;
    }

    public void setSpstageName(String spstageName) {
        this.spstageName = spstageName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setEstimateTime(Date estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getHasImportant() {
        return hasImportant;
    }

    public void setHasImportant(Integer hasImportant) {
        this.hasImportant = hasImportant;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getCustomerProjectPhase() {
        return customerProjectPhase;
    }

    public void setCustomerProjectPhase(String customerProjectPhase) {
        this.customerProjectPhase = customerProjectPhase;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getContactDeptName() {
        return contactDeptName;
    }

    public void setContactDeptName(String contactDeptName) {
        this.contactDeptName = contactDeptName;
    }

    public String getContactJob() {
        return contactJob;
    }

    public void setContactJob(String contactJob) {
        this.contactJob = contactJob;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCustomerDeptName() {
        return customerDeptName;
    }

    public void setCustomerDeptName(String customerDeptName) {
        this.customerDeptName = customerDeptName;
    }

    public Long getPartnerContactId() {
        return partnerContactId;
    }

    public void setPartnerContactId(Long partnerContactId) {
        this.partnerContactId = partnerContactId;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerContactTel() {
        return customerContactTel;
    }

    public void setCustomerContactTel(String customerContactTel) {
        this.customerContactTel = customerContactTel;
    }

    public BigDecimal getEstimateMoney() {
        return estimateMoney;
    }

    public void setEstimateMoney(BigDecimal estimateMoney) {
        this.estimateMoney = estimateMoney;
    }

    public Object getEstimateTime() {
        return estimateTime;
    }


    public Integer getEstimateSuccess() {
        return estimateSuccess;
    }

    public void setEstimateSuccess(Integer estimateSuccess) {
        this.estimateSuccess = estimateSuccess;
    }

    public String getProjectSituation() {
        return projectSituation;
    }

    public void setProjectSituation(String projectSituation) {
        this.projectSituation = projectSituation;
    }

    public String getUserRelationAnalysis() {
        return userRelationAnalysis;
    }

    public void setUserRelationAnalysis(String userRelationAnalysis) {
        this.userRelationAnalysis = userRelationAnalysis;
    }

    public String getIntegratorSituation() {
        return integratorSituation;
    }

    public void setIntegratorSituation(String integratorSituation) {
        this.integratorSituation = integratorSituation;
    }

    public String getCompeteOpponentAnalysis() {
        return competeOpponentAnalysis;
    }

    public void setCompeteOpponentAnalysis(String competeOpponentAnalysis) {
        this.competeOpponentAnalysis = competeOpponentAnalysis;
    }

    public String getProjectRunStrategy() {
        return projectRunStrategy;
    }

    public void setProjectRunStrategy(String projectRunStrategy) {
        this.projectRunStrategy = projectRunStrategy;
    }

    public Integer getSpstageId() {
        return spstageId;
    }

    public void setSpstageId(Integer spstageId) {
        this.spstageId = spstageId;
    }

    public Integer getProsId() {
        return prosId;
    }

    public void setProsId(Integer prosId) {
        this.prosId = prosId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getCustomerContactJob() {
        return customerContactJob;
    }

    public void setCustomerContactJob(String customerContactJob) {
        this.customerContactJob = customerContactJob;
    }

    public List<String> getDataDeptIdList() {
        return dataDeptIdList;
    }

    public void setDataDeptIdList(List<String> dataDeptIdList) {
        this.dataDeptIdList = dataDeptIdList;
    }

    public Integer getHasSimilarType() {
        return hasSimilarType;
    }

    public void setHasSimilarType(Integer hasSimilarType) {
        this.hasSimilarType = hasSimilarType;
    }

    public Integer getOverTimeFlag() {
        return overTimeFlag;
    }

    public void setOverTimeFlag(Integer overTimeFlag) {
        this.overTimeFlag = overTimeFlag;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getIsDelete() {
      return isDelete;
    }

    public void setIsDelete(int isDelete) {
      this.isDelete = isDelete;
    }
    
    
}