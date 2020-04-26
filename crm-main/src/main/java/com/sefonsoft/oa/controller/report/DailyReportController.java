package com.sefonsoft.oa.controller.report;

import com.sefonsoft.oa.domain.report.*;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.DailyReport;
import com.sefonsoft.oa.service.report.DailyReportService;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * 周日报表(DailyReportController)表控制层
 *
 * @author Aron
 * @since 2019-12-09 11:22:06
 */
@Api(tags = "日报相关接口")
@RestController
public class DailyReportController {
    /**
     * 服务对象
     */
    @Resource
    private DailyReportService dailyReportService;


    @Value("${reportDayLastHours}")
    private String reportDayLastHoursStr;

    private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);


    /**
     * 日报列表
     *
     * @param employeeQueryDTO
     * @return Response
     */
    @ApiOperation(value = "查询员工日报列表", response = EmployeeReportQueryDTO.class)
    @RequestMapping(value = "/dailyeReport/list", method = RequestMethod.GET)
    public Response dailyReportList(EmployeeReportQueryDTO employeeReportQueryDTO) throws Exception {

        logger.debug("查询员工日报列表");
        String employeeId = employeeReportQueryDTO.getEmployeeId();
        if (!ObjTool.isNotNull(employeeId)) {
            return new Response<>().failure("员工编号不能为空");
        }
        String year = employeeReportQueryDTO.getYear();
        if (!ObjTool.isNotNull(year)) {
            return new Response<>().failure("年不能为空");
        }
        String month = employeeReportQueryDTO.getMonth();
        if (!ObjTool.isNotNull(month)) {
            return new Response<>().failure("月不能为空");
        }

        Map<Integer, DailyReport> employeeReportDetailMap = dailyReportService.getCondition(employeeReportQueryDTO);

        return new Response<>().success(employeeReportDetailMap);
    }

    /**
     * 添加和修改日报
     *
     * @param
     * @return Response
     */
    @MethodPermission(menuIdArray = {C_WORKREPORT_DAILY_CALENDAR,C_WORKREPORT_WEEKLY_CHECK})
    @ApiOperation(value = "添加和修改日报")
    @RequestMapping(value = "/dailyReport", method = RequestMethod.POST)
    public Response saveDailyReport(@RequestBody DailyReportInsertOrUpdateInfo report, @CurrentUser LoginUserDTO loginUser) {
        logger.info("添加和修改日报");
        Response errorResponse = dailyReportService.insertCheck(report);
        if (ObjTool.isNotNull(errorResponse)) {
            return errorResponse;
        }
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }


        //检查日报id是否存在，存在则为更新，不存在则为新增
        if (!ObjTool.isNotNull(report.getId())) {

            logger.debug("新增日报,条件检查");
            //检查是否超过日报填写时间
            Date reportTime = report.getReportTime();
            if (ObjTool.isNotNull(Integer.parseInt(reportDayLastHoursStr))) {
                //限制日报在指定的某一天的某个小时后不能填写
                Response response = dailyReportService.ifItsTime2AddReport(Integer.parseInt(reportDayLastHoursStr), reportTime);
                if (ObjTool.isNotNull(response)) {
                    return response;
                }
            }
            //判断是否已存在当前日期的日报
            String timeStr = DateUtils.formatDate(report.getReportTime(), "yyyy-MM-dd");
            DailyReport temp = this.dailyReportService.getDailyReportDetail(timeStr, loginUser.getUserId());
            if (ObjTool.isNotNull(temp)) {
                return new Response<>().failure("当前日期的日报已存在");
            }
        }

        return dailyReportService.save(report, loginUser) != null ? new Response<>().success() : new Response<>().failure(UPDATE_ERROR);
    }

    /**
     * 获取可填写日报的最早日期和最晚时刻
     *
     * @return Response
     */
    @ApiOperation(value = "获取可填写日报的最早日期和最晚时刻")
    @RequestMapping(value = "/dailyReport/getMostEarlyAddDateTime", method = RequestMethod.GET)
    public Response getMostEarlyAddDateTime(@CurrentUser LoginUserDTO loginUser) {
        logger.debug("获取可填写日报的最早日期和最晚时刻");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = dailyReportService.getMostEarlyAddDateTime(Integer.parseInt(reportDayLastHoursStr), loginUser, sdf);
        if (!ObjTool.isNotNull(map)) {
            return new Response<>().failure("日报可填写日期出错");
        } else {
            if (map.size() != 2) {
                return new Response<>().failure("日报可填写日期出错,请联系管理员");
            } else {
                return new Response<>().success(map);
            }
        }
    }

    /**
     * 获取单天的周日报列表详情
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "获取单天的周日报列表详情", response = DailyReport.class)
    @RequestMapping(value = "/dailyReport/detail/{reportTime}", method = RequestMethod.GET)
    public Response detailByOneDay
    (@ApiParam(required = true, value = "日期类型的报告时间，日期类型") @PathVariable(required = true) String reportTime,
     @CurrentUser LoginUserDTO loginUser) {
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure("用户未登录，请重新登录");
        }
        return new Response<>().success(dailyReportService.getDailyReportDetail(reportTime, loginUser.getUserId()));
    }


    /**
     * 日报搜索
     *
     * @param dailyReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "日报搜索", response = DailyReport.class)
    @RequestMapping(value = "/dailyReport/search", method = RequestMethod.POST)
    public Response dailyReportSearch(@RequestBody DailyReportQueryDTO
                                              dailyReportQueryDTO, @CurrentUser LoginUserDTO loginUser) throws Exception {
        logger.debug("日报搜索");

        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(dailyReportQueryDTO.getPage())) {
            dailyReportQueryDTO.setPage(1);
        }

        if (!ObjTool.isNotNull(dailyReportQueryDTO.getLimit())) {
            dailyReportQueryDTO.setLimit(10);
        }

        PageableResult searchResult = dailyReportService.dailyReportSearch(dailyReportQueryDTO, loginUser);
        if (searchResult == null) {
            return new Response<>().success(searchResult);
        }
        PageResponse pageResponse = searchResult.PageResponse();
        return new Response<>().success(pageResponse);
    }

    /**
     * 日报统计
     *
     * @param dailyReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "日报统计", response = Map.class)
    @RequestMapping(value = "/dailyReport/statistics", method = RequestMethod.POST)
    public Response dailyReportStatistics(@RequestBody DailyReportQueryDTO
                                                  dailyReportQueryDTO, @CurrentUser LoginUserDTO loginUser) throws Exception {
        logger.debug("日报统计");
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        String startDay = dailyReportQueryDTO.getStartDay();
        if (!ObjTool.isNotNull(startDay)) {
            return new Response<>().failure("开始日期不能为空");
        }
        String endDay = dailyReportQueryDTO.getEndDay();
        if (!ObjTool.isNotNull(endDay)) {
            return new Response<>().failure("结束日期不能为空");
        }

        List<String> deptIds = dailyReportQueryDTO.getDeptIds();
        if (!ObjTool.isNotNull(deptIds) || deptIds.size() == 0) {
            return new Response<>().failure("找不到部门");
        }

        Object result = dailyReportService.statistics(dailyReportQueryDTO, loginUser);
        if (result == null) {
            return new Response<>().success(null);
        }

        return new Response<>().success(result);

    }

}