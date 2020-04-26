package com.sefonsoft.oa.dao.trans;

import com.sefonsoft.oa.domain.trans.TransCondition;
import com.sefonsoft.oa.domain.trans.TransDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Trans拓展DAO
 *
 * @author Aron
 * @version 1.0.0
 */
@Repository
public interface TransExtTransmatnDao {
    /**
     * 按条件查询列表
     *
     * @param transCondition 查询条件
     * @return TransDto列表
     */
    List<TransDto> selectTransByCondition(TransCondition transCondition);

    /**
     * 按条件查询总条数
     *
     * @param transCondition 查询条件
     * @return 总条数
     */
    int selectTransTotalByCondition(TransCondition transCondition);
}