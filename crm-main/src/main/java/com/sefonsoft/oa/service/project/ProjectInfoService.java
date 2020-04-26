package com.sefonsoft.oa.service.project;

import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.domain.project.*;
import com.sefonsoft.oa.domain.project.vo.ProjectExcelVO;
import com.sefonsoft.oa.domain.project.vo.SalesUpdateVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.product.ProductInfo;
import com.sefonsoft.oa.entity.project.ProjectInfo;
import com.sefonsoft.oa.entity.project.ProjectSaleRef;
import com.sefonsoft.oa.system.constant.ProjectConstant;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * (ProjectInfo)表服务接口
 *
 * @author PengYiWen
 * @since 2019-11-14 10:47:01
 */
public interface ProjectInfoService {

//    ProjectNeedParamsDTO getNeedParams();

    boolean batchpUdatePerson(ProjectSaleRefUpdateDTO projectInfoUpdateDTO, String userId);

    CustomerInfoInProjectDTO getCustomerByCid(String customerId);

    /**
     * 获取销售项目阶段
     * @param salesProjectStageDTO
     * @return
     */
    List<SalesProjectStageDTO> getSalesProjectStageList(SalesProjectStageDTO salesProjectStageDTO);

    /**
     * 新增项目
     * @param projectInfoInsertDTO
     * @return
     */
    boolean insert(ProjectInfoInsertDTO projectInfoInsertDTO, LoginUserDTO loginUserDTO);

    /**
     * 获取产品列表
     * @param productInfo
     * @return
     */
    List<ProductInfo> getProductInfoList(ProductInfo productInfo);

    /**
     * 获取某个员工的客户列表，除所传的客户编号之外
     * @param customerId
     * @return
     */
    List<CustomerInfo> getPartnersExludeByCustomerId(String customerId, String employeeId);

    /**
     * 新增项目
     * @param projectInfoUpdateDTO
     * @return
     */
    boolean update(ProjectInfoUpdateDTO projectInfoUpdateDTO, LoginUserDTO loginUserDTO);

    ProjectInfoGetOneDTO get(String projectId);

    String getMaxProjectId();

    int countByProjectName(String projectName);

    /**
     * 查询项目相似的项目
     * @param projectName
     * @return
     */
    List<ProjectSimilarDTO> searchSimilarProjectName(String projectId, String projectName, boolean useStrict);

//    /**
//     * 查询最终客户是否相同
//     * @param customerId
//     * @param projectNameSimilarList
//     * @return
//     */
//    List<ProjectSimilarDTO> searchSimilarCustomer(String customerId, List<ProjectSimilarDTO> projectNameSimilarList);

/*
    ProjectInfoInsertDTO setCustomerPartnerInfo(ProjectInfoInsertDTO projectInfoInsertDTO);
*/

    List<ProjectSimilarDTO> searchSameProducts(List<Integer> productIdList , List<ProjectSimilarDTO> projectNameSimilarList);

    List<ProjectSimilarDTO> getSimilarProjectList(String projectId, String projectName, boolean useStrict, String customerId, List<Integer> productIdList);

    /**
     * 查询过期项目列表
     * @param projectOverdueInfoDTO
     * @return
     */
    List<ProjectInfoQueryDTO> projectOverdueList(ProjectOverdueInfoDTO projectOverdueInfoDTO );
    /**
     * 查询过期项目总数
     * @param projectOverdueInfoDTO
     * @return
     */
    int projectOverdueCount(ProjectOverdueInfoDTO projectOverdueInfoDTO);

    /**
     * 分派任务，不需要审批
     * 继续跟进过期项目，需要审批
     * 
     * 修改创建时间和过期项目标识
     * @param userid 分配用户
     * @param projectIds 项目ID
     * @param follow 
     * @return
     */
    boolean assignProject(String userid, List<String> projectIds, int mode);
    
    /**
     * 更新项目负责人
     * @param projId 项目ID
     * @param empId  负责人ID
     * @param now  更新时间
     */
    void changeSale(String projId, String empId, Date now);
    
    /**
     * 释放项目
     * @param projId
     * @return
     */
    boolean unassignProject(List<String> projId);

    /**
     * 定时任务清除   离职人员6个月超期立项的项目
     *
     *
     * @throws ParseException
     */
    void projectTerminatedOverdueListTask();

    /**
     * 修改负责人
     * @param projectSaleRef
     * @return
     */
    boolean updateProjectMiner(ProjectSaleRef projectSaleRef);

    /**
     * 离职员工立项项目总数
     * @param projectInfoQueryDTO
     * @return
     */
    int terminatedNotOverdueCount(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 离职员工立项项目列表
     * @param projectInfoQueryDTO
     * @return
     */
    List<ProjectInfoQueryDTO> terminatedNotOverdueList(ProjectInfoQueryDTO projectInfoQueryDTO);
    
    /**
     * 检查系统中所有逾期项目，并自动回收，回收逻辑：
     * <li>个人项目回收到部门资源池
     * <li>部门项目回收到公司资源池
     * @param saleLimit 个人项目逾期时间
     * @param deptLimit 大区项目逾期时间 
     * @return 释放项目列表 
     * <pre>
     * [
     *   {
     *     projectId: proj-0001,
     *     projectName: My Project
     *     release: 释放个人项目/释放大区项目
     *   }
     * ]
     * </pre>
     */
    List<Map<String, String>> gcOverdueProject(LocalDateTime timeline);
    
    /**
     * 释放项目
     * @param projectId 项目ID
     * @param releaseTo 释放到 {@link ProjectConstant#OVER_TIME_FLAG_DEPT} 或者 {@link ProjectConstant#OVER_TIME_FLAG_ALL}
     */
    boolean releaseOverdueProject(String projectId, int releaseTo);

    /**
     * 销售/大区总更新项目
     * @param updateVo 更新信息
     * @param empId 更新人
     * @return 是否成功
     */
    boolean salesUpdate(SalesUpdateVO updateVo, String userId);

    /**
     * 导入项目
     * @param workbook
     * @param loginUserDTO
     */
    void importProjects(Workbook workbook, LoginUserDTO loginUserDTO);

    /**
     * 导出项目
     * @param copyOf
     * @param userId
     * @return
     */
    List<ProjectExcelVO> exporProjects(ImmutableSet<String> copyOf, String userId);

    List<String> validateExcel(Workbook workbook);
}