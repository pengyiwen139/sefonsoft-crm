package com.sefonsoft.oa.controller.sysemployee;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.BeanUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * (SysEmployee)表控制层
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
@Api(tags = "员工相关接口")
@RestController
public class SysEmployeeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SysEmployeeService sysEmployeeService;


    /**
     * 条件查询员工列表
     * @param employeeQueryDTO
     * @return Response
     */
    @ApiOperation(value = "条件查询员工列表",response = SysEmployeeQueryDTO.class)
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public Response findEmployeeByCondition(SysEmployeeQueryDTO employeeQueryDTO) {
        List<SysEmployeeQueryDTO> employeeDtos = sysEmployeeService.getCondition(employeeQueryDTO);
        int totalEmployeeServiceDto = sysEmployeeService.findCondition(employeeQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalEmployeeServiceDto, employeeDtos);
        return success(pageResponse);
    }
    
    /**
     * 报告搜索员工
     * @param employeeQueryDTO
     * @return Response
     */
    @ApiOperation(value = "搜索员工",response = SysEmployeeQueryDTO.class)
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public Response<List<SysEmployeeQueryDTO>> employeeList(@RequestBody SysEmployeeQueryDTO employeeQueryDTO) {
        //数据权限部门列表
        List<String> dataDeptIdList = employeeQueryDTO.getDataDeptIdList();

        if (ObjTool.isNotNull(dataDeptIdList)) {
            List<SysEmployeeQueryDTO> employeeDtos = sysEmployeeService.getConditionList(dataDeptIdList,employeeQueryDTO);

            return new Response().success(employeeDtos);
        }
        //根据前端人员要求：原提示：无数据权限查看他人报告,改为“”
        return new Response().failure("");
    }

    /**
     * 新增员工
     * @param sysEmployee
     * @return Response
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_STAFF_ADD)
    @ApiOperation(value = "新增员工")
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody SysEmployee sysEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }

        String employeeId = sysEmployee.getEmployeeId();
        String employeeName= sysEmployee.getEmployeeName();
        if(null==employeeId){
            return failure("员工编号不能为空");
        }
        if(null==employeeName){
            return failure("员工名字不能为空");
        }
        int employeeIdCount = sysEmployeeService.checkUnique(employeeId);
        if (employeeIdCount>0)
        {
            return failure(UNIQUE_EMPLOYEEID_ERROR);
        }
        return sysEmployeeService.insert(sysEmployee) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据主键id查询员工",response = SysEmployee.class)
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 0) {
                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            SysEmployee sysEmployee = sysEmployeeService.queryById(id);
            return ObjTool.isNotNull(sysEmployee) ? success(sysEmployee) : failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }

    /**
     * 修改员工信息
     * @param sysEmployee
     * @param result
     * @return Response
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_STAFF_UPDATE)
    @ApiOperation(value = "修改员工")
    @RequestMapping(value = "/employee", method = RequestMethod.PATCH)
    public Response update(@Valid @RequestBody SysEmployee sysEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        if (!ObjTool.isNotNull(sysEmployee.getId())) {
            return failure(PKEY_ERROR);
        }
        SysEmployee sysEmployeeInfo = BeanUtils.copyProperties(sysEmployee, SysEmployee.class);
        return sysEmployeeService.update(sysEmployeeInfo) ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 根据employeeId删除员工
     * @return
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_STAFF_DELETE)
    @ApiOperation("删除单个员工信息(传employeeId)")
    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "员工编号id") @PathVariable String employeeId) {

        if (!ObjTool.isNotNull(employeeId)) {
            return failure("删除用户工号不能为空");
        }

        return sysEmployeeService.deleteByEmployeeId(employeeId) ? success() : failure(DELETE_ERROR);
    }

    /**
     * 根据employId批量删除员工
     * @return
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_STAFF_DELETE)
    @ApiOperation("批量删除多个员工信息(employeeId以逗号分割)")
    @RequestMapping(value = "/employee/delAll", method = RequestMethod.DELETE)
    @ApiImplicitParam(name="ids", value="employeeId以逗号分割", required=true, paramType="query" ,allowMultiple=true, dataType = "String")
    public Response deleteIds(String ids) {

        if (!ObjTool.isNotNull(ids)) {
            return failure("删除用户工号不能为空");
        }
        List<String> idArray = Arrays.asList(ids.split(","));

        return sysEmployeeService.deleteByIds(idArray)? success() : failure(DELETE_ERROR);
    }




    /**
     * 校验员工编号不能重复
     */
    @ApiOperation(value = "校验员工编号是否重复",response = SysEmployee.class)
    @RequestMapping(value = "/checkUnique", method = RequestMethod.POST)
    public Response checkUnique(@RequestBody SysEmployee employee)
    {
        String employeeId = employee.getEmployeeId();
        int employeeIdCount = sysEmployeeService.checkUnique(employeeId);
        if (employeeIdCount>0)
        {
            return failure(UNIQUE_EMPLOYEEID_ERROR);
        }
        return success() ;
    }



}