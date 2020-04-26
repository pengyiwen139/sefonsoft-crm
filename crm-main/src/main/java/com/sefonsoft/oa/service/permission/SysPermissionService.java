package com.sefonsoft.oa.service.permission;

import com.sefonsoft.oa.domain.permission.SysPermissionSimpleDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionTreeDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateDTO;
import com.sefonsoft.oa.entity.permission.SysPermission;

import java.util.List;

/**
 * (SysPermission)表服务接口
 *
 * @author PengYiWen
 * @since 2019-11-11 11:24:56
 */
public interface SysPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysPermission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 实例对象
     */
    boolean insert(SysPermissionSimpleDTO sysPermission);

    /**
     * 通过主键删除数据
     *
     * @param idList 主键
     * @return 是否成功
     */
    boolean deleteById(String[] idList);

    /**
     * 获取权限树
     * @param sysPermission
     * @return
     */
    List<SysPermissionTreeDTO> listTree(SysPermission sysPermission);

    /**
     * 计算当前menuId下一级有多少子节点
     * @param menuId
     * @return
     */
    int countByMenuId(String menuId);

    /**
     * 修改权限
     * @param sysPermission
     * @return
     */
    boolean edit(SysPermissionUpdateDTO sysPermission);

    /**
     * 计算当前menuId下一级有多少子节点
     * @param menuIdList
     * @return
     */
    int batchCountChild( List<String> menuIdList);

    /**
     * 计算当前是否有在用当前权限
     * @param menuIdList
     * @return
     */
    int countInUsed(List<String> menuIdList);

    List<String> batchGetMenuIdById(String[] split);
}