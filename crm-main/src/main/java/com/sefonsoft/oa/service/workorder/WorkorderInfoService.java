package com.sefonsoft.oa.service.workorder;

import com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.WorkOrderMatchConfigDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderApprovalDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackPreInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderInfo;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.response.Response;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
public interface WorkorderInfoService {

    /**
     * 功能描述: 保存前检查
     *
     * @Param: workorderInfo
     * @Return: Response
     * @Author: liwenyi
     */
    Response checkInsert(WorkorderInfo workorderInfo);

    /**
     * 功能描述: 保存派工单
     *
     * @Param: workorderInfo
     * @Author: liwenyi
     */
    WorkorderInfo save(WorkorderInfo workorderInfo, LoginUserDTO loginUser);

    /**
     * 功能描述: 派工单审批
     *
     * @Param: loginUserDTO
     * @Param: workorderApprovalDTO
     * @Author: liwenyi
     */
    WorkorderInfo approval(LoginUserDTO loginUserDTO, WorkorderApprovalDTO workorderApprovalDTO);

    /**
     * 功能描述: 派工单重新指派
     *
     * @Param: loginUserDTO
     * @Param: workorderApprovalDTO
     * @Author: liwenyi
     */
    boolean reAssign(LoginUserDTO loginUserDTO, WorkorderApprovalDTO workorderApprovalDTO);

    /**
     * 功能描述: 更新派工单状态
     *
     * @Param: pgdId
     * @Author: liwenyi
     */
    void checkAndUpdatePgdStatus(String pgdId);


    /**
     * 获取派工单号
     *
     * @return
     */
    String getPGDnumber();


    /**
     * 功能描述: 获取需求方式表信息
     *
     * @Param: []
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: liwenyi
     */
    List<Map<String, Object>> getDemandWay();

    /**
     * 功能描述: 获取对象技术能力评估表信息
     *
     * @Param: []
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: liwenyi
     */
    List<Map<String, Object>> getObjectAbility();


    /**
     * 功能描述: 派工单查询
     *
     * @Param: loginUserDTO
     * @Param: workorderSearchDTO
     * @Return: Object
     * @Author: liwenyi
     */
    PageableResult getCondition(LoginUserDTO loginUserDTO, WorkorderSearchDTO workorderSearchDTO);


    /**
     * 功能描述: 售前反馈派工单
     *
     * @Param: loginUserDTO
     * @Param: workorderFeedbackPreInfo
     * @Return: Object
     * @Author: liwenyi
     */
    Object preFeedback(LoginUserDTO loginUserDTO, WorkorderFeedbackPreInfo workorderFeedbackPreInfo);


    /**
     * 功能描述: 售前反馈列表
     *
     * @Param: loginUserDTO
     * @Param: workorderSearchDTO
     * @Return: Object
     * @Author: liwenyi
     */
    PageableResult preFeedbackList(LoginUserDTO loginUserDTO, WorkorderSearchDTO workorderSearchDTO);

    /**
     * 功能描述: 派工单详细
     *
     * @Param: loginUserDTO
     * @Param: pgdId
     * @Return: WorkorderInfo
     * @Author: liwenyi
     */
    WorkorderInfo getDetail(LoginUserDTO loginUserDTO, String pgdId);

    /**
     * 功能描述: 删除派工单
     *
     * @Param: workorderInfo
     * @Param: loginUserDTO
     * @Author: liwenyi
     */
    Response delete(WorkorderInfo workorderInfo, LoginUserDTO loginUserDTO);

    /**
     * 功能描述: 获取销售售前匹配配置
     *
     * @Param: loginUser
     * @Return: List<WorkOrderMatchConfigDTO>
     */
    List<WorkOrderMatchConfigDTO> getDeptConfigMap(LoginUserDTO loginUser);

    /**
     * 工单统计
     * @param statistics
     * @return
     */
    int workOrderStatistics(WorkOrderStatisticsDTO statistics, LoginUserDTO userDTO);

}
