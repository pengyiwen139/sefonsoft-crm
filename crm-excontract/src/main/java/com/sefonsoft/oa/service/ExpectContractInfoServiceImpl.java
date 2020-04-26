package com.sefonsoft.oa.service;


import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractChartDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractExportDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractInsertDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedCustomerDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedParamDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractNeedProjectDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractQueryDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractRealConstractPair;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractSearchDTO;
import com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractUpdateDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contract.ExpectContractInfo;
import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import com.sefonsoft.oa.dao.ExpectContractInfoDao;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * (ExpectContractInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-04-17 15:47:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpectContractInfoServiceImpl implements ExpectContractInfoService {
    @Resource
    private ExpectContractInfoDao expectContractInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExpectContractInfo queryById(Long id) {
        return this.expectContractInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ExpectContractInfo> queryAllByLimit(int offset, int limit) {
        return this.expectContractInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param expectContractInfo 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(ExpectContractInsertDTO expectContractInfo, LoginUserDTO loginUserDTO) {
        boolean flag = false;
        Date date = new Date();
        //插入合同预测主表
        Long id = expectContractInfoDao.insert(expectContractInfo.setEstimateTime(expectContractInfo.getEstimateTime()+"-01").setOperator(loginUserDTO.getUserId()).setCreateTime(date).setLastTime(date));
        if (ObjTool.isNotNull(id)) {
            List<ExpectContractProductRef> contractProductRefList = expectContractInfo.getContractProductRefList();
            if (ObjTool.isNotNull(contractProductRefList)) {
                contractProductRefList.forEach(bean -> bean.setExpectContractId(expectContractInfo.getId()).setOperator(loginUserDTO.getUserId()).setCreateTime(date).setLastTime(date));
                //插入合同预测产品价格关联表
                int countSlaveInsertFlag = expectContractInfoDao.batchInsertExpectContractProducts(contractProductRefList);
                if (countSlaveInsertFlag > 0)
                    flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改数据
     *
     * @param expectContractUpdateDTO 实例对象
     * @param loginUserDTO
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(ExpectContractUpdateDTO expectContractUpdateDTO, LoginUserDTO loginUserDTO) {
        //删除产品关联表相关数据
        if (expectContractInfoDao.batchdeleteProductRefByLojiFkId(expectContractUpdateDTO.getId()) > 0) {
            //更新主表
            if (expectContractInfoDao.update(expectContractUpdateDTO.setOperator(loginUserDTO.getUserId()).setLastTime(new Date()))) {
                //新增产品关联表的数据
                return expectContractInfoDao.batchInsertExpectContractProducts(expectContractUpdateDTO.getContractProductRefList()) > 0;
            }
        }
        return false;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.expectContractInfoDao.deleteById(id) > 0;
    }

    /**
     * 获取新增编辑页面所需数据（负责人为当前用户的立项通过项目list和当前用户的客户list）
     * @param loginUserDTO
     * @return
     */
    @Override
    public ExpectContractNeedParamDTO getNeedParams(LoginUserDTO loginUserDTO) {
        List<ExpectContractNeedProjectDTO> projectList = expectContractInfoDao.getNeedProjectParams(loginUserDTO.getUserId());
        List<ExpectContractNeedCustomerDTO> customerList = expectContractInfoDao.getNeedCustomerParams(loginUserDTO.getUserId());
        return new ExpectContractNeedParamDTO(projectList, customerList);
    }

    /**
     * 批量删除合同预测
     * @param idArray
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchDeleteById(List<Long> idArray) {
        boolean a = expectContractInfoDao.batchDeleteById(idArray);
        if (a) {
            return expectContractInfoDao.batchDeleteProductRefByExpectId(idArray);
        }
        return false;
    }

    @Override
    public PageResponse list(LoginUserDTO loginUserDTO, ExpectContractSearchDTO expectContractSearchDTO) {
        List<String> userDeptList = loginUserDTO.getDataDeptIdList();
        List<String> dataDeptIdList = expectContractSearchDTO.getDataDeptIdList();
        //数据权限为空，则只查自己
        if (!ObjTool.isNotNull(userDeptList)) {
            expectContractSearchDTO.setEmployeeId(loginUserDTO.getUserId());
        } else if (ObjTool.isNotNull(userDeptList) && ObjTool.isNotNull(dataDeptIdList) && userDeptList.size() == dataDeptIdList.size()) {
            //当前用户部门权限和前端所传的部门权限个数一致，且都大于0，则视为查询全部数据，在查出全部部门相关数据同时，也要查出负责人为当前用户的数据
            expectContractSearchDTO.setEmployeeId(loginUserDTO.getUserId());
        } else if (ObjTool.isNotNull(userDeptList) && ObjTool.isNotNull(dataDeptIdList) && userDeptList.size() != dataDeptIdList.size()) {
            //当前用户部门权限和前端所传的部门权限个数不一致，且都大于0，则将前端传来的部门数据视为需要查看到的数据
        }
        List<ExpectContractQueryDTO> list = expectContractInfoDao.listPage(expectContractSearchDTO);
        list.forEach(bean -> bean.setEstimateTime(bean.getEstimateTime().substring(0,7)));
        int count = expectContractInfoDao.count(expectContractSearchDTO);
        return new PageResponse<>(count, list);
    }

    @Override
    public com.sefonsoft.oa.domain.contract.dto.expect.ExpectContractChartDTO getExpectContractChart(ExpectContractSearchDTO expectContractSearchDTO) {
        //获取所有总和的预测金额
        BigDecimal allSum = expectContractInfoDao.getExpectContractChartAllSum(expectContractSearchDTO);
        //每月的预估合同金额和实际合同金额
        List<ExpectContractRealConstractPair> list = expectContractInfoDao.getExpectContractChartSumMonth(expectContractSearchDTO);
        return new ExpectContractChartDTO(allSum, list);
    }

    @Override
    public List<ExpectContractExportDTO> listExportNoPage(LoginUserDTO loginUserDTO, ExpectContractSearchDTO expectContractSearchDTO) {
        return expectContractInfoDao.listExportPage(expectContractSearchDTO);
    }

}