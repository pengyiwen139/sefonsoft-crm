package com.sefonsoft.oa.controller.common;

import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.entity.customer.EnterpriseType;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.service.common.CommonService;
import com.sefonsoft.oa.service.sysdepartment.SysDepartmentService;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.emun.ResponseMessage.QUERY_ERROR;

/**
 * @author ：Aron
 * @version : 1.0
 * @description：公共接口管理
 * @date ：2019/11/16
 */
@RestController
@Api(tags = "公共调用接口")
public class CommonController extends BaseController {


    /**
     * 服务对象
     */
    @Resource
    private SysDepartmentService sysDepartmentService;

    /**
     * 服务对象
     */
    @Resource
    private SysEmployeeService sysEmployeeService;


    @Resource
    private CommonService commonService;


    /**
     * 加载销售树deptId=BM0006
     */
    @ApiOperation(value = "销售部门树",response = SysDepartment.class)
    @RequestMapping(value = "/sale/tree", method = RequestMethod.GET)
    public Response findSaleTree(SysDepartment sysDepartment) {
        List<Map<String, Object>> tree = sysDepartmentService.selectSaleDeptTree(sysDepartment);
        return new Response<>().success(tree);
    }

    /**
     * 售树节点员工
     * @param employeeQueryDTO
     * @return Response
     */
    @ApiOperation(value = "销售部门树对应节点员工",response = SysEmployeeQueryDTO.class)
    @RequestMapping(value = "/sale/employee", method = RequestMethod.GET)
    public Response employeeList(SysEmployeeQueryDTO employeeQueryDTO) {
        List<SysEmployeeQueryDTO> employeeDtos = sysEmployeeService.employeeList(employeeQueryDTO);
        return success(employeeDtos);
    }

    /**
     * 返回企业性质列表
     * @return Response
     */
    @ApiOperation(value = "返回码表中所有的企业性质列表")
    @RequestMapping(value = "/getEnterpriseTypeList", method = RequestMethod.GET)
    public Response getEnterpriseTypeList(EnterpriseType enterpriseType) {
        List<EnterpriseType> list = commonService.getEnterpriseTypeList(enterpriseType);
        return ObjTool.isNotNull(list) ? success(list) : failure(QUERY_ERROR);
    }
}