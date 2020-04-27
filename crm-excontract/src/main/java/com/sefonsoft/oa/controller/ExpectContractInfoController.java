package com.sefonsoft.oa.controller;

import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractExportDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractInsertDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedParamDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractPageDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractUpdateDTO;
import com.sefonsoft.oa.service.ExpectContractInfoService;
import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * (ExpectContractInfo)表控制层
 *
 * @author makejava
 * @since 2020-04-17 15:47:26
 */
@Api(tags = {"合同预测相关接口"})
@RestController
public class ExpectContractInfoController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private ExpectContractInfoService expectContractInfoService;

    @ApiOperation(value = "获取新增编辑页面所需数据（负责人为当前用户的立项通过项目list和当前用户的客户list）", response = ExpectContractNeedParamDTO.class)
    @GetMapping("/expect/contract/getNeedParams")
    public Response getNeedParams(@CurrentUser LoginUserDTO loginUserDTO) {
        ExpectContractNeedParamDTO expectContractNeedParamDTO = expectContractInfoService.getNeedParams(loginUserDTO);
        return ObjTool.isNotNull(expectContractNeedParamDTO) ? success(expectContractNeedParamDTO) : failure();
    }

    @ApiOperation(value = "新增合同预测")
    @PutMapping("/expect/contract")
    public Response insert(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody @Valid ExpectContractInsertDTO expectContractInsertDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return failure(bindingResult);
        return expectContractInfoService.insert(expectContractInsertDTO, loginUserDTO) ? success() : failure();
    }

    @ApiOperation(value = "修改合同预测")
    @PatchMapping("/expect/contract")
    public Response update(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody @Valid ExpectContractUpdateDTO expectContractUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return failure(bindingResult);
        return expectContractInfoService.update(expectContractUpdateDTO, loginUserDTO) ? success() : failure();
    }

    @ApiOperation(value = "批量删除合同预测")
    @DeleteMapping("/expect/contract/{idArray}")
    public Response batchDelete(@ApiParam(required = true, value = "id数组") @PathVariable(value = "idArray") List<Long> idArray) {
        return expectContractInfoService.batchDeleteById(idArray) ? success() : failure();
    }

    @ApiOperation(value = "列表查询合同预测", response = ExpectContractPageDTO.class)
    @PostMapping("/expect/contract")
    public Response list(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO expectContractSearchDTO) {
        //查询合同预测列表数据
        //查询合同预测列表页统计图封装数据
        return success(new ExpectContractPageDTO(expectContractInfoService.list(loginUserDTO, expectContractSearchDTO),
                                                 expectContractInfoService.getExpectContractChart(expectContractSearchDTO)));
    }

    @ApiOperation(value = "导出合同预测")
    @PostMapping(value = "/expect/contract/export")
    @OpLog(module = OpLog.Module.EXCPECT_CONTRACT, opType = OpLog.Type.EXPORT)
    public void export(
            @RequestBody ExpectContractSearchDTO expectContractSearchDTO,
            @ApiParam(hidden = true) HttpServletResponse response,
            @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {

        final List<ExpectContractExportDTO> list = expectContractInfoService.listExportNoPage(loginUserDTO, expectContractSearchDTO);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String fileName = "合同预测.xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        fileName = URLEncoder.encode(fileName, "UTF-8");

        OutputStream out = response.getOutputStream();

            try (Workbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
                Sheet sheet = workbook.createSheet("合同预测");

                //设置表头
                ExpectContractExportDTO.applyHeader(sheet);

                for (int i = 0; i < list.size(); i++) {
                    final ExpectContractExportDTO queryVo = list.get(i);
                    Row row = sheet.createRow(i + 2);
                    queryVo.exportSetData(workbook, row, sheet);
                }
                workbook.write(out);
                workbook.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new MsgException(e.getMessage());
            }
    }
}



