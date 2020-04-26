package com.sefonsoft.oa.dao.workorder;

import com.sefonsoft.oa.entity.workorder.WorkorderFeedbackSaleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkorderFeedbackSaleInfoDao {

    int deleteByPrimaryKey(Long id);

    int insert(WorkorderFeedbackSaleInfo record);

    int insertBatch(@Param("records") List<WorkorderFeedbackSaleInfo> records);

    int insertSelective(WorkorderFeedbackSaleInfo record);

    WorkorderFeedbackSaleInfo selectByPrimaryKey(Long id);

  /**
   * 功能描述: 根据派工单号查询反馈列表
   *
   * @Param: pgdId
   * @Return: WorkorderFeedbackSaleInfo
   */
    List<WorkorderFeedbackSaleInfo> selectByPgdId(String pgdId);

    int updateByPrimaryKeySelective(WorkorderFeedbackSaleInfo record);

    int updateBatchByPrimaryKeySelective(@Param("records") List<WorkorderFeedbackSaleInfo> records);

    int updateByPrimaryKey(WorkorderFeedbackSaleInfo record);

    List<WorkorderFeedbackSaleInfo> findSaleFeedbackByPgdId(@Param("pgdId") String pgdId);
}