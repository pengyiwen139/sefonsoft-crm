package com.sefonsoft.oa.domain.station;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/4 10:51
 * @description：职系新增所用的DTO
 * @modified By：
 */
@ApiModel(value = "新增职系实体类")
@Accessors(chain = true)
@Data
public class GradeInfoInsertDTO {

    private Long id;

    @NotBlank(message = "职系code" + NO_PARAM)
    @ApiModelProperty(value="职系code")
    private String gradeId;

    @NotBlank(message = "职系名称" + NO_PARAM)
    @ApiModelProperty(value="职系名称")
    private String gradeName;

    @NotNull(message = "岗位json" + NO_PARAM)
    @ApiModelProperty(value="岗位json，字段含stationName，sort，gradeId")
    private JSONArray stationList;

    //操作时间
    private Date modifiedTime;
    //创建时间
    private Date createTime;

}
