package com.sefonsoft.oa.domain.station;

import com.sefonsoft.oa.entity.station.StationInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/4 10:51
 * @description：职系新增所用的DTO
 * @modified By：
 */
@ApiModel(value = "职系返回实体类")
@Accessors(chain = true)
@Data
public class GradeInfoReturnDTO {

    private Long id;

    @NotBlank(message = "职系code" + NO_PARAM)
    @ApiModelProperty(value="职系code")
    private String gradeId;

    @NotBlank(message = "职系名称" + NO_PARAM)
    @ApiModelProperty(value="职系名称")
    private String gradeName;

    @ApiModelProperty(value="多个当前职系相关岗位名称，逗号隔开")
    private String stationNames;

    @ApiModelProperty(value="是否删除，0否，1是")
    private Integer delFlag;

    @ApiModelProperty(value = "当前相关此职系的所有岗位")
    private List<StationInfo> stationList;

}