package com.sefonsoft.oa.service.contact;

import com.sefonsoft.oa.domain.contact.ContactInfoQueryDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;

import java.util.List;

/**
 * (ContactInfo)表服务接口
 *
 * @author Aron
 * @since 2019-11-16 17:26:43
 */
public interface ContactShareService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ContactInfo queryById(Long id);

    /**
     * 通过客户ID查询联系人
     * @param customerId
     * @return
     */
    List<ContactInfo> queryContactInfoByCustomerId(String customerId);
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ContactInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param contactInfo 实例对象
     * @return 实例对象
     */
    boolean insert(ContactInfo contactInfo);

    /**
     * 修改数据
     *
     * @param contactInfo 实例对象
     * @return 实例对象
     */
    int update(ContactInfo contactInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 是否成功
     */
    boolean deleteByIds(List<String> ids);

    /**
     * 查询联系人列表
     *
     * @param contactInfoQueryDTO 查询条件
     * @return
     */
    List<ContactInfoQueryDTO> getCondition(ContactInfoQueryDTO contactInfoQueryDTO);

    /**
     * 查询联系人总数
     *
     * @param contactInfoQueryDTO 查询条件
     * @return
     */
    int findCondition(ContactInfoQueryDTO contactInfoQueryDTO);

    /**
     * 校验最大值
     * @param s
     * @return
     */
    boolean maxFormat(String s, int length);
}