package com.sefonsoft.oa.service.common;

import com.sefonsoft.oa.entity.customer.EnterpriseType;

import java.util.List;

/**
 * 公共使用的service层
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
public interface CommonService {

    List<EnterpriseType> getEnterpriseTypeList(EnterpriseType enterpriseType);

}