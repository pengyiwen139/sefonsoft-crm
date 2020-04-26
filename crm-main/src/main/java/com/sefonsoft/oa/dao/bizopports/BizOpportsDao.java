package com.sefonsoft.oa.dao.bizopports;

import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticVo;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.statistic.GroupTuple;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.workorder.BizOpportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liwenyi
 * Date: 2020/2/20
 * Desc: (biz_opports)表数据库访问层
 */
public interface BizOpportsDao {

  /**
   * 新增商机
   *
   * @param bizOpportsDTO
   * @return
   */
  int insert(BizOpportsDTO bizOpportsDTO);

  /**
   * 修改商机
   *
   * @param bizOpportsDTO
   * @return
   */
  int update(BizOpportsDTO bizOpportsDTO);

  /**
   * 根据id查询商机详情
   *
   * @param id
   * @return
   */
  BizOpports queryById(Long id);

  /**
   * 删除商机
   *
   * @param id
   * @return
   */
  int deleteById(Long id);

  /**
   * 个人销售根据关键字查看商机信息
   *
   * @param keywords
   * @param employeeId
   * @return
   */
  List<BizOpports> queryByManagementByKeyWords(@Param("keywords") String keywords, @Param("employeeId") String employeeId, @Param("page") Integer page, @Param("limit") Integer limit, @Param("name") String name);

  /**
   * 大区领导或销售管理部根据关键字查看商机信息
   *
   * @param employeeId
   * @param userId
   * @param keywords
   * @param ids
   * @param dataDeptIdList
   * @param page
   * @param limit
   * @return
   */
  List<BizOpports> queryByManagementByKeyWordsForRole(@Param("employeeId") String employeeId, @Param("userId") String userId,
                                                      @Param("keywords") String keywords, @Param("ids") List<String> ids,
                                                      @Param("dataDeptIdList") List<String> dataDeptIdList, @Param("page") Integer page,
                                                      @Param("limit") Integer limit, @Param("name") String name,
                                                      @Param("scope") Integer scope);


  /**
   * 个人销售根据关键字查看商机信息数据量
   *
   * @param keywords
   * @param employeeId
   * @return
   */
  int queryByManagementByKeyWordsTotal(@Param("keywords") String keywords, @Param("employeeId") String employeeId, @Param("name") String name);

  /**
   * 大区领导销售管理部根据关键字查看商机信息数据量
   *
   * @param employeeId
   * @param keywords
   * @param ids
   * @return
   */
  int queryByManagementByKeyWordsTotalForRole(@Param("employeeId") String employeeId, @Param("userId") String userId,
                                              @Param("keywords") String keywords, @Param("ids") List<String> ids,
                                              @Param("dataDeptIdList") List<String> dataDeptIdList, @Param("name") String name,
                                              @Param("scope") Integer scope);


  /**
   * xielf
   *
   * @return
   */
  BizOpports findFirstBizOpports();

  /**
   * 统计 普通分组
   * xielf
   *
   * @param entryList
   * @param groupType    0按天数, 1按月, 2按年
   * @param deptId
   * @param loginUserDTO
   * @return
   */
  List<BizOpportsStatisticVo> statisticsOfGroup(@Param("groupDateList") List<GroupTuple> entryList,
                                                @Param("groupType") int groupType,
                                                @Param("deptId") String deptId,
                                                @Param("employeeId") String employeeId,
                                                @Param("loginUser") LoginUserDTO loginUserDTO);

  /**
   * 统计 时间段组
   * xielf
   *
   * @param entryList
   * @param deptId
   * @return
   */
  List<BizOpportsStatisticVo> statisticsOfPeriod(@Param("groupDateList") List<GroupTuple> entryList,
                                                 @Param("deptId") String deptId,
                                                 @Param("employeeId") String employeeId,
                                                 @Param("loginUser") LoginUserDTO loginUserDTO);


  /**
   * 统计个数
   * xielf
   *
   * @param deptIds
   * @return
   */
  int statisticCount(@Param("deptIds") Set<String> deptIds,
                     @Param("employeeId") String employeeId);

