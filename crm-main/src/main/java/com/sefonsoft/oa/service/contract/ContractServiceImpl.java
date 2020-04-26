package com.sefonsoft.oa.service.contract;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sefonsoft.oa.dao.contract.ContractInfoDao;
import com.sefonsoft.oa.dao.contract.ContractInvoiceInfoDao;
import com.sefonsoft.oa.dao.contract.ContractPayInfoDao;
import com.sefonsoft.oa.dao.contract.ContractPayStageDao;
import com.sefonsoft.oa.dao.contract.ContractProductInfoDao;
import com.sefonsoft.oa.dao.contract.ContractSalesInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
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
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractPayStage;
import com.sefonsoft.oa.entity.contract.ContractProductInfo;
import com.sefonsoft.oa.entity.contract.ContractSalesInfo;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.exception.MsgException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContractServiceImpl implements ContractService {

  @Autowired
  SysEmployeeDao empDao;
  
  @Autowired
  CustomerInfoDao custDao;
  
  
  @Autowired
  ContractInfoDao contractInfoDao;
  
  @Autowired
  ContractSalesInfoDao contractSaleInfoDao;
  
  @Autowired
  ContractPayStageDao contractPayStageDao;
  
  @Autowired
  ContractInvoiceInfoDao contractInvoiceInfoDao;
  
  @Autowired
  ContractPayInfoDao contractPayInfoDao;
  
  @Autowired
  ContractProductInfoDao contractProductInfoDao;
  
  public ContractInfo getById(String contractId) {
    
    ContractInfo inf = contractInfoDao.selectByPrimaryKey(contractId);
    
    if (inf == null) {
      throw new MsgException(String.format("编号 '%s' 合同不存在", 
          contractId));
    }
    
    return inf;
  }
  
  /**
   * 根据合同编号
   * <li>清理销售信息
   * <li>清理产品信息
   * <li>清理回款信息
   * @param contractId 
   */
  public void clearContractSales(String contractId) {
    
    // 清理销售信息
    contractSaleInfoDao.deleteByContractId(contractId);
    // 清理产品信息
    contractProductInfoDao.deleteByContractId(contractId);
    // 清理回款信息
    contractPayStageDao.deleteByContractId(contractId);
    // 清理发票
    // contractInvoiceInfoDao.deleteByContractId(contractId);
    // 清理回款
    // contractPayInfoDao.deleteByContractId(contractId);
  }
  
  /**
   * 新增
   * <li>销售信息
   * <li>产品信息
   * <li>回款信息
   * @param contractId 
   */
  public void addContractSalesProductPayStage(ContractInfo inf,
      List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> stage) {
    
    
    // 销售负责人
    // String empId = inf.getEmployeeId();
    
    
    // 添加销售信息
    sales.forEach(si -> {
      
      // 验证负责人
      String saleId = si.getEmployeeId();
      SysEmployee sale = empDao.queryByIds(saleId);
      
      if (sale == null) {
        throw new MsgException(String.format("销售负责人 '%s' 不存在", 
            saleId));
      }
      si.setEmployeeId(saleId);
      si.setEmployeeName(sale.getEmployeeName());
      si.setEmployeePk(sale.getId());
      
      
      contractSaleInfoDao.insert(si);
    });
    
    // 添加产品消息
    products.forEach(prd -> {
      contractProductInfoDao.insert(prd);
    });
    
    // 添加回款消息
    stage.forEach(ps -> {
      contractPayStageDao.insert(ps);
    });
  }
  
  
  @Override
  @Transactional
  public void add(
      ContractInfo inf,
      List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> stage) {
    
    validateSales(inf, sales, products, stage);
    
    // 验证负责人
    String empId = inf.getEmployeeId();
    SysEmployee emp = empDao.queryByIds(empId);
    
    if (emp == null) {
      throw new MsgException(String.format("销售负责人 '%s' 不存在", 
          empId));
    }
    
    inf.setEmployeePk(emp.getId());
    inf.setEmployeeId(emp.getEmployeeId());
    inf.setEmployeeName(emp.getEmployeeName());
    inf.setDeptId(emp.getDeptId());
    
    // 验证客户
    String custId = inf.getCustomerId();
    CustomerInfoQueryVo cust = custDao.queryByCustomerId(inf.getCustomerId());
    
    if (cust == null) {
      throw new MsgException(String.format("客户 '%s' 不存在", 
          custId));
    }
    inf.setCustomerPk(cust.getId());
    inf.setCustomerId(custId);
    inf.setCustomerName(cust.getCustomerName());
    
    // 逻辑删除
    contractInfoDao.clearDelete(inf.getContractId());
    
    clearContractSales(inf.getContractId());
    
    try {
      
      contractInfoDao.insert(inf);
      
      
      // 添加附加信息
      addContractSalesProductPayStage(inf, sales, products, stage);
      
      
    } catch (Exception e) {
      
      if (e.getMessage().contains("Duplicate")) {
        throw new MsgException(String.format("合同编号 '%s' 重复", inf.getContractId()));
      } else {
        throw e;
      }
      
    }
    
  }

  @Override
  @Transactional
  public void update(ContractInfo inf,
      List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> stage) {
    
    validateSales(inf, sales, products, stage);
    
    ContractInfo old = getById(inf.getContractId());
    
    BeanUtils.copyProperties(inf, old);

    if (contractInfoDao.updateByPrimaryKeySelective(old) > 0) {
      
      // 清理
      clearContractSales(inf.getContractId());
      
      // 添加附加信息
      addContractSalesProductPayStage(inf, sales, products, stage);
    }
  }
  
  
  private void validateSales(ContractInfo inf, List<ContractSalesInfo> sales,
      List<ContractProductInfo> products,
      List<ContractPayStage> stage) {
    
    if (100 != sales.stream().mapToInt(ContractSalesInfo::getRatio).sum()) {
      throw new MsgException("销售分成比例之和应为 100");
    }
    
    if (100 != stage.stream().mapToInt(ContractPayStage::getRatio).sum()) {
      throw new MsgException("收款比例之和应为 100");
    }
    
    BigDecimal total = inf.getContractAmount();
    
    // 销售分成总计
    if(!sales.stream()
        .map(ContractSalesInfo::getAmount)
        .reduce(new BigDecimal(0), (a, b) -> a.add(b)).equals(total)) {
      throw new MsgException("销售分成总计金额与合同金额不符");
    }
    
    // 产品总金额要计算正确
    if(!products.stream()
        .map(ContractProductInfo::getAmount)
        .reduce(new BigDecimal(0), (a, b) -> a.add(b)).equals(total)) {
      throw new MsgException("产品总计金额与合同金额不符");
    }
    
    // 收款
    if (!stage.stream()
        .map(ContractPayStage::getAmount)
        .reduce(new BigDecimal(0), (a, b) -> a.add(b)).equals(total)) {
      throw new MsgException("收款总计金额与合同金额不符");
    }
  }

  @Override
  public PageableResult<ContractListVO> getList(ContractListQueryVO query) {
    
    // 权限
    AccessRule rule = query.getAccessRule();
    if (rule.isDenied()) {
      return PageableResult.emptyResult();
    }
    
    // 查询总数
    int count = contractInfoDao.selectListVOCount(query);
    
    // 查询列表
    List<ContractListVO> list = count > 0 ? contractInfoDao.selectListVO(query) 
        : Collections.emptyList();
    
     // 分页结果
     return new PageableResult<ContractListVO>(Long.valueOf(count), list);
  }
  
  @Override
  public ContractDetailVO getDetail(String contractId) {
    
    ContractDetailVO inf = contractInfoDao.selectDetailVO(contractId);
    
    
    
    return inf;
  }
  
  
  @Override
  public List<ContractProductVO> getProductInfo(String contractId) {
    
    
    return contractProductInfoDao.selectByContractId(contractId);
  }

  @Override
  public List<ContractPayStageVO> getPayStage(String contractId) {
    
    return contractPayStageDao.selectByContractId(contractId);
    
  }

  @Override
  public List<ContractInvoiceInfoVO> getInvoiceInfo(String contractId) {
    
    List<ContractInvoiceInfoVO> result = contractInvoiceInfoDao.selectByContractId(contractId);
    
    return result;
  }

  @Override
  public List<ContractPayInfoVO> getPayInfo(String contractId) {
    
    List<ContractPayInfoVO> result = contractPayInfoDao.selectByContractId(contractId);
    
    return result;
  }
  
  @Override
  public void delete(String contractId) {
    contractInfoDao.deleteByPrimaryKey(contractId);
  }

  @Override
  public List<ContractSaleInfoVO> getSalesInfo(String contractId) {
    
    List<ContractSaleInfoVO> vo = contractSaleInfoDao.selectByContractId(contractId);
    
    return vo;
  }
  
  @Override
  public void doImport(List<ContractExcelVO> vo) {
    
  }
  
  @Override
  public List<ContractExcelVO> getExports(AccessRule acess) {
    return Collections.emptyList();
  }

  // getter setter
  
  public ContractInfoDao getContractInfoDao() {
    return contractInfoDao;
  }


  public void setContractInfoDao(ContractInfoDao contractInfoDao) {
    this.contractInfoDao = contractInfoDao;
  }

  /**
   * @return the empDao
   */
  public SysEmployeeDao getEmpDao() {
    return empDao;
  }

  /**
   * @param empDao the empDao to set
   */
  public void setEmpDao(SysEmployeeDao empDao) {
    this.empDao = empDao;
  }

  /**
   * @return the contractSaleInfoDao
   */
  public ContractSalesInfoDao getContractSaleInfoDao() {
    return contractSaleInfoDao;
  }

  /**
   * @param contractSaleInfoDao the contractSaleInfoDao to set
   */
  public void setContractSaleInfoDao(ContractSalesInfoDao contractSaleInfoDao) {
    this.contractSaleInfoDao = contractSaleInfoDao;
  }

  /**
   * @return the contractPayStageDao
   */
  public ContractPayStageDao getContractPayStageDao() {
    return contractPayStageDao;
  }

  /**
   * @param contractPayStageDao the contractPayStageDao to set
   */
  public void setContractPayStageDao(ContractPayStageDao contractPayStageDao) {
    this.contractPayStageDao = contractPayStageDao;
  }

  /**
   * @return the contractProductInfoDao
   */
  public ContractProductInfoDao getContractProductInfoDao() {
    return contractProductInfoDao;
  }

  /**
   * @param contractProductInfoDao the contractProductInfoDao to set
   */
  public void setContractProductInfoDao(ContractProductInfoDao contractProductInfoDao) {
    this.contractProductInfoDao = contractProductInfoDao;
  }
}
