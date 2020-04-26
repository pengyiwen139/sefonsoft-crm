package com.sefonsoft.oa.controller.project;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.constant.ProjectConstant.CHECK_SIMILAR_PROJECT_FLAG;
import static com.sefonsoft.oa.system.constant.ProjectConstant.HAS_NO_SIMILAR_TYPE;
import static com.sefonsoft.oa.system.constant.ProjectConstant.HAS_SIMILAR_TYPE;
import static com.sefonsoft.oa.system.emun.ResponseMessage.INSERT_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.LOGIN_STATUS_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.QUERY_ERROR;
import static com.sefonsoft.oa.system.emun.ResponseMessage.UPDATE_ERROR;
import static com.sefonsoft.oa.system.utils.ExcelUtils.customerTitleList;
import static com.sefonsoft.oa.system.utils.UtilKhMethod.PROJECT_PREFIX;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.dao.project.ProjectCheckInfoDao;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.project.ProductProjectRefInsertDTO;
import com.sefonsoft.oa.domain.project.ProductProjectRefUpdateDTO;
import com.sefonsoft.oa.domain.project.ProjectAmountDevideRefInsertDTO;
import com.sefonsoft.oa.domain.project.ProjectAmountDevideRefUpdateDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoGetOneDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoInsertDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoUpdateDTO;
import com.sefonsoft.oa.domain.project.ProjectNeedParamsDTO;
import com.sefonsoft.oa.domain.project.ProjectOverdueInfoDTO;
import com.sefonsoft.oa.domain.project.ProjectSaleRefUpdateDTO;
import com.sefonsoft.oa.domain.project.ProjectSimilarDTO;
import com.sefonsoft.oa.domain.project.SalesProjectStageDTO;
import com.sefonsoft.oa.domain.project.vo.FollowAction;
import com.sefonsoft.oa.domain.project.vo.PreCheckProjectVO;
import com.sefonsoft.oa.domain.project.vo.ProjectExcelVO;
import com.sefonsoft.oa.domain.project.vo.SalesUpdateVO;
import com.sefonsoft.oa.domain.project.vo.SimilarResult;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.product.ProductInfo;
import com.sefonsoft.oa.entity.project.ProjectInfo;
import com.sefonsoft.oa.entity.project.ProjectSaleRef;
import com.sefonsoft.oa.service.project.ProjectInfoService;
import com.sefonsoft.oa.service.sys.SysEnvService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.constant.ProjectConstant;
import com.sefonsoft.oa.system.emun.CheckStatus;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.emun.ResponseMessage;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ExcelUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.StringUtils;
import com.sefonsoft.oa.system.utils.UtilKhMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 项目相关接口
 * @author PengYiWen
 * @since 2019-11-14 10:47:01
 */
@Api(tags = "项目相关接口")
@RestController
public class ProjectInfoController extends BaseController {

    @Resource
    private ProjectInfoService projectInfoService;
    @Resource
    private ProjectCheckInfoDao projectCheckInfoDao;
    @Resource
    SysEnvService sysEnv;
    
    @ApiOperation(value = "预检查立项信息，排查项目是否重复")
    @RequestMapping(value = "/projectPreCheck", method = RequestMethod.POST)
    public Response checkSimilar(@RequestBody @Valid PreCheckProjectVO proInfo) {
      
      List<Integer> prds = proInfo.getProductRefInsertDTOList().stream().map(ProductProjectRefInsertDTO::getProductId)
        .collect(Collectors.toList());
      
      SimilarResult result = new SimilarResult();
//      int cr = 0;
//      List<ProjectSimilarDTO> projects = projectInfoService.getSimilarProjectList(
//          proInfo.getProjectId(),
//          proInfo.getProjectName(),
//          true,
//          proInfo.getCustomerId(), 
//          prds);
//      if (ObjTool.isNotNull(projects)) {
//        cr = SimilarResult.DUMPLICATE;
//      } else {
//        // 检查疑似项目
//        projects = projectInfoService.getSimilarProjectList(
//            proInfo.getProjectId(),
//            proInfo.getProjectName(),
//            false,
//            proInfo.getCustomerId(), 
//            prds);
//        
//        if (ObjTool.isNotNull(projects)) {
//          cr = SimilarResult.SIMILAR;
//        } else {
//          projects = Collections.emptyList();
//        }
//      }
      int cr =  SimilarResult.NONE;
      
      List<ProjectSimilarDTO> projects = projectInfoService.getSimilarProjectList(
        proInfo.getProjectId(),
        proInfo.getProjectName(),
        false,
        proInfo.getCustomerId(), 
        prds);
    
      if (ObjTool.isNotNull(projects)) {
        cr = SimilarResult.SIMILAR;
      }
      
      // set up bean
      result.setCheckResult(cr);
      result.setProjects(projects);
      return success(result);
    }
    
