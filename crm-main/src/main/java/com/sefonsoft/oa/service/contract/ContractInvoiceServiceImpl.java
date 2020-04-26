package com.sefonsoft.oa.service.contract;

import com.sefonsoft.oa.dao.contract.ContractInvoiceInfoDao;
import com.sefonsoft.oa.dao.contract.ContractInvoiceInfoExample;
import com.sefonsoft.oa.dao.contract.ContractPayInfoExample;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractInvoiceByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractInvoiceInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractInvoiceInfo;
import com.sefonsoft.oa.entity.contract.ContractPayInfo;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
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
 * 合同发票
 *
 * @author xielf
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ContractInvoiceServiceImpl implements ContractInvoiceService {


  private ContractInvoiceInfoDao invoiceInfoDao;
  private SysEmployeeDao employeeDao;


  @Autowired
  public void setInvoiceInfoDao(ContractInvoiceInfoDao invoiceInfoDao) {
    this.invoiceInfoDao = invoiceInfoDao;
  }

  @Autowired
  public void setEmployeeDao(SysEmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  @Override
  public void addContractInvoice(AddContractInvoiceItemDTO addContractInvoiceInfoDTO, LoginUserDTO loginUserDTO) {

    final ContractInvoiceInfo invoiceInfo = addContractInvoiceInfoDTO.toContractInvoiceInfo(loginUserDTO);

    ContractInvoiceInfoExample payInfoExample = new ContractInvoiceInfoExample();
    payInfoExample.createCriteria().andContractIdEqualTo(addContractInvoiceInfoDTO.getContractId());

    final List<ContractInvoiceInfo> contractInvoiceInfos = invoiceInfoDao.selectByExample(payInfoExample);

    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
    for (ContractInvoiceInfo iInfo : contractInvoiceInfos) {
      String addDate = dateFormat.format(addContractInvoiceInfoDTO.getInvoiceDate());
      if (dateFormat.format(iInfo.getInvoiceDate()).equals(addDate)) {
        throw new MsgException(String.format("当前日期【%s】已有发票信息", addDate));
      }
    }

    final String invoiceEmployee = invoiceInfo.getInvoiceEmployee();
    final SysEmployee sysEmployee = employeeDao.queryByIds(invoiceEmployee);
    if (sysEmployee == null) {
      throw new MsgException(String.format("开票员工【%s】不存在", addContractInvoiceInfoDTO.getInvoiceEmployee()));
    }
    invoiceInfo.setInvoiceEmployeeName(sysEmployee.getEmployeeName());

    invoiceInfoDao.insert(invoiceInfo);
  }

  @Override
  public void updateContractInvoice(UpdateContractInvoiceInfoDTO updateContractInvoiceInfoDTO, LoginUserDTO loginUserDTO) {

    final ContractInvoiceInfo invoiceInfo = updateContractInvoiceInfoDTO.toContractInvoiceInfo(loginUserDTO);
    if (invoiceInfo.getId() == null) {
      throw new MsgException("发票ID为空");
    }

    ContractInvoiceInfoExample payInfoExample = new ContractInvoiceInfoExample();
    final List<ContractInvoiceInfo> contractInvoiceInfos = invoiceInfoDao.selectByExample(payInfoExample);

    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
    for (ContractInvoiceInfo iInfo : contractInvoiceInfos) {
      String addDate = dateFormat.format(updateContractInvoiceInfoDTO.getInvoiceDate());
      if (dateFormat.format(iInfo.getInvoiceDate()).equals(addDate)) {
        throw new MsgException(String.format("当前日期【%s】已有发票信息", addDate));
      }
    }

    final String invoiceEmployee = invoiceInfo.getInvoiceEmployee();
    final SysEmployee sysEmployee = employeeDao.queryByIds(invoiceEmployee);
    if (sysEmployee == null) {
      throw new MsgException(String.format("开票员工【%s】不存在", invoiceInfo.getInvoiceEmployee()));
    }
    invoiceInfo.setInvoiceEmployeeName(sysEmployee.getEmployeeName());
    invoiceInfoDao.updateByPrimaryKeySelective(invoiceInfo);
  }

  @Override
  public List<ContractInvoiceInfoVO> listContractInvoiceByContractId(ListContractInvoiceByContractIdDTO listContractInvoiceInfo) {

    final String contractId = listContractInvoiceInfo.getContractId();

    ContractInvoiceInfoExample example = new ContractInvoiceInfoExample();
    example.setOrderByClause("last_time desc");

    final ContractInvoiceInfoExample.Criteria criteria = example.createCriteria();
    criteria.andContractIdEqualTo(contractId);

    final List<ContractInvoiceInfo> invoiceInfos = invoiceInfoDao.selectByExample(example);

    return invoiceInfos.stream().map(item -> {
      ContractInvoiceInfoVO invoiceInfoVO = new ContractInvoiceInfoVO();
      BeanUtils.copyProperties(item, invoiceInfoVO);
      return invoiceInfoVO;
    }).collect(Collectors.toList());
  }

  @Override
  public void deleteContractInvoice(int id) {

    invoiceInfoDao.deleteByPrimaryKey(id);
  }
}
