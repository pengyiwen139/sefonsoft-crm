package com.sefonsoft.oa.dao.sysdepartment;

import com.sefonsoft.oa.domain.sysdepartment.SysDepartmentQueryDTO;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysDepartment)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-01 16:11:07
 */
public interface SysDepartmentDao {



    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDepartment> selectDeptList(SysDepartment dept);
    /**
     * 查询销售部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDepartment> selectSaleDeptList(SysDepartment dept);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDepartment queryById(Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param deptId 主键
     * @return 实例对象
     */
    SysDepartment selectByDeptId(String deptId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDepartment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDepartment 实例对象
     * @return 对象列表
     */
    List<SysDepartment> queryAll(SysDepartment sysDepartment);

    /**
     * 新增数据
     *
     * @param sysDepartment 实例对象
     * @return 影响行数
     */
    boolean insert(SysDepartment sysDepartment);

    /**
     * 修改数据
     *
     * @param sysDepartment 实例对象
     * @return 影响行数
     */
    int update(SysDepartment sysDepartment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


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
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDepartment> depts);


    /**
     * 查询部门数
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(SysDepartment dept);


    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public int checkDeptNameUnique( @Param("parentId") String parentId,@Param("deptName") String deptName);

    /**
     * 校验部门编号是否唯一
     * @param deptId
     * @return 结果
     */
    public int checkDeptIdUnique(@Param("deptId") String deptId);

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
     * @return 结果
     */
    public int checkDeptExistUser(String deptId);


    List<String> queryDeptIdByDeptName(String deptName);
}