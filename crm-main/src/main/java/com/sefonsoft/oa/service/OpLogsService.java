package com.sefonsoft.oa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sefonsoft.oa.dao.log.OpLogsDao;
import com.sefonsoft.oa.entity.OpLogs;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * op_logs
 *
 * @author
 */
@Service
public class OpLogsService {

  @Autowired
  private OpLogsDao opLogsDao;


  /**
   * 添加日志
   *
   * @param opLogs
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void addOpLogs(OpLogs opLogs) {
    opLogsDao.insert(opLogs);
  }

  /**
   * 查询日志
   *
   * @param page
   * @param pageSize
   * @return
   */
  public PageResponse getOpLogList(int page, int pageSize) {

    PageInfo pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> {
      opLogsDao.findAll();
    });
    return new PageResponse((int) pageInfo.getTotal(), pageInfo.getList());
  }

}