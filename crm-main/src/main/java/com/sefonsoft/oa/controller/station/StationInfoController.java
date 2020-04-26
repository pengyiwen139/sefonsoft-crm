package com.sefonsoft.oa.controller.station;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.station.GradeInfoInsertDTO;
import com.sefonsoft.oa.domain.station.GradeInfoQueryDTO;
import com.sefonsoft.oa.domain.station.GradeInfoReturnDTO;
import com.sefonsoft.oa.domain.station.GradeInfoUpdateDTO;
import com.sefonsoft.oa.service.station.StationInfoService;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.ERROR_DELETEED;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_RESPONSE;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.DELETE_ERROR;

/**
 * 岗位相关接口
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:25
 */
@Api(tags = "职系岗位相关接口")
@RestController
public class StationInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private StationInfoService stationInfoService;
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @ApiOperation(value = "根据主键id查询单个岗位",response = StationInfoQueryDTO.class)
//    @RequestMapping(value = "/station/{id}", method = RequestMethod.GET)
//    public Response selectOne(@ApiParam(required = true, value = "岗位的主键id") @PathVariable Integer id) {
//        if (ObjTool.isNotNull(id)) {
//            if (id < 0) {
//                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
//            }
//            StationInfo stationInfo = stationInfoService.queryById(id);
//            return ObjTool.isNotNull(stationInfo) ? success(stationInfo) : failure(NO_RESPONSE_ERROR);
//        }
//        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
//    }
//
//    /**
//     * 修改单个岗位
//     * @param stationInfoUpdateDTO
//     * @param result
//     * @return
//     */
//    @ApiOperation("修改单个岗位")
//    @RequestMapping(value = "/station", method = RequestMethod.PATCH)
//    public Response update(@Valid @RequestBody StationInfoUpdateDTO stationInfoUpdateDTO, BindingResult result) {
//        if (result.hasErrors()) {
//            return failure(result);
//        }
//        StationInfo stationInfo = BeanUtils.copyProperties(stationInfoUpdateDTO, StationInfo.class);
//        return stationInfoService.update(stationInfo) > 0 ? success() : failure(UPDATE_ERROR);
//    }
//
//    /**
//     * 删除单个岗位
//     * @return
//     */
//    @ApiOperation("删除单个岗位")
//    @RequestMapping(value = "/station/{id}", method = RequestMethod.DELETE)
//    public Response delete(@ApiParam(required = true, value = "岗位的主键id") @PathVariable Integer id) {
//        return stationInfoService.deleteById(id) ? success() : failure(DELETE_ERROR);
//    }




    /**
     * 新增单个职系
     * @return
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_GRADE_ADD)
    @ApiOperation("新增单个职系")
    @RequestMapping(value = "/grade", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody GradeInfoInsertDTO gradeInfoInsertDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        int sameCount = stationInfoService.countGradeByGradeId(gradeInfoInsertDTO.getGradeId());
        if (sameCount > 0) {
            return failure(INSERT_ERROR.getCode(), INSERT_ERROR + "，职系编号重复");
        }
        return stationInfoService.insert(gradeInfoInsertDTO) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 查询职系分页列表
     * @return
     */
    @ApiOperation(value = "查询职系分页列表", response = GradeInfoReturnDTO.class)
    @RequestMapping(value = "/grade", method = RequestMethod.POST)
    public Response listGrade(@Valid @RequestBody GradeInfoQueryDTO gradeInfoQueryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return success(stationInfoService.list(gradeInfoQueryDTO));
    }


    /**
     * 查询单个职系
     * @return
     */
    @ApiOperation(value = "查询单个职系", response = GradeInfoReturnDTO.class)
    @RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
    public Response getGradeById(@ApiParam(required = true, value = "职系id") @PathVariable Integer id) {
        GradeInfoReturnDTO gradeById = stationInfoService.getGradeById(id);
        if (!ObjTool.isNotNull(gradeById)) {
            return failure(NO_RESPONSE);
        }
        if (ObjTool.isNotNull(gradeById.getDelFlag()) && gradeById.getDelFlag().equals(1)) {
            return failure(ERROR_DELETEED);
        }
        return success(stationInfoService.getGradeById(id));
    }


    /**
     * 修改单个职系
     * @return
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_GRADE_UPDATE)
    @ApiOperation(value = "修改单个职系")
    @RequestMapping(value = "/grade", method = RequestMethod.PATCH)
    public Response updateGrade(@Valid @RequestBody GradeInfoUpdateDTO gradeInfoUpdateDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return stationInfoService.updateGrade(gradeInfoUpdateDTO) ? success() : failure();
    }


    /**
     * 根据id批量删除职系
     * @return
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_GRADE_DELETE)
    @ApiOperation(value = "根据id批量删除职系")
    @RequestMapping(value = "/grade/{idStr}", method = RequestMethod.DELETE)
    public Response updateGrade(@ApiParam(required = true, value = "逗号分隔的id字符串 idStr") @PathVariable String idStr) {
        String[] split = idStr.split(",");
        if (!ObjTool.isNotNull(split)) {
            return failure(NO_PARAM_ERROR);
        }
        //判断是否有已经在使用的职系，关联查询sys_employee表
        List<String> gradeIdList = stationInfoService.batchGetGradeIdById(split);
        if (!ObjTool.isNotNull(gradeIdList)) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，职系编号错误");
        }
        if (stationInfoService.countInUsed(gradeIdList) > 0) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，职系已经在使用中");
        }
        return stationInfoService.batchGradeInfo(split) ? success() : failure();
    }

}