package com.sefonsoft.oa.controller.contract;


import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoItemDTO;
import com.sefonsoft.oa.domain.contract.dto.AddContractPayInfoListDTO;
import com.sefonsoft.oa.domain.contract.dto.ListContractPayByContractIdDTO;
import com.sefonsoft.oa.domain.contract.dto.UpdateContractPayInfoDTO;
import com.sefonsoft.oa.domain.contract.vo.ContractPayInfoVO;
import com.sefonsoft.oa.service.contract.ContractPayService;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_ADDPAYINFO;
import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.CONTRACT_EDITPAYINFO;

/**
 * @author xielf
 */
@RestController
@RequestMapping("/contractPayInfo")
@Api(tags = {"合同回款接口"})
public class ContractPayInfoController extends BaseController {


  private ContractPayService payService;

  @Autowired
  public void setPayService(ContractPayService payService) {
    this.payService = payService;
  }

  /**
   * 添加发票
   *
   * @param infoItem
   * @return
   */
  @MethodPermission(menuIdArray = CONTRACT_ADDPAYINFO)
  @PostMapping
  @ApiOperation("添加回款")
  public Response addInvoice(@RequestBody AddContractPayInfoItemDTO infoItem) {

    try {
      payService.addContractPayInfo(infoItem, getLoginDTO());
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
   * @param updateContractPayInfoDTO
   * @return
   */
  @MethodPermission(menuIdArray = CONTRACT_EDITPAYINFO)
  @PutMapping
  @ApiOperation("修改回款")
  public Response updateInvoice(@RequestBody UpdateContractPayInfoDTO updateContractPayInfoDTO) {
    try {
      payService.updateContractPayInfo(updateContractPayInfoDTO, getLoginDTO());
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
   * @param payByContractIdDTO
   * @return
   */
  @ApiOperation("回款列表")
  @GetMapping
  public Response invoiceList(@ModelAttribute ListContractPayByContractIdDTO payByContractIdDTO) {

    try {
      final List<ContractPayInfoVO> contractPayInfoList = payService.listContractPayByContractId(payByContractIdDTO);
      return success(contractPayInfoList);
    } catch (MsgException e) {
      return failure(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return failure();
    }

  }

  /**
   * 删除回款
   * @param id
   * @return
   */
  @ApiOperation("删除回款")
  @DeleteMapping("/{id}")
  public Response delete(@PathVariable int id) {
    try {
      payService.deleteContractPayInfo(id);
      return success();
    }catch (Exception e) {
      e.printStackTrace();
      return failure();
    }
  }

}
