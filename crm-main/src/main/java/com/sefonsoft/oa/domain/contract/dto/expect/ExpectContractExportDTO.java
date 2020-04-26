package com.sefonsoft.oa.domain.contract.dto.expect;

import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (ExpectContractInfo)实体类
 *
 * @author makejava
 * @since 2020-04-17 16:34:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("合同预测导出实体类")
public class ExpectContractExportDTO implements Serializable {

    private static final long serialVersionUID = 4292684876703180061L;

    @ApiModelProperty(value = "主键", hidden = true)
    private Long id;

    @ApiModelProperty("合同预测产品金额实体列表")
    private List<ExpectContractProductRef> contractProductRefList;

    @ApiModelProperty(value = "立项项目id", example = "SFWY-202001-0055")
    private String projectId;

    @ApiModelProperty(value = "立项项目名称", example = "滕21aa59e5a99ae6eb278b")
    private String projectName;

    @ApiModelProperty(value = "销售负责人id", example = "SF3585")
    private String employeeId;

    @ApiModelProperty(value = "销售负责人名称", example = "黎79")
    private String employeeName;

    @ApiModelProperty(value = "部门id", example = "BM0020")
    private String deptId;

    @ApiModelProperty(value = "部门名称", example = "交通事业部")
    private String deptName;

    @ApiModelProperty(value = "签订甲方id，即客户编号", example = "KH202001090005")
    private String signCustomerId;

    @ApiModelProperty(value = "签订甲方名称，即客户名称", example = "山东c6bb42656a平台")
    private String signCustomerName;

    @ApiModelProperty(value = "行业，可选项参考project_info的industry字段，下拉选项", example = "公安")
    private String industry;

    @ApiModelProperty(value = "合同性质类型，1直签，2渠道", example = "1")
    private Integer contractNatureType;

    @ApiModelProperty(value = "销售负责人所在省区", example = "四川省/成都市/天府新区")
    private String employeeLocation;

    @ApiModelProperty(value = "用户所在地", example = "四川省/成都市/天府新区")
    private String location;

    @ApiModelProperty(value = "最终客户编号，只可随着立项项目的改变而改变", example = "KH202001090005")
    private String finalCustomerId;

    @ApiModelProperty(value = "最终客户名称，只可随着立项项目的改变而改变", example = "山东c6bb42656a平台")
    private String finalCustomerName;

    @ApiModelProperty(value = "预估签约时间，只要展示年月", example = "2019-12")
    private String estimateTime;

    @ApiModelProperty(value = "预估签约金额", example = "520.20")
    private Double estimateMoney;

    @ApiModelProperty(value = "合同类型：必填，下拉选择框，包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同", example = "1")
    private Integer expectContractType;

    @ApiModelProperty(value = "成单概率 1-100", example = "100")
    private Integer estimateSuccess;

    @ApiModelProperty(value = "是否重大项目：1是，2不是", example = "1")
    private Integer hasImportant;

    @ApiModelProperty(value = "所需公司支持")
    private String otherSupport;

    @ApiModelProperty(value = "操作人id", hidden = true)
    private String operator;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date lastTime;

    //直签or渠道（字符串）
    private String contractNatureTypeStr;

    //是否重要（字符串）
    private String hasImportantStr;

    //"合同类型字符串：必填，下拉选择框，包括:1产品合同、2服务合同、3产品+服务、4集成合同，5默认产品合同
    private String expectContractTypeStr;

    //第一个元素单价，第二个个数，第三个总数
    private List<String> needProductInfo;

    //产品名称列表
    private List<String> productNameList = Arrays.asList(
                                                        "UE",
                                                        "UE-GIS",
                                                        "UE-W3D",
                                                        "UE-AI",
                                                        "BE",
                                                        "ME",
                                                        "Miner",
                                                        "DS",
                                                        "Govern",
                                                        "Hadoop",
                                                        "CellularDB",
                                                        "AE",
                                                        "ETL",
                                                        "BC",
                                                        "实施服务",
                                                        "维保服务",
                                                        "其他");

