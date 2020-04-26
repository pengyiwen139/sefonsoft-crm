package com.sefonsoft.oa.controller.contract;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_ADD;
import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_DELETE;
import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_EDIT;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.common.AccessRule;
import com.sefonsoft.oa.domain.contract.vo.ContractDetailVO;
import com.sefonsoft.oa.domain.contract.vo.ContractExcelVO;
import com.sefonsoft.oa.domain.contract.vo.ContractInsertVO;
import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListQueryVO;
import com.sefonsoft.oa.domain.contract.vo.ContractListVO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayStageVO;
import com.sefonsoft.oa.domain.contract.vo.ContractProductVO;
import com.sefonsoft.oa.domain.contract.vo.ContractSaleInfoVO;
import com.sefonsoft.oa.domain.contract.vo.ContractUpdateVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ContractInfo;
import com.sefonsoft.oa.entity.contract.ContractPayStage;
import com.sefonsoft.oa.entity.contract.ContractProductInfo;
import com.sefonsoft.oa.entity.contract.ContractSalesInfo;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.contract.ContractExcelHeader;
import com.sefonsoft.oa.service.contract.ContractService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ExcelUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/contract")
@Api(tags = "合同相关接口")
public class ContractController extends BaseController {

  @Autowired
  ContractService contractService;
  
  /**
   * 新增合同
   */
  @MethodPermission(menuIdArray = CONTRACT_ADD)
  @ApiOperation("新增合同")
  @SuppressWarnings("unchecked")
  @PostMapping()
  public Response<String> create(
      @Valid @RequestBody ContractInsertVO vo) {
    
    Date now = new Date();
    
    LoginUserDTO cur = getLoginDTO();
    
    String userid = cur.getUserId();
    
    ContractInfo inf = vo.toContractInfo();
    
    // 填充合同信息
    // 创建人
    inf.setCreator(userid);
    inf.setCreateTime(now);
    
    // 操作员
    inf.setOperator(userid);
    inf.setLastTime(now);
    
    
    // 销售信息
    List<ContractSalesInfo> sales = vo.getSales().stream()
        .map(v -> v.toContractSalesInfo(inf))
        .collect(toList());
    
    // 产品信息
    List<ContractProductInfo> products = vo.getProducts().stream()
        .map(v -> v.toContractProductInfo(inf))
        .collect(toList());
    
    // 回款信息
    List<ContractPayStage> payStages = vo.getPayStages().stream()
        .map(v -> v.toContractPayStage(inf))
        .collect(toList());
    
    
    contractService.add(inf, sales, products, payStages);
    
    return success("操作成功");
  }
  
  /**
   * 更新合同信息
   */
  @MethodPermission(menuIdArray = CONTRACT_EDIT)
  @ApiOperation("更新合同信息")
  @SuppressWarnings("unchecked")
  @PutMapping("/{contractId}")
  public Response<String> update(
      @PathVariable(name = "contractId") String contractId,
      @Valid @RequestBody ContractUpdateVO vo) {
    
    ContractInfo inf = vo.toContractInfo();
    
    inf.setContractId(contractId);
    
    LoginUserDTO cur = getLoginDTO();
    
    String operator = cur.getUserId();
    
    inf.setLastTime(new Date());
    inf.setOperator(operator);
    
    
    // 销售信息
    List<ContractSalesInfo> sales = vo.getSales().stream()
        .map(v -> v.toContractSalesInfo(inf))
        .collect(toList());
    
    // 产品信息
    List<ContractProductInfo> products = vo.getProducts().stream()
        .map(v -> v.toContractProductInfo(inf))
        .collect(toList());
    
    // 回款信息
    List<ContractPayStage> payStages = vo.getPayStages().stream()
        .map(v -> v.toContractPayStage(inf))
        .collect(toList());
    
    contractService.update(inf, sales, products, payStages);
    
    return success("操作成功");
  }
  
  
  
