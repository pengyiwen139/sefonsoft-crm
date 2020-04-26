package com.sefonsoft.oa.dao.roledept;

import com.sefonsoft.oa.entity.role.SysRoleDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysRoleDept)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-18 15:29:07
 */
public interface SysRoleDeptDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRoleDept queryById(Long id);


    /**
     * 通过deptid查询
     * xielf
     * @param deptId
     * @return
     */
    SysRoleDept queryByDeptId(@Param("deptId") String deptId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRoleDept> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRoleDept 实例对象
     * @return 对象列表
     */
    List<SysRoleDept> queryAll(SysRoleDept sysRoleDept);

    /**
     * 新增数据
     *
     * @param sysRoleDept 实例对象
     * @return 影响行数
     */
    int insert(SysRoleDept sysRoleDept);

    /**
     * 修改数据
     *
     * @param sysRoleDept 实例对象
     * @return 影响行数
     */
    int update(SysRoleDept sysRoleDept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}