    //excel产品除外的表头信息
    private static List<String> excelOtherHead = Arrays.asList(
                                                                "编号",
                                                                "大区/事业部",
                                                                "省/区",
                                                                "销售",
                                                                "项目名称",
                                                                "签订甲方",
                                                                "直签or渠道",
                                                                "行业",
                                                                "用户所在地(省/市）",
                                                                "最终用户名称",
                                                                "签订时间",
                                                                "业绩金额（万元）",
                                                                "合同类型",
                                                                "成单率",
                                                                "是否立项",
                                                                "是否是重大项目",
                                                                "所需公司支持（详细阐述）"
                                                        );

    //产品相关表头
    private static List<String> excelProductHead = Arrays.asList(
                                                                    "UE",
                                                                    "UE合计",
                                                                    "UE-GIS",
                                                                    "UE-GIS合计",
                                                                    "UE-W3D",
                                                                    "UE-W3D合计",
                                                                    "UE-AI",
                                                                    "UE-AI合计",
                                                                    "ME",
                                                                    "Me合计",
                                                                    "BE",
                                                                    "BE合计",
                                                                    "MINER",
                                                                    "MINER合计",
                                                                    "HADOOP",
                                                                    "HADOP合计",
                                                                    "GOVERN",
                                                                    "GOVERN合计",
                                                                    "ETL",
                                                                    "ETL合计",
                                                                    "DS",
                                                                    "DS合计",
                                                                    "AE",
                                                                    "AE合计",
                                                                    "BC",
                                                                    "BC合计",
                                                                    "CellularDB",
                                                                    "CellularDB合计",
                                                                    "实施服务",
                                                                    "维保合同",
                                                                    "期限"
                                                                );

    /**
     * 设置excel表头
     *
     * @param sheet
     */
    public static void applyHeader(Sheet sheet) {
        Row th = sheet.createRow(0);
        th.setHeightInPoints(30);
        Row th1 = sheet.createRow(1);
        th1.setHeightInPoints(30);
        int i = 0;
        for (int k = 0; k < excelOtherHead.size(); k++) {
            //合并产品之外的信息的单元格
            CellRangeAddress craPatn = new CellRangeAddress(0, 1, k, k);
            sheet.addMergedRegion(craPatn);
            th.createCell(i++).setCellValue(excelOtherHead.get(k));
        }
        for (int u = 0; u < excelProductHead.size(); u++) {
            //合并产品的信息的单元格
            if (i % 3 == 2 && i >= 17 && i!=59) {
                CellRangeAddress craPatn = new CellRangeAddress(0, 0, i, i+1);
                sheet.addMergedRegion(craPatn);
            }

            if ((excelProductHead.size() - u) < 4) {
                th.createCell(i++).setCellValue(excelProductHead.get(u));
            }else if (u % 2 == 1) {
                th.createCell(i++).setCellValue(excelProductHead.get(u));
                th1.createCell(i).setCellValue("金额");
            } else {
                th.createCell(i++).setCellValue(excelProductHead.get(u));
                th1.createCell(i).setCellValue("数量");
                i++;
            }
        }
        th1.createCell(17).setCellValue("金额");
        th1.createCell(60).setCellValue("金额");
        th1.createCell(61).setCellValue("年");
    }


