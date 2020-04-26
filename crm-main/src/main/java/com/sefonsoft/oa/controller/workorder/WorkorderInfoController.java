package com.sefonsoft.oa.controller.workorder;


import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.WorkOrderMatchConfigDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderApprovalDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackPreInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackSaleInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderInfo;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.service.workorder.WorkorderInfoService;
import com.sefonsoft.oa.service.workorder.WorkorderInfoServiceImpl;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.service.workorder.WorkorderInfoServiceImpl.WORKORDER_APPROVAL_STATUS_ACCEPT;
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
@Api(tags = "售前派工单接口")
@RequestMapping("/workorder")
public class WorkorderInfoController {
    private static final Logger logger = LoggerFactory.getLogger(WorkorderInfoController.class);

    @Autowired
    private WorkorderInfoService workorderInfoService;
    @Autowired
    private SysEmployeeService sysEmployeeService;
    @Autowired
    private BizOpportsService bizOpportsService;
    @Value("${Approver.per}")
    private String pers;

    @ApiOperation("新建派工单")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response insert(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderInfo workorderInfo) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        Response errorResponse = workorderInfoService.checkInsert(workorderInfo);
        if (ObjTool.isNotNull(errorResponse)) {
            return errorResponse;
        }

        workorderInfoService.save(workorderInfo, loginUserDTO);
        return new Response<>().success();
    }

    @ApiOperation("删除派工单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderInfo workorderInfo) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (StringUtils.isEmpty(workorderInfo.getPgdId())) {
            return new Response<>().failure("派工单号缺失");
        }

        Response result = workorderInfoService.delete(workorderInfo, loginUserDTO);
        return result == null ? new Response<>().success() : result;
    }

    @ApiOperation("查询售前部门和销售部门匹配列表")
    @RequestMapping(value = "/getMatchList", method = RequestMethod.GET)
    public Response getMatchList(@CurrentUser LoginUserDTO loginUserDTO) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<WorkOrderMatchConfigDTO> resultMap = workorderInfoService.getDeptConfigMap(loginUserDTO);
        return new Response<>().success(resultMap);
    }


    @ApiOperation("派工单审批")
    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public Response approval(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderApprovalDTO workorderApprovalDTO) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(workorderApprovalDTO)) {
            return new Response().failure("数据缺失");
        }

        if (!ObjTool.isNotNull(workorderApprovalDTO.getPgdId())) {
            return new Response().failure("派工单号缺失");
        }

        if (!ObjTool.isNotNull(workorderApprovalDTO.getApprovalResult())) {
            return new Response().failure("审批数据缺失");
        }

        if (workorderApprovalDTO.getApprovalResult() == WORKORDER_APPROVAL_STATUS_ACCEPT && !ObjTool.isNotNull(workorderApprovalDTO.getTargetEmployeeIdList())) {
            return new Response().failure("审批对象缺失");
        }

        WorkorderInfo workorderInfo = workorderInfoService.approval(loginUserDTO, workorderApprovalDTO);
        return workorderInfo != null ? new Response<>().success() : new Response<>().failure("审批失败");
    }

    @ApiOperation("派工单重新指派")
    @RequestMapping(value = "/reassign", method = RequestMethod.POST)
    public Response reassign(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderApprovalDTO workorderApprovalDTO) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(workorderApprovalDTO)) {
            return new Response().failure("数据缺失");
        }

        if (!ObjTool.isNotNull(workorderApprovalDTO.getPgdId())) {
            return new Response().failure("派工单号缺失");
        }


        if ( !ObjTool.isNotNull(workorderApprovalDTO.getTargetEmployeeIdList())) {
            return new Response().failure("审批对象缺失");
        }

        WorkorderInfo workorderInfo = workorderInfoService.getDetail(loginUserDTO, workorderApprovalDTO.getPgdId());
        if (!ObjTool.isNotNull(workorderInfo)) {
            logger.info("找不到派工单数据，workorderApprovalDTO ", workorderApprovalDTO);
            return new Response<>().failure("找不到派工单数据");
        }

        if (!ObjTool.isNotNull(workorderInfo.getDeptId()) || StringUtils.isEmpty(workorderInfo.getDeptId())) {
            logger.info("找不到部门数据,  workorderInfo", workorderInfo);
            return new Response<>().failure("找不到部门数据");
        }

        if (workorderInfo.getApprovalStatus() != WORKORDER_APPROVAL_STATUS_ACCEPT) {
            logger.info("该派工单不在已审批状态,不能重新指派", workorderInfo);
            return new Response<>().failure("该派工单不在已审批状态");
        }

        List<WorkorderFeedbackPreInfo> workorderFeedbackPreInfo = workorderInfo.getWorkorderFeedbackPreInfo();
        if (workorderFeedbackPreInfo == null) {
            logger.info("workorderFeedbackPreInfo is null");
            return new Response<>().failure("派工单找不到售前数据");
        }

        List<WorkorderFeedbackSaleInfo> workorderFeedbackSaleInfo = workorderInfo.getWorkorderFeedbackSaleInfo();
        if (workorderFeedbackSaleInfo == null) {
            logger.info("workorderFeedbackSaleInfo is null");
            return new Response<>().failure("派工单找不到销售数据");
        }

        for (WorkorderFeedbackPreInfo pre : workorderFeedbackPreInfo) {
            if (pre.getPreCommentStatus() == 1) {
                return new Response<>().failure("当前派工单不可以重新指派");
            }
        }

        for (WorkorderFeedbackSaleInfo pre : workorderFeedbackSaleInfo) {
            if (pre.getSaleCommentStatus() == 1) {
                return new Response<>().failure("当前派工单不可以重新指派");
            }
        }

        boolean reAssign = workorderInfoService.reAssign(loginUserDTO, workorderApprovalDTO);
        return reAssign ? new Response<>().success() : new Response<>().failure("指派失败");
    }

    @ApiOperation("派工单列表查询）")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response workorderList(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody WorkorderSearchDTO workorderSearchDTO) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(workorderSearchDTO.getDeptIds()) || workorderSearchDTO.getDeptIds().size() == 0) {
            return new Response<>().failure("部门缺失");
        }

        PageableResult searchResult = workorderInfoService.getCondition(loginUserDTO, workorderSearchDTO);
        if (searchResult == null) {
            return new Response<>().success(searchResult);
        }

        PageResponse pageResponse = searchResult.PageResponse();
        return new Response<>().success(pageResponse);
    }

    @ApiOperation(value = "派工单详细（售前总监急领导）", response = WorkorderInfo.class)
    @RequestMapping(value = "/detail/{pgdId}", method = RequestMethod.GET)
    public Response detail(@CurrentUser LoginUserDTO loginUserDTO, @ApiParam(required = true, value = "派工单详细") @PathVariable(required = true) String pgdId) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (!ObjTool.isNotNull(pgdId) || "".equals(pgdId)) {
            return new Response<>().failure("派工单号缺失");
        }

        WorkorderInfo workorderInfo = workorderInfoService.getDetail(loginUserDTO, pgdId);

        return new Response<>().success(workorderInfo);
    }


    //    @ApiOperation(value = "获取商机相关码表信息")
    @PostMapping("/getCodeInfo")
    public Response getCodeInfo(@CurrentUser LoginUserDTO loginUserDTO) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("demandWay", workorderInfoService.getDemandWay());
        map.put("objectAbility", workorderInfoService.getObjectAbility());
        return ObjTool.isNotNull(map) ? new Response<>().success(map) : new Response<>().failure();
    }
}

