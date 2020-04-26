package com.sefonsoft.oa.dao.common;

import com.sefonsoft.oa.entity.customer.EnterpriseType;

import java.util.List;

/**
 * (EnterpriseType)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-11-24 15:39:08
 */
public interface CommonDao {

    /**
     * 查询所有企业性质（客户的性质）
     *
     * @return 对象列表
     */
    List<EnterpriseType> queryAllEnterpriseType(EnterpriseType enterpriseType);
}