package com.sefonsoft.oa.service.importexport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.sefonsoft.oa.dao.contact.ContactInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerSaleRefDao;
import com.sefonsoft.oa.dao.customer.CustomerShareDao;
import com.sefonsoft.oa.dao.user.SysUserDao;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.importexport.ImportErrorMessage;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.service.customer.CustomerShareService;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.ExcelUtil;
import com.sefonsoft.oa.system.utils.ObjTool;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/29 9:46
 * @description：导入导出service实现类
 * @modified By：
 */
@Service
@Slf4j
public class ImportExportServiceImpl implements ImportExportService {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private CustomerInfoDao customerInfoDao;
    @Resource
    private ContactInfoDao contactInfoDao;
    @Resource
    private CustomerSaleRefDao customerSaleRefDao;
    @Resource
    private CustomerInfoService customerInfoService;
    
    @Resource
    CustomerShareInsertService customerShareInsertService;
    
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat sdfxie = new SimpleDateFormat("yyyy/MM/dd");

    public static final List<String> list = Arrays.asList("安监",
                                        "博物",
                                        "部委",
                                        "财政",
                                        "畜牧",
                                        "传媒",
                                        "地震",
                                        "电力",
                                        "电网",
                                        "发改委",
                                        "法院",
                                        "法制办",
                                        "防震减灾",
                                        "港口",
                                        "工会",
                                        "工商",
                                        "工信部",
                                        "公安",
                                        "管委会",
                                        "广电",
                                        "规划局",
                                        "国企",
                                        "国土",
                                        "国资委",
                                        "海关",
                                        "航空",
                                        "航运",
                                        "环保",
                                        "环境",
                                        "机场",
                                        "纪检委",
                                        "监狱",
                                        "检察",
                                        "交通",
                                        "教育",
                                        "戒毒",
                                        "金融",
                                        "经信",
                                        "军工",
                                        "勘测",
                                        "科研",
                                        "口岸",
                                        "粮食",
                                        "林业",
                                        "旅游",
                                        "煤监",
                                        "民政",
                                        "能源",
                                        "农业",
                                        "企业",
                                        "气象",
                                        "人社",
                                        "商委",
                                        "审计",
                                        "食药监",
                                        "水利",
                                        "水文",
                                        "水务",
                                        "税务",
                                        "司法",
                                        "体育",
                                        "统计",
                                        "卫计委",
                                        "文旅",
                                        "武警",
                                        "物流",
                                        "消防",
                                        "烟草",
                                        "研究院",
                                        "药监局",
                                        "医疗",
                                        "应急",
                                        "园区",
                                        "运营商",
                                        "政法委",
                                        "政府",
                                        "质监",
                                        "住建",
                                        "组织部",
                                        "其他");

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean importCustomersAndContacts(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        boolean insertFlag = false;


        try {
            // 定义 row、cell
            XSSFRow row;
            String cell;
            CustomerInfo customerInfo = null;
            CustomerSaleRef customerSaleRef = null;
            List<ContactInfo> contactInfoList = null;
            //循环获取除第一二行之外的数据
            for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                //获取每行第一个元素，判断是否为空，如果不为空则读取到的是新的客户和新的联系人，如果为空，则读取到的是一个新的联系人，这个联系人需要被叠加到上一个客户的联系人列表上
                String cellStr = ExcelUtil.getCellStr(row, 0);
                if (ObjTool.isNotNull(cellStr)) {
                    //将数据插入到数据库中，并置空customerInfo和contactInfoList
                    insertFlag = insertCustomerAndBatchContacts(customerInfo, contactInfoList, customerSaleRef, loginUserDTO);
                    //清除联系人列表和客户列表，读取数据，并重新赋予一个客户实体和联系人实体
                    customerInfo = null;
                    customerSaleRef = null;
                    contactInfoList = null;
                    customerInfo = new CustomerInfo();
                    customerSaleRef = new CustomerSaleRef();
                    contactInfoList = new ArrayList<>();
                    customerInfo = setCustomerInfo(row, customerInfo);
                    customerSaleRef = setCustomerSaleRef(row, customerSaleRef);
                    contactInfoList = setContactInfo(row, contactInfoList, customerSaleRef.getEmployeeId());
                    if ((i + 1) == sheet.getPhysicalNumberOfRows()) {
                        insertFlag = insertCustomerAndBatchContacts(customerInfo, contactInfoList, customerSaleRef, loginUserDTO);
                    }
                } else {
                    //继续往原有的contactInfoList中添加数据
                    contactInfoList = setContactInfo(row, contactInfoList, customerInfo.getEmployeeId());
                    if ((i + 1) == sheet.getPhysicalNumberOfRows()) {
                        insertFlag = insertCustomerAndBatchContacts(customerInfo, contactInfoList, customerSaleRef, loginUserDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertFlag;
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ImportErrorMessage> importCustomersShareAndContacts(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
      

      List<ImportErrorMessage> failureList = new LinkedList<>();

      try {
          // 定义 row、cell
          XSSFRow row;
          String cell;
          CustomerInfo customerInfo = null;
          // CustomerSaleRef customerSaleRef = null;
          List<ContactInfo> contactInfoList = null;
          //循环获取除第一二行之外的数据
          
          
          int tryRow = 0;
          
          String trySheet = null;
          
          for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
              row = sheet.getRow(i);
              //获取每行第一个元素，判断是否为空，如果不为空则读取到的是新的客户和新的联系人，如果为空，则读取到的是一个新的联系人，这个联系人需要被叠加到上一个客户的联系人列表上
              String cellStr = ExcelUtil.getCellStr(row, 0);
              
              if (ObjTool.isNotNull(cellStr)) {
                //将数据插入到数据库中，并置空customerInfo和contactInfoList
                // 插入数据
                // 插入上一条
                try {
                  customerShareInsertService.insertCustomerShareAndBatchContacts(customerInfo, contactInfoList, loginUserDTO);
                } catch (Exception e) {
                  // log.warn(e.getMessage(), e);
                  // String errorMsg = String.format("无法添加 Sheet：%s，Row：%s 记录，ERROR：%s",trySheet, tryRow, e.getMessage());
                  ImportErrorMessage result = new ImportErrorMessage();
                  result.setMessage(e.getMessage());
                  result.setSheet(trySheet);
                  result.setRow(tryRow);
                  failureList.add(result);
                  log.warn("{}", result);
                }
                //清除联系人列表和客户列表，读取数据，并重新赋予一个客户实体和联系人实体
                customerInfo = null;
                tryRow = row.getRowNum() + 1;
                trySheet = row.getSheet().getSheetName();
                // customerSaleRef = null;
                contactInfoList = null;
                customerInfo = new CustomerInfo();
                // customerSaleRef = new CustomerSaleRef();
                contactInfoList = new ArrayList<>();
                customerInfo = setCustomerInfo(row, customerInfo);
                // customerSaleRef = setCustomerSaleRef(row, customerSaleRef);
                
                // 设置操作人
                String employeeId = ExcelUtil.getCellStr(row, 6);
                String employeeName = ExcelUtil.getCellStr(row, 5);
                if (ObjTool.isNotNull(employeeId)) {
                  customerInfo.setOperator(employeeId);
                } else {
                  if (ObjTool.isNotNull(employeeName)) {
                    employeeId = sysUserDao.getUserIdByUserName(employeeName);
                    if (ObjTool.isNotNull(employeeId)) {
                      customerInfo.setOperator(employeeId);
                    } else {
                      customerInfo.setOperator(loginUserDTO.getUserId());
                    }
                  }
                }
                
                final String fempId = employeeId;
                
                // 创建人为当前
                customerInfo.setOperator(fempId);
                
                // 联系人创建人为当前
                contactInfoList = setContactInfo(row, contactInfoList, customerInfo.getOperator());
                contactInfoList.forEach(ci -> {
                  ci.setOperator(fempId);
                });
                
                if ((i + 1) == sheet.getPhysicalNumberOfRows()) {
                  // 插入数据
                  try {
                    customerShareInsertService.insertCustomerShareAndBatchContacts(customerInfo, contactInfoList, loginUserDTO);
                  } catch (Exception e) {
                    // log.warn(e.getMessage(), e);
                    // String errorMsg = String.format("无法添加 Sheet：'%s'，Row：'%s' 处记录，ERROR：%s",trySheet, tryRow, e.getMessage());
                    //failureList.add(errorMsg);
                    ImportErrorMessage result = new ImportErrorMessage();
                    result.setMessage(e.getMessage());
                    result.setSheet(trySheet);
                    result.setRow(tryRow);
                    failureList.add(result);
                    log.warn("{}", result);
                  }
                }
              } else {
                //继续往原有的contactInfoList中添加数据
                contactInfoList = setContactInfo(row, contactInfoList, customerInfo.getEmployeeId());
                if ((i + 1) == sheet.getPhysicalNumberOfRows()) {
                  // 插入数据
                  try {
                    customerShareInsertService.insertCustomerShareAndBatchContacts(customerInfo, contactInfoList, loginUserDTO);
                  } catch (Exception e) {
                    // log.warn(e.getMessage(), e);
                    // String errorMsg = String.format("无法添加 Sheet：%s，Row：%s 记录，ERROR：%s",trySheet, tryRow, e.getMessage());
                    // failureList.add(errorMsg);
                    ImportErrorMessage result = new ImportErrorMessage();
                    result.setMessage(e.getMessage());
                    result.setSheet(trySheet);
                    result.setRow(tryRow);
                    failureList.add(result);
                    log.warn("{}", result);
                  }
                }
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return failureList;
    }

    @Override
    public Map<String, String> checkEmpty(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            //算出第一个到第四个必填的字段是否为空，客户名称到单位地址
            for (int j = 0; j <= 4; j++) {
                String cellStr = ExcelUtil.getCellStr(row, j);
                if (!ObjTool.isNotNull(cellStr)) {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "";
                    if (j == 0) {
                        key = "客户名称";
                    } else if (j == 1) {
                        key = "省市区";
                    } else if (j == 2) {
                        key = "单位性质";
                    } else if (j == 3) {
                        key = "所属行业";
                    } else if (j == 4) {
                        key = "单位地址";
                    }
                    if (ObjTool.isNotNull(key)) {
                        map.put(i + "", key);
                        return map;
                    }
                }
            }

            //算出销售负责人和销售负责人工号是否有空的，至少其中有一个不为空
            String employeeName = ExcelUtil.getCellStr(row, 5);
            String employeeId = ExcelUtil.getCellStr(row, 6);

            if ((!ObjTool.isNotNull(employeeId)) && (!ObjTool.isNotNull(employeeName))) {
                HashMap<String, String> map = new HashMap<>();
                map.put(i + "", "销售负责人或销售负责人工号");
                return map;
            }

            //算出第一个到第四个必填的字段是否为空，姓名到电话
            for (int k = 11; k <= 15; k++) {
                String cellStr = ExcelUtil.getCellStr(row, k);
                if (!ObjTool.isNotNull(cellStr)) {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "";
                    if (k == 11) {
                        key = "联系人的姓名";
                    } else if (k == 12) {
                        key = "性别";
                    } else if (k == 13) {
                        key = "部门";
                    } else if (k == 14) {
                        key = "职务";
                    } else if (k == 15) {
                        key = "联系人的手机号码";
                    }
                    if (ObjTool.isNotNull(key)) {
                        map.put(i + "", key);
                        return map;
                    }
                }
            }

        }
        return null;
    }

    @Override
    public Map<String, String> checkMax(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            //算出第一个到第四个必填的字段是否为空，客户名称到单位地址
            for (int j = 0; j <= 4; j++) {
                String cellStr = ExcelUtil.getCellStr(row, j);
                HashMap<String, String> map = new HashMap<>();
                String key = "";
                if (j == 0 && cellStr.length() > 64) {
                    map.put(i + "", "客户名称");
                    return map;
                }
                if (j == 1) {
                    String[] split = cellStr.split("/");
                    int length = split.length;
                    if (length == 1) {
                        String s = split[0];
                        if (s.length() > 16) {
                            map.put(i + "", "省");
                            return map;
                        }
                    }
                    if (length == 2) {
                        String s = split[0];
                        if (s.length() > 16) {
                            map.put(i + "", "省");
                            return map;
                        }
                        String s1 = split[1];
                        if (s1.length() > 16) {
                            map.put(i + "", "市");
                            return map;
                        }
                    }
                    if (length == 3) {
                        String s = split[0];
                        if (s.length() > 16) {
                            map.put(i + "", "省");
                            return map;
                        }
                        String s2 = split[2];
                        if (s2.length() > 16) {
                            map.put(i + "", "区");
                            return map;
                        }
                        String s1 = split[1];
                        if (s1.length() > 16) {
                            map.put(i + "", "市");
                            return map;
                        }
                    }
                    key = "省市区";
                }
                if (j == 4 && cellStr.length() > 64) {
                    map.put(i + "", "单位地址");
                    return map;
                }
            }

            //算出第一个到第四个必填的字段是否为空，姓名到电话
            for (int k = 11; k <= 15; k++) {
                String cellStr = ExcelUtil.getCellStr(row, k);
                if (!ObjTool.isNotNull(cellStr)) {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "";
                    if (k == 11 && cellStr.length() > 20) {
                        map.put(i + "", "联系人的姓名");
                        return map;
                    }
                    if (k == 13 && cellStr.length() > 20) {
                        map.put(i + "", "联系人的部门");
                        return map;
                    }
                    if (k == 14 && cellStr.length() > 16) {
                        map.put(i + "", "联系人的职务");
                        return map;
                    }
                    if (k == 15 && cellStr.length() > 13) {
                        map.put(i + "", "联系人的电话");
                        return map;
                    }
                }
            }

            HashMap<String, String> map = new HashMap<>();
            if (ObjTool.isNotNull(row.getCell(7))) {
                String tel = ExcelUtil.getNumericCellValue(row.getCell(7));
                if (ObjTool.isNotNull(tel)) {
                    if (tel.length() > 13) {
                        map.put(i + "", "客户电话");
                        return map;
                    }
                }
            }
            String zipCode = ExcelUtil.getCellStr(row, 8);
            if (ObjTool.isNotNull(zipCode)) {
                if (zipCode.length() > 64) {
                    map.put(i + "", "邮编");
                    return map;
                }
            }
            String website = ExcelUtil.getCellStr(row, 9);
            if (ObjTool.isNotNull(website)) {
                if (website.length() > 64) {
                    map.put(i + "", "网址");
                    return map;
                }
            }
            String fax = ExcelUtil.getCellStr(row, 10);
            if (ObjTool.isNotNull(fax)) {
                if (fax.length() > 20) {
                    map.put(i + "", "传真");
                    return map;
                }
            }

            String other = ExcelUtil.getCellStr(row, 22);
            if (ObjTool.isNotNull(other)) {
                if (other.length() > 128) {
                    map.put(i + "", "其他");
                    return map;
                }
            }

            String familyInfo = ExcelUtil.getCellStr(row, 21);
            if (ObjTool.isNotNull(familyInfo)) {
                if (familyInfo.length() > 128) {
                    map.put(i + "", "家庭状况");
                    return map;
                }
            }

            String major = ExcelUtil.getCellStr(row, 19);
            if (ObjTool.isNotNull(major)) {
                if (major.length() > 64) {
                    map.put(i + "", "专业");
                    return map;
                }
            }

            String university = ExcelUtil.getCellStr(row, 18);
            if (ObjTool.isNotNull(university)) {
                if (university.length() > 64) {
                    map.put(i + "", "毕业学校");
                    return map;
                }
            }

            String officePlane = ExcelUtil.getCellStr(row, 17);
            if (ObjTool.isNotNull(officePlane)) {
                if (officePlane.length() > 32) {
                    map.put(i + "", "办公座机");
                    return map;
                }
            }

            String email = ExcelUtil.getCellStr(row, 16);
            if (ObjTool.isNotNull(email)) {
                if (email.length() > 32) {
                    map.put(i + "", "联系人的邮箱");
                    return map;
                }
            }

        }
        return null;
    }

    @Override
    public Map<String, String> checkMobilePhone(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        HashMap<String, String> map = new HashMap<>();
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            String phone = ExcelUtil.getNumericCellValue(row.getCell(15));

            if (phone.length() != 11) {
                map.put(i + "", "联系人的电话必须为11位数");
                return map;
            } else {
//                Pattern p = Pattern.compile(regex);
//                Matcher m = p.matcher(phone);
//                boolean isMatch = m.matches();
                boolean isMatch = isPhone(phone);
                if (!isMatch) {
                    map.put(i + "", "联系人的电话不符合规范");
                    return map;
                }
            }

            XSSFCell cell = row.getCell(8);
            if (ObjTool.isNotNull(cell) && !StringUtils.isEmpty(cell.toString().trim())) {
                String s = ExcelUtil.numOfImport(cell);
                boolean postCode = isPostCode(s);
                if (!postCode) {
                    map.put(i + "", "客户的邮编格式不符合规范");
                    return map;
                }
            }

            //客户电话校验，座机和手机号码同时校验，如果都为false的话，那就格式有问题
            XSSFCell kehuTelObj = row.getCell(7);
            if (ObjTool.isNotNull(kehuTelObj) && !StringUtils.isEmpty(kehuTelObj.toString().trim())) {
                String kehuTel = ExcelUtil.getNumericCellValue(kehuTelObj);
                boolean flag = false;

                //手机号码校验
//                Pattern p = Pattern.compile(regex);
//                Matcher m = p.matcher(kehuTel);
//                boolean isMatch = m.matches();
                boolean isMatch = isPhone(kehuTel);

                //座机校验
                boolean fixedPhone = isFixedPhone(kehuTel);

                //只要其中有一个为true，就认为是正确的
                flag = isMatch || fixedPhone;

                if (!flag) {
                    map.put(i + "", "客户的电话不符合规范");
                    return map;
                }
            }

            //联系人的办公座机校验
            XSSFCell lianxirenzhuojiObj = row.getCell(17);
            if (ObjTool.isNotNull(lianxirenzhuojiObj) && !StringUtils.isEmpty(lianxirenzhuojiObj.toString().trim())) {
                String lianxirenzhuoji = ExcelUtil.numOfImport(lianxirenzhuojiObj);
                boolean fixedPhone = isFixedPhone(lianxirenzhuoji);
                if (!fixedPhone) {
                    map.put(i + "", "联系人的办公座机不符合规范");
                    return map;
                }
            }

            //联系人的办公座机校验
            XSSFCell lianxirenEmailObj = row.getCell(16);
            if (ObjTool.isNotNull(lianxirenEmailObj) && !StringUtils.isEmpty(lianxirenEmailObj.toString().trim())) {
                String lianxirenEmail = lianxirenEmailObj.toString();
                if (ObjTool.isNotNull(lianxirenEmail)) {
                    boolean fixedPhone = isEmail(lianxirenEmail);
                    if (!fixedPhone) {
                        map.put(i + "", "联系人的邮编不符合规范");
                        return map;
                    }
                }
            }

            //客户的传真校验
            XSSFCell chuanzhenObj = row.getCell(10);
            if (ObjTool.isNotNull(chuanzhenObj) && !StringUtils.isEmpty(chuanzhenObj.toString().trim())) {
                String chuanzhen = ExcelUtil.numOfImport(chuanzhenObj);
                if (ObjTool.isNotNull(chuanzhen)) {
                    boolean fixedPhone = isFixedPhone(chuanzhen);
                    if (!fixedPhone) {
                        map.put(i + "", "客户的传真不符合规范");
                        return map;
                    }
                }
            }

            //联系人的生日校验
            XSSFCell shengriObj = row.getCell(20);
            if (ObjTool.isNotNull(shengriObj) && !StringUtils.isEmpty(shengriObj.toString().trim())) {
                String str = ExcelUtil.parseString(shengriObj);
                /*String[] split = str.split("-");
                if (!ObjTool.isNotNull(split)) {
                    map.put(i + "", "联系人的生日不符合规范");
                    return map;
                }
                String day = split[0];
                String month = split[1];
                String year = split[2];*/

                Pair<Boolean,Date> date = isErrorDate(str);

                if (date.getKey()) {
                    map.put(i + "", "联系人的生日不符合规范 "+str);
                    return map;
                }

            }

        }
        return null;
    }

    @Override
    public Map<String, String> checkPersonAccount(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            HashMap<String, String> map = new HashMap<>();
            XSSFCell employeeNameObj = row.getCell(5);

            if (ObjTool.isNotNull(employeeNameObj)) {
                String employeeName = employeeNameObj.toString();
                String userIdByUserName = "";
                if (ObjTool.isNotNull(employeeName)) {
                    userIdByUserName = sysUserDao.getUserIdByUserName(employeeName);
                    if (!ObjTool.isNotNull(userIdByUserName)) {
                        map.put(i + "", "第" + (i - 1) + "行数据的销售负责人工号找不到相应的账号，请填写正确的工号");
                        return map;
                    }
                }
            }

            XSSFCell employeeIdObj = row.getCell(6);
            if (ObjTool.isNotNull(employeeIdObj)) {
                String employeeId = employeeIdObj.toString();
                if (ObjTool.isNotNull(employeeId)) {
                    String pwd = sysUserDao.getDbPwdByUserId(employeeId);
                    if (!ObjTool.isNotNull(pwd)) {
                        map.put(i + "", "第" + (i - 1) + "行数据的销售负责人工号找不到相应的账号,请填写正确的工号");
                        return map;
                    }
                }
            }

        }

        return null;
    }

    /**
     * 校验邮编
     *
     * @param postCode
     * @return
     */
    public static boolean isPostCode(String postCode) {
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);
    }

    /**
     * 区号+座机号码+分机号码
     *
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

    @Override
    public Map<String, String> checkFormat(XSSFSheet sheet, LoginUserDTO loginUserDTO) {
        HashMap<String, String> map = new HashMap<>();

        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);

            String sexContact = row.getCell(12).toString();
            if (!ObjTool.isNotNull(sexContact)) {
                map.put(i + "", "性别");
                return map;
            } else if (ObjTool.isNotNull(sexContact)) {
                if ((!sexContact.equals("男")) && (!sexContact.equals("女"))) {
                    map.put(i + "", "性别");
                    return map;
                }
            }


            //单位性质校验
            String companyStatmentStr = row.getCell(2).toString();
            if ((!"最终用户".equals(companyStatmentStr)) && (!"合作伙伴".equals(companyStatmentStr)) && (!"最终用户&合作伙伴".equals(companyStatmentStr))) {
                map.put(i + "", "单位性质");
                return map;
            }

            //所属行业校验
            String hangye = row.getCell(3).toString();

            if (ObjTool.isNotNull(hangye) && (!list.contains(hangye))) {
                map.put(i + "", "所属行业");
                return map;
            }


//            XSSFCell employeeNameObj = row.getCell(5);
//
//            if (ObjTool.isNotNull(employeeNameObj)) {
//                String employeeName = employeeNameObj.toString();
//                String userIdByUserName = sysUserDao.getUserIdByUserName(employeeName);
//                if (!ObjTool.isNotNull(userIdByUserName)) {
//                    map.put(i + "", "销售负责人");
//                    return map;
//                }
//                String employeeId = row.getCell(6).toString();
//                if (ObjTool.isNotNull(employeeId) && !employeeId.equals(userIdByUserName)) {
//                    map.put(i + "", "销售负责人和工号");
//                    return map;
//                }
//            }
//
//            String employeeId = row.getCell(6).toString();
//            if (ObjTool.isNotNull(employeeId)) {
//                String pwd = sysUserDao.getDbPwdByUserId(employeeId);
//                if (!ObjTool.isNotNull(pwd)) {
//                    map.put(i + "", "销售负责人工号");
//                    return map;
//                }
//            }


        }
        return null;
    }


    /**
     * 导入客户和联系人excel的时候添加客户和联系人数据
     *
     * @param customerInfo
     * @param contactInfoList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean insertCustomerAndBatchContacts(CustomerInfo customerInfo, List<ContactInfo> contactInfoList, CustomerSaleRef customerSaleRef, LoginUserDTO loginUserDTO) {
        Date date = new Date();
        if (ObjTool.isNotNull(customerInfo, contactInfoList)) {
            //生成customerId并判断customerId是否是重复的
            String customerId = generateCustomerId();
            if (ObjTool.isNotNull(customerId)) {
                customerInfo.setCustomerId(customerId);
                customerInfo.setLastTime(date);
                customerInfo.setCreateTime(date);
                // 没有提供操作员，那么默认当前
                if (customerInfo.getOperator() == null) {
                  customerInfo.setOperator(loginUserDTO.getUserId());
                }
                int insertCount = customerInfoDao.insert(customerInfo);
                if (customerSaleRef != null) {
                  
                  customerSaleRef.setLastTime(date);
                  customerSaleRef.setCreateTime(date);
                  customerSaleRef.setCustomerId(customerId);
                  customerSaleRef.setOperator(loginUserDTO.getUserId());
                  customerSaleRefDao.insert(customerSaleRef);
                }
                contactInfoList.forEach(bean -> {
                    bean.setCustomerId(customerId);
                    bean.setLastTime(date);
                    bean.setCreateTime(date);
                    // 没有提供操作员，那么默认当前
                    if (bean.getOperator() == null) {
                      bean.setOperator(loginUserDTO.getUserId());
                    }
                    bean.setRole(1);
                });
                boolean flag = contactInfoDao.batchInsert(contactInfoList);
                return insertCount > 0 && flag;
            }
        }
        return false;
    }
    
    private List<ContactInfo> setContactInfo(XSSFRow row, List<ContactInfo> contactInfoList, String employeeId) throws ParseException {
        String contactName = ExcelUtil.getCellStr(row, 11);
        String contactSexStr = ExcelUtil.getCellStr(row, 12);
        String deptName = ExcelUtil.getCellStr(row, 13);
        String job = ExcelUtil.getCellStr(row, 14);
        String telphone = ExcelUtil.getNumericCellValue(row.getCell(15));
        String email = ExcelUtil.getCellStr(row, 16);

        String officePlane = ExcelUtil.getCellStr(row, 17);
        String university = ExcelUtil.getCellStr(row, 18);
        String major = ExcelUtil.getCellStr(row, 19);
        String birthStr = ExcelUtil.getCellStr(row, 20);
        Date birthday = null;
        if (ObjTool.isNotNull(birthStr)) {
            /*String[] split = birthStr.split("-");
            String day = split[0];
            String month = split[1];
            String year = split[2];
            if ("一月".equals(month)) {
                month = "01";
            } else if ("二月".equals(month)) {
                month = "02";
            } else if ("三月".equals(month)) {
                month = "03";
            } else if ("四月".equals(month)) {
                month = "04";
            } else if ("五月".equals(month)) {
                month = "05";
            } else if ("六月".equals(month)) {
                month = "06";
            } else if ("七月".equals(month)) {
                month = "07";
            } else if ("八月".equals(month)) {
                month = "08";
            } else if ("九月".equals(month)) {
                month = "09";
            } else if ("十月".equals(month)) {
                month = "10";
            } else if ("十一月".equals(month)) {
                month = "11";
            } else if ("十二月".equals(month)) {
                month = "12";
            }
            String dateStr = year + "/" + month + "/" + day;*/

            Pair<Boolean,Date> date = isErrorDate(birthStr);
            /*birthday = sdfxie.parse(dateStr);*/
            birthday = date.getValue();
        }
        String familyInfo = ExcelUtil.getCellStr(row, 21);
        String other = ExcelUtil.getCellStr(row, 22);

        if (ObjTool.isNotNull(contactName, contactSexStr, deptName, job, telphone)) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setContactName(contactName);
            contactInfo.setDeptName(deptName);
            contactInfo.setJob(job);
            contactInfo.setTelphone(telphone);
            contactInfo.setEmail(email);
            contactInfo.setOfficePlane(officePlane);
            contactInfo.setUniversity(university);
            contactInfo.setMajor(major);
            contactInfo.setBirthday(birthday);
            contactInfo.setFamilyInfo(familyInfo);
            contactInfo.setOther(other);

            //设置联系人的所属负责人工号
            if (contactInfoList != null && contactInfoList.size() > 0) {
                String employeeId1 = contactInfoList.get(0).getEmployeeId();
                contactInfo.setEmployeeId(employeeId1);
            } else {
                contactInfo.setEmployeeId(employeeId);
            }

            if ("男".equals(contactSexStr)) {
                contactInfo.setContactSex(1);
            }
            if ("女".equals(contactSexStr)) {
                contactInfo.setContactSex(2);
            }
            contactInfoList.add(contactInfo);
            return contactInfoList;
        }

