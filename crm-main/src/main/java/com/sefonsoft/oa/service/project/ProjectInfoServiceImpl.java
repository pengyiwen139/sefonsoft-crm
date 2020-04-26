package com.sefonsoft.oa.service.project;

import static com.sefonsoft.oa.service.customer.CustomerInfoServiceImpl.NATURE_LIST;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.common.IndustryInfoDao;
import com.sefonsoft.oa.dao.contact.ContactInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.customer.CustomerSaleRefDao;
import com.sefonsoft.oa.dao.customer.EnterpriseType;
import com.sefonsoft.oa.dao.customer.EnterpriseTypeDao;
import com.sefonsoft.oa.dao.project.*;
import com.sefonsoft.oa.dao.report.EmployeeReportInfoDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo.EmployeeVo;
import com.sefonsoft.oa.domain.project.*;
import com.sefonsoft.oa.domain.project.vo.ProductExcelVO;
import com.sefonsoft.oa.domain.project.vo.ProjectExcelApplyVO;
import com.sefonsoft.oa.domain.project.vo.ProjectExcelVO;
import com.sefonsoft.oa.domain.project.vo.SalesUpdateVO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.entity.product.ProductInfo;
import com.sefonsoft.oa.entity.project.ProjectCheckInfo;
import com.sefonsoft.oa.entity.project.*;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.entity.workorder.BizOpportInfo;
import com.sefonsoft.oa.service.bizopports.BizOpportsService;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.service.sys.SysEnvService;
import com.sefonsoft.oa.service.sys.SysEnvService.EnvValue;
import com.sefonsoft.oa.system.constant.ProjectConstant;
import com.sefonsoft.oa.system.emun.CheckStatus;
import com.sefonsoft.oa.system.emun.GenderEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ExcelUtil;
import com.sefonsoft.oa.system.utils.ExcelUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.StringUtils;
import com.sefonsoft.oa.system.utils.UtilMethod;
import com.sefonsoft.oa.system.utils.UtilSJMethod;

import javafx.util.Pair;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sefonsoft.oa.system.constant.ProjectConstant.*;
import static com.sefonsoft.oa.system.constant.ReportConstant.DAY_TYPE_REPORT;
import static com.sefonsoft.oa.system.constant.ReportConstant.OPERATION_OPERATE_REPORT;
import static com.sefonsoft.oa.system.utils.ExcelUtils.cellToString;
import static com.sefonsoft.oa.system.utils.ExcelUtils.getCell;
import static com.sefonsoft.oa.system.utils.ExcelUtils.getSheet;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.ADDRESS;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.BIRTHDAY;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.CONTACT_EMAIL;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.CONTACT_NAME;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.*;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.CUSTOMER_NAME;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.DEPT_NAME;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.EMPLOYEE_ID;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.EMPLOYEE_NAME;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.ENTER_NAME;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.FAMILY_INFO;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.FAX;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.GENDER;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.INDUSTRY;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.JOB;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.MAJOR;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.OFFICE_PLANE;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.OTHER;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.PROVINCIAL;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.TELEPHONE;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.UNIVERSITY;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.WEBSITE;
import static com.sefonsoft.oa.system.utils.ExcelUtils.CustomerExcelIdx.ZIP_CODE;

