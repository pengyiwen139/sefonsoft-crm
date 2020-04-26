package com.sefonsoft.oa.service.station;

import com.sefonsoft.oa.domain.station.*;
import com.sefonsoft.oa.entity.station.StationInfo;
import com.sefonsoft.oa.system.utils.PageResponse;

import java.util.List;

/**
 * (StationInfo)表服务接口
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:24
 */
public interface StationInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    StationInfoQueryDTO queryById(Integer stationId);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    PageResponse list(GradeInfoQueryDTO gradeInfoQueryDTO);

    /**
     * 新增数据
     *
     * @param gradeInfoInsertDTO 实例对象
     * @return 实例对象
     */
    boolean insert(GradeInfoInsertDTO gradeInfoInsertDTO);

    /**
     * 修改数据
     *
     * @param stationInfo 实例对象
     * @return 实例对象
     */
    int update(StationInfo stationInfo);

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer stationId);

    GradeInfoReturnDTO getGradeById(Integer id);

    boolean updateGrade(GradeInfoUpdateDTO gradeInfoUpdateDTO);

    boolean batchGradeInfo(String[] split);

    int countGradeByGradeId(String gradeId);

    List<String> batchGetGradeIdById(String[] split);

    int countInUsed(List<String> gradeIdList);
}