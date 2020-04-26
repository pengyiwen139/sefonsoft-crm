package com.sefonsoft.oa.service.contract;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.sefonsoft.oa.domain.contract.vo.ContractExcelVO;

import io.swagger.annotations.ApiModelProperty;

public enum ContractExcelHeader {

  @ApiModelProperty("合同编号")
  contractId(),

  @ApiModelProperty("合同名称")
  contractName();

  static final int HEADER_ROWS = 1;
  static final Class<?> rootType = ContractExcelVO.class;
  
  final String title;
  
  ContractExcelHeader() {
    
    String title = null;
    try {
      ApiModelProperty modelProp = AnnotationUtils.getAnnotation(
          ContractExcelHeader.class.getField(name()), 
          ApiModelProperty.class);
      if (modelProp != null) {
        title = modelProp.value();
      }
    } catch (NoSuchFieldException | SecurityException e) {
      // ignore, never happen
    }
    this.title = title;
  }
  
  public String title() {
    return this.title;
  }
  
  private void setFieldValue(ContractExcelVO vo, Cell cell) throws Exception{
    
    PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(rootType, this.toString());
    
    if (pd == null) {
      throw new NullPointerException("Bean 中不包含" + this.toString() + " 的字段");
    }
    
    Class<?> type = pd.getPropertyType();
    
    if (type == String.class) {
      String v = cell.getStringCellValue();
      pd.getWriteMethod().invoke(vo, v);
    } else if (type == Long.class) {
      String v = cell.getStringCellValue();
      if (v != null) {
        try {
          long result = Long.parseLong(v);
          pd.getWriteMethod().invoke(vo, result);
        } catch (Exception e) {
          throw new IllegalArgumentException("无法转换");
        }
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private void setCellValue(ContractExcelVO vo, Cell cell) throws Exception {
    
    PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(rootType, this.toString());
    
    if (pd == null) {
      throw new NullPointerException("Bean 中不包含" + this.toString() + " 的字段");
    }
    
    Class<?> type = pd.getPropertyType();
    
    if (type == String.class) {
      String v = (String) pd.getReadMethod().invoke(vo);
      cell.setCellValue(v);
    } else if (type == Long.class) {
      String v = cell.getStringCellValue();
      if (v != null) {
        long result = Long.parseLong(v);
        pd.getWriteMethod().invoke(vo, result);
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public static List<ContractExcelVO> read(Sheet sheet, List<String> errors) {

    List<ContractExcelVO> datas = new LinkedList<ContractExcelVO>();

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    Validator validator = validatorFactory.getValidator();

    // 初始化
    String[] errorCollector = new String[values().length];

    for (int i = 0; i < sheet.getLastRowNum(); i++) {
      
      Arrays.fill(errorCollector, null);
      
      Row row = sheet.getRow(i + HEADER_ROWS);

      ContractExcelVO vo = resolveRow(row, errorCollector);

      Set<ConstraintViolation<ContractExcelVO>> validations = validator.validate(vo);
      
      for (ConstraintViolation<ContractExcelVO> validation : validations) {
        
        Path p = validation.getPropertyPath();
        
        ContractExcelHeader h = ContractExcelHeader.valueOf(p.toString());
        
        if (errorCollector[h.ordinal()] == null) {
          errorCollector[h.ordinal()] = validation.getMessage();
        }
      }
      
      boolean valid = true;
      for (String errormsg : errorCollector) {
        if (errormsg != null) {
          valid = false;
          errors.add(errormsg);
        }
      }
      
      if (valid) {
        datas.add(vo);
      }
    }
    return datas;
  }

  private static ContractExcelVO resolveRow(Row row, String[] errorCollector) {

    ContractExcelVO vo = new ContractExcelVO();

    ContractExcelHeader[] values = ContractExcelHeader.values();

    for (ContractExcelHeader header : values) {

      Cell cell = row.getCell(header.ordinal());

      if (cell != null) {
        try {
          header.setFieldValue(vo, cell);
        } catch (Exception e) {
          errorCollector[header.ordinal()] = e.getMessage();
        }
      }
    }
    return vo;
  }

  public static void write(Sheet sheet, List<ContractExcelVO> datas) {
    
    Row hr = sheet.createRow(0);

    // 行头
    for (ContractExcelHeader h : values()) {
      Cell cell = hr.createCell(h.ordinal());
      cell.setCellValue(h.title());
    }
    
    // 数据
    for (int i = 0; i < datas.size(); i++) {
      Row row = sheet.createRow(i + HEADER_ROWS);
      ContractExcelVO vo = datas.get(i);
      writeRow(row, vo);
    }
  }

  private static void writeRow(Row row, ContractExcelVO vo) {

    for (ContractExcelHeader header : values()) {
      Cell cell = row.createCell(header.ordinal());
      try {
        header.setCellValue(vo, cell);
      } catch (Exception e) {
        cell.setCellValue("ERROR: " + e.getMessage());
      }
    }
  }

}
