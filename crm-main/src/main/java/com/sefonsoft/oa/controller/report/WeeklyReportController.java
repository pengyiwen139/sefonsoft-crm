package com.sefonsoft.oa.controller.report;

import com.sefonsoft.oa.domain.report.WeeklyReportQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.report.WeeklyReport;
import com.sefonsoft.oa.service.report.DailyReportService;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.report.WeeklyReportService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
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

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.C_WORKREPORT_WEEKLY_CHECK;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.ERROR_QUERY;
import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;

/**
 * 周报表(WeeklyReportController)表控制层
 *
 * @author Aron
 * @since 2019-12-09 11:22:06
 */
@Api(tags = "周报相关接口")
@RestController
public class WeeklyReportController {

    private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);


    /**
     * 服务对象
     */
    @Resource
    private WeeklyReportService weeklyReportService;

    @Resource
    private DailyReportService dailyReportService;


    @Value("${reportDayLastHours}")
    private String reportDayLastHoursStr;

    /**
     * 周报列表
     *
     * @param weeklyReportQueryDTO
     * @return Response
     */
    @ApiOperation(value = "查询周报列表", response = WeeklyReportQueryDTO.class)
    @RequestMapping(value = "/weeklyReport/list", method = RequestMethod.POST)
    public Response weeklyReportList(@RequestBody WeeklyReportQueryDTO weeklyReportQueryDTO, @CurrentUser LoginUserDTO loginUser) throws Exception {
        logger.debug("周报列表" + weeklyReportQueryDTO);
//        String year = weeklyReportQueryDTO.getYear();
//        if (!ObjTool.isNotNull(year)) {
//            return new Response<>().failure("年不能为空");
//        }
//        String month = weeklyReportQueryDTO.getMonth();
//        if (!ObjTool.isNotNull(month)) {
//            return new Response<>().failure("月不能为空");
//        }

        PageableResult searchResult = weeklyReportService.getCondition(weeklyReportQueryDTO, loginUser);
        if (searchResult == null) {
            return new Response<>().failure(ERROR_QUERY);
        }
        PageResponse pageResponse = searchResult.PageResponse();
        return new Response<>().success(pageResponse);
    }

    /**
     * 保存周报
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "保存周报")
    @RequestMapping(value = "/weeklyReport", method = RequestMethod.POST)
    public Response saveWeeklyReport(@RequestBody WeeklyReport report, @CurrentUser LoginUserDTO loginUser) throws Exception {
        logger.debug("保存周报" );
        Response errorResponse = weeklyReportService.insertCheck(report);
        if (ObjTool.isNotNull(errorResponse)) {
            return errorResponse;
        }
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

//        //检查该周周报是否已生成
//        WeeklyReportQueryDTO weeklyReportQueryDTO = new WeeklyReportQueryDTO();
//        weeklyReportQueryDTO.setStartDay(report.getStartDate());
//        weeklyReportQueryDTO.setEndDay(report.getEndDate());
//        List<String> depIds = new ArrayList<>();
//        depIds.add(loginUser.getDeptId());
//        weeklyReportQueryDTO.setDeptIds(depIds);
//        Object searchResult = weeklyReportService.getCondition(weeklyReportQueryDTO, loginUser);
//        if (searchResult != null) {
//            DailyReportServiceImpl.SearchResult rsult = (DailyReportServiceImpl.SearchResult) searchResult;
//            if (rsult.resultList.size() > 0) {
//                return new Response<>().failure("该周报已经生成");
//            }
//        }


        return weeklyReportService.save(report, loginUser) != null ? new Response<>().success() : new Response<>().failure("保存失败");
    }


    /**
     * 根据周报id获取周报详细
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "根据周报id获取周报详细", response = WeeklyReport.class)
    @RequestMapping(value = "/WeeklyReport/detail/{id}", method = RequestMethod.GET)
    public Response WeeklyReportDetail(@ApiParam(required = true, value = "周报id") @PathVariable(required = true) Long id,
                                       @CurrentUser LoginUserDTO loginUser) {
        logger.debug("根据周报id获取周报详细" );
        if (!ObjTool.isNotNull(loginUser.getUserId())) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(id)) {
            return new Response<>().failure("周报id缺失");
        }

        WeeklyReport report = weeklyReportService.getWeeklyReportDetail(id, loginUser);
        return report != null ? new Response<>().success(report) : new Response<>().failure("记录未找到");
    }


}