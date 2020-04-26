package com.sefonsoft.oa.entity.report;

import com.sefonsoft.oa.domain.report.DailyReportInfoInsertOrUpdateInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 周日报表添加实体类
 *
 * @author PengYiWen
 * @since 2019-12-09 11:21:59
 */
@ApiModel("日报表添加实体类")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReport implements Serializable {

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
    报表类型：1日报，2周报
    */
    @ApiModelProperty(value = "报表类型：1日报，2周报", example = "1")
    private Integer reportType;
        
    /**
    报告日期 
    */
    @ApiModelProperty(value = "报告日期")
    private Date reportTime;

    /**
     日报状态，0缺少，1.普通，2，高亮
    */
    @ApiModelProperty(value = "日报状态，0缺少，1.普通，2，高亮")
    private Integer reportStatus;

    /**
     备注
     */
    @ApiModelProperty(value = "备注",example = "desc")
    private String reportDesc;

    /**
     部门
     */
    @ApiModelProperty(value = "部门",example = "deptName")
    private String deptName;

    /**
     员工姓名
     */
    @ApiModelProperty(value = "员工姓名",example = "employeeName")
    private String employeeName;

    /**
     日报信息列表
     */
    @ApiModelProperty("日报信息列表")
    private List<DailyReportInfo> dailyReportInfoList;

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