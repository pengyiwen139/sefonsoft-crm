package com.sefonsoft.oa.controller.customer;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.FORMAT_ERROR;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;
import static com.sefonsoft.oa.system.emun.ResponseMessage.DELETE_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.MIN_NUM_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.NO_PARAM_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.NO_RESPONSE_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.PKEY_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.QUERY_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.UPDATE_ERROR;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.customerTitleList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sefonsoft.oa.system.annotation.MethodPermission;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.customer.vo.CopyCustomerVO;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.importexport.ImportErrorMessage;
import com.sefonsoft.oa.domain.project.CustomerNameIdDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.service.contact.ContactInfoService;
import com.sefonsoft.oa.service.contact.ContactShareService;
import com.sefonsoft.oa.service.customer.CustomerShareService;
import com.sefonsoft.oa.service.importexport.ImportExportService;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.annotation.OpLog.Module;
import com.sefonsoft.oa.system.annotation.OpLog.Type;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.emun.ResponseMessage;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ExcelUtil;
import com.sefonsoft.oa.system.utils.ExcelUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.StringUtils;
import com.sefonsoft.oa.system.utils.UtilMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户资源池
 */
@RestController
@Api(tags = "客户资源池")
@RequestMapping("/customer/share")
public class CustomerShareController extends BaseController {

  private static final long serialVersionUID = -808721923587736163L;

    /**
     * 服务对象
     */
    @Resource
    private CustomerShareService customerShareService;

    @Resource
    private SysEmployeeService sysEmployeeService;

    @Resource
    private ContactShareService contactShareService;

    @Autowired
    private ImportExportService importExportService;

