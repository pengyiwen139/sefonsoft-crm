package com.sefonsoft.oa.domain.station;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (StationInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:22
 */
@ApiModel(value = "用于插入操作的stationInfo参数")
@Accessors(chain = true)
@Data
public class StationInfoInsertDTO implements Serializable {

    //岗位编号,主键
    @ApiModelProperty(value="岗位编号")
    private String stationId;
    //岗位名称
    @ApiModelProperty(value="岗位名称，必传",example="工程师")
    @NotBlank(message = "stationName" + NO_PARAM)
    private String stationName;
    //排序号
    @ApiModelProperty(value="排序号")
    @Min(value = 0, message = "排序号" + MIN_PARAM + 0)
    private Integer sort;
    //部门编号
    @ApiModelProperty(value="职系编号")
    @Min(value = 0, message = "gradeId" + MIN_PARAM + 0)
    private String gradeId;


}