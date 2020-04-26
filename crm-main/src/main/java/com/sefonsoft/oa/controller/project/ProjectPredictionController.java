package com.sefonsoft.oa.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.common.AccessRule;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionStatsVO;
import com.sefonsoft.oa.domain.project.vo.ProjectQueryListVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.project.ProjectService;
import com.sefonsoft.oa.system.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 项目趋势
 */
@Api(tags = "项目立项列表和跟踪相关接口")
@RequestMapping("/project/prediction")
@RestController
public class ProjectPredictionController extends BaseController {
  
  
  @Autowired
  ProjectService projectService;
  
  
  @SuppressWarnings("unchecked")
  @ApiOperation("获取趋势列表")
  @PostMapping()
  public Response<PageableResult<ProjectPredictionListVO>> list(
      @RequestBody ProjectQueryListVO vo) {
    
    if (vo == null) {
      vo = new ProjectQueryListVO();
    }
    
    // 数据权限
    AccessRule rule = AccessRule.newRule();

    // 查部门
    rule.withDepts(vo.getDataDeptIdList());
    
    // 包含自己
    if (vo.getScope() == ProjectQueryListVO.SCOPE_ALL) {
      rule.withCurrentUser();
    }
    
    vo.setAccessRule(rule);
    
    PageableResult<ProjectPredictionListVO> data = projectService.queryPredictionList(vo);
    return success(data);
  }
  
  @SuppressWarnings("unchecked")
  @ApiOperation("获取统计信息")
  @PostMapping("/stats")
  public Response<ProjectPredictionStatsVO> stats(
      @RequestBody ProjectQueryListVO q) {
    
    if (q == null) {
      q = new ProjectQueryListVO();
    }
    
    // 数据权限
    AccessRule rule = AccessRule.newRule();
    
    // 查部门
    rule.withDepts(q.getDataDeptIdList());
    
    // 包含自己
    if (q.getScope() == ProjectQueryListVO.SCOPE_ALL) {
      rule.withCurrentUser();
    }
    
    q.setAccessRule(rule);
    
    ProjectPredictionStatsVO vo = projectService.queryPredictionStats(q);
    return success(vo);
  }
}
