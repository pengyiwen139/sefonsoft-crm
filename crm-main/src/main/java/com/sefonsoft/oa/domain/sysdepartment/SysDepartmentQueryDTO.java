package com.sefonsoft.oa.domain.sysdepartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysDepartment)实体类
 *
 * @author Aron
 * @since 2019-11-04 16:33:51
 */
@ApiModel(value = "部门展示类")
@Data
public class SysDepartmentQueryDTO implements Serializable {
    private static final long serialVersionUID = 772486149458196720L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;
    /**
     * 部门编号
     */
    @ApiModelProperty(value="部门编号 ,example='BM0003'")
    private String deptId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称 ,example=产品部")
    private String deptName;
    /**
     * 上级部门编号
     */
    @ApiModelProperty(value="上级部门编号,example='BM0001'")
    private String parentId;

    /** 部门层级编号列表逗号分割 */
    @ApiModelProperty(value="部门层级编号列表逗号分割 ,example=0,BM0001,BM0003")
    private String ancestors; /**
     * 直管领导编号，关联sys_employee表的逻辑外键
     */
    @ApiModelProperty(value="直管领导编号")
    private String directLeaderId;
    /**
     * 负责人编号，关联sys_employee表的逻辑外键
     */
    @ApiModelProperty(value="负责人编号")
    private String chargePersonId;
    /**
     * 负责人编号，关联sys_employee表的逻辑外键
     */
    @ApiModelProperty(value="负责人名称")
    private String chargePersonName;
    /**
     * 直管领导名称，关联sys_employee表的逻辑外键
     */
    @ApiModelProperty(value="直管领导名称")
    private String directLeaderName;
    /**
     * 操作人员，关联sys_user表的逻辑外键
     */
    @ApiModelProperty(value="操作人员")
    private String operatorUserId;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 0不正常，1正常
     */
    @ApiModelProperty(value="数据状态" ,example="1")
    private Integer curStatus;


    /**
     * 操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="操作时间")
    private Date modifiedTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer orderNum;


    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @NotNull(message = "页数" + NO_PARAM)
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @NotNull(message = "每页个数" + NO_PARAM)
    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
    private Integer rows;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public String getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(String chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(Integer curStatus) {
        this.curStatus = curStatus;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}