    @Override
    public Response success() {
      return new Response<>().success();
    }
    
    @Override
    public Response failure() {
      return new Response<>().failure();
    }
    
    @Override
    public Response failure(String message) {
    return new Response().failure(message);
    }
    
    @Override
    public Response failure(String message, Object data) {
    return new Response<>().failure(message, data);
    }
    
    @Override
    public Response failure(ResponseMessage responseMessage) {
    return new Response<>().failure(responseMessage);
    }

    @Override
    public Response failure(int code, String message) {
    return new Response<>().failure(code, message);
    }
    
    @Override
    public Response failure(BindingResult result) {
    return new Response<>().failure(result);
    }
    
    @Override
    public Response success(Object data) {
    return new Response().success(data);
    }
    
    /**
     * 新增项目
     *
     * @return 单条数据
     */
    @MethodPermission(menuIdArray = C_PROJECT_ADD)
    @ApiOperation(value = "新增项目")
    @RequestMapping(value = "/project", method = RequestMethod.PUT)
    public Response insert(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody @Valid ProjectInfoInsertDTO projectInfoInsertDTO, BindingResult bindingResult, HttpSession session) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        if (bindingResult.hasErrors()) {
            return failure(bindingResult);
        }
        List<ProjectAmountDevideRefInsertDTO> projectAmountDevideRefInsertDTOList = projectInfoInsertDTO.getProjectAmountDevideRefInsertDTOList();
        Integer sumPercent = 0;
        Integer allPercent = 100;
        for (ProjectAmountDevideRefInsertDTO projectAmountDevideRefInsertDTO : projectAmountDevideRefInsertDTOList) {
            sumPercent += projectAmountDevideRefInsertDTO.getPerformanceDivision();
        }
        if (!sumPercent.equals(allPercent)) {
            return failure("业绩分割百分比之和必须为100");
        }

        String maxProjectId = projectInfoService.getMaxProjectId();
        String projectId = UtilKhMethod.getProjectFlowCode(maxProjectId, PROJECT_PREFIX);
        projectInfoInsertDTO.setProjectId(projectId);
//        int countSameProjectName = projectInfoService.countByProjectName(projectInfoInsertDTO.getProjectName());
//        if (countSameProjectName > 0) {
//            return failure("该项目信息已被注册不予立项");
//        }
        ProjectInfoGetOneDTO projectInfoGetOneDTO = projectInfoService.get(projectId);
        if (ObjTool.isNotNull(projectInfoGetOneDTO)) {
            return failure("项目编号生成有重复，请重新提交");
        }

        //设置最终客户和合作伙伴的信息
//        projectInfoInsertDTO = projectInfoService.setCustomerPartnerInfo(projectInfoInsertDTO);

        //判断是否有重复的产品编号,给前端校验重复产品
        List<ProductProjectRefInsertDTO> productRefInsertDTOList = projectInfoInsertDTO.getProductRefInsertDTOList();
        List<Integer> productIdList = productRefInsertDTOList.stream().map(ProductProjectRefInsertDTO::getProductId).collect(Collectors.toList());
        if (ObjTool.isNotNull(productIdList) && productIdList.size()>1) {
            HashSet<Integer> productIdSet = new HashSet<>(productIdList);
            if (ObjTool.isNotNull(productIdSet) && productIdSet.size() < productIdList.size()) {
                return failure("请勿添加相同的产品信息");
            }
        }

