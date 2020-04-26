package com.sefonsoft.oa.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUser)实体类
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
@ApiModel(value = "用户实体类")
public class SysUser implements Serializable {
    private static final long serialVersionUID = -66616827946163241L;
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;
    /**
     * 工牌号,主键
     */
    @ApiModelProperty(value="用户编号工牌号")
    private String userId;
    /**
     * 密码，不低于6位
     */
    @ApiModelProperty(value="密码，默认123456")
    private String password;

    /**
     * 头像路径
     */
    @ApiModelProperty(value="头像路径")
    private String avatar;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value="用户昵称")
    private String nickName;
    /**
     * 微信账号,用户所用的微信号
     */
    @ApiModelProperty(value="用户所用的微信号")
    private String wechatAccount;
    /**
     * 是否可用,1可用；2禁用
     */
    @ApiModelProperty(value="是否可用",example="1可用，2禁用")
    private Integer enabled;
    /**
    * 最后登录时间
    */
    @ApiModelProperty(value="最后登录时间")
    private Date lastLoginTime;
    /**
    * 操作时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date modifiedTime;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /** 角色组 */
    @ApiModelProperty(value="角色编号数组",example="[admin，user]")
    private String[] roleIds;

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

}