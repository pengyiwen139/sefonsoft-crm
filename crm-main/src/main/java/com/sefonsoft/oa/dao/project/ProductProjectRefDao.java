package com.sefonsoft.oa.dao.project;

import com.sefonsoft.oa.domain.project.ProductProjectRefInsertDTO;
import com.sefonsoft.oa.domain.project.ProductProjectRefUpdateDTO;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ProductProjectRefInsertDTO)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-03 17:25:53
 */
public interface ProductProjectRefDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductProjectRef queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductProjectRef> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param productProjectRef 实例对象
     * @return 对象列表
     */
    List<ProductProjectRef> queryAll(ProductProjectRef productProjectRef);

    /**
     * 新增数据
     *
     * @param productProjectRef 实例对象
     * @return 影响行数
     */
    int insert(ProductProjectRef productProjectRef);

    /**
     * 新增数据
     *
     * @param list 实例对象
     * @return 影响行数
     */
    int batchInsert(List<ProductProjectRefInsertDTO> list);

    /**
     * 修改数据
     *
     * @param productProjectRef 实例对象
     * @return 影响行数
     */
    int update(ProductProjectRef productProjectRef);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过projectId删除数据
     *
     * @param projectId
     * @return 影响行数
     */
    int deleteByIds(String projectId);


    int batchInsertByUpdate(@Param("list") List<ProductProjectRefUpdateDTO> productRefUpdateDTOList);

    List<ProductProjectRef> querybyProjectId(String projectId);
}