  /**
   * 按时间统计个数
   * xielf
   *
   * @param deptIds
   * @return
   */
  int onTimeStatisticCount(@Param("deptIds") Set<String> deptIds,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("employeeId") String employeeId);


  /**
   * 总数量
   * xielf
   *
   * @return
   */
  int totalCount();


  /**
   * 根据客户和职员查询商机
   * xielf
   *
   * @param employeeId
   * @param customerId
   * @return
   */
  List<BizOpports> queryBizOpportsByEmployeeIdAndCustomerId(@Param("employeeId") String employeeId, @Param("customerId") String customerId);

  /**
   * 修改
   * xielf
   *
   * @param bizOpports
   * @return
   */
  int updateBizOpports(BizOpports bizOpports);

  /**
   * 获取与销售相关的客户
   *
   * @param userId
   * @return
   */
  List<Map<String, Object>> queryCustomerByUser(String userId);

  /**
   * 大区领导或销售管理部获取相关销售
   *
   * @param deptIds
   * @return
   */
  List<Map<String, Object>> querySaleForRole(@Param("deptIds") List<String> deptIds);


  /**
   * 更新描述
   *
   * @param id
   * @param desc
   */
  void updateDesc(@Param("id") Long id, @Param("desc") String desc);

  /**
   * 客户信息名称、编码列表
   *
   * @param customer_id
   * @return
   */
  List<CustomerInfoQueryDTO> customerInfoList(@Param("customer_id") String customer_id, @Param("employee_id") String employee_id);

  /**
   * 查询所有销售
   *
   * @return
   */
  List<Map<String, Object>> queryAllSale();

  /**
   * 查询属于个人且未立项的商机列表
   *
   * @param userId
   * @return
   */
  List<BizOpports> queryByPersonal(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit, @Param("name") String name, @Param("keywords") String keywords);

  /**
   * 查询属于个人且未立项的商机数量
   *
   * @param userId
   * @return
   */
  int queryByPersonalTotal(@Param("userId") String userId, @Param("name") String name, @Param("keywords") String keywords);

  /**
   * 获取所有商机列表
   *
   * @return
   */
  List<BizOpports> queryAllBiz(@Param("biz") BizOpportsDTO bizOpportsDTO, @Param("ids") List<String> ids);

  /**
   * 获取所有商机列表数量
   *
   * @param bizOpportsDTO
   * @return
   */
  int queryAllBizTotal(@Param("biz") BizOpportsDTO bizOpportsDTO, @Param("ids") List<String> ids);

  /**
   * 交付售前获取自己创建的商机列表
   *
   * @param userId
   * @param page
   * @param limit
   * @return
   */
  List<BizOpports> queryPreSaleAll(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit, @Param("keywords") String keywords, @Param("name") String name);

  /**
   * 交付售前获取自己创建的商机列表数量
   *
   * @param userId
   * @return
   */
  int queryPreSaleAllTotal(@Param("userId") String userId, @Param("keywords") String keywords, @Param("name") String name);

  /**
   * 功能描述: 查询某个商机关联的立项或者派工单总量
   *
   * @Param: [id]
   * @Return: int
   * @Author: liwenyi
   */
  int relatedTotal(Long id);


  int findBizOpportsCountByCustomerId(String customerId);

  List<BizOpportInfo> queryBizOpportInfos(String employeeId);


  /**
   * 根据员工查询部门ID
   * xielf
   *
   * @param employeeId
   * @return
   */
  Set<String> getDeptIdsByEmployeeId(@Param("employeeId") String employeeId);

  List<BizOpportsExport> queryAllBizOpportsExport();

  String getMaxSJ(String sjPrefix);

  BizOpports findByBizId(String bizId);

  List<BizOpports> queryByBizId(String bizId);

  int findBizopportsWorkerOrderCount(@Param("bizId") Long bizId);

  /**
   * 更新商机信息，更新时检查商机状态
   *
   * @param bizOpportsDTO 商机信息
   * @return
   */
  int checkAndUpdate(@Param("biz") BizOpportsDTO bizOpportsDTO, @Param("state") int state);

  /**
   *
   * @param status 1 未立项，2 已立项，3立项审核中
   * @return
   */
  int findBizOpportsCountByStatus(int status);

}
