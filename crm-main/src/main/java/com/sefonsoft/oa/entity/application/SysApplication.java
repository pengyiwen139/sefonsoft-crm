package com.sefonsoft.oa.entity.application;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/9 10:41
 * @description：系统应用实体类
 * @modified By：
 */
@Data
public class SysApplication {

    /**
     主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     应用编号
     */
    @ApiModelProperty("应用编号")
    private String appId;

    /**
     应用名称
     */
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     操作时间
     */
    @ApiModelProperty(hidden = true)
    private Date modifiedTime;

    /**
     创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}