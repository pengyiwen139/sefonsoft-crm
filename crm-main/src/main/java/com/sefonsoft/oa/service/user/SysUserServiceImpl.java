package com.sefonsoft.oa.service.user;

import com.sefonsoft.oa.controller.user.CheckProfilesVO;
import com.sefonsoft.oa.dao.role.SysRoleDao;
import com.sefonsoft.oa.dao.user.SysUserDao;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.user.SysUserQueryDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.domain.user.vo.PretaskType;
import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.entity.user.SysUser;
import com.sefonsoft.oa.entity.user.SysUserRole;
import com.sefonsoft.oa.system.utils.MD5;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.constant.PermissionConstant.DATA_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.PermissionConstant.MENU_ROLE_TYPE;
import static com.sefonsoft.oa.system.constant.UserConstant.ORIGIN_PASSWORD;


/**
 * (SysUser)表服务实现类
 *
 * @author Aron
 * @since 2019-11-06 17:51:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

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
    public SysUser queryById(Long id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(SysUser sysUser) {

        //对密码进行md5加密
        sysUser = crptPwd(sysUser);
        Date date = new Date();
        sysUser.setCreateTime(date);
        sysUser.setModifiedTime(date);
        sysUser.setEnabled(1);
        boolean flag1 = sysUserDao.insert(sysUser);
        // 新增用户与角色管理
        boolean flag2=insertUserRole(sysUser);
        
        // 确认电话 & 邮箱 任务
        Pretask task = new Pretask();
        task.setUserId(sysUser.getUserId());
        task.setBlock(true);
        task.setArgs(null);
        task.setTaskType(PretaskType.CHECK_PROFILE);
        sysUserDao.insertPretask(task);

        return flag1&flag2;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public boolean insertUserRole(SysUser user)
    {
        String[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
             return sysUserDao.batchUserRole(list);
            }
        }
        return false;
    }


    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysUser sysUser) {
        //通过修改主键id获取对象信息
        SysUser user = sysUserDao.queryById(sysUser.getId());
        //String userId = user.getUserId();
        // 删除用户与角色关联
        //sysUserDao.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        //insertUserRole(sysUser);
        Date date = new Date();
        sysUser.setModifiedTime(date);

        int result = sysUserDao.update(sysUser);

        return result;
    }
    
    @Override
    @Transactional
    public int checkProfile(CheckProfilesVO sysUser) {
      sysUserDao.checkProfile(sysUser);
      sysUserDao.deletePretask(sysUser.getUserId(), PretaskType.CHECK_PROFILE.toString());
      return 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 是否成功
     */
    @Override
    public boolean deleteByIds(List<String> ids) {
        return sysUserDao.deleteByIds(ids) > 0;
    }
    /**
     * 校验员工股编号是否唯一
     * @param employeeId
     * @return 结果
     */
    @Override
    public int checkUnique(String employeeId) {
        int count = sysUserDao.checkUnique(employeeId);
        return count;
    }

    @Override
    public List<SysUserQueryDTO> getCondition(SysUserQueryDTO sysUserQueryDTO) {

        return sysUserDao.getCondition(sysUserQueryDTO);
    }

    @Override
    public int findCondition(SysUserQueryDTO sysUserQueryDTO) {
        return sysUserDao.findCondition(sysUserQueryDTO);
    }

    @Override
    public LoginUserDTO getSimpleLoginInfo(LoginUserDTO loginUser) {
        //获取登录数据
        LoginUserDTO simpleLoginInfo = sysUserDao.getSimpleLoginInfo(loginUser);
        if (ObjTool.isNotNull(simpleLoginInfo)) {
            //判断是否修改过密码(和原密码123456比较)
            String originPwd = MD5.crypt(ORIGIN_PASSWORD);
            String dbPwd = simpleLoginInfo.getPassword();
            if (originPwd.equals(dbPwd)) {
                //未修改过密码
                simpleLoginInfo.setHasChangePwd("1");
            } else {
                //已修改过密码
                simpleLoginInfo.setHasChangePwd("2");
            }

            //获取用户权限角色相关的数据,并设置进simpleUserInfo中
            simpleLoginInfo = getSetRoleAndPermission2LoginUserInfo(simpleLoginInfo);

            return simpleLoginInfo;
        }
        return null;
    }

    /**
     * 获取用户权限角色相关的数据,并设置进simpleUserInfo中
     * @param simpleLoginInfo
     * @return
     */
    LoginUserDTO getSetRoleAndPermission2LoginUserInfo(LoginUserDTO simpleLoginInfo) {
        //获取操作角色编号列表
        List<SysRole> sysRoles = sysRoleDao.queryUserRole(simpleLoginInfo.getUserId());
        if (ObjTool.isNotNull(sysRoles)) {
            List<SysRole> userDataRoleList = new ArrayList<>();
            List<SysRole> userMenuRoleList = new ArrayList<>();
            List<String> userDeptIdList = new ArrayList<>();
            List<String> userMenuIdList = new ArrayList<>();
            sysRoles.forEach(sysRole -> {
                Integer roleType = sysRole.getRoleType();
                if (ObjTool.isNotNull(roleType)) {
                    if (roleType.equals(DATA_ROLE_TYPE)) {
                        userDataRoleList.add(sysRole);
                    } else if (roleType.equals(MENU_ROLE_TYPE)) {
                        userMenuRoleList.add(sysRole);
                    }
                }
            });

            //获取当前用户的数据角色下的部门编号列表
            if (ObjTool.isNotNull(userDataRoleList)) {
                List<String> dataRoleIdList = userDataRoleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
                if (ObjTool.isNotNull(dataRoleIdList)) {
                    userDeptIdList = sysRoleDao.getUserDeptIdList(dataRoleIdList);
                }
            }

            //当前用户的菜单列表
            if (ObjTool.isNotNull(userMenuRoleList)) {
                List<String> menuRoleIdList = userMenuRoleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
                if (ObjTool.isNotNull(menuRoleIdList)) {
                    userMenuIdList = sysRoleDao.getUserMenuList(menuRoleIdList);
                    Set<String> hashSet = new HashSet<>(userMenuIdList);
                    userMenuIdList = new ArrayList<>(hashSet);
                }
            }

            simpleLoginInfo.setDataRoleList(userDataRoleList).setMenuRoleList(userMenuRoleList).setMenuList(userMenuIdList).setDataDeptIdList(userDeptIdList);
        }
        return simpleLoginInfo;
    }

    @Override
    public String getDbPwdByUserId(String employeeId) {
        return sysUserDao.getDbPwdByUserId(employeeId);
    }

    @Override
    public boolean resetPwd(String userId) {
        return sysUserDao.resetPwd(userId, MD5.crypt(ORIGIN_PASSWORD));
    }

    @Override
    public void setLoginTime(String userId) {
        Date date = new Date();
        sysUserDao.setLoginTime(userId, date);
    }

    @Override
    public String getUserIdByUserName(String employeeName) {
        return sysUserDao.getUserIdByUserName(employeeName);
    }

    /**
     * 对登录密码进行md5加密
     */
    private SysUser crptPwd(SysUser sysUser) {
        if (ObjTool.isNotNull(sysUser)) {
            String password = sysUser.getPassword();
            if (ObjTool.isNotNull(password)) {
                password = MD5.crypt(password);
                sysUser.setPassword(password);
            }
        }
        return sysUser;
    }

    @Override
    public List<Pretask> getPretask(String userid) {
      return sysUserDao.getPretask(userid);
    }
}