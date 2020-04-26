package com.sefonsoft.oa.dao.role;

import com.sefonsoft.oa.domain.permission.SysPermissionInsertDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateRefDTO;
import com.sefonsoft.oa.domain.role.*;
import com.sefonsoft.oa.entity.application.SysApplication;
import com.sefonsoft.oa.entity.role.SysRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysRole)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-11-08 17:24:58
 */
public interface SysRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRole queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRole 实例对象
     * @return 对象列表
     */
    List<SysRole> queryAll(SysRole sysRole);

    /**
     * 通过实体作为筛选条件查询
     * @return 对象列表
     */
    List<SysRole> queryUserRole(String userId);

    /**
     * 通过实体作为筛选条件查询
     * @return 对象列表
     */
    List<SysRoleUserDTO> queryUserRoleByUserIdList(@Param("list") List<String> list);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int insert(SysRole sysRole);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int update(SysRoleUpdateDTO sysRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    boolean deleteById(Long id);

    List<SysRole> list(SysRoleQueryDTO sysRoleQueryDTO);

    Integer listCount(SysRoleQueryDTO sysRoleQueryDTO);

    List<SysApplication> listApplication(SysApplication sysApplication);

    boolean batchAddRolePermissionRef(@Param("permissionList") List<SysPermissionInsertDTO> permissionList);

    int countRoleByRoleId(String roleId);

    int batchDelPermissionRef(String roleId);

    List<String> batchGetRoleIdById(@Param("idList") String[] idList);

    int countInUsed(@Param("roleIdList") List<String> roleIdList);

    boolean batchDeleteById(@Param("idList") List<String> idList);

    boolean batchDeleteRolePermissionRef(@Param("idList") List<String> idList);

    String getMaxRoleId();

    boolean addMenuRole(SysMenuRoleInsertDTO sysMenuRoleInsertDTO);

    boolean addDataRole(SysDataRoleInsertDTO sysDataRoleInsertDTO);

    boolean batchAddRoleDeptRef(@Param("idList") List<SysRoleDeptInsertDTO> deptList);

    boolean batchAddRoleDeptRefInUpdate(@Param("idList") List<SysRoleDeptUpdateDTO> deptList);

    boolean batchDeleteRoleDeptRef(@Param("idList") List<String> asList);

    boolean updateDataRole(SysDataRoleUpdateDTO setModifiedTime);

    boolean updateMenuRole(SysMenuRoleUpdateDTO setModifiedTime);

    boolean batchAddRolePermissionRefInUpdate(@Param("permissionList") List<SysPermissionUpdateRefDTO> permissionList);

    /**
     * 根据角色编号查询数据角色
     * @param roleId
     * @return
     */
    SysDataRoleQueryDTO queryDataRoleByRoleId(String roleId);

    /**
     * 根据角色编号查询数据角色与部门的关联信息列表
     * @param roleId
     * @return
     */
    List<SysRoleDeptQueryDTO> batchQueryRoleDeptByRoleId(String roleId);

    /**
     * 根据角色编号查询菜单角色
     * @param roleId
     * @return
     */
    SysMenuRoleQueryDTO queryMenuRoleByRoleId(String roleId);

    /**
     * 根据角色编号查询菜单角色与菜单的关联信息列表
     * @param roleId
     * @return
     */
    List<SysMenuRoleRefQueryDTO> batchQueryRoleMenuByRoleId(String roleId);

    List<String> getUserDeptIdList(@Param("idList") List<String> dataRoleIdList);

    List<String> getUserMenuList(@Param("idList") List<String> menuRoleIdList);

    List<SysRole> listData(SysRoleQueryDTO sysRoleQueryDTO);

    List<SysRole> listMenu(SysRoleQueryDTO sysRoleQueryDTO);

    Integer listDataCount(SysRoleQueryDTO sysRoleQueryDTO);

    Integer listMenuCount(SysRoleQueryDTO sysRoleQueryDTO);
}