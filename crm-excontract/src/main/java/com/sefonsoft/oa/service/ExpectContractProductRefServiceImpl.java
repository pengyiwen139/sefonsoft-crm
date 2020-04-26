package com.sefonsoft.oa.service;

import com.sefonsoft.oa.entity.contract.ExpectContractProductRef;
import com.sefonsoft.oa.dao.ExpectContractProductRefDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合同预测产品金额关联表(ExpectContractProductRef)表服务实现类
 *
 * @author makejava
 * @since 2020-04-17 16:23:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpectContractProductRefServiceImpl implements ExpectContractProductRefService {
    @Resource
    private ExpectContractProductRefDao expectContractProductRefDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExpectContractProductRef queryById(Long id) {
        return this.expectContractProductRefDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ExpectContractProductRef> queryAllByLimit(int offset, int limit) {
        return this.expectContractProductRefDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param expectContractProductRef 实例对象
     * @return 实例对象
     */
    @Override
    public ExpectContractProductRef insert(ExpectContractProductRef expectContractProductRef) {
        this.expectContractProductRefDao.insert(expectContractProductRef);
        return expectContractProductRef;
    }

    /**
     * 修改数据
     *
     * @param expectContractProductRef 实例对象
     * @return 实例对象
     */
    @Override
    public ExpectContractProductRef update(ExpectContractProductRef expectContractProductRef) {
        this.expectContractProductRefDao.update(expectContractProductRef);
        return this.queryById(expectContractProductRef.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.expectContractProductRefDao.deleteById(id) > 0;
    }
}