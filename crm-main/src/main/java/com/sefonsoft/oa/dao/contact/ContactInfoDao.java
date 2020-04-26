package com.sefonsoft.oa.dao.contact;

import com.sefonsoft.oa.domain.contact.ContactInfoQueryDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ContactInfo)表数据库访问层
 *
 * @author Aron
 * @since 2019-11-16 17:26:42
 */
public interface ContactInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ContactInfo queryById(Long id);


    /**
     * 通过customerId查询单条数据
     *
     * @param customerId
     * @return 实例对象
     */
    List<ContactInfo> queryByCustomerId(String customerId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ContactInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param contactInfo 实例对象
     * @return 对象列表
     */
    List<ContactInfo> queryAll(ContactInfo contactInfo);

    /**
     * 新增数据
     *
     * @param contactInfo 实例对象
     * @return 影响行数
     */
    boolean insert(ContactInfo contactInfo);

    /**
     * 修改数据
     *
     * @param contactInfo 实例对象
     * @return 影响行数
     */
    int update(ContactInfo contactInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<String> ids);

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


    boolean batchInsert(@Param("contactInfoList") List<ContactInfo> contactInfoList);

    ContactInfo findContactInfo(@Param("customerId") String customerId,
                                @Param("contactName") String contactName,
                                @Param("telephone") String telephone,
                                @Param("employeeId") String employeeId);
}