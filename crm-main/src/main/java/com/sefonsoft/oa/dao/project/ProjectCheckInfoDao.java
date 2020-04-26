package com.sefonsoft.oa.dao.project;

import com.sefonsoft.oa.entity.project.ProjectCheckInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ProjectCheckInfoInsertDTO)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-03 17:21:50
 */
public interface ProjectCheckInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectCheckInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProjectCheckInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param projectCheckInfo 实例对象
     * @return 对象列表
     */
    List<ProjectCheckInfo> queryAll(ProjectCheckInfo projectCheckInfo);

    /**
     * 新增数据
     *
     * @param projectCheckInfo 实例对象
     * @return 影响行数
     */
    int insert(ProjectCheckInfo projectCheckInfo);

    /**
     * 修改数据
     *
     * @param projectCheckInfo 实例对象
     * @return 影响行数
     */
    int update(ProjectCheckInfo projectCheckInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过主projectId删除数据
     *
     * @param projectId
     * @return 影响行数
     */
    int deleteByIds(String projectId);

    Integer getStatusByProjectId(String projectId);

    void updateCheckStatus2UnCheck(@Param("projectId") String projectId, @Param("projectPreCheckStatus") int projectPreCheckStatus);

    Integer getCheckStatusByProjectId(String projectId);

    void deleteCheck(String projectId, String employeeId);
}