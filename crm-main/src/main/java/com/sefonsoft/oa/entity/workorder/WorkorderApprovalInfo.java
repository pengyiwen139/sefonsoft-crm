package com.sefonsoft.oa.entity.workorder;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
@Data
public class WorkorderApprovalInfo {

    /**
     * 主键
     */
    private Long id;
    /**
     * 派工单编号：前缀+日期+流水（4位）
     */
    private String pgdId;

    /**
     * 审批人
     */
    private String approvalId;
    /**
     * 审批结果：1待审批，2待评价，3已完成，4已拒绝
     */
    private int approvalResult;
    /**
     * 审批描述
     */
    private String approvalDesc;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;

    @Override
    public String toString() {
        return "WorkorderApprovalInfo{" +
        ", id=" + id +
        ", pgdId=" + pgdId +
        ", approvalId=" + approvalId +
        ", approvalResult=" + approvalResult +
        ", approvalDesc=" + approvalDesc +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
