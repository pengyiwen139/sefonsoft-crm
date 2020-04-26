package com.sefonsoft.oa.service.head;

import com.sefonsoft.oa.dao.head.HeadPageDao;
import com.sefonsoft.oa.domain.head.HeadQueryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/13 15:19
 * @description：首页客户和项目相关的接口的服务层
 * @modified By：
 */
@Service
public class HeadPageServiceImpl implements HeadPageService {

    @Resource
    private HeadPageDao headPageDao;

    @Override
    public Integer customersCount(HeadQueryDTO headQueryDTO) {
        return headPageDao.customersCount(headQueryDTO);
    }

    @Override
    public Integer projectsCount(HeadQueryDTO headQueryDTO) {
        return headPageDao.projectsCount(headQueryDTO);
    }

    @Override
    public Integer saleProjectsCount(HeadQueryDTO headQueryDTO) {
        return headPageDao.saleProjectsCount(headQueryDTO);
    }

    @Override
    public Integer contractCount(HeadQueryDTO headQueryDTO) {
        return headPageDao.contractCount(headQueryDTO);
    }
}