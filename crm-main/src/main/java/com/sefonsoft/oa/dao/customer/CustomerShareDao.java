package com.sefonsoft.oa.dao.customer;

import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.CustomerStatisticsVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.project.ImportContactDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
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
public interface CustomerShareDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CustomerInfoQueryVo queryById(Long id);


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
     * 新增数据
     *
     * @param customerInfo 实例对象
     * @return 影响行数
     */
    int insert(CustomerInfo customerInfo);

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

//    /**
//     * 批量删除数据
//     *
//     * @param ids
//     * @return 影响行数
//     */
//    int deleteByCustomerIds(@Param("ids") List<String> ids);

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
     * 查询客户联系人个数
     *
     * @param customerInfo 查询条件
     * @return
     */
    int customersCount(CustomerInfo customerInfo);

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

    /**
     * 查询客户信息列表
     *
     * @param customerInfoQueryDTO 查询条件
     * @return
     */
    List<CustomerInfoQueryVo> customerInfoList(CustomerInfoQueryDTO customerInfoQueryDTO);
    
    
    default CustomerInfoQueryVo getOne(String customerId) {
      
      CustomerInfoQueryDTO customerInfoQueryDTO = new CustomerInfoQueryDTO();
      customerInfoQueryDTO.setCustomerId(customerId);
      List<CustomerInfoQueryVo> list = customerInfoList(customerInfoQueryDTO );
      if (list == null ||list.isEmpty()) {
        return null;
      }
      return list.get(0);
    }

    String getMaxTodayCustomerId(String toDayCustomerIdPrefix);

    /**
     * 客户信息名称、编码列表
     * @param customerNameIdDTO
     * @return
     */
    List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO);

    void importInsertContactInfo(ImportContactDTO importContactDTO);

    boolean importInsertCustomerInfo(@Param("industry") String industry, @Param("tel") String tel, @Param("importType") int importType, @Param("customerIdNew") String customerIdNew, @Param("customerName") String customerName, @Param("operatorId") String operatorId, @Param("date") Date date);


    boolean batchInsert(@Param("contactInfoList") List<ContactInfo> contactInfoList);


    List<CustomerInfoQueryVo> queryByCustomerName(@Param("customerName") String customerName);

    // boolean importInsertCustomerSaleRef(@Param("customerIdNew") String customerIdNew, @Param("employeeId") String employeeId, @Param("operatorId") String operatorId, @Param("date") Date date);

    
    /**
     * xielf
     * @param deptIds
     * @param employeeId
     * @return
     */
    List<ExportCustomerInfoQueryVo> exportCustomerInfoList(@Param("deptIds") Set<String> deptIds,
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
        @Param("telephone") String telephone);

}