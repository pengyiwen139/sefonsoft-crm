package com.sefonsoft.oa.controller.report;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.report.*;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.report.EmployeeReportInfoService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ReportConstant.DAY_TYPE_REPORT;
import static com.sefonsoft.oa.system.constant.ReportConstant.WEEK_TYPE_REPORT;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * 周日报表(EmployeeReportInfo)表控制层
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:06
 */
//@Api(tags = "周日报相关接口")
//@RestController
public class EmployeeReportInfoController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private EmployeeReportInfoService employeeReportInfoService;

    @Value("${reportDayLastHours}")
    private String reportDayLastHoursStr;

    /**
     * 周日报批量添加
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "日报批量添加")
    @RequestMapping(value = "/report", method = RequestMethod.PUT)
    public Response insert(@RequestBody List<EmployeeReporInsertOrUpdateInfo> list, @CurrentUser LoginUserDTO loginUser) {
        Response errorResponse = employeeReportInfoService.batchInsertCheck(list);
        if (ObjTool.isNotNull(errorResponse)) {
            return errorResponse;
        }
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return failure(LOGIN_STATUS_ERROR);
        }
//        Date reportTime = list.get(0).getReportTime();
//        Integer reportType = list.get(0).getReportType();
//        if (ObjTool.isNotNull(reportDayLastHours) && reportType.equals(DAY_TYPE_REPORT)) {
//            //限制日报在指定的某一天的某个小时后不能填写
//            Response response = employeeReportInfoService.ifItsTime2AddReport(reportDayLastHours, reportTime);
//            if (ObjTool.isNotNull(response)) {
//                return response;
//            }
//        }
        return employeeReportInfoService.batchInsert(list, loginUser) ? success() : failure(INSERT_ERROR);
    }


    /**
     * 获取可填写日报的最早日期和最晚时刻
     * @return Response
     */
    @ApiOperation(value = "获取可填写日报的最早日期和最晚时刻", response = EmployeeRecentDayReportListDTO.class)
    @RequestMapping(value = "/report/getMostEarlyAddDateTime", method = RequestMethod.GET)
    public Response getMostEarlyAddDateTime(@CurrentUser LoginUserDTO loginUser) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String,String> map = employeeReportInfoService.getMostEarlyAddDateTime(Integer.parseInt(reportDayLastHoursStr), loginUser, sdf);
        if (!ObjTool.isNotNull(map)) {
            return new Response<>().failure("日报可填写日期出错");
        } else {
            if (map.size() != 2) {
                return failure("日报可填写日期出错,请联系管理员");
            } else {
                return new Response<>().success(map);
            }
        }
    }

    /**
     * 周日报批量修改，若实体类中有id，则当前实体类是需要修改的数据；若实体类没id，则是需要新增的数据
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "周日报批量修改，若实体类中有id，则当前实体类是需要修改的数据；若实体类没id，则是需要新增的数据")
    @RequestMapping(value = "/report", method = RequestMethod.PATCH)
    public Response update(@RequestBody List<EmployeeReporInsertOrUpdateInfo> list, @CurrentUser LoginUserDTO loginUser) {
        if (!ObjTool.isNotNull(loginUser)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        Response errorResponse = employeeReportInfoService.batchInsertCheck(list);
        if (ObjTool.isNotNull(errorResponse)) {
            return errorResponse;
        }
        return employeeReportInfoService.update(list, loginUser) ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 获取最近添加的日报信息
     * 时间规则：首先查询上一次填写日报的的日期，如果有且小于七天，则获取上一次填写周报的日期到当前日期的所有相应需要的日报数据
     *                                            有查到周报且大于七天或者没有填写过周报，则获取最近七天填写的相应需要的日报数据
     * 内容规则：根据项目groupby出每一个项目最后一次填写的日报数据
     * @param
     * @return Response
     */
    @ApiOperation(value = "获取最近添加的日报信息", response = EmployeeRecentDayReportListDTO.class)
    @RequestMapping(value = "/report/recentDayList/{reportTime}", method = RequestMethod.GET)
    public Response recentDayList(@CurrentUser LoginUserDTO loginUser, @PathVariable(required = true) String reportTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return success(employeeReportInfoService.getRecentDayList(loginUser, sdf.parse(reportTime)));
    }

    /**
     * 获取指定某天的管辖的部门下的填写日报统计
     * @param
     * @return Response
     */
    @ApiOperation(value = "获取指定某天的管辖的部门下的填写日报统计", response = ReportDaySignCountDTO.class)
    @RequestMapping(value = "/report/signCount/{reportTime}", method = RequestMethod.GET)
    public Response getSignCount(@CurrentUser LoginUserDTO loginUser
                                ,@ApiParam(required = true, value = "查询日期，日期类型") @PathVariable(required = true) String reportTime) {
        return success(employeeReportInfoService.getSignCount(loginUser, reportTime));
    }

    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "获取单天的周日报列表详情", response = EmployeeReportListDTO.class)
    @RequestMapping(value = "/report/getOne/{reportTime}", method = RequestMethod.GET)
    public Response detailByOneDay(@ApiParam(required = true, value = "日期类型的报告时间，日期类型") @PathVariable(required = true) String reportTime,
                                   @CurrentUser LoginUserDTO loginUser) {
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return failure("用户未登录，请重新登录");
        }
        List<EmployeeReportDetailDTO> list = employeeReportInfoService.getOne(reportTime, loginUser.getUserId());
        if (ObjTool.isNotNull(list)) {
            EmployeeReportListDTO reportList = new EmployeeReportListDTO();
            List<EmployeeReportDetailDTO> dayList = new ArrayList<>();
            List<EmployeeReportDetailDTO> weekList = new ArrayList<>();
            for (EmployeeReportDetailDTO detailDTO : list) {
                Integer reportType = detailDTO.getReportType();
                if (ObjTool.isNotNull(reportType) && reportType.equals(DAY_TYPE_REPORT)) {
                    dayList.add(detailDTO);
                } else if (ObjTool.isNotNull(reportType) && reportType.equals(WEEK_TYPE_REPORT)) {
                    weekList.add(detailDTO);
                }
                if (ObjTool.isNotNull(dayList)) {
                    reportList.setDayReportList(dayList);
                }
                if (ObjTool.isNotNull(weekList)) {
                    reportList.setWeekReportList(weekList);
                }
                if (ObjTool.isNotNull(detailDTO.getDeptId()) && !ObjTool.isNotNull(reportList.getDeptId())) {
                    reportList.setDeptId(detailDTO.getDeptId());
                }
                if (ObjTool.isNotNull(detailDTO.getDeptName()) && !ObjTool.isNotNull(reportList.getDeptName())) {
                    reportList.setDeptName(detailDTO.getDeptName());
                }
                if (ObjTool.isNotNull(detailDTO.getEmployeeId()) && !ObjTool.isNotNull(reportList.getEmployeeId())) {
                    reportList.setEmployeeId(detailDTO.getEmployeeId());
                }
                if (ObjTool.isNotNull(detailDTO.getEmployeeName()) && !ObjTool.isNotNull(reportList.getEmployeeName())) {
                    reportList.setEmployeeName(detailDTO.getEmployeeName());
                }
                if (ObjTool.isNotNull(detailDTO.getReportTime()) && !ObjTool.isNotNull(reportList.getReportTime())) {
                    reportList.setReportTime(detailDTO.getReportTime());
                }
            }
            return success(reportList);
        }
        return success(new EmployeeReportListDTO());
    }

}