package com.sefonsoft.oa.controller.project;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectSimilarDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.service.project.ProjectInfoService;
import com.sefonsoft.oa.service.project.ProjectService;
import com.sefonsoft.oa.service.sysemployee.SysEmployeeService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.constant.ProjectConstant;
import com.sefonsoft.oa.system.emun.Dept;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.emun.MenuRole;
import com.sefonsoft.oa.system.emun.ResponseMessage;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * 项目列表页相关接口
 * @author Aron
 * @since 2019-12-03 16:13:01
 */
@Api(tags = "项目立项列表和跟踪相关接口")
@RestController
public class ProjectController extends BaseController {

    @Resource
    private ProjectService projectService;
    @Resource
    private SysEmployeeService sysEmployeeService;
    @Resource
    private ProjectInfoService projectInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping(value = "/testJemeter")
    public String findProjectByDataList() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello","world_"+ UUID.randomUUID().toString());
        String hello = ops.get("hello");
        System.out.println("hello:"+hello);
        return "success";
    }

    /**
     * 项目立项列表
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "(数据权限)条件查询项目立项列表+搜索",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/datalist", method = RequestMethod.POST)
    public Response findProjectByDataList(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) {


        // 未过期的数据
        projectInfoQueryDTO.setOverTimeFlag(ProjectConstant.OVER_TIME_FLAG_NO);
        projectInfoQueryDTO.setShareProjects(false);
        
        
        //  if (projectInfoQueryDTO.getScope() == ProjectInfoQueryDTO.SCOPE_ALL) {
        //    projectInfoQueryDTO.setIncludeMe(loginUserDTO.getUserId());
        //  } else if (projectInfoQueryDTO.getScope() == ProjectInfoQueryDTO.SCOPE_DEPT) {
        //    projectInfoQueryDTO.setIncludeMe(null);
        //  } else {
        //    if (Strings.isBlank(projectInfoQueryDTO.getEmployeeId())) {
        //      projectInfoQueryDTO.setEmployeeId(loginUserDTO.getUserId());
        //    }
        //  }
        
        // String grade = loginUserDTO.getGradeId();
        
        // 查大区，和查全部，如果前台没有传部门列表，那么拿用户本身的列表
        if (loginUserDTO.isLD()) {
          
          
          // 同时查自己
          if (projectInfoQueryDTO.getScope() == ProjectInfoQueryDTO.SCOPE_ALL) {
            projectInfoQueryDTO.setIncludeMe(loginUserDTO.getUserId());
          }
          
          List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();
          // 获取 dept list
          // 设置默认的 dep id
          if (CollectionUtils.isEmpty(dataDeptIdList)) {
            // 如果有对应的数据权限
            dataDeptIdList = loginUserDTO.getDataDeptIdList();
            
            // 检查部门完整性
            if (ObjTool.isNotNull(dataDeptIdList)) {
              dataDeptIdList = new ArrayList<>(dataDeptIdList);
              
              // 把自己的
              String mydpid = loginUserDTO.getDeptId();
              if (!dataDeptIdList.contains(mydpid)) {
                dataDeptIdList.add(mydpid);
              }
            }
          }
          
          // 把自己所在部门加进去
          if (CollectionUtils.isEmpty(dataDeptIdList)) {
            dataDeptIdList = Arrays.asList(loginUserDTO.getDeptId());
          } 
          
          projectInfoQueryDTO.setDataDeptIdList(dataDeptIdList);
        
          //数据权限部门列表
          dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();

          //(有数据数据权限,领导)
          // if (ObjTool.isNotNull(dataDeptIdList)) {
          //封装所以权限部门deptid作为in查询条件
          List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionList(dataDeptIdList, projectInfoQueryDTO);
          int totalProjectInfos = projectService.findConditionListCount(dataDeptIdList, projectInfoQueryDTO);
          PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
          return new Response().success(pageResponse);
        } else {
          
          // 只能查自己创建的
          projectInfoQueryDTO.setEmployeeId(loginUserDTO.getUserId());
          
          //(无数据权限,普通销售)
          List<ProjectInfoQueryDTO> projectInfos = projectService.getConditiont(projectInfoQueryDTO);
          int totalProjectInfos = projectService.findConditiont(projectInfoQueryDTO);
          PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
          return new Response().success(pageResponse);
        }
        

        




    }
    
    
    @ApiOperation(value = "检索资源池",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/overtimeDatalist", method = RequestMethod.POST)
    public Response findReleasedProject(@CurrentUser LoginUserDTO loginUserDTO, @RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) {

      // 查资源池标志
      projectInfoQueryDTO.setShareProjects(true);
      
      // 所有资源池
      // if (projectInfoQueryDTO.getOverTimeFlag() == null) {
      //   // 查所有
      //   projectInfoQueryDTO.setScope(ProjectInfoQueryDTO.SCOPE_ALL);
      // } else {
      //   // 查部门
      //   projectInfoQueryDTO.setScope(ProjectInfoQueryDTO.SCOPE_DEPT);
      // }
      // 检查合法性
      if (projectInfoQueryDTO.getOverTimeFlag() != null &&
          projectInfoQueryDTO.getOverTimeFlag() == ProjectConstant.OVER_TIME_FLAG_NO) {
        return new Response().failure("资源池中不包含未逾期的项目");
      }
      
      // 前端传入的列表
      List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();
      // 获取 dept list
      if (CollectionUtils.isEmpty(dataDeptIdList)) {
        // 如果有对应的数据权限
        dataDeptIdList = loginUserDTO.getDataDeptIdList();
        
        // 检查部门完备
        if (ObjTool.isNotNull(dataDeptIdList)) {
            dataDeptIdList = new ArrayList<>(dataDeptIdList);
            
            // 检查自己所在部门是否在
            // 部门列表中
            String mydpid = loginUserDTO.getDeptId();
            if (!dataDeptIdList.contains(mydpid)) {
              dataDeptIdList.add(mydpid);
            }
        }
        
      }
      
      if (CollectionUtils.isEmpty(dataDeptIdList)) {
        dataDeptIdList = Arrays.asList(loginUserDTO.getDeptId());
      }

      //封装所以权限部门deptid作为in查询条件
      List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionList(dataDeptIdList, projectInfoQueryDTO);
      int totalProjectInfos = projectService.findConditionListCount(dataDeptIdList, projectInfoQueryDTO);
      PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
      return new Response().success(pageResponse);
  
  }


    /**
     * 项目立项列表
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "(作废条)件查询项目立项列表",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/datalists", method = RequestMethod.POST)
    public Response findProjectByDataList1(@CurrentUser LoginUserDTO loginUserDTO,@RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) {

        if (!ObjTool.isNotNull(loginUserDTO)) {
            return failure(LOGIN_STATUS_ERROR);
        }
        String employeeId = projectInfoQueryDTO.getEmployeeId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工编号不能为空");
        }
        //菜单角色列表
        List<SysRole> menuRoleList = loginUserDTO.getMenuRoleList();
        if(!ObjTool.isNotNull(menuRoleList)){
            return failure("员工未配置操作权限");
        }
        //数据权限部门列表
        List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();
        //List<String> dataDeptIdList = projectInfoQueryDTO.getDataDeptIdList();

        if(ObjTool.isNotNull(menuRoleList)){
            SysRole role= menuRoleList.get(0);
            //(普通销售角色)
            if(role.getRoleName().equals(MenuRole.PTXS.getName())){
                List<ProjectInfoQueryDTO> projectInfos = projectService.getConditions(projectInfoQueryDTO);
                int totalProjectInfos = projectService.findConditions(projectInfoQueryDTO);
                PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
                return success(pageResponse);
            }
            //(大区领导)
            if(role.getRoleName().equals(MenuRole.DQLD.getName())){
                if(ObjTool.isNotNull(dataDeptIdList)){
                    //封装所以权限部门deptid作为in查询条件
                    List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionList(dataDeptIdList,projectInfoQueryDTO);
                    int totalProjectInfos = projectService.findConditionListCount(dataDeptIdList,projectInfoQueryDTO);
                    PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
                    return success(pageResponse);
                }
            }
            //(立项专员)
            if(role.getRoleName().equals(MenuRole.LXZY.getName())){
                if(ObjTool.isNotNull(dataDeptIdList)){
                    //封装所以权限部门deptid作为in查询条件
                    List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionList(dataDeptIdList,projectInfoQueryDTO);
                    int totalProjectInfos = projectService.findConditionListCount(dataDeptIdList,projectInfoQueryDTO);
                    PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
                    return success(pageResponse);
                }
            }
            //(销售管理部及以上领导)
            if(role.getRoleName().equals(MenuRole.LD.getName())){
                if(ObjTool.isNotNull(dataDeptIdList)){
                    //封装所以权限部门deptid作为in查询条件
                    List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionList(dataDeptIdList,projectInfoQueryDTO);
                    int totalProjectInfos = projectService.findConditionListCount(dataDeptIdList,projectInfoQueryDTO);
                    PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
                    return success(pageResponse);
                }
            }
        }

        return null;

    }






    /**
     * 项目立项列表
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "条件查询项目立项列表",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/list", method = RequestMethod.GET)
    public Response findProjectByCondition(ProjectInfoQueryDTO projectInfoQueryDTO) {

        String gradeId = projectInfoQueryDTO.getGradeId();
        if (!ObjTool.isNotNull(gradeId)) {
            return failure("职系编号不能为空");
        }
        String employeeId = projectInfoQueryDTO.getEmployeeId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工编号不能为空");
        }

        //1.普通销售
        if (gradeId.equals(Grade.XS.getCode())) {
            List<ProjectInfoQueryDTO> projectInfos = projectService.getConditions(projectInfoQueryDTO);
            int totalProjectInfos = projectService.findConditions(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
            return success(pageResponse);
        }
        SysEmployee sysEmployee = sysEmployeeService.queryByEmployeeId(employeeId);
        String deptIds = sysEmployee.getDeptId();//查出员工自己对应部门
        if (null == deptIds) {
            return failure("员工部门不能为空");
        }
        String deptId = projectInfoQueryDTO.getDeptId();//查出传来查询部门
        //查询部门是否是员工所属部门的子部门
        int res = projectService.contain(deptId, deptIds);
        //2.是其中某子部门，只显示子部门对应信息(不包括自己信息)
        if (res > 0) {
            List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionxx(projectInfoQueryDTO);
            if (!ObjTool.isNotNull(projectInfos)) {
                PageResponse pageResponse = new PageResponse<>(0, projectInfos);
                return success(pageResponse);
            }
            int totalProjectInfos = projectService.findConditionxx(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
            return success(pageResponse);
        }
        //3.是领导，是销售管理部门之下领导
        if (gradeId.equals(Grade.LD.getCode()) && !deptIds.equals(Dept.SF.getCode()) && !deptIds.equals(Dept.DS.getCode()) && !deptIds.equals(Dept.ZJ.getCode()) && !deptIds.equals(Dept.XS.getCode())) {
            projectInfoQueryDTO.setDeptId(deptIds);
            List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionx(projectInfoQueryDTO);
            if (!ObjTool.isNotNull(projectInfos)) {
                PageResponse pageResponse = new PageResponse<>(0, projectInfos);
                return success(pageResponse);
            }
            int totalProjectInfos = projectService.findConditionx(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
            return success(pageResponse);
        }
        //4.大领导
        List<ProjectInfoQueryDTO> projectInfos = projectService.getCondition(projectInfoQueryDTO);
        if (!ObjTool.isNotNull(projectInfos)) {
            PageResponse pageResponse = new PageResponse<>(0, projectInfos);
            return success(pageResponse);
        }
        int totalProjectInfos = projectService.findCondition(projectInfoQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);

        return success(pageResponse);

    }



    /**
     * 条件模糊搜索列表
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "模糊搜索项目列表",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/projectList", method = RequestMethod.GET)
    public Response findProjectByConditions(ProjectInfoQueryDTO projectInfoQueryDTO) {
        String gradeId = projectInfoQueryDTO.getGradeId();
        if (!ObjTool.isNotNull(gradeId)) {
            return failure("职系编号不能为空");
        }
        String employeeId = projectInfoQueryDTO.getEmployeeId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工编号不能为空");
        }
        //普通销售
        if(gradeId.equals(Grade.XS.getCode())){
            List<ProjectInfoQueryDTO> projectInfos = projectService.getConditions(projectInfoQueryDTO);
            if(!ObjTool.isNotNull(projectInfos)){
                PageResponse pageResponse = new PageResponse<>(0, projectInfos);
                return success(pageResponse);
            }

            int totalProjectInfos = projectService.findConditions(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
            return success(pageResponse);
        }

        SysEmployee sysEmployee = sysEmployeeService.queryByEmployeeId(employeeId);
        String deptId = sysEmployee.getDeptId();
        //是领导，是销售管理部门之下领导
        if(gradeId.equals(Grade.LD.getCode())&&!deptId.equals(Dept.SF.getCode())&&!deptId.equals(Dept.DS.getCode())&&!deptId.equals(Dept.ZJ.getCode())&&!deptId.equals(Dept.XS.getCode())){
            projectInfoQueryDTO.setDeptId(deptId);
            List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionx(projectInfoQueryDTO);
            if(!ObjTool.isNotNull(projectInfos)){
                PageResponse pageResponse = new PageResponse<>(0, projectInfos);
                return success(pageResponse);
            }
            int totalProjectInfos = projectService.findConditionx(projectInfoQueryDTO);
            PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
            return success(pageResponse);
        }
        //是大领导
        List<ProjectInfoQueryDTO> projectInfos = projectService.getConditionss(projectInfoQueryDTO);
        if(!ObjTool.isNotNull(projectInfos)){
            PageResponse pageResponse = new PageResponse<>(0, projectInfos);
            return success(pageResponse);
        }
        int totalProjectInfos = projectService.findConditionss(projectInfoQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalProjectInfos, projectInfos);
        return success(pageResponse);

    }


    /**
     * 根据id删除项目
     * @return
     */
    @MethodPermission(menuIdArray = C_PROJECT_DELETE)
    @ApiOperation("删除单个项目projectId")
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "projectId") @PathVariable String projectId) {

        return projectService.deleteById(projectId) ? success() : failure(DELETE_ERROR);
    }


    /**
     * 根据projectId查询项目信息
     * @param projectId
     * @return 单条数据
     */
    @ApiOperation(value = "根据projectId查询单个项目信息",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable String projectId) {
        if (ObjTool.isNotNull(projectId)) {
            ProjectInfoQueryDTO projectInfoQueryDTO = projectService.queryByProjectId(projectId);
            if (ObjTool.isNotNull(projectInfoQueryDTO)) {
                String projectName = projectInfoQueryDTO.getProjectName();
                String customerId = projectInfoQueryDTO.getCustomerId();
                List<ProductProjectRef> productProjectRefList = projectInfoQueryDTO.getProductProjectRefList();
                List<Integer> productIdList = productProjectRefList.stream().map(ProductProjectRef::getProductId).collect(Collectors.toList());
                List<ProjectSimilarDTO> similarProjectList = projectInfoService.getSimilarProjectList(projectId, projectName, false, customerId, productIdList);
                if (ObjTool.isNotNull(similarProjectList)) {
                    projectInfoQueryDTO.setSimilarProjectList(similarProjectList);
                }
                return success(projectInfoQueryDTO);
            }
            return failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "projectId"+NO_PARAM_ERROR.getMessage());
    }
    /**
     * 根据projectId查询项目基础信息
     * @return 单条数据
     */
    @ApiOperation(value = "批量查询项目基础信息",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/projects/batch", method = RequestMethod.GET)
    public Response batchGet(@RequestParam(name = "projectIds") List<String> projectIds) {
      
      
      
      List<ProjectInfoQueryDTO> projectInfoQueryDTO = projectService.queryByProjectId(projectIds);
      
      
      
      
      return success(projectInfoQueryDTO);
    }


    /**
     *  审批接口
     * @param loginUserDTO
     * @param projectInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "审批接口",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/check", method = RequestMethod.POST)
    public Response check(@CurrentUser LoginUserDTO loginUserDTO , @RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) {

        String employeeId =  loginUserDTO.getUserId();
        if (!ObjTool.isNotNull(employeeId)) {
            return failure("登录信息失效");
        }


        return projectService.check(loginUserDTO,projectInfoQueryDTO) > 0 ? success() : failure(UPDATE_ERROR);

    }
    /**
     *  审批接口
     * @param
     * @param projectInfoQueryDTO
     * @return Response
     */
    @MethodPermission(menuIdArray = {C_PROJECT_APPROVAL, C_TODO_PROJECTCHECK})
    @ApiOperation(value = "审批接口(新)",response = ProjectInfoQueryDTO.class)
    @RequestMapping(value = "/project/checkAll", method = RequestMethod.POST)
    public Response checkAll(@RequestBody ProjectInfoQueryDTO projectInfoQueryDTO) {

        String employeeId =  projectInfoQueryDTO.getEmployeeId();

        if (!ObjTool.isNotNull(employeeId)) {
            return failure("员工工号不能空");
        }
        if (!ObjTool.isNotNull(projectInfoQueryDTO.getProjectId())) {
            return failure("项目id不能为空");
        }
        if (!ObjTool.isNotNull(projectInfoQueryDTO.getCheckStatus())) {
            return failure("审批状态不能为空");
        }
        String opinion = projectInfoQueryDTO.getOpinion();
        if (opinion!=null&&opinion.length()>200) {
            return failure("审批意见不能超过200字符");
        }


        return projectService.checkAll(projectInfoQueryDTO) > 0 ? success() : failure(UPDATE_ERROR);

    }
    /**
     * 获取添加项目需要的客户项目跟进阶段下拉选项
     * @param employeeReportDTO
     * @return 单条数据
     */
    @ApiOperation(value = "客户项目跟进阶段列表")
    @RequestMapping(value = "/project/customerProjectPhasetList", method = RequestMethod.GET)
    public  Response getCustomerProjectPhaset(EmployeeReportDTO employeeReportDTO) {
        List<String> customerProjectPhasetList = projectService.getCustomerProjectPhaset();
        if (!ObjTool.isNotNull(customerProjectPhasetList)) {
            return failure("客户项目跟进阶段不能为空");
        }
        return success(customerProjectPhasetList);
    }

    /**
     * 查询项目跟踪列表
     * @param employeeReportDTO
     * @return Response
     */
    @ApiOperation(value = "详情页面/查询项目跟踪列表",response = EmployeeReportDTO.class)
    @RequestMapping(value = "/projectFollow", method = RequestMethod.GET)
    public Response projectFollow(EmployeeReportDTO employeeReportDTO) throws Exception {

        if (!ObjTool.isNotNull(employeeReportDTO.getProjectId())) {
            return failure("项目编号不能为空");
        }
//        if (!ObjTool.isNotNull(employeeReportDTO.getEmployeeId())) {
//            return failure("销售人员工号不能为空");
//        }

        List<EmployeeReportDTO> projectFollowList = projectService.projectFollow(employeeReportDTO);
        int projectFollowTotal = projectService.projectFollowTotal(employeeReportDTO);
        PageResponse pageResponse = new PageResponse<>(projectFollowTotal, projectFollowList);

        return new Response().success(pageResponse);



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


}