package com.sefonsoft.oa.controller.workorder;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigInsertDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigQueryDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigUpdateDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptOperateParams;
import com.sefonsoft.oa.entity.workorder.WorkorderDeptConfig;
import com.sefonsoft.oa.service.workorder.WorkOrderDeptRefService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName: WorkOrderDeptRefService
 * @author: Peng YiWen
 * @date: 2020/4/1  16:53
 */
@Api(tags = "派工单部门关联配置接口")
@RestController
public class WorkOrderDeptRefController extends Response<Object> {

    @Resource
    private WorkOrderDeptRefService workOrderDeptRefService;

    /**
     * 新增派工单部门关联配置
     *
     * @param workorderDeptCfgDO
     * @return Response
     */
    @ApiOperation(value = "新增派工单部门关联配置", response = Response.class)
    @RequestMapping(value = "/workorder/dept", method = RequestMethod.PUT)
    public Response insert(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody WorkorderDeptConfigInsertDTO workorderDeptCfgDO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return workOrderDeptRefService.insert(workorderDeptCfgDO, loginUserDTO) ? new Response().success() : new Response().failure();
    }

    /**
     * 修改派工单部门关联配置
     *
     * @param workorderDeptCfgDO
     * @return Response
     */
    @ApiOperation(value = "修改派工单部门关联配置", response = Response.class)
    @RequestMapping(value = "/workorder/dept", method = RequestMethod.PATCH)
    public Response update(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody WorkorderDeptConfigUpdateDTO workorderDeptCfgDO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return workOrderDeptRefService.update(workorderDeptCfgDO, loginUserDTO) ? new Response().success() : new Response().failure();
    }

    /**
     * 派工单部门关联配置列表查询
     * @param workorderDeptCfgDO
     * @return Response
     */
    @ApiOperation(value = "派工单部门关联配置列表查询", response = WorkorderDeptConfigQueryDTO.class)
    @RequestMapping(value = "/workorder/dept", method = RequestMethod.POST)
    public Response list(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody WorkorderDeptConfigQueryDTO workorderDeptCfgDO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return new Response().success(workOrderDeptRefService.list(workorderDeptCfgDO, loginUserDTO));
    }

    /**
     * 获取单个派工单部门关联
     * @return Response
     */
    @ApiOperation(value = "获取单个派工单部门关联", response = WorkorderDeptConfig.class)
    @RequestMapping(value = "/workorder/dept/{id}", method = RequestMethod.GET)
    public Response list(@PathVariable(value = "id", required = true) Long id) {
        WorkorderDeptConfig workorderDeptCfgDO = workOrderDeptRefService.getById(id);
        return ObjTool.isNotNull(workorderDeptCfgDO) ? new Response().success(workorderDeptCfgDO) : new Response().failure();
    }

    /**
     * 获取添加和编辑界面需要的部门参数
     * @return Response
     */
    @ApiOperation(value = "获取添加和编辑界面需要的部门参数", response = WorkorderDeptOperateParams.class)
    @RequestMapping(value = "/workorder/dept/operateParams", method = RequestMethod.GET)
    public Response getOperateParams() {
        WorkorderDeptOperateParams workorderDeptOperateParams = workOrderDeptRefService.getOperateParams();
        return ObjTool.isNotNull(workorderDeptOperateParams) ? new Response().success(workorderDeptOperateParams) : new Response().failure();
    }

    /**
     * 批量删除派工单部门关联
     * @return Response
     */
    @ApiOperation(value = "批量删除派工单部门关联", response = Response.class)
    @RequestMapping(value = "/workorder/dept/{idArray}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "id数组") @PathVariable(value = "idArray") Long[] idArray) {
        return workOrderDeptRefService.deleteById(idArray) ? new Response().success() : new Response().failure();
    }

}