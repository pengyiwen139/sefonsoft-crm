package com.sefonsoft.oa.domain.common;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.interceptor.HttpRequestHolder;
import com.sefonsoft.oa.system.utils.CookieUtil;
import com.sefonsoft.oa.system.utils.ObjTool;

/**
 * 数据权限传参
 */
public abstract class AccessRule implements Serializable {

  
  /**
   * 
   */
  private static final long serialVersionUID = -3328647901829217893L;
  
  
  /**
   * 包含部门
   */
  Set<String> includeDepts = new HashSet<>();
  
  /**
   * 包含用户
   */
  Set<String> includeEmployees = new HashSet<>();
  
  AccessRule() {}
  
  /**
   * 包含的部门
   */
  public Set<String> getIncludeDepts() {
    return Collections.unmodifiableSet(includeDepts);
  }

  /**
   * 包含的员工
   */
  public Set<String> getIncludeEmployees() {
    return Collections.unmodifiableSet(includeEmployees);
  }
  
  /**
   * 是否有权限，无权限则无需查询
   */
  public boolean isDenied() {
    return this.includeDepts.isEmpty() && this.includeEmployees.isEmpty();
  }

  public abstract AccessRule withDepts(Collection<String> depts);
  
  public abstract AccessRule withUser(Collection<String> users);
  
  public abstract AccessRule withCurrentUser();
  
  public static AccessRule newRule() {

    HttpServletRequest request = HttpRequestHolder.currentServletRequest();
    HttpSession session = request.getSession(false);

    LoginUserDTO cur = null;
    
    if (ObjTool.isNotNull(request)) {
      String loginUserId = CookieUtil.getLoginUserIdByCookie(request);
      if (ObjTool.isNotNull(loginUserId,session)) {
        Object sessionAttribute = session.getAttribute(USER_INFO + loginUserId);
        if (ObjTool.isNotNull(sessionAttribute) && sessionAttribute instanceof LoginUserDTO) {
          cur = (LoginUserDTO) sessionAttribute;
        }
      }
    }
    
    // 添加数据权限
    if (cur != null && cur.isLD()) {
        DeptLeaderAccessRule rule = new DeptLeaderAccessRule();
        rule.cur = cur;
        return rule;
    }
    
    NormalAccessRule sr = new NormalAccessRule();
    sr.cur = cur;
    return sr;
  }
  
  
  /**
   * 一般权限
   */
  private static class NormalAccessRule extends AccessRule {

    /**
     * 
     */
    private static final long serialVersionUID = 5672139742193720699L;

    @Nullable
    public LoginUserDTO cur;
    
    @Override
    public AccessRule withDepts(Collection<String> depts) {
      // 没有权限
      if (cur != null && "xn01".equals(cur.getUserId())) {
        if (depts != null) {
          includeDepts.addAll(depts);
        }
      }
      
      return this;
    }

    @Override
    public AccessRule withUser(Collection<String> users) {
      // 没有权限
      if (cur != null && "xn01".equals(cur.getUserId())) {
        if (users != null) {
          includeEmployees.addAll(users);
        }
      }
      return this;
    }

    @Override
    public AccessRule withCurrentUser() {
      if (cur != null) {
        includeEmployees.add(cur.getUserId());
      }
      return this;
    }
    
  }
  
  /**
   * 领导权限
   */
  private static class DeptLeaderAccessRule extends AccessRule {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3229487761097824918L;
    
    public LoginUserDTO cur;

    @Override
    public AccessRule withDepts(Collection<String> depts) {
      if (depts != null) {
        includeDepts.addAll(depts);
      }
      return this;
    }

    @Override
    public AccessRule withUser(Collection<String> users) {
      return this;
    }

    @Override
    public AccessRule withCurrentUser() {
      includeEmployees.add(cur.getUserId());
      return this;
    }
    
  }
  
  /**
   * 管理员权限
   */
  @SuppressWarnings("unused")
  private static class AdminAccessRule extends DeptLeaderAccessRule {

    /**
     * 
     */
    private static final long serialVersionUID = 6773613848936243751L;
    
  }
  
}
