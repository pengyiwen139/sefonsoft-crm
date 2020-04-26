package com.sefonsoft.oa.dao.user;

import com.sefonsoft.oa.controller.user.CheckProfilesVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.user.SysUserQueryDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.domain.user.vo.PretaskType;
import com.sefonsoft.oa.entity.user.SysUser;
import com.sefonsoft.oa.entity.user.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
public interface SysUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    boolean insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<String> ids);


    /**
     * 校验员工编号是否唯一
     * @param employeeId
     * @return 结果
     */
    public int checkUnique(@Param("employeeId") String employeeId);

    /**
     * 查询用户列表
     *
     * @param sysUserQueryDTO 查询条件
     * @return
     */
    List<SysUserQueryDTO> getCondition(SysUserQueryDTO sysUserQueryDTO);

    /**
     * 查询用户总数
     *
     * @param sysUserQueryDTO 查询条件
     * @return
     */
    int findCondition(SysUserQueryDTO sysUserQueryDTO);


    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
     boolean batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 通过用户编号删除用户和角色关联
     *
     * @param userId 用户编号
     * @return 结果
     */
    int deleteUserRoleByUserId(String userId);


    LoginUserDTO getSimpleLoginInfo(LoginUserDTO loginUser);

    String getDbPwdByUserId(String employeeId);

    boolean resetPwd(@Param("userId") String userId, @Param("password") String password);

    String getUserIdByUserName(String name);

    void setLoginTime(@Param("userId")String userId, @Param("date")Date date);

    boolean batchInsertUserRoleRef(@Param("userId") String userId, @Param("list") List<String> list);

    boolean deleteByUserId(String employeeId);

    Long getIdByUserId(String historyEmployeeId);

    boolean updateUserIdById(@Param("id") Long id, @Param("employeeId") String employeeId);

    List<String> getUserIdByUserNameAndDeptId(@Param("employeeName") String employeeName, @Param("deptId") String deptId);

    List<String> getUserIdListByUserName(String userName);

    /**
     *查询部门权限用户
     * @param deptId
     * @author nipengcheng
     * @return
     * @date 2020/3/20 15:14
     */
    List<String> getDataDeptUser(String deptId);

    /**
     *获取指定权限下的用户
     * @param
     * @author nipengcheng
     * @return
     * @date 2020/3/20 15:17
     */
    List<String> getPermissionUser(String per);

    /**
     * 检查是前置任务
     * @param userid
     * @return
     */
    List<Pretask> getPretask(@Param("userId") String userid);

    /**
     * 删除前置任务
     * @param userId
     * @param checkProfile
     */
    void deletePretask(@Param("userId") String userId, @Param("taskType") String taskType);

    /**
     * 确认基础信息
     * @param sysUser
     */
    void checkProfile(CheckProfilesVO sysUser);

    /**
     * 新增前置任务
     * @param task
     */
    void insertPretask(Pretask task);

}