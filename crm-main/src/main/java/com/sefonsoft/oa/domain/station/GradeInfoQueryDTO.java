package com.sefonsoft.oa.domain.station;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

/**
 * 职系实体类
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:22
 */
@ApiModel(value = "用于查询操作的职系gradeInfo参数")
@Accessors(chain = true)
@Getter
@Setter
@Data
public class GradeInfoQueryDTO implements Serializable {

    //岗位编号,主键
    @ApiModelProperty(value="职系编号")
    private String gradeId;

    //岗位名称
    @ApiModelProperty(value="职系名称",example="销售")
    private String gradeName;

    /**
     * 页数
     */
    @ApiModelProperty(value="页数",example="1")
    @NotNull(message = "页数" + NO_PARAM)
    @Min(value = 0, message = "page" + MIN_PARAM + 0)
    private Integer page;

    /**
     * 每页个数
     */
    @ApiModelProperty(value="每页个数",example="10")
    @NotNull(message = "每页个数" + NO_PARAM)
    @Min(value = 0, message = "rows" + MIN_PARAM + 0)
    private Integer rows;

    public Integer getPage() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

}