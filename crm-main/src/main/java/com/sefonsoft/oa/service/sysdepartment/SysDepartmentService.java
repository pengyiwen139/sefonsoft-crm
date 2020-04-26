package com.sefonsoft.oa.service.sysdepartment;

import com.sefonsoft.oa.domain.sysdepartment.SysDepartmentQueryDTO;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;

import java.util.List;
import java.util.Map;

/**
 * (SysDepartment)表服务接口
 *
 * @author Aron
 * @since 2019-11-01 16:11:07
 */
public interface SysDepartmentService {



    /**
     * 查询部门管理树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectDeptTree(SysDepartment dept);

    /**
     * 查询销售树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectSaleDeptTree(SysDepartment dept);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDepartment queryById(Long id);


    /**
     * 查询部门数
     *
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(String parentId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDepartment> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    boolean insert(SysDepartment sysDepartment);

    /**
     * 修改数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    int update(SysDepartment sysDepartment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询SysDepartment列表
     *
     * @param sysDepartmentQueryDTO 查询条件
     * @return
     */
    List<SysDepartmentQueryDTO> getCondition(SysDepartmentQueryDTO sysDepartmentQueryDTO);

    /**
     * 查询SysDepartment总数
     *
     * @param sysDepartmentQueryDTO 查询条件
     * @return
     */
    int findCondition(SysDepartmentQueryDTO sysDepartmentQueryDTO);


    /**
     * 校验部门名称是否唯一
     * @param parentId
     * @param deptName
     * @return 结果
     */
    public int checkDeptNameUnique(String parentId, String deptName);

    /**
     * 校验部门编号是否唯一
     * @param deptId
     * @return 结果
     */
    public int checkDeptIdUnique(String deptId);

    /**
     * 获取部门表最大部门编号
     * @param
     * @return 结果
     */
    public String getMaxDeptId();

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门编号
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(String deptId);





}