package com.sefonsoft.oa.controller.bizopports;

import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.bizopports.BizImportExportService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ExcelUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.C_BIZ_OPPORTS_EXPORT;
import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.C_BIZ_OPPORTS_IMPORT;
import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;

/**
 * Created by liwenyi
 * Date: 2020/3/24
 * Desc:
 */
@RefreshScope
@RestController
@Api(tags = "商机导入导出接口")
@Log4j2
public class BizImportExportController {

    @Resource
    private BizImportExportService bizImportExportService;

    @Value("${username.now}")
    private String name;

    @RequestMapping("/hei/test")
    public String test(){
        return name;
    }

    /**
     * 功能描述: 商机导出
     *
     * @Param: [request, response]
     * @Return: void
     * @Author: liwenyi
     */
    @MethodPermission(menuIdArray = C_BIZ_OPPORTS_EXPORT)
    @ApiOperation(value = "商机-导出")
    @RequestMapping(value = "/bizOpports/export",method = RequestMethod.GET)
    @OpLog(module = OpLog.Module.BIZ_OPPORTS,opType = OpLog.Type.EXPORT)
    public void exportBiz(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("开始导出商机数据");

        String[] header = {"商机ID","商机名称","提交人","提交人工号","提交人所属大区","销售负责人","销售负责人工号"
                ,"销售负责人所属大区","商机状态","销售商品","商机概述", "客户名称","省/市/区县","单位性质","所属行业",
                "单位地址","座机","邮编", "网址","传真","姓名","性别", "部门","职务","手机号码","邮箱","办公座机",
                "毕业学校","专业", "生日(年/月/日)","家庭状况", "其他"};
        List<BizOpportsExport> bizOpportsList = bizImportExportService.queryAllBizOpportsExport();
        Workbook workbook = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = "商机信息.xlsx";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            workbook = new SXSSFWorkbook(new XSSFWorkbook());
            Sheet sheet = workbook.createSheet("商机信息");
            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue("商机");
            Cell cell2 = row1.createCell(11);
            cell2.setCellValue("客户");
            Cell cell3 = row1.createCell(20);
            cell3.setCellValue("联系人");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 19));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 20, 31));
            Row row2 = sheet.createRow(1);
            for (int i = 0; i < header.length; i++) {
                row2.createCell(i).setCellValue(header[i]);
            }
            int rowNum = 2;
            for (BizOpportsExport bizOpportsExport : bizOpportsList) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(bizOpportsExport.getBizId());
                row.createCell(1).setCellValue(bizOpportsExport.getName());
                row.createCell(2).setCellValue(bizOpportsExport.getCreateName());
                row.createCell(3).setCellValue(bizOpportsExport.getCreateId());
                row.createCell(4).setCellValue(bizOpportsExport.getCreateDeptName());
                row.createCell(5).setCellValue(bizOpportsExport.getEmployeeName());
                row.createCell(6).setCellValue(bizOpportsExport.getEmployeeId());
                row.createCell(7).setCellValue(bizOpportsExport.getEmployeeDeptName());
                row.createCell(8).setCellValue(bizOpportsExport.getStateName());
                row.createCell(9).setCellValue(bizOpportsExport.getKeywords());
                row.createCell(10).setCellValue(bizOpportsExport.getDesc());

                row.createCell(11).setCellValue(bizOpportsExport.getCustomerName());
                row.createCell(12).setCellValue(bizOpportsExport.getSsq());
                row.createCell(13).setCellValue(bizOpportsExport.getEntername());
                row.createCell(14).setCellValue(bizOpportsExport.getIndustry());
                row.createCell(15).setCellValue(bizOpportsExport.getAddress());
                row.createCell(16).setCellValue(bizOpportsExport.getCustomerTelephone());
                row.createCell(17).setCellValue(bizOpportsExport.getZipCode());
                row.createCell(18).setCellValue(bizOpportsExport.getWebsite());
                row.createCell(19).setCellValue(bizOpportsExport.getFax());

                row.createCell(20).setCellValue(bizOpportsExport.getContactName());
                if (ObjTool.isNotNull(bizOpportsExport.getSex())){
                    row.createCell(21).setCellValue(bizOpportsExport.getSex()==1?"男":"女");
                }else{
                    row.createCell(21).setCellValue("");
                }
                row.createCell(22).setCellValue(bizOpportsExport.getContactDeptName());
                row.createCell(23).setCellValue(bizOpportsExport.getJob());
                row.createCell(24).setCellValue(bizOpportsExport.getContactTelephone());
                row.createCell(25).setCellValue(bizOpportsExport.getContactEmail());
                row.createCell(26).setCellValue(bizOpportsExport.getOfficePlane());
                row.createCell(27).setCellValue(bizOpportsExport.getUniversity());
                row.createCell(28).setCellValue(bizOpportsExport.getMajor());
                if (bizOpportsExport.getBirthday()==null){
                    row.createCell(29).setCellValue("");
                }else{
                    row.createCell(29).setCellValue(bizOpportsExport.getBirthday());
                }
                row.createCell(30).setCellValue(bizOpportsExport.getFamilyInfo());
                row.createCell(31).setCellValue(bizOpportsExport.getOther());
                rowNum++;
            }
            OutputStream out = response.getOutputStream();// 得到输出流
            workbook.write(out);
            out.close();
            log.info("商机导出结束");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出商机数据异常");
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    @MethodPermission(menuIdArray = C_BIZ_OPPORTS_IMPORT)
    @ApiOperation(value = "商机-导入")
    @RequestMapping(value = "/bizOpports/import",method = RequestMethod.POST)
    @OpLog(module = OpLog.Module.BIZ_OPPORTS,opType = OpLog.Type.IMPORT)
    public Response importBiz(@RequestParam("file") MultipartFile file,
                                               @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {
        log.info("开始导入商机数据");
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        InputStream is = file.getInputStream();
        Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
        Sheet sheet = workbook.getSheetAt(0);
        //XSSFSheet sheet = ExcelUtil.getOneSheetByInputStream(is, 0);

        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        List<String> stringList = new ArrayList<>();
        if (physicalNumberOfRows == 2) {
            stringList.add("导入商机信息为空，请重新填入信息并导入");
        }
        List<String> emptyList = bizImportExportService.checkEmpty(sheet);
        if (ObjTool.isNotNull(emptyList)) {
            stringList.addAll(emptyList);
        }
        List<String> maxList = bizImportExportService.checkMax(sheet);
        if (ObjTool.isNotNull(maxList)) {
            stringList.addAll(maxList);
        }
        List<String> formatList = bizImportExportService.checkFormat(sheet);
        if (ObjTool.isNotNull(formatList)) {
            stringList.addAll(formatList);
        }
        List<String> formatOtherList = bizImportExportService.checkFormatOther(sheet);
        if (ObjTool.isNotNull(formatOtherList)) {
            stringList.addAll(formatOtherList);
        }
        List<String> checkPersonAccount = bizImportExportService.checkPersonAccount(sheet);
        if (ObjTool.isNotNull(checkPersonAccount)) {
            stringList.addAll(checkPersonAccount);
        }
        if (ObjTool.isNotNull(stringList)){
            log.info("导入商机数据异常");
            return new Response().warning("数据验证失败", stringList);
        }
        bizImportExportService.importBiz(sheet,loginUserDTO);
        log.info("导入商机数据结束");
        return new Response().success();
    }
}
