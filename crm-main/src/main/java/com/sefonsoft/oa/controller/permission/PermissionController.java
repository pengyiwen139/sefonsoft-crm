package com.sefonsoft.oa.controller.permission;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.permission.SysPermissionSimpleDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionTreeDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateDTO;
import com.sefonsoft.oa.entity.permission.SysPermission;
import com.sefonsoft.oa.service.permission.SysPermissionService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/11 11:23
 * @description：权限菜单相关接口
 * @modified By：
 */
@Api(tags = "权限菜单相关接口")
@RestController
public class PermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody SysPermissionSimpleDTO sysPermissionSimpleDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        if (sysPermissionService.countByMenuId(sysPermissionSimpleDTO.getMenuId()) > 0) {
            return failure(INSERT_ERROR.getCode(), INSERT_ERROR.getMessage() + "，菜单编号重复");
        }
        return sysPermissionService.insert(sysPermissionSimpleDTO) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 权限菜单树查询
     * @param sysPermission
     * @return
     */
    @ApiOperation(value = "权限菜单树查询", response = SysPermissionTreeDTO.class)
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public Response listTree(@ApiParam(required = false) @RequestBody SysPermission sysPermission) {
        List<SysPermissionTreeDTO> sysPermissions = sysPermissionService.listTree(sysPermission);
        return  ObjTool.isNotNull(sysPermissions) ? success(sysPermissions) : failure(QUERY_ERROR);
    }

    /**
     * 修改单个权限菜单
     * @param sysPermission
     * @return
     */
    @ApiOperation(value = "修改单个权限菜单")
    @RequestMapping(value = "/permission", method = RequestMethod.PATCH)
    public Response edit(@ApiParam(required = true) @RequestBody SysPermissionUpdateDTO sysPermission) {
        return  sysPermissionService.edit(sysPermission) ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 根据id批量删除菜单
     * @param idStr
     * @return
     */
    @ApiOperation(value = "根据id批量删除菜单")
    @RequestMapping(value = "/permission/{idStr}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable String idStr) {
        String[] split = idStr.split(",");
        if (!ObjTool.isNotNull(split)) {
            return failure(NO_PARAM_ERROR);
        }
        List<String> menuIdList = sysPermissionService.batchGetMenuIdById(split);
        if (!ObjTool.isNotNull(menuIdList)) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，菜单编号错误");
        }
        if (sysPermissionService.batchCountChild(menuIdList) > 0) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，请删除子权限后再删除当前节点");
        }
        if (sysPermissionService.countInUsed(menuIdList) > 0) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，当前选中的权限有角色关联");
        }
        return sysPermissionService.deleteById(split) ? success() : failure(DELETE_ERROR);
    }

}