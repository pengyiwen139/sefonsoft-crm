package com.sefonsoft.oa.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (SysUser)实体类
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
@ApiModel(value = "用户展示类")
public class SysUserQueryDTO implements Serializable {
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

    @Override
    public String toString() {
        return "SysUserQueryDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", wechatAccount='" + wechatAccount + '\'' +
                ", enabled=" + enabled +
                ", lastLoginTime=" + lastLoginTime +
                ", modifiedTime=" + modifiedTime +
                ", createTime=" + createTime +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}