package com.sefonsoft.oa.dao.customer;

public interface EnterpriseTypeDao {


    int deleteByPrimaryKey(Byte enterId);

    int insert(EnterpriseType record);

    int insertSelective(EnterpriseType record);

    EnterpriseType selectByPrimaryKey(Byte enterId);

    int updateByPrimaryKeySelective(EnterpriseType record);

    int updateByPrimaryKey(EnterpriseType record);

    EnterpriseType selectByEnterpriseName(String enterpriseName);

}