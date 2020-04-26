package com.sefonsoft.oa.service.common;

import com.sefonsoft.oa.dao.common.CommonDao;
import com.sefonsoft.oa.entity.customer.EnterpriseType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CustomerInfo)表服务实现类
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonDao commonDao;

    @Override
    public List<EnterpriseType> getEnterpriseTypeList(EnterpriseType enterpriseType) {
        List<EnterpriseType> enterpriseTypes = commonDao.queryAllEnterpriseType(enterpriseType);
        return enterpriseTypes;
    }

}