    /**
     * 条件模糊搜索列表
     * @param customerInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "条件模糊搜索列表",response = CustomerInfoQueryDTO.class)
    @RequestMapping(value = "/customerList", method = RequestMethod.POST)
    public Response findCustomerByConditions(@RequestBody CustomerInfoQueryDTO customerInfoQueryDTO) {

        LoginUserDTO loginUserDTO = getLoginDTO();
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        // String employeeId = customerInfoQueryDTO.getEmployeeId();
        // if (StringUtils.isEmpty(employeeId)) {
        //    return failure("员工编号不能为空");
        // }
        customerInfoQueryDTO.setGradeId(loginUserDTO.getGradeId());
        customerInfoQueryDTO.setEmployeeId(loginUserDTO.getUserId());
//        customerInfoQueryDTO.setDataDeptIdList(UtilMethod.generatorAvailableDeptIds(loginUserDTO));
        PageResponse pageResponse = customerShareService.customerInfoByDataRoleList(customerInfoQueryDTO);
        return new Response().success(pageResponse);

    }
    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据主键id查询客户",response = CustomerInfo.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 0) {
                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            CustomerInfoQueryVo customerInfo = customerShareService.queryById(id);
            return ObjTool.isNotNull(customerInfo) ? success(customerInfo) : failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }

    /**
     * 新增客户
     * @param customerInfo
     * @return Response
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_ADD)
    @ApiOperation(value = "新增客户",response = CustomerInfo.class)
    @PutMapping()
    public Response insert(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        List<ContactInfo> contactList = customerInfo.getContactList();
        if(null==contactList){
            return failure("客户联系人信息不能为空");
        }

        if (ObjTool.isNotNull(contactList)) {
            for (ContactInfo contactInfo : contactList) {
                if (contactShareService.maxFormat(contactInfo.getFamilyInfo(), 128)) {
                    return failure("家庭状况"+ MAX_PARAM+"最长不能超过"+128);
                }
                if (contactShareService.maxFormat(contactInfo.getMajor(), 64)) {
                    return failure("专业"+ MAX_PARAM+"最长不能超过"+64);
                }
                if (contactShareService.maxFormat(contactInfo.getUniversity(), 64)) {
                    return failure("毕业学校"+ MAX_PARAM+"最长不能超过"+64);
                }
                if (contactShareService.maxFormat(contactInfo.getOfficePlane(), 32)) {
                    return failure("办工座机"+ MAX_PARAM+"最长不能超过"+32);
                }
                if (contactShareService.maxFormat(contactInfo.getOther(), 128)) {
                    return failure("其他"+ MAX_PARAM+"最长不能超过"+128);
                }
            }
        }

        //查询客户最大编码
        String maxCode = customerShareService.getMaxCustomerId();
        //如果查询数据库没有客户记录，设置maxCode默认值
        if(null==maxCode){
            maxCode="KH202001010000";
        }
        //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
        String customerId = customerShareService.getKhCode(maxCode);
        customerInfo.setCustomerId(customerId);
        String customerName = customerInfo.getCustomerName();
        String employeeId = customerInfo.getEmployeeId();

        if(null==employeeId){
            return failure("销售负责人不能为空");
        }
        if(null==customerId){
            return failure("客户编号不能为空");
        }
        if(null==customerName){
            return failure("客户名称不能为空");
        }
        int customerIdCount = customerShareService.checkUnique(customerId);
        if (customerIdCount>0)
        {
            return failure("客户编号不能重复");
        }
        CustomerInfo customerInfos = null;
        try {
            customerInfos = customerShareService.insert(customerInfo);
        } catch (MsgException e) {
          return new Response<>().failure(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response<>().failure("返回客户信息错误");
        }
        return  success(customerInfos);
    }

    /**
     * 修改客户信息
     * @param customerInfo
     * @param result
     * @return Response
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_EDIT)
    @ApiOperation(value = "修改客户",response = CustomerInfo.class)
    @PatchMapping(value = "/customer")
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
                if (contactShareService.maxFormat(contactInfo.getUniversity(), 64)) {
                    return failure("毕业学校"+ MAX_PARAM+"最长不能超过"+64);
                }
                if (contactShareService.maxFormat(contactInfo.getOfficePlane(), 32)) {
                    return failure("办工座机"+ MAX_PARAM+"最长不能超过"+32);
                }
                if (contactShareService.maxFormat(contactInfo.getMajor(), 64)) {
                    return failure("专业"+ MAX_PARAM+"最长不能超过"+64);
                }
                if (contactShareService.maxFormat(contactInfo.getOther(), 128)) {
                    return failure("其他"+ MAX_PARAM+"最长不能超过"+128);
                }
                if (contactShareService.maxFormat(contactInfo.getFamilyInfo(), 128)) {
                    return failure("家庭状况"+ MAX_PARAM+"最长不能超过"+128);
                }
            }
        }

        return customerShareService.update(customerInfo) > 0 ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 修改客户信息
     * @param customerInfo
     * @param result
     * @return Response
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_COPY)
    @ApiOperation(value = "复制客户")
    @PostMapping(value = "/copy")
    public Response copy(@Valid @RequestBody CopyCustomerVO customerInfo) {

      LoginUserDTO login = getLoginDTO();

      if (!ObjTool.isNotNull(customerInfo.getCustomerIds())) {
        return failure("客户编号不能为空");
      }

      customerShareService.copyCustomers(login.getUserId(), customerInfo.getCustomerIds());
      return success();
    }

    /**
     * 根据id删除客户
     * @return
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_DELETE)
    @ApiOperation("删除单个客户")
    @DeleteMapping(value = "/{id}")
    public Response delete(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {


        return customerShareService.deleteById(id) ? success() : failure(DELETE_ERROR);
    }

    /**
     * 根据id批量删除客户
     * @return
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_DELETE)
    @ApiOperation("批量删除客户")
    @DeleteMapping(value = "/delAll")
    @ApiImplicitParam(name="ids", value="id以逗号分割", required=true, paramType="query" ,allowMultiple=true, dataType = "String")
    public Response deleteIds(String ids) {
        String[] split = ids.split(",");
        //id列表的非空判断
        if (!ObjTool.isNotNull(split)) {
            return failure(NO_PARAM_ERROR);
        }
        List<String> customerIdList = customerShareService.batchGetCustomerId(split);
        if (!ObjTool.isNotNull(customerIdList)) {
            return failure(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage() + "，角色编号错误");
        }

        return customerShareService.deleteByIds(customerIdList)? success() : failure(DELETE_ERROR);

    }


    /**
     * 客户联系人个数
     * @param customerInfo
     * @param result
     * @return Response
     */
    @ApiOperation(value = "客户联系人个数(传customerId)",response = CustomerInfo.class)
    @RequestMapping(value = "/contactCount", method = RequestMethod.POST)
    public Response contactCount(@Valid @RequestBody CustomerInfo customerInfo, BindingResult result,
                                 @CurrentUser LoginUserDTO loginUserDTO) {
        if (result.hasErrors()) {
            return failure(result);
        }
        customerInfo.setEmployeeId(loginUserDTO.getUserId());
        customerInfo.setDataDeptIdList(UtilMethod.generatorAvailableDeptIds(loginUserDTO));
        Map<String, Object> map = new HashMap<>(16);
        Integer contactCount = customerShareService.customersCount(customerInfo);
        if (!ObjTool.isNotNull(contactCount)) {
            return failure(QUERY_ERROR);
        }
        map.put("contactCount", contactCount);
        return success(map);
    }

