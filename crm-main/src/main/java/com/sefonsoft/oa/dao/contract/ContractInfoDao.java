package com.sefonsoft.oa.dao.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.contract.vo.ContractDetailVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListQueryVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListVO;
import com.sefonsoft.oa.entity.contract.ContractInfo;

public interface ContractInfoDao {
    int deleteByPrimaryKey(String contractId);

    int insert(ContractInfo record);

    int insertSelective(ContractInfo record);

    ContractInfo selectByPrimaryKey(String contractId);

    int updateByPrimaryKeySelective(ContractInfo record);

    int updateByPrimaryKey(ContractInfo record);
    
    
    /**
     * 查询详细信息
     * @param contractId
     * @return
     */
    ContractDetailVO selectDetailVO(@Param("contractId") String contractId);
    

    /**
     * 查询列表
     * @param query
     * @return
     */
    List<ContractListVO> selectListVO(ContractListQueryVO query);
    
    
    /**
     * 查询总条数
     * @param query
     * @return
     */
    int selectListVOCount(ContractListQueryVO query);

    /**
     * 清理删除
     * @param contractId
     */
    void clearDelete(@Param("contractId") String contractId);
    
}