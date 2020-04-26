package com.sefonsoft.oa.controller.workorder;


import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackPreInfo;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.service.workorder.WorkorderInfoService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
@RestController
@Api(tags = "售前派工单反馈接口")
@RequestMapping("/workorder")
public class PreFeecbackController {
    @Autowired
    private WorkorderInfoService workorderInfoService;
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private BizOpportsService bizOpportsService;

    @ApiOperation("添加售前反馈")
    @RequestMapping(value = "/preFeedback", method = RequestMethod.POST)
    public Response preFeedback(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderFeedbackPreInfo workorderFeedbackPreInfo) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(workorderFeedbackPreInfo.getPgdId()) || "".equals(workorderFeedbackPreInfo.getPgdId())) {
            return new Response<>().failure("派工单ID缺失");
        }

        workorderInfoService.preFeedback(loginUserDTO, workorderFeedbackPreInfo);
        return new Response<>().success("success");
    }

    @ApiOperation("售前查询派工单和反馈列表")
    @RequestMapping(value = "/preFeedback/list", method = RequestMethod.POST)
    public Response preFeedbackList(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderSearchDTO workorderSearchDTO) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        PageableResult searchResult = workorderInfoService.preFeedbackList(loginUserDTO, workorderSearchDTO);
        if (searchResult == null){
            return new Response<>().success();
        }
        PageResponse pageResponse = searchResult.PageResponse();
        return new Response<>().success(pageResponse);
    }

}