    private ConcurrentHashMap<Integer, Row> getNeedProductInfoExport(List<ExpectContractProductRef> contractProductRefList, String mappingProductName, int i, Row row) {
        ConcurrentHashMap<Integer, Row> hashMap = new ConcurrentHashMap<>();
        ExpectContractProductRef expectContractProductRef = contractProductRefList.stream().filter(bean -> mappingProductName.equals(bean.getProductName())).findAny().orElse(null);
        if (ObjTool.isNotNull(expectContractProductRef)) {
            needProductInfo = new ArrayList<>();

            if (ObjTool.isNotNull(expectContractProductRef.getAmount()) && ObjTool.isNotNull(expectContractProductRef.getAmount())) {
                needProductInfo.add(expectContractProductRef.getAmount().toString());
                needProductInfo.add(expectContractProductRef.getSaleCount().toString());
                double sumAmount = expectContractProductRef.getAmount() * expectContractProductRef.getSaleCount();
                needProductInfo.add(sumAmount + "");
            } else {
                needProductInfo.add(null);
                needProductInfo.add(null);
                needProductInfo.add(null);
            }

            if (mappingProductName.equals("实施服务")) {
                row.createCell(i++).setCellValue(needProductInfo.get(2));
            } else if (mappingProductName.equals("维保合同")) {
                row.createCell(i++).setCellValue(needProductInfo.get(2));
                row.createCell(i++).setCellValue(needProductInfo.get(1));
            } else {
                row.createCell(i++).setCellValue(needProductInfo.get(0));
                row.createCell(i++).setCellValue(needProductInfo.get(1));
                row.createCell(i++).setCellValue(needProductInfo.get(2));
            }

        } else {
            if (mappingProductName.equals("实施服务")) {
                row.createCell(i++).setCellValue("");
            } else if (mappingProductName.equals("维保合同")) {
                row.createCell(i++).setCellValue("");
                row.createCell(i++).setCellValue("");
            } else {
                row.createCell(i++).setCellValue("");
                row.createCell(i++).setCellValue("");
                row.createCell(i++).setCellValue("");
            }
        }
        hashMap.put(i, row);
        return hashMap;
    }


    public void exportSetData(Workbook workbook, Row row, Sheet sheet) {
        int i = 0;

        //编号
        row.createCell(i++).setCellValue(id);
        //大区
        row.createCell(i++).setCellValue(deptName);
        //省/区
        row.createCell(i++).setCellValue(employeeLocation);
        //省/区
        row.createCell(i++).setCellValue(employeeName);
        //项目名称
        row.createCell(i++).setCellValue(projectName);
        //签订甲方
        row.createCell(i++).setCellValue(signCustomerName);
        //直签or渠道
        switch (contractNatureType) {
            case 1:
                contractNatureTypeStr = "直签";
                break;
            case 2:
                contractNatureTypeStr = "渠道";
                break;
        }
        row.createCell(i++).setCellValue(contractNatureTypeStr);
        //行业
        row.createCell(i++).setCellValue(industry);
        //用户所在地(省/市）
        row.createCell(i++).setCellValue(location);
        //最终用户名称
        row.createCell(i++).setCellValue(finalCustomerName);
        //签订时间
        row.createCell(i++).setCellValue(estimateTime.substring(0, 7));
        //业绩金额（万元）
        row.createCell(i++).setCellValue(estimateMoney);
        //合同类型
        switch (expectContractType) {
            case 1:
                expectContractTypeStr = "产品合同";
                break;
            case 2:
                expectContractTypeStr = "服务合同";
            case 3:
                expectContractTypeStr = "产品+服务";
            case 4:
                expectContractTypeStr = "集成合同";
            case 5:
                expectContractTypeStr = "默认产品合同";
        }
        row.createCell(i++).setCellValue(expectContractTypeStr);
        //成单率
        row.createCell(i++).setCellValue(estimateSuccess + "%");
        //是否立项
        row.createCell(i++).setCellValue("是");
        //是否是重大项目
        switch (hasImportant) {
            case 1:
                hasImportantStr = "是";
                break;
            case 2:
                hasImportantStr = "否";
                break;
        }
        row.createCell(i++).setCellValue(hasImportantStr);
        //所需公司支持（详细阐述）
        row.createCell(i++).setCellValue(otherSupport);

        //UE金额+数量+合计

        for (int q = 0; q < productNameList.size(); q++) {
            ConcurrentHashMap hashMap = getNeedProductInfoExport(contractProductRefList, productNameList.get(q), i, row);
            for (Object key : hashMap.keySet()) {
                i = (Integer) key;
                row = (Row) hashMap.get(key);
            }
        }
    }

}