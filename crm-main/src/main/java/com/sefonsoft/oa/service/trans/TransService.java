package com.sefonsoft.oa.service.trans;

import com.sefonsoft.oa.domain.trans.TransCondition;
import com.sefonsoft.oa.domain.trans.TransDto;

import java.util.List;

/**
 * 业务类接口
 *
 * @author Aron
 * @version 0.0.1
 */
public interface TransService {
    /**
     * 新增Trans
     *
     * @param transDto Trans信息
     * @return Trans信息
     */
    TransDto addTrans(TransDto transDto) throws Exception;

    /**
     * 删除Trans
     *
     * @param id 主键ID
     */
    int deleteTrans(Long id);

    /**
     * 更新Trans
     *
     * @param transDto Trans信息
     * @return Trans信息
     */
    TransDto editTrans(TransDto transDto);

    /**
     * 根据主键查询Trans信息
     *
     * @param id 主键
     * @return Trans信息
     */
    TransDto findTransById(Long id);

    /**
     * 查询Trans列表
     *
     * @param transCondition 查询条件
     * @return Trans列表
     */
    List<TransDto> getTransListByCondition(TransCondition transCondition);

    /**
     * 查询Trans总数
     *
     * @param transCondition 查询条件
     * @return 总条数
     */
    int findTranTotalByCondition(TransCondition transCondition);
}