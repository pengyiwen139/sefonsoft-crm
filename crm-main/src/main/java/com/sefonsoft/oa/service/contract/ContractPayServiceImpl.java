package com.sefonsoft.oa.service.contract;

import com.sefonsoft.oa.dao.contract.ContractPayInfoDao;
import com.sefonsoft.oa.dao.contract.ContractPayInfoExample;
import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractPayByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractPayInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractPayInfo;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同回款
 *
 * @author xielf
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContractPayServiceImpl implements ContractPayService {

  private ContractPayInfoDao payInfoDao;

  @Autowired
  public void setPayInfoDao(ContractPayInfoDao payInfoDao) {
    this.payInfoDao = payInfoDao;
  }

  @Override
  public void addContractPayInfo(AddContractPayInfoItemDTO addContractPayInfoDTO, LoginUserDTO loginUserDTO) {

    final ContractPayInfo contractPayInfo = addContractPayInfoDTO.toContractPayInfo(loginUserDTO);

    ContractPayInfoExample payInfoExample = new ContractPayInfoExample();
    payInfoExample.createCriteria().andContractIdEqualTo(addContractPayInfoDTO.getContractId());

    final List<ContractPayInfo> contractPayInfos = payInfoDao.selectByExample(payInfoExample);

    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
    for (ContractPayInfo payInfo : contractPayInfos) {
      String addDate = dateFormat.format(addContractPayInfoDTO.getPayDate());
      if (dateFormat.format(payInfo.getPayDate()).equals(addDate)) {
        throw new MsgException(String.format("当前日期【%s】已有回款信息", addDate));
      }
    }

    payInfoDao.insert(contractPayInfo);
  }

  @Override
  public void updateContractPayInfo(UpdateContractPayInfoDTO updateContractPayInfoDTO, LoginUserDTO loginUserDTO) {

    final ContractPayInfo contractPayInfo = updateContractPayInfoDTO.toContractPayInfo(loginUserDTO);

    ContractPayInfoExample payInfoExample = new ContractPayInfoExample();
    payInfoExample.createCriteria().andContractIdEqualTo(updateContractPayInfoDTO.getContractId());

    final List<ContractPayInfo> contractPayInfos = payInfoDao.selectByExample(payInfoExample);

    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
    for (ContractPayInfo payInfo : contractPayInfos) {
      String addDate = dateFormat.format(updateContractPayInfoDTO.getPayDate());
      if (dateFormat.format(payInfo.getPayDate()).equals(addDate)) {
        throw new MsgException(String.format("当前日期【%s】已有回款信息", addDate));
      }
    }

    payInfoDao.updateByPrimaryKeySelective(contractPayInfo);

  }

  @Override
  public List<ContractPayInfoVO> listContractPayByContractId(ListContractPayByContractIdDTO listContractPayInfo) {

    final String contractId = listContractPayInfo.getContractId();

    final ContractPayInfoExample example = new ContractPayInfoExample();
    example.setOrderByClause("create_time desc");
    example.setDistinct(false);

    final ContractPayInfoExample.Criteria criteria = example.createCriteria();
    criteria.andContractIdEqualTo(contractId);

    final List<ContractPayInfo> contractPayInfo = payInfoDao.selectByExample(example);
    return contractPayInfo.stream().map(item -> {
      ContractPayInfoVO payInfoVO = new ContractPayInfoVO();
      BeanUtils.copyProperties(item, payInfoVO);
      return payInfoVO;
    }).collect(Collectors.toList());
  }

  @Override
  public void deleteContractPayInfo(int id) {

    payInfoDao.deleteByPrimaryKey(id);

  }
}
