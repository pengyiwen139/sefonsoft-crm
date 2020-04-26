package com.sefonsoft.oa.system.utils;


import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/27 14:18
 * @description：excel工具类
 * @modified By：
 */
public class ExcelUtil {

    /**
     * 根据输入流获取excel的指定sheet
     *
     * @param is
     * @return
     */
    public static XSSFSheet getOneSheetByInputStream(InputStream is, int index) {
        XSSFSheet sheet = null;
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(is);
            sheet = xwb.getSheetAt(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * 根据这一行row获取指定下标的cell字符串，为空则返回null，否则返回cell的字符串
     *
     * @param row
     * @param index
     * @return
     */
    public static String getCellStr(Row row, int index) {
        if (ObjTool.isNotNull(row)) {
            Cell cell = row.getCell(index);
            if (ObjTool.isNotNull(cell)) {
                return parseString(cell);
            } else {
                return null;
            }
        }
        return null;
    }

    public static String parseString(Cell cell){

        switch(cell.getCellTypeEnum()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat sdf = new SimpleDateFormat(com.sefonsoft.oa.system.utils.DateUtil.DEFAULT_FORMAT_DATE, LocaleUtil.getUserLocale());
                    sdf.setTimeZone(LocaleUtil.getUserTimeZone());
                    return sdf.format(cell.getDateCellValue());
                }

                return Double.toString(cell.getNumericCellValue());
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

    /**
     * 将为数字的cell转化成string
     *
     * @param cell
     * @return
     */
    public static String getNumericCellValue(Cell cell) {
        if (ObjTool.isNotNull(cell) && cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (String.valueOf(cell.getNumericCellValue()).indexOf("E") == -1) {
                return String.valueOf(cell.getNumericCellValue());
            } else {
                return new DecimalFormat("#").format(cell.getNumericCellValue());
            }
        }
        return cell.toString();
    }

    /**
     * 处理导入小数点
     */
    public static String numOfImport(Cell cell) {
        if (ObjTool.isNotNull(cell)) {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType){
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return new DecimalFormat("0").format(cell.getNumericCellValue());
                default:
                    return cell.toString();
            }
        }
        return null;
    }

}
