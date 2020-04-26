package com.sefonsoft.oa.controller.report;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.report.EmployeeReportListDTO;
import com.sefonsoft.oa.domain.report.EmployeeReportQueryDTO;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.report.EmployeeReportService;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;

/**
 * 周日报表(EmployeeReportInfo)表控制层
 *
 * @author Aron
 * @since 2019-12-09 11:22:06
 */
//@Api(tags = "周日报列表相关接口")
//@RestController
public class EmployeeReportController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private EmployeeReportService employeeReportService;

    /**
     * 服务对象
     */
    @Resource
    private SysEmployeeService sysEmployeeService;



    /**
     * 报告搜索员工
     * @param employeeQueryDTO
     * @return Response
     */
    @ApiOperation(value = "报告搜索员工",response = SysEmployeeQueryDTO.class)
    @RequestMapping(value = "/employeeReport/employee", method = RequestMethod.POST)
    public Response employeeList(@RequestBody SysEmployeeQueryDTO employeeQueryDTO) {

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
     * 查询员工月周日报列表
     * @param employeeReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "查询员工月周日报列表",response = EmployeeReportQueryDTO.class)
    @RequestMapping(value = "/employeeReport/list", method = RequestMethod.GET)
    public Response employeeReportCondition(EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception {


        String employeeId = employeeReportQueryDTO.getEmployeeId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工编号不能为空");
        }
        String year = employeeReportQueryDTO.getYear();
        if (!ObjTool.isNotNull(year)) {
            return failure("年不能为空");
        }
        String month = employeeReportQueryDTO.getMonth();
        if (!ObjTool.isNotNull(month)) {
            return failure("月不能为空");
        }

        Map<Integer,EmployeeReportListDTO> employeeReportDetailMap = employeeReportService.getCondition(employeeReportQueryDTO);

        return success(employeeReportDetailMap);


    }

    /**
     * 查询员工月周日报列表
     * @param employeeReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "查询当日未填写日报列表，传reportTime和数据权限列表",response = EmployeeReportQueryDTO.class)
    @RequestMapping(value = "/noFillReports", method = RequestMethod.POST)
    public Response noFillReports(@RequestBody EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception {

        if (!ObjTool.isNotNull(employeeReportQueryDTO.getReportTime())) {
            return failure("报告日期不能为空");
        }

        List<EmployeeReportQueryDTO> employeeReportQueryList = employeeReportService.noFillReports(employeeReportQueryDTO);
        int noFillReportTotal = employeeReportService.noFillReportTotals(employeeReportQueryDTO);
        PageResponse pageResponse = new PageResponse<>(noFillReportTotal, employeeReportQueryList);

        return success(pageResponse);


    }

    /**
     * 查询员工月周日报列表
     * @param employeeReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "查询当日未填写日报列表，传reportTime",response = EmployeeReportQueryDTO.class)
    @RequestMapping(value = "/noFillReport", method = RequestMethod.GET)
    public Response noFillReport(@CurrentUser LoginUserDTO loginUser,EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception {

        if (!ObjTool.isNotNull(loginUser)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        String gradeId = loginUser.getGradeId();
        if (!ObjTool.isNotNull(gradeId)) {
            return failure("职系不能为空");
        }
        String employeeId = loginUser.getUserId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工编号不能为空");
        }
        String deptId = loginUser.getDeptId();
        if (!ObjTool.isNotNull(deptId)) {
            return failure("员工部门不能为空");
        }
        employeeReportQueryDTO.setGradeId(gradeId);
        employeeReportQueryDTO.setEmployeeId(employeeId);
        employeeReportQueryDTO.setDeptId(deptId);
        if (!ObjTool.isNotNull(employeeReportQueryDTO.getReportTime())) {
            return failure("报告日期不能为空");
        }


        List<EmployeeReportQueryDTO> employeeReportQueryList = employeeReportService.noFillReport(employeeReportQueryDTO);
        int noFillReportTotal = employeeReportService.noFillReportTotal(employeeReportQueryDTO);
        PageResponse pageResponse = new PageResponse<>(noFillReportTotal, employeeReportQueryList);

        return success(pageResponse);


    }




}