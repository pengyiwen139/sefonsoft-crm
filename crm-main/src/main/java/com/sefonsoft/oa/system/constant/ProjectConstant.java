package com.sefonsoft.oa.system.constant;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/22 13:57
 * @description：项目相关常量
 * @modified By：
 */
public class ProjectConstant {

    /**
     * 意向
     */
    public static final int PROJECT_STATUS_WANNA = 1;

    /**
     * 立项
     */
    public static final int PROJECT_STATUS_SETUP = 2;

    /**
     * 合同
     */
    public static final int PROJECT_STATUS_AGREEMENT = 3;

    /**
     * 交付
     */
    public static final int PROJECT_STATUS_SUBMIT = 4;

    /**
     * 运维
     */
    public static final int PROJECT_STATUS_DEV = 5;

    /**
     * 结束
     */
    public static final int PROJECT_STATUS_DOWN = 6;

    /**
     * 大区总已驳回
     */
    public static final int PROJECT_ZONE_TOP_REFUSE = 0;

    /**
     * 项目未审核
     */
    public static final int PROJECT_PRE_CHECK_STATUS = 1;

    /**
     * 项目已审核通过
     */
    public static final int PROJECT_AGREE_CHECK_STATUS = 3;

    /**
     * 有相似的项目
     */
    public static final int HAS_SIMILAR_TYPE = 1;

    /**
     * 无相似的项目
     */
    public static final int HAS_NO_SIMILAR_TYPE = 0;

    /**
     * 项目未过期
     */
    public static final int OVER_TIME_FLAG_NO = 0;
    
    /**
     * 项目已逾期，被移动至部门资源池
     */
    public static final int OVER_TIME_FLAG_DEPT = 1;
    
    /**
     * 项目已过期，被移动至公司资源池
     */
    public static final int OVER_TIME_FLAG_ALL = 2;

    /**
     * 校验项目疑似flag
     */
    public static final int CHECK_SIMILAR_PROJECT_FLAG = 1;

    /**
     * 不校验项目疑似flag
     */
    public static final int NO_CHECK_SIMILAR_PROJECT_FLAG = 0;
    
    /**
     * 指派模式，认领资源池
     */
    public static final int ASSGIN_PROJECT_FOLLOW = 0;
    /**
     * 指派模式，持续跟进
     */
    public static final int ASSGIN_PROJECT_KEEP = 1;
    
    

}