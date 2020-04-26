package com.sefonsoft.oa.controller.role;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.role.*;
import com.sefonsoft.oa.entity.application.SysApplication;
import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.service.role.RoleService;
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
 * @date ：Created in 2019/11/8 17:29
 * @description：角色相关接口
 * @modified By：
 */
@Api(tags = "角色权限应用相关接口")
@RestController
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询数据角色分页列表
     * @param sysRoleQueryDTO
     * @return
     */
    @ApiOperation(value = "查询数据角色分页列表", response = SysRole.class)
    @RequestMapping(value = "/role/listData", method = RequestMethod.POST)
    public Response listData(@RequestBody(required = false) SysRoleQueryDTO sysRoleQueryDTO) {
        return new Response<>().success(roleService.listData(sysRoleQueryDTO));
    }

    /**
     * 查询菜单角色分页列表
     * @param sysRoleQueryDTO
     * @return
     */
    @ApiOperation(value = "查询菜单角色分页列表", response = SysRole.class)
    @RequestMapping(value = "/role/listMenu", method = RequestMethod.POST)
    public Response listMenu(@RequestBody(required = false) SysRoleQueryDTO sysRoleQueryDTO) {
        return new Response<>().success(roleService.listMenu(sysRoleQueryDTO));
    }

    /**
     * 根据角色编号获取数据角色信息以及其他关联部门信息
     * @return
     */
    @ApiOperation(value = "根据角色编号获取数据角色信息以及其他关联部门信息", response = SysDataRoleQueryDTO.class)
    @RequestMapping(value = "/role/getDataRoleByRoleId/{roleId}", method = RequestMethod.GET)
    public Response getDataRoleByRoleId(@ApiParam(value = "角色编号", required = true) @PathVariable(required = true) String roleId) {
        SysDataRoleQueryDTO dataRoleQueryDTO = roleService.getDataRoleByRoleId(roleId);
        return ObjTool.isNotNull(dataRoleQueryDTO) ? success(dataRoleQueryDTO) : failure(QUERY_ERROR);
    }

    /**
     * 根据角色编号获取菜单角色信息以及其他关联菜单信息
     * @return
     */
    @ApiOperation(value = "根据角色编号获取菜单角色信息以及其他关联菜单信息", response = SysMenuRoleQueryDTO.class)
    @RequestMapping(value = "/role/getMenuRoleByRoleId/{roleId}", method = RequestMethod.GET)
    public Response getMenuRoleByRoleId(@ApiParam(value = "角色编号", required = true) @PathVariable(required = true) String roleId) {
        SysMenuRoleQueryDTO menuRoleQueryDTO = roleService.getMenuRoleByRoleId(roleId);
        return ObjTool.isNotNull(menuRoleQueryDTO) ? success(menuRoleQueryDTO) : failure(QUERY_ERROR);
    }

    /**
     * 查询所有系统应用
     * @param sysApplication
     * @return
     */
    @ApiOperation(value = "查询所有系统应用", response = SysApplication.class)
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public Response listApplication(@ApiParam(required = false) @RequestBody SysApplication sysApplication) {
        return success(roleService.listApplication(sysApplication));
    }

    /**
     * 添加数据权限角色
     * @param sysRoleInsertDTO
     * @param result
     * @return
     */
    @ApiOperation(value = "添加数据权限角色")
    @RequestMapping(value = "/role/addDataRole", method = RequestMethod.PUT)
    public Response addDataRole(@Valid @RequestBody SysDataRoleInsertDTO sysRoleInsertDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        String roleId = roleService.generateRoleId();
        sysRoleInsertDTO.setRoleId(roleId);
        return roleService.addDataRole(sysRoleInsertDTO) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 添加操作权限角色
     * @param sysRoleInsertDTO
     * @param result
     * @return
     */
    @ApiOperation(value = "添加操作权限角色")
    @RequestMapping(value = "/role/addMenuRole", method = RequestMethod.PUT)
    public Response addMenuRole(@Valid @RequestBody SysMenuRoleInsertDTO sysRoleInsertDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        String roleId = roleService.generateRoleId();
        if (!ObjTool.isNotNull(roleId)) {
            return failure(INSERT_ERROR);
        }
        sysRoleInsertDTO.setRoleId(roleId);
        return roleService.addMenuRole(sysRoleInsertDTO) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 修改数据角色
     * @param dataRoleUpdateDTO
     * @param result
     * @return
     */
    @ApiOperation(value = "修改数据角色")
    @RequestMapping(value = "/role/updateDataRole", method = RequestMethod.PATCH)
    public Response updateDataRole(@Valid @RequestBody SysDataRoleUpdateDTO dataRoleUpdateDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return roleService.updateDataRole(dataRoleUpdateDTO) ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 修改操作角色
     * @param menuRoleUpdateDTO
     * @param result
     * @return
     */
    @ApiOperation(value = "修改操作角色")
    @RequestMapping(value = "/role/updateMenuRole", method = RequestMethod.PATCH)
    public Response updateMenuRole(@Valid @RequestBody SysMenuRoleUpdateDTO menuRoleUpdateDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        return roleService.updateMenuRole(menuRoleUpdateDTO) ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 根据id批量删除角色
     * @return
     */
    @ApiOperation(value = "根据id批量删除角色")
    @RequestMapping(value = "/role/{idStr}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "逗号分隔的id字符串 idStr") @PathVariable String idStr) {
        String[] split = idStr.split(",");
        //id列表的非空判断
        if (!ObjTool.isNotNull(split)) {
            return failure(NO_PARAM_ERROR);
        }
        List<String> roleIdList = roleService.batchGetRoleIdById(split);
        if (!ObjTool.isNotNull(roleIdList)) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，查无此角色编号");
        }
        //判断角色是否正在被某些用户使用
        if (roleService.countInUsed(roleIdList) > 0) {
            return failure(DELETE_ERROR.getCode(), "角色已经在使用中，");
        }
        return roleService.delete(roleIdList) ? success() : failure(DELETE_ERROR);
    }

}