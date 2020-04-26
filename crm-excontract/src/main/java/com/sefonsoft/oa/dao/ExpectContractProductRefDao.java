package com.sefonsoft.oa.dao;

import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 合同预测产品金额关联表(ExpectContractProductRef)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-17 16:23:26
 */
@Mapper
public interface ExpectContractProductRefDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExpectContractProductRef queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExpectContractProductRef> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param expectContractProductRef 实例对象
     * @return 对象列表
     */
    List<ExpectContractProductRef> queryAll(ExpectContractProductRef expectContractProductRef);

    /**
     * 新增数据
     *
     * @param expectContractProductRef 实例对象
     * @return 影响行数
     */
    int insert(ExpectContractProductRef expectContractProductRef);

    /**
     * 修改数据
     *
     * @param expectContractProductRef 实例对象
     * @return 影响行数
     */
    int update(ExpectContractProductRef expectContractProductRef);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}