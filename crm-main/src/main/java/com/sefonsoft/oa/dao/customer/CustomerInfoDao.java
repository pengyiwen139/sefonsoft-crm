package com.sefonsoft.oa.dao.customer;

import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVoP;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.project.ImportContactDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * (CustomerInfo)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
public interface CustomerInfoDao {

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
    List<CustomerInfoQueryVo> queryByIds(@Param("ids") Long[] id);



    CustomerInfoQueryVo queryByCustomerId(String customerId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CustomerInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param customerInfo 实例对象
     * @return 对象列表
     */
    List<CustomerInfo> queryAll(CustomerInfo customerInfo);

    /**
     * 新增数据
     *
     * @param customerInfo 实例对象
     * @return 影响行数
     */
    int insert(CustomerInfo customerInfo);

    /**
     * xielf
     * batch insert
     * @param customerInfoList
     * @return
     */
    int batchInsert(@Param("batch") List<CustomerInfo> customerInfoList);

    /**
     * 修改数据
     *
     * @param customerInfo 实例对象
     * @return 影响行数
     */
    int update(CustomerInfo customerInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<String> ids);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByCustomerIds(@Param("ids") List<String> ids);

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
     * 查询昨天新增数
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    int countConditionx(CustomerInfoQueryDTO customerInfoQueryDTO);


    /**
     * 查询昨天新增数
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    int countConditions(CustomerInfoQueryDTO customerInfoQueryDTO);

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
     * @param
     * @return 结果
     */
    public String getMaxCustomerId();

    /**
     * 获取CustomerId列表
     * @param
     * @return 结果
     */
    List<String> batchGetCustomerId(@Param("idList") String[] idList);

    /**
     * 获取CustomerId列表
     * @param
     * @return 结果
     */
    List<CustomerSaleRef> batchGetCustomerSaleRef(@Param("customerIdList") String[] customerIdList);
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

    /**
     * 查询商机个数
     * xielf
     * @param customerInfo
     * @return
     */
    int bizOpportsCount(CustomerInfo customerInfo);

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
     * @ref getBizOpports
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getProject(CustomerInfoQueryDTO customerInfoQueryDTO);

    /**
     * xielf
     * 查询商机
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
     * 校验客户编号是否唯一
     * @param customerId
     * @return 结果
     */
    public int checkUnique(@Param("customerId") String customerId);


    /**
     * 查询是否是子部门
     *
     * @param deptId
     * @param deptIds
     * @return
     */
    int contain(@Param("deptId") String deptId,@Param("deptIds") String deptIds);


    /**
     * 查询导出客户列表
     *
     * @param
     * @return
     */
    List<CustomerInfoExport> getConditionn();

    CustomerInfo getLocationByCustomerId(String customerId);

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

    String queryCustomerNameByCustomerId(String customerId);

    /**
     * 查询客户信息列表
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    List<CustomerInfoQueryVo> customerInfoList(CustomerInfoQueryDTO customerInfoQueryDTO);

    /**
     * 查询客户信息列表总数
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    int customerInfoCount(CustomerInfoQueryDTO customerInfoQueryDTO);

    /**
     * 查询昨天新增数
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    int yesterdayCreateCustomerInfoTotalCount(CustomerInfoQueryDTO customerInfoQueryDTO);

    String getMaxTodayCustomerId(String toDayCustomerIdPrefix);

    /**
     * 客户信息名称、编码列表
     * @param customerNameIdDTO
     * @return
     */
    List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO);

    /**
     * 获取客户名称和相应的负责人都相同的客户数据和相应的客户负责人数据
     * @param employeeId
     * @param customerName
     * @return
     */
    List<CustomerInfo> getSameCustomerByUserIdAndCustomerName(@Param("employeeId") String employeeId, @Param("customerName") String customerName);

    void importInsertContactInfo(ImportContactDTO importContactDTO);

    boolean importInsertCustomerInfo(@Param("industry") String industry, @Param("tel") String tel, @Param("importType") int importType, @Param("customerIdNew") String customerIdNew, @Param("customerName") String customerName, @Param("operatorId") String operatorId, @Param("date") Date date);

    boolean importInsertCustomerSaleRef(@Param("customerIdNew") String customerIdNew, @Param("employeeId") String employeeId, @Param("operatorId") String operatorId, @Param("date") Date date);


    /**
     * xielf
     * @return
     */
    CustomerInfo findFirstCustomerInfo();

    /**
     * 统计 普通分组
     *
     * @param entryList
     * @param groupType 0按天数, 1按月, 2按年
     * @param deptId
     * @param loginUserDTO
     * @return
     */
    List<CustomerStatisticsVo> statisticsOfGroup(@Param("groupDateList") List<GroupTuple> entryList,
                                                 @Param("groupType") int groupType,
                                                 @Param("deptId") String deptId,
                                                 @Param("employeeId") String employeeId,
                                                 @Param("loginUser") LoginUserDTO loginUserDTO);

    /**
     * 统计 时间段组
     * xielf
     * @param entryList
     * @param deptId
     * @return
     */
    List<CustomerStatisticsVo> statisticsOfPeriod(@Param("groupDateList") List<GroupTuple> entryList,
                                                   @Param("deptId") String deptId,
                                                  @Param("employeeId") String employeeId,
                                                   @Param("loginUser") LoginUserDTO loginUserDTO);


    /**
     * 统计个数
     * @param deptIds
     * @return
     */
    int statisticCount(@Param("deptIds") Set<String> deptIds,
                       @Param("employeeId") String employeeId);

    /**
     * 按时间统计个数
     * xielf
     * @param deptIds
     * @return
     */
    int onTimeStatisticCount(@Param("deptIds") Set<String> deptIds,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("employeeId") String employeeId);


    /**
     * 总数量
     * @return
     */
    int totalCount();

    /**
     * 更具客户名称查询客户列表
     * @param customerName
     * @return
     */
    List<CustomerInfoQueryVo> queryByCustomerName(@Param("employeeId") String empId, @Param("customerName") String customerName);


    /**
     * 根据员工查询部门ID
     * xielf
     * @param employeeId
     * @return
     */
    Set<String> getDeptIdsByEmployeeId(@Param("employeeId") String employeeId);

    /**
     * xielf
     * @param deptIds
     * @param employeeId
     * @return
     */
    List<ExportCustomerInfoQueryVo> exportCustomerInfoList(@Param("deptIds") Set<String> deptIds,
                                                           @Param("employeeId") String employeeId);

    List<ExportCustomerInfoQueryVoP> exportCustomerInfoListP(@Param("deptIds") Set<String> deptIds,
                                                             @Param("employeeId") String employeeId);


    /**
     * xielf
     * @param customerName
     * @param address
     * @param industry
     * @return
     */
    List<CustomerInfo> findCustomerInfo(@Param("customerName") String customerName, @Param("address") String address, @Param("industry") String industry);

    CustomerInfo findCustomerInfoByContact(
        @Param("customerName") String customerName,
        @Param("address") String address,
        @Param("industry") String industry,
        @Param("contactName") String contactName,
        @Param("telephone") String telephone,
        @Param("employeeId") String employeeId);

}