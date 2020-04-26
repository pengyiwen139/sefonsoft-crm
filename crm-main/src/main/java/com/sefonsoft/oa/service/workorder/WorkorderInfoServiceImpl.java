package com.sefonsoft.oa.service.workorder;

import com.github.pagehelper.util.StringUtil;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.dao.sysemployee.SysEmployeeDao;
import com.sefonsoft.oa.dao.workorder.WorkorderApprovalInfoDao;
import com.sefonsoft.oa.dao.workorder.WorkorderFeedBackDao;
import com.sefonsoft.oa.dao.workorder.WorkorderFeedbackSaleInfoDao;
import com.sefonsoft.oa.dao.workorder.WorkorderInfoDao;
import com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO;
import com.sefonsoft.oa.domain.sysemployee.SysEmployeeQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.workorder.WorkOrderMatchConfigDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderApprovalDTO;
import com.sefonsoft.oa.domain.workorder.WorkorderSearchDTO;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackPreInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackSaleInfo;
import com.sefonsoft.oa.entity.workorder.WorkorderInfo;
import com.sefonsoft.oa.service.common.PageableResult;
import com.sefonsoft.oa.system.mail.MailUtil;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.sound.midi.SysexMessage;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO.PRE;
import static com.sefonsoft.oa.domain.statistic.WorkOrderStatisticsDTO.SALE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nipengcheng
 * @since 2020-03-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkorderInfoServiceImpl implements WorkorderInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkorderInfoServiceImpl.class);


    public static final String PGD_PREFIX = "PGD";

    //派工单状态：1待审批、2已拒绝、3已审批, 4已完成
    public static final int WORKORDER_APPROVAL_STATUS_WAITTING = 1;
    public static final int WORKORDER_APPROVAL_STATUS_REJECT = 2;
    public static final int WORKORDER_APPROVAL_STATUS_ACCEPT = 3;
    public static final int WORKORDER_APPROVAL_STATUS_COMPLETE = 4;
    public static final int WORKORDER_APPROVAL_STATUS_PREFEEDBACK = 5;

    //派工单删除状态
    public static final int WORKORDER_UNDELETE = 0;
    public static final int WORKORDER_DELETED = 1;

    @Resource
    private WorkorderInfoDao workorderInfoDao;

    @Resource
    private WorkorderFeedBackDao workorderFeedBackDao;


    @Resource
    private SysDepartmentDao sysDepartmentDao;

    @Resource
    private WorkorderFeedbackSaleInfoDao workorderFeedbackSaleInfoDao;

    @Resource
    private SysEmployeeDao sysEmployeeDao;


    @Resource
    private WorkorderApprovalInfoDao workorderApprovalInfoDao;

    public static Object object = new Object();

    public static AtomicInteger pgdNumber = new AtomicInteger();
    public static int dataInt;


    /**
     * 功能描述: 保存前检查
     *
     * @Param: workorderInfo
     * @Return: Response
     * @Author: liwenyi
     */
    @Override
    public Response checkInsert(WorkorderInfo workorderInfo) {
        Response<Object> response = new Response<>();
        if (!ObjTool.isNotNull(workorderInfo)) {
            return response.failure("请填写派工单数据");
        }

        if (!ObjTool.isNotNull(workorderInfo.getBizOpportsId())) {
            return response.failure("缺少商机数据");
        }

        if (!ObjTool.isNotNull(workorderInfo.getApplyType())) {
            return response.failure("缺少申请资源类型");
        }

        if (!ObjTool.isNotNull(workorderInfo.getDemandWayId())) {
            return response.failure("缺少需求方式");
        }

        if (!ObjTool.isNotNull(workorderInfo.getLocation())) {
            return response.failure("缺少交流地址");
        }

        if (!ObjTool.isNotNull(workorderInfo.getContentGoal())) {
            return response.failure("缺少目标内容");
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WorkorderInfo save(WorkorderInfo workorderInfo, LoginUserDTO loginUser) {
        //派工单编号：前缀+日期+流水（4位）
        Date date = new Date();
        String pgdId = getPGDnumber();
        workorderInfo.setPgdId(pgdId);
        workorderInfo.setEmployeeId(loginUser.getUserId());
        workorderInfo.setDeptId(loginUser.getDeptId());
        workorderInfo.setCreateTime(date);
        workorderInfo.setModifiedTime(date);
        //状态标为未审批
        workorderInfo.setApprovalStatus(1);

        workorderInfoDao.insert(workorderInfo);
        // send email
        logger.info("添加派工单成功，发送邮件");
        List<SysEmployee> approvalUserList = workorderInfoDao.getApprovalUserList("crm:presaleorder:check", loginUser.getDeptId());
        List<String> emailList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(approvalUserList)) {
            approvalUserList.forEach(sysEmployee -> {
                if (!StringUtils.isEmpty(sysEmployee.getEmail())) {
                    emailList.add(sysEmployee.getEmail());
                }

            });
        }
        /*
        主题：[申请售前支持]+项目名称
        内容：
        [售前支持申请单]
            销售人员：李飞成
            销售电话：13402020101
            所属部门：西南大区
            项目名称：成都飞控集团
            需求方式：PPT演讲交流
            交流地址：成都
            交流时间：2020-04-22
         */
        if (!CollectionUtils.isEmpty(emailList)) {
            SysEmployee employee = sysEmployeeDao.queryByIds(loginUser.getUserId());
            SysDepartment department = sysDepartmentDao.selectByDeptId(loginUser.getDeptId());
            String emailSubject = "申请售前支持[]" + workorderInfo.getProjectName();
            String emailContent = String.format("[售前支持申请单]" + "\n" +
                    "销售人员： " + employee.getEmployeeName() + "\n" +
                    "销售电话： " + employee.getTel() + "\n" +
                    "所属部门: " + department.getDeptName() + "\n" +
                    "项目名称: " + workorderInfo.getProjectName() + "\n" +
                    "需求方式：" + workorderInfo.getDemandWayId() + "\n" +
                    "交流地址：" + workorderInfo.getLocation() + "\n" +
                    "交流时间：" + workorderInfo.getCommunicationTime() + "\n"
            );
            try {
                MailUtil.sendEmail(emailList, emailSubject, emailContent);
            } catch (MessagingException e) {
                logger.error("添加派工单成功，发送邮件和: " + e);
                e.printStackTrace();
            }
        }

        return workorderInfo;
    }

    public void checkAndUpdatePgdStatus(String pgdId) {
        WorkorderInfo workorderInfo = getDetail(null, pgdId);
        if (workorderInfo == null) {
            logger.info("workorderInfo is null");
            return;
        }

//        if (workorderInfo.getApprovalStatus() != WORKORDER_APPROVAL_STATUS_ACCEPT && workorderInfo.getApprovalStatus() != WORKORDER_APPROVAL_STATUS_PREFEEDBACK) {
//            logger.info("workorderInfo , current status is ", workorderInfo.getApprovalStatus());
//            return;
//        }

        List<WorkorderFeedbackPreInfo> workorderFeedbackPreInfo = workorderInfo.getWorkorderFeedbackPreInfo();
        if (workorderFeedbackPreInfo == null) {
            logger.info("workorderFeedbackPreInfo is null");
            return;
        }
        List<WorkorderFeedbackSaleInfo> workorderFeedbackSaleInfo = workorderInfo.getWorkorderFeedbackSaleInfo();
        if (workorderFeedbackSaleInfo == null) {
            logger.info("workorderFeedbackSaleInfo is null");
            return;
        }

        for (WorkorderFeedbackPreInfo pre : workorderFeedbackPreInfo) {
            if (pre.getPreCommentStatus() == 0) {
                return;
            }
        }

        for (WorkorderFeedbackSaleInfo pre : workorderFeedbackSaleInfo) {
            if (pre.getSaleCommentStatus() == 0) {
                logger.info("update workorder status to: WORKORDER_APPROVAL_STATUS_PREFEEDBACK ");
                //update approval status
                workorderInfo.setApprovalStatus(WORKORDER_APPROVAL_STATUS_PREFEEDBACK);
                workorderInfo.setModifiedTime(new Date());
                workorderInfoDao.modify(workorderInfo);
                return;
            }
        }

        logger.info("update workorder status to: WORKORDER_APPROVAL_STATUS_COMPLETE ");
        //update approval status
        workorderInfo.setApprovalStatus(WORKORDER_APPROVAL_STATUS_COMPLETE);
        workorderInfo.setModifiedTime(new Date());
        workorderInfoDao.modify(workorderInfo);

    }

    /**
     * 功能描述: 派工单审批
     *
     * @Param: loginUserDTO
     * @Param: workorderApprovalDTO
     * @Author: liwenyi
     */
    @Transactional(rollbackFor = Exception.class)
    public WorkorderInfo approval(LoginUserDTO loginUserDTO, WorkorderApprovalDTO workorderApprovalDTO) {

        WorkorderInfo workorderInfo = workorderInfoDao.queryByPgdId(workorderApprovalDTO.getPgdId());
        if (!ObjTool.isNotNull(workorderInfo)) {
            logger.info("找不到派工单数据，workorderApprovalDTO ", workorderApprovalDTO);
            return null;
        }

        if (!ObjTool.isNotNull(workorderInfo.getDeptId()) || StringUtils.isEmpty(workorderInfo.getDeptId())) {
            logger.info("找不到部门数据,  workorderInfo", workorderInfo);
            return null;
        }

        if (workorderInfo.getApprovalStatus() > WORKORDER_APPROVAL_STATUS_WAITTING) {
            logger.info("该派工单已经被处理", workorderInfo);
            return workorderInfo;
        }

        //检查审批权限
        List<String> deptIds = new ArrayList<>();
        deptIds.add(workorderInfo.getDeptId());
        if (!this.checkApprovalPermission(loginUserDTO, deptIds)) {
            logger.info("没有数据权限");
            return null;
        }
        Date date = new Date();
        workorderInfo.setApprovalStatus(workorderApprovalDTO.getApprovalResult());
        workorderInfo.setApprovalDesc(workorderApprovalDTO.getApprovalDesc());
        workorderInfo.setApprovalEmployeeId(loginUserDTO.getUserId());
        workorderInfo.setModifiedTime(date);
        workorderInfoDao.modify(workorderInfo);


        //判断审批结果
        if (workorderApprovalDTO.getApprovalResult() == WORKORDER_APPROVAL_STATUS_ACCEPT) {
            //审核通过
            List<String> emailList = new ArrayList<>();
            String preStr = "";

            List<String> targetEmployeeIdList = workorderApprovalDTO.getTargetEmployeeIdList();
            for (String employeeId : targetEmployeeIdList) {
                SysEmployee targetEmeployee = sysEmployeeDao.queryByIds(employeeId);
                if (!ObjTool.isNotNull(targetEmeployee)) {
                    logger.info("targetEmeployee not found");
                    return null;
                }
                //插入售前反馈信息
                WorkorderFeedbackPreInfo feedbackPreInfo = new WorkorderFeedbackPreInfo();
                feedbackPreInfo.setPgdId(workorderInfo.getPgdId());
                feedbackPreInfo.setEmployeeId(employeeId);
                feedbackPreInfo.setCreateTime(date);
                feedbackPreInfo.setModifiedTime(date);
                feedbackPreInfo.setPreCommentStatus(0);
                feedbackPreInfo.setIsDiscard(0);
                workorderFeedBackDao.insertFeedbackPreInfo(feedbackPreInfo);

                //插入销售反馈信息
                WorkorderFeedbackSaleInfo feedbackSaleInfo = new WorkorderFeedbackSaleInfo();
                feedbackSaleInfo.setPgdId(workorderInfo.getPgdId());
                feedbackSaleInfo.setEmployeeId(workorderInfo.getEmployeeId());
                feedbackSaleInfo.setTargetEmployeeId(employeeId);
                feedbackSaleInfo.setTargetEmployeeName(targetEmeployee.getEmployeeName());
                feedbackSaleInfo.setCreateTime(date);
                feedbackSaleInfo.setModifiedTime(date);
                feedbackSaleInfo.setSaleCommentStatus(0);
                workorderFeedBackDao.insertFeedbackSaleInfo(feedbackSaleInfo);

                if (!StringUtils.isEmpty(targetEmeployee.getEmail())) {
                    emailList.add(targetEmeployee.getEmail());
                }

                if (!StringUtils.isEmpty(preStr)) {
                    preStr = preStr + ", ";
                }
                preStr = String.format("%s(%s)", targetEmeployee.getEmployeeName(), targetEmeployee.getTel());
            }
            //加入派工单发起人email
            SysEmployee employee = sysEmployeeDao.queryByIds(workorderInfo.getEmployeeId());
            if (!StringUtils.isEmpty(employee.getEmail())) {
                emailList.add(employee.getEmail());
            }
             /*
            主题：[申请售前支持]+项目名称
            内容：
            [售前支持申请单]
                审批结果：通过
                销售人员：李飞成
                销售电话：13402020101
                所属部门：西南大区
                项目名称：成都飞控集团
                需求方式：PPT演讲交流
                交流地址：成都
                交流时间：2020-04-22
                支撑人员： 李成飞（13232365986）， 李成成（16326525484）
             */

            String emailSubject = "[申请驳回]" + workorderInfo.getProjectName();
            String emailContent = String.format("[售前支持申请单]" + "\n" +
                    "审批结果：通过" + "\n" +
                    "销售人员： " + workorderInfo.getEmployeeName() + "\n" +
                    "销售电话： " + employee.getTel() + "\n" +
                    "所属部门: " + workorderInfo.getDeptName() + "\n" +
                    "项目名称: " + workorderInfo.getProjectName() + "\n" +
                    "需求方式：" + workorderInfo.getDemandWayId() + "\n" +
                    "交流地址：" + workorderInfo.getLocation() + "\n" +
                    "交流时间：" + workorderInfo.getCommunicationTime() + "\n" +
                    "支撑人员：" + preStr + "\n"
            );
            try {
                MailUtil.sendEmail(emailList, emailSubject, emailContent);
            } catch (MessagingException e) {
                logger.error("添加派工单成功，发送邮件和: " + e);
                e.printStackTrace();
            }
        } else if (workorderApprovalDTO.getApprovalResult() == WORKORDER_APPROVAL_STATUS_REJECT) {
            //审核 拒绝
            List<String> emailList = new ArrayList<>();
            SysEmployee employee = sysEmployeeDao.queryByIds(workorderInfo.getEmployeeId());
            if (!StringUtils.isEmpty(employee.getEmail())) {
                emailList.add(employee.getEmail());
            }
              /*
            主题：[申请售前支持]+项目名称
            内容：
            [售前支持申请单]
                审批结果：通过
                驳回理由：XXX
                销售人员：李飞成
                销售电话：13402020101
                所属部门：西南大区
                项目名称：成都飞控集团
                需求方式：PPT演讲交流
                交流地址：成都
                交流时间：2020-04-22
                支撑人员： 李成飞（13232365986）， 李成成（16326525484）
             */

            String emailSubject = "[申请通过]" + workorderInfo.getProjectName();
            String emailContent = String.format(
                    "[售前支持申请单]" + "\n" +
                            "审批结果：驳回" + "\n" +
                            "驳回理由: " + workorderApprovalDTO.getApprovalDesc() + "\n" +
                            "销售人员： " + workorderInfo.getEmployeeName() + "\n" +
                            "销售电话： " + employee.getTel() + "\n" +
                            "所属部门: " + workorderInfo.getDeptName() + "\n" +
                            "项目名称: " + workorderInfo.getProjectName() + "\n" +
                            "需求方式：" + workorderInfo.getDemandWayId() + "\n" +
                            "交流地址：" + workorderInfo.getLocation() + "\n" +
                            "交流时间：" + workorderInfo.getCommunicationTime() + "\n"
            );
            try {
                MailUtil.sendEmail(emailList, emailSubject, emailContent);
            } catch (MessagingException e) {
                logger.error("添加派工单成功，发送邮件和: " + e);
                e.printStackTrace();
            }

        }

        return workorderInfo;

    }

    /**
     * 功能描述: 派工单重新指派
     *
     * @Param: loginUserDTO
     * @Param: workorderApprovalDTO
     * @Author: liwenyi
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean reAssign(LoginUserDTO loginUserDTO, WorkorderApprovalDTO workorderApprovalDTO) {
        WorkorderInfo workorderInfo = this.getDetail(loginUserDTO, workorderApprovalDTO.getPgdId());

        List<WorkorderFeedbackPreInfo> workorderFeedbackPreInfo = workorderInfo.getWorkorderFeedbackPreInfo();
        List<WorkorderFeedbackSaleInfo> workorderFeedbackSaleInfo = workorderInfo.getWorkorderFeedbackSaleInfo();

        Date date = new Date();
        workorderInfo.setModifiedTime(date);
        workorderInfoDao.modify(workorderInfo);

        List<String> targetEmployeeIdList = workorderApprovalDTO.getTargetEmployeeIdList();
        //set discard and delete
        for (WorkorderFeedbackPreInfo preInfo : workorderFeedbackPreInfo) {
            if (!targetEmployeeIdList.contains(preInfo.getEmployeeId())) {
                //set discard
                //preInfo.setIsDiscard(1);
                workorderFeedBackDao.deletePreFeedbackByPrimaryKey(preInfo.getId());
                workorderFeedbackSaleInfo.forEach(saleInfo -> {
                    if (saleInfo.getTargetEmployeeId().equals(preInfo.getEmployeeId())) {
                        //delete
                        workorderFeedbackSaleInfoDao.deleteByPrimaryKey(saleInfo.getId());
                    }
                });
            }
        }
        //add new
        targetEmployeeIdList.forEach(employeeId -> {
            SysEmployee targetEmeployee = sysEmployeeDao.queryByIds(employeeId);
            if (!ObjTool.isNotNull(targetEmeployee)) {
                logger.info("targetEmeployee not found");
                return;
            }

            boolean isExist = false;
            for (WorkorderFeedbackPreInfo preInfo : workorderFeedbackPreInfo) {
                if (preInfo.getEmployeeId().equals(employeeId)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                //add a new record
                //插入售前反馈信息
                WorkorderFeedbackPreInfo feedbackPreInfo = new WorkorderFeedbackPreInfo();
                feedbackPreInfo.setPgdId(workorderInfo.getPgdId());
                feedbackPreInfo.setEmployeeId(employeeId);
                feedbackPreInfo.setCreateTime(date);
                feedbackPreInfo.setModifiedTime(date);
                feedbackPreInfo.setPreCommentStatus(0);
                feedbackPreInfo.setIsDiscard(0);
                workorderFeedBackDao.insertFeedbackPreInfo(feedbackPreInfo);

                //插入销售反馈信息
                WorkorderFeedbackSaleInfo feedbackSaleInfo = new WorkorderFeedbackSaleInfo();
                feedbackSaleInfo.setPgdId(workorderInfo.getPgdId());
                feedbackSaleInfo.setEmployeeId(workorderInfo.getEmployeeId());
                feedbackSaleInfo.setTargetEmployeeId(employeeId);
                feedbackSaleInfo.setTargetEmployeeName(targetEmeployee.getEmployeeName());
                feedbackSaleInfo.setCreateTime(date);
                feedbackSaleInfo.setModifiedTime(date);
                feedbackSaleInfo.setSaleCommentStatus(0);
                workorderFeedBackDao.insertFeedbackSaleInfo(feedbackSaleInfo);
            }
        });
        return true;
    }

    @Override
    public String getPGDnumber() {

        synchronized (object) {
            //获取当天值
            Integer courrentDateTimeKey = DateUtils.getCourrentDateTimeKey();
            String pdgPrefix = PGD_PREFIX + "-" + courrentDateTimeKey;
            //获取当天当前最大值
            String maxPGD = workorderInfoDao.getMaxPGD(pdgPrefix);
            //当天没有直接生成新的单号
            if (StringUtils.isEmpty(maxPGD) && courrentDateTimeKey != dataInt) {
                dataInt = courrentDateTimeKey;
                pgdNumber.set(0);
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }
            if (StringUtils.isEmpty(maxPGD) && courrentDateTimeKey == dataInt) {
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }

            String[] split = maxPGD.split("-");
            //单号非法，直接生成新的
            if (split.length != 3 && courrentDateTimeKey != dataInt) {
                dataInt = courrentDateTimeKey;
                pgdNumber.set(0);
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }
            if (split.length != 3 && courrentDateTimeKey == dataInt) {
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }
            String number = split[2];
            //校验流水号是否合法
            if (!StringUtils.isNumber(number) && courrentDateTimeKey != dataInt) {
                dataInt = courrentDateTimeKey;
                pgdNumber.set(0);
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }

            if (!StringUtils.isNumber(number) && courrentDateTimeKey == dataInt) {
                return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
            }

            //系统重启初始化参数
            int anInt = Integer.parseInt(number);
            if (dataInt != courrentDateTimeKey) {
                dataInt = courrentDateTimeKey;
                pgdNumber.set(anInt);
            }
            if (anInt > pgdNumber.get()) {
                pgdNumber.set(anInt);
            }
            //正常单号
            return pdgPrefix + "-" + StringUtils.autoGenericNumber(4, pgdNumber.incrementAndGet());
        }
    }


    /**
     * 功能描述: 派工单查询
     *
     * @Param: loginUserDTO
     * @Param: workorderSearchDTO
     * @Return: Object
     * @Author: liwenyi
     */
    public PageableResult getCondition(LoginUserDTO loginUserDTO, WorkorderSearchDTO workorderSearchDTO) {
        if (CollectionUtils.isEmpty(this.checkDataPermission(loginUserDTO, workorderSearchDTO.getDeptIds()))) {
            //没有数据权限，就只能查自己的派工单
            workorderSearchDTO.setDeptIds(null);
            List<String> employeeIds = new ArrayList<>();
            employeeIds.add(loginUserDTO.getUserId());
            workorderSearchDTO.setSaleEmployeeIdList(employeeIds);
        } else {
            if (!CollectionUtils.isEmpty(workorderSearchDTO.getSaleEmployeeIdList())) {
                //查询特定员工
                workorderSearchDTO.setDeptIds(null);
                workorderSearchDTO.setSaleEmployeeIdList(workorderSearchDTO.getSaleEmployeeIdList());
            }
        }

        if (!ObjTool.isNotNull(workorderSearchDTO.getPage())) {
            workorderSearchDTO.setPage(1);
        }
        if (!ObjTool.isNotNull(workorderSearchDTO.getLimit())) {
            workorderSearchDTO.setPage(10);
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getDeptIds())) {
            workorderSearchDTO.setDeptIds(null);
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getSaleEmployeeIdList())) {
            workorderSearchDTO.setSaleEmployeeIdList(null);
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getPreEmployeeIdList())) {
            workorderSearchDTO.setPreEmployeeIdList(null);
        }

