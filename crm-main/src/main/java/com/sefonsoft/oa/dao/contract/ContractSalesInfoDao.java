package com.sefonsoft.oa.dao.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.contract.vo.ContractSaleInfoVO;
import com.sefonsoft.oa.entity.contract.ContractSalesInfo;

public interface ContractSalesInfoDao {
  int deleteByPrimaryKey(Integer id);

  int insert(ContractSalesInfo record);

  int insertSelective(ContractSalesInfo record);

  ContractSalesInfo selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ContractSalesInfo record);

  int updateByPrimaryKey(ContractSalesInfo record);

  /**
   * 根据 contractId 删除信息
   * @param contractId
   */
  void deleteByContractId(@Param("contractId") String contractId);

  /**返回销售比例信息
   * @param contractId
   * @return
   */
  List<ContractSaleInfoVO> selectByContractId(String contractId);
}