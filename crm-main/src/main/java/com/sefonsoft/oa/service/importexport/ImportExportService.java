package com.sefonsoft.oa.service.importexport;

import com.sefonsoft.oa.domain.importexport.ImportErrorMessage;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.Map;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/29 9:45
 * @description：导入导出service
 * @modified By：
 */
public interface ImportExportService {

    boolean importCustomersAndContacts(XSSFSheet sheet, LoginUserDTO loginUserDTO);
    
    /**
     * 错误消息
     * @param sheet
     * @param loginUserDTO
     * @return
     */
    List<ImportErrorMessage> importCustomersShareAndContacts(XSSFSheet sheet, LoginUserDTO loginUserDTO);

    Map<String, String> checkEmpty(XSSFSheet sheet, LoginUserDTO loginUserDTO);

    Map<String, String> checkFormat(XSSFSheet sheet, LoginUserDTO loginUserDTO);

    Map<String, String> checkMax(XSSFSheet sheet, LoginUserDTO loginUserDTO);

    Map<String, String> checkMobilePhone(XSSFSheet sheet, LoginUserDTO loginUserDTO);

    Map<String, String> checkPersonAccount(XSSFSheet sheet, LoginUserDTO loginUserDTO);
    
   }
