package com.sefonsoft.oa.controller.workorder;


import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.UpdateSalesFeedbackDTO;
import com.sefonsoft.oa.service.workorder.SalesFeedbackService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;


/**
 * @author xielf
 */
@RestController
@Api(tags = "销售侧派工单回复")
@RequestMapping("/workorder/sales")
public class SalesFeedbackController extends BaseController {


    private SalesFeedbackService feedbackService;


    @Autowired
    public void setFeedbackService(SalesFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 添加反馈
     *
     * @param feedbackDTO
     * @return
     */
    @ApiOperation("提交回复")
    @PostMapping("/feedback")
    public Response feedback(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody List<UpdateSalesFeedbackDTO> feedbackDTOList) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }

        if (CollectionUtils.isEmpty(feedbackDTOList)) {
            return failure("缺少反馈数据");
        }

        for (UpdateSalesFeedbackDTO feedbackDTO : feedbackDTOList) {
            if (!ObjTool.isNotNull(feedbackDTO.getId())) {
                return new Response<>().failure("主键缺失");
            }

            if (!ObjTool.isNotNull(feedbackDTO.getPgdId())) {
                return new Response<>().failure("派工单缺失");
            }
        }

        try {
            feedbackService.updateSalesFeedback(feedbackDTOList, getLoginDTO());
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return failure();
        }
    }

    /**
     * 查询销售回复信息
     *
     * @param orderId
     * @return
     */
    @ApiOperation("查询销售回复信息")
    @GetMapping("/feedbackList")
    public Response feedbackList(@ApiParam("派工单ID,即pgdId") @RequestParam String orderId) {

        try {
            return success(feedbackService.getOrderSalesFeedback(orderId));
        } catch (Exception e) {
            e.printStackTrace();
            return failure();
        }
    }
}

