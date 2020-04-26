package com.sefonsoft.oa.dao.trans;

import com.sefonsoft.oa.entity.trans.Trans;
import org.springframework.stereotype.Repository;

@Repository
public interface TransDao {
    int deleteByPrimaryKey(Long id);

    int insert(Trans record);

    int insertSelective(Trans record);

    Trans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trans record);

    int updateByPrimaryKey(Trans record);
}