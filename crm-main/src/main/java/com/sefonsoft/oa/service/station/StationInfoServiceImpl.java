package com.sefonsoft.oa.service.station;

import com.alibaba.fastjson.JSONArray;
import com.sefonsoft.oa.dao.station.StationInfoDao;
import com.sefonsoft.oa.domain.station.*;
import com.sefonsoft.oa.entity.station.StationInfo;
import com.sefonsoft.oa.system.utils.DateUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * (StationInfo)表服务实现类
 *
 * @author PengYiWen
 * @since 2019-10-29 19:55:24
 */
@Service("stationInfoService")
@Transactional(rollbackFor = Exception.class)
public class StationInfoServiceImpl implements StationInfoService {

    @Resource
    private StationInfoDao stationInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    @Override
    public StationInfoQueryDTO queryById(Integer stationId) {
        StationInfoQueryDTO stationInfo = stationInfoDao.queryById(stationId);
        return ObjTool.isNotNull(stationInfo) ? stationInfo.setCreateTimeStr(DateUtil.format(stationInfo.getCreateTime()))
                                                           .setModifiedTimeStr(DateUtil.format(stationInfo.getModifiedTime())) : null;
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public PageResponse list(GradeInfoQueryDTO gradeInfoQueryDTO) {
        List<GradeInfoReturnDTO> list = stationInfoDao.listGrade(gradeInfoQueryDTO);

        if (ObjTool.isNotNull(list)) {
            //迭代拼接处前端需要的单个职系相关的多个岗位名称的逗号分隔开的字符串
            list.forEach(gradeInfoReturnDTO -> {
                StringJoiner joiner = new StringJoiner(",");
                List<StationInfo> stationList = gradeInfoReturnDTO.getStationList();
                for (StationInfo stationInfo : stationList) {
                    String stationName = stationInfo.getStationName();
                    joiner.add(stationName);
                }
                gradeInfoReturnDTO.setStationNames(joiner.toString());
            });
        } else {
            return null;
        }
        Integer countGrade = stationInfoDao.countGrade();
        return new PageResponse<>(countGrade, list);
    }

    /**
     * 新增数据
     *
     * @param gradeInfoInsertDTO 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(GradeInfoInsertDTO gradeInfoInsertDTO) {
        Date date = new Date();

        //插入的参数准备，包括时间和stationInfoList的准备
        gradeInfoInsertDTO.setModifiedTime(date).setCreateTime(date);
        List<StationInfo> stationInfoList = JSONArray.parseArray(gradeInfoInsertDTO.getStationList().toJSONString(), StationInfo.class);
        stationInfoList.forEach(stationInfo -> stationInfo.setGradeId(gradeInfoInsertDTO.getGradeId())
                                                          .setModifiedTime(date)
                                                          .setCreateTime(date)
                                                          .setStationId(UUIDUtil.getRandStr(6)));

        //插入操作
        boolean gradeFlag = stationInfoDao.insertGradeInfo(gradeInfoInsertDTO);
        boolean stationInfoFlag = stationInfoDao.batchInsertStation(stationInfoList);

        return gradeFlag && stationInfoFlag;
    }

    /**
     * 修改数据
     *
     * @param stationInfo 实例对象
     * @return 实例对象
     */
    @Override
    public int update(StationInfo stationInfo) {
        Date date = new Date();
        return stationInfoDao.update(stationInfo.setModifiedTime(date));
    }

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer stationId) {
        return this.stationInfoDao.deleteById(stationId) > 0;
    }

    @Override
    public GradeInfoReturnDTO getGradeById(Integer id) {
        GradeInfoReturnDTO gradeById = stationInfoDao.getGradeById(id);
        return ObjTool.isNotNull(gradeById) ? gradeById.setStationList(stationInfoDao.getStationListByGradeId(gradeById.getGradeId())) : null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateGrade(GradeInfoUpdateDTO gradeInfoUpdateDTO) {
        Date date = new Date();
        //更新职系相关字段
        boolean flagGrade = stationInfoDao.updateGradeInfo(gradeInfoUpdateDTO.setModifiedTime(date));
        List<StationInfoUpdateDTO> stationList = gradeInfoUpdateDTO.getStationList();
        String gradeId = gradeInfoUpdateDTO.getGradeId();
        stationList.forEach(bean -> bean.setModifiedTime(date).setCreateTime(date).setGradeId(gradeId).setStationId(UUIDUtil.getRandStr(6)));
        //批量更新岗位相关字段
        //先删除
        boolean delStationFlag = false;
        if (ObjTool.isNotNull(gradeId)) {
            delStationFlag = stationInfoDao.delByGradeId(gradeId);
        }
        //再批量新增
        boolean flagStation = stationInfoDao.batchInsertStationInUpdate(stationList);
        return flagGrade && flagStation && delStationFlag;
    }

    @Override
    public boolean batchGradeInfo(String[] split) {
        return stationInfoDao.batchGradeInfo(split);
    }

    @Override
    public int countGradeByGradeId(String gradeId) {
        return stationInfoDao.countGradeByGradeId(gradeId);
    }

    @Override
    public List<String> batchGetGradeIdById(String[] split) {
        return stationInfoDao.batchGetGradeIdById(split);
    }

    @Override
    public int countInUsed(List<String> gradeIdList) {
        return stationInfoDao.countInUsed(gradeIdList);
    }

}