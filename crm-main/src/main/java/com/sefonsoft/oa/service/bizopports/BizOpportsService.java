package com.sefonsoft.oa.service.bizopports;

import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.bizopports.vo.BizOpportsStatisticGroupVo;
import com.sefonsoft.oa.domain.customer.CustomerInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.bizopports.BizOpports;
import com.sefonsoft.oa.entity.workorder.BizOpportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liwenyi
 * Date: 2020/2/20
 * Desc: (biz_opports)表服务接口
 */
public interface BizOpportsService {

    /**
     * 新增商机
     *
     * @param bizOpportsDTO
     * @return
     */
    int insert(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO);
    
    /**
     * 新增商机
     * @param bizOpportsDTO
     * @param operator
     * @return 商机 ID
     */
    public Long insert(BizOpportsDTO bizOpportsDTO, String operator);
    
    /**
     * 检查商机是否重复立项
     * 这个方法会回填 自增 id 和 contact_id
     * @param bizOpportsDTO
     * @return
     */
    public boolean checkColudImport(BizOpportsExport biz);

    /**
     * 修改商机
     *
     * @param bizOpportsDTO
     * @return
     */
    int update(LoginUserDTO loginUserDTO,BizOpportsDTO bizOpportsDTO);

    /**
     * 根据id查看商机详情
     *
     * @param id
     * @return
     */
    BizOpports queryById(Long id);


    /**
     * 删除商机信息
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 关键字查询商机信息
     *
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    List<BizOpports> queryByKeyWords(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO);

    /**
     * 关键字查询商机信息总数
     *
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    int queryByKeyWordsTotal(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO);

    /**
     * 统计
     * xielf
     * @param onTime 0 所有, 1-月，2-季度，3-年, 4-周
     * @return
     */
    List<BizOpportsStatisticGroupVo> statistics(Set<String> deptIds, int onTime,String employeeId, LoginUserDTO loginUserDTO);

    /**
     * 获取与销售相关的客户
     * @param loginUserDTO
     * @return
     */
    List<Map<String, Object>> queryCustomerByUser(LoginUserDTO loginUserDTO);

    /**
     * 获取相关销售
     * @param loginUserDTO
     * @return
     */
    List<Map<String, Object>> querysaleByUser(LoginUserDTO loginUserDTO);
    

	/** 
	 * @param customer_id
	 * @return
	 * @author zhangyongfei Feb 26, 2020 9:18:57 PM
	 * @version 1.0
	 */
    List<CustomerInfoQueryDTO> getCustomerNameId(String customer_id,String employee_id);

    /**
     * 查询公司所有销售
     * @return
     */
    List<Map<String, Object>> queryAllSale();

    /**
     * 获取所有商机列表
     * @return
     */
    List<BizOpports> queryAllBiz(BizOpportsDTO bizOpportsDTO,List<String> ids);

    /**
     * 获取所有商机列表数量
     * @param bizOpportsDTO
     * @return
     */
    int queryAllBizTotal(BizOpportsDTO bizOpportsDTO,List<String> ids);

    /**
     * 交付售前获取自己创建的商机列表
     * @param loginUserDTO
     * @param bizOpportsDTO
     * @return
     */
    List<BizOpports> queryPreSaleAll(LoginUserDTO loginUserDTO, BizOpportsDTO bizOpportsDTO);

    /**
     * 交付售前获取自己创建的商机列表数量
     * @param loginUserDTO
     * @return
     */
    int queryPreSaleAllTotal(LoginUserDTO loginUserDTO,String keywords,String name);

    /**
     * 功能描述: 查询某个商机关联的立项或者派工单总量
     *
     * @Param: [id]
     * @Return: int
     * @Author: liwenyi
     */
    int relatedTotal(Long id);

    /**
     * 获取派工单时商机的基本信息
     * @param employeeId
     * @return
     */
    List<BizOpportInfo> getBizOpportInfos(String employeeId);



    int getBizopportsWorkerOrderCount(Long id);


}
