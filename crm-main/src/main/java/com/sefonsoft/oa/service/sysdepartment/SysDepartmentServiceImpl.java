package com.sefonsoft.oa.service.sysdepartment;

import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.domain.sysdepartment.SysDepartmentQueryDTO;
import com.sefonsoft.oa.entity.sysdepartment.SysDepartment;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * (SysDepartment)表服务实现类
 *
 * @author Aron
 * @since 2019-11-01 16:11:07
 */
@Service("sysDepartmentService")
@Transactional(rollbackFor = Exception.class)
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Resource
    private SysDepartmentDao sysDepartmentDao;

    /**
     * 查询部门管理树
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree(SysDepartment dept)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<SysDepartment> deptList = sysDepartmentDao.selectDeptList(dept);
        trees = getTrees(deptList, false, null);
        return trees;
    }

    /**
     * 查询销售树
     * @param dept 部门信息
     * @return 所有销售部门信息
     */
    @Override
    public List<Map<String, Object>> selectSaleDeptTree(SysDepartment dept)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<SysDepartment> deptList = sysDepartmentDao.selectSaleDeptList(dept);

//        for(SysDepartment dt : deptList) {
//            SysEmployeeQueryDTO sysEmployee = new SysEmployeeQueryDTO();
//            String deptId = dt.getDeptId();
//            sysEmployee.setDeptId(deptId);
//            //根据部门id查员工列表
//            List<SysEmployeeQueryDTO> list = sysEmployeeDao.getList(sysEmployee);
//            //放入部门对应员工列表
//            dt.setEmployeeList(list);
//
//        }

        trees = getAllTrees(deptList,false, null);
        return trees;
    }
    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param isCheck 是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<SysDepartment> deptList, boolean isCheck, List<String> roleDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (SysDepartment dept : deptList)
        {
                Map<String, Object> deptMap = new HashMap<String, Object>(16);
                deptMap.put("id", dept.getId());
                deptMap.put("deptId", dept.getDeptId());
                deptMap.put("pId", dept.getParentId());
                deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                deptMap.put("directLeaderId", dept.getDirectLeaderId());
                deptMap.put("chargePersonId", dept.getChargePersonId());
                deptMap.put("directLeaderName", dept.getDirectLeaderName());
                deptMap.put("chargePersonName", dept.getChargePersonName());
                if (isCheck)
                {
                    deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                else
                {
                    deptMap.put("checked", false);
                }
                trees.add(deptMap);
        }
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param isCheck 是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getAllTrees(List<SysDepartment> deptList, boolean isCheck, List<String> roleDeptList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (SysDepartment dept : deptList)
        {
            Map<String, Object> deptMap = new HashMap<String, Object>(16);
            deptMap.put("id", dept.getId());
            deptMap.put("deptId", dept.getDeptId());
            deptMap.put("pId", dept.getParentId());
            deptMap.put("name", dept.getDeptName());
            deptMap.put("title", dept.getDeptName());
            //deptMap.put("employeeList", dept.getEmployeeList());
            if (isCheck)
            {
                deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
            }
            else
            {
                deptMap.put("checked", false);
            }
            trees.add(deptMap);
        }
        return trees;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDepartment queryById(Long id) {
        return this.sysDepartmentDao.queryById(id);
    }


    /**
     * 查询部门数
     *
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(String parentId)
    {
        SysDepartment dept = new SysDepartment();
        dept.setParentId(parentId);
        return sysDepartmentDao.selectDeptCount(dept);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDepartment> queryAllByLimit(int offset, int limit) {
        return this.sysDepartmentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(SysDepartment dept) {
        Date date = new Date();

        dept.setModifiedTime(date);
        dept.setCreateTime(date);
        dept.setCurStatus(1);
        dept.setOrderNum(0);

        //查询父节点编码
        SysDepartment info = sysDepartmentDao.selectByDeptId(dept.getParentId());
        //以分级逗号分割放入对象
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());

        boolean flag = sysDepartmentDao.insert(dept);
        return flag;
    }

    /**
     * 修改数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysDepartment dept) {
        Date date = new Date();

        dept.setModifiedTime(date);

        SysDepartment info = sysDepartmentDao.selectByDeptId(dept.getParentId());

        if (ObjTool.isNotNull(info))
        {
            String ancestors = info.getAncestors() + "," + info.getDeptId();
            dept.setAncestors(ancestors);
            //更新子节点
            updateDeptChildren(dept.getDeptId(), ancestors);
        }
        int result = sysDepartmentDao.update(dept);

            // 如果该部门是启用状态，则启用该部门的所有上级部门
            //updateParentDeptStatus(dept);

        return result;

    }

    /**
     * 修改子元素关系
     *
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(String deptId, String ancestors)
    {
        SysDepartment dept = new SysDepartment();
        dept.setParentId(deptId);
        List<SysDepartment> childrens = sysDepartmentDao.selectDeptList(dept);
        for (SysDepartment children : childrens)
        {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0)
        {
            sysDepartmentDao.updateDeptChildren(childrens);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysDepartmentDao.deleteById(id) > 0;
    }

    @Override
    public List<SysDepartmentQueryDTO> getCondition(SysDepartmentQueryDTO sysDepartmentQueryDTO) {
        return sysDepartmentDao.getCondition(sysDepartmentQueryDTO);
    }

    @Override
    public int findCondition(SysDepartmentQueryDTO sysDepartmentQueryDTO) {
        return sysDepartmentDao.findCondition(sysDepartmentQueryDTO);
    }

    /**
     * 校验部门名称是否唯一
     * @param parentId
     * @param deptName
     * @return 结果
     */
    @Override
    public int checkDeptNameUnique(String parentId, String deptName) {

        int count = sysDepartmentDao.checkDeptNameUnique(parentId, deptName);

        return count;
    }

    @Override
    public int checkDeptIdUnique(String deptId) {

        int count = sysDepartmentDao.checkDeptIdUnique(deptId);
        return count;
    }

    @Override
    public String getMaxDeptId() {
        return sysDepartmentDao.getMaxDeptId();
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门编号
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(String deptId)
    {
        int result = sysDepartmentDao.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }
}