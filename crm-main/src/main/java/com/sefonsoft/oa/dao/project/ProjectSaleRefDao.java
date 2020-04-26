package com.sefonsoft.oa.dao.project;

import com.sefonsoft.oa.domain.project.ProjectSaleRefUpdateDTO;
import com.sefonsoft.oa.entity.project.ProjectSaleRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProjectSaleRef)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-11-16 09:59:37
 */
public interface ProjectSaleRefDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectSaleRef queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProjectSaleRef> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param projectSaleRef 实例对象
     * @return 对象列表
     */
    List<ProjectSaleRef> queryAll(ProjectSaleRef projectSaleRef);

    /**
     * 新增数据
     *
     * @param projectSaleRef 实例对象
     * @return 影响行数
     */
    boolean insert(ProjectSaleRef projectSaleRef);

    /**
     * 修改数据
     *
     * @param projectSaleRef 实例对象
     * @return 影响行数
     */
    boolean update(ProjectSaleRef projectSaleRef);

    /**
     * 通过主键删除数据
     *
     * @param projectId
     * @return 影响行数
     */
    boolean deleteById(String projectId);

    boolean batchUpdate(ProjectSaleRefUpdateDTO projectInfoUpdateDTO);

    ProjectSaleRef queryByProjectId(String projectId);
        }