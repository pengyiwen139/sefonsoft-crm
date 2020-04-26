package com.sefonsoft.oa.service.sysemployee;

import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;

import java.util.List;

/**
 * (SysEmployee)表服务接口
 *
 * @author Aron
 * @since 2019-11-05 15:45:34
 */
public interface SysEmployeeService {

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
    SysEmployee queryByEmployeeId(String employeeId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysEmployee> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    boolean insert(SysEmployee sysEmployee);

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    boolean update(SysEmployee sysEmployee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过主键删除数据
     *
     * @param employeeId
     * @return 是否成功
     */
    boolean deleteByEmployeeId(String employeeId);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 是否成功
     */
    boolean deleteByIds(List<String> ids);

    /**
     * 查询员工列表
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getCondition(SysEmployeeQueryDTO employeeQueryDTO);

    /**
     * 查询员工列表
     *
     * @param dataDeptIdList 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> getConditionList(List<String> dataDeptIdList,SysEmployeeQueryDTO employeeQueryDTO);

    /**
     * 查询员工列表
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    List<SysEmployeeQueryDTO> employeeList(SysEmployeeQueryDTO employeeQueryDTO);


    /**
     * 查询员工总数
     *
     * @param employeeQueryDTO 查询条件
     * @return
     */
    int findCondition(SysEmployeeQueryDTO employeeQueryDTO);

    /**
     * 校验员工股编号是否唯一
     * @param employeeId
     * @return 结果
     */
    public int checkUnique(String employeeId);

   /**
    *获取派工单审批人
    * @param
    * @author nipengcheng
    * @return
    * @date 2020/3/20 14:38
    */
    List<SysEmployee> getApproverList(String deptId,String per);

}