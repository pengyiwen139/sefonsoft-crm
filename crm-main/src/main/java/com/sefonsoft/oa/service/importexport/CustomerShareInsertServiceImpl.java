package com.sefonsoft.oa.service.importexport;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sefonsoft.oa.dao.customer.CustomerShareDao;
import com.sefonsoft.oa.domain.customer.vo.CustomerInfoQueryVo;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.service.customer.CustomerShareService;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.utils.ObjTool;

@Service
public class CustomerShareInsertServiceImpl implements CustomerShareInsertService {

  @Resource
  CustomerShareDao customerShareDao;
  
  @Resource
  CustomerShareService customerShareService;

  /**
   * 导入客户和联系人excel的时候添加客户和联系人数据
   *
   * @param customerInfo
   * @param contactInfoList
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public boolean insertCustomerShareAndBatchContacts(CustomerInfo customerInfo, List<ContactInfo> contactInfoList,
      LoginUserDTO loginUserDTO) {
    Date date = new Date();
    if (ObjTool.isNotNull(customerInfo, contactInfoList)) {
      // 生成customerId并判断customerId是否是重复的
      String customerId = generateCustomerShareId();
      if (ObjTool.isNotNull(customerId)) {
        customerInfo.setCustomerId(customerId);
        customerInfo.setLastTime(date);
        customerInfo.setCreateTime(date);
        // 没有提供操作员，那么默认当前
        if (customerInfo.getOperator() == null) {
          customerInfo.setOperator(loginUserDTO.getUserId());
        }

        int insertCount = customerShareDao.insert(customerInfo);
        contactInfoList.forEach(bean -> {
          bean.setCustomerId(customerId);
          bean.setLastTime(date);
          bean.setCreateTime(date);
          // 没有提供操作员，那么默认当前
          if (bean.getOperator() == null) {
            bean.setOperator(loginUserDTO.getUserId());
          }
          bean.setRole(1);
        });

        List<CustomerInfoQueryVo> size = customerShareDao.queryByCustomerName(customerInfo.getCustomerName());

        if (size.size() > 1) {
          throw new MsgException("客户名称重复");
        }

        boolean flag = customerShareDao.batchInsert(contactInfoList);
        return insertCount > 0 && flag;
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * 生成customerId并判断customerId是否是重复的
   *
   * @return
   */
  private String generateCustomerShareId() {
    // 查询客户最大编码
    String maxCode = customerShareDao.getMaxCustomerId();
    // 如果查询数据库没有客户记录，设置maxCode默认值
    if (null == maxCode) {
      maxCode = "SKH201911220000";
    }
    // 最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
    String customerId = customerShareService.getKhCode(maxCode);
    int customerIdCount = customerShareDao.checkUnique(customerId);
    if (customerIdCount > 0) {
      return null;
    }
    return customerId;
  }

  
  
  public CustomerShareDao getCustomerShareDao() {
    return customerShareDao;
  }

  public void setCustomerShareDao(CustomerShareDao customerShareDao) {
    this.customerShareDao = customerShareDao;
  }

  public CustomerShareService getCustomerShareService() {
    return customerShareService;
  }

  public void setCustomerShareService(CustomerShareService customerShareService) {
    this.customerShareService = customerShareService;
  }
  
  
  
}
