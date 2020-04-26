package com.sefonsoft.oa.service.workorder;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.UpdateSalesFeedbackDTO;
import com.sefonsoft.oa.domain.workorder.vo.WorkorderFeedbackSaleInfoVo;

import java.util.List;

/**
 * @author xielf
 */
public interface SalesFeedbackService {


  /**
   * 添加销售反馈
   *
   * @param feedback
   * @param loginUser
   */
  void updateSalesFeedback(List<UpdateSalesFeedbackDTO> feedbackDTOList, LoginUserDTO loginUser);

  /**
   * 获取销售评论
   *
   * @param orderId
   * @return
   */
  List<WorkorderFeedbackSaleInfoVo> getOrderSalesFeedback(String orderId);

}
