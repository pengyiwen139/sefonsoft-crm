package com.sefonsoft.oa.dao.contract;

import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.entity.contract.ContractInvoiceInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ContractInvoiceInfoDao {
  long countByExample(ContractInvoiceInfoExample example);

  int deleteByExample(ContractInvoiceInfoExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(ContractInvoiceInfo record);

  int batchInsert(@Param("records") List<ContractInvoiceInfo> records);

  int insertSelective(ContractInvoiceInfo record);

  List<ContractInvoiceInfo> selectByExample(ContractInvoiceInfoExample example);

  ContractInvoiceInfo selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") ContractInvoiceInfo record, @Param("example") ContractInvoiceInfoExample example);

  int updateByExample(@Param("record") ContractInvoiceInfo record, @Param("example") ContractInvoiceInfoExample example);

  int updateByPrimaryKeySelective(ContractInvoiceInfo record);

  int updateByPrimaryKey(ContractInvoiceInfo record);

  /**根据合同编号查询
   * @param contractId
   * @return
   */
  List<ContractInvoiceInfoVO> selectByContractId(@Param("contractId") String contractId);
}