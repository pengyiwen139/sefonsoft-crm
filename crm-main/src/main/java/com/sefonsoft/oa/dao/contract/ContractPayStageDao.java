package com.sefonsoft.oa.dao.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.contract.vo.ContractPayStageVO;
import com.sefonsoft.oa.entity.contract.ContractPayStage;

public interface ContractPayStageDao {
  int deleteByPrimaryKey(Integer id);

  int insert(ContractPayStage record);

  int insertSelective(ContractPayStage record);

  ContractPayStage selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ContractPayStage record);

  int updateByPrimaryKey(ContractPayStage record);

  /**
   * 根据 contractId 删除信息
   * @param contractId
   */
  void deleteByContractId(@Param("contractId") String contractId);

  /**
   * 根据 contractId 获取信息
   * @param contractId
   */
  List<ContractPayStageVO> selectByContractId(@Param("contractId")String contractId);
}