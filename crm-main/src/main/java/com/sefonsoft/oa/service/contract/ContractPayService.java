package com.sefonsoft.oa.service.contract;

import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractPayByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractPayInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;

import java.util.List;

/**
 * 合同回款
 *
 * @author xielf
 */
public interface ContractPayService {


  /**
   * 添加合同回款
   *
   * @param addContractPayInfoDTO
   * @param loginUserDTO
   */
  void addContractPayInfo(AddContractPayInfoItemDTO addContractPayInfoDTO, LoginUserDTO loginUserDTO);

  /**
   * 修改合同回款
   *
   * @param addContractPayInfoDTO
   * @param loginUserDTO
   */
  void updateContractPayInfo(UpdateContractPayInfoDTO addContractPayInfoDTO, LoginUserDTO loginUserDTO);


  /**
   * 列表
   *
   * @param listContractPayInfo
   * @return
   */
  List<ContractPayInfoVO> listContractPayByContractId(ListContractPayByContractIdDTO listContractPayInfo);


  /**
   * 删除合同回款
   *
   * @param id
   */
  void deleteContractPayInfo(int id);

}
