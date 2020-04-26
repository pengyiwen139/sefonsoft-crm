package com.sefonsoft.oa.service.trans;

import com.sefonsoft.oa.dao.trans.TransDao;
import com.sefonsoft.oa.dao.trans.TransExtTransmatnDao;
import com.sefonsoft.oa.domain.trans.TransCondition;
import com.sefonsoft.oa.domain.trans.TransDto;
import com.sefonsoft.oa.system.utils.UUIDUtil;
import com.sefonsoft.oa.entity.trans.Trans;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Trans业务实现
 *
 * @author Aron
 * @version 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransServiceImpl implements TransService {
    @Autowired
    private TransDao transDao;
    @Autowired
    private TransExtTransmatnDao transExtTransmatnDao;


    /**
     * 新增Trans
     *
     * @param transDto Trans信息
     * @return Trans信息
     */
    @Override
    public TransDto addTrans(TransDto transDto) throws Exception {

        Long uuid=  UUIDUtil.getRand();
        transDto.setId(uuid);

        Trans trans = new Trans();
        BeanUtils.copyProperties(transDto, trans);
        transDao.insert(trans);
        return transDto;
    }

    @Override
    public int deleteTrans(Long id) {
        return transDao.deleteByPrimaryKey(id);
    }

    @Override
    public TransDto editTrans(TransDto transDto) {
        Trans trans = new Trans();
        BeanUtils.copyProperties(transDto, trans);
        transDao.updateByPrimaryKeySelective(trans);
        return transDto;
    }

    @Override
    public TransDto findTransById(Long id) {
        TransDto transDto = new TransDto();
        Trans trans = transDao.selectByPrimaryKey(id);
        BeanUtils.copyProperties(trans, transDto);
        return transDto;
    }

    @Override
    public List<TransDto> getTransListByCondition(TransCondition transCondition) {
        return transExtTransmatnDao.selectTransByCondition(transCondition);
    }

    @Override
    public int findTranTotalByCondition(TransCondition transCondition) {
        return transExtTransmatnDao.selectTransTotalByCondition(transCondition);
    }
}
