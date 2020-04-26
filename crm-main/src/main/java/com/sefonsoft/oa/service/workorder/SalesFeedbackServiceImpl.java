package com.sefonsoft.oa.service.workorder;

import com.sefonsoft.oa.controller.report.DailyReportController;
import com.sefonsoft.oa.dao.workorder.WorkorderFeedbackSaleInfoDao;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.UpdateSalesFeedbackDTO;
import com.sefonsoft.oa.domain.workorder.vo.WorkorderFeedbackSaleInfoVo;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackSaleInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xielf
 */
@Service
public class SalesFeedbackServiceImpl implements SalesFeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(DailyReportController.class);


    private WorkorderFeedbackSaleInfoDao feedbackSaleInfoDao;

    @Resource
    private WorkorderInfoService workorderInfoService;

    @Autowired
    public void setFeedbackSaleInfoDao(WorkorderFeedbackSaleInfoDao feedbackSaleInfoDao) {
        this.feedbackSaleInfoDao = feedbackSaleInfoDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSalesFeedback(List<UpdateSalesFeedbackDTO> feedbackDTOList, LoginUserDTO loginUser) {
        WorkorderInfo workorderInfo = workorderInfoService.getDetail(null, feedbackDTOList.get(0).getPgdId());
        if (workorderInfo == null) {
            logger.info("workorderInfo is null");
            return;
        }

        final List<WorkorderFeedbackSaleInfo> collect = feedbackDTOList.stream().map(v -> {
            WorkorderFeedbackSaleInfo feedbackSaleInfo = new WorkorderFeedbackSaleInfo();
            feedbackSaleInfo.setId(v.getId());
            feedbackSaleInfo.setCaseGrasp(v.getCaseGrasp());
            feedbackSaleInfo.setCustomerFeedback(v.getCustomerFeedback());
            feedbackSaleInfo.setCustomerLead(v.getCustomerLead());
            feedbackSaleInfo.setModifiedTime(new Date());
            feedbackSaleInfo.setOther(v.getOther());
            feedbackSaleInfo.setService(v.getService());
            feedbackSaleInfo.setTechnology(v.getTechnology());
            feedbackSaleInfo.setTimeComply(v.getTimeComply());

            //标记为已反馈状态
            feedbackSaleInfo.setSaleCommentStatus(1);

            return feedbackSaleInfo;

        }).collect(Collectors.toList());

        feedbackSaleInfoDao.updateBatchByPrimaryKeySelective(collect);

        workorderInfoService.checkAndUpdatePgdStatus(workorderInfo.getPgdId());
    }


    @Override
    public List<WorkorderFeedbackSaleInfoVo> getOrderSalesFeedback(String orderId) {

        List<WorkorderFeedbackSaleInfo> feedbackSaleInfos = feedbackSaleInfoDao.findSaleFeedbackByPgdId(orderId);

        return feedbackSaleInfos.stream().map(v -> {
            WorkorderFeedbackSaleInfoVo saleInfoVo = new WorkorderFeedbackSaleInfoVo();
            BeanUtils.copyProperties(v, saleInfoVo);
            return saleInfoVo;
        }).collect(Collectors.toList());
    }
}
