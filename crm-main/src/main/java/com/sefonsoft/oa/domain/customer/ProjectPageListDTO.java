package com.sefonsoft.oa.domain.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 客户详情/项目相关展示
 *
 * @author Aron
 * @since 2019-11-21 10:23:10
 */
@ApiModel(value = "客户详情/项目相关展示DTO")
@Accessors(chain = true)
@Data
public class ProjectPageListDTO {

    /**
     主键
     */
    @ApiModelProperty("主键") private String id;

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
     项目总投资金额,单位：万元
     */
    @ApiModelProperty("项目总投资金额,单位：万元")
    private String totalInvestment;

    /**
     预估项目金额,单位：万元
     */
    @ApiModelProperty("预估项目金额,单位：万元")
    private String estimateMoney;

    /**
     销售阶段,关承sales_project_stage表spstage_id
     */
    @ApiModelProperty("销售阶段,关承sales_project_stage表spstage_id")
    private String spstageId;

    /**
     销售阶段名称
     */
    @ApiModelProperty("销售阶段名称,1=初期沟通10%，2=方案交流30%，3=客户立项50%，4=招投标中70%，5=合同谈判90%，6=合同落地100%")
    private String spstageName;

    /**
     预估签约时间,年月日
     */
    @ApiModelProperty("预估签约时间,年月日")
    private String estimateTime;

    /**
     客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty("客户编号,索引，关联customer_info表customer_id")
    private String customerId;

    /**
     客户名称
     */
    @ApiModelProperty("客户名称")
    private String customerName;

    /**
     客户名称
     */
    @ApiModelProperty("客户联系人/项目负责人")
    private String contacts;

    /**
     客户电话
     */
    @ApiModelProperty("客户电话")
    private String telephone;

    /**
     项目共有人，逗号隔开
     */
    @ApiModelProperty(value = "项目共有人，逗号隔开", hidden = true)
    private String cowner;

    /**
     项目负责人工号
     */
    @ApiModelProperty("销售负责人")
    private String chargerId;

    /**
     项目负责人名称
     */
    @ApiModelProperty("销售负责人名称")
    private String chargerName;

    /**
     项目共有人工号
     */
    @ApiModelProperty(value = "项目共有人工号", hidden = true)
    private String jointOwnerId;

    /**
     项目共有人名称
     */
    @ApiModelProperty(value = "项目共有人名称",hidden = true)
    private String jointOwnerName;

    /**
     项目负责人部门
     */
    @ApiModelProperty(value = "销售负责人部门编号", hidden = true)
    private String chargerDeptId;

    /**
     项目负责人部门名称
     */
    @ApiModelProperty("销售负责人部门名称")
    private String chargerDeptName;

    /**
     项目共有人部门编号
     */
    @ApiModelProperty(value = "项目共有人部门编号", hidden = true)
    private String jointOwnerDeptId;

    /**
     项目共有人部门名称
     */
    @ApiModelProperty(value = "项目共有人部门名称", hidden = true)
    private String jointOwnerDeptName;

    @ApiModelProperty(value = "更新时间")
    private String lastTime;

    /**
     立项时间,年月日
     */
    @ApiModelProperty("公司项目立项时间")
    private String applyTime;



    /**
     合同金额,单位：万元
     */
    @ApiModelProperty("合同金额,单位：万元")
    private String contractAmount;

    /**
     签订时间,年月日
     */
    @ApiModelProperty("合同签订时间")
    private String signTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(String totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getEstimateMoney() {
        return estimateMoney;
    }

    public void setEstimateMoney(String estimateMoney) {
        this.estimateMoney = estimateMoney;
    }

    public String getSpstageId() {
        return spstageId;
    }

    public void setSpstageId(String spstageId) {
        this.spstageId = spstageId;
    }

    public String getSpstageName() {
        return spstageName;
    }

    public void setSpstageName(String spstageName) {
        this.spstageName = spstageName;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCowner() {
        return cowner;
    }

    public void setCowner(String cowner) {
        this.cowner = cowner;
    }

    public String getChargerId() {
        return chargerId;
    }

    public void setChargerId(String chargerId) {
        this.chargerId = chargerId;
    }

    public String getChargerName() {
        return chargerName;
    }

    public void setChargerName(String chargerName) {
        this.chargerName = chargerName;
    }

    public String getJointOwnerId() {
        return jointOwnerId;
    }

    public void setJointOwnerId(String jointOwnerId) {
        this.jointOwnerId = jointOwnerId;
    }

    public String getJointOwnerName() {
        return jointOwnerName;
    }

    public void setJointOwnerName(String jointOwnerName) {
        this.jointOwnerName = jointOwnerName;
    }

    public String getChargerDeptId() {
        return chargerDeptId;
    }

    public void setChargerDeptId(String chargerDeptId) {
        this.chargerDeptId = chargerDeptId;
    }

    public String getChargerDeptName() {
        return chargerDeptName;
    }

    public void setChargerDeptName(String chargerDeptName) {
        this.chargerDeptName = chargerDeptName;
    }

    public String getJointOwnerDeptId() {
        return jointOwnerDeptId;
    }

    public void setJointOwnerDeptId(String jointOwnerDeptId) {
        this.jointOwnerDeptId = jointOwnerDeptId;
    }

    public String getJointOwnerDeptName() {
        return jointOwnerDeptName;
    }

    public void setJointOwnerDeptName(String jointOwnerDeptName) {
        this.jointOwnerDeptName = jointOwnerDeptName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }
}