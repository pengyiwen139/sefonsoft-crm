package com.sefonsoft.oa.service.workorder;

import com.sefonsoft.oa.dao.workorder.WorkorderDeptConfigDao;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigInsertDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigQueryDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigUpdateDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptOperateParams;
import com.sefonsoft.oa.entity.workorder.WorkorderDeptConfig;
import com.sefonsoft.oa.system.emun.WorkOrderDeptEmun;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: WorkOrderDeptRefServiceImpl
 * @author: Peng YiWen
 * @date: 2020/4/1  16:53
 */
@Service
public class WorkOrderDeptRefServiceImpl implements WorkOrderDeptRefService {

    @Resource
    private WorkorderDeptConfigDao workOrderDeptRefDao;

    /**
     * 插入派工单部门配置
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    @Override
    public boolean insert(WorkorderDeptConfigInsertDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO) {
        Date date = new Date();
        return workOrderDeptRefDao.insert(workorderDeptCfgDO.setCreateTime(date)
                                                            .setModifiedTime(date)
                                                            ,loginUserDTO.getUserId());
    }

    /**
     * 更新派工单部门配置
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    @Override
    public boolean update(WorkorderDeptConfigUpdateDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO) {
        return workOrderDeptRefDao.update(workorderDeptCfgDO.setModifiedTime(new Date()), loginUserDTO.getUserId());
    }

    /**
     * 派工单部门关联配置列表查询
     * @param workorderDeptCfgDO
     * @param loginUserDTO
     * @return
     */
    @Override
    public PageResponse<List<WorkorderDeptConfigQueryDTO>> list(WorkorderDeptConfigQueryDTO workorderDeptCfgDO, LoginUserDTO loginUserDTO) {
        return new PageResponse(workOrderDeptRefDao.count(workorderDeptCfgDO), (workOrderDeptRefDao.queryAllPage(workorderDeptCfgDO)));
    }

    /**
     * 获取单个派工单部门关联
     * @return Response
     */
    @Override

    public WorkorderDeptConfig getById(Long id){
        return workOrderDeptRefDao.queryById(id);
    }

    /**
     * 获取添加和编辑界面需要的部门参数
     * @return Response
     */
    @Override
    public WorkorderDeptOperateParams getOperateParams() {
        return new WorkorderDeptOperateParams(workOrderDeptRefDao.getResourceDeptListByParentDeptId(WorkOrderDeptEmun.AS_DEPT.getDeptId()),
                                              workOrderDeptRefDao.getDeptListByParentDeptId(WorkOrderDeptEmun.BS_DEPT.getDeptId()));
    }

    /**
     * 删除派工单部门关联
     * @return Response
     */
    @Override
    public boolean deleteById(Long[] idArray) {
        return workOrderDeptRefDao.deleteById(idArray);
    }


}