package com.sefonsoft.oa.dao.permission;

import com.sefonsoft.oa.domain.permission.SysPermissionSimpleDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionTreeDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateDTO;
import com.sefonsoft.oa.entity.permission.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysPermission)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-11-11 11:19:22
 */
public interface SysPermissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysPermission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysPermission 实例对象
     * @return 对象列表
     */
    List<SysPermissionTreeDTO> queryAll(SysPermission sysPermission);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysPermission 实例对象
     * @return 对象列表
     */
    List<SysPermissionTreeDTO> queryTreeNeed(SysPermission sysPermission);

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    boolean insert(SysPermissionSimpleDTO sysPermission);

    /**
     * 修改数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    int update(SysPermissionUpdateDTO sysPermission);

    /**
     * 通过主键删除数据
     *
     * @param idList 主键
     * @return 影响行数
     */
    int batchDeleteById(@Param("idList") String[] idList);

    int countByMenuId(String menuId);

    int batchCountChild(@Param("menuIdList") List<String> menuIdList);

    int countInUsed(@Param("menuIdList") List<String> menuIdList);

    List<String> batchGetMenuIdById(@Param("idList") String[] idList);
}