    /**
     * 联系人collection关联映射分页处理
     * @param customerInfoQueryDTO
     * @param customerInfos
     * @return Response
     */
    public List<CustomerInfoQueryDTO> getCustomerInfoQueryDTOS(CustomerInfoQueryDTO customerInfoQueryDTO, List<CustomerInfoQueryDTO> customerInfos) {
        //分页参数不传时候
        if (!ObjTool.isNotNull(customerInfoQueryDTO.getPage())||!ObjTool.isNotNull(customerInfoQueryDTO.getRows())) {
            return customerInfos;
        }

        //封装分页后结果集
        List<CustomerInfoQueryDTO> customerInfoPage = new ArrayList<CustomerInfoQueryDTO>();
        //联系人collection关联映射分页问题处理
        int page = customerInfoQueryDTO.getPage();
        int rows = customerInfoQueryDTO.getRows();
        int currIdx = (page > 1 ? (page -1) * rows : 0);
        for (int i = 0; i < rows && i < customerInfos.size() - currIdx; i++) {
            CustomerInfoQueryDTO customerInfo = customerInfos.get(currIdx + i);
            customerInfoPage.add(customerInfo);
        }
        return customerInfoPage;
    }

    @ApiOperation(value = "客户信息名称、编码列表",response = CustomerNameIdDTO.class)
    @RequestMapping(value = "/getCustomerNameId", method = RequestMethod.GET)
    public List<CustomerNameIdDTO> getCustomerNameId(CustomerNameIdDTO customerNameIdDTO) {
        return  customerShareService.getCustomerNameId(customerNameIdDTO);
    }


