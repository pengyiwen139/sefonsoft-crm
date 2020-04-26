package com.sefonsoft.oa.service.contract;

import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractInvoiceByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractInvoiceInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;

import java.util.List;

/**
 * 合同发票
 *
 * @author xielf
 */
public interface ContractInvoiceService {

  /**
   * 添加合同发票
   * xielf
   *
   * @param addContractInvoiceInfoDTO
   * @param loginUserDTO
   */
  void addContractInvoice(AddContractInvoiceItemDTO addContractInvoiceInfoDTO, LoginUserDTO loginUserDTO);

  /**
   * 修改合同发票
   * xielf
   *
   * @param updateContractInvoiceInfoDTO
   * @param loginUserDTO
   */
  void updateContractInvoice(UpdateContractInvoiceInfoDTO updateContractInvoiceInfoDTO, LoginUserDTO loginUserDTO);


  /**
   * 列表
   * xielf
   *
   * @param listContractInvoiceInfo
   * @return
   */
  List<ContractInvoiceInfoVO> listContractInvoiceByContractId(ListContractInvoiceByContractIdDTO listContractInvoiceInfo);

  /**
   * 删除合同发票
   *
   * @param id
   */
  void deleteContractInvoice(int id);

}
