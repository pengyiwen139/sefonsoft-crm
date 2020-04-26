package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * 日报信息表添加实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("日报信息表添加实体类")
@Accessors(chain = true)
@Data
public class DailyReportInfoInsertOrUpdateInfo implements Serializable {

    private static final long serialVersionUID = 1982827395679856204L;

    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    日报id
    */
    @ApiModelProperty(hidden = true)
    private long reportId;
        
    /**
     类型：1客户，2商机，3项目，4日常
    */
    @NotNull(message = "类型" + NO_PARAM)
    @ApiModelProperty(value = "类型：1客户，2商机，3项目，4日常", example = "1")
    private Integer infoType;

    /**
     对应外部id
    */
    @ApiModelProperty(value = "对应外部id ", example = "1")
    private String externalId;

    /**
     日报状态，1.普通，2，高亮
    */
    @NotNull(message = "状态" + NO_PARAM)
    @ApiModelProperty(value = "日报状态，1.普通，2，高亮")
    private int status;

    /**
     名字
     */
    @ApiModelProperty(value = "名字",example = "name")
    private String name;


    /**
     摘要
     */
    @ApiModelProperty(value = "摘要",example = "digest")
    private String digest;


    /**
     主体内容
    */
    @ApiModelProperty(value = "主体内容",example = "content")
    private String content;

    /**
     备注
    */
    @ApiModelProperty(value = "备注",example = "desc")
    private String desc;
        
    /**
    最后操作时间 
    */
    @ApiModelProperty(hidden = true)
    private Date lastTime;
        
    /**
    创建时间 
    */
    @ApiModelProperty(hidden = true)
    private Date createTime;

}