    /**
     * excel导入客户和联系人
     * @return Response
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_IMPORT)
    @ApiOperation(value = "导入共享客户和联系人")
    @PostMapping(value = "/import")
    @OpLog(module = Module.CUSTOMER_PROPERTIES, opType = Type.IMPORT)
    public Response importCustomersAndContacts(@RequestParam("file") MultipartFile file,
                                               @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {

//        InputStream is = file.getInputStream();
//        XSSFSheet sheet = ExcelUtil.getOneSheetByInputStream(is, 0);
//        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//        if (physicalNumberOfRows == 2) {
//            return failure("导入信息为空，请重新填入导入信息");
//        }
//
//        Map emptyMap = importExportService.checkEmpty(sheet,loginUserDTO);
//        if (ObjTool.isNotNull(emptyMap)) {
//            for (Object keyObj : emptyMap.keySet()) {
//                Integer keyInt = Integer.parseInt(keyObj.toString());
//                String val = emptyMap.get(keyInt+"").toString();
//                keyInt = keyInt - 1;
//                return failure("第"+ keyInt+"行的"+val + NO_PARAM);
//            }
//        }
//
//        Map maxMap = importExportService.checkMax(sheet,loginUserDTO);
//        if (ObjTool.isNotNull(maxMap)) {
//            for (Object keyObj : maxMap.keySet()) {
//                Integer keyInt = Integer.parseInt(keyObj.toString());
//                String val = maxMap.get(keyInt+"").toString();
//                keyInt = keyInt - 1;
//                return failure("第"+ keyInt+"行的"+val + MAX_PARAM);
//            }
//        }
//
//        Map formatMap = importExportService.checkFormat(sheet, loginUserDTO);
//        if (ObjTool.isNotNull(formatMap)) {
//            for (Object keyObj : formatMap.keySet()) {
//                Integer keyInt = Integer.parseInt(keyObj.toString());
//                String val = formatMap.get(keyInt+"").toString();
//                keyInt = keyInt - 1;
//                return failure("第"+ keyInt+"行的"+val + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
//            }
//        }
//
//        Map checkPersonAccount = importExportService.checkPersonAccount(sheet, loginUserDTO);
//        if (ObjTool.isNotNull(checkPersonAccount)) {
//            for (Object keyObj : checkPersonAccount.keySet()) {
//                Integer keyInt = Integer.parseInt(keyObj.toString());
//                String val = checkPersonAccount.get(keyInt+"").toString();
//                return failure(val);
//            }
//        }
//
//        Map checkMobilePhoneMap = importExportService.checkMobilePhone(sheet, loginUserDTO);
//        if (ObjTool.isNotNull(checkMobilePhoneMap)) {
//            for (Object keyObj : checkMobilePhoneMap.keySet()) {
//                Integer keyInt = Integer.parseInt(keyObj.toString());
//                String val = checkMobilePhoneMap.get(keyInt+"").toString();
//                keyInt = keyInt - 1;
//                return failure("第"+ keyInt+"行的"+val);
//            }
//        }

//        List<ImportErrorMessage> result = importExportService.importCustomersShareAndContacts(sheet, loginUserDTO);

//        InputStream is = file.getInputStream();
//        Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
//        customerShareService.importCustomer(workbook, loginUserDTO);
        InputStream is = file.getInputStream();
        Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
        List<String> stringList = customerShareService.validateExcel(workbook);
        if (stringList != null && !stringList.isEmpty()) {
          return warning("数据验证失败", stringList);
        }
        return success(customerShareService.importCustomer(workbook, getLoginDTO()));
    }


    /**
     * excel导入客户
     *
     * xielf
     * @return Response
     */
    @MethodPermission(menuIdArray = C_SHARE_CUSTOMER_EXPORT)
    @ApiOperation(value = "excel导出客户以及联系人")
    @GetMapping(value = "/export")
    @OpLog(module = OpLog.Module.CUSTOMER_PROPERTIES, opType = OpLog.Type.EXPORT)
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

      final List<ExportCustomerInfoQueryVo> exportCustomerInfoQueryVos = customerShareService.exportCustomer(set, loginUserDTO.getUserId());

      if(exportCustomerInfoQueryVos==null || exportCustomerInfoQueryVos.size()==0){
        throw  new MsgException("无数据,拒绝操作");
      }
      else{

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String fileName = "客户资源池信息.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        return out -> {

          try (Workbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
            Sheet sheet = workbook.createSheet("客户信息");
            Row row = sheet.createRow(0);
            for (int i = 0; i < customerTitleList.size(); i++) {
              row.createCell(i).setCellValue(customerTitleList.get(i));
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
            throw new MsgException(e.getMessage());
          }
        };
      }
    }

}