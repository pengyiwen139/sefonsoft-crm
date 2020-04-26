package com.sefonsoft.oa.system.constant;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/10/30 10:54
 * @description：用于返回前端的常量
 * @modified By：
 */
public class ResponseMsgConstant {

    public static final String ERROR_PKEY= "主键id不能为空";

    public static final String ERROR_PARAM = "参数异常";

    public static final String NO_PARAM = "参数不能为空";

    public static final String NO_RESPONSE = "返回参数为空";

    public static final String MIN_PARAM = "参数超出最小范围";

    public static final String MAX_PARAM = "参数超出最大范围";

    public static final String NO_DATA_PERMISSION = "您没有任何数据权限，请联系管理员";

    public static final String FORMAT_ERROR = "参数格式错误";

    public static final String ERROR_INSERT = "插入失败";

    public static final String ERROR_UPDATE = "更新失败";

    public static final String ERROR_QUERY = "查询失败";

    public static final String ERROR_DELETE = "删除失败";

    public static final String ERROR_RELATED_DELETE = "删除失败,该商机已存在关联数据";

    public static final String ERROR_DELETEED = "数据已经删除";

    public static final String ERROR_LOGIN_STATUS = "未获取到登录状态，请重新登录";

    public static final String WARN_SIMILAR_PROJECT = "此申请疑似重复，是否需要转入人工审核";

    public static final String DEPTID_ERROR = "已存在部门编号，请修改部门编号";
    public static final String DEPTNAME_ERROR = "父节点下存在相同部门名称,请修改部门名称";
    public static final String DEPT_ERROR_DELETE  = "存在下级部门,不允许删除";
    public static final String EMPLOYEEID_ERROR  = "已存在员工编号，请修员工编号";
    public static final String DELETE_ERROR_EMPEXIT  = "部门下存在员工,不允许删除";

}