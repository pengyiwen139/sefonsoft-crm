package com.sefonsoft.oa.service.workorder;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigQueryDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigInsertDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigUpdateDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptOperateParams;
import com.sefonsoft.oa.entity.workorder.WorkorderDeptConfig;
import com.sefonsoft.oa.system.utils.PageResponse;

import java.util.List;

/**
 * @ClassName: WorkOrderDeptRefService
 * @author: Peng YiWen
 * @date: 2020/4/1  16:53
 */
public interface WorkOrderDeptRefService {

    /**
     * 插入派工单部门配置
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    boolean insert(WorkorderDeptConfigInsertDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO);

    /**
     * 更新派工单部门配置
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    boolean update(WorkorderDeptConfigUpdateDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO);

    /**
     * 派工单部门关联配置列表查询
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    PageResponse<List<WorkorderDeptConfigQueryDTO>> list(WorkorderDeptConfigQueryDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO);

    /**
     * 获取单个派工单部门关联
     * @return Response
     */
    WorkorderDeptConfig getById(Long id);

    /**
     * 获取添加和编辑界面需要的部门参数
     * @return Response
     */
    WorkorderDeptOperateParams getOperateParams();

    /**
     * 删除派工单部门关联
     * @return Response
     */
    boolean deleteById(Long[] idArray);
}

