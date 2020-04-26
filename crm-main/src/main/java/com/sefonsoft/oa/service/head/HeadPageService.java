package com.sefonsoft.oa.service.head;

import com.sefonsoft.oa.domain.head.HeadQueryDTO;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/13 15:19
 * @description：首页客户和项目相关的接口服务层
 * @modified By：
 */
public interface HeadPageService {

    Integer customersCount(HeadQueryDTO headQueryDTO);

    Integer projectsCount(HeadQueryDTO headQueryDTO);

    Integer saleProjectsCount(HeadQueryDTO headQueryDTO);

    Integer contractCount(HeadQueryDTO headQueryDTO);

}
