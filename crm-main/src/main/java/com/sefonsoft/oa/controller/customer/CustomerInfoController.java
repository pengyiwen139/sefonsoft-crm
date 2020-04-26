package com.sefonsoft.oa.controller.customer;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.CustomerSaleDto;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.customer.ProjectPageListDTO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.service.contact.ContactInfoService;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.service.role.RoleService;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.*;
import com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.customerTitleList;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.IndexedColors.BLACK;

/**
 * (CustomerInfo)表控制层
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@RestController
@Api(tags = "客户信息相关接口")
public class CustomerInfoController extends BaseController {
  /**
   * 服务对象
   */
  @Resource
  private CustomerInfoService customerInfoService;

  @Resource
  private SysEmployeeService sysEmployeeService;

  @Resource
  private ContactInfoService contactInfoService;

  @Autowired
  private RoleService roleService;


  private void processLoginUserQueryScope(LoginUserDTO loginUserDTO, CustomerInfoQueryDTO infoQueryDTO) {

    //不是领导
    if (!loginUserDTO.getGradeId().equals(Grade.LD.getCode())) {
      //设置查询范围为自己
      infoQueryDTO.setEmployeeId(loginUserDTO.getUserId());
      //设置部门权限为null
      infoQueryDTO.setDataDeptIdList(null);
    }
    //是领导
    else {
      //如果为查询全部
      if (infoQueryDTO.getScope() == 1) {
        //设置部门权限为所有拥有的权限
        infoQueryDTO.setDataDeptIdList(loginUserDTO.getDataDeptIdList());
      }
    }
  }


  /**
   * 条件模糊搜索列表
   *
   * @param customerInfoQueryDTO
   * @return Response
   */
  @ApiOperation(value = "条件模糊搜索列表", response = CustomerInfoQueryDTO.class)
  @RequestMapping(value = "/customerList", method = RequestMethod.POST)
  public Response findCustomerByConditions(@RequestBody CustomerInfoQueryDTO customerInfoQueryDTO) {

    if (customerInfoQueryDTO.getScope() == 2) {
      if (customerInfoQueryDTO.getDataDeptIdList() == null || customerInfoQueryDTO.getDataDeptIdList().size() == 0) {
        return success(new PageResponse<List<?>>(0, 0, new ArrayList<>()));
      }
    }

    LoginUserDTO loginUserDTO = getLoginDTO();
    if (!ObjTool.isNotNull(loginUserDTO)) {
      return failure(LOGIN_STATUS_ERROR);
    }
    String employeeId = customerInfoQueryDTO.getEmployeeId();
    if (StringUtils.isEmpty(employeeId)) {
      return failure("员工编号不能为空");
    }
    processLoginUserQueryScope(loginUserDTO, customerInfoQueryDTO);
    PageResponse pageResponse = customerInfoService.customerInfoByDataRoleList(customerInfoQueryDTO);
    return success(pageResponse);

  }

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @ApiOperation(value = "根据主键id查询客户", response = CustomerInfo.class)
  @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
  public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {
    if (ObjTool.isNotNull(id)) {
      if (id < 0) {
        return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
      }
      CustomerInfoQueryVo customerInfo = customerInfoService.queryById(id);
      return ObjTool.isNotNull(customerInfo) ? success(customerInfo) : failure(NO_RESPONSE_ERROR);
    }
    return failure(NO_PARAM_ERROR.getCode(), "id" + NO_PARAM_ERROR.getMessage());
  }

  /**
   * 通过主键查询多条数据
   * customers?id=1,2,3,4
   * xielf add
   *
   * @param id
   * @return
   */
  @ApiOperation(value = "根据主键id查询多条客户数据,多个ID用逗号隔开", response = CustomerInfo.class)
  @RequestMapping(value = "/customers", method = RequestMethod.GET)
  public Response selectMore(@ApiParam(required = true, value = "表主键id") Long[] id) {

    try {

      if (id == null || id.length == 0) {
        return failure(NO_PARAM_ERROR.getCode(), "id" + NO_PARAM_ERROR.getMessage());
      }
      List<CustomerInfoQueryVo> customerInfo = customerInfoService.queryByIds(id);
      return success(customerInfo);
    } catch (Exception e) {
      e.printStackTrace();
      return failure("数据查询失败");
    }
  }


  /**
   * 新增客户
   *
   * @param customerInfo
   * @return Response
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_ADD)
  @ApiOperation(value = "新增客户", response = CustomerInfo.class)
  @RequestMapping(value = "/customer", method = RequestMethod.PUT)
  public Response insert(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result) {
    if (result.hasErrors()) {
      return failure(result);
    }
    List<ContactInfo> contactList = customerInfo.getContactList();
    if (null == contactList) {
      return failure("客户联系人信息不能为空");
    }

    if (ObjTool.isNotNull(contactList)) {
      for (ContactInfo contactInfo : contactList) {
        if (contactInfoService.maxFormat(contactInfo.getFamilyInfo(), 128)) {
          return failure("家庭状况" + MAX_PARAM + "最长不能超过" + 128);
        }
        if (contactInfoService.maxFormat(contactInfo.getMajor(), 64)) {
          return failure("专业" + MAX_PARAM + "最长不能超过" + 64);
        }
        if (contactInfoService.maxFormat(contactInfo.getUniversity(), 64)) {
          return failure("毕业学校" + MAX_PARAM + "最长不能超过" + 64);
        }
        if (contactInfoService.maxFormat(contactInfo.getOfficePlane(), 32)) {
          return failure("办工座机" + MAX_PARAM + "最长不能超过" + 32);
        }
        if (contactInfoService.maxFormat(contactInfo.getOther(), 128)) {
          return failure("其他" + MAX_PARAM + "最长不能超过" + 128);
        }
      }
    }

    //查询客户最大编码
    String maxCode = customerInfoService.getMaxCustomerId();
    //如果查询数据库没有客户记录，设置maxCode默认值
    if (null == maxCode) {
      maxCode = "KH202001010000";
    }
    //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
    String customerId = customerInfoService.getKhCode(maxCode);
    customerInfo.setCustomerId(customerId);
    String customerName = customerInfo.getCustomerName();
    String employeeId = customerInfo.getEmployeeId();

    if (null == employeeId) {
      return failure("销售负责人不能为空");
    }
    if (null == customerId) {
      return failure("客户编号不能为空");
    }
    if (null == customerName) {
      return failure("客户名称不能为空");
    }
    int customerIdCount = customerInfoService.checkUnique(customerId);
    if (customerIdCount > 0) {
      return failure("客户编号不能重复");
    }
    CustomerInfo customerInfos = null;
    try {
      customerInfos = customerInfoService.insert(customerInfo);
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("返回客户信息错误");
    }
    return success(customerInfos);
  }

  /**
   * 修改客户信息
   *
   * @param customerInfo
   * @param result
   * @return Response
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_UPDATE)
  @ApiOperation(value = "修改客户", response = CustomerInfo.class)
  @RequestMapping(value = "/customer", method = RequestMethod.PATCH)
  public Response update(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result) {
    if (result.hasErrors()) {
      return failure(result);
    }
    //主键id不能为空
    if (!ObjTool.isNotNull(customerInfo.getId())) {
      return failure(PKEY_ERROR);
    }

    List<ContactInfo> contactList = customerInfo.getContactList();
    if (ObjTool.isNotNull(contactList)) {
      for (ContactInfo contactInfo : contactList) {
        if (contactInfoService.maxFormat(contactInfo.getUniversity(), 64)) {
          return failure("毕业学校" + MAX_PARAM + "最长不能超过" + 64);
        }
        if (contactInfoService.maxFormat(contactInfo.getOfficePlane(), 32)) {
          return failure("办工座机" + MAX_PARAM + "最长不能超过" + 32);
        }
        if (contactInfoService.maxFormat(contactInfo.getMajor(), 64)) {
          return failure("专业" + MAX_PARAM + "最长不能超过" + 64);
        }
        if (contactInfoService.maxFormat(contactInfo.getOther(), 128)) {
          return failure("其他" + MAX_PARAM + "最长不能超过" + 128);
        }
        if (contactInfoService.maxFormat(contactInfo.getFamilyInfo(), 128)) {
          return failure("家庭状况" + MAX_PARAM + "最长不能超过" + 128);
        }
      }
    }

    customerInfo.setOperator(getLoginDTO().getUserId());

    return customerInfoService.update(customerInfo) > 0 ? success() : failure(UPDATE_ERROR);
  }

  /**
   * 根据客户编号customerId批量修改负责人或者共有人
   * xielf edit
   *
   * @return
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_CHANGECHARGER)
  @ApiOperation("修改客户负责人/新旧员工ID在数组中的索引位置一 一对应")
  @RequestMapping(value = "/customer/updateCustomerSales", method = RequestMethod.PATCH)
  public Response batchUpdate(@Valid @RequestBody CustomerSaleDto customerSaleRef, BindingResult result,
                              @CurrentUser LoginUserDTO loginUserDTO) {

    try {
      String customerId = customerSaleRef.getCustomerId();

      if (!ObjTool.isNotNull(customerId)) {

        return failure("客户编号不能为空");
      }

      String[] oldEmployeeId = customerSaleRef.getOldEmployeeId();
      String[] employeeId = customerSaleRef.getEmployeeId();

      if (oldEmployeeId == null || employeeId == null) {
        return failure("销售人员或变更销售人员信息为空");
      }
      if (oldEmployeeId.length != employeeId.length) {
        return failure("销售人员与变更销售人员数量不一致");
      }
      boolean eq = true;
      List<String> oldList = new ArrayList<>();
      List<String> newList = new ArrayList<>();
      for (int i = 0; i < oldEmployeeId.length; i++) {
        if (oldEmployeeId[i] != null && !oldEmployeeId[i].equals(employeeId[i])) {
          eq = false;
          oldList.add(oldEmployeeId[i]);
          newList.add(employeeId[i]);
          break;
        }
      }
      if (eq) {
        success();
      }
      String[] oldArr = new String[oldList.size()];
      String[] newArr = new String[oldList.size()];
      for (int i = 0; i < oldList.size(); i++) {
        oldArr[i] = oldList.get(i);
        newArr[i] = newList.get(i);
      }
      customerSaleRef.setOldEmployeeId(oldArr);
      customerSaleRef.setEmployeeId(newArr);
      customerInfoService.updateCustomerSale(customerSaleRef, loginUserDTO.getUserId());
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("变更失败");
    }
  }


  /**
   * 根据id删除客户
   *
   * @return
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_DELETE)
  @ApiOperation("删除单个客户")
  @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
  public Response delete(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {

    return customerInfoService.deleteById(id) ? success() : failure(DELETE_ERROR);
  }

  /**
   * 根据id批量删除客户
   *
   * @return
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_DELETE)
  @ApiOperation("批量删除客户")
  @RequestMapping(value = "/customer/delAll", method = RequestMethod.DELETE)
  @ApiImplicitParam(name = "ids", value = "id以逗号分割", required = true, paramType = "query", allowMultiple = true, dataType = "String")
  public Response deleteIds(String ids) {
    String[] split = ids.split(",");
    //id列表的非空判断
    if (!ObjTool.isNotNull(split)) {
      return failure(NO_PARAM_ERROR);
    }
    List<String> customerIdList = customerInfoService.batchGetCustomerId(split);
    if (!ObjTool.isNotNull(customerIdList)) {
      return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，角色编号错误");
    }

    return customerInfoService.deleteByIds(customerIdList) ? success() : failure(DELETE_ERROR);

  }


  /**
   * 客户联系人个数
   *
   * @param customerInfo
   * @param result
   * @return Response
   */
  @ApiOperation(value = "客户联系人个数(传customerId)", response = CustomerInfo.class)
  @RequestMapping(value = "/contactCount", method = RequestMethod.POST)
  public Response contactCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result,
                               @CurrentUser LoginUserDTO loginUserDTO) {
    if (result.hasErrors()) {
      return failure(result);
    }
    customerInfo.setEmployeeId(loginUserDTO.getUserId());
    customerInfo.setDataDeptIdList(loginUserDTO.getDataDeptIdList());
    Map<String, Object> map = new HashMap<>(16);
    Integer contactCount = customerInfoService.customersCount(customerInfo);
    if (!ObjTool.isNotNull(contactCount)) {
      return failure(QUERY_ERROR);
    }
    map.put("contactCount", contactCount);
    return success(map);
  }

  /**
   * 客户跟踪个数
   *
   * @param customerInfo
   * @param result
   * @return Response
   */
  @ApiOperation(value = "过期 del 详情页/机会项目个数(传customerId)")
  @RequestMapping(value = "/projectCount", method = RequestMethod.POST)
  @Deprecated
  public Response projectCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result) {
    if (result.hasErrors()) {
      return failure(result);
    }
    Map<String, Object> map = new HashMap<>();
    Integer projectCount = customerInfoService.projectCount(customerInfo);
    if (!ObjTool.isNotNull(projectCount)) {
      return failure(QUERY_ERROR);
    }
    map.put("projectCount", projectCount);
    return success(map);
  }

  /**
   * 商机项目
   *
   * @param customerInfo
   * @param result
   * @return
   */
  @ApiOperation(value = "商机个数(传customerId)")
  @PostMapping(value = "/bizOpportsCount")
  public Response bizOpportsCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result,
                                  @CurrentUser LoginUserDTO loginUserDTO) {
    if (result.hasErrors()) {
      return failure(result);
    }
    Map<String, Object> map = new HashMap<>();
    customerInfo.setEmployeeId(loginUserDTO.getUserId());
    customerInfo.setDataDeptIdList(loginUserDTO.getDataDeptIdList());
    Integer projectCount = customerInfoService.bizOpportCount(customerInfo);
    if (!ObjTool.isNotNull(projectCount)) {
      return failure(QUERY_ERROR);
    }
    map.put("bizOpportsCount", projectCount);
    return success(map);
  }

  /**
   * 销售立项个数
   *
   * @param customerInfo
   * @param result
   * @return Response
   */
  @ApiOperation(value = "项目个数(传customerId)", response = CustomerInfo.class)
  @RequestMapping(value = "/saleApprovalCount", method = RequestMethod.POST)
  public Response saleApprovalCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result,
                                    @CurrentUser LoginUserDTO loginUserDTO) {
    if (result.hasErrors()) {
      return failure(result);
    }
    customerInfo.setEmployeeId(loginUserDTO.getUserId());
    customerInfo.setDataDeptIdList(loginUserDTO.getDataDeptIdList());
    Map<String, Object> map = new HashMap<>(16);
    Integer saleApprovalCount = customerInfoService.saleApprovalCount(customerInfo);
    if (!ObjTool.isNotNull(saleApprovalCount)) {
      return failure(QUERY_ERROR);
    }
    map.put("saleApprovalCount", saleApprovalCount);
    return success(map);
  }

  /**
   * 销售合同个数
   *
   * @param customerInfo
   * @param result
   * @return Response
   */
  @ApiOperation(value = "合同个数(传customerId)", response = CustomerInfo.class)
  @RequestMapping(value = "/saleContractCount", method = RequestMethod.POST)
  public Response saleContractCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result) {

    if (result.hasErrors()) {
      return failure(result);
    }
    customerInfo.setEmployeeId(getLoginDTO().getUserId());
    customerInfo.setDataDeptIdList(getLoginDTO().getDataDeptIdList());
    Map<String, Object> map = new HashMap<>(16);
    Integer saleContractCount = customerInfoService.saleContractCount(customerInfo);
    if (!ObjTool.isNotNull(saleContractCount)) {
      return failure(QUERY_ERROR);
    }
    map.put("saleContractCount", saleContractCount);
    return success(map);
  }


  /**
   * 条件查询机会项目列表
   *
   * @param customerInfoQueryDTO
   * @return Response
   * <p>
   * 原 URL = "/chanceProject"
   * xielf edited
   */
  @ApiOperation(value = "客户详情页/商机列表(传customerId),原 /chanceProject", response = ProjectPageListDTO.class)
  @RequestMapping(value = "/customerBizOpports", method = RequestMethod.GET)
  public Response findProject(CustomerInfoQueryDTO customerInfoQueryDTO
      , @CurrentUser LoginUserDTO loginUserDTO) {
    processLoginUserQueryScope(loginUserDTO, customerInfoQueryDTO);
    List<BizOpports> chanceProject = customerInfoService.getBizOpports(customerInfoQueryDTO);
    int totalCustomerInfos = customerInfoService.getProjectCount(customerInfoQueryDTO);
    PageResponse pageResponse = new PageResponse<>(totalCustomerInfos, chanceProject);
    return success(pageResponse);
  }


  /**
   * xielf edited
   * 项目立项列表
   *
   * @param customerInfoQueryDTO
   * @return Response
   */
  @ApiOperation(value = "详情页/项目立项列表(传customerId)", response = ProjectPageListDTO.class)
  @RequestMapping(value = "/approvalProject", method = RequestMethod.GET)
  public Response findApproval(@CurrentUser LoginUserDTO loginUserDTO, CustomerInfoQueryDTO customerInfoQueryDTO) {

    String customerId = customerInfoQueryDTO.getCustomerId();
    if (null == customerId) {
      return failure("客户编号不能为空");
    }

    processLoginUserQueryScope(loginUserDTO, customerInfoQueryDTO);
    customerInfoQueryDTO.setEmployeeId(loginUserDTO.getUserId());
    List<ProjectInfoQueryDTO> approvalProject = customerInfoService.getApproval(customerInfoQueryDTO);
    int totalCustomerInfos = customerInfoService.getApprovalCount(customerInfoQueryDTO);
    PageResponse pageResponse = new PageResponse<>(totalCustomerInfos, approvalProject);
    return success(pageResponse);

  }


  /**
   * 销售合同列表
   *
   * @param customerInfoQueryDTO
   * @return Response
   */
  @ApiOperation(value = "详情页/销售合同列表(传customerId)", response = ProjectPageListDTO.class)
  @RequestMapping(value = "/contractProject", method = RequestMethod.GET)
  public Response findContract(CustomerInfoQueryDTO customerInfoQueryDTO) {
    List<ProjectInfoQueryDTO> contractProject = customerInfoService.getContract(customerInfoQueryDTO);
    int totalCustomerInfos = customerInfoService.getContractCount(customerInfoQueryDTO);
    PageResponse pageResponse = new PageResponse<>(totalCustomerInfos, contractProject);
    return success(pageResponse);
  }

  /**
   * 根据客户编号customerId批量修改负责人或者共有人
   *
   * @return
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_CHANGECHARGER)
  @ApiOperation("详情页面/删除共有人/更新共有人")
  @RequestMapping(value = "/customer/update", method = RequestMethod.POST)
  public Response update(@Valid @RequestBody CustomerSaleRef customerSaleRef, BindingResult result) {
    String customerId = customerSaleRef.getCustomerId();
    if (!ObjTool.isNotNull(customerId)) {
      return failure("客户编号不能为空");
    }

    return customerInfoService.customerUpdate(customerSaleRef) ? success() : failure(DELETE_ERROR);
  }


  /**
   * 根据cowner查询员工名称
   *
   * @return
   */
  @ApiOperation(value = "根据cowner字段查询员工名称", response = CustomerSaleRef.class)
  @RequestMapping(value = "/cowner", method = RequestMethod.POST)
  public Response cowner(@Valid @RequestBody CustomerSaleRef customerSaleRef, BindingResult result) {
    String cowner = customerSaleRef.getCowner();

    if (!ObjTool.isNotNull(cowner)) {
      return failure("共有人不能为空");
    }
    List<Map<String, Object>> cowners = customerInfoService.cowner(customerSaleRef);

    return success(cowners);


  }


  /**
   * 查询销售客户项目跟踪列表
   *
   * @param employeeReportDTO
   * @return Response
   */
  @ApiOperation(value = "详情页面/查询销售跟踪客户项目列表", response = EmployeeReportDTO.class)
  @RequestMapping(value = "/customerFollow", method = RequestMethod.GET)
  public Response customerFollow(EmployeeReportDTO employeeReportDTO) throws Exception {

    if (!ObjTool.isNotNull(employeeReportDTO.getCustomerId())) {
      return failure("客户编号不能为空");
    }
//        if (!ObjTool.isNotNull(employeeReportDTO.getEmployeeId())) {
//            return failure("销售人员工号不能为空");
//        }

    List<EmployeeReportDTO> customerFollowList = customerInfoService.customerFollow(employeeReportDTO);
    int customerFollowTotal = customerInfoService.customerFollowTotal(employeeReportDTO);
    PageResponse pageResponse = new PageResponse<>(customerFollowTotal, customerFollowList);
    return success(pageResponse);
  }

  /**
   * 联系人collection关联映射分页处理
   *
   * @param customerInfoQueryDTO
   * @param customerInfos
   * @return Response
   */
  public List<CustomerInfoQueryDTO> getCustomerInfoQueryDTOS(CustomerInfoQueryDTO customerInfoQueryDTO, List<CustomerInfoQueryDTO> customerInfos) {
    //分页参数不传时候
    if (!ObjTool.isNotNull(customerInfoQueryDTO.getPage()) || !ObjTool.isNotNull(customerInfoQueryDTO.getRows())) {
      return customerInfos;
    }

    //封装分页后结果集
    List<CustomerInfoQueryDTO> customerInfoPage = new ArrayList<CustomerInfoQueryDTO>();
    //联系人collection关联映射分页问题处理
    int page = customerInfoQueryDTO.getPage();
    int rows = customerInfoQueryDTO.getRows();
    int currIdx = (page > 1 ? (page - 1) * rows : 0);
    for (int i = 0; i < rows && i < customerInfos.size() - currIdx; i++) {
      CustomerInfoQueryDTO customerInfo = customerInfos.get(currIdx + i);
      customerInfoPage.add(customerInfo);
    }
    return customerInfoPage;
  }

  @ApiOperation(value = "客户信息名称、编码列表", response = CustomerNameIdDTO.class)
  @RequestMapping(value = "/getCustomerNameId", method = RequestMethod.GET)
  public List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO) {
    return customerInfoService.getCustomerNameId(customerNameIdDTO);
  }


  /**
   * excel导入客户
   * <p>
   * xielf
   *
   * @return Response
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_IMPORT)
  @ApiOperation(value = "excel导入客户以及联系人")
  @PostMapping(value = "/customerInfo/import")
  @OpLog(module = OpLog.Module.CUSTOMER, opType = OpLog.Type.IMPORT)
  public Response importCustomer(@RequestParam("file") MultipartFile file) throws IOException {
    InputStream is = file.getInputStream();
    Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
    List<String> stringList = customerInfoService.validateExcel(workbook);
    if (stringList != null && !stringList.isEmpty()) {
      return warning("数据验证失败", stringList);
    }
    return success(customerInfoService.importCustomer(workbook, getLoginDTO()));
  }


  /**
   * excel导入客户
   * <p>
   * xielf
   *
   * @return Response
   */
  @MethodPermission(menuIdArray = C_CUSTOMER_EXPORT)
  @ApiOperation(value = "excel导出客户以及联系人")
  @GetMapping(value = "/customerInfo/export")
  @OpLog(module = OpLog.Module.CUSTOMER, opType = OpLog.Type.EXPORT)
  public StreamingResponseBody export(@ApiParam(hidden = true) HttpServletResponse response) throws IOException {

    final LoginUserDTO loginUserDTO = getLoginDTO();

    Set set = null;
    //不是领导
    if (!loginUserDTO.getGradeId().equals(Grade.LD.getCode())) {
      loginUserDTO.setDataDeptIdList(null);
    }
    else{
      if(loginUserDTO.getDataDeptIdList()!=null && loginUserDTO.getDataDeptIdList().size()>0) {
        set = ImmutableSet.copyOf(loginUserDTO.getDataDeptIdList());
      }
    }

    final List<ExportCustomerInfoQueryVo> exportCustomerInfoQueryVos = customerInfoService.exportCustomer1(set, loginUserDTO.getUserId());

    if(exportCustomerInfoQueryVos==null || exportCustomerInfoQueryVos.size()==0){
      throw  new MsgException("无数据,拒绝操作");
    }else {

      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/x-download");
      String fileName = "客户信息.xlsx";
      fileName = URLEncoder.encode(fileName, "UTF-8");
      response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

      return out -> {

        try (Workbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
          Sheet sheet = workbook.createSheet("客户信息");
          sheet.setDefaultColumnWidth(20);
          Row row = sheet.createRow(0);
          row.setHeightInPoints(25);
          for (int i = 0; i < customerTitleList.size(); i++) {
            Cell cell = row.createCell(i);

            CellStyle cellStyle = workbook.createCellStyle();
            if (i == CUSTOMER_NAME
                || i == ADDRESS
                || i == INDUSTRY
                || i == ENTER_NAME
                || i == EMPLOYEE_NAME
                || i == EMPLOYEE_ID
                || i == PROVINCIAL

                || i == CONTACT_NAME
                || i == GENDER
//                || i == DEPT_NAME
                || i == JOB
                || i == CONTACT_TELPHONE) {
              cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
              cellStyle.setFillPattern(SOLID_FOREGROUND);

            }
            cellStyle.setBorderBottom(THIN);
            cellStyle.setBorderLeft(THIN);
            cellStyle.setBorderRight(THIN);

            cellStyle.setBottomBorderColor(BLACK.index);
            cellStyle.setLeftBorderColor(BLACK.index);
            cellStyle.setRightBorderColor(BLACK.index);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(customerTitleList.get(i));
          }
          for (int i = 0; i < exportCustomerInfoQueryVos.size(); i++) {

            final ExportCustomerInfoQueryVo queryVo = exportCustomerInfoQueryVos.get(i);
            row = sheet.createRow(i + 1);
            row.setHeightInPoints(20);
            row.createCell(CUSTOMER_NAME).setCellValue(queryVo.getCustomerName());
            row.createCell(PROVINCIAL).setCellValue(queryVo.getProvincial());
            row.createCell(ENTER_NAME).setCellValue(queryVo.getEnterName());
            row.createCell(INDUSTRY).setCellValue(queryVo.getIndustry());
            row.createCell(ADDRESS).setCellValue(queryVo.getAddress());

            StringBuilder employeeNameList = new StringBuilder();
            StringBuilder employeeIdList = new StringBuilder();
            for (ExportCustomerInfoQueryVo.EmployeeVo employeeVo : queryVo.getEmployeeVoList()) {
              employeeNameList.append(employeeVo.getEmployeeName()).append(";");
              employeeIdList.append(employeeVo.getEmployeeId()).append(";");
            }
            employeeNameList.deleteCharAt(employeeNameList.length() - 1);
            employeeIdList.deleteCharAt(employeeIdList.length() - 1);

            row.createCell(EMPLOYEE_NAME).setCellValue(employeeNameList.toString());
            row.createCell(EMPLOYEE_ID).setCellValue(employeeIdList.toString());

            row.createCell(TELEPHONE).setCellValue(queryVo.getTelephone());
            row.createCell(ZIP_CODE).setCellValue(queryVo.getZipCode());
            row.createCell(WEBSITE).setCellValue(queryVo.getWebsite());
            row.createCell(FAX).setCellValue(queryVo.getFax());

            row.createCell(CONTACT_NAME).setCellValue(queryVo.getContactName());
            row.createCell(GENDER).setCellValue(queryVo.getGender());
            row.createCell(DEPT_NAME).setCellValue(queryVo.getDeptName());
            row.createCell(JOB).setCellValue(queryVo.getJob());
            row.createCell(CONTACT_TELPHONE).setCellValue(queryVo.getContactTelphone());
            row.createCell(CONTACT_EMAIL).setCellValue(queryVo.getContactEmail());
            row.createCell(OFFICE_PLANE).setCellValue(queryVo.getOfficePlane());
            row.createCell(UNIVERSITY).setCellValue(queryVo.getUniversity());
            row.createCell(MAJOR).setCellValue(queryVo.getMajor());
            row.createCell(BIRTHDAY).setCellValue(queryVo.getBirthday());
            row.createCell(FAMILY_INFO).setCellValue(queryVo.getFamilyInfo());
            row.createCell(OTHER).setCellValue(queryVo.getOther());
          }
          workbook.write(out);
        } catch (Exception e) {
          e.printStackTrace();
          throw new MsgException(e.getMessage());
        }
      };
    }
  }
}