        return null;
    }

    /**
     * 设置客户和销售的关系实体
     *
     * @param row
     * @param customerSaleRef
     * @return
     */
    private CustomerSaleRef setCustomerSaleRef(XSSFRow row, CustomerSaleRef customerSaleRef) {
        String employeeId = ExcelUtil.getCellStr(row, 6);
        String employeeName = ExcelUtil.getCellStr(row, 5);
        if (ObjTool.isNotNull(employeeId)) {
            customerSaleRef.setEmployeeId(employeeId);
            return customerSaleRef;
        } else {
            if (ObjTool.isNotNull(employeeName)) {
                employeeId = sysUserDao.getUserIdByUserName(employeeName);
                if (ObjTool.isNotNull(employeeId)) {
                    customerSaleRef.setEmployeeId(employeeId);
                    return customerSaleRef;
                }
            }
        }
        return null;
    }

    private CustomerInfo setCustomerInfo(XSSFRow row, CustomerInfo customerInfo) {
        String customerName = ExcelUtil.getCellStr(row, 0);
        String location = ExcelUtil.getCellStr(row, 1);
        String enterStr = ExcelUtil.getCellStr(row, 2);
        String industry = ExcelUtil.getCellStr(row, 3);
        String address = ExcelUtil.getCellStr(row, 4);
        String isListedStr = "是";
        String telephone = "";
        if (ObjTool.isNotNull(row.getCell(7))) {
            telephone = ExcelUtil.getNumericCellValue(row.getCell(7));
        }
        String zipCode = ExcelUtil.numOfImport(row.getCell(8));
        String website = ExcelUtil.getCellStr(row, 9);
        String fax = ExcelUtil.numOfImport(row.getCell(10));
        if (ObjTool.isNotNull(customerName, location, enterStr, industry, address, isListedStr)) {
            customerInfo.setCustomerName(customerName);

            String[] split = location.split("/");
            customerInfo.setCountryNum("中国");
            int length = split.length;

            if (length == 1) {
                customerInfo.setProvinceNum(split[0]);
                customerInfo.setCityNum("");
                customerInfo.setDistrict("");
            } else if (length == 2) {
                customerInfo.setProvinceNum(split[0]);
                customerInfo.setCityNum(split[1]);
                customerInfo.setDistrict("");
            } else if (length == 3) {
                customerInfo.setProvinceNum(split[0]);
                customerInfo.setCityNum(split[1]);
                customerInfo.setDistrict(split[2]);
            }

            if ("最终用户".equals(enterStr)) {
                customerInfo.setEnterId(1);
            }
            if ("合作伙伴".equals(enterStr)) {
                customerInfo.setEnterId(2);
            }
            if ("最终用户&合作伙伴".equals(enterStr)) {
                customerInfo.setEnterId(3);
            }

            customerInfo.setIndustry(industry);
            customerInfo.setAddress(address);

            if ("是".equals(isListedStr)) {
                customerInfo.setIsListed(1);
            }
            if ("否".equals(isListedStr)) {
                customerInfo.setIsListed(2);
            }

            customerInfo.setWebsite(website);

            customerInfo.setFax(fax);

            customerInfo.setTelephone(telephone);

            customerInfo.setZipCode(zipCode);

            return customerInfo;
        }
        return null;
    }

    /**
     * 生成customerId并判断customerId是否是重复的
     *
     * @return
     */
    private String generateCustomerId() {
        //查询客户最大编码
        String maxCode = customerInfoDao.getMaxCustomerId();
        //如果查询数据库没有客户记录，设置maxCode默认值
        if (null == maxCode) {
            maxCode = "KH201911220000";
        }
        //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
        String customerId = customerInfoService.getKhCode(maxCode);
        int customerIdCount = customerInfoDao.checkUnique(customerId);
        if (customerIdCount > 0) {
            return null;
        }
        return customerId;
    }
    
    /**
     * 邮箱校验
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,6,7,8,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,2,3,4,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


    public static Pair<Boolean,Date> isErrorDate(String dateStr){
      boolean parsefail;
      Date date = null;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try {
        date = dateFormat.parse(dateStr);
        parsefail = false;
      } catch (ParseException e) {
        parsefail = true;
      }
      if(parsefail) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
          date = df.parse(dateStr);
          parsefail = false;
        } catch (ParseException e) {
          parsefail = true;
        }
      }
      return new Pair<>(parsefail,date);
    }
}