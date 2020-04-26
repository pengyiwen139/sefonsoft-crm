package com.sefonsoft.oa.dao;

import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractExportDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractInsertDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedCustomerDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedProjectDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractQueryDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractRealConstractPair;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractUpdateDTO;
import com.sefonsoft.oa.entity.contract.ExpectContractInfo;
import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * (ExpectContractInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-17 15:47:25
 */
@Mapper
public interface ExpectContractInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExpectContractInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExpectContractInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param expectContractInfo 实例对象
     * @return 对象列表
     */
    List<ExpectContractInfo> queryAll(ExpectContractInfo expectContractInfo);

    /**
     * 新增数据
     *
     * @param expectContractInfo 实例对象
     * @return 影响行数
     */
    Long insert(ExpectContractInsertDTO expectContractInfo);

    /**
     * 修改数据
     *
     * @param expectContractUpdateDTO 实例对象
     * @return 影响行数
     */
    boolean update(ExpectContractUpdateDTO expectContractUpdateDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int batchInsertExpectContractProducts(@Param("list") List<ExpectContractProductRef> contractProductRefList);

    List<ExpectContractNeedProjectDTO> getNeedProjectParams(String userId);

    List<ExpectContractNeedCustomerDTO> getNeedCustomerParams(String userId);

    /**
     * 批量删除合同预测
     * @param idArray
     * @return
     */
    boolean batchDeleteById(@Param("idArray") List<Long> idArray);

    List<ExpectContractQueryDTO> listPage(com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO expectContractSearchDTO);

    List<ExpectContractExportDTO> listExportPage(ExpectContractSearchDTO expectContractSearchDTO);

    int count(ExpectContractSearchDTO expectContractSearchDTO);

    int batchdeleteProductRefByLojiFkId(Long id);

    BigDecimal getExpectContractChartAllSum(ExpectContractSearchDTO expectContractSearchDTO);

    List<ExpectContractRealConstractPair> getExpectContractChartSumMonth(ExpectContractSearchDTO expectContractSearchDTO);

    boolean batchDeleteProductRefByExpectId(@Param("idArray") List<Long> idArray);
}