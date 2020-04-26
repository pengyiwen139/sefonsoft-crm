package com.sefonsoft.oa.dao.workorder;

import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.entity.workorder.WorkorderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
public interface WorkorderInfoDao {

    String getMaxPGD(String prefix);

    /**
     * 功能描述: 根据派工单号查询
     *
     * @Param: pgdId
     * @Return: WorkorderInfo
     */
    WorkorderInfo queryByPgdId(String pgdId);

    int insert(WorkorderInfo workorderInfo);

    int modify(WorkorderInfo workorderInfo);

    List<Map<String, Object>> getProjectStage();

    List<Map<String, Object>> getDemandWay();

    List<Map<String, Object>> getObjectAbility();

    List<WorkorderInfo> getCondition(WorkorderSearchDTO workorderSearchDTO);

    Long getConditionTotal(WorkorderSearchDTO workorderSearchDTO);

    List<SysEmployee> getApprovalUserList(@Param("perms") String perms, @Param("deptId") String deptId);


    /**
     * 功能描述: 获取销售售前匹配配置
     *
     * @Param: deptIds
     * @Return: List<String>
     */
    List<String> getSourceDeptConfig(@Param("deptIds") List<String> deptIds);

    /**
     * 功能描述: 获取售前匹配配置
     *
     * @Param: deptIds
     * @Return: List<String>
     */
    List<String> getDesDeptConfig(@Param("deptIds") List<String> deptIds);


    /**
     * 销售条数
     * xielf
     *
     * @param employeeId
     * @return
     */
    int countBySales(String employeeId);

    /**
     * 售前条数
     * xielf
     *
     * @param employeeId
     * @return
     */
    int countByPre(String employeeId);

    int totalCount();

}