//        if (CollectionUtils.isEmpty(workorderSearchDTO.getPreDeptIds())) {
//            workorderSearchDTO.setPreDeptIds(null);
//        } else {
//            if (CollectionUtils.isEmpty(workorderSearchDTO.getPreEmployeeIdList())) {
//                workorderSearchDTO.setPreEmployeeIdList(new ArrayList<>());
//            }
//            List<SysEmployeeQueryDTO> employees = sysEmployeeDao.getConditionList(workorderSearchDTO.getPreDeptIds(), new SysEmployeeQueryDTO());
//            employees.forEach(employee -> {
//                workorderSearchDTO.getPreEmployeeIdList().add(employee.getEmployeeId());
//            });
//        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getApprovalStatusList())) {
            workorderSearchDTO.setApprovalStatusList(null);
        }
        if (CollectionUtils.isEmpty(workorderSearchDTO.getProjectPhaseList())) {
            workorderSearchDTO.setProjectPhaseList(null);
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getProjectIndustryList())) {
            workorderSearchDTO.setProjectIndustryList(null);
        }

        if (!StringUtil.isEmpty(workorderSearchDTO.getEndDate()) && !StringUtil.isEmpty(workorderSearchDTO.getEndDate().trim())) {
            workorderSearchDTO.setEndDate(workorderSearchDTO.getEndDate().trim() + " 23 59:59");
        }


        List<WorkorderInfo> list = workorderInfoDao.getCondition(workorderSearchDTO);
        Long totalCount = workorderInfoDao.getConditionTotal(workorderSearchDTO);
        list.forEach(workorderInfo -> {
            List<WorkorderFeedbackPreInfo> preInfoList = workorderFeedBackDao.queryPreFeedbackByPgdId(workorderInfo.getPgdId());
            workorderInfo.setWorkorderFeedbackPreInfo(preInfoList);
            List<WorkorderFeedbackSaleInfo> saleInfoList = workorderFeedbackSaleInfoDao.selectByPgdId(workorderInfo.getPgdId());
            workorderInfo.setWorkorderFeedbackSaleInfo(saleInfoList);

        });
        return new PageableResult<>(totalCount, list);
    }

    List<String> checkDataPermission(LoginUserDTO loginUser, List<String> inputDeptIds) {
        if (CollectionUtils.isEmpty(inputDeptIds)) {
            return null;
        }
        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            logger.info("没有数据权限");
            return null;
        }
        List<String> tempList = new ArrayList<>();
        tempList.addAll(dataDeptIdList);

        List<String> approvalList = workorderInfoDao.getSourceDeptConfig(dataDeptIdList);
        if (!CollectionUtils.isEmpty(approvalList)) {
            tempList.addAll(approvalList);
        }

        if (CollectionUtils.isEmpty(tempList)) {
            logger.info("没有数据权限");
            return null;
        } else {
            //按部门条件查询
            //判断参数中的部门是否在数据权限列表里
            for (String deptId : inputDeptIds) {
                if (!tempList.contains(deptId)) {
                    logger.info("没有该部门授权权限:" + deptId);
                    return null;
                }
            }
        }
        return inputDeptIds;
    }

    //检查审批权限
    boolean checkApprovalPermission(LoginUserDTO loginUser, List<String> inputDeptIds) {
        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            logger.info("没有数据权限");
            return false;
        }
        List<String> approvalList = workorderInfoDao.getSourceDeptConfig(dataDeptIdList);
        if (CollectionUtils.isEmpty(approvalList)) {
            logger.info("没有数据权限");
            return false;
        } else {
            //按部门条件查询
            if (!CollectionUtils.isEmpty(inputDeptIds)) {
                //判断参数中的部门是否在数据权限列表里
                for (String deptId : inputDeptIds) {
                    if (!approvalList.contains(deptId)) {
                        logger.info("没有该部门授权权限:" + deptId);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 功能描述: 获取销售售前匹配配置
     *
     * @Param: deptIds
     * @Return: List<String>
     */
    public List<WorkOrderMatchConfigDTO> getDeptConfigMap(LoginUserDTO loginUser) {
        List<WorkOrderMatchConfigDTO> resultList = new ArrayList<>();
        //数据权限部门列表
        List<String> dataDeptIdList = loginUser.getDataDeptIdList();
        if (!CollectionUtils.isEmpty(dataDeptIdList)) {
            List<String> bmlList = workorderInfoDao.getDesDeptConfig(dataDeptIdList);
            bmlList.forEach(deptDes -> {
                SysDepartment department = sysDepartmentDao.selectByDeptId(deptDes);
                WorkOrderMatchConfigDTO matchConfigDTO = new WorkOrderMatchConfigDTO();
                matchConfigDTO.setPreDepartment(department);

                List<String> tempList = new ArrayList<>();
                tempList.add(deptDes);

                List<SysDepartment> xiaoshouList = new ArrayList<>();
                List<String> deptSourceList = workorderInfoDao.getSourceDeptConfig(tempList);
                deptSourceList.forEach(deptId -> {
                    SysDepartment sourceDept = sysDepartmentDao.selectByDeptId(deptId);
                    xiaoshouList.add(sourceDept);
                });

                matchConfigDTO.setSaleDepartmentList(xiaoshouList);

                resultList.add(matchConfigDTO);

            });
        }

        return resultList;
    }

    /**
     * 功能描述: 售前反馈派工单
     *
     * @Param: loginUserDTO
     * @Param: workorderFeedbackPreInfo
     * @Return: Object
     * @Author: liwenyi
     */
    @Transactional(rollbackFor = Exception.class)
    public Object preFeedback(LoginUserDTO loginUserDTO, WorkorderFeedbackPreInfo workorderFeedbackPreInfo) {

        WorkorderFeedbackPreInfo feedbackPreInfo = workorderFeedBackDao.queryPreFeedbackById(workorderFeedbackPreInfo.getId());
        if (!ObjTool.isNotNull(feedbackPreInfo)) {
            logger.info("找不到派工单反馈数据， ", workorderFeedbackPreInfo);
            return null;
        }
        Date date = new Date();
        feedbackPreInfo.setModifiedTime(date);
        feedbackPreInfo.setSpendTime(workorderFeedbackPreInfo.getSpendTime());
        feedbackPreInfo.setWorkConcent(workorderFeedbackPreInfo.getWorkConcent());
        feedbackPreInfo.setRequireConsistency(workorderFeedbackPreInfo.getRequireConsistency());
        feedbackPreInfo.setCapabilityMatching(workorderFeedbackPreInfo.getCapabilityMatching());
        feedbackPreInfo.setBusinessRelation(workorderFeedbackPreInfo.getBusinessRelation());
        feedbackPreInfo.setEvaluationDesc(workorderFeedbackPreInfo.getEvaluationDesc());
        feedbackPreInfo.setPreCommentStatus(1);
        feedbackPreInfo.setIsDiscard(0);


        workorderFeedBackDao.updatePreFeedback(feedbackPreInfo);

        checkAndUpdatePgdStatus(feedbackPreInfo.getPgdId());
        return feedbackPreInfo;
    }


    /**
     * 功能描述: 售前反馈列表
     *
     * @Param: loginUserDTO
     * @Param: workorderSearchDTO
     * @Return: Object
     * @Author: liwenyi
     */
    public PageableResult preFeedbackList(LoginUserDTO loginUserDTO, WorkorderSearchDTO workorderSearchDTO) {
//        if (!this.checkDataPermission(loginUserDTO, workorderSearchDTO.getDeptIds())) {
//            logger.info("没有数据权限");
//            return null;
//        }

        if (!ObjTool.isNotNull(workorderSearchDTO.getPage())) {
            workorderSearchDTO.setPage(1);
        }
        if (!ObjTool.isNotNull(workorderSearchDTO.getLimit())) {
            workorderSearchDTO.setPage(10);
        }

        if (!ObjTool.isNotNull(workorderSearchDTO.getApprovalStatusList())) {
            workorderSearchDTO.setApprovalStatusList(null);
        }

        if (!StringUtil.isEmpty(workorderSearchDTO.getEndDate()) && !StringUtil.isEmpty(workorderSearchDTO.getEndDate().trim())) {
            workorderSearchDTO.setEndDate(workorderSearchDTO.getEndDate().trim() + " 23 59:59");
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getProjectPhaseList())) {
            workorderSearchDTO.setProjectPhaseList(null);
        }

        if (CollectionUtils.isEmpty(workorderSearchDTO.getProjectIndustryList())) {
            workorderSearchDTO.setProjectIndustryList(null);
        }

        if (!StringUtil.isEmpty(workorderSearchDTO.getEndDate()) && !StringUtil.isEmpty(workorderSearchDTO.getEndDate().trim())) {
            workorderSearchDTO.setEndDate(workorderSearchDTO.getEndDate().trim() + " 23 59:59");
        }

        List<String> inputDeptIds = workorderSearchDTO.getPreDeptIds();
        List<String> employeeIds = new ArrayList<>();

        List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
        if (CollectionUtils.isEmpty(dataDeptIdList)) {
            employeeIds.add(loginUserDTO.getUserId());
        } else {
            if (!CollectionUtils.isEmpty(workorderSearchDTO.getPreEmployeeIdList())) {
                //只查询单个人的
                employeeIds.addAll(workorderSearchDTO.getPreEmployeeIdList());
            } else {
                //按部门条件查询
                if (CollectionUtils.isEmpty(inputDeptIds)) {
                    inputDeptIds = dataDeptIdList;
//                    logger.info("部门id缺失");
//                    return null;
                }

                List<SysEmployeeQueryDTO> employees = sysEmployeeDao.getConditionList(inputDeptIds, new SysEmployeeQueryDTO());
                employees.forEach(employee -> {
                    employeeIds.add(employee.getEmployeeId());
                });
            }
        }

        workorderSearchDTO.setPreEmployeeIdList(employeeIds);

        logger.info("查询员工：", employeeIds);

        List<WorkorderFeedbackPreInfo> list = workorderFeedBackDao.getPreFeedbackList(workorderSearchDTO);
        Long totalCount = workorderFeedBackDao.getPreFeedbackListTotal(workorderSearchDTO);
        list.forEach(feedbackPreInfo -> {
            WorkorderInfo workorderInfo = workorderInfoDao.queryByPgdId(feedbackPreInfo.getPgdId());
            List<WorkorderFeedbackPreInfo> preInfoList = workorderFeedBackDao.queryPreFeedbackByPgdId(workorderInfo.getPgdId());
            workorderInfo.setWorkorderFeedbackPreInfo(preInfoList);

            feedbackPreInfo.setWorkorderInfo(workorderInfo);

        });
        return new PageableResult<>(totalCount, list);
    }


    /**
     * 功能描述: 派工单详细
     *
     * @Param: loginUserDTO
     * @Param: pgdId
     * @Return: WorkorderInfo
     * @Author: liwenyi
     */
    @Override
    public WorkorderInfo getDetail(LoginUserDTO loginUserDTO, String pgdId) {
        WorkorderInfo workorderInfo = workorderInfoDao.queryByPgdId(pgdId);
        if (!ObjTool.isNotNull(workorderInfo)) {
            logger.info("找不到派工单数据，pgdId ", pgdId);
            return null;
        }
        List<WorkorderFeedbackPreInfo> preInfoList = workorderFeedBackDao.queryPreFeedbackByPgdId(workorderInfo.getPgdId());
        workorderInfo.setWorkorderFeedbackPreInfo(preInfoList);
        List<WorkorderFeedbackSaleInfo> saleInfoList = workorderFeedbackSaleInfoDao.selectByPgdId(workorderInfo.getPgdId());
        workorderInfo.setWorkorderFeedbackSaleInfo(saleInfoList);
        return workorderInfo;
    }

    /**
     * 功能描述: 删除派工单
     *
     * @Param: workorderInfo
     * @Param: loginUserDTO
     * @Author: liwenyi
     */
    public Response delete(WorkorderInfo workorderInfo, LoginUserDTO loginUserDTO) {
        WorkorderInfo tempWorkorderInfo = workorderInfoDao.queryByPgdId(workorderInfo.getPgdId());
        if (!ObjTool.isNotNull(tempWorkorderInfo)) {
            logger.info("找不到派工单数据，workorderApprovalDTO ", workorderInfo);
            return new Response<>().failure("找不到派工单数据 ");
        }

        if (tempWorkorderInfo.getApprovalStatus() != WORKORDER_APPROVAL_STATUS_REJECT) {
            logger.info("此派工单不可删除，只有已拒绝状态派工单才能删除 ");
            return new Response<>().failure("此派工单不可删除，只有已拒绝状态派工单才能删除");
        }

        if (tempWorkorderInfo.getIsDelete() == WORKORDER_DELETED) {
            logger.info("此派工单已删除 ");
            return new Response<>().failure("此派工单已删除");
        }

        //逻辑删除，0未删除，1已删除
        tempWorkorderInfo.setIsDelete(WORKORDER_DELETED);
        workorderInfoDao.modify(tempWorkorderInfo);
        return null;
    }


    /**
     * 功能描述: 获取需求方式表信息
     *
     * @Param: []
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: liwenyi
     */
    @Override
    public List<Map<String, Object>> getDemandWay() {
        return workorderInfoDao.getDemandWay();
    }

    /**
     * 功能描述: 获取对象技术能力评估表信息
     *
     * @Param: []
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: liwenyi
     */
    @Override
    public List<Map<String, Object>> getObjectAbility() {
        return workorderInfoDao.getObjectAbility();
    }


    @Override
    public int workOrderStatistics(WorkOrderStatisticsDTO statistics, LoginUserDTO userDTO) {

        final Integer employeeType = statistics.getEmployeeType();
        int count = 0;
        switch (employeeType) {
            case PRE:
                count = workorderInfoDao.countByPre(userDTO.getUserId());
                break;
            case SALE:
                count = workorderInfoDao.countBySales(userDTO.getUserId());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + employeeType);
        }

        return count;
    }
}
