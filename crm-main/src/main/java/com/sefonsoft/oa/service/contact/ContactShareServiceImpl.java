package com.sefonsoft.oa.service.contact;

import com.sefonsoft.oa.dao.contact.ContactInfoDao;
import com.sefonsoft.oa.dao.contact.ContactShareDao;
import com.sefonsoft.oa.domain.contact.ContactInfoQueryDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (ContactShare)表服务实现类
 *
 * @author Aron
 * @since 2019-11-16 17:26:43
 */
@Service("contactShareService")
@Transactional(rollbackFor = Exception.class)
public class ContactShareServiceImpl implements ContactShareService {
    @Resource
    private ContactShareDao contactShareDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ContactInfo queryById(Long id) {
        return this.contactShareDao.queryById(id);
    }

    @Override
    public List<ContactInfo> queryContactInfoByCustomerId(String customerId) {

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setCustomerId(customerId);
        return contactShareDao.queryAll(contactInfo);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ContactInfo> queryAllByLimit(int offset, int limit) {
        return this.contactShareDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param contactInfo 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(ContactInfo contactInfo) {
        Date date = new Date();

        contactInfo.setCreateTime(date);
        contactInfo.setLastTime(date);
        boolean flag = contactShareDao.insert(contactInfo);

        return flag;
    }

    /**
     * 修改数据
     *
     * @param contactInfo 实例对象
     * @return 实例对象
     */
    @Override
    public int update(ContactInfo contactInfo) {
        Date date = new Date();
        contactInfo.setLastTime(date);
        int result = contactShareDao.update(contactInfo);

        return result;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.contactShareDao.deleteById(id) > 0;
    }

    @Override
    public boolean deleteByIds(List<String> ids) {
        return contactShareDao.deleteByIds(ids) > 0;
    }

    @Override
    public List<ContactInfoQueryDTO> getCondition(ContactInfoQueryDTO contactInfoQueryDTO) {
        return contactShareDao.getCondition(contactInfoQueryDTO);
    }

    @Override
    public int findCondition(ContactInfoQueryDTO contactInfoQueryDTO) {
        return contactShareDao.findCondition(contactInfoQueryDTO);
    }

    @Override
    public boolean maxFormat(String str, int length) {
        if (ObjTool.isNotNull(str)) {
            if (str.length() > length) {
                return true;
            }
        }
        return false;
    }
}