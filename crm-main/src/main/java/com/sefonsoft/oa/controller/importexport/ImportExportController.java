package com.sefonsoft.oa.controller.importexport;

import com.sefonsoft.oa.domain.customer.CustomerInfoExport;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.service.importexport.ImportExportService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ExcelUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.*;

/**
 * @author ：PengYiWen/Aron
 * @date ：Created in 2019/11/29 9:42
 * @description：导入导出controller
 * @modified By：
 */
@RestController
@Api(tags = "导入导出controller")
@Deprecated
public class ImportExportController extends Response<Object> {

    @Autowired
    private ImportExportService importExportService;

    /**
     * 服务对象
     */
    @Resource
    private CustomerInfoService customerInfoService;

    private static final Logger logger = LoggerFactory.getLogger(ImportExportController.class);

    /**
     * excel导入客户和联系人
     * @return Response
     */
    @ApiOperation(value = "excel导入客户和联系人")
    @RequestMapping(value = "/importCustomersAndContacts", method = RequestMethod.PUT)
    @Deprecated
    public Response importCustomersAndContacts(@RequestParam("file") MultipartFile file,
                                               @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {

        InputStream is = file.getInputStream();
        XSSFSheet sheet = ExcelUtil.getOneSheetByInputStream(is, 0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows == 2) {
            return failure("导入信息为空，请重新填入导入信息");
        }

        Map emptyMap = importExportService.checkEmpty(sheet,loginUserDTO);
        if (ObjTool.isNotNull(emptyMap)) {
            for (Object keyObj : emptyMap.keySet()) {
                Integer keyInt = Integer.parseInt(keyObj.toString());
                String val = emptyMap.get(keyInt+"").toString();
                keyInt = keyInt - 1;
                return failure("第"+ keyInt+"行的"+val + NO_PARAM);
            }
        }

        Map maxMap = importExportService.checkMax(sheet,loginUserDTO);
        if (ObjTool.isNotNull(maxMap)) {
            for (Object keyObj : maxMap.keySet()) {
                Integer keyInt = Integer.parseInt(keyObj.toString());
                String val = maxMap.get(keyInt+"").toString();
                keyInt = keyInt - 1;
                return failure("第"+ keyInt+"行的"+val + MAX_PARAM);
            }
        }

        Map formatMap = importExportService.checkFormat(sheet, loginUserDTO);
        if (ObjTool.isNotNull(formatMap)) {
            for (Object keyObj : formatMap.keySet()) {
                Integer keyInt = Integer.parseInt(keyObj.toString());
                String val = formatMap.get(keyInt+"").toString();
                keyInt = keyInt - 1;
                return failure("第"+ keyInt+"行的"+val + FORMAT_ERROR + ",请参考导入模板的第二个页签的注意事项进行填写");
            }
        }

        Map checkPersonAccount = importExportService.checkPersonAccount(sheet, loginUserDTO);
        if (ObjTool.isNotNull(checkPersonAccount)) {
            for (Object keyObj : checkPersonAccount.keySet()) {
                Integer keyInt = Integer.parseInt(keyObj.toString());
                String val = checkPersonAccount.get(keyInt+"").toString();
                return failure(val);
            }
        }

        Map checkMobilePhoneMap = importExportService.checkMobilePhone(sheet, loginUserDTO);
        if (ObjTool.isNotNull(checkMobilePhoneMap)) {
            for (Object keyObj : checkMobilePhoneMap.keySet()) {
                Integer keyInt = Integer.parseInt(keyObj.toString());
                String val = checkMobilePhoneMap.get(keyInt+"").toString();
                keyInt = keyInt - 1;
                return failure("第"+ keyInt+"行的"+val);
            }
        }

        importExportService.importCustomersAndContacts(sheet, loginUserDTO);
        return success();
    }


    @ApiOperation(value = "excel导出客户和联系人")
    @Deprecated
    @RequestMapping(value = "/exportCustomersAndContacts", method = RequestMethod.GET)
    public void excelTest(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = "客户全量信息.xlsx";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //第一步：定义一个新的工作簿
            XSSFWorkbook web = new XSSFWorkbook();
            //第二步：创建一个sheet页
            XSSFSheet sheet = web.createSheet("客户信息");
            //设置行高
            sheet.setDefaultRowHeight((short) (2 * 256));
            //设置列宽
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 6000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 3000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 2000);
            sheet.setColumnWidth(14, 4000);
            sheet.setColumnWidth(15, 4000);
            sheet.setColumnWidth(16, 4000);
            sheet.setColumnWidth(17, 5000);
            sheet.setColumnWidth(18, 4000);
            sheet.setColumnWidth(19, 4000);
            sheet.setColumnWidth(20, 4000);
            sheet.setColumnWidth(21, 4000);
            sheet.setColumnWidth(22, 4000);
            sheet.setColumnWidth(23, 4000);
            sheet.setColumnWidth(24, 6000);

            //设置样式或者是字体
            XSSFFont font = web.createFont();
            //设置字体
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 16);
            //创建第一行
            XSSFRow row = sheet.createRow(0);
            //为该行创建第一个单元格
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("单位名称 ");
            cell = row.createCell(1);
            cell.setCellValue("单位性质 ");
            cell = row.createCell(2);
            cell.setCellValue("所属行业 ");
            cell = row.createCell(3);
            cell.setCellValue("省市区 ");
            cell = row.createCell(4);
            cell.setCellValue("单位地址 ");
            cell = row.createCell(5);
            cell.setCellValue("网址");
            cell = row.createCell(6);
            cell.setCellValue("邮编");
            cell = row.createCell(7);
            cell.setCellValue("座机");
            cell = row.createCell(8);
            cell.setCellValue("传真");
            cell = row.createCell(9);
            cell.setCellValue("销售负责人工号");
            cell = row.createCell(10);
            cell.setCellValue("销售负责人");
            cell = row.createCell(11);
            cell.setCellValue("所属团队");
            cell = row.createCell(12);
            cell.setCellValue("联系人姓名");
            cell = row.createCell(13);
            cell.setCellValue("联系人性别");
            cell = row.createCell(14);
            cell.setCellValue("联系人部门");
            cell = row.createCell(15);
            cell.setCellValue("联系人职务");
            cell = row.createCell(16);
            cell.setCellValue("联系人手机号");
            cell = row.createCell(17);
            cell.setCellValue("联系人邮箱");
            cell = row.createCell(18);
            cell.setCellValue("办公座机");
            cell = row.createCell(19);
            cell.setCellValue("毕业学校");
            cell = row.createCell(20);
            cell.setCellValue("专业");
            cell = row.createCell(21);
            cell.setCellValue("家庭状况");
            cell = row.createCell(22);
            cell.setCellValue("生日");
            cell = row.createCell(23);
            cell.setCellValue("其他");
            cell = row.createCell(24);
            cell.setCellValue("加入时间");

