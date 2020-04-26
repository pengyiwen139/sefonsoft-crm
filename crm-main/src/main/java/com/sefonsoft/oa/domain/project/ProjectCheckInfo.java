package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * (ProjectCheckInfoInsertDTO)实体类
 *
 * @author Aron
 * @since 2019-12-04 17:23:21
 */
@ApiModel("项目审核信息实体类")

public class ProjectCheckInfo implements Serializable {
    private static final long serialVersionUID = 540720404032774821L;
    private Long id;

    /**
     项目编号，索引，关联project_info表project_id
     */
    @ApiModelProperty("项目编号，索引，关联project_info表project_id")
    private String projectId;

    /**
     审核人工号，索引，关联sys_employee表employee_id
     */
    @ApiModelProperty("审核人工号，索引，关联sys_employee表employee_id")
    private String employeeId;

    /**
     审核意见
     */
    @ApiModelProperty("审核意见")
    private String opinion;

    /**
     审核状态,1未审核，2审核通过，0已驳回
     */
    @ApiModelProperty("审核状态,1未审核，2审核通过，0已驳回")
    private Integer checkStatus;

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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
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

}