/**
 * (ProjectInfo)表服务实现类
 *
 * @author PengYiWen
 * @since 2019-11-14 10:47:01
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends Response<Object> implements ProjectInfoService{
    private static final Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);
    @Resource
    private ProjectInfoDao projectInfoDao;
    @Resource
    private ProjectSaleRefDao projectSaleRefDao;
    @Resource
    private EmployeeReportInfoDao employeeReportInfoDao;
    @Resource
    private ProjectCheckInfoDao projectCheckInfoDao;
    @Resource
    private ProjectAmountDevideRefDao projectAmountDevideRefDao;
    @Resource
    private ProductProjectRefDao productProjectRefDao;
    @Resource
    private ContactInfoDao contactInfoDao;
    @Resource
    private CustomerInfoDao customerInfoDao;
    @Resource
    private ProjectDao projectDao;
    @Resource
    private BizOpportsDao bizOpportsDao;
    @Resource
    CustomerSaleRefDao customerSaleRefDao;
    @Resource
    ProjectAcquireCountDao acquireDao;
    @Resource
    SysEnvService sysEnvService;
    
    @Resource
    private EnterpriseTypeDao typeDao;

    @Resource
    SysEmployeeDao sysEmployeeDao;
    
    @Resource
    CustomerInfoService customerInfoService;
    
    @Resource
    BizOpportsService bizOpportsService;


    private IndustryInfoDao industryInfoDao;


    @Autowired
    public void setIndustryInfoDao(IndustryInfoDao industryInfoDao) {
        this.industryInfoDao = industryInfoDao;
    }
    
    /**
     * 批量更新项目相关的人
     * @param projectInfoUpdateDTO
     * @return
     */
    @Override
    public boolean batchpUdatePerson(ProjectSaleRefUpdateDTO projectInfoUpdateDTO, String userId) {
        Date date = new Date();
        
        List<String> projIds = projectInfoUpdateDTO.getProjectIdList();
        
        int c = 0;
        for (String pjId : projIds) {
          c += changeProjectSale(pjId, projectInfoUpdateDTO.getEmployeeId(), date);
        }
        
        return c > 0;
    }
    
    private int changeProjectSale(String pjId, String empId, Date date) {
      
      ProjectInfoGetOneDTO oldInfo = projectInfoDao.queryByProjectId(pjId);
      
      if (oldInfo == null) {
        return 0;
      }
      
      // 更新主负责人
      changeSale(pjId, empId, date);
      
      // 更新商机主负责人
      if (oldInfo.getBizOpportId() != null) {
        BizOpportsDTO bizDto = new BizOpportsDTO();
        bizDto.setId(oldInfo.getBizOpportId());
        bizDto.setEmployeeId(empId);
        bizDto.setModifiedTime(date);
        bizDto.setState(2);
        bizOpportsDao.update(bizDto);
      }
      
      // 变更负责人
      customerSaleRefDao.deleteRef(oldInfo.getCustomerId(), empId);
      CustomerSaleRef csref = new CustomerSaleRef();
      csref.setEmployeeId(empId);
      csref.setCustomerId(oldInfo.getCustomerId());
      csref.setCreateTime(date);
      csref.setLastTime(date);
      csref.setOperator(empId);
      customerSaleRefDao.insert(csref);
      
      return 1;
      
    }

    @Override
    public CustomerInfoInProjectDTO getCustomerByCid(String customerId) {
        return projectInfoDao.getCustomerByCid(customerId);
    }

    /**
     * 获取销售项目阶段
     * @param salesProjectStageDTO
     * @return
     */
    @Override
    public List<SalesProjectStageDTO> getSalesProjectStageList(SalesProjectStageDTO salesProjectStageDTO) {
        return projectInfoDao.getSalesProjectStageList(salesProjectStageDTO);
    }

    /**
     * 新增项目
     * @param projectInfoInsertDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(ProjectInfoInsertDTO projectInfoInsertDTO, LoginUserDTO loginUserDTO) {
      return insert(projectInfoInsertDTO, loginUserDTO, false);
    }
    
    public boolean insert(ProjectInfoInsertDTO projectInfoInsertDTO, LoginUserDTO loginUserDTO, boolean asChecked) {
        //准备需要设置进项目主体实体的字段信息
        String projectId = projectInfoInsertDTO.getProjectId();
        String userId = loginUserDTO.getUserId();
        Date date = new Date();
        projectInfoInsertDTO.setProjectId(projectId)
                            .setProsId(PROJECT_STATUS_WANNA)
                            .setOperator(userId)
                            .setCreateTime(date)
                            .setLastTime(date);

        projectInfoInsertDTO.setHasSimilarType(HAS_NO_SIMILAR_TYPE).setOverTimeFlag(OVER_TIME_FLAG_NO);
        
        int bid = projectInfoInsertDTO.getBizOpportId();
        
        BizOpports bizInfo = bizOpportsDao.queryById(Long.valueOf(bid));
        
        if (bizInfo == null) {
          throw new MsgException("商机不存在");
        }
        
        // 设置 id name cid
        // 不使用前端传过来的数据
        // 是因为前端穿过的数据有时候会扯拐
        projectInfoInsertDTO.setCustomerId(bizInfo.getCustomerId());
        projectInfoInsertDTO.setCustomerName(bizInfo.getCustomerName());
        projectInfoInsertDTO.setContactId(bizInfo.getContactId());
        
        
        

        //新增主表信息
        int insertProject = projectInfoDao.insert(projectInfoInsertDTO);
        boolean insertMiner = false;
        int insertProjectCheck = 0;
        int insertAmountDevide = 0;
        int insertProduct = 0;

        if (insertProject > 0) {

            //新增负责人信息
            insertMiner = projectSaleRefDao.insert(new ProjectSaleRef(null, projectId, projectInfoInsertDTO.getEmployeeId(), null, userId, date, date));

            // 更新商机为立项审核中
            BizOpportsDTO bizOpportsDTO = new BizOpportsDTO();
            bizOpportsDTO.setId(Long.valueOf(projectInfoInsertDTO.getBizOpportId()));
            bizOpportsDTO.setModifiedTime(date);
            // 2. 已立项
            // 3. 审核
            bizOpportsDTO.setState(asChecked ? 2 : 3);
            
            // 直接更细，忽略其他
            if (asChecked) {
              bizOpportsDao.update(bizOpportsDTO);
            } else {
              if (bizOpportsDao.checkAndUpdate(bizOpportsDTO, 1) < 1) {
                throw new MsgException("商机已立项，或者已删除");
              }
            }
            
            if (insertMiner) {

                //新增项目待审核信息
                insertProjectCheck = projectCheckInfoDao.insert(new ProjectCheckInfo(
                    null,
                    projectId, 
                    asChecked ? loginUserDTO.getUserId() : null,
                    null, 
                    // 默认审核通过
                    asChecked ? CheckStatus.YTG.getCode() : PROJECT_PRE_CHECK_STATUS,
                    date, date));

                if (insertProjectCheck > 0) {

                    //新增业绩分隔信息
                    BigDecimal estimateMoney = projectInfoInsertDTO.getEstimateMoney();
                    List<ProjectAmountDevideRefInsertDTO> amountDevideList = projectInfoInsertDTO.getProjectAmountDevideRefInsertDTOList();
                    amountDevideList.forEach(bean -> {
                        //将预估金额乘以对应百分比
                        BigDecimal multiply = estimateMoney.multiply((new BigDecimal(bean.getPerformanceDivision())).divide(new BigDecimal(100)));
                        bean.setDivisionAmount(multiply).setProjectId(projectId).setOperator(userId).setCreateTime(date).setLastTime(date);
                    });
                    insertAmountDevide = projectAmountDevideRefDao.batchInsert(amountDevideList);

                    if (insertAmountDevide > 0) {
                        //新增产品信息
                        List<ProductProjectRefInsertDTO> productRefInsertDTOList = projectInfoInsertDTO.getProductRefInsertDTOList();
                        productRefInsertDTOList.forEach(bean->bean.setProjectId(projectId).setOperator(userId).setCreateTime(date).setLastTime(date));
                        insertProduct = productProjectRefDao.batchInsert(productRefInsertDTOList);
                    }
                }
            }
        }

        boolean insertFlag = insertProject > 0 && insertMiner && insertProjectCheck > 0 && insertAmountDevide > 0 && insertProduct > 0;

        //插入项目跟踪的操作日志到周日报表的日报表类型中,因为没了contact_id，
        if (insertFlag && ObjTool.isNotNull(projectInfoInsertDTO.getContactId())
            && !asChecked
            ) {
            employeeReportInfoDao.insert(new EmployeeReportInfo().setEmployeeId(userId).setReportType(DAY_TYPE_REPORT).setReportTime(date).setProjectId(projectId)
                                                                 .setContactId(projectInfoInsertDTO.getContactId()).setOperationType(OPERATION_OPERATE_REPORT)
                                                                 .setFollowDetails(loginUserDTO.getEmployeeName() + "添加了名为【" + projectInfoInsertDTO.getProjectName() + "】的项目")
                                                                 .setOperator(userId).setLastTime(date).setCreateTime(date));
        }

        return insertFlag;
    }

    /**
     * 获取产品列表
     * @param productInfo
     * @return
     */
    @Override
    public List<ProductInfo> getProductInfoList(ProductInfo productInfo) {
        return projectInfoDao.getProductInfoList(productInfo);
    }


    /**
     * 获取某个员工的客户列表，除所传的客户编号之外
     * @param customerId
     * @return
     */
    @Override
    public List<CustomerInfo> getPartnersExludeByCustomerId(String customerId, String employeeId) {
        List<CustomerInfo> list = projectInfoDao.getPartnersExludeByCustomerId(customerId, employeeId);
        for (CustomerInfo customerInfo : list) {
            String customerIdGet = customerInfo.getCustomerId();
            if (ObjTool.isNotNull(customerIdGet)) {
                List<ContactInfo> contactInfoList = contactInfoDao.queryByCustomerId(customerIdGet);
                if (ObjTool.isNotNull(contactInfoList)) {
                    customerInfo.setContactList(contactInfoList);
                }
            }
        }
        return list;
    }

    /**
     * 新增项目
     * @param projectInfoUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(ProjectInfoUpdateDTO projectInfoUpdateDTO, LoginUserDTO loginUserDTO) {

        String projectId = projectInfoUpdateDTO.getProjectId();
        String userId = loginUserDTO.getUserId();

        //准备需要设置进项目主体实体的字段信息
        Date date = new Date();
        projectInfoUpdateDTO.setOperator(userId)
                                .setCreateTime(date)
                                .setLastTime(date);

            //删除历史业绩分割
            int dealAmountDevideFlag = projectAmountDevideRefDao.deleteByIds(projectId);

            //删除历史产品信息
            int dealProjectProductsFlag = productProjectRefDao.deleteByIds(projectId);

            // if (dealAmountDevideFlag>0 && dealProjectProductsFlag>0) {}


            //根据当前审核状态判断是否需要将状态改为待审批（仅当审批状态为大区总已驳回的时候，对这个项目进行修改的时候，需要把项目把状态修改为待审批）
            ProjectInfoGetOneDTO oldProj = projectInfoDao.queryByProjectId(projectId);
            if (ObjTool.isNotNull(oldProj.getCheckStatus()) && oldProj.getCheckStatus().equals(PROJECT_ZONE_TOP_REFUSE)) {
                projectCheckInfoDao.updateCheckStatus2UnCheck(projectId, PROJECT_PRE_CHECK_STATUS);
                
                // 更换了商机
                if (!projectInfoUpdateDTO.getBizOpportId().equals(oldProj.getBizOpportId())) {
                  BizOpportsDTO bizOpportsDTO = new BizOpportsDTO();
                  bizOpportsDTO.setModifiedTime(date);
                  if (oldProj.getBizOpportId() != null) {
                    // 恢复商机状态
                    bizOpportsDTO.setState(1);
                    bizOpportsDTO.setId(oldProj.getBizOpportId());
                    bizOpportsDao.update(bizOpportsDTO);
                  }
                  // 关联商机
                  bizOpportsDTO.setState(2);
                  bizOpportsDTO.setId(projectInfoUpdateDTO.getBizOpportId());
                  bizOpportsDao.update(bizOpportsDTO);
                } 
            }
            
            //修改主表信息
            int updateProject = projectInfoDao.update(projectInfoUpdateDTO);
            //修改负责人信息
            boolean updateMiner = projectSaleRefDao.update(new ProjectSaleRef(null, projectId, projectInfoUpdateDTO.getEmployeeId(), null, userId, date, null));

            //新增业绩分隔信息
            BigDecimal estimateMoney = projectInfoUpdateDTO.getEstimateMoney();
            List<ProjectAmountDevideRefUpdateDTO> amountDevideList = projectInfoUpdateDTO.getProjectAmountDevideRefInsertDTOList();
            amountDevideList.forEach(bean -> {
                //将预估金额乘以对应百分比
                BigDecimal multiply = estimateMoney.multiply((new BigDecimal(bean.getPerformanceDivision())).divide(new BigDecimal(100)));
                bean.setDivisionAmount(multiply).setProjectId(projectId).setOperator(userId).setCreateTime(date).setLastTime(date);
            });
            int devideAmountCountInsert = projectAmountDevideRefDao.batchInsertByUpdate(amountDevideList);

            //新增产品信息
            List<ProductProjectRefUpdateDTO> productRefUpdateDTOList = projectInfoUpdateDTO.getProductRefInsertDTOList();
            productRefUpdateDTOList.forEach(bean->bean.setProjectId(projectId).setOperator(userId).setCreateTime(date).setLastTime(date));
            int insertProductFlag = productProjectRefDao.batchInsertByUpdate(productRefUpdateDTOList);

            boolean updateFlag = updateProject > 0 && updateMiner && devideAmountCountInsert > 0 && insertProductFlag > 0;

            //插入项目跟踪的操作日志到周日报表的日报表类型中
            if (updateFlag && ObjTool.isNotNull(projectInfoUpdateDTO.getContactId())) {
                employeeReportInfoDao.insert(new EmployeeReportInfo().setEmployeeId(userId).setReportType(DAY_TYPE_REPORT).setReportTime(date).setProjectId(projectId)
                        .setContactId(projectInfoUpdateDTO.getContactId()).setOperationType(OPERATION_OPERATE_REPORT)
                        .setFollowDetails(loginUserDTO.getEmployeeName() + "修改了名为【" + projectInfoUpdateDTO.getProjectName() + "】的项目信息")
                        .setOperator(userId).setLastTime(date).setCreateTime(date));
            }

            return updateFlag;
        
    }

    @Override
    public ProjectInfoGetOneDTO get(String projectId) {
        ProjectInfoGetOneDTO projectInfoGetOneDTO = projectInfoDao.queryByProjectId(projectId);

        if (ObjTool.isNotNull(projectInfoGetOneDTO)) {
            ProjectSaleRef projectSaleRef = projectSaleRefDao.queryByProjectId(projectId);
            projectInfoGetOneDTO.setEmployeeId(projectSaleRef.getEmployeeId());
            List<ProductProjectRef> productProjectRefList = productProjectRefDao.querybyProjectId(projectId);
            List<ProjectAmountDevideRef> projectAmountDevideRefList = projectAmountDevideRefDao.querybyProjectId(projectId);
            projectInfoGetOneDTO.setProductProjectRefList(productProjectRefList).setProjectAmountDevideRefList(projectAmountDevideRefList);

            //客户信息查询
            if (ObjTool.isNotNull(projectInfoGetOneDTO.getCustomerId())) {
                Long contactId = projectInfoGetOneDTO.getContactId();
                projectInfoGetOneDTO = queryCustomerPartnerInfo(projectInfoGetOneDTO, projectInfoGetOneDTO.getCustomerId(), contactId, 1);
            }
            //合作伙伴信息查询
            if (ObjTool.isNotNull(projectInfoGetOneDTO.getPartnerId())) {
                Long partnerContactId = projectInfoGetOneDTO.getPartnerContactId();
                projectInfoGetOneDTO = queryCustomerPartnerInfo(projectInfoGetOneDTO, projectInfoGetOneDTO.getPartnerId(), partnerContactId, 2);
            }
            return projectInfoGetOneDTO;
        }
        return null;
    }

    /**
     * 查询单个项目的时候，查询合作伙伴和客户的信息方法
     * @param projectInfoGetOneDTO
     * @param customerId
     * @param contactId
     * @return
     */
    private ProjectInfoGetOneDTO queryCustomerPartnerInfo(ProjectInfoGetOneDTO projectInfoGetOneDTO, String customerId, Long contactId, Integer type) {
        //客户信息
        String customerName = customerInfoDao.queryCustomerNameByCustomerId(customerId);

        //联系人信息
        ContactInfo partnerContactInfo = null;
        if (ObjTool.isNotNull(contactId)) {
            partnerContactInfo = contactInfoDao.queryById(contactId);
        }

        if (type.equals(1)) {
            //设置最终客户和客户联系人信息
            projectInfoGetOneDTO.setCustomerName(customerName);
            if (ObjTool.isNotNull(partnerContactInfo)) {
                projectInfoGetOneDTO.setContactName(partnerContactInfo.getContactName())
                        .setContactDeptName(partnerContactInfo.getDeptName())
                        .setContactJob(partnerContactInfo.getJob())
                        .setContactTel(partnerContactInfo.getTelphone());
            }
        } else if (type.equals(2)) {
            //设置合作伙伴和合作伙伴联系人信息
            projectInfoGetOneDTO.setPartnerName(customerName);
            if (ObjTool.isNotNull(partnerContactInfo)) {
                projectInfoGetOneDTO.setPartnerContactName(partnerContactInfo.getContactName())
                        .setPartnerContactDeptName(partnerContactInfo.getDeptName())
                        .setPartnerContactTel(partnerContactInfo.getTelphone());
            }
        }
        return projectInfoGetOneDTO;
    }

    @Override
    public String getMaxProjectId() {
        return projectInfoDao.getMaxProjectId();
    }

    @Override
    public int countByProjectName(String projectName) {
        return projectInfoDao.countByProjectName(projectName);
    }

    
    /**
     * 第二个版本的详细项目规则
     * <ul>
     * <ol>客户名称相同</ol>
     * <ol>至少一个项目相同</ol>
     * </ul>
     * @return
     */
    private List<ProjectSimilarDTO> searchSimilarProjectV2(String projectId, String custId, List<Integer> productIdList) {
      // 获取客户名
      CustomerInfoQueryVo cvo = customerInfoDao.queryByCustomerId(custId);
      
      if (cvo == null) {
        return Collections.emptyList();
      }

      String name = cvo.getCustomerName();
      
      if (ObjTool.isNull(productIdList)) {
        return Collections.emptyList();
      }
      
      // 获取相似 project id
      List<String> pjids = projectInfoDao.searchSimilarProjectV2(projectId, name, productIdList);
      
      if (pjids == null || pjids.isEmpty()) {
        return Collections.emptyList();
      }
      
      // 返回相似
      return projectInfoDao.getSimilarProjects(pjids);
      
    }
    
    /**
     * 查询项目相似的项目
     * @param projectName
     * @return
     */
    @Override
    public List<ProjectSimilarDTO> searchSimilarProjectName(String projectId, String projectName, boolean useStrict) {
        int projectNameSize = projectName.length();
        List<String> similarProjectNames = new ArrayList<>(3);
        int caseType;
        if (projectNameSize <= 4 || useStrict) {
            caseType = 0;
        } else if (projectNameSize <= 6) {
            caseType = 1;
        } else if (projectNameSize <= 18) {
            caseType = 2;
        } else if (projectNameSize < 28) {
            caseType = 3;
        } else {
            caseType = 4;
        }

        //获取到需要被匹配的项目名称截取的字符串
        switch (caseType) {
            //直接使用项目名称去模糊匹配
            case 0:
                similarProjectNames.add(projectName);
                break;
            case 1:
                String fiProjectName = projectName.substring(0, 4);
                String seProjectName = projectName.substring(projectNameSize-4, projectNameSize);
                similarProjectNames.add(fiProjectName);
                similarProjectNames.add(seProjectName);
                break;
            //截取一半字符串去模糊匹配
            case 2:
                String preProjectName = projectName.substring(0, projectNameSize / 2);
                String serProjectName = projectName.substring(projectNameSize / 2, projectNameSize);
                similarProjectNames.add(preProjectName);
                similarProjectNames.add(serProjectName);
                break;
            //截取三分之一去模糊匹配
            case 3:
                String preTreeProjectName = projectName.substring(0, projectNameSize / 3);
                String midTreeProjectName = projectName.substring(projectNameSize / 3, projectNameSize * 2 / 3);
                String serTreeProjectName = projectName.substring(projectNameSize * 2 / 3, projectNameSize);
                similarProjectNames.add(preTreeProjectName);
                similarProjectNames.add(midTreeProjectName);
                similarProjectNames.add(serTreeProjectName);
                break;
            //截取四分之一
            case 4:
                String preFourProjectName = projectName.substring(0, projectNameSize / 4);
                String midFourProjectName = projectName.substring(projectNameSize / 4, projectNameSize / 2);
                String serFourProjectName = projectName.substring(projectNameSize / 2, projectNameSize * 3 / 4);
                String endFourProjectName = projectName.substring(projectNameSize * 3 / 4, projectNameSize);
                similarProjectNames.add(preFourProjectName);
                similarProjectNames.add(midFourProjectName);
                similarProjectNames.add(serFourProjectName);
                similarProjectNames.add(endFourProjectName);
                break;
            default:
                break;
        }

        return projectInfoDao.searchSimilarProjectName(projectId, similarProjectNames, useStrict);
    }

    /**
     * 查询最终客户是否相同
     * @param projectNameSimilarList
     * @return
     */
  /*  @Override
    public List<ProjectSimilarDTO> searchSimilarCustomer(String customerId, List<ProjectSimilarDTO> projectNameSimilarList) {
        //查询客户名称
        String customerName = customerInfoDao.queryCustomerNameByCustomerId(customerId);
        if (ObjTool.isNotNull(customerName)) {
            projectNameSimilarList.forEach(projectInfo -> {
                String similarCustomerName = projectInfo.getCustomerName();
                if (customerName.contains(similarCustomerName) || (ObjTool.isNotNull(similarCustomerName) && similarCustomerName.contains(customerName))) {
                } else {
                    projectInfo.setCustomerId(null).setCustomerName(null);
                }
            });
        } else {
            //没有查到新增项目相关的客户名称，则把相似列表中的客户相关信息都置空
            projectNameSimilarList.forEach(projectInfo -> projectInfo.setCustomerId(null).setCustomerName(null));
        }
        return projectNameSimilarList;
    }*/

    /*
    @Override
    public ProjectInfoInsertDTO setCustomerPartnerInfo(ProjectInfoInsertDTO projectInfoInsertDTO) {

        //查询客户联系人信息
        ContactInfo contactInfo = contactInfoDao.queryById(projectInfoInsertDTO.getContactId());
        projectInfoInsertDTO.setContactName(contactInfo.getContactName())
                .setContactJob(contactInfo.getJob())
                .setContactDeptName(contactInfo.getDeptName())
                .setContactTel(contactInfo.getTelphone());
        //查询合作伙伴联系人的信息
        if (ObjTool.isNotNull(projectInfoInsertDTO.getPartnerContactId())) {
            ContactInfo partnerContactInfo = contactInfoDao.queryById(projectInfoInsertDTO.getPartnerContactId());
            projectInfoInsertDTO.setPartnerContactName(partnerContactInfo.getContactName())
                    .setPartnerContactDeptName(partnerContactInfo.getDeptName())
                    .setPartnerContactTel(partnerContactInfo.getTelphone());
        }
        return projectInfoInsertDTO;
    }
    */

    @Override
    public List<ProjectSimilarDTO> searchSameProducts(List<Integer> productIdList , List<ProjectSimilarDTO> projectNameSimilarList) {
      
      Set<Integer> test = new HashSet<>(productIdList);
      
      return projectNameSimilarList.stream().filter(sto -> {
        List<ProductInfo> pil = sto.getProductInfoList();
        if (pil == null || pil.size() != test.size()) {
          return false;
        }
        return pil.stream().map(ProductInfo::getProductId).allMatch(test::contains);
      }).collect(Collectors.toList());
    }

    /**
     * 查询是否有疑似项目
     * @param projectName
     * @param customerId
     * @param productIdList
     * @return
     */
    @Override
    public List<ProjectSimilarDTO> getSimilarProjectList(String projectId, String projectName, boolean useStrict, String customerId, List<Integer> productIdList) {
        // 第一个版本
        //查询项目相似的项目
        // List<ProjectSimilarDTO> projectNameSimilarList = searchSimilarProjectName(projectId, projectName, useStrict);
        // if (ObjTool.isNotNull(projectNameSimilarList)) {
        //     //查询最终客户是否相同
        //     projectNameSimilarList = projectNameSimilarList.stream().filter(p -> customerId.equals(p.getCustomerId())).collect(Collectors.toList());
        //     if (ObjTool.isNotNull(projectNameSimilarList)) {
        //         //查询产品是否相同
        //         projectNameSimilarList = searchSameProducts(productIdList, projectNameSimilarList);
        //     }
        // }
      
      
      // 第二个版本，相似规则
      // 1. 客户名称完全一样
      // 2. 至少有一个产品
      
      List<ProjectSimilarDTO> projectNameSimilarList = searchSimilarProjectV2(projectId, customerId, productIdList);
      
      return projectNameSimilarList;
    }
    @Override
    public List<ProjectInfoQueryDTO> projectOverdueList(ProjectOverdueInfoDTO projectOverdueInfoDTO) {
        List<ProjectInfoQueryDTO> projectOverdueList = projectInfoDao.projectOverdueList(projectOverdueInfoDTO);
        return projectOverdueList;
    }
    @Override
    public int projectOverdueCount(ProjectOverdueInfoDTO projectOverdueInfoDTO) {
        int projectOverdueCount= projectInfoDao.projectOverdueCount(projectOverdueInfoDTO);
        return projectOverdueCount;
    }
    @Override
    @Transactional
    public boolean assignProject(String empId, List<String> projectIds, int mode){
      Date date = new Date();
      int cc = 0;
      
      for (String pjId : projectIds) {
        
        // 检查
        ProjectInfoGetOneDTO oldInfo = projectInfoDao.queryByProjectId(pjId);
        
        if (oldInfo == null) {
          logger.warn("项目 '" +  pjId + "' 不存在");
          continue;
        }
        
        int setCheckStatus = CheckStatus.WSP.getCode();
        
        String setCheckMsg = null;
        // 项目继续跟进
        //     需要专员审批
        // 资源池中的项目
        //     不需要审批
        //     每天有最大数限制
        if (mode == ProjectConstant.ASSGIN_PROJECT_KEEP) {
          
          if (oldInfo.getOverTimeFlag() != 0) {
            throw new MsgException("操作失败，项目信息已过期，请刷新后重试");
          }
          
          // TODO 检查是否可以跟进
          if(!empId.equals(oldInfo.getEmployeeId())) {
            throw new MsgException("操作失败，权限错误");
          }
          
          // 专员审批
          setCheckStatus = CheckStatus.DQZYSP.getCode();
          // 审批消息
          setCheckMsg = "申请持续跟进此项目";

        } else if (mode == ProjectConstant.ASSGIN_PROJECT_FOLLOW) {
          
          // 检查状态
          if (oldInfo.getOverTimeFlag() == 0) {
            throw new MsgException("操作失败，项目信息已过期，请刷新后重试");
          }
          
          // 审批状态
          setCheckStatus = CheckStatus.YTG.getCode();
          // 审批消息
          setCheckMsg = "认领资源池项目";
          java.sql.Date sdate = DateUtils.toSqlDate(date);
          // 一天不能超过这个数
          int max = sysEnvService.sales_daily_acquire_project_max.getValue();
          
          if (!(acquireDao.insertIgnore(new ProjectAcquireLog(empId, 1, sdate))
               || acquireDao.acquireOne(empId, sdate, max))) {
            throw new MsgException("认领次数过多，每天只能认领 " + max + " 个项目");
          }
          
          
        } else {
          throw new MsgException("un supported mode");
        }
        
        
        // 更新项目状态
        ProjectOverdueInfoDTO projectOverdueInfoDTO = new ProjectOverdueInfoDTO();
        projectOverdueInfoDTO.setCheckOverTimeFlag(oldInfo.getOverTimeFlag());
        projectOverdueInfoDTO.setProjectId(pjId);
        // 过期标识：正常跟进，
        // 状态：意向，
        // 创建时间：当前
        projectOverdueInfoDTO.setOverTimeFlag(ProjectConstant.OVER_TIME_FLAG_NO);
        projectOverdueInfoDTO.setPropsId(PROJECT_STATUS_WANNA);
        projectOverdueInfoDTO.setLastTime(date);
        
        // 检查位
        // 防止连续跟进，防止连续认领
        projectOverdueInfoDTO.setCheckOverTimeFlag(oldInfo.getOverTimeFlag());
        
        if (projectInfoDao.projectOverdueUpdate(projectOverdueInfoDTO)) {

          // 更新主负责人
          changeSale(pjId, empId, date);
          
          // 更新商机主负责人
          if (oldInfo.getBizOpportId() != null) {
            BizOpportsDTO bizDto = new BizOpportsDTO();
            bizDto.setId(oldInfo.getBizOpportId());
            bizDto.setEmployeeId(empId);
            bizDto.setModifiedTime(date);
            bizDto.setState(2);
            bizOpportsDao.update(bizDto);
          }
          
          // 变更负责人
          customerSaleRefDao.deleteRef(oldInfo.getCustomerId(), empId);
          CustomerSaleRef csref = new CustomerSaleRef();
          csref.setEmployeeId(empId);
          csref.setCustomerId(oldInfo.getCustomerId());
          csref.setCreateTime(date);
          csref.setLastTime(date);
          csref.setOperator(empId);
          customerSaleRefDao.insert(csref);
        
          
          // 添加一条审批信息
          projectCheckInfoDao.deleteByIds(pjId);
          cc += projectCheckInfoDao.insert(new ProjectCheckInfo(null, pjId, empId, setCheckMsg, setCheckStatus, date, date));
        } else {
          throw new MsgException("操作失败，项目信息已过期，请刷新后重试");
        }
      }
      return cc > 0;
    }
    
    @Override
    public void changeSale(String pjId, String empId, Date date) {
      projectSaleRefDao.deleteById(pjId);
      ProjectSaleRef psr = new ProjectSaleRef();
      psr.setCreateTime(date);
      psr.setEmployeeId(empId);
      psr.setLastTime(date);
      psr.setOperator(empId);
      psr.setProjectId(pjId);
      projectSaleRefDao.insert(psr);
    }
    
    @Override
    public boolean unassignProject(List<String> projectIds) {
      int cc = 0;
      for (String pjId : projectIds) {
        if (releaseOverdueProject(pjId, ProjectConstant.OVER_TIME_FLAG_DEPT)) {
          cc+= 1;
        }
      }
      return cc > 0;
    }

    @Transactional(propagation = Propagation.NESTED , rollbackFor = Exception.class)
    @Override
    public void projectTerminatedOverdueListTask() {
        try {
            //6个月
            Date projectCreateTime = DateUtils.addMouth(DateUtils.currentDate(),-6);
            //员工离职状态
            Integer JobStatus=2;
            //每次操作查询1000条数据
            int batchNum=1000;
            //离职人员 6个月已过期
            ProjectOverdueInfoDTO projectOverdueInfoDTO=new ProjectOverdueInfoDTO( );
            projectOverdueInfoDTO.setProjectCreateTime(projectCreateTime)
                    .setJobStatus(JobStatus)
                    .setPage(1)
                    .setRows(batchNum);
            Integer projectTerminatedOverdueCount = projectInfoDao.projectTerminatedOverdueCount(JobStatus,projectCreateTime);
            for (int i = 0; i < projectTerminatedOverdueCount; i+=batchNum) {
                List<ProjectInfo> projectTerminatedOverdueList = projectInfoDao.projectTerminatedOverdueList(projectOverdueInfoDTO);
                if(ObjTool.isNotNull(projectTerminatedOverdueList) ){
                    for(ProjectInfo projectInfo :projectTerminatedOverdueList){
                        String projectId =projectInfo.getProjectId();
                        projectSaleRefDao.deleteById(projectId);
                        projectCheckInfoDao.deleteByIds(projectId);
                        projectAmountDevideRefDao.deleteByIds(projectId);
                        productProjectRefDao.deleteByIds(projectId);
                        projectDao.deleteById(projectId);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("执行离职过期项目定时任务 出现异常",e);
        }
    }

    @Override
    public boolean updateProjectMiner(ProjectSaleRef projectSaleRef) {
        return projectSaleRefDao.update(projectSaleRef);
    }

    @Override
    public int terminatedNotOverdueCount(ProjectInfoQueryDTO projectInfoQueryDTO){
       return projectInfoDao.terminatedNotOverdueCount(projectInfoQueryDTO);
    }

    @Override
    public List<ProjectInfoQueryDTO> terminatedNotOverdueList(ProjectInfoQueryDTO projectInfoQueryDTO){
        return projectInfoDao.terminatedNotOverdueList(projectInfoQueryDTO);
    }
    
    @Override
    public List<Map<String, String>> gcOverdueProject(LocalDateTime timeline) {
      
      EnvValue<Boolean> gcEnableEnv = sysEnvService.project_gc_enable;
      if (!gcEnableEnv.getValue()) {
        logger.warn("未启用 '" + gcEnableEnv.getDescription() + "'");
        return Collections.emptyList();
      }
      
      List<Map<String, String>> ret = new LinkedList<>();
      
      ProjectOverdueInfoDTO query = new ProjectOverdueInfoDTO();
      // 清理个人项目
      int salesLimitM = sysEnvService.project_gc_sales_limit_month.getValue();
      LocalDateTime saleLimit = timeline.minusMonths(salesLimitM);
      // 已立项
      query.setCheckStatuses(new Integer[] {CheckStatus.YTG.getCode()});
      // 未逾期
      query.setOverTimeFlag(ProjectConstant.OVER_TIME_FLAG_NO);
      // 时间已经不允许
      query.setCheckDate(DateUtils.toDate(saleLimit));
      // 清理个人任务
      List<ProjectInfo> result = projectInfoDao.projectTerminatedOverdueList(query);
      for (ProjectInfo proj : result) {
        try {
          releaseOverdueProject(proj.getProjectId(), OVER_TIME_FLAG_DEPT);
          Map<String, String> msg = new HashMap<String, String>();
          msg.put("projectId", proj.getProjectId());
          msg.put("projectName", proj.getProjectName());
          msg.put("release", "释放个人逾期项目");
          ret.add(msg);
        } catch (Exception e) {
          logger.warn("清理个人逾期项目 {}-{} 失败：{}", proj.getProjectName(), proj.getProjectId(), e.getMessage());
        }
      }
      
      // 清理大区任务
      int deptsLimitD = sysEnvService.project_gc_depts_limit_day.getValue();
      LocalDateTime deptLimit = timeline.minusDays(deptsLimitD);
      
      query.setOverTimeFlag(ProjectConstant.OVER_TIME_FLAG_DEPT);
      query.setProjectCreateTime(DateUtils.toDate(deptLimit));
      result = projectInfoDao.projectTerminatedOverdueList(query);
      for (ProjectInfo proj : result) {
        try {
          releaseOverdueProject(proj.getProjectId(), OVER_TIME_FLAG_ALL);
          Map<String, String> msg = new HashMap<String, String>();
          msg.put("projectId", proj.getProjectId());
          msg.put("projectName", proj.getProjectName());
          msg.put("release", "释放大区逾期项目");
          ret.add(msg);
        } catch (Exception e) {
          logger.warn("清理大区逾期项目 {}-{} 失败：{}", proj.getProjectName(), proj.getProjectId(), e.getMessage());
        }
      }
      return ret;
    }
    
    @Override
    public boolean releaseOverdueProject(String pjId, int releaseTo) {
      if (releaseTo == ProjectConstant.OVER_TIME_FLAG_DEPT
           || releaseTo == ProjectConstant.OVER_TIME_FLAG_ALL) {
        ProjectInfoQueryDTO p = new ProjectInfoQueryDTO();
        p.setProjectId(pjId);
        p.setLastTime(new Date());
        p.setOverTimeFlag(releaseTo);
        return projectDao.update(p) > 0;
      } else {
        throw new IllegalArgumentException("错误的资源池代码: " + releaseTo);
      }
    
    }
    
    @Override
    @Transactional
    public boolean salesUpdate(SalesUpdateVO updateVo, String empId) {
      
      
      Date now = new Date();
      
      // 产品 ID
      String projectId = updateVo.getProjectId();
      
      ProjectInfoUpdateDTO updto = new ProjectInfoUpdateDTO();
      
      updto.setProjectId(updateVo.getProjectId());
      updto.setCustomerProjectPhase(updateVo.getCustomerProjectPhase());
      updto.setSpstageId(updateVo.getSpstageId());
      updto.setOperator(empId);
      
      updto.setEstimateSuccess(updateVo.getEstimateSuccess());
      updto.setEstimateTime(updateVo.getEstimateTime());
      
      updto.setLastTime(now);
    
      
      // 新增产品信息
      List<ProductProjectRefUpdateDTO> productRefUpdateDTOList = updateVo.getProductRefInsertDTOList();
      BigDecimal amount = new BigDecimal(0);
      for (ProductProjectRefUpdateDTO bean : productRefUpdateDTOList) {
        bean.setProjectId(projectId).setOperator(empId).setCreateTime(now).setLastTime(now);
        amount = amount.add(bean.getAmount());
      }
      if (!productRefUpdateDTOList.isEmpty()) {
        //删除历史产品信息
        productProjectRefDao.deleteByIds(updateVo.getProjectId());
        productProjectRefDao.batchInsertByUpdate(productRefUpdateDTOList);
      }
      
      // 更新产品预估金额
      updto.setEstimateMoney(amount);
      
      // 更改分成信息
      final BigDecimal estimateMoney = amount;
      List<ProjectAmountDevideRefUpdateDTO> amountDevideList = projectAmountDevideRefDao.querybyProjectId(projectId)
          .stream().map(dref -> {
            ProjectAmountDevideRefUpdateDTO dup = new ProjectAmountDevideRefUpdateDTO();
            BeanUtils.copyProperties(dref, dup, "id");
            //将预估金额乘以对应百分比
            BigDecimal multiply = estimateMoney.multiply((new BigDecimal(dup.getPerformanceDivision())).divide(new BigDecimal(100)));
            dup.setDivisionAmount(multiply).setProjectId(projectId).setOperator(empId).setCreateTime(now).setLastTime(now);
            return dup;
          }).collect(Collectors.toList());
      // 更新
      if (!amountDevideList.isEmpty()) {
        // 分割信息
        projectAmountDevideRefDao.deleteByIds(projectId);
        projectAmountDevideRefDao.batchInsertByUpdate(amountDevideList);
      }
      
      return projectInfoDao.update(updto) > 0;
    }
    
    @Override
    @Transactional
    public void importProjects(Workbook workbook, LoginUserDTO loginUserDTO) {
      
      Date now = new Date();
      
      LinkedList<ProjectExcelVO> imports = new LinkedList<ProjectExcelVO>();
      validateExcel(workbook, imports);
      
      for (ProjectExcelVO pevo : imports) {
        CustomerInfo custInfo = pevo.getCustomerInfo();
        CustomerInfo patnInfo = pevo.getPattnerInfo();
        BizOpportsDTO bizInfo = pevo.getBizOpportInfo();
        ProjectInfoInsertDTO projectInfoInsertDTO = pevo.getProjectInfo();
        
        // 新增伙伴
        if (patnInfo != null) {
          customerInfoService.importInsertCustomer(
              patnInfo, 
              Arrays.asList(projectInfoInsertDTO.getEmployeeId()), 
              patnInfo.getContactList().get(0), 
              loginUserDTO.getUserId(), 
              now, false);
        }
        
        // 新增客户
        // 商机已存在，则已已存在的商机为准
        Long bizId = bizInfo.getId();
        if (bizId == null) {
          custInfo.setEmployeeId(pevo.getEmployeeId());
          String custId = customerInfoService.importInsertCustomer(
              custInfo, 
              Arrays.asList(projectInfoInsertDTO.getEmployeeId()), 
              custInfo.getContactList().get(0), 
              loginUserDTO.getUserId(), 
              now, false);
          
          bizInfo.setCustomerId(custId);
          bizInfo.setEmployeeId(pevo.getEmployeeId());
          bizInfo.setContactId(custInfo.getContactList().get(0).getId());
          bizId = bizOpportsService.insert(bizInfo, loginUserDTO.getUserId());
        }
        
        // 取商机的客户ID
        // projectInfoInsertDTO.setCustomerId(bizInfo.getCustomerId());
        // 取商机的联系人ID
        // projectInfoInsertDTO.setContactId(bizInfo.getContactId());
        
        if (patnInfo != null) {
          projectInfoInsertDTO.setPartnerId(patnInfo.getCustomerId());
          projectInfoInsertDTO.setPartnerContactId(patnInfo.getContactList().get(0).getId());
        }
        
        projectInfoInsertDTO.setImportType(1);
        projectInfoInsertDTO.setBizOpportId(bizId.intValue());
        
        // 新增项目
        insert(projectInfoInsertDTO, loginUserDTO, true);
      }
    }
    
    @Override
    public List<ProjectExcelVO> exporProjects(ImmutableSet<String> copyOf, String userId) {
      List<ProjectExcelVO> dd = projectInfoDao.getExportDatas(userId, copyOf);
      
      
      
      return dd;
    }




    @Override
    public List<String> validateExcel(Workbook workbook) {

      return validateExcel(workbook, null);
    }
    
    public List<String> validateExcel(Workbook workbook, List<ProjectExcelVO> collector) {
      
      Sheet sheet = getSheet(workbook, 0);
      int rows = ExcelUtils.getRowNum(sheet);
      
      if (rows < 2) {
        throw new MsgException("文档内容为空");
      }
      
      List<String> questions = new ArrayList<>();
      
      int quesMark = questions.size();
      
      Map<String, Integer> pjidCache = new HashMap<>();
      Map<String, Integer> bizIdCache = new HashMap<>();
      
      for (int i = 2; i < rows; i++) {
        
        Row row = ExcelUtils.getRow(sheet, i);
        
        
        boolean end = true;
        
        for (int t = 0; t < row.getLastCellNum(); t++) {
          if (row.getCell(t, MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
            end = false;
            break;
          }
        }
        
        if (questions.size() > 1000) {
          if (sheet.getLastRowNum() > i) {
            questions.add("...忽略剩余数据 " + (i + 1) + " ~ " + sheet.getLastRowNum());
          }
          end = true;
        }
        
        if (end) {
          break;
        }
        
        
        int offset = 0;
        
        ProjectExcelVO pevo = validateProject(row, offset, ProjectExcelVO.prdCellLength(), questions);
        
        offset += ProjectExcelVO.prdCellLength();
        
        BizOpportsExport biz = validateBizOpport(row, offset, ProjectExcelVO.bizCellLength(), questions, pevo);
        
        offset += ProjectExcelVO.bizCellLength();
        
        ExportCustomerInfoQueryVo cust = validateCustomer(row, offset, ProjectExcelVO.custCellLength(), questions, false);
        
        offset += ProjectExcelVO.custCellLength();;
        
        ExportCustomerInfoQueryVo patn = validateCustomer(row, offset, ProjectExcelVO.custCellLength(), questions, true);
        
        offset += ProjectExcelVO.custCellLength();
        
        ProjectExcelApplyVO apply = validateProjectApply(row, offset, ProjectExcelVO.applyCellLength(), questions);
        
        
        Integer existsPjNum = pjidCache.get(pevo.getProjectId());
        
        if(existsPjNum != null) {
          questions.add((row.getRowNum() + 1) + "行 表格中已存在 " + pevo.getProjectId() + " 的立项信息，位置 " + existsPjNum + " 行");
        } else {
          pjidCache.put(pevo.getProjectId(), row.getRowNum() + 1);
        }
        
        if (biz.getId() != null) {
          
          Integer existsBizNum =  bizIdCache.get(biz.getBizId());
          
          if(existsBizNum != null) {
            questions.add((row.getRowNum() + 1) + "行 表格中存在商机" + biz.getBizId() + "重复立项情况，位置 " + existsBizNum + " 行");
          } else {
            bizIdCache.put(biz.getBizId(), row.getRowNum() + 1);
          }
          
        }
        
        
        
        if (collector != null
            && quesMark == questions.size()
            ) {
          pevo.setBiz(biz);
          
          EmployeeVo evo = new EmployeeVo();
          evo.setEmployeeId(pevo.getEmployeeId());
          evo.setEmployeeName(pevo.getEmployeeName());
          
          List<EmployeeVo> evoList = Arrays.asList(evo);
          cust.setEmployeeVoList(evoList);
          pevo.setCust(cust);
          
          if (patn != null) {
            patn.setEmployeeVoList(evoList);
            pevo.setPatn(patn);
          }
          
          pevo.setApply(apply);
          
          collector.add(pevo);
        }
        quesMark = questions.size();
      }
      
      return questions;
    }
    
    static final Pattern PROJ_ID_PATN = Pattern.compile("SFWY-\\d{6}-\\d{4}");
    
    public ProjectExcelVO validateProject(Row row, int offset, int length, List<String> questions) {

      int rowNum = row.getRowNum() + 1;
      
      List<String> cellList = getCellString(row, offset, length);
      
      int index = 0;
      
      ProjectExcelVO pevo = new ProjectExcelVO();
      
      // 项目编号
      String headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String pjid = cellList.get(index++);
      
      
      if (StringUtils.isEmpty(pjid)) {
        questions.add((rowNum) + "行 " + headerName +"不能为空");
      } else {
        
        
        
        
        if (!PROJ_ID_PATN.matcher(pjid).matches()) {
          questions.add((rowNum) + "行 " + headerName +"格式错误，请使用 SFWY-XXXXXX-XXXX（四位年2位月-4位随机码）");
        } else if (projectInfoDao.isExists(pjid)) {
          questions.add(rowNum + "行 项目" + pjid + "已存在");
        } else {
          pevo.setProjectId(pjid);
        }
      }
      
      // 项目名称
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String pjname = cellList.get(index++);
      if (StringUtils.isEmpty(pjname)) {
        questions.add((rowNum) + "行 " + headerName + "不能为空");
      } else {
        pevo.setProjectName(pjname);
      }
      
      //验证省市区
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String provincial = cellList.get(index++);
      if (StringUtils.isEmpty(provincial)) {
        questions.add((rowNum) + "行 " + headerName +"不能为空");
      } else {
        if (!StringUtils.isProvincial(provincial)) {
          questions.add((rowNum) + "行 " + headerName + "格式有误,例: 四川省/成都市/锦江区(区域可选)");
        }
        
        pevo.setLocation(provincial);
      }
      
      //验证行业
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String business = cellList.get(index++);
      if(StringUtils.isEmpty(business)) {
        questions.add((rowNum) + "行 " + headerName +"不能为空");
      }else {
          if(industryInfoDao.findIndustryByName(business)==null){
              questions.add((rowNum) + "行 " + headerName + "不支持");
              // throw new MsgException((row) + "行 单位所属行业不支持:");
          }
//        if (!UtilMethod.BUSINESS_LIST.contains(business)) {
//          questions.add((rowNum) + "行 单位所属行业不支持");
//        }
      else {
          pevo.setIndustry(business);
        }
      }
      
      // 验证立项时间
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String checkDate = cellList.get(index++);
      if (StringUtils.isEmpty(checkDate)) { //生日
        questions.add((rowNum) + "行 " + headerName + "不能为空");
      } else {
        final Pair<Boolean, Date> correctDate = StringUtils.correctDate(checkDate);
        if (!correctDate.getKey()) {
          questions.add((rowNum) + "行 " + headerName + "格式不正确，请使用 yyyy/MM/dd");
        } else {
          pevo.setCheckTime(correctDate.getValue());
        }
      }
      
      //员工姓名
      String employeeName = cellList.get(index++);
      //工号
      String employeeId = cellList.get(index+++offset);
      if(validateUser(employeeId, employeeName, questions, rowNum)) {
        pevo.setEmployeeId(employeeId);
        pevo.setEmployeeName(employeeName);
      }
      
      // 所在大区
      index++;
      
      // 总投资金额
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String totalInvestment = cellList.get(index++);
      try {
        BigDecimal tt = new BigDecimal(totalInvestment);
        
        if (tt.compareTo(BigDecimal.ZERO) < 0 || tt.compareTo(new BigDecimal(999999)) > 0) {
          questions.add(rowNum + "行 " + headerName + "配置错误");
        } else {
          
          pevo.setTotalInvestment(tt.setScale(4, BigDecimal.ROUND_HALF_UP));
        }
        
      } catch (Exception e) {
        questions.add(rowNum + "行 " + headerName + "格式错误");
      }
      
      
      // 客户项目状态
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String custState = cellList.get(index++);
      if (StringUtils.isEmpty(custState)) {
        questions.add((rowNum) + "行 " + headerName + "不能为空: ");
      } else {
        
        try {
          CustomerStage.valueOf(custState);
          pevo.setCustomerProjectPhase(custState);
        } catch(Exception e) {
          questions.add((rowNum) + "行 " + headerName + "错误，请使用" + Arrays.asList(CustomerStage.values()));
        }
        
      }
      
      // 销售状态
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String saleState = cellList.get(index++);
      if (StringUtils.isEmpty(saleState)) {
        questions.add((rowNum) + "行 " + headerName + "不能为空: ");
      } else {
        try {
          SalesStage t = SalesStage.valueOf(saleState.replace('%', ' ').trim());
          pevo.setSpstageName(t.toString());
        } catch (Exception e) {
          questions.add((rowNum) + "行 " + headerName +"错误，请使用" + Arrays.asList(SalesStage.values()));
        }
      }

      // 预计签约时间
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String estDate = cellList.get(index++);
      if (StringUtils.isEmpty(estDate)) { //生日
        questions.add((rowNum) + "行 " + headerName + "不能为空:");
      } else {
        final Pair<Boolean, Date> correctDate = StringUtils.correctDate(estDate);
        if (!correctDate.getKey()) {
          questions.add((rowNum) + "行 " + headerName +"格式不正确，请使用 yyyy/MM/dd");
        } else {
          pevo.setEstimateTime(correctDate.getValue());
        }
      }

      // 签约概率
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String estGuess = cellList.get(index++);
      if (StringUtils.isEmpty(estGuess)) {
        questions.add((rowNum) + "行 " + headerName + "不能为空: ");
      } else {
        try {
          
          int estGuessInt = Integer.parseInt(estGuess);
          
          if (estGuessInt > 0 && estGuessInt < 101) {
            pevo.setEstimateSuccess(Integer.parseInt(estGuess));
          } else {
            
            questions.add(rowNum + "行 " + headerName + "概率值应为 1 ~ 100");
          }
          
        } catch (Exception e) {
          questions.add(rowNum + "行 " + headerName + "格式错误");
        }
      }
      
      // 预计合同金额
      // 需要自行计算
      index++;
//      String contractMoney = cellList.get(index++);
//      try {
//        BigDecimal tt = new BigDecimal(contractMoney);
//        
//        if (tt.compareTo(BigDecimal.ZERO) < 0) {
//          questions.add(rowNum + "行 预计合同金额配置错误");
//        } else {
//          
//          pevo.setEstimateMoney(tt);
//        }
//        
//      } catch (Exception e) {
//        questions.add("行 预计合同金额格式错误");
//      }
      
      // 产品
      String[] prdp = new String[] {"数量", "单价", "小计"};
      
      for (String prd : ProjectExcelVO.PRDS.keySet()) {
        
        String prdName = ProjectExcelVO.PRDS.get(prd).getName();
        
        ProductExcelVO prdvo = new ProductExcelVO();
        
        int i  = 0;
        Integer tcnt = 0;
        try {
          String cnt = cellList.get(index++);
          tcnt = StringUtils.isEmpty(cnt) ? 0 : Integer.parseInt(cnt);
          
          if(tcnt < 0 || tcnt > 999999) {
            questions.add(rowNum + "行 " + prdName + prdp[i] + "配置错误");
          } else {
            
            prdvo.setSaleCount(tcnt);
          }
          
        } catch (Exception e) {
          questions.add(rowNum + "行 " + prdName + prdp[i] + "配置错误");
        }
        
        BigDecimal tprice = BigDecimal.ZERO;
        try {
          i++;
          String price = cellList.get(index++);
          tprice =  StringUtils.isEmpty(price) ? BigDecimal.ZERO :new BigDecimal(price);
          
          if(tprice.compareTo(BigDecimal.ZERO) < 0 || tprice.compareTo(new BigDecimal(999999)) > 0) {
            questions.add(rowNum + "行 " + prdName + prdp[i] + "配置错误");
          } else {
            
            prdvo.setAmount(tprice.setScale(4, BigDecimal.ROUND_HALF_UP));
          }
          
          
        } catch (Exception e) {
          questions.add(rowNum + "行 " + prdName + prdp[i] + "配置错误");
        }
        index++;
        
        if (tprice.multiply(new BigDecimal(tcnt)).equals(BigDecimal.ZERO)
            && tprice.add(new BigDecimal(tcnt)).compareTo(BigDecimal.ZERO) > 0) {
          if (tcnt == 0) {
            questions.add(rowNum + "行 " + prdName + prdp[0] + "配置错误");
          } else {
            questions.add(rowNum + "行 " + prdName + prdp[1] + "配置错误");
          }
        }
        
        
        
        pevo.setPrd(prd, prdvo);
        
        // BigDecimal tamount = null;
        // 核算小计
        // try {
        //   i++;
        //   String amount = cellList.get(index++);
        //   tamount = StringUtils.isEmpty(amount) ? BigDecimal.ZERO :new BigDecimal(amount);
        //   prdvo.setAmount(tamount);
        // } catch (Exception e) {
        //   questions.add(rowNum + "行 " + prd + prdp[i] + "配置错误");
        // }
        
        // if (tamount != null && tprice != null && tcnt != null) {
        //   if (!tamount.equals(tprice.multiply(new BigDecimal(tcnt)))) {
        //     questions.add(rowNum + "行 " + prd + prdp[i] + "计算错误");
        //   } else {
        //     pevo.setPrd(prd, prdvo);
        //   }
        // }
      }
      
      if (!pevo.containsAnyPrd()) {
        questions.add(rowNum + "行 至少包含一个产品的销售信息");
      }
      
      // 业绩分割比例
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String div = cellList.get(index++);
      if (StringUtils.isEmpty(div)) {
        questions.add((rowNum) + "行 " + headerName +"不能为空:");
      } else {
        String fdvi = div.replaceAll("；", ";").replaceAll("，", ",");
        String[] vs = fdvi.split(";");
        
        int mtt = 0;
        for (String v : vs) {
          String ffv = v.trim();
          if (ffv.isEmpty()) {
            continue;
          }
          String[] divv = ffv.split(",");
          if (divv.length != 3) {
            questions.add((rowNum) + "行 " + headerName + "格式错误，请使用\"销售人员,销售人员工号,分割比例\" 格式，多个业绩分割使用 ; 间隔");
            break;
          }
          
          String empName = divv[0];
          String empId = divv[1];
          
          // 验证用户
          validateUser(empId, empName, questions, rowNum);
          
          try {
            int tt = Integer.parseInt(divv[2].trim());
            
            if (tt < 1 || tt > 999999) {
              throw new IllegalArgumentException();
            }
            mtt += tt;
          } catch (Exception e) {
            questions.add((rowNum) + "行 " + headerName + "必须为整数，取值区间 1 - 999999");
            break;
          }
        }
        if (mtt != 100) {
          
          questions.add(rowNum + "行 " + headerName + "业绩分割占比总和应为 100");
          
        } else {
          pevo.setDvi(fdvi);
        }
      }

      // 是否终止跟进
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String keep = cellList.get(index++);
      if (!StringUtils.isEmpty(keep)) {
        if (!("是".equals(keep) || "否".equals(keep))) {
          questions.add((rowNum) + "行 " + headerName + "格式不正确");
        } else {
          // 后期扩展字段
          // pevo.setHasImportant(isImportant);
        }
      }
      
      // 是否签单
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String qiandan = cellList.get(index++);
      if (!StringUtils.isEmpty(qiandan)) {
        if (!("是".equals(qiandan) || "否".equals(qiandan))) {
          questions.add((rowNum) + "行 " + headerName + "格式不正确");
        } else {
          // 后期扩展字段
          // pevo.setHasImportant(isImportant);
        }
      }
      
      // 备注
      // pevo.setProjectSituation(cellList.get(index++));
      
      // 是否重大关键
      headerName = ProjectExcelVO.PROJ_HEADERS.get(index);
      String isImportant = cellList.get(index++);
      if (StringUtils.isEmpty(isImportant)) {
        questions.add((rowNum) + "行 " + headerName + "不能为空");
      } else {
        if (!("是".equals(isImportant) || "否".equals(isImportant))) {
          questions.add((rowNum) + "行 " + headerName + "格式不正确");
        } else {
          pevo.setHasImportant(isImportant);
        }
      }
      
      return pevo;
    }
    
    
    public BizOpportsExport validateBizOpport(Row row, int offset, int length, List<String> questions, ProjectExcelVO pevo) {
      
      List<String> cellList = getCellString(row, offset, length);
      BizOpportsExport biz = new BizOpportsExport();
      int rowNum = row.getRowNum() + 1;
      
      int index = 0;
      
      
      //商机ID
      String headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String bizId = cellList.get(index++);
      
      biz.setBizId(bizId);
      
      String empId = pevo.getEmployeeId();
      if (empId != null ) {
        biz.setEmployeeId(empId);
        if (!bizOpportsService.checkColudImport(biz)) {
          questions.add(rowNum + "行 商机权限错误或者商机已立项"); 
        }
      }
      
      
      
      //商机名称
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String name = cellList.get(index++);
      
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 " + headerName + "商机名称不能为空");
      } else {
        biz.setName(name);
      }
      
      //提交人
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String username = cellList.get(index++);
      //提交人工号
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String userId = cellList.get(index++);
      if(validateUser(userId, username, questions, rowNum)) {
        biz.setCreateId(userId);
        biz.setCreateDeptName(username);
      }
      
      
      
      //提交人所属大区
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String ggg = cellList.get(index++);
      biz.setEmployeeDeptName(ggg);
      
      //商机状态
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String state = cellList.get(index++);
      if (ObjTool.isNotNull(state)){
        if ("未立项".equals(state) ||
            "已立项".equals(state) ||
            "立项审核中".equals(state)){
          biz.setStateName(state);
        } else {
          questions.add(rowNum + "行 " + headerName +"格式错误");
        }
      }
      
      //销售商品
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String keywords = cellList.get(index++);
      if (StringUtils.isEmpty(keywords)) {
        questions.add(rowNum + "行 " + headerName + "不能为空");
      } else {
        biz.setKeywords(keywords);
      }
      
      //商机概述
      headerName = ProjectExcelVO.BIZ_HEADERS.get(index);
      String desc = cellList.get(index++);
      
      if (StringUtils.isEmpty(desc)) {
        questions.add(rowNum + "行" + headerName + "不能为空");
      } else {
        biz.setDesc(desc);
      }
      
      
      return biz;
    }
    

    public ExportCustomerInfoQueryVo validateCustomer(Row row, int offset, int length, List<String> questions, boolean optional) {

      int rowNum = row.getRowNum() + 1;
      
      ExportCustomerInfoQueryVo vo = new ExportCustomerInfoQueryVo();
      
      List<String> cellList = getCellString(row, offset, length);
      
      
      if (optional
          &&( cellList.isEmpty()
          || cellList.stream().allMatch(StringUtils::isEmpty)
          )) {
        return null;
      }
      
      offset = 0;

      String customerName = cellList.get(CUSTOMER_NAME + offset);
      String headerName = ProjectExcelVO.CUST_HEADERS.get(CUSTOMER_NAME + offset);
      //验证名称
      if (StringUtils.isEmpty(customerName)) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不能为空: ");
      } else {
        vo.setCustomerName(customerName);
      }
      
      //验证省市区
      headerName = ProjectExcelVO.CUST_HEADERS.get(PROVINCIAL + offset);
      String provincial = cellList.get(PROVINCIAL + offset);
      if (StringUtils.isEmpty(provincial)) {
        questions.add(rowNum + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName +"不能为空");
      } else {
        if (!StringUtils.isProvincial(provincial)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "格式有误,例: 四川省/成都市/锦江区(区域可选)");
        } else {
          vo.setProvincial(provincial);
        }
      }

      //验证性质
      headerName = ProjectExcelVO.CUST_HEADERS.get(ENTER_NAME + offset);
      String nature = cellList.get(ENTER_NAME + offset);
      if (!NATURE_LIST.contains(nature)) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName +"取值应为: " + JSONObject.toJSON(NATURE_LIST));
      } else {
        vo.setEnterName(nature);
      }

      //验证行业
      headerName = ProjectExcelVO.CUST_HEADERS.get(INDUSTRY + offset);
      String business = cellList.get(INDUSTRY + offset);

        if(industryInfoDao.findIndustryByName(business)==null){
            questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不支持");
        }

