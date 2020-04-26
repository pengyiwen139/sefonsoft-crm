package com.sefonsoft.oa.service.customer;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableList;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.common.IndustryInfoDao;
import com.sefonsoft.oa.dao.contact.ContactInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerSaleRefDao;
import com.sefonsoft.oa.dao.customer.EnterpriseType;
import com.sefonsoft.oa.dao.customer.EnterpriseTypeDao;
import com.sefonsoft.oa.dao.project.ProjectInfoDao;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.customer.vo.*;
import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.CustomerSaleDto;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo.EmployeeVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.entity.project.ProjectInfo;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.service.project.ProjectInfoService;
import com.sefonsoft.oa.system.emun.GenderEnum;
import com.sefonsoft.oa.system.emun.OnTimeEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.*;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.utils.ExcelUtils.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.*;

/**
 * (CustomerInfo)表服务实现类
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@Service("customerInfoService")
@Transactional(rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {

  /**
   * log
   */
  private final static Logger LOG = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

  @Resource
  private CustomerInfoDao customerInfoDao;

  @Resource
  private CustomerSaleRefDao customerSaleRefDao;

  @Resource
  private SysEmployeeDao sysEmployeeDao;

  @Resource
  private ContactInfoDao contactInfoDao;

  private BizOpportsDao bizOpportsDao;

  private ProjectInfoDao projectInfoDao;

  private SysDepartmentDao sysDepartmentDao;

  private ProjectInfoService projectInfoService;

  private EnterpriseTypeDao typeDao;

  private IndustryInfoDao industryInfoDao;


  @Autowired
  public void setIndustryInfoDao(IndustryInfoDao industryInfoDao) {
    this.industryInfoDao = industryInfoDao;
  }

  @Autowired
  public void setTypeDao(EnterpriseTypeDao typeDao) {
    this.typeDao = typeDao;
  }

  @Autowired
  public void setBizOpportsDao(BizOpportsDao bizOpportsDao) {
    this.bizOpportsDao = bizOpportsDao;
  }

  @Autowired
  public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
    this.projectInfoDao = projectInfoDao;
  }

  @Autowired
  public void setSysDepartmentDao(SysDepartmentDao sysDepartmentDao) {
    this.sysDepartmentDao = sysDepartmentDao;
  }

  @Autowired
  public void setProjectInfoService(ProjectInfoService projectInfoService) {
    this.projectInfoService = projectInfoService;
  }

  private static ThreadLocal<SimpleDateFormat> parse = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

  private final ExecutorService executorService = Executors.newFixedThreadPool(8);

  public static final List NATURE_LIST = ImmutableList.of("最终用户", "合作伙伴", "最终用户&合作伙伴");

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  @Override
  public CustomerInfoQueryVo queryById(Long id) {
    CustomerInfoQueryVo infoQueryVo = this.customerInfoDao.queryById(id);
    CustomerSaleRef customerSaleRef = new CustomerSaleRef();
    customerSaleRef.setCustomerId(infoQueryVo.getCustomerId());
    List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
    List<String> deptNames = new ArrayList<>();
    List<CustomerInfoQueryVo.EmployeeVo> employeeNames = new ArrayList<>();
    for (CustomerSaleRef saleRef : customerSaleRefs) {
      SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
      if (sysEmployee != null) {
        CustomerInfoQueryVo.EmployeeVo employeeVo = new CustomerInfoQueryVo.EmployeeVo();
        employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
        employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
        employeeNames.add(employeeVo);
      }
      SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(sysEmployee.getDeptId());
      if (sysDepartment != null) {
        deptNames.add(sysDepartment.getDeptName());
      }
    }
    SysEmployee sysEmployee = sysEmployeeDao.queryByIds(infoQueryVo.getOperator());
    if (sysEmployee != null) {
      infoQueryVo.setOperatorName(sysEmployeeDao.queryByIds(infoQueryVo.getOperator()).getEmployeeName());
    } else {
      infoQueryVo.setOperatorName(infoQueryVo.getOperator());
    }
    infoQueryVo.setDeptNameList(deptNames);
    infoQueryVo.setEmployeeList(employeeNames);
    return infoQueryVo;
  }

  @Override
  public List<CustomerInfoQueryVo> queryByIds(Long[] id) {

    List<CustomerInfoQueryVo> infoQueryVos = this.customerInfoDao.queryByIds(id);
    for (CustomerInfoQueryVo infoQueryVo : infoQueryVos) {
      CustomerSaleRef customerSaleRef = new CustomerSaleRef();
      customerSaleRef.setCustomerId(infoQueryVo.getCustomerId());
      List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
      List<String> deptNames = new ArrayList<>();
      List<CustomerInfoQueryVo.EmployeeVo> employeeNames = new ArrayList<>();
      for (CustomerSaleRef saleRef : customerSaleRefs) {
        SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
        if (sysEmployee != null) {
          CustomerInfoQueryVo.EmployeeVo employeeVo = new CustomerInfoQueryVo.EmployeeVo();
          employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
          employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
          employeeNames.add(employeeVo);
        }
        SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(sysEmployee.getDeptId());
        if (sysDepartment != null) {
          deptNames.add(sysDepartment.getDeptName());
        }
      }
      SysEmployee sysEmployee = sysEmployeeDao.queryByIds(infoQueryVo.getOperator());
      if (sysEmployee != null) {
        infoQueryVo.setOperatorName(sysEmployeeDao.queryByIds(infoQueryVo.getOperator()).getEmployeeName());
      } else {
        infoQueryVo.setOperatorName(infoQueryVo.getOperator());
      }
      infoQueryVo.setDeptNameList(deptNames);
      infoQueryVo.setEmployeeList(employeeNames);
    }

    return infoQueryVos;
  }

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit  查询条数
   * @return 对象列表
   */
  @Override
  public List<CustomerInfo> queryAllByLimit(int offset, int limit) {
    return this.customerInfoDao.queryAllByLimit(offset, limit);
  }

  /**
   * 新增数据
   *
   * @param customerInfo 实例对象
   * @return 实例对象
   */
  @Override
  public CustomerInfo insert(CustomerInfo customerInfo) {
    Date date = new Date();

    customerInfo.setCreateTime(date);
    customerInfo.setLastTime(date);

    CustomerSaleRef customerSaleRef = new CustomerSaleRef();
    customerSaleRef.setCustomerId(customerInfo.getCustomerId());
    customerSaleRef.setCowner(customerInfo.getCowner());
    customerSaleRef.setEmployeeId(customerInfo.getEmployeeId());
    customerSaleRef.setOperator(customerInfo.getOperator());
    customerSaleRef.setCreateTime(date);
    customerSaleRef.setLastTime(date);

    List<ContactInfo> contactList = customerInfo.getContactList();
    for (ContactInfo contactInfo : contactList) {
      ContactInfo contact = new ContactInfo();
      contact.setCustomerId(customerInfo.getCustomerId());
      contact.setLastTime(date);
      contact.setCreateTime(date);
      contact.setContactName(contactInfo.getContactName());
      contact.setContactSex(contactInfo.getContactSex());
      contact.setDeptName(contactInfo.getDeptName());
      contact.setEmail(contactInfo.getEmail());
      contact.setJob(contactInfo.getJob());
      contact.setEmployeeId(contactInfo.getEmployeeId());
      contact.setOperator(contactInfo.getOperator());
      contact.setRole(contactInfo.getRole());
      contact.setTelphone(contactInfo.getTelphone());

      //新加的非必填字段
      contact.setOfficePlane(contactInfo.getOfficePlane())
          .setUniversity(contactInfo.getUniversity())
          .setMajor(contactInfo.getMajor())
          .setBirthday(contactInfo.getBirthday())
          .setFamilyInfo(contactInfo.getFamilyInfo())
          .setOther(contactInfo.getOther());

      //插入联系人
      contactInfoDao.insert(contact);
    }
    //插入客户和销售关系表
    customerSaleRefDao.insert(customerSaleRef);
    //插入用户信息表
    customerInfoDao.insert(customerInfo);

    return customerInfo;
  }

  /**
   * 修改数据
   *
   * @param customerInfo 实例对象
   * @return 实例对象
   */
  @Override
  public int update(CustomerInfo customerInfo) {
    Date date = new Date();
//    CustomerInfoQueryVo customerInfos = queryById(customerInfo.getId());
//    String customerId = customerInfos.getCustomerId();
//    // 删除客户与销售关联数据
//    int count = customerSaleRefDao.deleteByCustomerId(customerId);
//    if (count > 0) {
//      // 重新插入客户与销售关联表信息
//      CustomerSaleRef customerSale = new CustomerSaleRef();
//      customerSale.setCustomerId(customerInfo.getCustomerId());
//      customerSale.setCowner(customerInfo.getCowner());
//      customerSale.setEmployeeId(customerInfo.getEmployeeId());
//      customerSale.setOperator(customerInfo.getOperator());
//      customerSale.setCreateTime(date);
//      customerSale.setLastTime(date);
//      boolean flag = customerSaleRefDao.insert(customerSale);
//
//    }

    // 对于从资源此来的项目，可以编辑一次
    // 且只能编辑一次
    String cid = customerInfo.getCustomerId();
    String empId = customerInfo.getOperator();
    // 负责人编辑，只能编辑一次
    if (empId != null && customerSaleRefDao.countRef(cid, empId) > 0) {
      customerInfo.setEditable(0);
    } else {
      // 不更新这个字段
      customerInfo.setEditable(null);
    }

    customerInfo.setLastTime(date);
    int result = customerInfoDao.update(customerInfo);

    return result;
  }

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  @Override
  public boolean deleteById(Long id) {


    CustomerInfoQueryVo customerInfo = customerInfoDao.queryById(id);
    String customerId = customerInfo.getCustomerId();

    int count = bizOpportsDao.findBizOpportsCountByCustomerId(customerId);
    if (count > 0) {
      throw new MsgException("此客户含商机或项目，不能删除");
    }

    // 删除客户与销售关联数据
    customerSaleRefDao.deleteByCustomerId(customerId);

    return this.customerInfoDao.deleteById(id) > 0;
  }

  /**
   * 批量删除数据
   *
   * @param ids
   * @return 是否成功
   */
  @Override
  public boolean deleteByIds(List<String> ids) {
    //删除客户与销售关联表数据
    customerInfoDao.deleteByCustomerIds(ids);
    return customerInfoDao.deleteByIds(ids) > 0;
  }

  @Override
  public List<CustomerInfoQueryDTO> getCondition(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerInfoDao.getCondition(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findCondition(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.findCondition(customerInfoQueryDTO);
  }

  @Override
  public int countCondition(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.countCondition(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditions(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerInfoDao.getConditions(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditions(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.findConditions(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionx(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerInfoDao.getConditionx(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int countConditionx(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.countConditionx(customerInfoQueryDTO);
  }

  @Override
  public int findConditionx(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.findConditionx(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerInfoDao.getConditionxx(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.findConditionxx(customerInfoQueryDTO);
  }


  @Override
  public int countConditions(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.countConditions(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionss(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerInfoDao.getConditionss(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditionss(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.findConditionss(customerInfoQueryDTO);
  }

  @Override
  public String getMaxCustomerId() {
    return customerInfoDao.getMaxCustomerId();
  }


  @Override
  public List<String> batchGetCustomerId(String[] idList) {

    return customerInfoDao.batchGetCustomerId(idList);
  }

  @Override
  public List<CustomerSaleRef> batchGetCustomerSaleRef(String[] customerIdList) {
    return customerInfoDao.batchGetCustomerSaleRef(customerIdList);
  }

  @Override
  public void updateCustomerSale(CustomerSaleDto saleDto, String Operator) {

    String customerId = saleDto.getCustomerId();
    String[] oldEmployeeIds = saleDto.getOldEmployeeId();
    String[] employeeIds = saleDto.getEmployeeId();

    Date now = new Date();
    for (int i = 0; i < oldEmployeeIds.length; i++) {

      String oldEmployeeId = oldEmployeeIds[i];
      String employeeId = employeeIds[i];

      //当id不一样，即表示有修改
      if (!oldEmployeeId.equals(employeeId)) {

        //查询并修改商机负责人信息
        List<BizOpports> bizOpports = bizOpportsDao.queryBizOpportsByEmployeeIdAndCustomerId(oldEmployeeId, customerId);

        bizOpports.forEach(bizOpport -> {

          bizOpport.setEmployeeId(employeeId);
          bizOpportsDao.updateBizOpports(bizOpport);


          //查询并修改项目负责人信息
          List<ProjectInfo> projectInfos = projectInfoDao.findProjectInfoByBizOpportsId(bizOpport.getId());
          projectInfos.forEach(project -> {
            projectInfoService.changeSale(project.getProjectId(), employeeId, now);
          });
        });


        //查询当前客户负责人信息
        CustomerSaleRef customerSaleRef = new CustomerSaleRef();
        customerSaleRef.setCustomerId(customerId);
        customerSaleRef.setEmployeeId(oldEmployeeId);
        List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.queryAll(customerSaleRef);

        //修改客户负责人信息
        for (CustomerSaleRef saleRef : customerSaleRefs) {
          saleRef.setEmployeeId(employeeId);
          customerSaleRefDao.update(saleRef);
        }

      }
    }

  }


  @Override
  public boolean customerUpdate(CustomerSaleRef customerSaleRef) {
    return customerSaleRefDao.customerUpdate(customerSaleRef) > 0;
  }

  @Override
  public int customersCount(CustomerInfo customerInfo) {
    return customerInfoDao.customersCount(customerInfo);
  }

  @Override
  public int projectCount(CustomerInfo customerInfo) {
    return customerInfoDao.projectCount(customerInfo);
  }

  @Override
  public int bizOpportCount(CustomerInfo customerInfo) {
    return customerInfoDao.bizOpportsCount(customerInfo);
  }

  @Override
  public int saleApprovalCount(CustomerInfo customerInfo) {
    return customerInfoDao.saleApprovalCount(customerInfo);
  }

  @Override
  public int saleContractCount(CustomerInfo customerInfo) {
    return customerInfoDao.saleContractCount(customerInfo);
  }

  @Override
  public List<ProjectInfoQueryDTO> getProject(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getProject(customerInfoQueryDTO);
  }

  @Override
  public List<BizOpports> getBizOpports(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getBizOpports(customerInfoQueryDTO);
  }

  @Override
  public int getProjectCount(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getProjectCount(customerInfoQueryDTO);
  }

  @Override
  public List<ProjectInfoQueryDTO> getApproval(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getApproval(customerInfoQueryDTO);
  }

  @Override
  public int getApprovalCount(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getApprovalCount(customerInfoQueryDTO);
  }

  @Override
  public List<ProjectInfoQueryDTO> getContract(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getContract(customerInfoQueryDTO);
  }

  @Override
  public int getContractCount(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerInfoDao.getContractCount(customerInfoQueryDTO);
  }

  @Override
  public List<Map<String, Object>> cowner(CustomerSaleRef customerSaleRef) {

    List<Map<String, Object>> cowners = new ArrayList<Map<String, Object>>();
    Map<String, Object> map = new HashMap<>(16);
    String cowner = customerSaleRef.getCowner();
    List<String> list = Arrays.asList(cowner.split(","));
    SysEmployee employee = new SysEmployee();
    for (String employeeId : list) {

      employee = sysEmployeeDao.queryByIds(employeeId);
      //String employeeName =employee.getEmployeeName();
      //String employeeIds =employee.getEmployeeId();
      map.put(employeeId, employee);

    }
    cowners.add(map);

    return cowners;
  }

  @Override
  public int checkUnique(String customerId) {
    return customerInfoDao.checkUnique(customerId);
  }

  @Override
  public int contain(String deptId, String deptIds) {
    return customerInfoDao.contain(deptId, deptIds);
  }

  @Override
  public List<CustomerInfoExport> getConditionn() {
    return customerInfoDao.getConditionn();
  }

  @Override
  public List<EmployeeReportDTO> customerFollow(EmployeeReportDTO employeeReportDTO) {
    return customerInfoDao.customerFollow(employeeReportDTO);
  }

  @Override
  public int customerFollowTotal(EmployeeReportDTO employeeReportDTO) {
    return customerInfoDao.customerFollowTotal(employeeReportDTO);
  }

  /**
   * xielf
   *
   * @param customerInfoQueryDTO
   * @return
   */
  @Override
  public PageResponse customerInfoByDataRoleList(CustomerInfoQueryDTO customerInfoQueryDTO) {

    int pageNum = customerInfoQueryDTO.getPage();
    int pageSize = customerInfoQueryDTO.getRows();

    PageInfo<CustomerInfoQueryVo> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> customerInfoDao.customerInfoList(customerInfoQueryDTO));
    List<CustomerInfoQueryVo> infoQueryVos = pageInfo.getList();

    for (CustomerInfoQueryVo infoQueryVo : infoQueryVos) {
      CustomerSaleRef customerSaleRef = new CustomerSaleRef();
      customerSaleRef.setCustomerId(infoQueryVo.getCustomerId());
      List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
      if (customerInfoQueryDTO.getDataDeptIdList() != null && customerInfoQueryDTO.getDataDeptIdList().size() > 0) {
        List<String> deptNames = new ArrayList<>();
        List<CustomerInfoQueryVo.EmployeeVo> employeeNames = new ArrayList<>();
        for (CustomerSaleRef saleRef : customerSaleRefs) {
          SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
          if (sysEmployee != null) {
            CustomerInfoQueryVo.EmployeeVo employeeVo = new CustomerInfoQueryVo.EmployeeVo();
            employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
            employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
            employeeNames.add(employeeVo);
            SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(sysEmployee.getDeptId());
            if (sysDepartment != null) {
              deptNames.add(sysDepartment.getDeptName());
            }
          }
        }
        infoQueryVo.setDeptNameList(deptNames);
        infoQueryVo.setEmployeeList(employeeNames);
      }

    }

    //昨日新增客户总数
    int yesterdayCreateTotalCount = customerInfoDao.yesterdayCreateCustomerInfoTotalCount(customerInfoQueryDTO);
    PageResponse<List<?>> pageResponse = new PageResponse((int) pageInfo.getTotal(), yesterdayCreateTotalCount, infoQueryVos);
    return pageResponse;
  }

  @Override
  public String getMaxTodayCustomerId(String toDayCustomerIdPrefix) {
    return customerInfoDao.getMaxTodayCustomerId(toDayCustomerIdPrefix);
  }


  @Override
  public String getKhCode(String maxCode) {
    String codePfix = "KH";
    String customerCode = "";
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
    String tableTime = maxCode.substring(2, 10);
    String time = format.format(new Date());

    if (!time.equals(tableTime)) {
      customerCode = codePfix + time + "0001";
    } else {
      String uidEnd = maxCode.substring(2, 14);
      long endNum = Long.parseLong(uidEnd);
      long tmpNum = endNum + 1;
      customerCode = codePfix + tmpNum;
    }
//        KH201912300001
    if (ObjTool.isNotNull(customerCode)) {
      String toDayCustomerIdPrefix = customerCode.substring(0, 10);
      maxCode = customerInfoDao.getMaxTodayCustomerId(toDayCustomerIdPrefix);
      if (!ObjTool.isNotNull(maxCode)) {
        maxCode = customerCode;
        return maxCode;
      }
      String uidEnd = maxCode.substring(2, 14);
      long endNum = Long.parseLong(uidEnd);
      long tmpNum = endNum + 1;
      customerCode = codePfix + tmpNum;
    }
    return customerCode;
  }

  @Override
  public List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO) {
    return customerInfoDao.getCustomerNameId(customerNameIdDTO);
  }

  @Override
  public List<CustomerStatisticsGroupVo> statistics(Set<String> deptIds, int onTime, String employeeId, LoginUserDTO loginUserDTO) {

    OnTimeEnum onTimeEnum = OnTimeEnum.convert(onTime);
    try {
      Objects.requireNonNull(onTimeEnum);
    } catch (Exception e) {
      throw new MsgException("传入时间类型有误");
    }

    LOG.info("根据{} 统计客户信息", onTimeEnum.getDescription());

    if (deptIds == null) {
      deptIds = customerInfoDao.getDeptIdsByEmployeeId(employeeId);
    }

    LocalDate localDate = LocalDate.now();
    List<GroupTuple> groupEntry = new ArrayList<>();
    switch (onTimeEnum) {

      case ALL:

        CustomerInfo customerInfo = customerInfoDao.findFirstCustomerInfo();

        if (customerInfo != null) {
          LocalDate first = customerInfo.getCreateTime()
              .toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDateTime()
              .toLocalDate();

          while (first.getYear() <= localDate.getYear()) {
            groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.YEAR).format(first), null));
            first = first.plusYears(1);
          }
        } else {
          groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.YEAR).format(localDate), null));
        }
        break;
      case QUARTER:

        //原结束时间为“今天”
        /*String startTimeSequence = DateUtils.getPatternOfQuarter().format(localDate);
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.parse(startTimeSequence, DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
        while (!(startDate.getMonthValue() == now.getMonthValue() && startDate.getDayOfMonth() == now.getDayOfMonth())) {
            if ((startDate = startDate.plusDays(1)).getDayOfWeek().getValue() == 1) {
                String startValue1 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                startDate = startDate.plusDays(6);
                String endValue2 = null;
                if(startDate.getMonthValue()>now.getMonthValue()){
                    endValue2 = now.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                    groupEntry.add(GroupTuple.of(startValue1, endValue2));
                    break;
                }else{
                    if(startDate.getMonthValue()== now.getMonthValue() && startDate.getDayOfMonth() >= now.getDayOfMonth()){
                        endValue2 = now.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                        groupEntry.add(GroupTuple.of(startValue1, endValue2));
                        break;
                    }
                    endValue2 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                    groupEntry.add(GroupTuple.of(startValue1, endValue2));
                }
            }
        }*/


        //计算开始时间
        String startTimeSequence = DateUtils.getPatternOfQuarter().format(localDate);
        LocalDate startDate = LocalDate.parse(startTimeSequence, DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
        int endMonth = startDate.plusMonths(3).getMonthValue();

        //当前月份时候小于结束月份
        while (startDate.getMonthValue() < endMonth) {

          startDate = startDate.plusDays(1);
          String startValue1 = null;
          String endValue2 = null;

          if (startDate.getDayOfWeek().getValue() == 7) {
            endValue2 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
            startValue1 = startDate.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
            groupEntry.add(GroupTuple.of(startValue1, endValue2));
          }


        }

        break;

      case MONTH:

        localDate = LocalDate.now().withDayOfMonth(1);
        int monthDay = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        for (int i = 0; i < (monthDay - 1); i++) {
          localDate = localDate.plusDays(1);
          groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        }

        //原结束时间为“今天”
        /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        while (localDate.getDayOfMonth()!=1){
            localDate = localDate.minusDays(1);
            groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        }*/
        break;

      case WEEK:

        localDate = LocalDate.now().with(DayOfWeek.MONDAY);
        groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        for (int i = 0; i < 6; i++) {
          localDate = localDate.plusDays(1);
          groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        }

        //原结束时间为“今天”
        /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        while (localDate.getDayOfWeek().getValue()!=1){
            localDate = localDate.minusDays(1);
            groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
        }*/
        break;

      case YEAR:

        //原结束时间为“今天”
        /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
        while (localDate.getMonthValue()!=1){
            localDate = localDate.minusMonths(1);
            groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
        }*/

        localDate = localDate.withMonth(1);

        groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
        for (int i = 0; i < 11; i++) {
          localDate = localDate.plusMonths(1);
          groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
        }

        break;
      default:
        throw new IllegalStateException("Unexpected value: " + onTimeEnum);
    }

    List<CustomerStatisticsGroupVo> customerStatisticsGroupVos = new ArrayList<>();
    switch (onTimeEnum) {

      case ALL:
        for (String deptId : deptIds) {
          CustomerStatisticsGroupVo groupVo = new CustomerStatisticsGroupVo();
          final List<CustomerStatisticsVo> customerStatisticsVos = customerInfoDao.statisticsOfGroup(groupEntry, 2, deptId, employeeId, loginUserDTO);
          SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(deptId);
          if (sysDepartment != null) {
            groupVo.setCategoryAndData(customerStatisticsVos);
            groupVo.setName(sysDepartment.getDeptName());
            customerStatisticsGroupVos.add(groupVo);
          } else {
            LOG.error("出现数据错误,客户数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
          }
        }
        break;

      case QUARTER:
        for (String deptId : deptIds) {
          CustomerStatisticsGroupVo groupVo = new CustomerStatisticsGroupVo();
          final List<CustomerStatisticsVo> customerStatisticsVos = customerInfoDao.statisticsOfPeriod(groupEntry, deptId, employeeId, loginUserDTO);
          SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(deptId);
          if (sysDepartment != null) {
            groupVo.setCategoryAndData(customerStatisticsVos);
            groupVo.setName(sysDepartment.getDeptName());
            customerStatisticsGroupVos.add(groupVo);
          } else {
            LOG.error("出现数据错误,客户数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
          }
          for (int i = 0; i < customerStatisticsVos.size(); i++) {
            customerStatisticsVos.get(i).setDate(String.format("第%d周", (i + 1)));
          }
        }
        break;
      case MONTH:
      case WEEK:
        for (String deptId : deptIds) {
          CustomerStatisticsGroupVo groupVo = new CustomerStatisticsGroupVo();
          final List<CustomerStatisticsVo> customerStatisticsVos = customerInfoDao.statisticsOfGroup(groupEntry, 0, deptId, employeeId, loginUserDTO);
          SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(deptId);
          if (sysDepartment != null) {
            groupVo.setCategoryAndData(customerStatisticsVos);
            groupVo.setName(sysDepartment.getDeptName());
            customerStatisticsGroupVos.add(groupVo);
          } else {
            LOG.error("出现数据错误,客户数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
          }
        }
        break;
      case YEAR:
        for (String deptId : deptIds) {
          CustomerStatisticsGroupVo groupVo = new CustomerStatisticsGroupVo();
          final List<CustomerStatisticsVo> customerStatisticsVos = customerInfoDao.statisticsOfGroup(groupEntry, 1, deptId, employeeId, loginUserDTO);
          SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(deptId);
          if (sysDepartment != null) {
            groupVo.setCategoryAndData(customerStatisticsVos);
            groupVo.setName(sysDepartment.getDeptName());
            customerStatisticsGroupVos.add(groupVo);
          } else {
            LOG.error("出现数据错误,客户数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
          }
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + onTimeEnum);
    }
    return customerStatisticsGroupVos;
  }


  @Override
  public List<String> importCustomer(Workbook workbook, LoginUserDTO loginUser) {

    Sheet sheet = getSheet(workbook, 0);

    if (sheet == null) {
      return null;
    }

    List<String> customerIdList = new ArrayList<>();
    int rows = ExcelUtils.getRowNum(sheet);

    Date now = new Date();

    for (int i = 1; i < rows; i++) {

      int rowNum = i + 1;

      Row row = ExcelUtils.getRow(sheet, i);
      if (row == null) {
        continue;
      }
      List<String> cellList = getCellString(row);
      CustomerInfo customerInfo = getCustomerInfo(cellList, i + 1);
      customerInfo.setOperator(loginUser.getUserId());
      customerInfo.setImportType(1);
      customerInfo.setLastTime(now);
      customerInfo.setCreateTime(now);
      customerInfo.setCountryNum("中国");


      //客户信息处理
      /*String customerId = "";
      final List<CustomerInfo> customerInfoList = customerInfoDao.findCustomerInfo(customerInfo.getCustomerName(),
          customerInfo.getAddress(),
          customerInfo.getIndustry());
      if (customerInfoList.size() == 0) {
        //查询客户最大编码
        String maxCode = this.getMaxCustomerId();
        //如果查询数据库没有客户记录，设置maxCode默认值
        if (null == maxCode) {
          maxCode = "KH202001010000";
        }
        //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
        customerId = this.getKhCode(maxCode);
        customerInfo.setCustomerId(customerId);
        customerInfoDao.insert(customerInfo);
        customerIdList.add(customerId);
      } else {
        if (customerInfoList.size() > 1) {
          throw new MsgException("此客户存在多条重复记录");
        }
        customerId = customerInfoList.get(0).getCustomerId();
      }*/

      //销售负责人处理
      List<String> saleIdList = getCustomerEmployeeIdList(cellList, rowNum);

      ContactInfo contactInfo = getContactInfo(cellList, rowNum);

      //导入插入
      importInsertCustomer(customerInfo, saleIdList, contactInfo, loginUser.getUserId(), now, true);

      customerIdList.add(customerInfo.getCustomerId());
      /*for (String employeeId : saleIdList) {
        //是否存在此销售负责人
        CustomerSaleRef customerSaleRef = customerSaleRefDao.refInfo(customerId, employeeId);
        //不存在
        if (customerSaleRef == null) {
          customerSaleRef = new CustomerSaleRef();
          customerSaleRef.setEmployeeId(employeeId);
          customerSaleRef.setCustomerId(customerId);
          customerSaleRef.setCreateTime(now);
          customerSaleRef.setLastTime(now);
          customerSaleRef.setOperator(loginUser.getUserId());
          customerSaleRefDao.insert(customerSaleRef);
        }


        //查询负责人
        ContactInfo c = contactInfoDao.findContactInfo(customerId, contactInfo.getContactName(), contactInfo.getTelphone(), employeeId);
        if (c == null) {
          contactInfo.setOperator(loginUser.getUserId());
          contactInfo.setLastTime(now);
          contactInfo.setCreateTime(now);
          contactInfo.setCustomerId(customerId);
          contactInfo.setEmployeeId(employeeId);
          contactInfoDao.insert(contactInfo);
        }

      }*/
    }
    return customerIdList;
  }

  public String importInsertCustomer(CustomerInfo customerInfo,
                                     List<String> saleIdList,
                                     ContactInfo contactInfo,
                                     String operator,
                                     Date now,
                                     boolean errorOnDumplicate) {

    //客户信息处理
    String customerId = "";
    final List<CustomerInfo> customerInfoList = customerInfoDao.findCustomerInfo(customerInfo.getCustomerName(),
        customerInfo.getAddress(),
        customerInfo.getIndustry());
    if (customerInfoList.size() == 0) {
      //查询客户最大编码
      String maxCode = this.getMaxCustomerId();
      //如果查询数据库没有客户记录，设置maxCode默认值
      if (null == maxCode) {
        maxCode = "KH202001010000";
      }
      //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
      customerId = this.getKhCode(maxCode);
      customerInfo.setCustomerId(customerId);
      customerInfo.setOperator(operator);
      customerInfo.setCreateTime(now);
      customerInfo.setLastTime(now);
      customerInfoDao.insert(customerInfo);
    } else {
      if (customerInfoList.size() > 1 && errorOnDumplicate) {
        throw new MsgException("此客户存在多条重复记录");
      }
      customerId = customerInfoList.get(0).getCustomerId();
      customerInfo.setCustomerId(customerId);
    }

    //销售负责人处理
    for (String employeeId : saleIdList) {
      //是否存在此销售负责人
      CustomerSaleRef customerSaleRef = customerSaleRefDao.refInfo(customerId, employeeId);
      //不存在
      if (customerSaleRef == null) {
        customerSaleRef = new CustomerSaleRef();
        customerSaleRef.setEmployeeId(employeeId);
        customerSaleRef.setCustomerId(customerId);
        customerSaleRef.setCreateTime(now);
        customerSaleRef.setLastTime(now);
        customerSaleRef.setOperator(operator);
        customerSaleRefDao.insert(customerSaleRef);
      }

      //查询负责人
      ContactInfo c = contactInfoDao.findContactInfo(customerId, contactInfo.getContactName(), contactInfo.getTelphone(), employeeId);
      if (c == null) {
        contactInfo.setOperator(operator);
        contactInfo.setLastTime(now);
        contactInfo.setCreateTime(now);
        contactInfo.setCustomerId(customerId);
        contactInfo.setEmployeeId(employeeId);
        contactInfoDao.insert(contactInfo);
      } else {
        // 填充 ID
        contactInfo.setId(c.getId());
      }

    }

    return customerId;
  }

  @Override
  public List<ExportCustomerInfoQueryVo> exportCustomer(Set<String> deptIds, String employeeId) {

    /*final int pageSize = 1000;

    final PageInfo<Object> pageInfo = PageHelper.startPage(1, pageSize).doSelectPageInfo(() -> {
      customerInfoDao.exportCustomerInfoList(deptIds, employeeId);
    });

    final int pageNum = (int) Math.ceil(pageInfo.getTotal() / (double) pageSize);

    final List<ExportCustomerInfoQueryVo> queryVos = new Vector<>();

    CountDownLatch count = new CountDownLatch(pageNum);

    for (int i = 0; i < pageNum; i++) {

      int finalI = i;
      executorService.execute(() -> {
        final List<Object> infoQueryVos = PageHelper.startPage(finalI + 1, pageSize).doSelectPageInfo(() -> {
          customerInfoDao.exportCustomerInfoList(deptIds, employeeId);
        }).getList();
        for (Object obj : infoQueryVos) {
          ExportCustomerInfoQueryVo exportVo = (ExportCustomerInfoQueryVo) obj;
          CustomerSaleRef customerSaleRef = new CustomerSaleRef();
          customerSaleRef.setCustomerId(exportVo.getCustomerId());
          List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
          List<EmployeeVo> employeeNames = new ArrayList<>();
          for (CustomerSaleRef saleRef : customerSaleRefs) {
            SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
            if (sysEmployee != null) {
              EmployeeVo employeeVo = new EmployeeVo();
              employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
              employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
              employeeNames.add(employeeVo);
            }
          }
          exportVo.setEmployeeVoList(employeeNames);
          queryVos.add(exportVo);
        }
        count.countDown();
      });
    }
    try {
      count.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
    final List<ExportCustomerInfoQueryVo> queryVos = customerInfoDao.exportCustomerInfoList(deptIds, employeeId);
    for (ExportCustomerInfoQueryVo exportVo : queryVos) {
      CustomerSaleRef customerSaleRef = new CustomerSaleRef();
      customerSaleRef.setCustomerId(exportVo.getCustomerId());
      List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
      List<EmployeeVo> employeeNames = new ArrayList<>();
      for (CustomerSaleRef saleRef : customerSaleRefs) {
        SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
        if (sysEmployee != null) {
          EmployeeVo employeeVo = new EmployeeVo();
          employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
          employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
          employeeNames.add(employeeVo);
        }
      }
      exportVo.setEmployeeVoList(employeeNames);
    }
    return queryVos;
  }

  @Override
  public List<ExportCustomerInfoQueryVo> exportCustomer1(Set<String> deptIds, String employeeId) {



    //所有信息
    final List<ExportCustomerInfoQueryVoP> exportCustomerInfoQueryVos = customerInfoDao.exportCustomerInfoListP(deptIds, employeeId);
    String customerContact = null;
    //返回信息
    final List<ExportCustomerInfoQueryVo> queryVos = new ArrayList<>();
    ExportCustomerInfoQueryVo queryVo;
    List<EmployeeVo> employeeNames = null;
    //处理数据
    for (ExportCustomerInfoQueryVoP queryVoP : exportCustomerInfoQueryVos) {

      //联系人为临界点
      if (customerContact == null || !customerContact.equals(queryVoP.getCustomerContact())) {
        //新的联系人
        customerContact = queryVoP.getCustomerContact();
        //新的一行数据
        queryVo = new ExportCustomerInfoQueryVo();
        BeanUtils.copyProperties(queryVoP, queryVo);

        //新的负责人集合
        employeeNames = new ArrayList<>();
        queryVo.setEmployeeVoList(employeeNames);
        queryVos.add(queryVo);
      }
      EmployeeVo employeeVo = new EmployeeVo();
      employeeVo.setEmployeeId(queryVoP.getEmployeeId());
      employeeVo.setEmployeeName(queryVoP.getEmployeeName());
      employeeNames.add(employeeVo);
    }
    return queryVos;
  }

  private List<String> validateExists(Sheet sheet, int rows) {

    List<String> questions = new ArrayList<>();

    Map<CustomerAndContactVo, List<Integer>> map = new LinkedHashMap<>();
    for (int i = 1; i < rows; i++) {
      int rowNum = i + 1;
      Row row = ExcelUtils.getRow(sheet, i);
      if (row == null) {
        continue;
      }
      List<String> cellList = getCellString(row);
      CustomerAndContactVo customerAndContactVo = new CustomerAndContactVo();
      String customerName = cellList.get(CUSTOMER_NAME);
      String industry = cellList.get(INDUSTRY);
      String address = cellList.get(ADDRESS);
      String contactName = cellList.get(CONTACT_NAME);
      String contactPhone = cellList.get(CONTACT_TELPHONE);

      String moreEmployeeId = cellList.get(EMPLOYEE_ID);
      String[] employeeIds = moreEmployeeId.split(";");

      customerAndContactVo.setEmployeeIds(employeeIds);
      customerAndContactVo.setAddress(address);
      customerAndContactVo.setContactName(contactName);
      customerAndContactVo.setCustomerName(customerName);
      customerAndContactVo.setIndustry(industry);
      customerAndContactVo.setTelephone(contactPhone);

      if (map.containsKey(customerAndContactVo)) {
        map.get(customerAndContactVo).add(rowNum);
      } else {
        List<Integer> rowList = new ArrayList<>();
        rowList.add(rowNum);
        map.put(customerAndContactVo, rowList);
      }
    }
    for (Map.Entry<CustomerAndContactVo, List<Integer>> kv : map.entrySet()) {
      if (kv.getValue().size() > 1) {
        questions.add(JSONObject.toJSONString(kv.getValue()) + " 行 [客户名称、客户性质、客户地址、销售负责人、联系人姓名、联系人手机号码] 均一致");
      }
      final CustomerAndContactVo key = kv.getKey();
      for (String employeeId : key.getEmployeeIds()) {
        CustomerInfo customerInfo = customerInfoDao.findCustomerInfoByContact(key.getCustomerName(), key.getAddress(), key.getIndustry(), key.getContactName(), key.getTelephone(), employeeId);
        if (customerInfo != null) {
          questions.add(
              JSONObject.toJSONString(kv.getValue())
                  + " 行 此销售 【" + employeeId + "】 负责人已经有此企业联系人信息");
        }
      }
    }
    return questions;
  }

  @Override
  public List<String> validateExcel(Workbook workbook) {

    Sheet sheet = getSheet(workbook, 0);
    int rows = ExcelUtils.getRowNum(sheet);

    if (rows < 2) {
      throw new MsgException("文档内容为空");
    }

    List<String> questions = new ArrayList<>();
    for (int i = 1; i < rows; i++) {
      int rowNum = i + 1;
      Row row = ExcelUtils.getRow(sheet, i);
      if (row == null) {
        continue;
      }
      List<String> cellList = getCellString(row);
      //员工姓名
      String moreEmployeeName = cellList.get(EMPLOYEE_NAME);
      //工号
      String moreEmployeeId = cellList.get(EMPLOYEE_ID);
      if (StringUtils.isEmpty(moreEmployeeName) || StringUtils.isEmpty(moreEmployeeId)) {
        questions.add((rowNum) + "行 员工姓名和工号均不能为空");
      } else {
        String[] employeeNames = moreEmployeeName.split(";");
        String[] employeeIds = moreEmployeeId.split(";");
        if (employeeNames.length != employeeIds.length) {
          questions.add((rowNum) + "行 员工姓名和工号用英文“;” 隔开,且数量以及位置需要保持一致");
        }
        for (int j = 0; j < employeeNames.length; j++) {

          String employeeId = employeeIds[j];
          String employeeName = employeeNames[j];
          if (!StringUtils.isEmpty(employeeId)) {
            final SysEmployee employee = sysEmployeeDao.queryByIds(employeeId);
            if (employee == null) {
              questions.add(String.format("%d行 %s 员工不存在", (rowNum), employeeId));
            } else {
              //判断姓名和工号一直性
              if (!employee.getEmployeeName().equals(employeeName)) {
                questions.add(String.format("%d行 %s 员工和姓名不匹配", (rowNum), employeeId));
              }
            }
          }
        }
      }
      String customerName = cellList.get(CUSTOMER_NAME);
      //验证名称
      if (StringUtils.isEmpty(customerName)) {
        questions.add((rowNum) + "行 客户名称不能为空");
      } else {
        if (customerName.length() > 64) {
          questions.add((rowNum) + "行 客户名称长度限制[64]");
        }
      }
      //验证省市区
      String provincial = cellList.get(PROVINCIAL);
      if (StringUtils.isEmpty(provincial)) {
        questions.add((rowNum) + "行 省市区不能为空");
      } else {
        if (!StringUtils.isProvincial(provincial)) {
          questions.add((rowNum) + "行 单位省市区格式有误,例: 四川省/成都市/锦江区(区域可选)");
        }
        if (provincial.length() > 100) {
          questions.add((rowNum) + "行 单位省市区长度限制[100]");
        }
      }

      //验证性质
      String nature = cellList.get(ENTER_NAME);
      if (StringUtils.isEmpty(nature)) {
        questions.add((rowNum) + "行 客户性质不能为空");
      } else {
        if (!NATURE_LIST.contains(nature)) {
          questions.add((rowNum) + "行 单位性质取值应为: " + JSONObject.toJSON(NATURE_LIST));
        }
      }

      //验证行业
      String business = cellList.get(INDUSTRY);
      if (StringUtils.isEmpty(business)) {
        questions.add((rowNum) + "行 客户行业不能为空");
      } else {
        if (industryInfoDao.findIndustryByName(business) == null) {
          questions.add((rowNum) + "行 客户所属行业不支持");
        }
//        if (!UtilMethod.BUSINESS_LIST.contains(business)) {
//          questions.add((rowNum) + "行 客户所属行业不支持");
//        }
      }

      //地址
      String address = cellList.get(ADDRESS);
      if (StringUtils.isEmpty(address)) {
        questions.add((rowNum) + "行 单位地址不能为空");
      } else {
        if (address.length() > 64) {
          questions.add((rowNum) + "行 单位地址长度限制[64]");
        }
      }

      //单位座机
      String phoneNumber = cellList.get(TELEPHONE);
      if (!StringUtils.isEmpty(phoneNumber)) {
        if (!StringUtils.isPhoneNumber(phoneNumber)) {
          questions.add((rowNum) + "行 单位座机格式不正确");
        }
      }

      //邮编
      String postCode = cellList.get(ZIP_CODE);
      if (!StringUtils.isEmpty(postCode)) {
        if (!StringUtils.isPostCode(postCode)) {
          questions.add((rowNum) + "行 单位邮政编码格式不正确");
        }
      }

      //网址
      String netUrl = cellList.get(WEBSITE);
      if (!StringUtils.isEmpty(netUrl)) {
        if (!StringUtils.isNetUrl(netUrl)) {
          questions.add((rowNum) + "行 单位网址格式不正确");
        }
        if (netUrl.length() > 200) {
          questions.add((rowNum) + "行 单位网址长度限制[200]");
        }
      }

      //传真
      String faxNumber = cellList.get(FAX);
      if (!StringUtils.isEmpty(faxNumber)) {
        if (!StringUtils.isPhoneNumber(faxNumber)) {
          questions.add((rowNum) + "行 单位传真格式不正确");
        }
      }

      String contactName = cellList.get(CONTACT_NAME);
      //姓名
      if (StringUtils.isEmpty(contactName)) {
        questions.add((rowNum) + "行 联系人姓名必输");
      } else {
        if (contactName.length() > 20) {
          questions.add((rowNum) + "行 联系人姓名长度限制[20]");
        }
      }

      //contactName,

      String gender = cellList.get(GENDER);
      //性别
      GenderEnum genderEnum = GenderEnum.convertName(gender);
      if (genderEnum == null) {
        questions.add((rowNum) + "行 联系人性别必须是(男|女)");
      }

      //部门
      String deptName = cellList.get(DEPT_NAME);
      if (!StringUtils.isEmpty(deptName)) {
        if (deptName.length() > 32) {
          questions.add((rowNum) + "行 联系人部门长度限制[32]");
        }
      }

      //职务
      String position = cellList.get(JOB);
      if (StringUtils.isEmpty(position)) {
        questions.add((rowNum) + "行 联系人职位必填");
        if (position.length() > 16) {
          questions.add((rowNum) + "行 联系人职位长度限制[16]");
        }
      }

      //手机号码
      String telephoneNumber = cellList.get(CustomerExcelIdx.CONTACT_TELPHONE);
      if (StringUtils.isEmpty(telephoneNumber)) {
        questions.add((rowNum) + "行 联系人手机号码必输");
      } else {
        if (!StringUtils.isPhone(telephoneNumber)) {
          questions.add((rowNum) + "行 联系人手机号码不正确");
        }
      }

      //邮箱
      String email = cellList.get(CONTACT_EMAIL);
      if (!StringUtils.isEmpty(email)) {
        if (!StringUtils.isEmail(email)) {
          questions.add((rowNum) + "行 邮箱格式不正确");
        }
        if (email.length() > 64) {
          questions.add((rowNum) + "行 邮箱长度限制[64]");
        }
      }

      //办公电话
      String officePhone = cellList.get(OFFICE_PLANE);
      if (!StringUtils.isEmpty(officePhone)) {
        if (!StringUtils.isPhoneNumber(officePhone)) {
          questions.add((rowNum) + "行 联系人座机格式不正确");
        }
      }


      //学校
      String school = cellList.get(UNIVERSITY);
      if (!StringUtils.isEmpty(school)) {
        if (school.length() > 64) {
          questions.add((rowNum) + "行 学校长度限制[64]");
        }
      }

      //主修
      String major = cellList.get(MAJOR);
      if (!StringUtils.isEmpty(major)) {
        if (major.length() > 64) {
          questions.add((rowNum) + "行 专业长度限制[64]");
        }
      }

      //生日
      String birthday = cellList.get(BIRTHDAY);
      if (!StringUtils.isEmpty(birthday)) {
        final Pair<Boolean, Date> correctDate = StringUtils.correctDate(birthday);
        if (!correctDate.getKey()) {
          questions.add((rowNum) + "行 生日不正确");
        }
      }


      //家庭
      String familyStatus = cellList.get(FAMILY_INFO);
      if (!StringUtils.isEmpty(familyStatus)) {
        if (familyStatus.length() > 128) {
          questions.add((rowNum) + "行 家庭信息长度限制[128]");
        }
      }

      String other = cellList.get(OTHER);
      if (!StringUtils.isEmpty(other)) {
        if (other.length() > 64) {
          questions.add((rowNum) + "行 其他说明长度限制[64]");
        }
      }

    }
    if (questions.size() == 0) {
      questions.addAll(validateExists(sheet, rows));
    }
    return questions;
  }

  /**
   * 处理负责人信息
   *
   * @param cellList
   * @param row
   * @return
   */
  private List<String> getCustomerEmployeeIdList(List<String> cellList, int row) {

    //员工姓名
    String moreEmployeeName = cellList.get(EMPLOYEE_NAME);
    //工号
    String moreEmployeeId = cellList.get(EMPLOYEE_ID);


    //判断姓名和工号为空
    if (StringUtils.isEmpty(moreEmployeeName) || StringUtils.isEmpty(moreEmployeeId)) {
      throw new MsgException((row) + "行 员工姓名和工号均不能为空");
    }

    String[] employeeNames = moreEmployeeName.split(";");
    String[] employeeIds = moreEmployeeId.split(";");

    if (employeeNames.length != employeeIds.length) {
      throw new MsgException((row) + "行 员工姓名和工号用英文“;” 隔开,且数量以及位置需要保持一致");
    }

    List<String> employeeIdList = new ArrayList<>();
    for (int i = 0; i < employeeNames.length; i++) {

      String employeeId = employeeIds[i];
      String employeeName = employeeNames[i];
      if (!StringUtils.isEmpty(employeeId)) {
        final SysEmployee employee = sysEmployeeDao.queryByIds(employeeId);
        if (employee == null) {
          throw new MsgException(String.format("%d行 %s 员工不存在", row, employeeId));
        }
        //判断姓名和工号一直性
        if (!employee.getEmployeeName().equals(employeeName)) {
          throw new MsgException(String.format("%d行 %s 员工和姓名不匹配", row, employeeId));
        }
        employeeIdList.add(employeeId);
      }
    }
    return employeeIdList;
  }


  /**
   * 处理客户
   *
   * @param cellList
   * @param row
   * @return
   */
  private CustomerInfo getCustomerInfo(List<String> cellList, int row) {

    CustomerInfo customerInfo = new CustomerInfo();

    String customerName = cellList.get(CUSTOMER_NAME);
    //验证名称
    if (StringUtils.isEmpty(customerName)) {
      throw new MsgException((row) + "行 客户名称不能为空");
    }
    customerInfo.setCustomerName(customerName);

    //验证省市区
    String provincial = cellList.get(PROVINCIAL);
    if (StringUtils.isEmpty(provincial)) {
      throw new MsgException((row) + "行 客户省市区不能为空");
    }
    if (!StringUtils.isProvincial(provincial)) {
      throw new MsgException((row) + "行 客户省市区格式有误, 例: 四川省/成都市/锦江区(区域可选)");
    }

    String[] provincialArr = provincial.split("/");
    customerInfo.setProvinceNum(provincialArr[0]);
    customerInfo.setCityNum(provincialArr[1]);
    if (provincialArr.length >= 3) {
      customerInfo.setDistrict(provincialArr[2]);
    }

    //验证性质
    String nature = cellList.get(ENTER_NAME);
    if (!NATURE_LIST.contains(nature)) {
      throw new MsgException((row) + "行 单位性质取值应为: " + JSONObject.toJSON(NATURE_LIST));
    }
    final EnterpriseType enterpriseType = typeDao.selectByEnterpriseName(nature);
    customerInfo.setEnterId(enterpriseType.getEnterId().intValue());

    //验证行业
    String business = cellList.get(INDUSTRY);

    if (industryInfoDao.findIndustryByName(business) == null) {
      throw new MsgException((row) + "行 单位所属行业不支持:");
    }
//    if (!UtilMethod.BUSINESS_LIST.contains(business)) {
//      throw new MsgException((row) + "行 单位所属行业不支持:");
//    }
    customerInfo.setIndustry(business);

    //地址
    String address = cellList.get(ADDRESS);
    if (StringUtils.isEmpty(address)) {
      throw new MsgException((row) + "行 单位地址不能为空");
    }
    customerInfo.setAddress(address);

    //单位座机
    String phoneNumber = cellList.get(TELEPHONE);
    if (!StringUtils.isEmpty(phoneNumber)) {
      if (!StringUtils.isPhoneNumber(phoneNumber)) {
        throw new MsgException((row) + "行 单位座机格式不正确");
      }
      customerInfo.setTelephone(phoneNumber);
    }

    //邮编
    String postCode = cellList.get(ZIP_CODE);
    if (!StringUtils.isEmpty(postCode)) {
      if (!StringUtils.isPostCode(postCode)) {
        throw new MsgException((row) + "行 单位邮政编码格式不正确");
      }
    }

    customerInfo.setZipCode(postCode);

    //网址
    String netUrl = cellList.get(WEBSITE);
    if (!StringUtils.isEmpty(netUrl)) {
      if (!StringUtils.isNetUrl(netUrl)) {
        throw new MsgException((row) + "行 单位网址格式不正确");
      }
    }
    customerInfo.setWebsite(netUrl);

    //传真
    String faxNumber = cellList.get(FAX);
    if (!StringUtils.isEmpty(faxNumber)) {
      if (!StringUtils.isPhoneNumber(faxNumber)) {
        throw new MsgException((row) + "行 单位传真格式不正确");
      }
      customerInfo.setFax(faxNumber);
    }
    return customerInfo;
  }

  /**
   * 处理联系人
   *
   * @param cellList
   * @param row
   * @return
   */
  private ContactInfo getContactInfo(List<String> cellList, int row) {

    ContactInfo contactInfo = new ContactInfo();
    String contactName = cellList.get(CONTACT_NAME);
    //姓名
    if (StringUtils.isEmpty(contactName)) {
      throw new MsgException((row) + "行 联系人姓名必输");
    }
    contactInfo.setContactName(contactName);

    String gender = cellList.get(GENDER);
    //性别
    GenderEnum genderEnum = GenderEnum.convertName(gender);
    if (genderEnum == null) {
      throw new MsgException((row) + "行 联系人性别必须是(男|女)");
    }
    contactInfo.setContactSex(genderEnum.getCode());

    //部门
    String deptName = cellList.get(DEPT_NAME);
    contactInfo.setDeptName(deptName);

    //职务
    String position = cellList.get(JOB);
    if (StringUtils.isEmpty(position)) {
      throw new MsgException((row) + "行 联系人职位必填");
    }
    contactInfo.setJob(position);

    //手机号码
    String telephoneNumber = cellList.get(CONTACT_TELPHONE);
    if (!StringUtils.isPhone(telephoneNumber)) {
      throw new MsgException((row) + "行 手机号码不正确");
    }
    contactInfo.setTelphone(telephoneNumber);

    //邮箱
    String email = cellList.get(CONTACT_EMAIL);
    if (!StringUtils.isEmpty(email)) {
      if (!StringUtils.isEmpty(email)) {
        if (!StringUtils.isEmail(email)) {
          throw new MsgException((row) + "行 邮箱格式不正确");
        }
      }
      contactInfo.setEmail(email);
    }

    //办公电话
    String officePhone = cellList.get(OFFICE_PLANE);
    if (!StringUtils.isEmpty(officePhone)) {
      if (!StringUtils.isPhoneNumber(officePhone)) {
        throw new MsgException((row) + "行 联系人座机格式不正确");
      }
      contactInfo.setOfficePlane(officePhone);
    }

    //学校
    String school = cellList.get(UNIVERSITY);
    if (!StringUtils.isEmpty(school)) {
      contactInfo.setUniversity(school);
    }

    //主修
    String major = cellList.get(MAJOR);
    if (!StringUtils.isEmpty(major)) {
      contactInfo.setMajor(major);
    }

    //生日
    String birthday = cellList.get(BIRTHDAY);
    if (!StringUtils.isEmpty(birthday)) {
      final Pair<Boolean, Date> correctDate = StringUtils.correctDate(birthday);
      if (!correctDate.getKey()) {
        throw new MsgException((row) + "行 生日不正确");
      }
      contactInfo.setBirthday(correctDate.getValue());
    }

    //家庭
    String familyStatus = cellList.get(FAMILY_INFO);
    if (!StringUtils.isEmpty(familyStatus)) {
      contactInfo.setFamilyInfo(familyStatus);
    }

    String other = cellList.get(OTHER);
    if (!StringUtils.isEmpty(other)) {
      //其他
      contactInfo.setOther(other);
    }

    return contactInfo;
  }

  /**
   * 获取所有cell 并转换为 sequence
   *
   * @param row
   * @return
   */
  private List<String> getCellString(Row row) {
    List<String> cellList = new ArrayList<>();
//    int cells = getCellNum(row);
    int cells = 23;
    for (int i = 0; i < cells; i++) {
      Cell cell = getCell(row, i);
      String value = cellToString(cell);
      cellList.add(value);
    }
    return cellList;
  }
}