        Integer checkSimilarFlag = projectInfoInsertDTO.getCheckSimilarFlag();
        //判断是否需要校验疑似项目
        if (ObjTool.isNotNull(checkSimilarFlag) && checkSimilarFlag.equals(CHECK_SIMILAR_PROJECT_FLAG)) {
            //校验疑似项目(项目名称相似，项目最终客户名称相似，项目相关产品相同)
            String projectName = projectInfoInsertDTO.getProjectName();
            String customerId = projectInfoInsertDTO.getCustomerId();
            //查询有疑似重复项目
            List<ProjectSimilarDTO> similarProjectList = projectInfoService.getSimilarProjectList(null, projectName, false, customerId, productIdList);
            
            projectInfoInsertDTO.setHasSimilarType(ObjTool.isNotNull(similarProjectList) ? HAS_SIMILAR_TYPE : HAS_NO_SIMILAR_TYPE);
            
            // if (ObjTool.isNotNull(similarProjectList)) {
            //     session.setAttribute("HAS_SIMILAR_TYPE",HAS_SIMILAR_TYPE);
            //     return failure(SIMILAR_PROJECT_WARN);
            // }
        }

        //若有重复项目的标识，则将其设置进更新项目的实体类中
        // Object similarTypeObj = session.getAttribute("HAS_SIMILAR_TYPE");
        // if (ObjTool.isNotNull(similarTypeObj) && ((Integer) similarTypeObj).equals(HAS_SIMILAR_TYPE)) {
        //    projectInfoInsertDTO.setHasSimilarType(HAS_SIMILAR_TYPE);
        // }

        return projectInfoService.insert(projectInfoInsertDTO,loginUserDTO) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 查询单个项目
     *
     * @return 单条数据
     */
    @ApiOperation(value = "查询单个项目")
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public Response get(@PathVariable String projectId) {
        if (!ObjTool.isNotNull(projectId)) {
            return failure("未获取到项目参数");
        }
        ProjectInfoGetOneDTO projectInfoGetOneDTO = projectInfoService.get(projectId);
        return ObjTool.isNotNull(projectInfoGetOneDTO) ? success(projectInfoGetOneDTO) : failure(QUERY_ERROR);
    }

    @MethodPermission(menuIdArray = C_PROJECT_FRESH)
    @ApiOperation(value = "销售/大区总更新项目")
    @RequestMapping(value = "/project/salesUpdate", method = RequestMethod.POST)
    public Response update(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody @Valid SalesUpdateVO updateVo) {
      
      
      List<ProductProjectRefUpdateDTO> productRefInsertDTOList = updateVo.getProductRefInsertDTOList();
      if (!ObjTool.isNotNull(productRefInsertDTOList)) {
          return failure("请填写产品信息");
      }

      for (ProductProjectRefUpdateDTO productInfo : productRefInsertDTOList) {
          Integer productId = productInfo.getProductId();
          BigDecimal amount = productInfo.getAmount();
          Integer saleCount = productInfo.getSaleCount();
          if (!ObjTool.isNotNull(productId) || !ObjTool.isNotNull(amount) || !ObjTool.isNotNull(saleCount)) {
              return failure("请填写产品信息");
          }
      }
      
        if (projectInfoService.salesUpdate(updateVo, loginUserDTO.getUserId())) {
          return success();
        } else {
          return failure();
        }
    }

