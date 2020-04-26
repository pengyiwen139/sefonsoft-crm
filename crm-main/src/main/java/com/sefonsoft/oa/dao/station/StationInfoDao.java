package com.sefonsoft.oa.dao.station;

import com.sefonsoft.oa.domain.station.*;
import com.sefonsoft.oa.entity.station.StationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (StationInfo)表数据库访问层
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:23
 */
public interface StationInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    StationInfoQueryDTO queryById(Integer stationId);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<StationInfoReturnDTO> list(StationInfoQueryParamDTO queryAllByLimit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param stationInfo 实例对象
     * @return 对象列表
     */
    List<StationInfo> queryAll(StationInfo stationInfo);

    /**
     * 新增数据
     *
     * @param stationInfo 实例对象
     * @return 影响行数
     */
    int insert(StationInfo stationInfo);

    /**
     * 修改数据
     *
     * @param stationInfo 实例对象
     * @return 影响行数
     */
    int update(StationInfo stationInfo);

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 影响行数
     */
    int deleteById(Integer stationId);

    int count(StationInfoQueryParamDTO queryAllByLimit);

    boolean insertGradeInfo(GradeInfoInsertDTO gradeInfoInsertDTO);

    boolean batchInsertStation(@Param("stationInfoList") List<StationInfo> stationInfoList);

    List<GradeInfoReturnDTO> listGrade(GradeInfoQueryDTO gradeInfoQueryDTO);

    Integer countGrade();

    GradeInfoReturnDTO getGradeById(Integer id);

    boolean updateGradeInfo(GradeInfoUpdateDTO setModifiedTime);

    boolean batchUpdateStationInfo(@Param("stationList") List<StationInfoUpdateDTO> stationList);

    boolean batchGradeInfo(@Param("idList") String[] split);

    int countGradeByGradeId(String gradeId);

    List<StationInfo> getStationListByGradeId(String gradeId);

    List<String> batchGetGradeIdById(@Param("idList") String[] split);

    int countInUsed(@Param("gradeIdList") List<String> gradeIdList);

    boolean delByGradeId(String gradeId);

    boolean batchInsertStationInUpdate(@Param("stationList") List<StationInfoUpdateDTO> stationList);
}