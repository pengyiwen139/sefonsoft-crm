package com.sefonsoft.oa.domain.common;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aron
 */
public class TreeNode {
    /**
     * 父节点名称
     */
    private String parentName;
    /**
     * 父节点编码
     */
    private String parentCode;
    /**
     * 层级编码
     */

    private transient String ancestors;
    /**
     * ID
     */
    private transient Long id;
    /**
     * 父节点ID
     */
    private transient String parentId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 状态
     */
    private int curStatus;
    /**
     * 组织类型
     */
    private String orgType;
    /**
     * 增加的排序号
     */

    private Integer indexOrder;
    /**
     * 是否为临时代理对象
     */
    private Boolean template;
    /**
     * 子节点
     */
    private List<TreeNode> children;


    public String getancestors() {
        return ancestors;
    }

    public void setancestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    public void addChild(TreeNode node){
        if(this.children==null){
            this.children=new ArrayList<TreeNode>();
            this.children.add(node);
        }else {
            this.children.add(node);

            Collections.sort(this.children, (TreeNode firTreeNode,TreeNode secTreeNode)-> {
                //如果indexOrder不一样样先按照indexOrder排序，如果indexOrder一样按照id排序
                if (firTreeNode.indexOrder != null && secTreeNode.indexOrder != null && !firTreeNode.indexOrder .equals(secTreeNode.indexOrder)) {
                    if (firTreeNode.getIndexOrder() > secTreeNode.getIndexOrder()) {
                        return 1;
                    }
                    if (firTreeNode.getIndexOrder().equals(secTreeNode.getIndexOrder()) ) {
                        return 0;
                    }
                    return -1;
                } else {
                    if (firTreeNode.getId() > secTreeNode.getId()) {
                        return 1;
                    }
                    if (firTreeNode.getId().equals(secTreeNode.getId())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(int curStatus) {
        this.curStatus = curStatus;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(Integer indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}
