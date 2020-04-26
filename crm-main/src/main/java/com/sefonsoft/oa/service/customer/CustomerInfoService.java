package com.sefonsoft.oa.service.customer;

import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsGroupVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.CustomerSaleDto;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (CustomerInfo)表服务接口
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
public interface CustomerInfoService {

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  CustomerInfoQueryVo queryById(Long id);

  /**
   * 通过ID查询多条条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  List<CustomerInfoQueryVo> queryByIds(Long[] id);

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

  /**
   * 修改客户销售
   *
   * @param saleDto
   * @param Operator
   * @return
   */
  void updateCustomerSale(CustomerSaleDto saleDto, String Operator);


  /**
   * 批量更新数据
   *
   * @param customerSaleRef
   * @return 是否成功
   */
  boolean customerUpdate(CustomerSaleRef customerSaleRef);


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
   * 查询昨天新增数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int countCondition(CustomerInfoQueryDTO customerInfoQueryDTO);


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
   * 查询昨天新增数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int countConditions(CustomerInfoQueryDTO customerInfoQueryDTO);


  /**
   * 查询昨天新增数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int countConditionx(CustomerInfoQueryDTO customerInfoQueryDTO);


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
   * 获取客户销售对象
   *
   * @param customerIdList
   * @return 结果
   */
  List<CustomerSaleRef> batchGetCustomerSaleRef(String[] customerIdList);

  /**
   * 查询客户联系人个数
   *
   * @param customerInfo 查询条件
   * @return
   */
  int customersCount(CustomerInfo customerInfo);

  /**
   * 查询跟踪项目个数
   *
   * @param customerInfo 查询条件
   * @return
   */
  int projectCount(CustomerInfo customerInfo);

  int bizOpportCount(CustomerInfo customerInfo);

  /**
   * 查询销售立项个数
   *
   * @param customerInfo 查询条件
   * @return
   */
  int saleApprovalCount(CustomerInfo customerInfo);

  /**
   * 查询销售合同个数
   *
   * @param customerInfo 查询条件
   * @return
   */
  int saleContractCount(CustomerInfo customerInfo);

  /**
   * 查询机会项目列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<ProjectInfoQueryDTO> getProject(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询客户商机
   * xielf
   * @param customerInfoQueryDTO
   * @return
   */
  List<BizOpports> getBizOpports(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询个数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int getProjectCount(CustomerInfoQueryDTO customerInfoQueryDTO);


  /**
   * 查询立项项目阶段列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<ProjectInfoQueryDTO> getApproval(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询个数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int getApprovalCount(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询销售合同阶段列表
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  List<ProjectInfoQueryDTO> getContract(CustomerInfoQueryDTO customerInfoQueryDTO);

  /**
   * 查询个数
   *
   * @param customerInfoQueryDTO 查询条件
   * @return
   */
  int getContractCount(CustomerInfoQueryDTO customerInfoQueryDTO);


  /**
   * 共有人名称
   *
   * @param customerSaleRef
   * @return 所有部门信息
   */
  public List<Map<String, Object>> cowner(CustomerSaleRef customerSaleRef);


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
   * 查询为填写员工跟踪日报列表
   *
   * @param employeeReportDTO 查询条件
   * @return
   */
  List<EmployeeReportDTO> customerFollow(EmployeeReportDTO employeeReportDTO);

  /**
   * 查询为填写员工跟踪日报总数
   *
   * @param employeeReportDTO 查询条件
   * @return
   */
  int customerFollowTotal(EmployeeReportDTO employeeReportDTO);

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
   * 统计信息
   *
   * @param onTime 0 所有, 1-月，2-季度，3-年, 4-周
   * @return
   */
  List<CustomerStatisticsGroupVo> statistics(Set<String> deptIds, int onTime,String employeeId, LoginUserDTO loginUserDTO);


  /**
   * import
   * xielf
   * @param workbook
   * @param loginUserDTO
   * @return
   */
  List<String> importCustomer(Workbook workbook, LoginUserDTO loginUserDTO);
  
  
  /**
   * 导入插入
   * @param customerInfo 基本信息
   * @param saleIdList 销售ID
   * @param contactInfo 联系人信息
   * @param operator 操作员
   * @param now 时间
   * @param errorOnDumplicate true 检查重复时会抛出 exception，false 不会抛出异常
   * @return 客户ID
   */
  public String importInsertCustomer(CustomerInfo customerInfo, 
      List<String> saleIdList,
      ContactInfo contactInfo,
      String operator,
      Date now,
      boolean errorOnDumplicate);

  /**
   * xielf
   * export
   * @param deptIds
   * @param employeeId
   * @return
   */
  List<ExportCustomerInfoQueryVo> exportCustomer(Set<String> deptIds, String employeeId);

  List<ExportCustomerInfoQueryVo> exportCustomer1(Set<String> deptIds, String employeeId);

  /**
   * xielf
   * @param workbook
   * @return
   */
  List<String> validateExcel(Workbook workbook);

}