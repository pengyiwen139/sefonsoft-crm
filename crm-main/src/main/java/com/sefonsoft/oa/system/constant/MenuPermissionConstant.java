package com.sefonsoft.oa.system.constant;

/**
 * 用于接口鉴权的权限菜单实体类constant(增删改相关接口必须鉴权，查询接口可选择性鉴权)，
 * 放在MethodPermission注解的menuIdArray属性上，当有新功能模块时，需要将数据库表的sys_permission中新增的权限菜单数据更新至此。
 * 当此接口仅供拥有删除客户权限的用户调用时，配置如下：
 *  @MethodPermission(menuIdArray = C_CUSTOMER_DELETE)
 *  @RequestMapping(value = "/customer/delAll", method = RequestMethod.DELETE)
 *  public Response deleteIds(String ids) {...}
 *  若某方法接口会在两个或两个以上的权限模块中用到，则menuIdArray值可以是数组，示例如下:
 *  @MethodPermission(menuIdArray = {C_CUSTOMER_DELETE,C_CUSTOMER_EDIT})
 *  @RequestMapping(value = "/customer/delAll", method = RequestMethod.DELETE)
 *  public Response deleteIds(String ids) {...}
 * @ClassName: com.sefonsoft.oa.system.constant.MenuPermissionConstant
 * @author: Peng YiWen
 * @date: 2020/4/8  10:36
 */
public class MenuPermissionConstant {

    //系统管理中心
    public static final String MANAGE = "manage";

    //权限管理
    public static final String M_PERMISSION = "manage:permission";

    //组织架构管理
    public static final String M_ORGANIZATION = "manage:organization";

    //员工管理
    public static final String M_ORGANIZATION_STAFF = "manage:organization:staff";

    //员工查询
    public static final String M_ORGANIZATION_STAFF_QUERY = "manage:organization:staff:query";

    //员工新增
    public static final String M_ORGANIZATION_STAFF_ADD = "manage:organization:staff:add";

    //员工修改
    public static final String M_ORGANIZATION_STAFF_UPDATE = "manage:organization:staff:update";

    //员工删除
    public static final String M_ORGANIZATION_STAFF_DELETE = "manage:organization:staff:delete";

    //部门管理
    public static final String M_ORGANIZATION_DEPT = "manage:organization:dept";

    //部门查询
    public static final String M_ORGANIZATION_DEPT_QUERY = "manage:organization:dept:query";

    //部门新增
    public static final String M_ORGANIZATION_DEPT_ADD = "manage:organization:dept:add";

    //部门修改
    public static final String M_ORGANIZATION_DEPT_UPDATE = "manage:organization:dept:update";

    //部门删除
    public static final String M_ORGANIZATION_DEPT_DELETE = "manage:organization:dept:delete";

    //职系管理
    public static final String M_ORGANIZATION_GRADE = "manage:organization:grade";

    //职系查询
    public static final String M_ORGANIZATION_GRADE_QUERY = "manage:organization:grade:query";

    //职系新增
    public static final String M_ORGANIZATION_GRADE_ADD = "manage:organization:grade:add";

    //职系修改
    public static final String M_ORGANIZATION_GRADE_UPDATE = "manage:organization:grade:update";

    //职系删除
    public static final String M_ORGANIZATION_GRADE_DELETE = "manage:organization:grade:delete";

    //客户关系管理
    public static final String CRM = "crm";

    //客户管理
    public static final String C_CUSTOMER = "crm:customer";

    //客户查询
    public static final String C_CUSTOMER_QUERY = "crm:customer:query";

    //客户新增
    public static final String C_CUSTOMER_ADD = "crm:customer:add";

    //客户修改
    public static final String C_CUSTOMER_UPDATE = "crm:customer:update";

    //客户删除
    public static final String C_CUSTOMER_DELETE = "crm:customer:delete";

    //查看客户详情
    public static final String C_CUSTOMER_DETAIL = "crm:customer:detail";

    //导入客户与联系人
    public static final String C_CUSTOMER_IMPORT = "crm:customer:import";

    //导出客户与联系人
    public static final String C_CUSTOMER_EXPORT = "crm:customer:export";

    //客户负责人变更
    public static final String C_CUSTOMER_CHANGECHARGER = "crm:customer:changecharger";

    //联系人查询
    public static final String C_CONTACT_QUERY = "crm:contact:query";

    //联系人新增
    public static final String C_CONTACT_ADD = "crm:contact:add";

    //联系人修改
    public static final String C_CONTACT_UPDATE = "crm:contact:update";

    //联系人删除
    public static final String C_CONTACT_DELETE = "crm:contact:delete";

    //立项管理
    public static final String C_PROJECT = "crm:project";

    //项目查询
    public static final String C_PROJECT_QUERY = "crm:project:query";

    //项目新增
    public static final String C_PROJECT_ADD = "crm:project:add";

    //项目修改
    public static final String C_PROJECT_UPDATE = "crm:project:update";

    //项目删除
    public static final String C_PROJECT_DELETE = "crm:project:delete";

    //项目更新
    public static final String C_PROJECT_FRESH = "crm:project:salesUpdate";

    //项目释放
    public static final String C_PROJECT_RELEASE = "crm:project:release";

    //查看项目详情
    public static final String C_PROJECT_DETAIL = "crm:project:detail";

    //项目负责人变更
    public static final String C_PROJECT_CHANGECHARGER = "crm:project:changecharger";

