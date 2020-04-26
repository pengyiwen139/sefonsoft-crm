package com.sefonsoft.oa.service.role;

import com.sefonsoft.oa.domain.role.*;
import com.sefonsoft.oa.entity.application.SysApplication;
import com.sefonsoft.oa.system.utils.PageResponse;

import java.util.List;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/9 10:17
 * @description：
 * @modified By：
 */
public interface RoleService {

    PageResponse list(SysRoleQueryDTO sysRoleQueryDTO);

    List<SysApplication> listApplication(SysApplication sysApplication);

    boolean addDataRole(SysDataRoleInsertDTO sysRoleInsertDTO);

    int countRoleByRoleId(String roleId);

    boolean edit(SysRoleUpdateDTO sysRoleInsertDTO);

    boolean delete(List<String> idList);

    List<String> batchGetRoleIdById(String[] idList);

    int countInUsed(List<String> roleIdList);

    /**
     * 生成roleId，根据自定义规则
     * @return
     */
    String generateRoleId();

    boolean addMenuRole(SysMenuRoleInsertDTO sysRoleInsertDTO);

    /**
     * 修改数据角色
     * @param dataRoleUpdateDTO
     * @return
     */
    boolean updateDataRole(SysDataRoleUpdateDTO dataRoleUpdateDTO);

    /**
     * 修改操作角色
     * @param menuRoleUpdateDTO
     * @return
     */
    boolean updateMenuRole(SysMenuRoleUpdateDTO menuRoleUpdateDTO);

    /**
     * 根据角色编号获取数据角色信息以及其他关联部门信息
     * @return
     */
    SysDataRoleQueryDTO getDataRoleByRoleId(String roleId);

    /**
     * 根据角色编号获取菜单角色信息以及其他关联菜单信息
     * @return
     */
    SysMenuRoleQueryDTO getMenuRoleByRoleId(String roleId);

    PageResponse listData(SysRoleQueryDTO sysRoleQueryDTO);

    PageResponse listMenu(SysRoleQueryDTO sysRoleQueryDTO);
}