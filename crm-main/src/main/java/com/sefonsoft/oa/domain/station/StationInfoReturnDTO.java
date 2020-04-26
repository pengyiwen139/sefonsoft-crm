package com.sefonsoft.oa.domain.station;

import com.sefonsoft.oa.entity.station.StationInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (StationInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:22
 */
@ApiModel(value = "用于查询操作的stationInfo参数")
@Accessors(chain = true)
@Data
public class StationInfoReturnDTO extends StationInfo implements Serializable {

    //岗位编号,主键
    @ApiModelProperty(value="主键")
    private Long id;
    //岗位编号,主键
    @ApiModelProperty(value="岗位编号")
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
    //职系名称
    @ApiModelProperty(value="职系名称")
    private String gradeName;
    //操作时间
    @ApiModelProperty(value="操作时间")
    private String modifiedTimeStr;
    //创建时间
    @ApiModelProperty(value="创建时间")
    private String createTimeStr;
    //部门名称
    @ApiModelProperty(value="部门名称")
    private String deptName;

}