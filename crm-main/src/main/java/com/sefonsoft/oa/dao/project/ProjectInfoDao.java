package com.sefonsoft.oa.dao.project;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sefonsoft.oa.domain.project.CustomerInfoInProjectDTO;
import com.sefonsoft.oa.domain.project.IntegerMapKeyDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoGetOneDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoInsertDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoUpdateDTO;
import com.sefonsoft.oa.domain.project.ProjectOverdueInfoDTO;
import com.sefonsoft.oa.domain.project.ProjectSimilarDTO;
import com.sefonsoft.oa.domain.project.SalesProjectStageDTO;
import com.sefonsoft.oa.domain.project.StringMapKeyDTO;
import com.sefonsoft.oa.domain.project.vo.ProjectExcelVO;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.product.ProductInfo;
import com.sefonsoft.oa.entity.project.ProjectInfo;

/**
 * (ProjectInfo)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-11-14 10:47:01
 */
public interface ProjectInfoDao {

    /**
     * 通过主键删除数据 <br>
     * 这是一个逻辑删除
     * @param id
     * @return 影响行数
     */
    boolean deleteById(Long id);

    String getMaxProjectId();

    List<StringMapKeyDTO> getCownerPairs(@Param("cownerList") String[] cownerList);

    List<IntegerMapKeyDTO> getProsPairs();

    List<IntegerMapKeyDTO> getSpstagePairs();

    List<IntegerMapKeyDTO> getStagePairs();

    int countProject();

    CustomerInfoInProjectDTO getCustomerByCid(String customerId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     * @deprecated 似乎没有使用了
     */
    @Deprecated
    List<ProjectInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param projectInfo 实例对象
     * @return 对象列表
     * @deprecated 似乎没有用了
     */
    @Deprecated
    List<ProjectInfo> queryAll(ProjectInfo projectInfo);

    /**
     * 新增数据
     *
     * @param projectInfoInsertDTO 实例对象
     * @return 影响行数
     */
    int insert(ProjectInfoInsertDTO projectInfoInsertDTO);

    /**
     * 修改数据
     *
     * @param projectInfoUpdateDTO 实例对象
     * @return 影响行数
     */
    int update(ProjectInfoUpdateDTO projectInfoUpdateDTO);

    List<SalesProjectStageDTO> getSalesProjectStageList(SalesProjectStageDTO salesProjectStageDTO);

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
    List<CustomerInfo> getPartnersExludeByCustomerId(@Param("customerId") String customerId, @Param("employeeId") String employeeId);

    ProjectInfoGetOneDTO queryByProjectId(String projectId);

    int countByProjectName(String projectName);

    /**
     * 相似检查第一版本
     * @param projectId
     * @param similarProjectNames
     * @param useStrict
     * @return
     */
    List<ProjectSimilarDTO> searchSimilarProjectName(@Param("projectId") String projectId, @Param("list") List<String> similarProjectNames, @Param("useStrict") boolean useStrict);

    /**
     * 检查相似项目
     * @param projectId 排除项目ID
     * @param customerName 客户名称
     * @param products 项目列表
     * @return 相似的项目ID
     */
    List<String> searchSimilarProjectV2(
        @Param("projectId") String projectId, 
        @Param("customerName") String customerName, 
        @Param("products") List<Integer> products);

    
    /**
     * 组装相似详情
     * @param projectIds 项目ID
     * @return
     */
    List<ProjectSimilarDTO> getSimilarProjects(
        @Param("projects") List<String> projectIds);
    
    /**
     * 默认6个月超期立项的项目
     * @param projectOverdueInfoDTO
     * @return
     */
    List<ProjectInfoQueryDTO> projectOverdueList(ProjectOverdueInfoDTO projectOverdueInfoDTO);

    /**
     * 默认6个月超期立项的项目总数
     * @param projectOverdueInfoDTO
     * @return
     */
    int projectOverdueCount(ProjectOverdueInfoDTO projectOverdueInfoDTO);

    /**
     * 过期项目继续更进，修改创建时间和过期项目标识
     *
     * @param projectOverdueInfoDTO
     * @return
     */
    boolean projectOverdueUpdate(ProjectOverdueInfoDTO projectOverdueInfoDTO);

    /**
     * 离职人员6个月超期立项的项目
     * @param projectOverdueInfoDTO
     * @return
     */
    List<ProjectInfo> projectTerminatedOverdueList(ProjectOverdueInfoDTO projectOverdueInfoDTO);

    /**
     * 离职人员6个月过期项目 总数
     * @param jobStatus
     * @param projectCreateTime
     * @return
     */
    int projectTerminatedOverdueCount(@Param("jobStatus") Integer jobStatus ,@Param("projectCreateTime") Date projectCreateTime );

    int countByprojectId(String projectId);
    /**
     * 查询离职人员项目列表
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    List<ProjectInfoQueryDTO> terminatedNotOverdueList(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 查询离职人员项目总数
     *
     * @param projectInfoQueryDTO 查询条件
     * @return
     */
    int terminatedNotOverdueCount(ProjectInfoQueryDTO projectInfoQueryDTO);

    /**
     * 通过商机id查询项目
     * xielf
     * @param bizOpportsId
     * @return
     */
    List<ProjectInfo> findProjectInfoByBizOpportsId(@Param("bizOpportsId") Long bizOpportsId);

    /**
     * 获取导出结果
     * @param userId
     * @param copyOf
     * @return
     */
    List<ProjectExcelVO> getExportDatas(@Param("employeeId") String userId, @Param("deptIds") Set<String> copyOf);

    /**
     * 检查是否存在
     * @param projectId
     * @return
     */
    boolean isExists(@Param("projectId") String projectId);
}