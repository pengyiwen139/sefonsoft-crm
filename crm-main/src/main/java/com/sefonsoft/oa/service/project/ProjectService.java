package com.sefonsoft.oa.service.project;

import com.sefonsoft.oa.domain.project.vo.ProjectPredictionListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionStatsVO;
import com.sefonsoft.oa.domain.project.vo.ProjectQueryListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticGroupVo;
import com.sefonsoft.oa.domain.statistic.CommonStatisticsDto;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryExpDTO;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticVo;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.common.PageableResult;

import java.util.List;
import java.util.Set;

/**
 * (ProjectInfo)表服务接口
 *
 * @author Aron
 * @since 2019-12-03 10:47:01
 */
public interface ProjectService {

    /**
     * 导出全量数据
     *
     * @param
     * @return
     */
    List<ProjectInfoQueryDTO> getConditionAll();

    /**
     * 导出全量数据
     *
     * @param
     * @return
     */
    List<ProjectInfoQueryExpDTO> getConditionAlls();



    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditionList(List<String> dataDeptIdList,ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditionListCount(List<String> dataDeptIdList,ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditiont(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditiont(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditions(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditions(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditionx(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditionx(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditionxx(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditionxx(ProjectInfoQueryDTO projectInfoQueryDTO);


    /**
     * 查询是否是子部门
     *
     * @param deptId
     * @param deptIds
     * @return
     */
    int contain(String deptId,String deptIds);

    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getCondition(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findCondition(ProjectInfoQueryDTO projectInfoQueryDTO);



    /**
     * 查询项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> getConditionss(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditionss(ProjectInfoQueryDTO projectInfoQueryDTO);


    /**
     * 通过projectId删除数据
     *
     * @param projectId
     * @return 是否成功
     */
    boolean deleteById(String projectId);


    /**
     * 通过projectId查询单条数据
     *
     * @param projectId
     * @return 实例对象
     */
    ProjectInfoQueryDTO queryByProjectId(String projectId);
    
    /**
     * 批量查询项目基础信息
     * @param projectId
     * @return
     */
    List<ProjectInfoQueryDTO> queryByProjectId(List<String> projectId);


    /**
     * 修改数据
     * @param loginUserDTO
     * @param projectInfoQueryDTO 实例对象
     * @return 实例对象
     */
    int check(LoginUserDTO loginUserDTO, ProjectInfoQueryDTO projectInfoQueryDTO);




    /**
     * 修改数据
     * @param
     * @param projectInfoQueryDTO 实例对象
     * @return 实例对象
     */
    int checkAll(ProjectInfoQueryDTO projectInfoQueryDTO);


    /**
     * 查询为填写项目跟踪日报列表
     *
     * @param employeeReportDTO 查询条件
     * @return
     */
    List<EmployeeReportDTO> projectFollow(EmployeeReportDTO employeeReportDTO);

    /**
     * 查询为填写项目跟踪日报总数
     *
     * @param employeeReportDTO 查询条件
     * @return
     */
    int projectFollowTotal(EmployeeReportDTO employeeReportDTO);

    /**
     * 查询客户项目阶段
     *
     * @param
     * @return
     */
    List<String> getCustomerProjectPhaset();

    /**
     * statistic
     * @param onTime 0 所有, 1-月，2-季度，3-年, 4-周
     * @return
     */
    List<ProjectStatisticGroupVo> statistic(Set<String> deptIds, int onTime, String employeeId, LoginUserDTO loginUserDTO);

    /**
     * 项目预测列表
     * @param vo
     * @return
     */
    PageableResult<ProjectPredictionListVO> queryPredictionList(ProjectQueryListVO vo);

    /**
     * 项目预测统计
     * @param q
     * @return
     */
    ProjectPredictionStatsVO queryPredictionStats(ProjectQueryListVO q);



}