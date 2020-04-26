package com.sefonsoft.oa.dao.report;

import com.sefonsoft.oa.domain.report.DailyReportInfoInsertOrUpdateInfo;
import com.sefonsoft.oa.entity.report.DailyReportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日报表(DailyReportInfoDao)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-12-09 11:22:05
 */
public interface DailyReportInfoDao {

    /**
     * 新增日报信息数据
     *
     * @param reportInfo 实例对象
     * @return 影响行数
     */
    int insert(DailyReportInfoInsertOrUpdateInfo reportInfo);

    /**
     * 通过
     *
     * @param reportId 日报id
     * @return 影响行数
     */
    List<DailyReportInfo> queryByReportId(@Param("reportId") Long reportId);

    /**
     * 修改数据
     *
     * @param reportInfoUpdateInfo 实例对象
     * @return 影响行数
     */
    boolean update(DailyReportInfoInsertOrUpdateInfo reportInfoUpdateInfo);

}