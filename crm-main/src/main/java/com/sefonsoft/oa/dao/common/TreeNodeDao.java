package com.sefonsoft.oa.dao.common;

import com.sefonsoft.oa.domain.common.TreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Aron
 */
public interface TreeNodeDao {

    /**
     * 以列表形式返回所有子节点
     * @param rootCode 根节点树编码
     * @return
     */
    List<TreeNode> getChildren(@Param("rootCode") String rootCode);



    /**
     * 以列表形式返回所有子节点
     * @param tableName 表名
     * @param rootCode 根节点树编码
     * @return
     */
    List<TreeNode> getEnterChildren(@Param("tableName") String tableName, @Param("rootCode") String rootCode);

}