    /**
     * 修改项目
     *
     * @return 单条数据
     */
    @MethodPermission(menuIdArray = C_PROJECT_UPDATE)
    @ApiOperation(value = "修改项目")
    @RequestMapping(value = "/project", method = RequestMethod.PATCH)
    public Response update(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody @Valid ProjectInfoUpdateDTO projectInfoUpdateDTO, BindingResult bindingResult, HttpSession session) {
        if (!ObjTool.isNotNull(loginUserDTO)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        if (bindingResult.hasErrors()) {
            return failure(bindingResult);
        }

        List<ProductProjectRefUpdateDTO> productRefInsertDTOList = projectInfoUpdateDTO.getProductRefInsertDTOList();
        if (!ObjTool.isNotNull(productRefInsertDTOList)) {
            return failure("请填写产品信息");
        }

        for (ProductProjectRefUpdateDTO productInfo : productRefInsertDTOList) {
            Integer productId = productInfo.getProductId();
            BigDecimal amount = productInfo.getAmount();
            Integer saleCount = productInfo.getSaleCount();
            if (!ObjTool.isNotNull(productId) || !ObjTool.isNotNull(amount) || !ObjTool.isNotNull(saleCount)) {
                return failure("请填写产品信息");
            }
        }

        String projectId = projectInfoUpdateDTO.getProjectId();
//        Integer statusByProjectId = projectCheckInfoDao.getStatusByProjectId(projectId);
//        if (ObjTool.isNotNull(statusByProjectId) && statusByProjectId.equals(PROJECT_AGREE_CHECK_STATUS)) {
//            return failure("当前项目已经审批通过，禁止进行修改");
//        }

        List<ProjectAmountDevideRefUpdateDTO> projectAmountDevideRefUpdateDTOList = projectInfoUpdateDTO.getProjectAmountDevideRefInsertDTOList();
        Integer allPercent = 100;
        Integer sumPercent = 0;
        for (ProjectAmountDevideRefUpdateDTO projectAmountDevideRefUpdateDTO : projectAmountDevideRefUpdateDTOList) {
            sumPercent += projectAmountDevideRefUpdateDTO.getPerformanceDivision();
        }
        if (!sumPercent.equals(allPercent)) {
            return failure("业绩分割百分比之和必须为100");
        }
        int countSameProjectName = projectInfoService.countByProjectName(projectInfoUpdateDTO.getProjectName());
        if (countSameProjectName > 1) {
            return failure("此项目名称已存在，请修改项目名称");
        }

        Integer checkSimilarFlag = projectInfoUpdateDTO.getCheckSimilarFlag();

        //判断是否有重复的产品编号,给前端校验重复产品
        List<ProductProjectRefUpdateDTO> productRefUpdateDTOList = projectInfoUpdateDTO.getProductRefInsertDTOList();
        List<Integer> productIdList = productRefUpdateDTOList.stream().map(ProductProjectRefUpdateDTO::getProductId).collect(Collectors.toList());
        if (ObjTool.isNotNull(productIdList) && productIdList.size()>1) {
            HashSet<Integer> productIdSet = new HashSet<>(productIdList);
            if (ObjTool.isNotNull(productIdSet) && productIdSet.size() < productIdList.size()) {
                return failure("请勿添加相同的产品信息");
            }
        }

        if (ObjTool.isNotNull(checkSimilarFlag) && checkSimilarFlag.equals(CHECK_SIMILAR_PROJECT_FLAG)) {
            //校验疑似项目(项目名称相似，项目最终客户名称相似，项目相关产品相同)
            String projectName = projectInfoUpdateDTO.getProjectName();
            String customerId = projectInfoUpdateDTO.getCustomerId();
            //查询有疑似重复项目
            List<ProjectSimilarDTO> similarProjectList = projectInfoService.getSimilarProjectList(projectId, projectName, false, customerId, productIdList);
            // if (ObjTool.isNotNull(similarProjectList)) {
            //      session.setAttribute("HAS_SIMILAR_TYPE",HAS_SIMILAR_TYPE);
            //      return failure(SIMILAR_PROJECT_WARN);
            //  }
            projectInfoUpdateDTO.setHasSimilarType(ObjectUtils.isEmpty(similarProjectList) ? HAS_NO_SIMILAR_TYPE : HAS_SIMILAR_TYPE);
        }

        //若有重复项目的标识，则将其设置进更新项目的实体类中
        // Object similarTypeObj = session.getAttribute("HAS_SIMILAR_TYPE");
        // if (ObjTool.isNotNull(similarTypeObj) && ((Integer) similarTypeObj).equals(HAS_SIMILAR_TYPE)) {
        //    projectInfoUpdateDTO.setHasSimilarType(HAS_SIMILAR_TYPE);
        // }

        return projectInfoService.update(projectInfoUpdateDTO,loginUserDTO) ? success() : failure(UPDATE_ERROR);
    }


    /**
     * 获取添加项目需要的【销售项目阶段】下拉选项
     * @return 单条数据
     */
    @ApiOperation(value = "获取添加项目需要的下拉选项", response = ProjectNeedParamsDTO.class)
    @RequestMapping(value = "/project/salesProjectStageList", method = RequestMethod.GET)
    public Response getSalesProjectStageList(SalesProjectStageDTO salesProjectStageDTO, ProductInfo productInfo) {
        List<SalesProjectStageDTO> spstagePairs = projectInfoService.getSalesProjectStageList(salesProjectStageDTO);
        List<ProductInfo> productInfoList = projectInfoService.getProductInfoList(productInfo);
        return ObjTool.isNotNull(spstagePairs,productInfoList) ? success(new ProjectNeedParamsDTO(spstagePairs, productInfoList)) : failure(QUERY_ERROR);
    }

    /**
     * 获取某个员工的客户，若传了customerId，则会排除此客户
     */
    @ApiOperation(value = "获取某个员工的客户，若传了customerId，则会排除此客户", response = CustomerInfo.class)
    @RequestMapping(value = {"/project/getPartnersExludeByCustomerId/{employeeId}/{customerId}","/project/getPartnersExludeByCustomerId/{employeeId}"}, method = RequestMethod.GET)
    public Response getPartnersExludeByCustomerId(@ApiParam(required = false, name = "需要排除的客户编号") @PathVariable(required = false) String customerId,
                                                  @ApiParam(required = true, name = "员工编号") @PathVariable String employeeId) {
        List<CustomerInfo> customerInfoList = projectInfoService.getPartnersExludeByCustomerId(customerId,employeeId);
        return success(customerInfoList);
    }

    /**
     * 项目联系人和共有人修改（只会向project_sale_ref表中修改数据）
     * @param projectInfoUpdateDTO
     * @return 单条数据
     */
    @MethodPermission(menuIdArray = C_PROJECT_CHANGECHARGER)
    @ApiOperation(value = "项目联系人和共有人修改（只会向project_sale_ref表中修改数据）")
    @RequestMapping(value = "/project/batchpUdatePerson", method = RequestMethod.PATCH)
    public Response batchpUdatePerson(@CurrentUser LoginUserDTO loginUserDTO, @Valid @RequestBody ProjectSaleRefUpdateDTO projectInfoUpdateDTO, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        String employeeId = projectInfoUpdateDTO.getEmployeeId();
        List<String> projectIdList = projectInfoUpdateDTO.getProjectIdList();
        if (!ObjTool.isNotNull(projectIdList)) {
            return failure("项目编号不能为空");
        }
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("负责人编号不能为空");
        }
        return projectInfoService.batchpUdatePerson(projectInfoUpdateDTO,loginUserDTO.getUserId()) ? success() : failure(UPDATE_ERROR);
    }

