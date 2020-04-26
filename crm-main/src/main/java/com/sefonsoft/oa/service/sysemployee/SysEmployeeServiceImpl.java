package com.sefonsoft.oa.service.sysemployee;

import com.sefonsoft.oa.dao.role.SysRoleDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.dao.user.SysUserDao;
import com.sefonsoft.oa.domain.role.SysRoleUserDTO;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.domain.user.vo.PretaskType;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.entity.user.SysUser;
import com.sefonsoft.oa.system.utils.MD5;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.constant.PermissionConstant.DATA_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.PermissionConstant.MENU_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.UserConstant.MAKE_ACCOUNT_FLAG;
import static com.sefonsoft.oa.system.constant.UserConstant.NOT_MAKE_ACCOUNT_FLAG;

/**
 * (SysEmployee)表服务实现类
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
@Service("sysEmployeeService")
@Transactional(rollbackFor = Exception.class)
public class SysEmployeeServiceImpl implements SysEmployeeService {
    @Resource
    private SysEmployeeDao sysEmployeeDao;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysRoleDao sysRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysEmployee queryById(Long id) {
        return this.sysEmployeeDao.queryById(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    @Override
    public SysEmployee queryByEmployeeId(String employeeId) {
        return this.sysEmployeeDao.queryByIds(employeeId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysEmployee> queryAllByLimit(int offset, int limit) {
        return this.sysEmployeeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(SysEmployee sysEmployee) {
        Date date = new Date();

        Integer hasMakeAccount = sysEmployee.getHasMakeAccount();
        //如果开通账号，则向sys_user插入信息
        if (ObjTool.isNotNull(hasMakeAccount) && hasMakeAccount.equals(MAKE_ACCOUNT_FLAG)) {
            insertUserInfo(sysEmployee);
        }

        sysEmployee.setModifiedTime(date);
        sysEmployee.setCreateTime(date);
        boolean flag = sysEmployeeDao.insert(sysEmployee);

        addUserRoleRef(sysEmployee);
        
        // 确认电话 & 邮箱 任务
        Pretask task = new Pretask();
        task.setUserId(sysEmployee.getEmployeeId());
        task.setBlock(true);
        task.setArgs(null);
        task.setTaskType(PretaskType.CHECK_PROFILE);
        sysUserDao.insertPretask(task);

        return flag;
    }

    /**
     * 根据员工信息插入登录账号信息
     * @param sysEmployee
     * @return
     */
    public boolean insertUserInfo(SysEmployee sysEmployee) {
        Date date = new Date();
        SysUser user = new SysUser();
        user.setUserId(sysEmployee.getEmployeeId());
        user.setNickName(sysEmployee.getEmployeeName());
        String password = "123456";
        password = MD5.crypt(password);
        user.setPassword(password);
        user.setEnabled(1);
        user.setModifiedTime(date);
        user.setCreateTime(date);
        return sysUserDao.insert(user);
    }

    /**
     * 添加员工与角色的关联信息
     * @Author pengyiwen
     * @param sysEmployee
     * @return
     */
    public boolean addUserRoleRef(SysEmployee sysEmployee) {
        //信息插入用户角色关联表
        List<String> dataRoleIdList = sysEmployee.getDataRoleIdList();
        List<String> menuRoleIdList = sysEmployee.getMenuRoleIdList();
        List<String> list = new ArrayList<>();
        if (ObjTool.isNotNull(dataRoleIdList)) {
            list.addAll(dataRoleIdList);
        }
        if (ObjTool.isNotNull(menuRoleIdList)) {
            list.addAll(menuRoleIdList);
        }
        if (ObjTool.isNotNull(list)) {
            return sysUserDao.batchInsertUserRoleRef(sysEmployee.getEmployeeId(), list);
        }
        return true;
    }

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(SysEmployee sysEmployee) {
        Date date = new Date();

        sysEmployee.setModifiedTime(date);

        //查询出之前的员工编号是多少
        SysEmployee sysEmployeeHistory = sysEmployeeDao.queryById(sysEmployee.getId());
        String historyEmployeeId = sysEmployeeHistory.getEmployeeId();
        //如果未查询到员工编号，则返回false
        if (!ObjTool.isNotNull(historyEmployeeId)) {
            return false;
        }
        //是否需要有登录账号
        Integer hasMakeAccount = sysEmployee.getHasMakeAccount();
        //如果不需要登录账号，则根据上面的employeeId删除掉登录账号表信息sys_user
        if (!ObjTool.isNotNull(hasMakeAccount) || hasMakeAccount.equals(NOT_MAKE_ACCOUNT_FLAG)) {
            sysUserDao.deleteByUserId(historyEmployeeId);
        } else {
            //需要登录账号，若查询之前有sys_user的信息，则只同步员工的employeeId到相关联的sys_user中,若查询之前没有登录账号，则新创建一个登录账号
            Long id = sysUserDao.getIdByUserId(historyEmployeeId);
            if (ObjTool.isNotNull(id)) {

                sysUserDao.updateUserIdById(id, sysEmployee.getEmployeeId());
                SysUser sysUser = new SysUser();
                sysUser.setId(id);
                sysUser.setNickName(sysEmployee.getEmployeeName());
                sysUserDao.update(sysUser);

            } else {
                insertUserInfo(sysEmployee);
            }
        }

        int result = sysEmployeeDao.update(sysEmployee);

        //删除当前用户所有角色
        int delRoleRefFlag = sysUserDao.deleteUserRoleByUserId(sysEmployee.getEmployeeId());

        //信息插入用户角色关联表
        addUserRoleRef(sysEmployee);

        return result>0;

    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysEmployeeDao.deleteById(id) > 0;
    }
    /**
     * 通过主键删除数据
     * @param employeeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByEmployeeId(String employeeId) {

        //删除用户表信息
        sysUserDao.deleteByUserId(employeeId);
        //删除角色表信息
        sysUserDao.deleteUserRoleByUserId(employeeId);
        //删除员工表信息
        return  sysEmployeeDao.deleteByEmployeeId(employeeId)>0;
    }

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 是否成功
     */
    @Override
    public boolean deleteByIds(List<String> ids) {
        //删除用户表信息
        sysEmployeeDao.deleteByUserIds(ids);
        //删除角色表信息
        sysEmployeeDao.deleteUserRoleByUserIds(ids);
        //删除员工表信息
        return sysEmployeeDao.deleteByEmployeeIds(ids) > 0;
    }

    @Override
    public List<SysEmployeeQueryDTO> getCondition(SysEmployeeQueryDTO employeeQueryDTO) {
        List<SysEmployeeQueryDTO> list = sysEmployeeDao.getCondition(employeeQueryDTO);
        //设置是否含有账号的属性
        if (ObjTool.isNotNull(list)) {
            list.stream().forEach(employeeDO ->{
                String userId = employeeDO.getUserId();
                if (ObjTool.isNotNull(userId)) {
                    employeeDO.setHasMakeAccount(MAKE_ACCOUNT_FLAG);
                } else {
                    employeeDO.setHasMakeAccount(NOT_MAKE_ACCOUNT_FLAG);
                }
            });
        }
        //获取角色,并设置进员工列表中
        list = getSetRole2Employees(list);
        return list;
    }

    @Override
    public List<SysEmployeeQueryDTO> getConditionList(List<String> dataDeptIdList,SysEmployeeQueryDTO employeeQueryDTO) {
        return sysEmployeeDao.getConditionListSupportLeave(dataDeptIdList,employeeQueryDTO);
    }

    private List<SysEmployeeQueryDTO> getSetRole2Employees(List<SysEmployeeQueryDTO> list) {
        if (ObjTool.isNotNull(list)) {
            List<String> userIdList = list.stream().map(SysEmployeeQueryDTO::getEmployeeId).collect(Collectors.toList());
            List<SysRoleUserDTO> userRoleList = sysRoleDao.queryUserRoleByUserIdList(userIdList);
            if (ObjTool.isNotNull(userRoleList)) {
                list.stream().forEach(employeeDO ->{
                    String employeeId = employeeDO.getEmployeeId();
                    List<String> dataRoleIdList = new ArrayList<>();
                    List<String> menuRoleIdList = new ArrayList<>();
                    for (SysRoleUserDTO roleUserDTO : userRoleList) {
                        String userId = roleUserDTO.getUserId();
                        Integer roleType = roleUserDTO.getRoleType();
                        String roleId = roleUserDTO.getRoleId();
                        if (ObjTool.isNotNull(userId, roleType) && userId.equals(employeeId)) {
                            if (roleType.equals(DATA_ROLE_TYPE)) {
                                dataRoleIdList.add(roleId);
                            } else if (roleType.equals(MENU_ROLE_TYPE)) {
                                menuRoleIdList.add(roleId);
                            }
                        }
                    }
                    employeeDO.setDataRoleIdList(dataRoleIdList);
                    employeeDO.setMenuRoleIdList(menuRoleIdList);
                });
            }
        }
        return list;
    }

    @Override
    public int findCondition(SysEmployeeQueryDTO employeeQueryDTO) {
        return sysEmployeeDao.findCondition(employeeQueryDTO);
    }

    @Override
    public int checkUnique(String employeeId) {

        int count = sysEmployeeDao.checkUnique(employeeId);
        return count;
    }

    @Override
    public List<SysEmployeeQueryDTO> employeeList(SysEmployeeQueryDTO employeeQueryDTO) {
        List<SysEmployeeQueryDTO> list = sysEmployeeDao.getList(employeeQueryDTO);
        return list;
    }

    @Override
    public List<SysEmployee> getApproverList(String deptId,String per) {
        /**
         * 1、获取当前部门有数据权限的人员
         */
        List<String> dataDeptUser = sysUserDao.getDataDeptUser(deptId);
        /**
         * 2、获取指定权限下的操作人员
         */
        List<String> permissionUser = sysUserDao.getPermissionUser(per);
        /**
         * 获取交集
         */
        dataDeptUser.retainAll(permissionUser);

        return sysEmployeeDao.getApproverList(dataDeptUser);
    }

}