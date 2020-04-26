package com.sefonsoft.oa.dao.head;

import com.sefonsoft.oa.domain.head.HeadQueryDTO;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/13 15:21
 * @description：首页客户和项目相关的接口的dao层
 * @modified By：
 */
public interface HeadPageDao {

    Integer customersCount(HeadQueryDTO headQueryDTO);

    Integer projectsCount(HeadQueryDTO headQueryDTO);

    Integer saleProjectsCount(HeadQueryDTO headQueryDTO);

    Integer contractCount(HeadQueryDTO headQueryDTO);
}