    /**
     *销售查询本人项目六个月过期列表
     * @param employeeId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询本人即将逾期项目列表" ,response = ProjectInfo.class)
    @RequestMapping(value = "/project/overdueList", method = RequestMethod.GET)
    public Response overdueList(@CurrentUser LoginUserDTO loginUserDTO) throws Exception {
        String employeeId = loginUserDTO.getUserId();
        
        LocalDateTime max = LocalDateTime.now()
            .minusMonths(sysEnv.project_gc_sales_limit_month.getValue())
            .plusDays(sysEnv.project_gc_sales_warning_day.getValue());
        
        ProjectOverdueInfoDTO projectOverdueInfoDTO =new ProjectOverdueInfoDTO();
        projectOverdueInfoDTO.setEmployeeId(employeeId);
        // 未过期的项目
        projectOverdueInfoDTO.setOverTimeFlag(ProjectConstant.OVER_TIME_FLAG_NO);
        // 审核时间
        projectOverdueInfoDTO.setProjectCreateTime(DateUtils.toDate(max));
        // 审批状态
        projectOverdueInfoDTO.setCheckStatuses(new Integer[] {CheckStatus.YTG.getCode()});
        
        
        int projectOverdueCount =projectInfoService.projectOverdueCount(projectOverdueInfoDTO);
        List<ProjectInfoQueryDTO> projectOverdueList = null;
        if (projectOverdueCount > 0) {
            projectOverdueList = projectInfoService.projectOverdueList(projectOverdueInfoDTO);
        }
        PageResponse pageResponse = new PageResponse(projectOverdueCount,projectOverdueList);
        return success(pageResponse);
    }

    @ApiOperation(value = "跟进项目" )
    @RequestMapping(value = "/project/overdueUpdate", method = RequestMethod.POST)
    public Response overdueUpdate(@CurrentUser LoginUserDTO loginUserDTO, 
        @Valid @RequestBody FollowAction act) {
      
        String empId = act.getEmployeeId() == null ? loginUserDTO.getUserId() : act.getEmployeeId();
        
        if (act.getFollow().isYes()) {
          
          return projectInfoService.assignProject(empId, act.getProjectIds(), ProjectConstant.ASSGIN_PROJECT_KEEP) ? success() : failure(UPDATE_ERROR);
        } else {
          return projectInfoService.unassignProject(act.getProjectIds()) ? success() : failure(UPDATE_ERROR);
        }
    }
    
    @MethodPermission(menuIdArray = {PROJECT_ASSIGN, PROJECT_ASSIGNME, C_PROJECT_RELEASE})
    @ApiOperation(value = "认领/指派资源池中的项目" )
    @PostMapping(value = "/project/share/assign")
    public Response assgin(@CurrentUser LoginUserDTO loginUserDTO, 
        @Valid @RequestBody FollowAction act) {
      
        String empId = act.getEmployeeId() == null ? loginUserDTO.getUserId() : act.getEmployeeId();
        
        if (act.getFollow().isYes()) {
          return projectInfoService.assignProject(empId, act.getProjectIds(), ProjectConstant.ASSGIN_PROJECT_FOLLOW) ? success() : failure(UPDATE_ERROR);
        } else {
          return projectInfoService.unassignProject(act.getProjectIds()) ? success() : failure(UPDATE_ERROR);
        }
    }

    /**
     * 离职人员未过期项目立项列表
     * @param
     * @return Response
     */
    @ApiOperation(value = "(数据权限)离职人员未过期项目立项列表",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/terminatedNotOverdueList", method = RequestMethod.GET)
    public Response terminatedNotOverdueList () throws ParseException {
        //数据权限部门列表
//        List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();
//        if (ObjTool.isNull(dataDeptIdList)) {
//            return failure("数据权限部门不能为空");
//        }
      
        ProjectInfoQueryDTO projectInfoQueryDTO= new ProjectInfoQueryDTO ();
        //6个月
        Date projectCreateTime= DateUtils.addMouth(DateUtils.currentDate(),-6);
        projectInfoQueryDTO.setProjectCreateTime(projectCreateTime);
        //员工离职
        projectInfoQueryDTO.setJobStatus(2);
        //封装所以权限部门deptid作为in查询条件
        int totalProjectInfoCount = projectInfoService.terminatedNotOverdueCount(projectInfoQueryDTO);
        if(totalProjectInfoCount>0){
            List<ProjectInfoQueryDTO> projectInfos = projectInfoService.terminatedNotOverdueList(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfoCount, projectInfos);
            return success(pageResponse);
        }
        return success();
    }

    /**
     * 离职人员未过期项目立项修改负责人
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "离职人员未过期项目立项修改负责人")
    @RequestMapping(value = "/project/updateProjectMiner", method = RequestMethod.PATCH)
    public Response updateProjectMiner(@CurrentUser LoginUserDTO loginUserDTO,@RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) throws ParseException {
        //数据权限部门列表
        List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();
//        if (ObjTool.isNull(dataDeptIdList)) {
//            return failure("数据权限部门不能为空");
//        }
        String employeeId=projectInfoQueryDTO.getEmployeeId();
        if (StringUtils.isEmpty(employeeId)) {
            return failure("负责人工号编号不能为空");
        }
        String projectId=projectInfoQueryDTO.getProjectId();
        if (StringUtils.isEmpty(projectId)) {
            return failure("项目编号不能为空");
        }
        String userId=loginUserDTO.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return failure("当前登录人不能为空");
        }
        boolean updateMiner =  projectInfoService.updateProjectMiner(new ProjectSaleRef(null, projectId, employeeId, null, userId, new Date(), null));
        return success(updateMiner);
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "回收逾期项目")
    @RequestMapping(value = "/gcProject", method = RequestMethod.POST)
    public Response<List<Map<String, String>>> gcProject(
        @ApiParam("yyyy-MM-dd HH:mm:ss") @RequestParam(name = "datetime", required = false) String date) {
      
      LocalDateTime dt = date == null ? LocalDateTime.now() : LocalDateTime.from(DateUtils.DEFAULT_DTF.parse(date));
      
      List<Map<String, String>> result = projectInfoService.gcOverdueProject(dt);
      return success(result);
    }



    @MethodPermission(menuIdArray = C_PROJECT_IMPORT)
    @ApiOperation(value = "导入项目")
    @PostMapping(value = "/project/import")
    @OpLog(module = OpLog.Module.PROJECT, opType = OpLog.Type.IMPORT)
    public Response importCustomersAndContacts(
        @RequestParam(name = "skipError", required = false, defaultValue = "false") boolean skipError,
        @RequestParam("file") MultipartFile file,
                                               @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {
        InputStream is = file.getInputStream();
        Workbook workbook = ExcelUtils.createWorkBook(is, file.getOriginalFilename());
        List<String> stringList = projectInfoService.validateExcel(workbook);
        if (stringList != null && !stringList.isEmpty()) {
          
          if (skipError) {
            projectInfoService.importProjects(workbook, loginUserDTO);
          }
          
          return warning("数据验证失败", stringList);
        } else {
          projectInfoService.importProjects(workbook, loginUserDTO);
          return success();
        }
    }

    @MethodPermission(menuIdArray = C_PROJECT_EXPORT)
    @ApiOperation(value = "导出项目")
    @GetMapping(value = "/project/export")
    @OpLog(module = OpLog.Module.PROJECT, opType = OpLog.Type.EXPORT)
    public StreamingResponseBody export(
        @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
        @ApiParam(hidden = true) HttpServletResponse response,
        @ApiParam(hidden = true) @CurrentUser LoginUserDTO loginUserDTO) throws IOException {

      //不是领导
      if (!loginUserDTO.getGradeId().equals(Grade.LD.getCode())) {
        loginUserDTO.setDataDeptIdList(null);
      }

      List<String> depts = Optional.ofNullable(loginUserDTO.getDataDeptIdList()).orElse(Collections.emptyList());
      
      final List<ProjectExcelVO> exportCustomerInfoQueryVos = projectInfoService.exporProjects(ImmutableSet.copyOf(depts), loginUserDTO.getUserId());

      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/x-download");
      String fileName = "项目信息.xlsx";
      fileName = URLEncoder.encode(fileName, "UTF-8");
      response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

      return out -> {

        try (Workbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
          Sheet sheet = workbook.createSheet("项目信息");
         
          ProjectExcelVO.applyHeader(sheet);

          for (int i = 0; i < exportCustomerInfoQueryVos.size(); i++) {

            final ProjectExcelVO queryVo = exportCustomerInfoQueryVos.get(i);
            Row row = sheet.createRow(i + 2);

            // GOGOOGGO
            queryVo.apply(workbook, row);
          }
          workbook.write(out);
        } catch (Exception e) {
          e.printStackTrace();
          throw new MsgException(e.getMessage());
        }
      };
    }
    
    
    
    
//    @Override
    public Response warning(String message) {
      return new Response().warning(message);
    }
    
    @Override
    public Response warning(String message, Object data) {
      return new Response().warning(message, data);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}