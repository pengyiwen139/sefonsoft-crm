package com.sefonsoft.oa.service.bizopports;

import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.contact.ContactInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerSaleRefDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.dao.user.SysUserDao;
import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.system.utils.*;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.*;

/**
 * Created by liwenyi
 * Date: 2020/3/25
 * Desc:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BizImportExportServiceImpl implements BizImportExportService {

    @Resource
    private BizOpportsService bizOpportsService;

    @Resource
    private CustomerInfoService customerInfoService;

    @Resource
    private BizOpportsDao bizOpportsDao;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysEmployeeDao sysEmployeeDao;

    @Resource
    private CustomerInfoDao customerInfoDao;

    @Resource
    private ContactInfoDao contactInfoDao;

    @Resource
    private CustomerSaleRefDao customerSaleRefDao;

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

    /**
     * 功能描述: 查询所有商机
     *
     * @Param: []
     * @Return: java.util.List<com.sefonsoft.oa.domain.bizopports.BizOpportsExport>
     * @Author: liwenyi
     */
    @Override
    public List<BizOpportsExport> queryAllBizOpportsExport() {
        return bizOpportsDao.queryAllBizOpportsExport();
    }

    @Override
    public List<String> checkEmpty(Sheet sheet) {
        List<String> stringList = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            //算出商机名称到提交人工号必填字段是否为空
            for (int j = 1; j <= 3; j++) {
                String cellStr = ExcelUtil.getCellStr(row, j);
                String key = "";
                if (!ObjTool.isNotNull(cellStr)) {
                    if (j == 1) {
                        key = "商机名称";
                    } else if (j == 2) {
                        key = "提交人";
                    } else if (j == 3) {
                        key = "提交人工号";
                    }
                    if (ObjTool.isNotNull(key)) {
                        stringList.add((i + 1) + "行  "+ key + NO_PARAM);
                    }
                }
            }
            //算出销售负责人和销售负责人工号是否为空
            String employeeName = ExcelUtil.getCellStr(row, 5);
            String employeeId = ExcelUtil.getCellStr(row, 6);
            if (!ObjTool.isNotNull(employeeName)) {
                stringList.add((i + 1) + "行  销售负责人" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(employeeId)) {
                stringList.add((i + 1) + "行  销售负责人工号" + NO_PARAM);
            }
            //算出销售商品到客户单位地址是否为空
            for (int j = 9; j <= 15; j++) {
                String cellStr = ExcelUtil.getCellStr(row, j);
                if (!ObjTool.isNotNull(cellStr)) {
                    String key = "";
                    if (j == 9) {
                        key = "销售商品";
                    } else if (j == 10) {
                        key = "商机概述";
                    } else if (j == 11) {
                        key = "客户名称";
                    } else if (j == 12) {
                        key = "省/市/区县";
                    } else if (j == 13) {
                        key = "单位性质";
                    } else if (j == 14) {
                        key = "所属行业";
                    } else if (j == 15) {
                        key = "单位地址";
                    }
                    if (ObjTool.isNotNull(key)) {
                        stringList.add((i + 1) + "行  "+ key + NO_PARAM);
                    }
                }
            }
            //算出联系人姓名到联系人手机号码是否为空
            for (int j = 20; j <= 24; j++) {
                if (j == 22){
                    continue;
                }
                String cellStr = ExcelUtil.getCellStr(row, j);
                if (!ObjTool.isNotNull(cellStr)) {
                    String key = "";
                    if (j == 20) {
                        key = "联系人姓名";
                    } else if (j == 21) {
                        key = "联系人性别";
                    } else if (j == 23) {
                        key = "联系人职务";
                    } else if (j == 24) {
                        key = "联系人手机号码";
                    }
                    if (ObjTool.isNotNull(key)) {
                        stringList.add((i + 1) + "行  "+ key + NO_PARAM);
                    }
                }
            }
        }
        return stringList;
    }

    @Override
    public List<String> checkMax(Sheet sheet) {
        List<String> stringList = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            //判断所有字段的长度是否符合标准
            for (int j = 0; j <= 31; j++) {
                String cellStr = ExcelUtil.getCellStr(row, j);
                if (j == 0 && ObjTool.isNotNull(cellStr) && cellStr.length() > 14) {
                    stringList.add((i + 1) + "行  商机Id" + MAX_PARAM);
                } else if (j == 1 && ObjTool.isNotNull(cellStr) && cellStr.length() > 128) {
                    stringList.add((i + 1) + "行  商机名称" + MAX_PARAM);
                } else if (j == 2 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  提交人" + MAX_PARAM);
                } else if (j == 3 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  提交人Id" + MAX_PARAM);
                } else if (j == 4 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  提交人所属大区" + MAX_PARAM);
                } else if (j == 5 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  销售负责人" + MAX_PARAM);
                } else if (j == 6 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  销售负责人Id" + MAX_PARAM);
                } else if (j == 7 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  销售负责人所属大区" + MAX_PARAM);
                } else if (j == 8 && ObjTool.isNotNull(cellStr) && cellStr.length() > 50) {
                    stringList.add((i + 1) + "行  商机状态" + MAX_PARAM);
                } else if (j == 9 && ObjTool.isNotNull(cellStr) && cellStr.length() > 200) {
                    stringList.add((i + 1) + "行  销售商品" + MAX_PARAM);
                } else if (j == 10 && ObjTool.isNotNull(cellStr) && cellStr.length() > 2000) {
                    stringList.add((i + 1) + "行  商机概述" + MAX_PARAM);
                } else if (j == 11 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  客户名称" + MAX_PARAM);
                } else if (j == 12 && ObjTool.isNotNull(cellStr)) {
                    String[] split = cellStr.split("/");
                    int length = split.length;
                    /*if (length == 1) {
                        String s = split[0];
                        if (s.length() > 16) {
                            map.put(i + "", "省");
                            return map;
                        }
                    }*/
                    if (length == 2) {
                        String s = split[0];
                        if (s.length() > 16) {
                            stringList.add((i + 1) + "行  省" + MAX_PARAM);
                        }
                        String s1 = split[1];
                        if (s1.length() > 16) {
                            stringList.add((i + 1) + "行  市" + MAX_PARAM);
                        }
                    }
                    if (length == 3) {
                        String s = split[0];
                        if (s.length() > 16) {
                            stringList.add((i + 1) + "行  省" + MAX_PARAM);
                        }
                        String s1 = split[1];
                        if (s1.length() > 16) {
                            stringList.add((i + 1) + "行  市" + MAX_PARAM);
                        }
                        String s2 = split[1];
                        if (s2.length() > 16) {
                            stringList.add((i + 1) + "行  区县" + MAX_PARAM);
                        }
                    }
                } else if (j == 13 && ObjTool.isNotNull(cellStr) && cellStr.length() > 16) {
                    stringList.add((i + 1) + "行  单位性质" + MAX_PARAM);
                } else if (j == 14 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  所属行业" + MAX_PARAM);
                } else if (j == 15 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  单位地址" + MAX_PARAM);
                } else if (j == 16 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  客户座机" + MAX_PARAM);
                } else if (j == 17 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  邮编" + MAX_PARAM);
                } else if (j == 18 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  网址" + MAX_PARAM);
                } else if (j == 19 && ObjTool.isNotNull(cellStr) && cellStr.length() > 20) {
                    stringList.add((i + 1) + "行  传真" + MAX_PARAM);
                } else if (j == 20 && ObjTool.isNotNull(cellStr) && cellStr.length() > 20) {
                    stringList.add((i + 1) + "行  姓名" + MAX_PARAM);
                } else if (j == 21 && ObjTool.isNotNull(cellStr) && cellStr.length() > 4) {
                    stringList.add((i + 1) + "行  性别" + MAX_PARAM);
                } else if (j == 22 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  部门" + MAX_PARAM);
                } else if (j == 23 && ObjTool.isNotNull(cellStr) && cellStr.length() > 16) {
                    stringList.add((i + 1) + "行  职务" + MAX_PARAM);
                } else if (j == 24 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  手机号码" + MAX_PARAM);
                } else if (j == 25 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  邮箱" + MAX_PARAM);
                } else if (j == 26 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  办公座机" + MAX_PARAM);
                } else if (j == 27 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  毕业学校" + MAX_PARAM);
                } else if (j == 28 && ObjTool.isNotNull(cellStr) && cellStr.length() > 64) {
                    stringList.add((i + 1) + "行  专业" + MAX_PARAM);
                } else if (j == 29 && ObjTool.isNotNull(cellStr) && cellStr.length() > 32) {
                    stringList.add((i + 1) + "行  生日" + MAX_PARAM);
                } else if (j == 30 && ObjTool.isNotNull(cellStr) && cellStr.length() > 128) {
                    stringList.add((i + 1) + "行  家庭情况" + MAX_PARAM);
                } else if (j == 31 && ObjTool.isNotNull(cellStr) && cellStr.length() > 128) {
                    stringList.add((i + 1) + "行  其他" + MAX_PARAM);
                }
            }
        }
        return stringList;
    }

    @Override
    public List<String> checkFormat(Sheet sheet) {
        List<String> stringList = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            List<String> keywords = Arrays.asList(new String[]{"UE", "UE-GIS", "UE-W3D", "UE-AI", "BE", "ME", "Miner", "DS"
                    , "Govern", "Hadoop", "CellularDB", "AE", "ETL", "BC", "实施服务", "维保服务", "其他"});
            if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 9))){
                String[] keys = ExcelUtil.getCellStr(row, 9).split("、");
                for (String key : keys) {
                    if (!keywords.contains(key)) {
                        stringList.add((i + 1) + "行  销售商品" + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
                        break;
                    }
                }
            }
            List<String> stateList = Arrays.asList(new String[]{"未立项", "已立项", "立项审核中"});
            String state = ExcelUtil.getCellStr(row, 8);
            if (ObjTool.isNotNull(state) && !stateList.contains(state)) {
                stringList.add((i + 1) + "行  商机状态" + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
            }
            String sexContact = row.getCell(21).toString();
            if ((!sexContact.equals("男")) && (!sexContact.equals("女"))) {
                stringList.add((i + 1) + "行  性别" + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
            }
            //单位性质校验
            String companyStatmentStr = row.getCell(13).toString();
            if (ObjTool.isNotNull(companyStatmentStr) && (!"最终用户".equals(companyStatmentStr)) && (!"合作伙伴".equals(companyStatmentStr)) && (!"最终用户&合作伙伴".equals(companyStatmentStr))) {
                stringList.add((i + 1) + "行  单位性质" + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
            }
            //所属行业校验
            String industry = row.getCell(14).toString();
            if (ObjTool.isNotNull(industry) && (!list.contains(industry))) {
                stringList.add((i + 1) + "行  所属行业" + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
            }
        }
        return stringList;
    }

    @Override
    public List<String> checkPersonAccount(Sheet sheet) {
        List<String> stringList = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            String bizId = ExcelUtil.getCellStr(row, 0);
            if (ObjTool.isNotNull(bizId)) {
                List<BizOpports> bizOpportsList = bizOpportsDao.queryByBizId(bizId);
                if (ObjTool.isNotNull(bizOpportsList)) {
                    stringList.add((i + 1) + "行  商机id已存在");
                }
            }
            String createId = ExcelUtil.getCellStr(row,3);
            if (ObjTool.isNotNull(createId)){
                String createIdPass = sysUserDao.getDbPwdByUserId(createId);
                if (ObjTool.isNotNull(createIdPass)) {
                    SysEmployee createEmployee = sysEmployeeDao.queryByIds(createId);
                    if (!createEmployee.getEmployeeName().equalsIgnoreCase(ExcelUtil.getCellStr(row,2))){
                        stringList.add((i + 1) + "行  提交人工号与提交人不匹配");
                    }
                }else{
                    stringList.add((i + 1) + "行  提交人工号找不到相应的账号，请填写正确的工号");
                }
            }
            String employeeId = ExcelUtil.getCellStr(row,6);
            if (ObjTool.isNotNull(employeeId)){
                String employeePass = sysUserDao.getDbPwdByUserId(employeeId);
                if (ObjTool.isNotNull(employeePass)) {
                    SysEmployee employee = sysEmployeeDao.queryByIds(employeeId);
                    if (!employee.getEmployeeName().equalsIgnoreCase(ExcelUtil.getCellStr(row,5))){
                        stringList.add((i + 1) + "行  销售负责人工号与销售负责人不匹配");
                    }
                }else{
                    stringList.add((i + 1) + "行  销售负责人工号找不到相应的账号，请填写正确的工号");
                }

            }
        }
        return stringList;
    }

    @Override
    public List<String> checkFormatOther(Sheet sheet) {
        List<String> stringList = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            String bizName = ExcelUtil.getCellStr(row, 1);
            //匹配数字、字母和中文
            String regex = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
            boolean flag = bizName.matches(regex);
            if (!flag) {
                stringList.add((i + 1) + "行  商机名称不符合规范（限制中文,字母和数字）");
            }
            /*String phone = ExcelUtil.getCellStr(row, 24);
            if (ObjTool.isNotNull(phone) && phone.length() != 11) {
                stringList.add((i + 1) + "行  联系人电话必须为11位数");
            } else if (ObjTool.isNotNull(phone) && phone.length() == 11){
                boolean isMatch = isPhone(phone);
                if (!isMatch) {
                    stringList.add((i + 1) + "行  联系人电话不符合规范");
                }
            }*/
            if (ObjTool.isNotNull(ExcelUtil.getCellStr(row,12))){
                String[] ssq = ExcelUtil.getCellStr(row,12).split("/");
                if (ssq.length != 2 && ssq.length != 3){
                    stringList.add((i + 1) + "行  省/市/区县格式不符合规范，正确格式形如：湖北省/武汉市/");
                }
            }
            Cell cell = row.getCell(17);
            if (ObjTool.isNotNull(cell) && !StringUtils.isEmpty(cell.toString().trim())) {
                String s = ExcelUtil.numOfImport(cell);
                boolean postCode = isPostCode(s);
                if (!postCode) {
                    stringList.add((i + 1) + "行  客户邮编格式不符合规范");
                }
            }
            //客户电话和座机同时校验
            Cell customerTel = row.getCell(16);
            if (ObjTool.isNotNull(customerTel) && !StringUtils.isEmpty(customerTel.toString().trim())) {
                String cusTel = ExcelUtil.getNumericCellValue(customerTel);
                boolean isMatch = isPhone(cusTel);
                boolean fixedPhone = isFixedPhone(cusTel);
                if (!isMatch && !fixedPhone) {
                    stringList.add((i + 1) + "行  客户电话不符合规范");
                }
            }
            //联系人座机校验
            Cell contactTel = row.getCell(26);
            if (ObjTool.isNotNull(contactTel) && !StringUtils.isEmpty(contactTel.toString().trim())) {
                String conTel = ExcelUtil.getNumericCellValue(contactTel);
                boolean fixedPhone = isFixedPhone(conTel);
                if (!fixedPhone) {
                    stringList.add((i + 1) + "行  联系人办公座机不符合规范");
                }
            }
            //联系人邮箱校验
            Cell contactEmail = row.getCell(25);
            if (ObjTool.isNotNull(contactEmail) && !StringUtils.isEmpty(contactEmail.toString().trim())) {
                String conEmail = contactEmail.toString();
                if (ObjTool.isNotNull(conEmail)) {
                    boolean email = isEmail(conEmail);
                    if (!email) {
                        stringList.add((i + 1) + "行  联系人邮箱不符合规范");
                    }
                }
            }
            //客户传真校验
            Cell customerFax = row.getCell(19);
            if (ObjTool.isNotNull(customerFax) && !StringUtils.isEmpty(customerFax.toString().trim())) {
                String fax = ExcelUtil.numOfImport(customerFax);
                if (ObjTool.isNotNull(fax)) {
                    boolean f = isFixedPhone(fax);
                    if (!f) {
                        stringList.add((i + 1) + "行  客户传真不符合规范");
                    }
                }
            }
            //联系人生日校验
            Cell contactBirth = row.getCell(29);
            if (ObjTool.isNotNull(contactBirth) && !StringUtils.isEmpty(contactBirth.toString().trim())) {
                String birth = ExcelUtil.parseString(contactBirth);
                Pair<Boolean, Date> date = isErrorDate(birth);
                if (date.getKey()) {
                    stringList.add((i + 1) + "行  联系人生日不符合规范" + birth);
                }
            }
        }
        return stringList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean importBiz(Sheet sheet, LoginUserDTO loginUserDTO) {
        boolean insertFlag = false;
        Date date = new Date();
        try {
            Row row;
            for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (!ObjTool.isNotNull(row)) {
                    continue;
                }
                String bizId ;
                BizOpportsDTO bizOpports = new BizOpportsDTO();
                bizOpports.setEmployeeId(ExcelUtil.getCellStr(row, 6));
                bizOpports.setCreateId(ExcelUtil.getCellStr(row, 3));
                bizOpports.setName(ExcelUtil.getCellStr(row, 1));
                if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 8))) {
                    if (ExcelUtil.getCellStr(row, 8).equalsIgnoreCase("未立项")) {
                        bizOpports.setState(1);
                    } else if (ExcelUtil.getCellStr(row, 8).equalsIgnoreCase("已立项")) {
                        bizOpports.setState(2);
                    } else if (ExcelUtil.getCellStr(row, 8).equalsIgnoreCase("立项审核中")) {
                        bizOpports.setState(3);
                    }
                } else {
                    bizOpports.setState(1);
                }
                bizOpports.setKeywords(ExcelUtil.getCellStr(row, 9));
                bizOpports.setDesc(ExcelUtil.getCellStr(row, 10));
                bizId = UtilSJMethod.getSJnumber(bizOpportsDao.getMaxSJ((UtilSJMethod.SJ_PREFIX) + DateUtils.getCourrentDateTimeKey()));
                bizOpports.setBizId(bizId);
                bizOpports.setImportType(1);
                bizOpports.setCreateTime(date);
                bizOpports.setModifiedTime(date);

                //判断客户信息是否存在
                List<CustomerInfo> customerList = customerInfoDao.findCustomerInfo(ExcelUtil.getCellStr(row, 11),
                        ExcelUtil.getCellStr(row, 15), ExcelUtil.getCellStr(row, 14));
                //客户存在
                if (ObjTool.isNotNull(customerList)) {
                    List<String> customerSaleEmployeeIdList = customerSaleRefDao.findByCustomerId(customerList.get(0).getCustomerId());
                    //判断客户和商机销售负责人是否关联
                    if (!customerSaleEmployeeIdList.contains(ExcelUtil.getCellStr(row, 6))) {
                        //客户和商机负责人没关联，新增关联信息
                        CustomerSaleRef customerSaleRef = new CustomerSaleRef();
                        customerSaleRef.setCustomerId(customerList.get(0).getCustomerId());
                        customerSaleRef.setLastTime(date);
                        customerSaleRef.setCreateTime(date);
                        customerSaleRef.setOperator(loginUserDTO.getUserId());
                        customerSaleRef.setEmployeeId(ExcelUtil.getCellStr(row, 6));
                        customerSaleRefDao.insert(customerSaleRef);
                    }
                    List<ContactInfo> contactInfoList = contactInfoDao.queryByCustomerId(customerList.get(0).getCustomerId());
                    long contact = -1;
                    for (ContactInfo con : contactInfoList) {
                        //判断联系人是否存在
                        if (ExcelUtil.getCellStr(row, 20).equals(con.getContactName()) && ExcelUtil.getCellStr(row, 24).equals(con.getTelphone())) {
                            contact = con.getId();
                        }
                    }
                    bizOpports.setCustomerId(customerList.get(0).getCustomerId());
                    if (contact != -1) {
                        //联系人存在
                        bizOpports.setContactId(contact);

                    } else {
                        //联系人不存在
                        ContactInfo contactInfo = new ContactInfo();
                        contactInfo.setContactName(ExcelUtil.getCellStr(row, 20));
                        contactInfo.setContactSex(ExcelUtil.getCellStr(row, 21).equalsIgnoreCase("男") ? 1 : 2);
                        contactInfo.setDeptName(ExcelUtil.getCellStr(row, 22));
                        contactInfo.setJob(ExcelUtil.getCellStr(row, 23));
                        contactInfo.setTelphone(ExcelUtil.getNumericCellValue(row.getCell(24)));
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 25))) {
                            contactInfo.setEmail(ExcelUtil.getCellStr(row, 25));
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 26))) {
                            contactInfo.setOfficePlane(ExcelUtil.getNumericCellValue(row.getCell(26)));
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 27))) {
                            contactInfo.setUniversity(ExcelUtil.getCellStr(row, 27));
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 28))) {
                            contactInfo.setMajor(ExcelUtil.getCellStr(row, 28));
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 29))) {
                            contactInfo.setBirthday(isErrorDate(ExcelUtil.getCellStr(row, 29)).getValue());
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 30))) {
                            contactInfo.setFamilyInfo(ExcelUtil.getCellStr(row, 30));
                        }
                        if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 31))) {
                            contactInfo.setOther(ExcelUtil.getCellStr(row, 31));
                        }
                        contactInfo.setCreateTime(date);
                        contactInfo.setLastTime(date);

                        contactInfo.setCustomerId(customerList.get(0).getCustomerId());
                        contactInfo.setImportType(1);
                        contactInfo.setEmployeeId(ExcelUtil.getCellStr(row, 6));
                        contactInfo.setOperator(loginUserDTO.getUserId());

                        contactInfoDao.insert(contactInfo);
                        contactInfoList = contactInfoDao.queryByCustomerId(customerList.get(0).getCustomerId());
                        for (ContactInfo con : contactInfoList) {
                            //判断联系人是否存在
                            if (ExcelUtil.getCellStr(row, 20).equals(con.getContactName()) || ExcelUtil.getCellStr(row, 24).equals(con.getTelphone())) {
                                contact = con.getId();
                            }
                        }
                        bizOpports.setContactId(contact);
                    }
                } else {   //客户不存在
                    CustomerInfo customerInfo = new CustomerInfo();
                    customerInfo.setCustomerId(generateCustomerId());
                    customerInfo.setImportType(1);
                    customerInfo.setCustomerName(ExcelUtil.getCellStr(row, 11));
                    String[] ssq = ExcelUtil.getCellStr(row, 12).split("/");
                    if (ssq.length == 3) {
                        customerInfo.setProvinceNum(ssq[0]);
                        customerInfo.setCityNum(ssq[1]);
                        customerInfo.setDistrict(ssq[2]);
                    } else if (ssq.length == 2) {
                        customerInfo.setProvinceNum(ssq[0]);
                        customerInfo.setCityNum(ssq[1]);
                    }
                    if ("最终用户".equalsIgnoreCase(ExcelUtil.getCellStr(row, 13))) {
                        customerInfo.setEnterId(1);
                    } else if ("合作伙伴".equalsIgnoreCase(ExcelUtil.getCellStr(row, 13))) {
                        customerInfo.setEnterId(2);
                    } else if ("最终用户&合作伙伴".equalsIgnoreCase(ExcelUtil.getCellStr(row, 13))) {
                        customerInfo.setEnterId(3);
                    }
                    customerInfo.setIndustry(ExcelUtil.getCellStr(row, 14));
                    customerInfo.setAddress(ExcelUtil.getCellStr(row, 15));
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 16))) {
                        customerInfo.setTelephone(ExcelUtil.getNumericCellValue(row.getCell(16)));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 17))) {
                        customerInfo.setZipCode(ExcelUtil.getCellStr(row, 17));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 18))) {
                        customerInfo.setWebsite(ExcelUtil.getCellStr(row, 18));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 19))) {
                        customerInfo.setFax(ExcelUtil.getCellStr(row, 19));
                    }
                    customerInfo.setCreateTime(date);
                    customerInfo.setLastTime(date);
                    customerInfo.setOperator(loginUserDTO.getUserId());
                    customerInfoDao.insert(customerInfo);

                    CustomerSaleRef customerSaleRef = new CustomerSaleRef();
                    customerSaleRef.setCustomerId(customerInfo.getCustomerId());
                    customerSaleRef.setLastTime(date);
                    customerSaleRef.setCreateTime(date);
                    customerSaleRef.setOperator(loginUserDTO.getUserId());
                    customerSaleRef.setEmployeeId(ExcelUtil.getCellStr(row, 6));
                    customerSaleRefDao.insert(customerSaleRef);

                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setContactName(ExcelUtil.getCellStr(row, 20));
                    contactInfo.setContactSex(ExcelUtil.getCellStr(row, 21).equalsIgnoreCase("男") ? 1 : 2);
                    contactInfo.setDeptName(ExcelUtil.getCellStr(row, 22));
                    contactInfo.setJob(ExcelUtil.getCellStr(row, 23));
                    contactInfo.setTelphone(ExcelUtil.getNumericCellValue(row.getCell(24)));
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 25))) {
                        contactInfo.setEmail(ExcelUtil.getCellStr(row, 25));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 26))) {
                        contactInfo.setOfficePlane(ExcelUtil.getNumericCellValue(row.getCell(26)));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 27))) {
                        contactInfo.setUniversity(ExcelUtil.getCellStr(row, 27));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 28))) {
                        contactInfo.setMajor(ExcelUtil.getCellStr(row, 28));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 29))) {
                        contactInfo.setBirthday(isErrorDate(ExcelUtil.getCellStr(row, 29)).getValue());
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 30))) {
                        contactInfo.setFamilyInfo(ExcelUtil.getCellStr(row, 30));
                    }
                    if (ObjTool.isNotNull(ExcelUtil.getCellStr(row, 31))) {
                        contactInfo.setOther(ExcelUtil.getCellStr(row, 31));
                    }
                    contactInfo.setCreateTime(date);
                    contactInfo.setLastTime(date);
                    contactInfo.setCustomerId(customerInfo.getCustomerId());
                    contactInfo.setImportType(1);
                    contactInfo.setEmployeeId(ExcelUtil.getCellStr(row, 6));
                    contactInfo.setOperator(loginUserDTO.getUserId());

                    contactInfoDao.insert(contactInfo);
                    List<ContactInfo> contactInfoList = contactInfoDao.queryByCustomerId(customerInfo.getCustomerId());

                    bizOpports.setContactId(contactInfoList.get(0).getId());
                    bizOpports.setCustomerId(customerInfo.getCustomerId());
                }
                bizOpportsDao.insert(bizOpports);
            }
            insertFlag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertFlag;
    }

    public static Pair<Boolean, Date> isErrorDate(String dateStr) {
        boolean parsefail;
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(dateStr);
            parsefail = false;
        } catch (ParseException e) {
            parsefail = true;
        }
        if (parsefail) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            try {
                date = df.parse(dateStr);
                parsefail = false;
            } catch (ParseException e) {
                parsefail = true;
            }
        }
        return new Pair<>(parsefail, date);
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,6,7,8,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,2,3,4,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        return isMatch;
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
}
