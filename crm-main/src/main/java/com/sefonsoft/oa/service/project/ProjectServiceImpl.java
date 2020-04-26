package com.sefonsoft.oa.service.project;

import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.project.*;
import com.sefonsoft.oa.dao.report.EmployeeReportInfoDao;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.customer.EmployeeReportDTO;
import com.sefonsoft.oa.domain.project.ProjectCheckInfo;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoQueryExpDTO;
import com.sefonsoft.oa.domain.project.dto.PredictionInfo;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectPredictionStatsVO;
import com.sefonsoft.oa.domain.project.vo.ProjectQueryListVO;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticGroupVo;
import com.sefonsoft.oa.domain.project.vo.ProjectStatisticVo;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.project.ProductProjectRef;
import com.sefonsoft.oa.entity.project.ProjectAmountDevideRef;
import com.sefonsoft.oa.entity.project.ProjectInfo;
import com.sefonsoft.oa.entity.project.ProjectSaleRef;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.service.customer.CustomerInfoServiceImpl;
import com.sefonsoft.oa.system.emun.CheckStatus;
import com.sefonsoft.oa.system.emun.OnTimeEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.sefonsoft.oa.system.constant.ReportConstant.DAY_TYPE_REPORT;
import static com.sefonsoft.oa.system.constant.ReportConstant.OPERATION_OPERATE_REPORT;

