package com.sefonsoft.oa.service.customer;

import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.cellToString;
import static com.sefonsoft.oa.system.utils.ExcelUtils.getCell;
import static com.sefonsoft.oa.system.utils.ExcelUtils.getCellNum;
import static com.sefonsoft.oa.system.utils.ExcelUtils.getSheet;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.sefonsoft.oa.dao.common.IndustryInfoDao;
import com.sefonsoft.oa.dao.customer.EnterpriseType;
import com.sefonsoft.oa.dao.customer.EnterpriseTypeDao;
import com.sefonsoft.oa.domain.customer.vo.CustomerAndContactVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableList;
import com.sefonsoft.oa.dao.contact.ContactShareDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerShareDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo.EmployeeVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.system.emun.GenderEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.ExcelUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.StringUtils;
import com.sefonsoft.oa.system.utils.UtilMethod;
import com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx;

import javafx.util.Pair;

/**
 * (CustomerInfo)表服务实现类
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@Service("customerShareService")
@Transactional(rollbackFor = Exception.class)
public class CustomerShareServiceImpl implements CustomerShareService {


  public static final List NATURE_LIST = ImmutableList.of("最终用户", "合作伙伴", "最终用户&合作伙伴");

  @Resource
  private CustomerShareDao customerShareDao;

  @Resource
  CustomerShareService customerShareService;

  @Resource
  private SysEmployeeDao sysEmployeeDao;

  @Resource
  private ContactShareDao contactShareDao;

  @Resource
  private CustomerInfoService customerInfoService;

  @Resource
  private CustomerInfoDao customerDao;


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


  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  @Override
  public CustomerInfoQueryVo queryById(Long id) {
    CustomerInfoQueryVo infoQueryVo = this.customerShareDao.queryById(id);
    CustomerSaleRef customerSaleRef = new CustomerSaleRef();
    Objects.requireNonNull(infoQueryVo, "客户 '" + id + "' 不存在");
    customerSaleRef.setCustomerId(infoQueryVo.getCustomerId());
//    List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
    List<String> deptNames = new ArrayList<>();
    List<CustomerInfoQueryVo.EmployeeVo> employeeNames = new ArrayList<>();
//    for (CustomerSaleRef saleRef : customerSaleRefs) {
//      SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
//      if (sysEmployee != null) {
//        CustomerInfoQueryVo.EmployeeVo employeeVo = new CustomerInfoQueryVo.EmployeeVo();
//        employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
//        employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
//        employeeNames.add(employeeVo);
//      }
//      SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(sysEmployee.getDeptId());
//      if (sysDepartment != null) {
//        deptNames.add(sysDepartment.getDeptName());
//      }
//    }
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


  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit  查询条数
   * @return 对象列表
   */
  @Override
  public List<CustomerInfo> queryAllByLimit(int offset, int limit) {
    return this.customerShareDao.queryAllByLimit(offset, limit);
  }

  /**
   * 新增数据
   *
   * @param customerInfo 实例对象
   * @return 实例对象
   */
  @Override
  @Transactional
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
      contactShareDao.insert(contact);
    }
    // 共享客户没有此关系
    // 插入客户和销售关系表
    // customerSaleRefDao.insert(customerSaleRef);
    //插入用户信息表
    customerShareDao.insert(customerInfo);

    List<CustomerInfoQueryVo> size = customerShareDao.queryByCustomerName(customerInfo.getCustomerName());

    if (size.size() > 1) {
      throw new MsgException("客户名称重复");
    }

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

    final List<ContactInfo> contactList = customerInfo.getContactList();

    for (ContactInfo contactInfo : contactList) {
      contactInfo.setLastTime(date);
      contactShareDao.update(contactInfo);
    }

    customerInfo.setLastTime(date);
    int result = customerShareDao.update(customerInfo);

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

    CustomerInfoQueryVo customerInfo = customerShareDao.queryById(id);
    String customerId = customerInfo.getCustomerId();
    // 共享客户没有此关系
    // 删除客户与销售关联数据
    // customerSaleRefDao.deleteByCustomerId(customerId);

    return this.customerShareDao.deleteById(id) > 0;
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
    // customerShareDao.deleteByCustomerIds(ids);
    return customerShareDao.deleteByIds(ids) > 0;
  }

  @Override
  public List<CustomerInfoQueryDTO> getCondition(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerShareDao.getCondition(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findCondition(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerShareDao.findCondition(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditions(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerShareDao.getConditions(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditions(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerShareDao.findConditions(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionx(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerShareDao.getConditionx(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditionx(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerShareDao.findConditionx(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerShareDao.getConditionxx(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditionxx(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerShareDao.findConditionxx(customerInfoQueryDTO);
  }

  @Override
  public List<CustomerInfoQueryDTO> getConditionss(CustomerInfoQueryDTO customerInfoQueryDTO) {

    List<CustomerInfoQueryDTO> list = customerShareDao.getConditionss(customerInfoQueryDTO);

    return list;
  }

  @Override
  public int findConditionss(CustomerInfoQueryDTO customerInfoQueryDTO) {
    return customerShareDao.findConditionss(customerInfoQueryDTO);
  }

  @Override
  public String getMaxCustomerId() {
    return customerShareDao.getMaxCustomerId();
  }


  @Override
  public List<String> batchGetCustomerId(String[] idList) {

    return customerShareDao.batchGetCustomerId(idList);
  }

  @Override
  public int customersCount(CustomerInfo customerInfo) {
    return customerShareDao.customersCount(customerInfo);
  }

  @Override
  public int checkUnique(String customerId) {
    return customerShareDao.checkUnique(customerId);
  }

  @Override
  public int contain(String deptId, String deptIds) {
    return customerShareDao.contain(deptId, deptIds);
  }

  @Override
  public List<CustomerInfoExport> getConditionn() {
    return customerShareDao.getConditionn();
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

    PageInfo<CustomerInfoQueryVo> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> customerShareDao.customerInfoList(customerInfoQueryDTO));
    List<CustomerInfoQueryVo> infoQueryVos = pageInfo.getList();

    /*for (CustomerInfoQueryVo infoQueryVo : infoQueryVos) {
      CustomerSaleRef customerSaleRef = new CustomerSaleRef();
      customerSaleRef.setCustomerId(infoQueryVo.getCustomerId());
      // List<CustomerSaleRef> customerSaleRefs = customerSaleRefDao.findCustomerSaleRef(customerSaleRef);
      if (customerInfoQueryDTO.getDataDeptIdList() != null && customerInfoQueryDTO.getDataDeptIdList().size() > 0){
        List<String> deptNames = new ArrayList<>();
        List<CustomerInfoQueryVo.EmployeeVo> employeeNames = new ArrayList<>();
        // 共享客户，无此功能
        // for (CustomerSaleRef saleRef : customerSaleRefs) {
        //   SysEmployee sysEmployee = sysEmployeeDao.queryByIds(saleRef.getEmployeeId());
        //   if (sysEmployee != null) {
        //     CustomerInfoQueryVo.EmployeeVo employeeVo = new CustomerInfoQueryVo.EmployeeVo();
        //     employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
        //     employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
        //     employeeNames.add(employeeVo);
        //     SysDepartment sysDepartment = sysDepartmentDao.selectByDeptId(sysEmployee.getDeptId());
        //     if (sysDepartment != null) {
        //       deptNames.add(sysDepartment.getDeptName());
        //     }
        //   }
        // }
        infoQueryVo.setDeptNameList(deptNames);
        infoQueryVo.setEmployeeList(employeeNames);
      }*/
    //昨日新增客户总数
    // int yesterdayCreateTotalCount = customerInfoDao.yesterdayCreateCustomerInfoTotalCount(customerInfoQueryDTO);
    PageResponse<List<?>> pageResponse = new PageResponse((int) pageInfo.getTotal(), 0, infoQueryVos);
    return pageResponse;
  }

  @Override
  public String getMaxTodayCustomerId(String toDayCustomerIdPrefix) {
    return customerShareDao.getMaxTodayCustomerId(toDayCustomerIdPrefix);
  }


  @Override
  public String getKhCode(String maxCode) {
    String codePfix = "SKH";
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
      maxCode = customerShareDao.getMaxTodayCustomerId(toDayCustomerIdPrefix);
      if (!ObjTool.isNotNull(maxCode)) {
        maxCode = customerCode;
        return maxCode;
      }
      String uidEnd = maxCode.substring(codePfix.length(), 14);
      long endNum = Long.parseLong(uidEnd);
      long tmpNum = endNum + 1;
      customerCode = codePfix + tmpNum;
    }
    return customerCode;
  }

  @Override
  public List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO) {
    return customerShareDao.getCustomerNameId(customerNameIdDTO);
  }

  @Override
  @Transactional
  public void copyCustomers(String empId, List<String> customerIds) {
    Date date = new Date();

    for (String sid : customerIds) {

      CustomerInfo customerInfo = new CustomerInfo();

      //查询客户最大编码
      String maxCode = customerInfoService.getMaxCustomerId();
      //如果查询数据库没有客户记录，设置maxCode默认值
      if (null == maxCode) {
        maxCode = "KH202001010000";
      }
      //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
      String customerId = customerInfoService.getKhCode(maxCode);
      customerInfo.setCustomerId(customerId);

      CustomerInfoQueryVo cvo = customerShareDao.getOne(sid);

      if (cvo == null) {
        throw new MsgException("客户信息不存在");
      }

      customerInfo.setAddress(cvo.getAddress());
      customerInfo.setCityNum(cvo.getCityNum());

      if (cvo.getContactList() != null) {

        // 处理联系人
        cvo.getContactList().forEach(ci -> {
          ci.setOperator(empId);
          ci.setEmployeeId(empId);
          ci.setCreateTime(date);
          ci.setLastTime(date);
          ci.setId(null);
        });

        customerInfo.setContactList(cvo.getContactList());

      } else {
        customerInfo.setContactList(Collections.emptyList());
      }

      customerInfo.setContacts(cvo.getContacts());
      customerInfo.setContactsDepart(cvo.getContactsDepart());
      customerInfo.setContactsEmail(cvo.getContactsEmail());
      customerInfo.setContactsTelephone(cvo.getTelephone());
      customerInfo.setCountryNum(cvo.getCountryNum());
      // customerInfo.setCowner(cvo.getCowner());
      customerInfo.setEnterId(cvo.getEnterId());
      customerInfo.setEmployeeId(empId);
      customerInfo.setCreateTime(date);
      customerInfo.setCustomerName(cvo.getCustomerName());
      customerInfo.setFax(cvo.getFax());
      customerInfo.setIndustry(cvo.getIndustry());
      customerInfo.setIsListed(cvo.getIsListed());
      customerInfo.setLastTime(date);
      customerInfo.setOperator(empId);
      customerInfo.setProvinceNum(cvo.getProvinceNum());
      customerInfo.setDistrict(cvo.getDistrict());
      customerInfo.setSex(cvo.getSex());
      customerInfo.setTelephone(cvo.getTelephone());
      customerInfo.setWebsite(cvo.getWebsite());
      customerInfo.setZipCode(cvo.getZipCode());

      customerInfo.setImportType(3);
      customerInfo.setEditable(1);


      customerInfoService.insert(customerInfo);

      List<CustomerInfoQueryVo> size = customerDao.queryByCustomerName(empId, customerInfo.getCustomerName());

      if (size.size() > 1) {
        throw new MsgException("客户名称重复");
      }
    }
  }

  @Override
  public boolean importCustomer(Workbook workbook, LoginUserDTO loginUser) {

    Sheet sheet = getSheet(workbook, 0);

    if (sheet == null) {
      return false;
    }

    int rows = ExcelUtils.getRowNum(sheet);

    Date now = new Date();

    for (int i = 1; i < rows; i++) {

      Row row = ExcelUtils.getRow(sheet, i);
      if (row == null) {
        continue;
      }
      List<String> cellList = getCellString(row);

      //销售负责人处理
      List<String> saleIdList = getCustomerEmployeeIdList(cellList, i);


      CustomerInfo customerInfo = getCustomerInfo(cellList, i);
      customerInfo.setOperator(saleIdList.get(0));
      customerInfo.setImportType(1);
      customerInfo.setLastTime(now);
      customerInfo.setCreateTime(now);
      customerInfo.setCountryNum("中国");


      //客户信息处理
      String customerId = "";
      final List<CustomerInfo> customerInfoList = customerShareDao.findCustomerInfo(customerInfo.getCustomerName(),
          customerInfo.getAddress(),
          customerInfo.getIndustry());
      if (customerInfoList.size() == 0) {
        //查询客户最大编码
        String maxCode = this.getMaxCustomerId();
        //如果查询数据库没有客户记录，设置maxCode默认值
        if (null == maxCode) {
          maxCode = "SKH202001010000";
        }
        //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
        customerId = this.getKhCode(maxCode);
        customerInfo.setCustomerId(customerId);
//        customerInfoList.add(customerInfo);
        customerShareDao.insert(customerInfo);
      } else {
        if (customerInfoList.size() > 1) {
          throw new MsgException("此客户存在多条重复记录");
        }
        customerId = customerInfoList.get(0).getCustomerId();
      }

      for (String employeeId : saleIdList) {
        //是否存在此销售负责人
//        CustomerSaleRef customerSaleRef = customerSaleRefDao.refInfo(customerId, employeeId);
//        if (customerSaleRef == null) {
//          customerSaleRef = new CustomerSaleRef();
//          customerSaleRef.setEmployeeId(employeeId);
//          customerSaleRef.setCustomerId(customerId);
//          customerSaleRef.setCreateTime(now);
//          customerSaleRef.setLastTime(now);
//          customerSaleRef.setOperator(loginUser.getUserId());
//          customerSaleRefDao.insert(customerSaleRef);
//        }

        ContactInfo contactInfo = getContactInfo(cellList, i);

        ContactInfo c = contactShareDao.findContactInfo(customerId, contactInfo.getContactName(), contactInfo.getTelphone());

        if (c == null) {
          contactInfo.setOperator(loginUser.getUserId());
          contactInfo.setLastTime(now);
          contactInfo.setCreateTime(now);
          contactInfo.setCustomerId(customerId);
          contactInfo.setEmployeeId(employeeId);
          contactShareDao.insert(contactInfo);
        }

      }
    }
    return true;
  }

  @Override
  public List<ExportCustomerInfoQueryVo> exportCustomer(Set<String> deptIds, String employeeId) {

    final List<ExportCustomerInfoQueryVo> infoQueryVos = customerShareDao.exportCustomerInfoList(deptIds, employeeId);

//    for (ExportCustomerInfoQueryVo exportVo : infoQueryVos) {
//      CustomerSaleRef customerSaleRef = new CustomerSaleRef();
//      customerSaleRef.setCustomerId(exportVo.getCustomerId());
//      List<EmployeeVo> employeeNames = new ArrayList<>();
//      SysEmployee sysEmployee = sysEmployeeDao.queryByIds(exportVo.getOperator());
//      if (sysEmployee != null) {
//        EmployeeVo employeeVo = new EmployeeVo();
//        employeeVo.setEmployeeId(sysEmployee.getEmployeeId());
//        employeeVo.setEmployeeName(sysEmployee.getEmployeeName());
//        employeeNames.add(employeeVo);
//      }
//      exportVo.setEmployeeVoList(employeeNames);
//    }
    return infoQueryVos;
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
    if(StringUtils.isEmpty(provincial)){
      throw new MsgException((row) + "行 客户省市区不能为空");
    }
    if (!StringUtils.isProvincial(provincial)) {
      throw new MsgException((row) + "行 客户省市区格式有误, 例: 四川省/成都市/锦江区(区域可选)");
    }

    String[] provincialArr = provincial.split("/");
    customerInfo.setProvinceNum(provincialArr[0]);
    customerInfo.setCityNum(provincialArr[1]);
    if(provincialArr.length>=3) {
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
    if(industryInfoDao.findIndustryByName(business)==null){
      throw new MsgException((row) + "行 单位所属行业不支持:");
    }

//    if (!UtilMethod.BUSINESS_LIST.contains(business)) {
//      throw new MsgException((row) + "行 单位所属行业不支持");
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
    int cells = 23;
    for (int i = 0; i < cells; i++) {
      Cell cell = getCell(row, i);
      String value = cellToString(cell);
      cellList.add(value);
    }
    return cellList;
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
//      for (String employeeId : key.getEmployeeIds()) {
        CustomerInfo customerInfo = customerShareDao.findCustomerInfoByContact(key.getCustomerName(), key.getAddress(), key.getIndustry(), key.getContactName(), key.getTelephone());
        if (customerInfo != null) {
          questions.add(
              JSONObject.toJSONString(kv.getValue())
                  + " 行 资源池已经有此企业联系人信息");
//        }
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
      }else{
        if (customerName.length()>64) {
          questions.add((rowNum) + "行 客户名称长度限制[64]");
        }
      }
      //验证省市区
      String provincial = cellList.get(PROVINCIAL);
      if (StringUtils.isEmpty(provincial)) {
        questions.add((rowNum) + "行 省市区不能为空");
      }else {
        if (!StringUtils.isProvincial(provincial)) {
          questions.add((rowNum) + "行 单位省市区格式有误,例: 四川省/成都市/锦江区(区域可选)");
        }
        if (provincial.length()>100) {
          questions.add((rowNum) + "行 单位省市区长度限制[100]");
        }
      }

      //验证性质
      String nature = cellList.get(ENTER_NAME);
      if (StringUtils.isEmpty(nature)) {
        questions.add((rowNum) + "行 客户性质不能为空");
      }else {
        if (!NATURE_LIST.contains(nature)) {
          questions.add((rowNum) + "行 单位性质取值应为: " + JSONObject.toJSON(NATURE_LIST));
        }
      }

      //验证行业
      String business = cellList.get(INDUSTRY);
      if (StringUtils.isEmpty(business)) {
        questions.add((rowNum) + "行 客户行业不能为空");
      }else {
        if(industryInfoDao.findIndustryByName(business)==null){
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
      }else{
        if (address.length()>64) {
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
        if (netUrl.length()>200) {
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
      }else{
        if (contactName.length()>20) {
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
        if (deptName.length()>32) {
          questions.add((rowNum) + "行 联系人部门长度限制[32]");
        }
      }

      //职务
      String position = cellList.get(JOB);
      if (StringUtils.isEmpty(position)) {
        questions.add((rowNum) + "行 联系人职位必填");
        if (position.length()>16) {
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
        if (email.length()>64) {
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
        if (school.length()>64) {
          questions.add((rowNum) + "行 学校长度限制[64]");
        }
      }

      //主修
      String major = cellList.get(MAJOR);
      if (!StringUtils.isEmpty(major)) {
        if (major.length()>64) {
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
        if (familyStatus.length()>128) {
          questions.add((rowNum) + "行 家庭信息长度限制[128]");
        }
      }

      String other = cellList.get(OTHER);
      if (!StringUtils.isEmpty(other)) {
        if (other.length()>64) {
          questions.add((rowNum) + "行 其他说明长度限制[64]");
        }
      }

    }
    if(questions.size()==0) {
      questions.addAll(validateExists(sheet, rows));
    }
    return questions;
  }

}