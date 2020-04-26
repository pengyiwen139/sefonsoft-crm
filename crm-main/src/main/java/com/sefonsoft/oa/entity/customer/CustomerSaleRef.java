package com.sefonsoft.oa.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * (CustomerSaleRef)实体类
 *
 * @author Aron
 * @since 2019-11-15 16:25:45
 */
@ApiModel(value ="客户和销售关联表")
public class CustomerSaleRef implements Serializable {
    private static final long serialVersionUID = -86016245034440792L;
    /**
    * 自增长
    */
    private Long id;
    /**
    * 索引，关联customer_info表customer_id
    */
    @ApiModelProperty(value="客户编号")
    private String customerId;
    /**
    * 负责人工号,索引，关联sys_employee表employee_id
    */
    private String employeeId;
    /**
    * 共有人工号,工号逗号分隔，关联sys_employee表employee_id
    */
    @ApiModelProperty(value="共有人工号逗号分隔 ,example=SF10001,SF10002,SF10003")
    private String cowner;
    /**
    * 关联sys_user表user_id
    */
    private String operator;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date lastTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCowner() {
        return cowner;
    }

    public void setCowner(String cowner) {
        this.cowner = cowner;
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


    @Override
    public String toString() {
        return "CustomerSaleRef{" +
            "id=" + id +
            ", customerId='" + customerId + '\'' +
            ", employeeId='" + employeeId + '\'' +
            ", cowner='" + cowner + '\'' +
            ", operator='" + operator + '\'' +
            ", lastTime=" + lastTime +
            ", createTime=" + createTime +
            '}';
    }
}