/**
 * (ProjectInfo)表服务实现类
 *
 * @author Aron
 * @since 2019-12-03 10:47:01
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {


    /**
     * log
     */
    private final static Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Resource
    private ProjectDao projectDao;
    @Resource
    private ProjectSaleRefDao projectSaleRefDao;
    @Resource
    private ProjectCheckInfoDao projectCheckInfoDao;
    @Resource
    private ProjectAmountDevideRefDao projectAmountDevideRefDao;
    @Resource
    private ProductProjectRefDao productProjectRefDao;
    @Resource
    private EmployeeReportInfoDao employeeReportInfoDao;
    @Resource
    private BizOpportsDao bizOpportsDao;

    private SysDepartmentDao departmentDao;

    @Autowired
    public void setDepartmentDao(SysDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<ProjectInfoQueryDTO> getConditionAll() {
        List<ProjectInfoQueryDTO> list = projectDao.getConditionAll();
        if (ObjTool.isNotNull(list)) {
            for(ProjectInfoQueryDTO projectInfo : list) {
                List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(projectInfo.getProjectId());
                projectInfo.setProductProjectRefList(productProjectRefList);
            }
        }

        return list;
    }

    @Override
    public List<ProjectInfoQueryExpDTO> getConditionAlls() {

        List<ProjectInfoQueryExpDTO> list = projectDao.getConditionAlls();
        return list;
    }

    @Override
    public List<ProjectInfoQueryDTO> getConditionList(List<String> dataDeptIdList,ProjectInfoQueryDTO projectInfoQueryDTO) {
        List<ProjectInfoQueryDTO> list = projectDao.getConditionList(dataDeptIdList,projectInfoQueryDTO);

        if (ObjTool.isNotNull(list)) {
          for(ProjectInfoQueryDTO projectInfo : list) {
              List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(projectInfo.getProjectId());
              projectInfo.setProductProjectRefList(productProjectRefList);
          }
        }
        return list;
    }

    @Override
    public int findConditionListCount(List<String> dataDeptIdList,ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditionListCount(dataDeptIdList,projectInfoQueryDTO);
    }

    @Override
    public List<ProjectInfoQueryDTO> getConditiont(ProjectInfoQueryDTO projectInfoQueryDTO) {
        List<ProjectInfoQueryDTO> list = projectDao.getConditiont(projectInfoQueryDTO);

        if (ObjTool.isNotNull(list)) {
          for(ProjectInfoQueryDTO projectInfo : list) {
              List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(projectInfo.getProjectId());
              projectInfo.setProductProjectRefList(productProjectRefList);
          }
        }
        return list;
    }

    @Override
    public int findConditiont(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditiont(projectInfoQueryDTO);
    }

    @Override
    public List<ProjectInfoQueryDTO> getConditions(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.getConditions(projectInfoQueryDTO);
    }

    @Override
    public int findConditions(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditions(projectInfoQueryDTO);
    }


    @Override
    public List<ProjectInfoQueryDTO> getConditionx(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.getConditionx(projectInfoQueryDTO);
    }

    @Override
    public int findConditionx(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditionx(projectInfoQueryDTO);
    }

    @Override
    public List<ProjectInfoQueryDTO> getConditionxx(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.getConditionxx(projectInfoQueryDTO);
    }

    @Override
    public int findConditionxx(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditionxx(projectInfoQueryDTO);
    }

    @Override
    public int contain(String deptId, String deptIds) {
        return projectDao.contain(deptId,deptIds);
    }

    @Override
    public List<ProjectInfoQueryDTO> getCondition(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.getCondition(projectInfoQueryDTO);
    }

    @Override
    public int findCondition(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findCondition(projectInfoQueryDTO);
    }


    @Override
    public List<ProjectInfoQueryDTO> getConditionss(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.getConditionss(projectInfoQueryDTO);
    }

    @Override
    public int findConditionss(ProjectInfoQueryDTO projectInfoQueryDTO) {
        return projectDao.findConditionss(projectInfoQueryDTO);
    }


    @Override
    public boolean deleteById(String projectId) {

        //删除关联信息表
        projectSaleRefDao.deleteById(projectId);
        projectCheckInfoDao.deleteByIds(projectId);
        projectAmountDevideRefDao.deleteByIds(projectId);
        productProjectRefDao.deleteByIds(projectId);

        //释放商机立项状态
        long opportId = projectDao.getBizOpportIdByProjectId(projectId);
        BizOpportsDTO bizOpportsDTO = new BizOpportsDTO();
        bizOpportsDTO.setId(opportId);
        bizOpportsDTO.setModifiedTime(new Date());
        bizOpportsDTO.setState(1);
        bizOpportsDao.update(bizOpportsDTO);

        return this.projectDao.deleteById(projectId) > 0;
    }

    @Override
    public ProjectInfoQueryDTO queryByProjectId(String projectId) {
        ProjectInfoQueryDTO projectInfoQueryDTO = projectDao.queryByProjectId(projectId);
        if (ObjTool.isNotNull(projectInfoQueryDTO)) {
            ProjectSaleRef projectSaleRef = projectSaleRefDao.queryByProjectId(projectId);
            projectInfoQueryDTO.setEmployeeId(projectSaleRef.getEmployeeId());
            List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(projectId);
            List<ProjectAmountDevideRef> projectAmountDevideRefList = projectAmountDevideRefDao.querybyProjectId(projectId);
            projectInfoQueryDTO.setProductProjectRefList(productProjectRefList);
            projectInfoQueryDTO.setProjectAmountDevideRefList(projectAmountDevideRefList);
            return projectInfoQueryDTO;
        }
        return projectInfoQueryDTO;
    }

    @Override
    public List<ProjectInfoQueryDTO> queryByProjectId(List<String> projectIds) {

      return projectDao.queryByProjectIds(projectIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int check(LoginUserDTO loginUserDTO, ProjectInfoQueryDTO projectInfoQueryDTO) {
        Date date = new Date();
        ProjectCheckInfo projectCheckInfo = new ProjectCheckInfo();
        projectCheckInfo.setEmployeeId(loginUserDTO.getUserId());
        projectCheckInfo.setProjectId(projectInfoQueryDTO.getProjectId());
        projectCheckInfo.setCheckStatus(projectInfoQueryDTO.getCheckStatus());
        projectCheckInfo.setOpinion(projectInfoQueryDTO.getOpinion());
        projectCheckInfo.setLastTime(date);
        //改状态为立项
        projectInfoQueryDTO.setProsId(2);
        int update = projectDao.update(projectInfoQueryDTO);
        int updates = projectDao.updates(projectCheckInfo);
        if (update > 0 && updates > 0) {
            ProjectInfoQueryDTO projectInfo = projectDao.queryByProjectId(projectInfoQueryDTO.getProjectId());
            employeeReportInfoDao.insert(new EmployeeReportInfo().setEmployeeId(loginUserDTO.getUserId()).setReportType(DAY_TYPE_REPORT).setReportTime(date).setProjectId(projectInfo.getProjectId())
                    .setContactId(projectInfo.getContactId()).setOperationType(OPERATION_OPERATE_REPORT)
                    .setFollowDetails(loginUserDTO.getEmployeeName() + "审批通过了名为【" + projectInfo.getProjectName() + "】的项目")
                    .setOperator(loginUserDTO.getUserId()).setLastTime(date).setCreateTime(date));
            return 1;
        }
        return 0;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int checkAll(ProjectInfoQueryDTO projectInfoQueryDTO) {
        Date date = new Date();
        int checkStatus = projectInfoQueryDTO.getCheckStatus();
        
        ProjectInfoQueryDTO project = projectDao.queryByProjectId(projectInfoQueryDTO.getProjectId());
        
        if (project == null) {
          throw new MsgException("项目" + projectInfoQueryDTO.getProjectId() + "不存在");
        }
        
        
        //大区总审批通过
        if(checkStatus==CheckStatus.DQZYSP.getCode()){
            ProjectCheckInfo projectCheckInfo = new ProjectCheckInfo();
            projectCheckInfo.setEmployeeId(projectInfoQueryDTO.getEmployeeId());
            projectCheckInfo.setProjectId(projectInfoQueryDTO.getProjectId());
            projectCheckInfo.setCheckStatus(checkStatus);
            projectCheckInfo.setOpinion(projectInfoQueryDTO.getOpinion());
            projectCheckInfo.setLastTime(date);

            int updates = projectDao.updates(projectCheckInfo);
            return updates;
        }
        //大区总审批已驳回
        if(checkStatus==CheckStatus.YBB.getCode()){
            ProjectCheckInfo projectCheckInfo = new ProjectCheckInfo();
            projectCheckInfo.setEmployeeId(projectInfoQueryDTO.getEmployeeId());
            projectCheckInfo.setProjectId(projectInfoQueryDTO.getProjectId());
            projectCheckInfo.setCheckStatus(checkStatus);
            projectCheckInfo.setOpinion(projectInfoQueryDTO.getOpinion());
            projectCheckInfo.setLastTime(date);

            int updates = projectDao.updates(projectCheckInfo);
            return updates;
        }
        //立项专员审批通过
        if(checkStatus==CheckStatus.YTG.getCode()){
            ProjectCheckInfo projectCheckInfo = new ProjectCheckInfo();
            projectCheckInfo.setEmployeeId(projectInfoQueryDTO.getEmployeeId());
            projectCheckInfo.setProjectId(projectInfoQueryDTO.getProjectId());
            projectCheckInfo.setCheckStatus(checkStatus);
            projectCheckInfo.setOpinion(projectInfoQueryDTO.getOpinion());
            projectCheckInfo.setLastTime(date);
            //项目状态改为“立项”
            projectInfoQueryDTO.setProsId(2);
            projectInfoQueryDTO.setLastTime(date);
            // int overTimeFlag = projectInfoQueryDTO.getOverTimeFlag();
            //项目过期继续根据，改变创建时间,改变状态为2（正常跟进）
            // if(overTimeFlag==1){
            //    projectInfoQueryDTO.setCreateTime(date);
            //    projectInfoQueryDTO.setOverTimeFlag(2);
            // }
            int update = projectDao.update(projectInfoQueryDTO);
            int updates = projectDao.updates(projectCheckInfo);

            
            
            // 更新商机为已立项
            // 兼容不存在商机的情况
            Integer bid = project.getBizOpportId();
            if (bid != null) {
              BizOpportsDTO bizOpportsDTO = new BizOpportsDTO();
              bizOpportsDTO.setId(bid.longValue());
              bizOpportsDTO.setModifiedTime(date);
              bizOpportsDTO.setState(2);
              bizOpportsDao.update(bizOpportsDTO);
            }

            return updates;
        }
        //立项专员审批已拒绝
        if(checkStatus== CheckStatus.YJJ.getCode()){
            ProjectCheckInfo projectCheckInfo = new ProjectCheckInfo();
            projectCheckInfo.setEmployeeId(projectInfoQueryDTO.getEmployeeId());
            projectCheckInfo.setProjectId(projectInfoQueryDTO.getProjectId());
            projectCheckInfo.setCheckStatus(checkStatus);
            projectCheckInfo.setOpinion(projectInfoQueryDTO.getOpinion());
            projectCheckInfo.setLastTime(date);

            //释放商机立项状态
            // 兼容没有商机的项目
            Integer bid = project.getBizOpportId();
            
            if (bid != null) {
              long opportId = bid;
              BizOpportsDTO bizOpportsDTO = new BizOpportsDTO();
              bizOpportsDTO.setId(opportId);
              bizOpportsDTO.setModifiedTime(new Date());
              bizOpportsDTO.setState(1);
              bizOpportsDao.update(bizOpportsDTO);            
            }
            

            int updates = projectDao.updates(projectCheckInfo);
            return updates;
        }

        return 1;
    }



    @Override
    public List<EmployeeReportDTO> projectFollow(EmployeeReportDTO employeeReportDTO) {
        return projectDao.projectFollow(employeeReportDTO);
    }

    @Override
    public int projectFollowTotal(EmployeeReportDTO employeeReportDTO) {
        return projectDao.projectFollowTotal(employeeReportDTO);
    }

    @Override
    public List<String> getCustomerProjectPhaset() {
        return projectDao.getCustomerProjectPhaset();
    }

    @Override
    public List<ProjectStatisticGroupVo> statistic(Set<String> deptIds, int onTime, String employeeId, LoginUserDTO loginUserDTO) {

        OnTimeEnum onTimeEnum = OnTimeEnum.convert(onTime);
        try {
            Objects.requireNonNull(onTimeEnum);
        } catch (Exception e) {
            throw new MsgException("传入时间类型有误");
        }

        if(deptIds==null){
            deptIds = projectDao.getDeptIdsByEmployeeId(employeeId);
        }

        LOG.info("根据{} 统计项目信息",onTimeEnum.getDescription());

        LocalDate localDate = LocalDate.now();
        List<GroupTuple> groupEntry = new ArrayList<>();
        switch (onTimeEnum){

            case ALL:

                ProjectInfo project = projectDao.findFirstProject();

                if(project!=null){
                    LocalDate first = project.getCreateTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .toLocalDate();

                    while (first.getYear() <= localDate.getYear()){
                        groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.YEAR).format(first), null));
                        first = first.plusYears(1);
                    }
                }else{
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.YEAR).format(localDate), null));
                }
                break;
            case QUARTER:

                //原结束时间为“今天”
                /*String startTimeSequence = DateUtils.getPatternOfQuarter().format(localDate);
                LocalDate now = LocalDate.now();
                LocalDate startDate = LocalDate.parse(startTimeSequence, DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                while (!(startDate.getMonthValue() == now.getMonthValue() && startDate.getDayOfMonth() == now.getDayOfMonth())) {
                    if ((startDate = startDate.plusDays(1)).getDayOfWeek().getValue() == 1) {
                        String startValue1 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                        startDate = startDate.plusDays(6);
                        String endValue2 = null;
                        if(startDate.getMonthValue()>now.getMonthValue()){
                            endValue2 = now.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                            groupEntry.add(GroupTuple.of(startValue1, endValue2));
                            break;
                        }else{
                            if(startDate.getMonthValue()== now.getMonthValue() && startDate.getDayOfMonth() >= now.getDayOfMonth()){
                                endValue2 = now.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                                groupEntry.add(GroupTuple.of(startValue1, endValue2));
                                break;
                            }
                            endValue2 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                            groupEntry.add(GroupTuple.of(startValue1, endValue2));
                        }
                    }
                }*/


                //计算开始时间
                String startTimeSequence = DateUtils.getPatternOfQuarter().format(localDate);
                LocalDate startDate = LocalDate.parse(startTimeSequence, DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                int endMonth = startDate.plusMonths(3).getMonthValue();

                while (startDate.getMonthValue() < endMonth) {

                    startDate = startDate.plusDays(1);
                    String startValue1 =null;
                    String endValue2 = null;

                    if(startDate.getDayOfWeek().getValue()==7){
                        endValue2 = startDate.format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                        startValue1 = startDate.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN));
                        groupEntry.add(GroupTuple.of(startValue1, endValue2));
                    }


                }

                break;

            case MONTH:

                localDate = LocalDate.now().withDayOfMonth(1);
                int monthDay = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                for (int i = 0; i < (monthDay-1); i++) {
                    localDate = localDate.plusDays(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                }

                //原结束时间为“今天”
                /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                while (localDate.getDayOfMonth()!=1){
                    localDate = localDate.minusDays(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                }*/
                break;

            case WEEK:

                localDate = LocalDate.now().with(DayOfWeek.MONDAY);
                groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                for (int i = 0; i < 6; i++) {
                    localDate = localDate.plusDays(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                }

                //原结束时间为“今天”
                /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                while (localDate.getDayOfWeek().getValue()!=1){
                    localDate = localDate.minusDays(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN).format(localDate), null));
                }*/
                break;

            case YEAR:

                //原结束时间为“今天”
                /*groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
                while (localDate.getMonthValue()!=1){
                    localDate = localDate.minusMonths(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
                }*/

                localDate = localDate.withMonth(1);

                groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
                for (int i = 0; i < 11; i++) {
                    localDate = localDate.plusMonths(1);
                    groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.MONTH_OF_YEAR_PATTERN).format(localDate), null));
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + onTimeEnum);
        }

        List<ProjectStatisticGroupVo> projectStatisticGroupVos = new ArrayList<>();
        switch (onTimeEnum) {

            case ALL:
                for (String deptId : deptIds) {
                    ProjectStatisticGroupVo groupVo = new ProjectStatisticGroupVo();
                    final List<ProjectStatisticVo> projectStatisticVos = projectDao.statisticsOfGroup(groupEntry, 2, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);;
                    if(sysDepartment!=null){
                        groupVo.setCategoryAndData(projectStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        projectStatisticGroupVos.add(groupVo);
                    }
                    else{
                        LOG.error("出现数据错误,项目数据统计,此 {} 部门数据不存在, 统计将丢弃",deptId);
                    }
                }
                break;

            case QUARTER:
                for (String deptId : deptIds) {
                    ProjectStatisticGroupVo groupVo = new ProjectStatisticGroupVo();
                    final List<ProjectStatisticVo> projectStatisticVos = projectDao.statisticsOfPeriod(groupEntry, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);;
                    if(sysDepartment!=null){
                        groupVo.setCategoryAndData(projectStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        projectStatisticGroupVos.add(groupVo);
                    }
                    else{
                        LOG.error("出现数据错误,项目数据统计,此 {} 部门数据不存在, 统计将丢弃",deptId);
                    }
                    for (int i = 0; i < projectStatisticVos.size(); i++) {
                        projectStatisticVos.get(i).setDate(String.format("第%d周",(i+1)));
                    }
                }
                break;
            case MONTH:
            case WEEK:
                for (String deptId : deptIds) {
                    ProjectStatisticGroupVo groupVo = new ProjectStatisticGroupVo();
                    final List<ProjectStatisticVo> projectStatisticVos = projectDao.statisticsOfGroup(groupEntry, 0, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);;
                    if(sysDepartment!=null){
                        groupVo.setCategoryAndData(projectStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        projectStatisticGroupVos.add(groupVo);
                    }
                    else{
                        LOG.error("出现数据错误,项目数据统计,此 {} 部门数据不存在, 统计将丢弃",deptId);
                    }
                }
                break;
            case YEAR:
                for (String deptId : deptIds) {
                    ProjectStatisticGroupVo groupVo = new ProjectStatisticGroupVo();
                    final List<ProjectStatisticVo> projectStatisticVos = projectDao.statisticsOfGroup(groupEntry, 1, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);
                    if(sysDepartment!=null){
                        groupVo.setCategoryAndData(projectStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        projectStatisticGroupVos.add(groupVo);
                    }
                    else{
                        LOG.error("出现数据错误,项目数据统计,此 {} 部门数据不存在, 统计将丢弃",deptId);
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + onTimeEnum);
        }
        return projectStatisticGroupVos;

    }
    
    @Override
    public PageableResult<ProjectPredictionListVO> queryPredictionList(ProjectQueryListVO q) {
      
      q.setCheckStatuses(Arrays.asList(2));
      
      long count = projectDao.selectPredictionListCount(q);
      
      List<ProjectPredictionListVO> list = Collections.emptyList();
          
      if (count > 0) {
        list = projectDao.selectPredictionList(q);
      }
      
      return new PageableResult<ProjectPredictionListVO>(count, list);
    }
    
    @Override
    public ProjectPredictionStatsVO queryPredictionStats(ProjectQueryListVO q) {
      
      q.setCheckStatuses(Arrays.asList(2));
      
      // 按概率
      List<PredictionInfo> sucessInfo = projectDao.selectPredictionInfoBySucess(q);
      
      // 按月
      List<PredictionInfo> monthInfo = projectDao.selectPredictionInfoByMonth(q);
      
      // 总信息
      BigDecimal totalMoney = new BigDecimal(0);
      int totalCount = 0;

      for (PredictionInfo m : sucessInfo) {
        totalCount += m.getCount();
        totalMoney = totalMoney.add(m.getMoney());
      };
      
      ProjectPredictionStatsVO ppvo = new ProjectPredictionStatsVO();
      ppvo.setTotalEstimateMoney(totalMoney);
      ppvo.setTotalCount(totalCount);
      ppvo.setSucessInfo(sucessInfo);
      ppvo.setMonthInfo(monthInfo);
      
      return ppvo;
    }

}