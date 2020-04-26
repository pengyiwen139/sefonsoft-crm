package com.sefonsoft.oa.dao.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.contract.vo.ContractProductVO;
import com.sefonsoft.oa.entity.contract.ContractProductInfo;

public interface ContractProductInfoDao {
  int deleteByPrimaryKey(Integer id);

  int insert(ContractProductInfo record);

  int insertSelective(ContractProductInfo record);

  ContractProductInfo selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ContractProductInfo record);

  int updateByPrimaryKey(ContractProductInfo record);

  /**
   * 根据 contractId 删除信息
   * @param contractId
   */
  void deleteByContractId(@Param("contractId") String contractId);

  /**
   * 根据 contractId 获取信息
   * @param contractId
   */
  List<ContractProductVO> selectByContractId(@Param("contractId") String contractId);
}