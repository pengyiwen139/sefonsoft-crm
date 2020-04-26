package com.sefonsoft.oa.service.permission;

import com.sefonsoft.oa.dao.permission.SysPermissionDao;
import com.sefonsoft.oa.domain.permission.SysPermissionSimpleDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionTreeDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateDTO;
import com.sefonsoft.oa.entity.permission.SysPermission;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (SysPermission)表服务实现类
 *
 * @author PengYiWen
 * @since 2019-11-11 11:24:57
 */
@Service("sysPermissionService")
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDao sysPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPermission queryById(Long id) {
        return this.sysPermissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysPermission> queryAllByLimit(int offset, int limit) {
        return this.sysPermissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(SysPermissionSimpleDTO sysPermission) {
        Date date = new Date();
        return sysPermissionDao.insert(sysPermission.setCreateTime(date).setModifiedTime(date));
    }

    /**
     * 通过主键删除数据
     *
     * @param idList 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String[] idList) {
        return sysPermissionDao.batchDeleteById(idList) > 0;
    }

    @Override
    public List<SysPermissionTreeDTO> listTree(SysPermission sysPermission) {
        List<SysPermissionTreeDTO> sysPermissionDTOs = sysPermissionDao.queryTreeNeed(sysPermission);
        if (ObjTool.isNotNull(sysPermissionDTOs)) {
            List<SysPermissionTreeDTO> sysPermissionTree = SysPermissionTreeDTO.buildByRecursive(sysPermissionDTOs);
            return sysPermissionTree;
        }
        return null;
    }

    @Override
    public int countByMenuId(String menuId) {
        return sysPermissionDao.countByMenuId(menuId);
    }

    /**
     * 修改数据
     *
     * @param sysPermission 实例对象
     * @return 实例对象
     */
    @Override
    public boolean edit(SysPermissionUpdateDTO sysPermission) {
        Date date = new Date();
        return sysPermissionDao.update(sysPermission.setModifiedTime(date)) > 0;
    }

    @Override
    public int batchCountChild( List<String> menuIdList) {
        return sysPermissionDao.batchCountChild(menuIdList);
    }

    @Override
    public int countInUsed(List<String> menuIdList) {
        return sysPermissionDao.countInUsed(menuIdList);
    }

    @Override
    public List<String> batchGetMenuIdById(String[] split) {
        return sysPermissionDao.batchGetMenuIdById(split);
    }

}