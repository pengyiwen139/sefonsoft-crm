package com.sefonsoft.oa.system.utils;

import com.sefonsoft.oa.system.annotation.CellMapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/12/26 16:41
 * @description：excel映射到实体类
 * @modified By：
 */
public class ExcelMappingBeanUtil {

    private DateFormat format = new SimpleDateFormat(com.sefonsoft.oa.system.utils.DateUtil.DEFAULT_FORMAT);

    public <T> List<T> parse(Class<T> clazz, MultipartFile file, HttpSession session) throws IllegalAccessException, IOException, InstantiationException, ParseException {
        List<T> resultList = new ArrayList<>();

        Map<String, Field> beanFields = getBeanFields(clazz);
        Map<String, String> excelHeadFields = new HashMap<>();


        //获取excel的表头属性
        InputStream is = file.getInputStream();
        XSSFSheet sheet = ExcelUtil.getOneSheetByInputStream(is, 0);
        XSSFRow headRow = sheet.getRow(0);
        for (Cell title : headRow) {
            CellReference cellRef = new CellReference(title);
            excelHeadFields.put(cellRef.getCellRefParts()[2], title.getRichStringCellValue().getString());
        }

        XSSFRow row;
        //循环获取除第一二行之外的数据
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            System.out.println();

            T t = clazz.newInstance();
            Row dataRow = sheet.getRow(i);
            if (ObjTool.isNotNull(dataRow)) {

                for (Cell cell : dataRow) {
                    CellReference cellRef = new CellReference(cell);
                    //获取当前cell的excel的当前列的cell指针
                    String cellTag = cellRef.getCellRefParts()[2];
                    //根据指针在excel中获取到当前列的属性名称
                    String cellKey = excelHeadFields.get(cellTag);
                    Field field = beanFields.get(cellKey);
                    if (null != field) {
                        field.setAccessible(true);
                        getAndSetCellValue2Bean(cell, t, field, session, i);
                    }
                }

            }
            resultList.add(t);
        }

        return resultList;
    }

    private void getAndSetCellValue2Bean(Cell cell, Object o, Field field, HttpSession session, int i) throws IllegalAccessException, ParseException {
        //cellMapping注解
        CellMapping annotation = field.getAnnotation(CellMapping.class);
        //注解的name值
        String name = annotation.name();

        switch (cell.getCellTypeEnum()) {
            case BLANK:
                break;
            case BOOLEAN:
                field.setBoolean(o, cell.getBooleanCellValue());
                break;
            case ERROR:
                field.setByte(o, cell.getErrorCellValue());
                break;
            case FORMULA:
                field.set(o, cell.getCellFormula());
                break;
            case NUMERIC:
                //Double类型限制
                boolean doubleFlag = annotation.doubleValueFlag();
                //Integer类型限制
                boolean integerFlag = annotation.integerValueFlag();


                if (DateUtil.isCellDateFormatted(cell)) {
                    if (field.getType().getName().equals(Date.class.getName())) {
                        field.set(o, cell.getDateCellValue());
                    } else {
                        field.set(o, format.format(cell.getDateCellValue()));
                    }
                } else {
                    if (field.getType().isAssignableFrom(Integer.class) || field.getType().getName().equals("int")) {
                        field.setInt(o, (int) cell.getNumericCellValue());
                    } else if (field.getType().isAssignableFrom(Short.class) || field.getType().getName().equals("short")) {
                        field.setShort(o, (short) cell.getNumericCellValue());
                    } else if (field.getType().isAssignableFrom(Float.class) || field.getType().getName().equals("float")) {
                        field.setFloat(o, (float) cell.getNumericCellValue());
                    } else if (field.getType().isAssignableFrom(Byte.class) || field.getType().getName().equals("byte")) {
                        field.setByte(o, (byte) cell.getNumericCellValue());
                    } else if (field.getType().isAssignableFrom(Double.class) || field.getType().getName().equals("double")) {
                        field.setDouble(o, cell.getNumericCellValue());
                    } else if (field.getType().isAssignableFrom(String.class)) {
                        String s = String.valueOf(cell.getNumericCellValue());
                        if (s.contains("E")) {
                            s = s.trim();
                            BigDecimal bigDecimal = new BigDecimal(s);
                            s = bigDecimal.toPlainString();
                        }
                        //防止整数判定为浮点数
                        if (s.endsWith(".0")) {
                            s = s.substring(0, s.indexOf(".0"));
                        }


                        if (ObjTool.isNotNull(s)) {
                            //校验Double类型限制
                            if (doubleFlag) {
                                try {
                                    Double doubleVal = Double.valueOf(s);
                                } catch (Exception e) {
                                    session.setAttribute("EXCEL_CELL_DOUBLE_TYPE_ERROR", "第" + i + "行的" + name + "不符合规范");
                                    e.printStackTrace();
                                }
                            }

                            //校验Integer类型限制
                            if (integerFlag) {
                                try {
                                    Integer integerVal = Integer.valueOf(s);
                                } catch (Exception e) {
                                    session.setAttribute("EXCEL_CELL_INTEGER_TYPE_ERROR", "第" + i + "行的" + name + "不符合规范");
                                    e.printStackTrace();
                                }
                            }


                        }

                        field.set(o, s);
                    } else {
                        field.set(o, cell.getNumericCellValue());
                    }
                }
                break;
            case STRING:
                //长度限制
                Integer length = annotation.length();
                //属性值
                String strValue = cell.getRichStringCellValue().getString();

                if (ObjTool.isNotNull(strValue)) {
                    strValue = strValue.replace(" ", "");

                    //校验字符串长度
                    if (ObjTool.isNotNull(length) && (!length.equals(0))) {
                        if ((!field.getType().getName().equals(Date.class.getName()))) {
                            if (strValue.length() > length) {
                                session.setAttribute("EXCEL_CELL_TOO_LONG","第"+i+"条项目中的"+name+"最长不能超过"+length+"个字,请修改后重新导入");
                            }
                        }
                    }

                }



                //获取值
                if (field.getType().getName().equals(Date.class.getName())) {
                    field.set(o, format.parse(cell.getRichStringCellValue().getString().replace(" ","")));
                } else {
                    field.set(o, cell.getRichStringCellValue().getString().replace(" ",""));
                }
                break;
            default:
                field.set(o, cell.getStringCellValue());
                break;
        }
    }


    public static Map<String, Field> getBeanFields(Class clazz) {
        //实体类的属性
        Map<String, Field> fieldMap = new HashMap<String, Field>(16);
        Field[] fields = clazz.getDeclaredFields();
        //这里开始处理映射类型里的注解
        for (Field field : fields) {
            if (field.isAnnotationPresent(CellMapping.class)) {
                CellMapping mapperCell = field.getAnnotation(CellMapping.class);
                fieldMap.put(mapperCell.name(), field);
            }
        }
        return fieldMap;
    }

}
