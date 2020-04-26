package com.sefonsoft.oa.service.contract;

import java.util.List;

import com.sefonsoft.oa.domain.common.AccessRule;
import com.sefonsoft.oa.domain.contract.vo.ContractDetailVO;
import com.sefonsoft.oa.domain.contract.vo.ContractExcelVO;
import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListQueryVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListVO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayStageVO;
import com.sefonsoft.oa.domain.contract.vo.ContractProductVO;
import com.sefonsoft.oa.domain.contract.vo.ContractSaleInfoVO;
import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractPayStage;
import com.sefonsoft.oa.entity.contract.ContractProductInfo;
import com.sefonsoft.oa.entity.contract.ContractSalesInfo;
import com.sefonsoft.oa.service.common.PageableResult;

public interface ContractService {
  
  

  /**
   * 新增合同信息
   * @param inf 合同信息
   * @param sales 合同销售信息
   * @param products 合同产品信息
   * @param payStage 合同收款比例信息
   */
  void add(
      ContractInfo inf,
      List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> payStage);

  /**
   * 修改合同信息
   * @param inf 合同信息
   * @param sales 合同销售信息
   * @param products 合同产品信息
   * @param payStage 合同收款比例信息
   */
  void update(ContractInfo inf,
      List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> payStage);
  
  
  /**
   * 查询列表，返回分页信息
   */
  PageableResult<ContractListVO> getList(ContractListQueryVO query);

  /**
   * 获取合同详细信息
   * @param contractId
   * @return
   */
  ContractDetailVO getDetail(String contractId);

  /**
   * 合同产品信息
   * @param contractId
   * @return
   */
  List<ContractProductVO> getProductInfo(String contractId);
  
  /**
   * 合同收款比例信息
   * @param contractId
   * @return
   */
  List<ContractPayStageVO> getPayStage(String contractId);
  
  /**
   * 合同开票信息
   * @param contractId
   * @return
   */
  List<ContractInvoiceInfoVO> getInvoiceInfo(String contractId);
  
  /**
   * 合同回款信息
   * @param contractId
   * @return
   */
  List<ContractPayInfoVO> getPayInfo(String contractId);

  /**
   * 删除合同
   * @param contractId
   */
  void delete(String contractId);

  /**
   * 合同销售分成信息
   * @param contractId
   * @return
   */
  List<ContractSaleInfoVO> getSalesInfo(String contractId);

  /**
   * 导入数据
   * @param vo
   */
  void doImport(List<ContractExcelVO> vo);

  /**
   * 导出数据
   * @param acess 权限限制
   * @return
   */
  List<ContractExcelVO> getExports(AccessRule acess);




}
