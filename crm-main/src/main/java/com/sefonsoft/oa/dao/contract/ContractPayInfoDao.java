package com.sefonsoft.oa.dao.contract;

import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.entity.contract.ContractPayInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ContractPayInfoDao {
  long countByExample(ContractPayInfoExample example);

  int deleteByExample(ContractPayInfoExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(ContractPayInfo record);

  int insertSelective(ContractPayInfo record);

  List<ContractPayInfo> selectByExample(ContractPayInfoExample example);

  ContractPayInfo selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") ContractPayInfo record, @Param("example") ContractPayInfoExample example);

  int updateByExample(@Param("record") ContractPayInfo record, @Param("example") ContractPayInfoExample example);

  int updateByPrimaryKeySelective(ContractPayInfo record);

  int updateByPrimaryKey(ContractPayInfo record);

  /**
   * 根据合同编号，选取详细信息
   * @param contractId
   */
  List<ContractPayInfoVO> selectByContractId(String contractId);
}