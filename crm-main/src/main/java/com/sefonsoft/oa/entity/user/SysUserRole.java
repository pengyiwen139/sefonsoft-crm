package com.sefonsoft.oa.entity.user;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author Aron
 * @since 2019-11-11 13:35:10
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 852549268341274032L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 用户编号
    */
    private String userId;
    /**
    * 角色编号
    */
    private String roleId;
    
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}