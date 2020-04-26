package com.sefonsoft.oa.service.importexport;

import java.util.List;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;

public interface CustomerShareInsertService {
  
  public boolean insertCustomerShareAndBatchContacts(CustomerInfo customerInfo, List<ContactInfo> contactInfoList, LoginUserDTO loginUserDTO);


}
