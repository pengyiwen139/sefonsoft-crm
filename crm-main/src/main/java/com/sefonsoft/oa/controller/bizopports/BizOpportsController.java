package com.sefonsoft.oa.controller.bizopports;

import com.sefonsoft.oa.dao.project.ProductProjectRefDao;
import com.sefonsoft.oa.dao.project.ProjectDao;
import com.sefonsoft.oa.dao.project.ProjectInfoDao;
import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.emun.Grade.JF;
import static com.sefonsoft.oa.system.emun.Grade.SQ;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * Created by liwenyi
 * Date: 2020/2/20
 * Desc: 商机接口
 */
@RestController
@Api(tags = "商机接口")
public class BizOpportsController{

    @Resource
    private BizOpportsService bizOpportsService;

    @Resource
    private ProjectDao projectDao;
    
    @Resource
    private ProductProjectRefDao productProjectRefDao;

    /**
     * 新增商机
     *
     * @param bizOpportsDTO
     * @param result
     * @return
     */
    @SuppressWarnings("rawtypes")
    @MethodPermission(menuIdArray = C_BIZ_OPPORTS_ADD)
    @ApiOperation(value = "新增商机", response = BizOpports.class)
    @PostMapping(value = "/addBizopports")
    public Response insert(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody BizOpportsDTO bizOpportsDTO, BindingResult result) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        if (result.hasErrors()) {
            return new Response<>().failure(result);
        }
        int res = bizOpportsService.insert(loginUserDTO, bizOpportsDTO);
        return res == 1 ? new Response<>().success() : new Response<>().failure(INSERT_ERROR);
    }

    /**
     * 修改商机
     *
     * @param bizOpportsDTO
     * @param result
     * @return
     */
    @MethodPermission(menuIdArray = {C_BIZ_OPPORTS_EDIT,C_TODO_BIZOPPORT,C_BIZASSIGN})
    @ApiOperation(value = "修改商机", response = BizOpports.class)
    @PostMapping(value = "/modifyBizopports")
    public Response update(@CurrentUser LoginUserDTO loginUserDTO,@Valid @RequestBody BizOpportsDTO bizOpportsDTO, BindingResult result) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        if (result.hasErrors()) {
            return new Response<>().failure(result);
        }
        int res = bizOpportsService.update(loginUserDTO,bizOpportsDTO);
        return res == 1 ? new Response<>().success() : new Response<>().failure(UPDATE_ERROR);
    }

    /**
     * 根据id查看商机详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查看商机详情", response = BizOpports.class)
    @GetMapping(value = "/bizopports/{id}")
    public Response selectOne(@CurrentUser LoginUserDTO loginUserDTO,@ApiParam(required = true, value = "商机表主键id") @PathVariable Long id) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        if (ObjTool.isNotNull(id)) {
            if (id < 1) {
                return new Response<>().failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            BizOpports bizOpport = bizOpportsService.queryById(id);
            List<CustomerInfoQueryDTO> customers = null;
            if(bizOpport != null ){
                customers  = bizOpportsService.getCustomerNameId(bizOpport.getCustomerId(),bizOpport.getEmployeeId());
            }
            ProjectInfoQueryDTO project = null;
            if (bizOpport != null) {
              project = projectDao.queryByBizId(bizOpport.getId());
              if (project != null) {
                List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(project.getProjectId());
                project.setProductProjectRefList(productProjectRefList);
              }
            }
            Map<String, Object> res = new HashMap<>();
            res.put("bizopport", bizOpport);
            res.put("customers", customers);
            res.put("project", project);
            return ObjTool.isNotNull(bizOpport) ? new Response<>().success(res) : new Response<>().failure(DELETEE_ERROR);
        }
        return new Response<>().failure(NO_PARAM_ERROR.getCode(), "id" + NO_PARAM_ERROR.getMessage());
    }

    /**
     * 删除商机信息
     *
     * @param id
     * @return
     */
    @MethodPermission(menuIdArray = C_BIZ_OPPORTS_DEL)
    @ApiOperation(value = "根据主键id删除商机", response = BizOpports.class)
    @PostMapping(value = "/deleteBizopports/{id}")
    public Response deleteOne(@ApiParam(required = true, value = "商机表主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 1) {
                return new Response<>().failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            int relatedTotal = bizOpportsService.relatedTotal(id);
            int workerCount = bizOpportsService.getBizopportsWorkerOrderCount(id);
            relatedTotal += workerCount;
            if (relatedTotal == 0){
                int res = bizOpportsService.deleteById(id);
                return res == 1 ? new Response<>().success(res) : new Response<>().failure(DELETE_ERROR);
            }else{
                return new Response<>().failure(DELETE_RELATED_ERROR);
            }

        }
        return new Response<>().failure(NO_PARAM_ERROR.getCode(), "id" + NO_PARAM_ERROR.getMessage());
    }

    /**
     * 商机管理主界面
     *
     * @param bizOpportsDTO
     * @return
     */
    @ApiOperation(value = "商机管理主界面", response = BizOpports.class)
    @PostMapping(value = "/listBizopports")
    public Response queryByKeywords(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody BizOpportsDTO bizOpportsDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new Response<>().failure(result);
        }
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        int totalBizOpportsList = 0;
        List<BizOpports> bizOpportsList;
        /**
         * 交付售前电销只能查看自己创建的商机
         */
        if(loginUserDTO.getGradeId().equalsIgnoreCase(SQ.getCode()) || loginUserDTO.getGradeId().equalsIgnoreCase(JF.getCode())){
            bizOpportsList = bizOpportsService.queryPreSaleAll(loginUserDTO,bizOpportsDTO);
            totalBizOpportsList = bizOpportsService.queryPreSaleAllTotal(loginUserDTO,bizOpportsDTO.getKeywords(),bizOpportsDTO.getName());
        }else{
            bizOpportsList = bizOpportsService.queryByKeyWords(loginUserDTO, bizOpportsDTO);
            totalBizOpportsList = bizOpportsService.queryByKeyWordsTotal(loginUserDTO, bizOpportsDTO);
        }

        PageResponse pageResponse = new PageResponse<>(totalBizOpportsList, bizOpportsList);
        return new Response<>().success(pageResponse);
    }

    /**
     * 获取与销售相关的客户
     *
     * @return
     */
    @ApiOperation(value = "获取与销售相关的客户", response = Map.class)
    @PostMapping(value = "/bizopports/customer")
    public Response queryCustomerByUser(@CurrentUser LoginUserDTO loginUserDTO) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<Map<String, Object>> customerList = bizOpportsService.queryCustomerByUser(loginUserDTO);
        return ObjTool.isNotNull(customerList) ? new Response<>().success(customerList) : new Response<>().failure(NO_RESPONSE_ERROR);
    }


    /**
     * 获取相关销售
     *
     * @param loginUserDTO
     * @return
     */
    @ApiOperation(value = "获取相关销售")
    @PostMapping("/bizOpports/sale")
    public Response getSale(@CurrentUser LoginUserDTO loginUserDTO) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<Map<String, Object>> saleList = bizOpportsService.querysaleByUser(loginUserDTO);
        return ObjTool.isNotNull(saleList) ? new Response<>().success(saleList) : new Response<>().failure(NO_DATA_PERMISSION_ERROR);
    }

    /**
     * 根据id列表查询商机信息
     *
     * @param loginUserDTO
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id列表查询商机信息")
    @PostMapping("/bizOpports/ids")
    public Response queryByIds(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody List<Long> ids) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (long id : ids) {
            BizOpports bizOpport = bizOpportsService.queryById(id);
            List<CustomerInfoQueryDTO> customers = bizOpportsService.getCustomerNameId(bizOpport.getCustomerId(),bizOpport.getEmployeeId());
            Map<String, Object> res = new HashMap<>();
            res.put("bizopport", bizOpport);
            res.put("customers", customers);
            result.add(res);
        }
        return ObjTool.isNotNull(result) ? new Response<>().success(result) : new Response<>().failure(NO_RESPONSE_ERROR);
    }

    /**
     * 获取公司所有销售
     *
     * @param loginUserDTO
     * @return
     */
    @ApiOperation(value = "获取公司所有销售")
    @PostMapping("/bizOpports/allSale")
    public Response allSale(@CurrentUser LoginUserDTO loginUserDTO) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<Map<String, Object>> result = bizOpportsService.queryAllSale();
        return ObjTool.isNotNull(result) ? new Response<>().success(result) : new Response<>().failure(NO_RESPONSE_ERROR);
    }

    /**
     * 指派商机-查询
     *只提供给销管使用
     * @param loginUserDTO
     * @return
     */
    @ApiOperation(value = "指派商机-查询")
    @PostMapping("/bizOpports/xgall")
    public Response queryAllBiz(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody BizOpportsDTO bizOpportsDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new Response<>().failure(result);
        }
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return new Response<>().failure(LOGIN_STATUS_ERROR);
        }
        List<String> ids = new ArrayList<>();
        ids.addAll(loginUserDTO.getDataDeptIdList());
        List<BizOpports> bizOpportsList = bizOpportsService.queryAllBiz(bizOpportsDTO,ids);
        int totaol = bizOpportsService.queryAllBizTotal(bizOpportsDTO,ids);
        PageResponse pageResponse = new PageResponse<>(totaol, bizOpportsList);
        return ObjTool.isNotNull(pageResponse) ? new Response<>().success(pageResponse) : new Response<>().failure(NO_RESPONSE_ERROR);
    }


}
