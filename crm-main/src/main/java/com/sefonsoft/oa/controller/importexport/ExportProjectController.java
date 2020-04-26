package com.sefonsoft.oa.controller.importexport;

import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryExpDTO;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.service.project.ProjectService;
import com.sefonsoft.oa.system.emun.CheckStatus;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：Aron
 * @date ：Created in 2019/12/26 15:22
 * @description：项目导出controller
 * @modified By：
 */
@RestController
@Api(tags = "项目立项全量导出controller")
@Deprecated
public class ExportProjectController extends Response<Object> {



    /**
     * 服务对象
     */
    @Resource
    private ProjectService projectService;

    private static final Logger logger = LoggerFactory.getLogger(ExportProjectController.class);


    @ApiOperation(value = "excel导出项目立项信息(全量)")
    @RequestMapping(value = "/exportProject", method = RequestMethod.GET)
    public void excelTest(HttpServletRequest request, HttpServletResponse response) {

        logger.info("开始导出数据");

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = "项目立项全量信息.xlsx";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //第一步：定义一个新的工作簿
            XSSFWorkbook web = new XSSFWorkbook();
            //第二步：创建一个sheet页
            XSSFSheet sheet = web.createSheet("项目立项全量信息");
            //设置行高
            sheet.setDefaultRowHeight((short) (2 * 256));
            //设置列宽
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 4500);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 4000);
            sheet.setColumnWidth(14, 4000);
            sheet.setColumnWidth(15, 4000);
            sheet.setColumnWidth(16, 4000);
            sheet.setColumnWidth(17, 4000);
            sheet.setColumnWidth(18, 4000);
            sheet.setColumnWidth(19, 4000);
            sheet.setColumnWidth(20, 4500);
            sheet.setColumnWidth(21, 4000);
            sheet.setColumnWidth(22, 4000);
            sheet.setColumnWidth(23, 4000);
            sheet.setColumnWidth(24, 5500);

            sheet.setColumnWidth(25, 25000);

            //设置样式或者是字体
            XSSFFont font = web.createFont();
            //设置字体
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 16);
            //创建第一行
            XSSFRow row = sheet.createRow(0);
            //为该行创建第一个单元格
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("项目编号 ");
            cell = row.createCell(1);
            cell.setCellValue("项目名称 ");
            cell = row.createCell(2);
            cell.setCellValue("审批状态 ");
            cell = row.createCell(3);
            cell.setCellValue("销售负责人 ");  //与18销售负责人 换位置
            cell = row.createCell(4);
            cell.setCellValue("所属客户 ");
            cell = row.createCell(5);
            cell.setCellValue("所属行业");
            cell = row.createCell(6);
            cell.setCellValue("是否重大项目");
            cell = row.createCell(7);
            cell.setCellValue("项目总投资金额(万元)");
            cell = row.createCell(8);
            cell.setCellValue("客户项目阶段");
            cell = row.createCell(9);
            cell.setCellValue("项目所在城市");
            cell = row.createCell(10);
            cell.setCellValue("项目联系人");
            cell = row.createCell(11);
            cell.setCellValue("项目联系人部门");
            cell = row.createCell(12);
            cell.setCellValue("项目联系人职务");
            cell = row.createCell(13);
            cell.setCellValue("项目联系人手机");
            cell = row.createCell(14);
            cell.setCellValue("合作伙伴名称");
            cell = row.createCell(15);
            cell.setCellValue("合作伙伴部门");
            cell = row.createCell(16);
            cell.setCellValue("合作伙伴联系人");
            cell = row.createCell(17);
            cell.setCellValue("合作伙伴联系人手机");
            cell = row.createCell(18);
            cell.setCellValue("项目状态");//与3项目状态换位置
            cell = row.createCell(19);
            cell.setCellValue("销售阶段");
            cell = row.createCell(20);
            cell.setCellValue("预估项目金额(万元)");
            cell = row.createCell(21);
            cell.setCellValue("预估签约时间");
            cell = row.createCell(22);
            cell.setCellValue("预估成功率(%)");
            cell = row.createCell(23);
            cell.setCellValue("所属团体");
            cell = row.createCell(24);
            cell.setCellValue("更新时间");

            cell = row.createCell(25);
            cell.setCellValue("销售产品信息(价格:万元)");

            //定义一个行
            XSSFRow rows;
            //定义一个单元格
            XSSFCell cells;
            //导出项目列表数据
            List<ProjectInfoQueryDTO> list = projectService.getConditionAll();


            if (list != null && list.size() > 0) {
                for (int i = 1; i <= list.size(); i++) {
                    ProjectInfoQueryDTO  projectInfo = list.get(i-1);
                    // 第三步：在这个sheet页里创建一行（每次循环都会创建一个新的行）
                    rows = sheet.createRow(i);
                    // 第四步：在该行创建一个单元格(从标题下第二行开始)

                    //项目编号
                    cells = rows.createCell(0);

                    int checkStatuss = projectInfo.getCheckStatus();
                    if(checkStatuss== CheckStatus.YTG.getCode()){
                        cells.setCellValue(projectInfo.getProjectId());
                    }else { cells.setCellValue("");}


                    //项目名称
                    cells = rows.createCell(1);
                    cells.setCellValue(projectInfo.getProjectName());
                    //审核状态,-1已拒绝，0大区总已驳回，1未审批，2大区总已审批，3已通过
                    cells = rows.createCell(2);

                    int checkStatus = projectInfo.getCheckStatus();
                    if(checkStatus== CheckStatus.YJJ.getCode()){
                        cells.setCellValue(CheckStatus.YJJ.getName());
                    }
                    if(checkStatus== CheckStatus.YBB.getCode()){
                        cells.setCellValue(CheckStatus.YBB.getName());
                    }
                    if(checkStatus== CheckStatus.WSP.getCode()){
                        cells.setCellValue(CheckStatus.WSP.getName());
                    }
                    if(checkStatus== CheckStatus.DQZYSP.getCode()){
                        cells.setCellValue(CheckStatus.DQZYSP.getName());
                    }
                    if(checkStatus== CheckStatus.YTG.getCode()){
                        cells.setCellValue(CheckStatus.YTG.getName());
                    }
                    //项目状态与18销售负责人换位置
                    cells = rows.createCell(3);
                    cells.setCellValue(projectInfo.getEmployeeName());
                    //所属客户
                    cells = rows.createCell(4);
                    cells.setCellValue(projectInfo.getCustomerName());
                    //所属行业
                    cells = rows.createCell(5);
                    cells.setCellValue(projectInfo.getIndustry());
                    //是否重大项目
                    cells = rows.createCell(6);
                    if(projectInfo.getHasImportant()== 1){
                        cells.setCellValue("是");
                    }else{
                        cells.setCellValue("否");
                    }

                    //项目总投资金额(万元)
                    cells = rows.createCell(7);
                    cells.setCellValue(projectInfo.getTotalInvestment().doubleValue());
                    //客户项目阶段
                    cells = rows.createCell(8);
                    cells.setCellValue(projectInfo.getCustomerProjectPhase());
                    //项目所在城市
                    cells = rows.createCell(9);
                    cells.setCellValue(projectInfo.getLocation());
                    //项目联系人
                    cells = rows.createCell(10);
                    cells.setCellValue(projectInfo.getContacts());
                    //项目联系人部门
                    cells = rows.createCell(11);
                    cells.setCellValue(projectInfo.getContactDeptName());
                    //项目联系人职务
                    cells = rows.createCell(12);
                    cells.setCellValue(projectInfo.getContactJob());
                    //项目联系人手机
                    cells = rows.createCell(13);
                    cells.setCellValue(projectInfo.getContactTel());
                    //合作伙伴名称
                    cells = rows.createCell(14);
                    cells.setCellValue(projectInfo.getPartnerName());
                    //合作伙伴部门
                    cells = rows.createCell(15);
                    cells.setCellValue(projectInfo.getCustomerDeptName());
                    //合作伙伴联系人
                    cells = rows.createCell(16);
                    cells.setCellValue(projectInfo.getCustomerContact());
                    //联系人手机
                    cells = rows.createCell(17);
                    cells.setCellValue(projectInfo.getCustomerContactTel());
                    //销售负责人,与3项目状态换位置
                    cells = rows.createCell(18);
                    cells.setCellValue(projectInfo.getProsName());
                    //销售阶段
                    cells = rows.createCell(19);
                    cells.setCellValue(projectInfo.getSpstageName());
                    //预估金额
                    cells = rows.createCell(20);
                    cells.setCellValue(projectInfo.getEstimateMoney().doubleValue());
                    //预估签约时间
                    cells = rows.createCell(21);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date estimateTime = (Date)projectInfo.getEstimateTime();
                    if(ObjTool.isNotNull(estimateTime)){
                        String estimateTimes = df.format(estimateTime);
                        cells.setCellValue(estimateTimes);
                    }else {
                        cells.setCellValue(estimateTime);
                    }

                    //预估成功率
                    cells = rows.createCell(22);
                    cells.setCellValue(projectInfo.getEstimateSuccess());//
                    //所属团队
                    cells = rows.createCell(23);
                    cells.setCellValue(projectInfo.getDeptName());
                    //更新时间
                    cells = rows.createCell(24);
                    Date lastTime = projectInfo.getCreateTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = dateFormat.format(lastTime);
                    cells.setCellValue(time);


                    List<ProductProjectRef> productProjectRefList = projectInfo.getProductProjectRefList();
                    if(ObjTool.isNotNull(productProjectRefList)){
                        //StringBuffer buffer = new StringBuffer();
                        String productProject = "";
                        DecimalFormat dft= new DecimalFormat("#.##");
                        for(ProductProjectRef productProjectRef : productProjectRefList) {

                            String productName = productProjectRef.getProductName();
                            Integer saleCount = productProjectRef.getSaleCount();
                            BigDecimal amount = productProjectRef.getAmount();
                            BigDecimal saleCounts = new BigDecimal(saleCount);
                            String total = dft.format(amount.multiply(saleCounts));
                            String amounts = dft.format(amount);


                            productProject=productProject+("名称:"+productName+",数量:"+saleCount+",单价:"+amounts+",小计:"+total)+"; ";


                        }
                        //产品信息
                        cells = rows.createCell(25);
                        //cells.setCellValue(productProjectRefList.get(0).getAmount().doubleValue());
                        cells.setCellValue(productProject);
                    }



                }
            }
            logger.info("导出数据结束");
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



    @ApiOperation(value = "excel导出项目立项信息(全量)")
    @RequestMapping(value = "/exportProjectAlls", method = RequestMethod.GET)
    public void exportProjectAlls(HttpServletRequest request, HttpServletResponse response) {

        logger.info("开始导出数据");

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = "项目立项全量信息.xlsx";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //第一步：定义一个新的工作簿
            XSSFWorkbook web = new XSSFWorkbook();
            //第二步：创建一个sheet页
            XSSFSheet sheet = web.createSheet("项目立项全量信息");
            //设置行高
            sheet.setDefaultRowHeight((short) (2 * 256));
            //设置列宽
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4500);
            sheet.setColumnWidth(7, 4500);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 4000);
            sheet.setColumnWidth(14, 4000);
            sheet.setColumnWidth(15, 4000);
            sheet.setColumnWidth(16, 4000);
            sheet.setColumnWidth(17, 4000);
            sheet.setColumnWidth(18, 4000);
            sheet.setColumnWidth(19, 4000);
            sheet.setColumnWidth(20, 4500);
            sheet.setColumnWidth(21, 4000);
            sheet.setColumnWidth(22, 4000);
            sheet.setColumnWidth(23, 4000);
            sheet.setColumnWidth(24, 6000);

            sheet.setColumnWidth(25, 4000);
            sheet.setColumnWidth(26, 4000);
            sheet.setColumnWidth(27, 4000);
            sheet.setColumnWidth(28, 4000);

            //设置样式或者是字体
            XSSFFont font = web.createFont();
            //设置字体
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 16);
            //创建第一行
            XSSFRow row = sheet.createRow(0);
            //为该行创建第一个单元格
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("项目编号 ");
            cell = row.createCell(1);
            cell.setCellValue("项目名称 ");
            cell = row.createCell(2);
            cell.setCellValue("审批状态 ");
            cell = row.createCell(3);
            cell.setCellValue("项目状态 ");
            cell = row.createCell(4);
            cell.setCellValue("所属客户 ");
            cell = row.createCell(5);
            cell.setCellValue("所属行业");
            cell = row.createCell(6);
            cell.setCellValue("是否重大项目");
            cell = row.createCell(7);
            cell.setCellValue("项目总投资金额(万元)");
            cell = row.createCell(8);
            cell.setCellValue("客户项目阶段");
            cell = row.createCell(9);
            cell.setCellValue("项目所在城市");
            cell = row.createCell(10);
            cell.setCellValue("项目联系人");
            cell = row.createCell(11);
            cell.setCellValue("项目联系人部门");
            cell = row.createCell(12);
            cell.setCellValue("项目联系人职务");
            cell = row.createCell(13);
            cell.setCellValue("项目联系人手机");
            cell = row.createCell(14);
            cell.setCellValue("合作伙伴名称");
            cell = row.createCell(15);
            cell.setCellValue("合作伙伴部门");
            cell = row.createCell(16);
            cell.setCellValue("合作伙伴联系人");
            cell = row.createCell(17);
            cell.setCellValue("合作伙伴联系人手机");
            cell = row.createCell(18);
            cell.setCellValue("销售负责人");
            cell = row.createCell(19);
            cell.setCellValue("销售阶段");
            cell = row.createCell(20);
            cell.setCellValue("预估项目金额(万元)");
            cell = row.createCell(21);
            cell.setCellValue("预估签约时间");
            cell = row.createCell(22);
            cell.setCellValue("预估成功率(%)");
            cell = row.createCell(23);
            cell.setCellValue("所属团体");
            cell = row.createCell(24);
            cell.setCellValue("更新时间");

            cell = row.createCell(25);
            cell.setCellValue("产品名称");
            cell = row.createCell(26);
            cell.setCellValue("产品数量");
            cell = row.createCell(27);
            cell.setCellValue("产品单价");
            cell = row.createCell(28);
            cell.setCellValue("小计");

            //定义一个行
            XSSFRow rows;
            //定义一个单元格
            XSSFCell cells;
            //导出项目列表数据
            List<ProjectInfoQueryExpDTO> list = projectService.getConditionAlls();


            if (list != null && list.size() > 0) {
                for (int i = 1; i < list.size(); i++) {
                    ProjectInfoQueryExpDTO  projectInfo = list.get(i-1);
                    // 第三步：在这个sheet页里创建一行（每次循环都会创建一个新的行）
                    rows = sheet.createRow(i);
                    // 第四步：在该行创建一个单元格(从标题下第二行开始)

                    //项目编号
                    cells = rows.createCell(0);
                    cells.setCellValue(projectInfo.getProjectId());
                    //项目名称
                    cells = rows.createCell(1);
                    cells.setCellValue(projectInfo.getProjectName());
                    //审核状态,-1已拒绝，0大区总已驳回，1未审批，2大区总已审批，3已通过
                    cells = rows.createCell(2);

                    int checkStatus = projectInfo.getCheckStatus();
                    if(checkStatus== CheckStatus.YJJ.getCode()){
                        cells.setCellValue(CheckStatus.YJJ.getName());
                    }
                    if(checkStatus== CheckStatus.YBB.getCode()){
                        cells.setCellValue(CheckStatus.YBB.getName());
                    }
                    if(checkStatus== CheckStatus.WSP.getCode()){
                        cells.setCellValue(CheckStatus.WSP.getName());
                    }
                    if(checkStatus== CheckStatus.DQZYSP.getCode()){
                        cells.setCellValue(CheckStatus.DQZYSP.getName());
                    }
                    if(checkStatus== CheckStatus.YTG.getCode()){
                        cells.setCellValue(CheckStatus.YTG.getName());
                    }
                    //项目状态
                    cells = rows.createCell(3);
                    cells.setCellValue(projectInfo.getProsName());
                    //所属客户
                    cells = rows.createCell(4);
                    cells.setCellValue(projectInfo.getCustomerName());
                    //所属行业
                    cells = rows.createCell(5);
                    cells.setCellValue(projectInfo.getIndustry());
                    //是否重大项目
                    cells = rows.createCell(6);
                    if(projectInfo.getHasImportant()== 1){
                        cells.setCellValue("是");
                    }else{
                        cells.setCellValue("否");
                    }

                    //项目总投资金额(万元)
                    cells = rows.createCell(7);
                    cells.setCellValue(projectInfo.getTotalInvestment().doubleValue());
                    //客户项目阶段
                    cells = rows.createCell(8);
                    cells.setCellValue(projectInfo.getCustomerProjectPhase());
                    //项目所在城市
                    cells = rows.createCell(9);
                    cells.setCellValue(projectInfo.getLocation());
                    //项目联系人
                    cells = rows.createCell(10);
                    cells.setCellValue(projectInfo.getContacts());
                    //项目联系人部门
                    cells = rows.createCell(11);
                    cells.setCellValue(projectInfo.getContactDeptName());
                    //项目联系人职务
                    cells = rows.createCell(12);
                    cells.setCellValue(projectInfo.getContactJob());
                    //项目联系人手机
                    cells = rows.createCell(13);
                    cells.setCellValue(projectInfo.getContactTel());
                    //合作伙伴名称
                    cells = rows.createCell(14);
                    cells.setCellValue(projectInfo.getPartnerName());
                    //合作伙伴部门
                    cells = rows.createCell(15);
                    cells.setCellValue(projectInfo.getCustomerDeptName());
                    //合作伙伴联系人
                    cells = rows.createCell(16);
                    cells.setCellValue(projectInfo.getCustomerContact());
                    //联系人手机
                    cells = rows.createCell(17);
                    cells.setCellValue(projectInfo.getCustomerContactTel());
                    //销售负责人
                    cells = rows.createCell(18);
                    cells.setCellValue(projectInfo.getEmployeeName());
                    //销售阶段
                    cells = rows.createCell(19);
                    cells.setCellValue(projectInfo.getSpstageName());
                    //预估金额
                    cells = rows.createCell(20);
                    cells.setCellValue(projectInfo.getEstimateMoney().doubleValue());
                    //预估签约时间
                    cells = rows.createCell(21);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date estimateTime = (Date)projectInfo.getEstimateTime();
                    if(ObjTool.isNotNull(estimateTime)){
                        String estimateTimes = df.format(estimateTime);
                        cells.setCellValue(estimateTimes);
                    }else {
                        cells.setCellValue(estimateTime);
                    }

                    //预估成功率
                    cells = rows.createCell(22);
                    cells.setCellValue(projectInfo.getEstimateSuccess());//
                    //所属团队
                    cells = rows.createCell(23);
                    cells.setCellValue(projectInfo.getDeptName());
                    //更新时间
                    cells = rows.createCell(24);
                    Date lastTime = projectInfo.getCreateTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = dateFormat.format(lastTime);
                    cells.setCellValue(time);


                    if(ObjTool.isNotNull(projectInfo.getProductName())){



                            //产品名字
                            cells = rows.createCell(25);
                            //cells.setCellValue(productProjectRefList.get(0).getProductName());
                            cells.setCellValue(projectInfo.getProductName());

                            //产品数量
                            cells = rows.createCell(26);
                            //cells.setCellValue(productProjectRefList.get(0).getSaleCount());
                            cells.setCellValue(projectInfo.getSaleCount());

                            //单价
                            cells = rows.createCell(27);
                            //cells.setCellValue(productProjectRefList.get(0).getAmount().doubleValue());
                            cells.setCellValue(projectInfo.getAmount().doubleValue());

                            //小计
                            cells = rows.createCell(28);
                            //cells.setCellValue(productProjectRefList.get(0).getAmount().doubleValue()*productProjectRefList.get(0).getSaleCount());
                            cells.setCellValue(projectInfo.getAmount().doubleValue()*projectInfo.getSaleCount());


                    }



                }
            }
            logger.info("导出数据结束");
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