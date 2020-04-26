package com.sefonsoft.oa.dao.project;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.project.ProjectCheckInfo;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryExpDTO;
import com.sefonsoft.oa.domain.project.dto.PredictionInfo;
import com.sefonsoft.oa.domain.project.dto.PredictionInfo;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionStatsVO;
import com.sefonsoft.oa.domain.project.vo.ProjectQueryListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticVo;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.project.ProjectInfo;

/**
 * (ProjectInfo)表数据库访问层
 *
 * @author Aron
 * @since 2019-12-03 10:47:01
 */
public interface ProjectDao {


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
    List<ProjectInfoQueryDTO> getConditionList(@Param("dataDeptIdList") List<String> dataDeptIdList, @Param("projectInfoQueryDTO") ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int findConditionListCount(@Param("dataDeptIdList") List<String> dataDeptIdList, @Param("projectInfoQueryDTO") ProjectInfoQueryDTO projectInfoQueryDTO);


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
    int contain(@Param("deptId") String deptId, @Param("deptIds") String deptIds);

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
     * 这是一个逻辑删除
     *
     * @param projectId
     * @return 影响行数
     */
    int deleteById(String projectId);

    /**
     * 通过商机 ID 查询单条数据
     *
     * @param projectId
     * @return 实例对象
     */
    ProjectInfoQueryDTO queryByBizId(@Param("bizId") Long bizId);


    /**
     * 通过projectId查询单条数据
     *
     * @param projectId
     * @return 实例对象
     */
    ProjectInfoQueryDTO queryByProjectId(String projectId);

    /**
     * 批量查询项目基础信息
     * @param projectIds 项目编号
     * @return
     */
    List<ProjectInfoQueryDTO> queryByProjectIds(@Param("projectIds") List<String> projectIds);

    /**
     * 修改数据
     *
     * @param projectCheckInfo 实例对象
     * @return 影响行数
     */
    int updates(ProjectCheckInfo projectCheckInfo);


    /**
     * 修改数据
     *
     * @param projectInfoQueryDTO 实例对象
     * @return 影响行数
     */
    int update(ProjectInfoQueryDTO projectInfoQueryDTO);



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
     * 统计 普通分组
     *
     * @param entryList
     * @param groupType 0按天数, 1按月, 2按年
     * @param deptId
     * @param loginUserDTO
     * @return
     */
    List<ProjectStatisticVo> statisticsOfGroup(@Param("groupDateList") List<GroupTuple> entryList,
                                               @Param("groupType") int groupType,
                                               @Param("deptId") String deptId,
                                               @Param("employeeId") String employeeId,
                                               @Param("loginUser") LoginUserDTO loginUserDTO);

    /**
     * xielf
     * @return
     */
    ProjectInfo findFirstProject();

    /**
     * 统计 时间段组
     * xielf
     * @param entryList
     * @param deptId
     * @return
     */
    List<ProjectStatisticVo> statisticsOfPeriod(@Param("groupDateList") List<GroupTuple> entryList,
                                                @Param("deptId") String deptId,
                                                @Param("employeeId") String employeeId,
                                                @Param("loginUser") LoginUserDTO loginUserDTO);


    /**
     * 统计个数
     * @param deptIds
     * @return
     */
    int statisticCount(@Param("deptIds") Set<String> deptIds,
                       @Param("employeeId") String employeeId);

    BigDecimal statisticMoney(@Param("deptIds") Set<String> deptIds,
                              @Param("employeeId") String employeeId);

    /**
     * 按时间统计个数
     * @param deptIds
     * @return
     */
    int onTimeStatisticCount(@Param("deptIds") Set<String> deptIds,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("employeeId") String employeeId);

    BigDecimal onTimeStatisticMoney(@Param("deptIds") Set<String> deptIds,
                                    @Param("startTime") String startTime,
                                    @Param("endTime") String endTime,
                                    @Param("employeeId") String employeeId);

    /**
     * 总数量
     * @return
     */
    int totalCount();

    /**
     * 获取立项对应的商机
     * @param projectId
     * @return
     */
    long getBizOpportIdByProjectId(String projectId);

    /**
     * 根据员工查询部门ID
     * xielf
     * @param employeeId
     * @return
     */
    Set<String> getDeptIdsByEmployeeId(@Param("employeeId") String employeeId);


    /**
     * 总金额
     * @return
     */
    int totalAmount();

    /**
     * 项目预测列表
     * @param q
     * @return
     */
    List<ProjectPredictionListVO> selectPredictionList(ProjectQueryListVO q);

    /**
     * 项目预测数量
     * @param q
     * @return
     */
    int selectPredictionListCount(ProjectQueryListVO q);

    
    /**
     * 按成功概率统计预测信息
     * @param q
     * @return
     */
    List<PredictionInfo> selectPredictionInfoBySucess(ProjectQueryListVO q);

    /**
     * 按月统计预测信息
     * @param q
     * @return
     */
    List<PredictionInfo> selectPredictionInfoByMonth(ProjectQueryListVO q);
    
    
}