  /**
   * 查询合同列表
   * @param query
   * @return
   */
  @ApiOperation("查询合同列表")
  @SuppressWarnings("unchecked")
  @PostMapping("search")
  public Response<PageableResult<ContractListVO>> getList(
      @Valid @RequestBody ContractListQueryVO query) {
    
    // 数据权限
    AccessRule accessRule = AccessRule.newRule()
        .withCurrentUser()
        .withDepts(query.getDataDeptIdList());
    
    query.setAccessRule(accessRule);
    
    PageableResult<ContractListVO> result = contractService.getList(query);
    
    return success(result);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同详细信息")
  @GetMapping("/{contractId}")
  public Response<ContractDetailVO> getContract(
      @PathVariable("contractId") String contractId) {
    
    // 详细信息
    ContractDetailVO vo = contractService.getDetail(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("unchecked")
  @MethodPermission(menuIdArray = CONTRACT_DELETE)
  @ApiOperation("删除合同")
  @DeleteMapping("/{contractId}")
  public Response<ContractDetailVO> deleteContract(
      @PathVariable("contractId") String contractId) {
    
    // 详细信息
    contractService.delete(contractId);
    
    return success("删除成功");
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同产品信息")
  @GetMapping("/{contractId}/productInfo")
  public Response<List<ContractProductVO>> getContractProductInfo(
      @PathVariable("contractId") String contractId) {
    
    List<ContractProductVO> vo = contractService.getProductInfo(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同收款比例信息")
  @GetMapping("/{contractId}/payStage")
  public Response<List<ContractPayStageVO>> getContractPayStage(
      @PathVariable("contractId") String contractId) {
    
    List<ContractPayStageVO> vo = contractService.getPayStage(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同发票信息")
  @GetMapping("/{contractId}/invoiceInfo")
  public Response<List<ContractInvoiceInfoVO>> getInvoiceInfo(
      @PathVariable("contractId") String contractId) {
    
    List<ContractInvoiceInfoVO> vo = contractService.getInvoiceInfo(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同回款信息")
  @GetMapping("/{contractId}/payInfo")
  public Response<List<ContractPayInfoVO>> getContractPayInfo(
      @PathVariable("contractId") String contractId) {
    
    List<ContractPayInfoVO> vo = contractService.getPayInfo(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("查询合同销售信息")
  @GetMapping("/{contractId}/salesInfo")
  public Response<List<ContractSaleInfoVO>> getContractSalesInfo(
      @PathVariable("contractId") String contractId) {
    
    List<ContractSaleInfoVO> vo = contractService.getSalesInfo(contractId);
    
    return success(vo);
  }
  
  @SuppressWarnings("rawtypes")
  @ApiOperation("导入合同信息")
  @OpLog(module = OpLog.Module.CONTRACT, opType = OpLog.Type.IMPORT)
  @GetMapping("/import")
  public Response importContractInfo(
      @RequestParam(name = "skipError", required = false, defaultValue = "false") boolean skipError,
      @RequestParam("file") MultipartFile file) throws IOException {
    
    
    InputStream is = file.getInputStream();
    Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
    
    Sheet sheet = workbook.getSheetAt(0);
    List<String> errors = new LinkedList<String>();
    
    List<ContractExcelVO> vo = ContractExcelHeader.read(sheet, errors);
    
    if (errors.isEmpty() || skipError) {
      contractService.doImport(vo);
      return success();
    } else {
      return failure("", errors);
    }
    
  }
  
  
  @ApiOperation(value = "导出合同信息")
  @GetMapping(value = "/export")
  @OpLog(module = OpLog.Module.CONTRACT, opType = OpLog.Type.EXPORT)
  public StreamingResponseBody exportContractInfo(
      @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
      @ApiParam(hidden = true) HttpServletResponse response,
      @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {

    
    
    AccessRule acess = AccessRule.newRule().withCurrentUser().withDepts(loginUserDTO.getDataDeptIdList());
    
    final List<ContractExcelVO> result = contractService.getExports(acess);
    
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/x-download");
    String fileName = "合同信息.xlsx";
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

    return out -> {

      try (Workbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
        Sheet sheet = workbook.createSheet("合同信息");
       
        ContractExcelHeader.write(sheet, result);
        
        workbook.write(out);
      } catch (Exception e) {
        e.printStackTrace();
        throw new MsgException(e.getMessage());
      }
    };
  }
  
  
  // getter and setter

  public ContractService getContractService() {
    return contractService;
  }

  public void setContractService(ContractService contractService) {
    this.contractService = contractService;
  }
  
  
  
}
