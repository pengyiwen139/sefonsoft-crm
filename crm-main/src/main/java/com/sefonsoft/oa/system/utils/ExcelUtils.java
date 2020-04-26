package com.sefonsoft.oa.system.utils;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.system.exception.MsgException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;


/**
 * @author xielf
 */
public class ExcelUtils {


  public static final String SUFFIX_CLS = ".xls";

  public static final String SUFFIX_XLSX = ".xlsx";

  /**
   * xielf
   * <p>
   * 客户导出title设置
   */
  public static final List<String> customerTitleList = ImmutableList.of(

      "客户名称	",
      "省/市/区县	",
      "单位性质	",
      "所属行业	",
      "单位地址	",
      "销售负责人	",
      "销售负责人工号	",
      "座机	",
      "邮编	",
      "网址	",
      "传真	",
      "联系人姓名	",
      "性别	",
      "部门	",
      "职务	",
      "手机号码	",
      "邮箱	",
      "办公座机	",
      "毕业学校	",
      "专业	",
      "生日(年/月/日)	",
      "家庭状况	",
      "其他"
  );

  public final static class CustomerExcelIdx {

    /**
     * 客户名称
     */
    public static final int CUSTOMER_NAME = 0;

    /**
     * 省市区
     */
    public static final int PROVINCIAL = 1;

    /**
     * 企业性质名称
     */
    public static final int ENTER_NAME = 2;

    /**
     * 行业
     */
    public static final int INDUSTRY = 3;

    /**
     * 详细地址
     */
    public static final int ADDRESS = 4;

    /**
     * 员工姓名
     */
    public static final int EMPLOYEE_NAME = 5;

    /**
     * 工号
     */
    public static final int EMPLOYEE_ID = 6;

    /**
     * 客户联系电话
     */
    public static final int TELEPHONE = 7;


    /**
     * 邮编
     */
    public static final int ZIP_CODE = 8;


    /**
     * 公司网址
     */
    public static final int WEBSITE = 9;


    public static final int FAX = 10;

    /***
     * =================以下为联系人信息
     */

    /**
     * 联系人
     */
    public static final int CONTACT_NAME = 11;

    /**
     * 性别
     */
    public static final int GENDER = 12;


    /**
     * 所属部门
     */
    public static final int DEPT_NAME = 13;

    /**
     * 职务
     */
    public static final int JOB = 14;

    /**
     * 联系人手机号码
     */
    public static final int CONTACT_TELPHONE = 15;
    /**
     * 邮箱
     */
    public static final int CONTACT_EMAIL = 16;

    /**
     * 办公座机
     */
    public static final int OFFICE_PLANE = 17;

    /**
     * 大学
     */
    public static final int UNIVERSITY = 18;

    /**
     * 主修课
     */
    public static final int MAJOR = 19;
    /**
     * 生日
     */
    public static final int BIRTHDAY = 20;

    /**
     * 家庭情况
     */
    public static final int FAMILY_INFO = 21;
    /**
     * 其他
     */
    public static final int OTHER = 22;

    private CustomerExcelIdx() {
    }
  }


  public static Workbook createWorkBook(InputStream is, String name) throws IOException {

    if (name.endsWith(SUFFIX_CLS)) {
      try {
        return new HSSFWorkbook(is);
      } catch (OLE2NotOfficeXmlFileException e) {
        e.printStackTrace();
        throw new MsgException(String.format("文件不存在或 %s 文件格式已损坏",SUFFIX_CLS));
      }
    } else if (name.endsWith(SUFFIX_XLSX)) {
      try {
        return new XSSFWorkbook(is);
      } catch (OLE2NotOfficeXmlFileException e) {
        e.printStackTrace();
        throw new MsgException(String.format("文件不存在或 %s 文件格式已损坏",SUFFIX_XLSX));
      }
    } else {
      throw new MsgException("不支持的文件类型");
    }
  }

  public static Sheet getSheet(Workbook workbook, int idx) {

    return workbook.getSheetAt(idx);
  }


  public static Sheet getSheet(Workbook workbook, String name) {

    return workbook.getSheet(name);
  }

  public static int getRowNum(Sheet sheet) {
    return sheet.getPhysicalNumberOfRows();

  }

  public static Row getRow(Sheet sheet, int idx) {

    return sheet.getRow(idx);
  }


  public static int getCellNum(Row row) {

    return row.getLastCellNum();
  }

  public static Cell getCell(Row row, int idx) {

    return row.getCell(idx);
  }

  public static String cellToString(Cell cell) {
    
    if (cell == null) {
      return null;
    }

    switch (cell.getCellTypeEnum()) {
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          DateFormat sdf = new SimpleDateFormat(com.sefonsoft.oa.system.utils.DateUtil.DEFAULT_FORMAT_DATE, LocaleUtil.getUserLocale());
          return sdf.format(cell.getDateCellValue());
        }
        
        cell.getNumericCellValue();
        
        BigDecimal bd = new BigDecimal(cell.getNumericCellValue()).setScale(4, BigDecimal.ROUND_UP);
        
        if (isIntegerValue(bd)) {
          return getNumericNoENotationValue(cell);
        } else {
          return bd.toPlainString();
        }
      case STRING:
        return cell.getRichStringCellValue().toString();
      case FORMULA:
        return cell.getCellFormula();
      case BLANK:
        return "";
      case BOOLEAN:
        return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
      case ERROR:
        return ErrorEval.getText(cell.getErrorCellValue());
      default:
        return "Unknown Cell Type: " + cell.getCellTypeEnum();
    }
  }
  
  private static boolean isIntegerValue(BigDecimal bd) {
    return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
  }


  /**
   * 将为数字的cell转化成string
   *
   * @param cell
   * @return
   */
  public static String getNumericNoENotationValue(Cell cell) {
    return new DecimalFormat("#").format(cell.getNumericCellValue());
  }

  /**
   * 处理导入小数点
   */
  public static String deleteDecimalPoint(String num) {

    try {
      Double.valueOf(num);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      throw new MsgException(e.getMessage());
    }
    if (!num.contains(".")) {
      return num;
    }
    return num.substring(0, num.indexOf("."));
  }

}
