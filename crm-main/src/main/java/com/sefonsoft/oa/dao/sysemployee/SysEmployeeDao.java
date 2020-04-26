package com.sefonsoft.oa.dao.sysemployee;

import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (SysEmployee)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
public interface SysEmployeeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysEmployee queryById(Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    SysEmployee queryByIds(String employeeId);

    /**
     *
     * @param employeeIds
     * @return
     */
    List<SysEmployee> queryByEmployeeIds(@Param("employeeIds") Set<String> employeeIds);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysEmployee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysEmployee 实例对象
     * @return 对象列表
     */
    List<SysEmployee> queryAll(SysEmployee sysEmployee);

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 影响行数
     */
    boolean insert(SysEmployee sysEmployee);

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 影响行数
     */
    int update(SysEmployee sysEmployee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 通过主键删除数据
     *
     * @param employeeId
     * @return 是否成功
     */
    int deleteByEmployeeId(String employeeId);



    /**
     * 批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<String> ids);

    /**
     * 批量删除员工数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByEmployeeIds(@Param("ids") List<String> ids);

    /**
     * 批量删除用户数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByUserIds(@Param("ids") List<String> ids);

    /**
     * 批量删除用户和角色数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteUserRoleByUserIds(@Param("ids") List<String> ids);



    /**
     * 查询员工列表
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getCondition(SysEmployeeQueryDTO employeeQueryDTO);

    /**
     * 查询员工总数
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    int findCondition(SysEmployeeQueryDTO employeeQueryDTO);

    /**
     * 校验员工编号是否唯一
     * @param employeeId
     * @return 结果
     */
    public int checkUnique(@Param("employeeId") String employeeId);


    /**
     * 查询员工列表
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getList(SysEmployeeQueryDTO employeeQueryDTO);


    /**
     * 查询员工列表
     *
     * @param dataDeptIdList 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getConditionList(@Param("dataDeptIdList") List<String> dataDeptIdList,@Param("employeeQueryDTO")SysEmployeeQueryDTO employeeQueryDTO);


    List<SysEmployeeQueryDTO> getConditionListSupportLeave(@Param("dataDeptIdList") List<String> dataDeptIdList,@Param("employeeQueryDTO")SysEmployeeQueryDTO employeeQueryDTO);


    /**
     * 查询员工列表，包括已离职的, 离职的员工，按时间区间查
     *
     * @param dataDeptIdList 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getAllEmployeeInDepts(@Param("dataDeptIdList") List<String> dataDeptIdList, @Param("startDate") String startDate, @Param("endDate")String endDate);

    /**
     * 根据员工id获取员工信息
     * @param userIds
     * @return
     */
    List<SysEmployee> getApproverList(@Param("userIds") List<String> userIds);


}