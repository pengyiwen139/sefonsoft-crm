package com.sefonsoft.oa.service;

import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractChartDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractExportDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractInsertDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedParamDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractUpdateDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ExpectContractInfo;
import com.sefonsoft.oa.system.utils.PageResponse;

import java.util.List;

/**
 * (ExpectContractInfo)表服务接口
 *
 * @author makejava
 * @since 2020-04-17 15:47:25
 */
public interface ExpectContractInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExpectContractInfo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExpectContractInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param expectContractInfo 实例对象
     * @return 实例对象
     */
    boolean insert(ExpectContractInsertDTO expectContractInfo, LoginUserDTO loginUserDTO);

    /**
     * 修改数据
     *
     * @param expectContractUpdateDTO 实例对象
     * @param loginUserDTO
     * @return 实例对象
     */
    boolean update(ExpectContractUpdateDTO expectContractUpdateDTO, LoginUserDTO loginUserDTO);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 获取新增编辑页面所需数据（负责人为当前用户的立项通过项目list和当前用户的客户list）
     * @param loginUserDTO
     * @return
     */
    ExpectContractNeedParamDTO getNeedParams(LoginUserDTO loginUserDTO);

    /**
     * 批量删除合同预测
     * @param idArray
     * @return
     */
    boolean batchDeleteById(List<Long> idArray);

    /**
     * 列表查询合同预测

     * @param loginUserDTO
     * @param expectContractSearchDTO
     * @return
     */
    PageResponse list(LoginUserDTO loginUserDTO, ExpectContractSearchDTO expectContractSearchDTO);

    /**
     * 查询合同预测列表页统计图封装数据
     * @param expectContractSearchDTO
     * @return
     */
    ExpectContractChartDTO getExpectContractChart(ExpectContractSearchDTO expectContractSearchDTO);

    List<ExpectContractExportDTO> listExportNoPage(LoginUserDTO loginUserDTO, ExpectContractSearchDTO expectContractSearchDTO);

}