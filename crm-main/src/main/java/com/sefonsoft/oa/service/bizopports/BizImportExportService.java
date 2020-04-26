package com.sefonsoft.oa.service.bizopports;

import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * Created by liwenyi
 * Date: 2020/3/25
 * Desc:
 */
public interface BizImportExportService {

    /**
     * 功能描述: 查询所有商机
     *
     * @Param: []
     * @Return: java.util.List<com.sefonsoft.oa.entity.bizopports.BizOpports>
     * @Author: liwenyi
     */
    List<BizOpportsExport> queryAllBizOpportsExport();

    List<String> checkEmpty(Sheet sheet);

    List<String> checkMax(Sheet sheet);

    List<String> checkFormat(Sheet sheet);

    List<String> checkPersonAccount(Sheet sheet);

    List<String> checkFormatOther(Sheet sheet);

    boolean importBiz(Sheet sheet, LoginUserDTO loginUserDTO);

}
