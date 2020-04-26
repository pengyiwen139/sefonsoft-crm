package com.sefonsoft.oa.domain.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/12/11 10:39
 * @description：当天日报填写人个数统计
 * @modified By：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportDaySignCountDTO {

    @ApiModelProperty(value = "当前登录员工所管辖的总人数")
    private Integer sumCount;

    @ApiModelProperty(value = "当前登录员工所管辖的某一天填写日报的人数")
    private Integer signedCount;

    @ApiModelProperty(value = "当前登录员工所管辖的某一天未填写日报的人数")
    private Integer unSignedCount;

}

