package com.sefonsoft.oa.service.customer;

import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;

import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.system.utils.PageResponse;

/**
 * (CustomerShare)表服务接口
 */
public interface CustomerShareService {

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  CustomerInfoQueryVo queryById(Long id);

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit  查询条数
   * @return 对象列表
   */
  List<CustomerInfo> queryAllByLimit(int offset, int limit);

  /**
   * 新增数据
   *
   * @param customerInfo 实例对象
   * @return 实例对象
   */
  CustomerInfo insert(CustomerInfo customerInfo);

  /**
   * 校验客户编号是否唯一
   *
   * @param customerId
   * @return 结果
   */
  public int checkUnique(String customerId);

  /**
   * 修改数据
   *
   * @param customerInfo 实例对象
   * @return 实例对象
   */
  int update(CustomerInfo customerInfo);

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  boolean deleteById(Long id);

  /**
   * 批量删除数据
   *
   * @param ids
   * @return 是否成功
   */
  boolean deleteByIds(List<String> ids);

//  /**
//   * 批量更新数据
//   *
//   * @param customerSaleRef
//   * @return 是否成功
//   */
//  boolean customerUpdate(CustomerSaleRef customerSaleRef);


  /**
   * 查询客户列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<CustomerInfoQueryDTO> getCondition(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户总数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int findCondition(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<CustomerInfoQueryDTO> getConditions(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户总数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int findConditions(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<CustomerInfoQueryDTO> getConditionx(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户总数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int findConditionx(CustomerInfoQueryDTO customerInfoQueryDTO);


  /**
   * 查询客户列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<CustomerInfoQueryDTO> getConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户总数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int findConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO);


  /**
   * 查询客户列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<CustomerInfoQueryDTO> getConditionss(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户总数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int findConditionss(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 获取表编号
   *
   * @param
   * @return 结果
   */
  public String getMaxCustomerId();

  /**
   * 获取客户编号
   *
   * @param idList
   * @return 结果
   */
  List<String> batchGetCustomerId(String[] idList);

  /**
   * 查询客户联系人个数
   *
   * @param customerInfo 查询条件
   * @return
   */
  int customersCount(CustomerInfo customerInfo);

  /**
   * 查询是否是子部门
   *
   * @param deptId
   * @param deptIds
   * @return
   */
  int contain(String deptId, String deptIds);

  /**
   * 查询导出客户列表
   *
   * @param
   * @return
   */
  List<CustomerInfoExport> getConditionn();

  /**
   * 查询客户信息
   * xielf edited
   *
   * @param customerInfoQueryDTO
   * @return
   */
  PageResponse customerInfoByDataRoleList(CustomerInfoQueryDTO customerInfoQueryDTO);

  String getMaxTodayCustomerId(String toDayCustomerIdPrefix);

  String getKhCode(String maxCode);

  /**
   * 客户信息名称、编码列表
   *
   * @param customerNameIdDTO
   * @return
   */
  List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO);

  /**
   * 拷贝客户信息
   * @param empId 雇员ID
   * @param customerIds 客户编号
   */
  void copyCustomers(String empId, List<String> customerIds);
  
  /**
   * 导入共享客户
   * @param workbook
   * @param loginUser
   * @return
   */
  public boolean importCustomer(Workbook workbook, LoginUserDTO loginUser);
  
  /**
   * 导出共享客户
   * @param deptIds
   * @param employeeId
   * @return
   */
  public List<ExportCustomerInfoQueryVo> exportCustomer(Set<String> deptIds, String employeeId);

  List<String> validateExcel(Workbook workbook);

}