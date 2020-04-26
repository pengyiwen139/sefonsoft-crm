package com.sefonsoft.oa.controller.sysdepartment;


import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.sysdepartment.SysDepartmentQueryDTO;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.service.sysdepartment.SysDepartmentService;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.BeanUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.UtilMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * (SysDepartment)表控制层
 *
 * @author Aron
 * @since 2019-11-01 16:11:07
 */
@RestController
@Api(tags = "部门组织相关接口")
public class SysDepartmentController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SysDepartmentService sysDepartmentService;

    /**
     * 加载部门树
     */
    @ApiOperation("部门树")
    @RequestMapping(value = "/department/tree", method = RequestMethod.GET)
    public Response findTree(SysDepartment sysDepartment) {
        List<Map<String, Object>> tree = sysDepartmentService.selectDeptTree(sysDepartment);
        return new Response<>().success(tree);
    }


    /**
     * 条件查询部门列表
     * @param departmentQueryDTO
     * @return Response
     */
    @ApiOperation(value = "条件查询部门列表",response = SysDepartmentQueryDTO.class)
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public Response findDepartmentByCondition(SysDepartmentQueryDTO departmentQueryDTO) {
        List<SysDepartmentQueryDTO> departmentDtos = sysDepartmentService.getCondition(departmentQueryDTO);
        int totalDepartment = sysDepartmentService.findCondition(departmentQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalDepartment, departmentDtos);
        return new Response<>().success(pageResponse);
    }

    /**
     * 新增部门
     * @param sysDepartment
     * @return Response
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_DEPT_ADD)
    @ApiOperation(value = "新增部门",response = SysDepartment.class)
    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody SysDepartment sysDepartment, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        //查询部门表最大部门编码
        String maxCode = sysDepartmentService.getMaxDeptId();
        //如果查询数据库没有部门记录，设置maxCode默认值
        if(null==maxCode){
            maxCode="BM0001";
        }
        //最大部门编码后四位流水+1
        String dptCode = UtilMethod.getDptCode(maxCode);
        sysDepartment.setDeptId(dptCode);
        String deptId = sysDepartment.getDeptId();

        String parentId = sysDepartment.getParentId();
        String deptName = sysDepartment.getDeptName();
        if(null==deptId){
            return failure("部门编号不能为空");
        }
        if(null==deptName){
            return failure("部门名字不能为空");
        }
        int deptIdCount = sysDepartmentService.checkDeptIdUnique(deptId);
        int deptNameCount = sysDepartmentService.checkDeptNameUnique(parentId,deptName);
        //验证部门编号重复
        if (deptIdCount>0)
        {
            return failure(UNIQUE_DEPTID_ERROR);
        }
        //验证同一父节点部门名称重复
        if (deptNameCount>0)
        {
            return failure(UNIQUE_DEPTNAME_ERROR);
        }
        return sysDepartmentService.insert(sysDepartment) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据主键id查询部门",response = SysDepartmentQueryDTO.class)
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "部门主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 0) {
                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            SysDepartment departmentInfo = sysDepartmentService.queryById(id);
            return ObjTool.isNotNull(departmentInfo) ? success(departmentInfo) : failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }


    /**
     * 修改单个部门
     * @param sysDepartment
     * @param result
     * @return Response
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_DEPT_UPDATE)
    @ApiOperation(value = "修改部门",response = SysDepartment.class)
    @RequestMapping(value = "/department", method = RequestMethod.PATCH)
    public Response update(@Valid @RequestBody SysDepartment sysDepartment, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        if (!ObjTool.isNotNull(sysDepartment.getId())) {
            return failure(PKEY_ERROR);
        }
        SysDepartment departmentInfo = BeanUtils.copyProperties(sysDepartment, SysDepartment.class);
        return sysDepartmentService.update(departmentInfo) > 0 ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 根据id删除部门
     * @return Response
     */
    @MethodPermission(menuIdArray = M_ORGANIZATION_DEPT_DELETE)
    @ApiOperation("删除部门")
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "部门主键id") @PathVariable Long id) {

        SysDepartment departmentInfo = sysDepartmentService.queryById(id);
         if(ObjTool.isNotNull(departmentInfo)){
             String parentId = departmentInfo.getDeptId();
             //存在下级部门
             if (sysDepartmentService.selectDeptCount(parentId) > 0)
             {
                 return failure(DELETE_DEPT_ERROR);
             }
         }
         //部门下存在员工
        if (sysDepartmentService.checkDeptExistUser(departmentInfo.getDeptId()))
        {
            return failure(DELETE_EMPEXIT_ERROR);
        }
        return sysDepartmentService.deleteById(id) ? success() : failure(DELETE_ERROR);
    }


    /**
     * 校验同一父节点下部门名称是否重复和部门编号不能重复
     */
    @ApiOperation(value = "校验部门编号和一父节点下部门名称是否重复",response = SysDepartment.class)
    @RequestMapping(value = "/checkDeptNameAndDeptIdUnique", method = RequestMethod.POST)
    public Response checkDeptNameUnique(@Valid @RequestBody SysDepartment dept)
    {
        String parentId = dept.getParentId();
        String deptName = dept.getDeptName();
        String deptId = dept.getDeptId();
        int deptIdCount = sysDepartmentService.checkDeptIdUnique(deptId);
        int deptNameCount = sysDepartmentService.checkDeptNameUnique(parentId,deptName);
        //验证部门编码重复
        if (deptIdCount>0)
        {
            return failure(UNIQUE_DEPTID_ERROR);
        }
        //验证同一父节点部门名称重复
        if (deptNameCount>0)
        {
            return failure(UNIQUE_DEPTNAME_ERROR);
        }
        return success() ;
    }



}