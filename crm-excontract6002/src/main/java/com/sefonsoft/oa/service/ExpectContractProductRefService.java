package com.sefonsoft.oa.service;


import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;

import java.util.List;

/**
 * 合同预测产品金额关联表(ExpectContractProductRef)表服务接口
 *
 * @author makejava
 * @since 2020-04-17 16:23:26
 */
public interface ExpectContractProductRefService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExpectContractProductRef queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExpectContractProductRef> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param expectContractProductRef 实例对象
     * @return 实例对象
     */
    ExpectContractProductRef insert(ExpectContractProductRef expectContractProductRef);

    /**
     * 修改数据
     *
     * @param expectContractProductRef 实例对象
     * @return 实例对象
     */
    ExpectContractProductRef update(ExpectContractProductRef expectContractProductRef);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}