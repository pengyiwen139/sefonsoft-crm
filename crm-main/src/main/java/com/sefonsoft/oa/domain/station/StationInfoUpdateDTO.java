package com.sefonsoft.oa.domain.station;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * (StationInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:22
 */
@ApiModel(value = "用于更新操作的stationInfo参数")
@Accessors(chain = true)
@Data
public class StationInfoUpdateDTO implements Serializable {

    //岗位编号,主键
    @NotNull(message = "岗位id" + NO_PARAM)
    @ApiModelProperty(value="主键")
    private Long id;

    //岗位编号,主键
    @ApiModelProperty(value="岗位编号",example="1")
    @NotNull(message = "岗位编号" + NO_PARAM)
    @Min(value = 0, message = "stationId" + MIN_PARAM + 0)
    private String stationId;

    //岗位名称
    @ApiModelProperty(value="岗位名称",example="工程师")
    private String stationName;

    //排序号
    @ApiModelProperty(value="排序号")
    private Integer sort;

    //职系编号
    @ApiModelProperty(value="职系编号")
    private String gradeId;

    @ApiModelProperty(hidden = true)
    private Date modifiedTime;

    @ApiModelProperty(hidden = true)
    private Date createTime;

}