            //定义一个行
            XSSFRow rows;
            //定义一个单元格
            XSSFCell cells;
            //导出客户列表
            List<CustomerInfoExport> list = customerInfoService.getConditionn();

            if (list != null && list.size() > 0) {
                for (int i = 1; i <=list.size(); i++) {
                    CustomerInfoExport  customerInfo = list.get(i-1);
                    // 第三步：在这个sheet页里创建一行（每次循环都会创建一个新的行）
                    rows = sheet.createRow(i);
                    // 第四步：在该行创建一个单元格
                    cells = rows.createCell(0);
                    // 第五步：在该单元格里设置值
                    cells.setCellValue(customerInfo.getCustomerName());
                    cells = rows.createCell(1);
                    cells.setCellValue(customerInfo.getEntername());
                    cells = rows.createCell(2);
                    cells.setCellValue(customerInfo.getIndustry());
                    cells = rows.createCell(3);
                    cells.setCellValue(customerInfo.getSsq());
                    cells = rows.createCell(4);
                    cells.setCellValue(customerInfo.getAddress());
                    cells = rows.createCell(5);
                    cells.setCellValue(customerInfo.getWebsite());
                    cells = rows.createCell(6);
                    cells.setCellValue(customerInfo.getZipCode());
                    cells = rows.createCell(7);
                    cells.setCellValue(customerInfo.getTelephone());
                    cells = rows.createCell(8);
                    cells.setCellValue(customerInfo.getFax());
                    cells = rows.createCell(9);
                    cells.setCellValue(customerInfo.getEmployeeId());
                    cells = rows.createCell(10);
                    cells.setCellValue(customerInfo.getEmployeeName());
                    cells = rows.createCell(11);
                    cells.setCellValue(customerInfo.getDeptName());
                    cells = rows.createCell(12);
                    cells.setCellValue(customerInfo.getContacts());
                    cells = rows.createCell(13);
                    if (ObjTool.isNotNull(customerInfo.getSex())) {
                        if(customerInfo.getSex()==1){
                            cells.setCellValue("男");
                        }else{
                            cells.setCellValue("女");
                        }
                    }else {
                        cells.setCellValue("");
                    }

                    cells = rows.createCell(14);
                    cells.setCellValue(customerInfo.getContactsDepart());
                    cells = rows.createCell(15);
                    cells.setCellValue(customerInfo.getJob());
                    cells = rows.createCell(16);
                    cells.setCellValue(customerInfo.getContactsTelephone());
                    cells = rows.createCell(17);
                    cells.setCellValue(customerInfo.getContactsEmail());

                    cells = rows.createCell(18);
                    cells.setCellValue(customerInfo.getOfficePlane());
                    cells = rows.createCell(19);
                    cells.setCellValue(customerInfo.getUniversity());
                    cells = rows.createCell(20);
                    cells.setCellValue(customerInfo.getMajor());
                    cells = rows.createCell(21);
                    cells.setCellValue(customerInfo.getFamilyInfo());
                    cells = rows.createCell(22);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthday = customerInfo.getBirthday();
                    if(ObjTool.isNotNull(birthday)){
                        String birthdayTime = df.format(birthday);
                        cells.setCellValue(birthdayTime);
                    }else {
                        cells.setCellValue(birthday);
                    }
                    cells = rows.createCell(23);
                    cells.setCellValue(customerInfo.getOther());

                    cells = rows.createCell(24);
                    Date createTime = customerInfo.getCreateTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = dateFormat.format(createTime);
                    cells.setCellValue(time);

                }
            }

            try {
                OutputStream out = response.getOutputStream();
                web.write(out);
                out.close();
                web.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("导出数据异常");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}