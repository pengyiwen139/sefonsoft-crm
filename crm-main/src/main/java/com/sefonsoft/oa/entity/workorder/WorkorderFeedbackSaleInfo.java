package com.sefonsoft.oa.entity.workorder;

import java.io.Serializable;
import java.util.Date;

/**
 * workorder_feedback_sale_info
 *
 * @author
 */
public class WorkorderFeedbackSaleInfo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 派工单id
     */
    private String pgdId;

    /**
     * 销售id
     */
    private String employeeId;

    /**
     * 售前支持员工id
     */
    private String targetEmployeeId;

    /**
     * 售前支持员工名字
     */
    private String targetEmployeeName;

    /**
     * 售前支持员工部门id
     */
    private String targetEmployeeDeptId;

    /**
     * 售前支持员工部门名字
     */
    private String targetEmployeeDeptName;

    /**
     * 时间遵守5优秀、4良好、3合格、2较差、1很差
     */
    private Integer timeComply;

    /**
     * 服务态度,5优秀、4良好、3合格、2较差、1很差
     */
    private Integer service;

    /**
     * 技术水平, 5优秀、4良好、3合格、2较差、1很差
     */
    private Integer technology;

    /**
     * 案例掌握, 5优秀、4良好、3合格、2较差、1很差
     */
    private Integer caseGrasp;

    /**
     * 客户引导, 5优秀、4良好、3合格、2较差、1很差
     */
    private Integer customerLead;

    /**
     * 客户反馈, 5优秀、4良好、3合格、2较差、1很差
     */
    private Integer customerFeedback;

    /**
     * 其它
     */
    private String other;

    /**
     * 评价状态：0待评价，1已评价
     */
    private Integer saleCommentStatus;

    /**
     * 最后修改时间
     */
    private Date modifiedTime;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPgdId() {
        return pgdId;
    }

    public void setPgdId(String pgdId) {
        this.pgdId = pgdId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTargetEmployeeId() {
        return targetEmployeeId;
    }

    public void setTargetEmployeeId(String targetEmployeeId) {
        this.targetEmployeeId = targetEmployeeId;
    }

    public String getTargetEmployeeName() {
        return targetEmployeeName;
    }

    public void setTargetEmployeeName(String targetEmployeeName) {
        this.targetEmployeeName = targetEmployeeName;
    }

    public Integer getTimeComply() {
        return timeComply;
    }

    public void setTimeComply(Integer timeComply) {
        this.timeComply = timeComply;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getTechnology() {
        return technology;
    }

    public void setTechnology(Integer technology) {
        this.technology = technology;
    }

    public Integer getCaseGrasp() {
        return caseGrasp;
    }

    public void setCaseGrasp(Integer caseGrasp) {
        this.caseGrasp = caseGrasp;
    }

    public Integer getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(Integer customerLead) {
        this.customerLead = customerLead;
    }

    public Integer getCustomerFeedback() {
        return customerFeedback;
    }

    public void setCustomerFeedback(Integer customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getSaleCommentStatus() {
        return saleCommentStatus;
    }

    public void setSaleCommentStatus(Integer saleCommentStatus) {
        this.saleCommentStatus = saleCommentStatus;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WorkorderFeedbackSaleInfo other = (WorkorderFeedbackSaleInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPgdId() == null ? other.getPgdId() == null : this.getPgdId().equals(other.getPgdId()))
                && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
                && (this.getTargetEmployeeId() == null ? other.getTargetEmployeeId() == null : this.getTargetEmployeeId().equals(other.getTargetEmployeeId()))
                && (this.getTargetEmployeeName() == null ? other.getTargetEmployeeName() == null : this.getTargetEmployeeName().equals(other.getTargetEmployeeName()))
                && (this.getTimeComply() == null ? other.getTimeComply() == null : this.getTimeComply().equals(other.getTimeComply()))
                && (this.getService() == null ? other.getService() == null : this.getService().equals(other.getService()))
                && (this.getTechnology() == null ? other.getTechnology() == null : this.getTechnology().equals(other.getTechnology()))
                && (this.getCaseGrasp() == null ? other.getCaseGrasp() == null : this.getCaseGrasp().equals(other.getCaseGrasp()))
                && (this.getCustomerLead() == null ? other.getCustomerLead() == null : this.getCustomerLead().equals(other.getCustomerLead()))
                && (this.getCustomerFeedback() == null ? other.getCustomerFeedback() == null : this.getCustomerFeedback().equals(other.getCustomerFeedback()))
                && (this.getOther() == null ? other.getOther() == null : this.getOther().equals(other.getOther()))
                && (this.getSaleCommentStatus() == null ? other.getSaleCommentStatus() == null : this.getSaleCommentStatus().equals(other.getSaleCommentStatus()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPgdId() == null) ? 0 : getPgdId().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getTargetEmployeeId() == null) ? 0 : getTargetEmployeeId().hashCode());
        result = prime * result + ((getTargetEmployeeName() == null) ? 0 : getTargetEmployeeName().hashCode());
        result = prime * result + ((getTimeComply() == null) ? 0 : getTimeComply().hashCode());
        result = prime * result + ((getService() == null) ? 0 : getService().hashCode());
        result = prime * result + ((getTechnology() == null) ? 0 : getTechnology().hashCode());
        result = prime * result + ((getCaseGrasp() == null) ? 0 : getCaseGrasp().hashCode());
        result = prime * result + ((getCustomerLead() == null) ? 0 : getCustomerLead().hashCode());
        result = prime * result + ((getCustomerFeedback() == null) ? 0 : getCustomerFeedback().hashCode());
        result = prime * result + ((getOther() == null) ? 0 : getOther().hashCode());
        result = prime * result + ((getSaleCommentStatus() == null) ? 0 : getSaleCommentStatus().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pgdId=").append(pgdId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", targetEmployeeId=").append(targetEmployeeId);
        sb.append(", targetEmployeeName=").append(targetEmployeeName);
        sb.append(", timeComply=").append(timeComply);
        sb.append(", service=").append(service);
        sb.append(", technology=").append(technology);
        sb.append(", caseGrasp=").append(caseGrasp);
        sb.append(", customerLead=").append(customerLead);
        sb.append(", customerFeedback=").append(customerFeedback);
        sb.append(", other=").append(other);
        sb.append(", saleCommentStatus=").append(saleCommentStatus);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}