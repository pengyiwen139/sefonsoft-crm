package com.sefonsoft.oa.service.user;

import com.sefonsoft.oa.controller.user.CheckProfilesVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.user.SysUserQueryDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.entity.user.SysUser;

import java.util.List;

/**
 * (SysUser)表服务接口
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
public interface SysUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    boolean insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int update(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int checkProfile(CheckProfilesVO sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 是否成功
     */
    boolean deleteByIds(List<String> ids);

    /**
     * 校验员工股编号是否唯一
     * @param employeeId
     * @return 结果
     */
    public int checkUnique(String employeeId);

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

    LoginUserDTO getSimpleLoginInfo(LoginUserDTO loginUser);

    String getDbPwdByUserId(String employeeId);

    boolean resetPwd(String userId);

    void setLoginTime(String userId);

    String getUserIdByUserName(String employeeName);

    /**
     * 获取我的前置任务
     * @param userId 用户工号
     * @return
     */
    List<Pretask> getPretask(String userId);
}