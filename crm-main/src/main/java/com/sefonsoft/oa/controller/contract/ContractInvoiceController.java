package com.sefonsoft.oa.controller.contract;


import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractInvoiceListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractInvoiceByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractInvoiceInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractInvoiceInfoVO;
import com.sefonsoft.oa.service.contract.ContractInvoiceService;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_ADDINVOICE;
import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_EDITINVOICE;

/**
 * @author xielf
 */
@RestController
@RequestMapping("/contractInvoiceInfo")
@Api(tags = {"合同发票接口"})
public class ContractInvoiceController extends BaseController {

  private ContractInvoiceService invoiceService;

  @Autowired
  public void setInvoiceService(ContractInvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  /**
   * 添加发票
   *
   * @param addContractInvoiceInfo
   * @return
   */
  @MethodPermission(menuIdArray = CONTRACT_ADDINVOICE)
  @PostMapping
  @ApiOperation("添加发票")
  public Response addInvoice(@Valid @RequestBody AddContractInvoiceItemDTO addContractInvoiceInfo, BindingResult result) {

    try {
      if (result.hasErrors()) {
        return failure(result);
      }
      invoiceService.addContractInvoice(addContractInvoiceInfo, getLoginDTO());
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure();
    }
  }

  /**
   * 修改发票
   *
   * @param updateContractInvoiceInfo
   * @return
   */
  @MethodPermission(menuIdArray = CONTRACT_EDITINVOICE)
  @PutMapping
  @ApiOperation("修改发票")
  public Response updateInvoice(@Valid @RequestBody UpdateContractInvoiceInfoDTO updateContractInvoiceInfo) {
    try {
      invoiceService.updateContractInvoice(updateContractInvoiceInfo, getLoginDTO());
      return success();
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure();
    }
  }

  /**
   * 发票列表
   *
   * @param invoiceByContractIdDTO
   * @return
   */
  @ApiOperation("发票列表")
  @GetMapping
  public Response invoiceList(@Valid @ModelAttribute ListContractInvoiceByContractIdDTO invoiceByContractIdDTO) {

    try {
      final List<ContractInvoiceInfoVO> contractInvoiceInfoList = invoiceService.listContractInvoiceByContractId(invoiceByContractIdDTO);
      return success(contractInvoiceInfoList);
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure();
    }

  }

  /**
   * 删除发票
   * @param id
   * @return
   */
  @ApiOperation("删除发票")
  @DeleteMapping("/{id}")
  public Response delete(@PathVariable int id) {

    try {
      invoiceService.deleteContractInvoice(id);
      return success();
    }catch (Exception e) {
      e.printStackTrace();
      return failure();
    }

  }


}
