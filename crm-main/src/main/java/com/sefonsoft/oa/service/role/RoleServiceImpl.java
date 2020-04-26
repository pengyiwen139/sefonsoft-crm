package com.sefonsoft.oa.service.role;

import com.sefonsoft.oa.dao.role.SysRoleDao;
import com.sefonsoft.oa.domain.permission.SysPermissionInsertDTO;
import com.sefonsoft.oa.domain.permission.SysPermissionUpdateRefDTO;
import com.sefonsoft.oa.domain.role.*;
import com.sefonsoft.oa.entity.application.SysApplication;
import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.PermissionConstant.DATA_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.PermissionConstant.MENU_ROLE_TYPE;
import static com.sefonsoft.oa.system.utils.UtilMethod.createFlowNumId;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/9 10:18
 * @description：角色相关的service层
 * @modified By：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public PageResponse list(SysRoleQueryDTO sysRoleQueryDTO) {
        List<SysRole> list = sysRoleDao.list(sysRoleQueryDTO);
        Integer count = sysRoleDao.listCount(sysRoleQueryDTO);
        return new PageResponse<>(count, list);
    }

    @Override
    public List<SysApplication> listApplication(SysApplication sysApplication) {
        return sysRoleDao.listApplication(sysApplication);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMenuRole(SysMenuRoleInsertDTO sysRoleInsertDTO) {
        Date date = new Date();
        List<SysPermissionInsertDTO> permissionList = sysRoleInsertDTO.getPermissionList();
        permissionList.forEach(bean -> bean.setCreateTime(date).setRoleId(sysRoleInsertDTO.getRoleId()));
        boolean refFlag = sysRoleDao.batchAddRolePermissionRef(permissionList);
        boolean roleFlag = sysRoleDao.addMenuRole(sysRoleInsertDTO.setCreateTime(date).setModifiedTime(date));
        return refFlag && roleFlag;
    }

    /**
     * 修改数据角色
     * @param dataRoleUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDataRole(SysDataRoleUpdateDTO dataRoleUpdateDTO) {
        Date date = new Date();
        boolean updateMainFlag = sysRoleDao.updateDataRole(dataRoleUpdateDTO.setModifiedTime(date));
        boolean delFlag = sysRoleDao.batchDeleteRoleDeptRef(Arrays.asList(dataRoleUpdateDTO.getRoleId()));
        List<SysRoleDeptUpdateDTO> deptList = dataRoleUpdateDTO.getDeptList();
        deptList.forEach(bean -> bean.setCreateTime(date).setRoleId(dataRoleUpdateDTO.getRoleId()));
        boolean batchAddRefFlag = sysRoleDao.batchAddRoleDeptRefInUpdate(deptList);
        return updateMainFlag && delFlag && batchAddRefFlag;
    }

    /**
     * 修改数据角色
     * @param menuRoleUpdateDTO
     * @return
     */
    @Override
    public boolean updateMenuRole(SysMenuRoleUpdateDTO menuRoleUpdateDTO) {
        Date date = new Date();
        boolean updateMainFlag = sysRoleDao.updateMenuRole(menuRoleUpdateDTO.setModifiedTime(date));
        boolean delFlag = sysRoleDao.batchDeleteRolePermissionRef(Arrays.asList(menuRoleUpdateDTO.getRoleId()));
        List<SysPermissionUpdateRefDTO> permissionList = menuRoleUpdateDTO.getPermissionList();
        permissionList.forEach(bean->bean.setCreateTime(date).setRoleId(menuRoleUpdateDTO.getRoleId()));
        boolean batchAddRefFlag = sysRoleDao.batchAddRolePermissionRefInUpdate(permissionList);
        return updateMainFlag && delFlag && batchAddRefFlag;
    }

    /**
     * 根据角色编号获取数据角色信息以及其他关联部门信息
     * @return
     */
    @Override
    public SysDataRoleQueryDTO getDataRoleByRoleId(String roleId) {
        SysDataRoleQueryDTO dataRoleQueryDTO = sysRoleDao.queryDataRoleByRoleId(roleId);
        if (ObjTool.isNotNull(dataRoleQueryDTO)) {
            List<SysRoleDeptQueryDTO> deptList = sysRoleDao.batchQueryRoleDeptByRoleId(roleId);
            dataRoleQueryDTO.setDeptList(deptList);
            return dataRoleQueryDTO;
        }
        return null;
    }

    /**
     * 根据角色编号获取菜单角色信息以及其他关联菜单信息
     * @return
     */
    @Override
    public SysMenuRoleQueryDTO getMenuRoleByRoleId(String roleId) {
        SysMenuRoleQueryDTO menuRoleQueryDTO = sysRoleDao.queryMenuRoleByRoleId(roleId);
        if (ObjTool.isNotNull(menuRoleQueryDTO)) {
            List<SysMenuRoleRefQueryDTO> permissionList = sysRoleDao.batchQueryRoleMenuByRoleId(roleId);
            menuRoleQueryDTO.setPermissionList(permissionList);
            return menuRoleQueryDTO;
        }
        return null;
    }

    @Override
    public PageResponse listData(SysRoleQueryDTO sysRoleQueryDTO) {
        sysRoleQueryDTO.setRoleType(DATA_ROLE_TYPE);
        List<SysRole> list = sysRoleDao.listData(sysRoleQueryDTO);
        Integer count = sysRoleDao.listDataCount(sysRoleQueryDTO);
        return new PageResponse<>(count, list);
    }

    @Override
    public PageResponse listMenu(SysRoleQueryDTO sysRoleQueryDTO) {
        sysRoleQueryDTO.setRoleType(MENU_ROLE_TYPE);
        List<SysRole> list = sysRoleDao.listMenu(sysRoleQueryDTO);
        Integer count = sysRoleDao.listMenuCount(sysRoleQueryDTO);
        return new PageResponse<>(count, list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addDataRole(SysDataRoleInsertDTO sysDataRoleInsertDTO) {
        Date date = new Date();
        List<SysRoleDeptInsertDTO> deptList = sysDataRoleInsertDTO.getDeptList();
        deptList.forEach(bean -> bean.setCreateTime(date).setRoleId(sysDataRoleInsertDTO.getRoleId()));
        boolean refFlag = sysRoleDao.batchAddRoleDeptRef(deptList);
        boolean roleFlag = sysRoleDao.addDataRole(sysDataRoleInsertDTO.setCreateTime(date).setModifiedTime(date));
        return refFlag && roleFlag;
    }

    @Override
    public int countRoleByRoleId(String roleId) {
        return sysRoleDao.countRoleByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean edit(SysRoleUpdateDTO roleUpdateDTO) {
        Date date = new Date();
        int update = sysRoleDao.update(roleUpdateDTO.setModifiedTime(date));
        int deleteCount = 0;
        if (ObjTool.isNotNull(roleUpdateDTO.getRoleId())) {
            deleteCount = sysRoleDao.batchDelPermissionRef(roleUpdateDTO.getRoleId());
        }
        List<SysPermissionInsertDTO> permissionList = roleUpdateDTO.getPermissionList();
        permissionList.forEach(bean -> bean.setCreateTime(date));
        boolean addFlag = sysRoleDao.batchAddRolePermissionRef(permissionList);
        return update>0 && deleteCount>0 && addFlag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<String> idList) {
        boolean deleteRoleFlag = sysRoleDao.batchDeleteById(idList);
        boolean deleteMenuRefFlag = sysRoleDao.batchDeleteRolePermissionRef(idList);
        boolean deleteDataRefFlag = sysRoleDao.batchDeleteRoleDeptRef(idList);
        return deleteRoleFlag && (deleteMenuRefFlag || deleteDataRefFlag);
    }

    @Override
    public List<String> batchGetRoleIdById(String[] idList) {
        return sysRoleDao.batchGetRoleIdById(idList);
    }

    @Override
    public int countInUsed(List<String> roleIdList) {
        return sysRoleDao.countInUsed(roleIdList);
    }


    /**
     * 生成roleId，根据自定义规则
     * @return
     */
    @Override
    public String generateRoleId() {
        String maxRoleId = sysRoleDao.getMaxRoleId();
        return createFlowNumId(maxRoleId);
    }

}