//      if (!UtilMethod.BUSINESS_LIST.contains(business)) {
//        questions.add((rowNum) + "行 单位所属行业不支持:");
//      }
      else {
        vo.setIndustry(business);
      }

      //地址
      headerName = ProjectExcelVO.CUST_HEADERS.get(ADDRESS + offset);
      String address = cellList.get(ADDRESS + offset);
      if (StringUtils.isEmpty(address)) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不能为空: ");
      } else {
        vo.setAddress(address);
      }
      
      // 没错就是这么干
      offset -= 2;

      //单位座机
      headerName = ProjectExcelVO.CUST_HEADERS.get(TELEPHONE + offset);
      String phoneNumber = cellList.get(TELEPHONE + offset);
      if (!StringUtils.isEmpty(phoneNumber)) {
        if (!StringUtils.isPhoneNumber(phoneNumber)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "格式不正确");
        } else {
          vo.setTelephone(phoneNumber);
        }
      }

      //邮编
      headerName = ProjectExcelVO.CUST_HEADERS.get(ZIP_CODE + offset);
      String postCode = cellList.get(ZIP_CODE + offset);
      if (!StringUtils.isEmpty(postCode)) {
        if (!StringUtils.isPostCode(postCode)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "格式不正确");
        } else {
          vo.setZipCode(postCode);
        }
      }

      //网址
      headerName = ProjectExcelVO.CUST_HEADERS.get(WEBSITE + offset);
      String netUrl = cellList.get(WEBSITE + offset);
      if (!StringUtils.isEmpty(netUrl)) {
        if (!StringUtils.isNetUrl(netUrl)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName+ "不正确");
        } else {
          vo.setWebsite(netUrl);
        }
      }

      //传真
      headerName = ProjectExcelVO.CUST_HEADERS.get(FAX + offset);
      String faxNumber = cellList.get(FAX + offset);
      if (!StringUtils.isEmpty(faxNumber)) {
        if (!StringUtils.isPhoneNumber(faxNumber)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "格式不正确");
        } else {
          vo.setFax(faxNumber);
        }
      }

      //姓名
      String contactName = cellList.get(CONTACT_NAME + offset);
      headerName = ProjectExcelVO.CUST_HEADERS.get(CONTACT_NAME + offset);
      if (StringUtils.isEmpty(contactName)) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "名必输");
      } else {
        vo.setContactName(contactName);
      }

      //contactName,

      //性别
      String gender = cellList.get(GENDER + offset);
      headerName = ProjectExcelVO.CUST_HEADERS.get(GENDER + offset);
      GenderEnum genderEnum = GenderEnum.convertName(gender);
      if (genderEnum == null) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "必须是(男|女)");
      } else {
        vo.setGender(gender);
      }

      //部门
      headerName = ProjectExcelVO.CUST_HEADERS.get(DEPT_NAME + offset);
      String deptName = cellList.get(DEPT_NAME + offset);
      if (!StringUtils.isEmpty(deptName)) {
        vo.setDeptName(deptName);
      }

      //职务
      headerName = ProjectExcelVO.CUST_HEADERS.get(JOB + offset);
      String position = cellList.get(JOB + offset);
      if (StringUtils.isEmpty(position)) {
        questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "职位必填");
      } else {
        vo.setJob(position);
      }

      //手机号码
      headerName = ProjectExcelVO.CUST_HEADERS.get(CONTACT_TELPHONE + offset);
      String telephoneNumber = cellList.get(CONTACT_TELPHONE + offset);
      if (StringUtils.isEmpty(telephoneNumber)) {
        questions.add(rowNum + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不能为空");
      } else {
        if (!StringUtils.isPhone(telephoneNumber)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不正确");
        } else {
          vo.setContactTelphone(telephoneNumber);
        }
      }

      //邮箱
      headerName = ProjectExcelVO.CUST_HEADERS.get(CONTACT_EMAIL + offset);
      String email = cellList.get(CONTACT_EMAIL+ offset);
      if (!StringUtils.isEmpty(email)) {
        if (!StringUtils.isEmail(email)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "不正确");
        } else {
          vo.setContactEmail(email);
        }
      }

      //办公电话
      headerName = ProjectExcelVO.CUST_HEADERS.get(OFFICE_PLANE + offset);
      String officePhone = cellList.get(OFFICE_PLANE + offset);
      if (!StringUtils.isEmpty(officePhone)) {
        if (!StringUtils.isPhoneNumber(officePhone)) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "联系人座机格式不正确");
        } else {
          vo.setOfficePlane(officePhone);
        }
      }
      // 毕业学校
      headerName = ProjectExcelVO.CUST_HEADERS.get(UNIVERSITY + offset);
      String univ = cellList.get(UNIVERSITY + offset);
      if(!StringUtils.isEmpty(univ)) {
        vo.setUniversity(univ);
      }
      
      // 专业
      headerName = ProjectExcelVO.CUST_HEADERS.get(MAJOR + offset);
      String major = cellList.get(MAJOR + offset);
      if(!StringUtils.isEmpty(major)) {
        vo.setUniversity(major);
      }
      
      //生日
      headerName = ProjectExcelVO.CUST_HEADERS.get(BIRTHDAY + offset);
      String birthday = cellList.get(BIRTHDAY + offset);
      if (!StringUtils.isEmpty(birthday)) {
        final Pair<Boolean, Date> correctDate = StringUtils.correctDate(birthday);
        if (!correctDate.getKey()) {
          questions.add((rowNum) + "行 " + (!optional ? "最终客户及联系人：" : "合作伙伴及联系人：") + headerName + "格式不正确，请使用 yyyy/MM/dd");
        } else {
          vo.setBirthday(birthday);
        }
      }
      
      String family = cellList.get( FAMILY_INFO+ offset);
      
      if (StringUtils.isNotEmpty(family)) {
        vo.setFamilyInfo(family);
      }
      
      String other = cellList.get( OTHER + offset);
      if (StringUtils.isNotEmpty(other)) {
        vo.setOther(other);
      }
      
      return vo;
    }
    
    
    public ProjectExcelApplyVO validateProjectApply(Row row, int offset, int length, List<String> questions) {
      
      List<String> cellList = getCellString(row, offset, length);
      ProjectExcelApplyVO biz = new ProjectExcelApplyVO();
      int rowNum = row.getRowNum() + 1;
      
      int index = 0;
      
      //商机名称
      String name = cellList.get(index++);
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 项目总情况不能为空");
      } else {
        if (name.length() > 512) {
          questions.add(rowNum + "行 项目总情况支持最大长度 512");
        }
        biz.setProjectSituation(name);
      }
      //商机名称
      name = cellList.get(index++);
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 最终用户关系分析不能为空");
      } else {
        if (name.length() > 512) {
          questions.add(rowNum + "行 最终用户关系分析支持最大长度 512");
        }
        biz.setUserRelationAnalysis(name);
      }
      //商机名称
      name = cellList.get(index++);
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 参与集成商情况分析名称不能为空");
      } else {
        if (name.length() > 512) {
          questions.add(rowNum + "行 参与集成商情况分析支持最大长度 512");
        }
        biz.setIntegratorSituation(name);
      }
      //商机名称
      name = cellList.get(index++);
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 竞争对手情况分析不能为空");
      } else {
        if (name.length() > 512) {
          questions.add(rowNum + "行 竞争对手情况分析支持最大长度 512");
        }
        biz.setCompeteOpponentSnalysis(name);
      }
      //商机名称
      name = cellList.get(index++);
      if (StringUtils.isEmpty(name)) {
        questions.add(rowNum + "行 项目运作策略不能为空");
      } else {
        if (name.length() > 512) {
          questions.add(rowNum + "行 项目运作策略支持最大长度 512");
        }
        biz.setProjectRunStrategy(name);
      }
      
      return biz;
    }
    
    /**
     * 处理客户
     *
     * @param cellList
     * @param row
     * @return
     */
    private CustomerInfo getCustomerInfo(List<String> cellList, int row, int offset) {

      CustomerInfo customerInfo = new CustomerInfo();

      String customerName = cellList.get(CUSTOMER_NAME + offset);
      customerInfo.setCustomerName(customerName);
      
      String provincial = cellList.get(PROVINCIAL + offset);
      String[] provincialArr = provincial.split("/");
      customerInfo.setProvinceNum(provincialArr[0]);
      customerInfo.setCityNum(provincialArr[1]);
      if(provincialArr.length>=3) {
      customerInfo.setDistrict(provincialArr[2]);
    }

      //验证性质
      String nature = cellList.get(ENTER_NAME + offset);
      final EnterpriseType enterpriseType = typeDao.selectByEnterpriseName(nature);
      customerInfo.setEnterId(enterpriseType.getEnterId().intValue());

      //验证行业
      String business = cellList.get(INDUSTRY + offset);
      customerInfo.setIndustry(business);

      //地址
      String address = cellList.get(ADDRESS + offset);
      customerInfo.setAddress(address);

      // 没错就是这么干
      offset = offset - 2;
      
      //单位座机
      String phoneNumber = cellList.get(TELEPHONE + offset);
      if (!StringUtils.isEmpty(phoneNumber)) {
        customerInfo.setTelephone(phoneNumber);
      }

      //邮编
      String postCode = cellList.get(ZIP_CODE + offset);
      if (!StringUtils.isEmpty(postCode)) {
        customerInfo.setZipCode(postCode);
      }


      //网址
      String netUrl = cellList.get(WEBSITE + offset);
      if (!StringUtils.isEmpty(netUrl)) {
        customerInfo.setWebsite(netUrl);
      }

      //传真
      String faxNumber = cellList.get(FAX+ offset);
      if (!StringUtils.isEmpty(faxNumber)) {
        customerInfo.setFax(faxNumber);
      }
      
      // ~~~~~~~~ 联系人 ~~~~~~~~~~~~

      ContactInfo contactInfo = new ContactInfo();
      String contactName = cellList.get(CONTACT_NAME);
      //姓名
      contactInfo.setContactName(contactName);

      String gender = cellList.get(GENDER);
      //性别
      GenderEnum genderEnum = GenderEnum.convertName(gender);
      contactInfo.setContactSex(genderEnum.getCode());

      //部门
      String deptName = cellList.get(DEPT_NAME);
      contactInfo.setDeptName(deptName);

      //职务
      String position = cellList.get(JOB);
      contactInfo.setJob(position);

      //手机号码
      String telephoneNumber = cellList.get(CONTACT_TELPHONE);
      contactInfo.setTelphone(telephoneNumber);

      //邮箱
      String email = cellList.get(CONTACT_EMAIL);
      if (!StringUtils.isEmpty(email)) {
        contactInfo.setEmail(email);
      }

      //办公电话
      String officePhone = cellList.get(OFFICE_PLANE);
      if (!StringUtils.isEmpty(officePhone)) {
        contactInfo.setOfficePlane(officePhone);
      }

      //学校
      String school = cellList.get(UNIVERSITY);
      if (!StringUtils.isEmpty(school)) {
        contactInfo.setUniversity(school);
      }

      //主修
      String major = cellList.get(MAJOR);
      if (!StringUtils.isEmpty(major)) {
        contactInfo.setMajor(major);
      }

      //生日
      String birthday = cellList.get(BIRTHDAY);
      if (!StringUtils.isEmpty(birthday)) {
        final Pair<Boolean, Date> correctDate = StringUtils.correctDate(birthday);
        contactInfo.setBirthday(correctDate.getValue());
      }

      //家庭
      String familyStatus = cellList.get(FAMILY_INFO);
      if (!StringUtils.isEmpty(familyStatus)) {
        contactInfo.setFamilyInfo(familyStatus);
      }

      String other = cellList.get(OTHER);
      if (!StringUtils.isEmpty(other)) {
        //其他
        contactInfo.setOther(other);
      }

      return customerInfo;
    }


    /**
     * 获取所有cell 并转换为 sequence
     *
     * @param row
     * @return
     */
    private List<String> getCellString(Row row, int offset, int max) {
      List<String> cellList = new ArrayList<>();
//      int cells = getCellNum(row);
      int cells = max;
      for (int i = 0; i < cells; i++) {
        Cell cell = getCell(row, i + offset);
        String value = cellToString(cell);
        cellList.add(value);
      }
      return cellList;
    }


    private boolean validateUser(String employeeId, String employeeName, List<String> questions, int rowNum) {
      
      if (StringUtils.isEmpty(employeeName) || StringUtils.isEmpty(employeeId)) {
        questions.add((rowNum) + "行 员工姓名和工号均不能为空");
      }
      if (!StringUtils.isEmpty(employeeId)) {
        final SysEmployee employee = sysEmployeeDao.queryByIds(employeeId);
        if (employee == null) {
          questions.add(String.format("%d行 %s 员工不存在", (rowNum), employeeId));
        } else {
          //判断姓名和工号一直性
          if (!employee.getEmployeeName().equals(employeeName)) {
            questions.add(String.format("%d行 %s 员工和姓名不匹配", (rowNum), employeeId));
          }
          
          if(employee.getJobStatus() != null && employee.getJobStatus() == 2) {
            
            questions.add(String.format("%d行 %s 员工已离职", (rowNum), employeeId));
          }
          
          return true;
        }
        
      }
      return false;
    }












}










