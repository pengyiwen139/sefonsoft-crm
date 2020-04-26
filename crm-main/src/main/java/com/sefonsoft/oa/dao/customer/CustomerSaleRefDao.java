package com.sefonsoft.oa.dao.customer;

import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (CustomerSaleRef)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-15 16:25:45
 */
public interface CustomerSaleRefDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CustomerSaleRef queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CustomerSaleRef> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param customerSaleRef 实例对象
     * @return 对象列表
     */
    List<CustomerSaleRef> queryAll(CustomerSaleRef customerSaleRef);

    /**
     * 新增数据
     *
     * @param customerSaleRef 实例对象
     * @return 影响行数
     */
    boolean insert(CustomerSaleRef customerSaleRef);

    /**
     * batch insert
     * xielf
     * @param customerSaleRefs
     * @return
     */
    int batchInsert(@Param("batch") List<CustomerSaleRef> customerSaleRefs);

    /**
     * 修改数据
     *
     * @param customerSaleRef 实例对象
     * @return 影响行数
     */
    int update(CustomerSaleRef customerSaleRef);

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
     * @param customerId
     * @return 影响行数
     */
    int deleteByCustomerId(String customerId);



    /**
     * 批量更新数据
     *
     * @param customerSaleRef
     * @return 是否成功
     */
    int batchUpdate(CustomerSaleRef customerSaleRef);


    /**
     * 批量更新数据
     *
     * @param customerSaleRef
     * @return 是否成功
     */
    int customerUpdate(CustomerSaleRef customerSaleRef);


    /**
     * 通过customerId查询对象查询单条数据
     *
     * @param customerSaleRef
     * @return 实例对象
     */
    List<CustomerSaleRef> findCustomerSaleRef(CustomerSaleRef customerSaleRef);

    /**
     * 查询多个
     * @param customerIds
     * @return
     */
    List<CustomerSaleRef> findCustomerByCustomerIds(@Param("customerIds") Set<String> customerIds);

    /**
     * 删除 customerId 和 empId 关联
     * @param customerId
     * @param employeeId
     */
    void deleteRef(String customerId, String employeeId);

    /**
     * 测试客户销售是否关联
     * @param customerId 客户ID
     * @param employeeId 销售ID
     * @return 是否关联
     */
    int countRef(@Param("customerId") String customerId, @Param("employeeId") String employeeId);

    /**
     * 引用关系
     * @param customerId
     * @param employeeId
     * @return
     */
    CustomerSaleRef refInfo(@Param("customerId") String customerId, @Param("employeeId") String employeeId);

    /**
     * 剔除负责此项目的销售与客户之间的关联
     * @param projectId
     */
    void deleteRefByProjectId(@Param("projectId") String projectId);


    List<String> findByCustomerId(@Param("customerId") String customerId);
}