    //报告管理
    public static final String C_REPORT = "crm:report";

    //报告查询
    public static final String C_REPORT_QUERY = "crm:report:query";

    //查看报告统计
    public static final String C_REPORT_RANGE = "crm:report:range";

    //重置密码
    public static final String M_ORGANIZATION_STAFF_RESET = "manage:organization:staff:reset";

    //项目审批
    public static final String C_PROJECT_APPROVAL = "crm:project:approval";

    //项目导入
    public static final String C_PROJECT_IMPORT = "crm:project:import";

    //项目导出
    public static final String C_PROJECT_EXPORT = "crm:project:export";

    //离职人员项目交接
    public static final String C_PROJECT_QUITEMPLOYEE = "crm:project:quitEmployee";

    //商机管理
    public static final String C_BIZ_OPPORTS = "crm:biz_opports";

    //新增商机信息
    public static final String C_BIZ_OPPORTS_ADD = "crm:biz_opports:add";

    //修改商机信息
    public static final String C_BIZ_OPPORTS_EDIT = "crm:biz_opports:edit";

    //查询商机信息
    public static final String C_BIZ_OPPORTS_QUERY = "crm:biz_opports:query";

    //商机信息详情
    public static final String C_BIZ_OPPORTS_DETAIL = "crm:biz_opports:detail";

    //商机指派
    public static final String C_BIZASSIGN = "crm:bizAssign";

    //指派销售
    public static final String C_BIZASSIGN_TOSALE = "crm:bizAssign:toSale";

    //工作汇报
    public static final String C_WORKREPORT = "crm:workreport";

    //日报管理
    public static final String C_WORKREPORT_DAILY = "crm:workreport:daily";

    //日报日历
    public static final String C_WORKREPORT_DAILY_CALENDAR = "crm:workreport:daily:calendar";

    //日报列表
    public static final String C_WORKREPORT_DAILY_LIST = "crm:workreport:daily:list";

    //高级搜索
    public static final String C_WORKREPORT_DAILY_SEARCH = "crm:workreport:daily:search";

    //周报管理
    public static final String C_WORKREPORT_WEEKLY = "crm:workreport:weekly";

    //审批本周日报
    public static final String C_WORKREPORT_WEEKLY_CHECK = "crm:workreport:weekly:check";

    //高级搜索
    public static final String C_WORKREPORT_WEEKLY_SEARCH = "crm:workreport:weekly:search";

    //公共资源池
    public static final String C_SHARE = "crm:share";

    //客户资源
    public static final String C_SHARE_CUSTOMER = "crm:share:customer";

    //项目资源
    public static final String C_SHARE_PROJECT = "crm:share:project";

    //项目指派
    public static final String PROJECT_ASSIGN = "crm:share:project:assign";

    //项目认领
    public static final String PROJECT_ASSIGNME = "crm:share:project:assignMe";

    //待办事项
    public static final String C_TODO = "crm:todo";

    //商机指派
    public static final String C_TODO_BIZOPPORT = "crm:todo:bizOpport";

    //立项审批
    public static final String C_TODO_PROJECTCHECK = "crm:todo:projectCheck";

    //客户画像
    public static final String C_CUSTOMERPROFILE = "crm:customerProfile";

    //商机删除
    public static final String C_BIZ_OPPORTS_DEL = "crm:biz_opports:del";

    //客户资源池导入
    public static final String C_SHARE_CUSTOMER_IMPORT = "crm:share:customer:import";

    //客户资源池导出
    public static final String C_SHARE_CUSTOMER_EXPORT = "crm:share:customer:export";

    //商机导入
    public static final String C_BIZ_OPPORTS_IMPORT = "crm:biz_opports:import";

    //商机导出
    public static final String C_BIZ_OPPORTS_EXPORT = "crm:biz_opports:export";

    //客户资源池新增客户
    public static final String C_SHARE_CUSTOMER_ADD = "crm:share:customer:add";

    //客户资源池修改客户
    public static final String C_SHARE_CUSTOMER_EDIT = "crm:share:customer:edit";

    //客户资源池删除客户
    public static final String C_SHARE_CUSTOMER_DELETE = "crm:share:customer:delete";

    //客户资源池添加到我的客户管理
    public static final String C_SHARE_CUSTOMER_COPY = "crm:share:customer:copy";

    //操作记录
    public static final String C_OPLOG = "crm:oplog";

    /**
     * 合同管理
     */
    //新增合同
    public static final String CONTRACT_ADD = "crm:contract:add";

    //修改合同
    public static final String CONTRACT_EDIT = "crm:contract:edit";

    //查看合同详情
    public static final String CONTRACT_DETAIL = "crm:contract:detail";

    //删除合同
    public static final String CONTRACT_DELETE = "crm:contract:delete";

    //合同导入
    public static final String CONTRACT_IMPORT = "crm:contract:import";

    //合同导出
    public static final String CONTRACT_EXPORT = "crm:contract:export";

    //回款添加
    public static final String CONTRACT_ADDPAYINFO = "crm:contract:addPayInfo";

    //回款修改
    public static final String CONTRACT_EDITPAYINFO = "crm:contract:editPayInfo";

    //发票添加
    public static final String CONTRACT_ADDINVOICE = "crm:contract:addInvoice";

    //发票修改
    public static final String CONTRACT_EDITINVOICE = "crm:contract:editInvoice";

    //操作记录
    public static final String CONTRACT_QUERY = "crm:contract:query";


}
