package com.sefonsoft.oa.dao.workorder;

import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackPreInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackSaleInfo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
public interface WorkorderFeedBackDao {


    /**
     * 功能描述: 保存售前派工单反馈
     *
     * @Param: workorderFeedbackPreInfo
     * @Author: liwenyi
     */
    int insertFeedbackPreInfo(WorkorderFeedbackPreInfo workorderFeedbackPreInfo);


    /**
     * 功能描述: 保存销售派工单反馈
     *
     * @Param: workorderFeedbackPreInfo
     * @Author: liwenyi
     */
    int insertFeedbackSaleInfo(WorkorderFeedbackSaleInfo workorderFeedbackSaleInfo);

    /**
     * 功能描述: 根据id查询派工单反馈
     *
     * @Param: id
     * @Return: WorkorderFeedbackPreInfo
     */
    WorkorderFeedbackPreInfo queryPreFeedbackById(Long id);

    /**
     * 功能描述: 根据派工单号查询反馈列表
     *
     * @Param: pgdId
     * @Return: WorkorderFeedbackPreInfo
     */
    List<WorkorderFeedbackPreInfo> queryPreFeedbackByPgdId(String pgdId);

    /**
     * 功能描述: 更新派工单反馈
     *
     * @Param: id
     * @Return: WorkorderFeedbackPreInfo
     */
    void updatePreFeedback(WorkorderFeedbackPreInfo workorderFeedbackPreInfo);

    /**
     * 功能描述: 更新派工单反馈
     *
     * @Param: id
     * @Return: WorkorderFeedbackPreInfo
     */
    void deletePreFeedbackByPrimaryKey(Long id);

    /**
     * 功能描述: 搜索售前反馈列表
     *
     * @Param: employeeIds
     * @Param: page
     * @Param: limit
     * @Return: WorkorderFeedbackPreInfo
     */
//    List<WorkorderFeedbackPreInfo> getPreFeedbackList(@Param("pgdId") String pgdId, @Param("employeeIds") List<String> employeeIds, @Param("approvalStatusList") List<Integer> approvalStatusList, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Integer page, @Param("limit") Integer limit);
    List<WorkorderFeedbackPreInfo> getPreFeedbackList(WorkorderSearchDTO workorderSearchDTO);

    /**
     * 功能描述: 搜索售前反馈列表总数量
     *
     * @Param: employeeIds
     * @Param: page
     * @Param: limit
     * @Return: WorkorderFeedbackPreInfo
     */
//    Long getPreFeedbackListTotal(@Param("pgdId") String pgdId, @Param("employeeIds") List<String> employeeIds, @Param("approvalStatusList") List<Integer> approvalStatusList, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Integer page, @Param("limit") Integer limit);
    Long getPreFeedbackListTotal(WorkorderSearchDTO workorderSearchDTO);


}
