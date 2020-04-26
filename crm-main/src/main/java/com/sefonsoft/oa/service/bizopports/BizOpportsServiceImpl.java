package com.sefonsoft.oa.service.bizopports;

import com.sefonsoft.oa.dao.bizopports.BizOpportsDao;
import com.sefonsoft.oa.dao.customer.CustomerSaleRefDao;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticGroupVo;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticVo;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.customer.CustomerSaleRef;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.entity.workorder.BizOpportInfo;
import com.sefonsoft.oa.system.emun.Grade;
import com.sefonsoft.oa.system.emun.OnTimeEnum;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.DateUtil;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.UtilSJMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.text.Position.Bias;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.sefonsoft.oa.system.emun.Grade.LD;

/**
 * Created by liwenyi
 * Date: 2020/2/20
 * Desc: (biz_opports)表服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BizOpportsServiceImpl implements BizOpportsService {

    /**
     * log
     */
    private final static Logger LOG = LoggerFactory.getLogger(BizOpportsServiceImpl.class);

    @Resource
    private BizOpportsDao bizOpportsDao;

    @Resource
    private CustomerSaleRefDao customerSaleRefDao;

    @Resource
    private SysDepartmentDao departmentDao;

    private static ThreadLocal<SimpleDateFormat> parse = ThreadLocal.withInitial(() -> new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE));

    /**
     * 判断销售负责人是否已与传入的客户关联
     *
     * @param bizOpportsDTO
     * @return
     */
    public boolean hasCustomer(BizOpportsDTO bizOpportsDTO) {
        List<Map<String, Object>> customerList = bizOpportsDao.queryCustomerByUser(bizOpportsDTO.getEmployeeId());
        boolean hasCustomer = false;
        for (Map<String, Object> map : customerList) {
            if (map.get("customer_id").equals(bizOpportsDTO.getCustomerId())) {
                hasCustomer = true;
            }
        }
        return hasCustomer;
    }

    /**
     * 新增商机
     *
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public int insert(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO) {
        Date date = new Date();
        bizOpportsDTO.setCreateTime(date);
        bizOpportsDTO.setModifiedTime(date);
        bizOpportsDTO.setBizId(UtilSJMethod.getSJnumber(bizOpportsDao.getMaxSJ((UtilSJMethod.SJ_PREFIX)+DateUtils.getCourrentDateTimeKey())));
        bizOpportsDTO.setState(1);
        bizOpportsDTO.setImportType(0);
        bizOpportsDTO.setCreateId(loginUserDTO.getUserId());
        if (loginUserDTO.getGradeId().equals(Grade.LD.getCode()) || loginUserDTO.getGradeId().equals(Grade.XS.getCode()) || loginUserDTO.getGradeId().equals(Grade.DX.getCode())) {
            bizOpportsDTO.setEmployeeId(loginUserDTO.getUserId());
            boolean hasCustomer = hasCustomer(bizOpportsDTO);
            if (!hasCustomer) {
                CustomerSaleRef customerSaleRef = new CustomerSaleRef();
                customerSaleRef.setEmployeeId(bizOpportsDTO.getEmployeeId());
                customerSaleRef.setCustomerId(bizOpportsDTO.getCustomerId());
                customerSaleRef.setCreateTime(date);
                customerSaleRef.setLastTime(date);
                customerSaleRef.setOperator(loginUserDTO.getUserId());
                customerSaleRefDao.insert(customerSaleRef);
            }
        }
        return bizOpportsDao.insert(bizOpportsDTO);
    }
    
    /**
     * 新增商机
     *
     * @param bizOpportsDTO
     * @return
     */
    public Long insert(BizOpportsDTO bizOpportsDTO, String operator) {
        Date date = new Date();
        bizOpportsDTO.setCreateTime(date);
        bizOpportsDTO.setModifiedTime(date);
        
        // 我们始终使用我们自己的ID
        bizOpportsDTO.setBizId(UtilSJMethod.getSJnumber(bizOpportsDao.getMaxSJ((UtilSJMethod.SJ_PREFIX)+DateUtils.getCourrentDateTimeKey())));
        bizOpportsDTO.setState(1);
        bizOpportsDTO.setImportType(0);
        // bizOpportsDTO.setCreateId(operator);
        
        
        if (bizOpportsDTO.getCustomerId() != null && !hasCustomer(bizOpportsDTO)) {
            CustomerSaleRef customerSaleRef = new CustomerSaleRef();
            customerSaleRef.setEmployeeId(bizOpportsDTO.getEmployeeId());
            customerSaleRef.setCustomerId(bizOpportsDTO.getCustomerId());
            customerSaleRef.setCreateTime(date);
            customerSaleRef.setLastTime(date);
            customerSaleRef.setOperator(operator);
            customerSaleRefDao.insert(customerSaleRef);
        }
        
        
        bizOpportsDao.insert(bizOpportsDTO);
        return bizOpportsDTO.getId();
    }
    
    public boolean checkColudImport(BizOpportsExport biz) {
      
      String bizId = biz.getBizId();
      String empId = biz.getEmployeeId();
      BizOpports findone = bizId == null ? null : bizOpportsDao.findByBizId(bizId);
      if (findone == null) {
        return true;
      } else {
        
        // 只有未立项
        // 并且属于这个销售负责人的，才能立项
        boolean r = Objects.equals(findone.getEmployeeId(), empId) // 是这个销售
                && Objects.equals(findone.getState(), 1); // 未立项
        
        if (r) {
          
          // 使用库里的数据
          Long bid = findone.getId();
          biz.setId(bid);
          
          // 联系人会在后面用到
          Long cid = findone.getContactId();;
          biz.setContactId(cid);
          
          
          return true;
        } else {
          return false;
        }
        
      }
      
      
      
    }

    /**
     * 修改商机信息
     *
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public int update(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO) {
        Date date = new Date();
        bizOpportsDTO.setModifiedTime(date);
        int res;
        if (bizOpportsDTO.getEmployeeId() == null || "".equals(bizOpportsDTO.getEmployeeId())) {
            res = bizOpportsDao.update(bizOpportsDTO);
        } else {
            boolean hasCustomer = hasCustomer(bizOpportsDTO);
            if (hasCustomer) {
                res = bizOpportsDao.update(bizOpportsDTO);
            } else {
                res = bizOpportsDao.update(bizOpportsDTO);
                CustomerSaleRef customerSaleRef = new CustomerSaleRef();
                customerSaleRef.setEmployeeId(bizOpportsDTO.getEmployeeId());
                customerSaleRef.setCustomerId(bizOpportsDTO.getCustomerId());
                customerSaleRef.setCreateTime(date);
                customerSaleRef.setLastTime(date);
                customerSaleRef.setOperator(loginUserDTO.getUserId());
                customerSaleRefDao.insert(customerSaleRef);
            }
        }
        return res;
    }

    /**
     * 根据id查看商机详情
     *
     * @param id
     * @return
     */
    @Override
    public BizOpports queryById(Long id) {
        return bizOpportsDao.queryById(id);
    }

    /**
     * 根据id删除商机
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return bizOpportsDao.deleteById(id);
    }

    /**
     * 根据关键字查看商机信息
     *
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public List<BizOpports> queryByKeyWords(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO) {
        boolean role = isRole(loginUserDTO);
        List<String> ids = new ArrayList<>();
        List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
        if (dataDeptIdList != null && dataDeptIdList.size() > 0) {
            ids.addAll(dataDeptIdList);
        }
        List<BizOpports> bizOpportsList = null;
        if (role && !bizOpportsDTO.isPersonal() && !bizOpportsDTO.isNotProject()) {
            if (bizOpportsDTO.getDataDeptIdList() == null || bizOpportsDTO.getDataDeptIdList().size() == 0) {
                return null;
            }
            bizOpportsList = bizOpportsDao.queryByManagementByKeyWordsForRole(bizOpportsDTO.getEmployeeId(), loginUserDTO.getUserId(), bizOpportsDTO.getKeywords(), ids, bizOpportsDTO.getDataDeptIdList(), bizOpportsDTO.getPage(), bizOpportsDTO.getLimit(), bizOpportsDTO.getName(), bizOpportsDTO.getScope());
        } else if (!role && !bizOpportsDTO.isPersonal() && !bizOpportsDTO.isNotProject()) {
            bizOpportsList = bizOpportsDao.queryByManagementByKeyWords(bizOpportsDTO.getKeywords(), loginUserDTO.getUserId(), bizOpportsDTO.getPage(), bizOpportsDTO.getLimit(), bizOpportsDTO.getName());
        } else if (bizOpportsDTO.isPersonal() && bizOpportsDTO.isNotProject()) {
            bizOpportsList = bizOpportsDao.queryByPersonal(loginUserDTO.getUserId(), bizOpportsDTO.getPage(), bizOpportsDTO.getLimit(), bizOpportsDTO.getName(), bizOpportsDTO.getKeywords());
        }
        return bizOpportsList;
    }

    /**
     * 功能描述: 判断用户是否为领导
     *
     * @Param: [loginUserDTO]
     * @Return: boolean
     * @Author: liwenyi
     */
    public boolean isRole(LoginUserDTO loginUserDTO) {
        String gradeId = loginUserDTO.getGradeId();
        boolean role = false;
        if (gradeId.equalsIgnoreCase(LD.getCode())) {
            role = true;
        }
        return role;
    }

    /**
     * 根据关键字查看商机信息总量
     *
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public int queryByKeyWordsTotal(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO) {
        boolean role = isRole(loginUserDTO);
        List<String> ids = new ArrayList<>();
        List<String> dataDeptIdList = loginUserDTO.getDataDeptIdList();
        if (dataDeptIdList != null && dataDeptIdList.size() > 0) {
            ids.addAll(dataDeptIdList);
        }
        int total = 0;
        if (role && !bizOpportsDTO.isPersonal() && !bizOpportsDTO.isNotProject()) {
            if (bizOpportsDTO.getDataDeptIdList() == null || bizOpportsDTO.getDataDeptIdList().size() == 0) {
                return 0;
            }
            total = bizOpportsDao.queryByManagementByKeyWordsTotalForRole(bizOpportsDTO.getEmployeeId(), loginUserDTO.getUserId(), bizOpportsDTO.getKeywords(), ids, bizOpportsDTO.getDataDeptIdList(), bizOpportsDTO.getName(), bizOpportsDTO.getScope());
        } else if (!role && !bizOpportsDTO.isPersonal() && !bizOpportsDTO.isNotProject()) {
            total = bizOpportsDao.queryByManagementByKeyWordsTotal(bizOpportsDTO.getKeywords(), loginUserDTO.getUserId(), bizOpportsDTO.getName());
        } else if (bizOpportsDTO.isPersonal() && bizOpportsDTO.isNotProject()) {
            total = bizOpportsDao.queryByPersonalTotal(loginUserDTO.getUserId(), bizOpportsDTO.getName(), bizOpportsDTO.getKeywords());
        }
        return total;
    }


    /**
     * 按条件统计
     *
     * @param deptIds
     * @param onTime
     * @param loginUserDTO
     * @return
     */
    @Override
    public List<BizOpportsStatisticGroupVo> statistics(Set<String> deptIds, int onTime, String employeeId, LoginUserDTO loginUserDTO) {


        OnTimeEnum onTimeEnum = OnTimeEnum.convert(onTime);
        try {
            Objects.requireNonNull(onTimeEnum);
        } catch (Exception e) {
            throw new MsgException("传入时间类型有误");
        }

        if (deptIds == null) {
            deptIds = bizOpportsDao.getDeptIdsByEmployeeId(employeeId);
        }
        LOG.info("根据 {} 统计商机信息", onTimeEnum.getDescription());

        //仅时间段分组使用开始时间
        LocalDate localDate = LocalDate.now();
        List<GroupTuple> groupEntry = new ArrayList<>();
        switch (onTimeEnum) {

            case ALL:

                BizOpports bizOpports = bizOpportsDao.findFirstBizOpports();

                if (bizOpports != null) {
                    LocalDate first = bizOpports.getCreateTime()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .toLocalDate();

                    while (first.getYear() <= localDate.getYear()) {
                        groupEntry.add(GroupTuple.of(DateTimeFormatter.ofPattern(DateUtils.YEAR).format(first), null));
                        first = first.plusYears(1);
                    }
                } else {
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
                    String startValue1 = null;
                    String endValue2 = null;

                    if (startDate.getDayOfWeek().getValue() == 7) {
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
                for (int i = 0; i < (monthDay - 1); i++) {
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

        List<BizOpportsStatisticGroupVo> bizOpportsStatisticGroupVos = new ArrayList<>();
        switch (onTimeEnum) {


            case ALL:
                for (String deptId : deptIds) {
                    BizOpportsStatisticGroupVo groupVo = new BizOpportsStatisticGroupVo();
                    final List<BizOpportsStatisticVo> bizOpportsStatisticVos = bizOpportsDao.statisticsOfGroup(groupEntry, 2, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);
                    if (sysDepartment != null) {
                        groupVo.setCategoryAndData(bizOpportsStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        bizOpportsStatisticGroupVos.add(groupVo);
                    } else {
                        LOG.error("出现数据错误,商机数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
                    }
                }
                break;

            case QUARTER:
                for (String deptId : deptIds) {
                    BizOpportsStatisticGroupVo groupVo = new BizOpportsStatisticGroupVo();
                    final List<BizOpportsStatisticVo> bizOpportsStatisticVos = bizOpportsDao.statisticsOfPeriod(groupEntry, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);
                    if (sysDepartment != null) {
                        groupVo.setCategoryAndData(bizOpportsStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        bizOpportsStatisticGroupVos.add(groupVo);
                    } else {
                        LOG.error("出现数据错误,商机数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
                    }
                    for (int i = 0; i < bizOpportsStatisticVos.size(); i++) {
                        bizOpportsStatisticVos.get(i).setDate(String.format("第%d周", (i + 1)));
                    }
                }
                break;
            case MONTH:
            case WEEK:
                for (String deptId : deptIds) {
                    BizOpportsStatisticGroupVo groupVo = new BizOpportsStatisticGroupVo();
                    final List<BizOpportsStatisticVo> bizOpportsStatisticVos = bizOpportsDao.statisticsOfGroup(groupEntry, 0, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);
                    if (sysDepartment != null) {
                        groupVo.setCategoryAndData(bizOpportsStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        bizOpportsStatisticGroupVos.add(groupVo);
                    } else {
                        LOG.error("出现数据错误,商机数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
                    }
                }
                break;
            case YEAR:
                for (String deptId : deptIds) {
                    BizOpportsStatisticGroupVo groupVo = new BizOpportsStatisticGroupVo();
                    final List<BizOpportsStatisticVo> bizOpportsStatisticVos = bizOpportsDao.statisticsOfGroup(groupEntry, 1, deptId, employeeId, loginUserDTO);
                    SysDepartment sysDepartment = departmentDao.selectByDeptId(deptId);
                    if (sysDepartment != null) {
                        groupVo.setCategoryAndData(bizOpportsStatisticVos);
                        groupVo.setName(sysDepartment.getDeptName());
                        bizOpportsStatisticGroupVos.add(groupVo);
                    } else {
                        LOG.error("出现数据错误,商机数据统计,此 {} 部门数据不存在, 统计将丢弃", deptId);
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + onTimeEnum);
        }
        return bizOpportsStatisticGroupVos;
    }

    /**
     * 获取与销售相关的客户
     *
     * @param loginUserDTO
     * @return
     */
    @Override
    public List<Map<String, Object>> queryCustomerByUser(LoginUserDTO loginUserDTO) {
        return bizOpportsDao.queryCustomerByUser(loginUserDTO.getUserId());
    }

    /**
     * 功能描述: 获取相关销售
     *
     * @Param: [loginUserDTO]
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: liwenyi
     */
    @Override
    public List<Map<String, Object>> querysaleByUser(LoginUserDTO loginUserDTO) {
        boolean role = isRole(loginUserDTO);
        List<String> ids = new ArrayList<>();
        ids.addAll(loginUserDTO.getDataDeptIdList());
        List<Map<String, Object>> result = null;
        if (role) {
            result = bizOpportsDao.querySaleForRole(ids);
        }
        return result;
    }

    /**
     * @param customer_id
     * @return
     * @author zhangyongfei Feb 26, 2020 9:08:44 PM
     * @version 1.0
     * @see com.sefonsoft.oa.service.bizopports.BizOpportsService getCustomerNameId(com.sefonsoft.oa.domain.project.CustomerNameIdDTO)
     */
    @Override
    public List<CustomerInfoQueryDTO> getCustomerNameId(String customer_id, String employee_id) {
        // TODO Auto-generated method stub
        return bizOpportsDao.customerInfoList(customer_id, employee_id);
    }

    /**
     * 查询公司所有销售
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> queryAllSale() {
        return bizOpportsDao.queryAllSale();
    }

    /**
     * 指派商机-查询
     *
     * @return
     */
    @Override
    public List<BizOpports> queryAllBiz(BizOpportsDTO bizOpportsDTO, List<String> ids) {
        return bizOpportsDao.queryAllBiz(bizOpportsDTO, ids);
    }

    /**
     * 指派商机-查询
     *
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public int queryAllBizTotal(BizOpportsDTO bizOpportsDTO, List<String> ids) {
        return bizOpportsDao.queryAllBizTotal(bizOpportsDTO, ids);
    }

    /**
     * 交付售前获取自己创建的商机列表
     *
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    @Override
    public List<BizOpports> queryPreSaleAll(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO) {
        return bizOpportsDao.queryPreSaleAll(loginUserDTO.getUserId(), bizOpportsDTO.getPage(), bizOpportsDTO.getLimit(), bizOpportsDTO.getKeywords(), bizOpportsDTO.getName());
    }

    /**
     * 交付售前获取自己创建的商机列表数量
     *
     * @param loginUserDTO
     * @return
     */
    @Override
    public int queryPreSaleAllTotal(LoginUserDTO loginUserDTO, String keywords, String name) {
        return bizOpportsDao.queryPreSaleAllTotal(loginUserDTO.getUserId(), keywords, name);
    }

    /**
     * 功能描述: 查询某个商机关联的立项或者派工单总量
     *
     * @Param: [id]
     * @Return: int
     * @Author: liwenyi
     */
    @Override
    public int relatedTotal(Long id) {
        return bizOpportsDao.relatedTotal(id);
    }

    @Override
    public List<BizOpportInfo> getBizOpportInfos(String employeeId) {
        return bizOpportsDao.queryBizOpportInfos(employeeId);
    }

    @Override
    public int getBizopportsWorkerOrderCount(Long id) {
        return bizOpportsDao.findBizopportsWorkerOrderCount(id);
    }
}
