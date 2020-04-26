package com.sefonsoft.oa.dao.workorder;

import com.sefonsoft.oa.domain.workorder.deptconfig.DeptNamePair;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigInsertDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigQueryDTO;
import com.sefonsoft.oa.domain.workorder.deptconfig.WorkorderDeptConfigUpdateDTO;
import com.sefonsoft.oa.entity.workorder.WorkorderDeptConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (WorkorderDeptConfigQueryDTO)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-01 17:25:24
 */
@Mapper
public interface WorkorderDeptConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WorkorderDeptConfig queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WorkorderDeptConfigQueryDTO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param workorderDeptConfig 实例对象
     * @return 对象列表
     */
    List<WorkorderDeptConfigQueryDTO> queryAll(WorkorderDeptConfigQueryDTO workorderDeptConfig);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param workorderDeptConfig 实例对象
     * @return 对象列表
     */
    List<WorkorderDeptConfigQueryDTO> queryAllPage(WorkorderDeptConfigQueryDTO workorderDeptConfig);


    /**
     * 个数计算
     *
     * @return 对象列表
     */
    int count(WorkorderDeptConfigQueryDTO workorderDeptCfgDO);

    /**
     * 新增数据
     *
     * @param workorderDeptConfig 实例对象
     * @return 影响行数
     */
    boolean insert(@Param("workorderDeptConfig") WorkorderDeptConfigInsertDTO workorderDeptConfig, @Param("userId") String userId);

    /**
     * 修改数据
     *
     * @param workorderDeptConfig 实例对象
     * @return 影响行数
     */
    boolean update(@Param("workorderDeptConfig") WorkorderDeptConfigUpdateDTO workorderDeptConfig, @Param("userId") String userId);

    /**
     * 通过主键删除数据
     *
     * @param idArray 主键
     * @return 影响行数
     */
    boolean deleteById(@Param("idArray") Long[] idArray);

    List<DeptNamePair> getDeptListByParentDeptId(String deptId);
    
    List<DeptNamePair> getResourceDeptListByParentDeptId(String deptId);

}