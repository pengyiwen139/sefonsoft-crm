package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * 周日报表添加实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("日报表添加实体类")
@Accessors(chain = true)
@Data
public class DailyReportInsertOrUpdateInfo implements Serializable {

    private static final long serialVersionUID = 1982827395679856204L;

    /**
    主键 
    */
    @ApiModelProperty("主键")
    private Long id;
        
    /**
    员工工号 
    */
    @ApiModelProperty(hidden = true)
    private String employeeId;
        
    /**
     类型：1工作日，2加班
    */
    @NotNull(message = "报表类型" + NO_PARAM)
    @ApiModelProperty(value = "类型：1工作日，2加班", example = "1")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @NotNull(message = "报告日期" + NO_PARAM)
    @ApiModelProperty(value = "报告日期")
    private Date reportTime;

    /**
     日报状态，-1缺失，0未读，1.已读，2，标记
    */
    @ApiModelProperty(value = "日报状态，-1缺失，0未读，1.已读，2，标记")
    private Integer reportStatus;

    /**
     备注
     */
    @ApiModelProperty(value = "备注",example = "desc")
    private String reportDesc;

    /**
     日报信息列表
     */
    @ApiModelProperty("日报信息列表")
    private List<DailyReportInfoInsertOrUpdateInfo> dailyReportInfoList;

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