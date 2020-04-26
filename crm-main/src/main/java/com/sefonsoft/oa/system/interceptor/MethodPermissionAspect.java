package com.sefonsoft.oa.system.interceptor;

import com.sefonsoft.oa.dao.role.SysRoleDao;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.role.SysRole;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.exception.UnauthorizedException;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

/**
 * 对接口进行操作权限的判断
 * @ClassName: MethodPermissionAspect
 * @author: Peng YiWen
 * @date: 2020/4/7  11:05
 */
@Component
@Aspect
public class MethodPermissionAspect {

    @Resource
    private SysRoleDao sysRoleDao;

    @Before(value = "@annotation(com.sefonsoft.oa.system.annotation.MethodPermission)")
    public void doBefore(JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        MethodPermission methodPermission = signature.getMethod().getAnnotation(MethodPermission.class);
        String[] menuIdArray = methodPermission.menuIdArray();
        //获取登录信息
        LoginUserDTO loginUserInfo = getLoginUserInfo();
        //根据登录用户信息中的操作角色进行判断(若用户信息中的操作角色数组中含有注解参数数组上某一个操作角色，则判定为有权限访问此接口)
        if (!methodIfHasMenuPermission(loginUserInfo, menuIdArray)) {
            throw new UnauthorizedException("该用户没有此接口的访问权限");
        }
    }

    /**
     * 根据登录用户信息中的操作菜单权限进行判断(若用户信息中的操作菜单权限数组中含有注解参数数组上某一个操作菜单权限，则判定为有权限访问此接口)则判定为有权限访问此接口
     * @param loginUserInfo
     * @param annotationMenuIdArray
     */
    private boolean methodIfHasMenuPermission(LoginUserDTO loginUserInfo, String[] annotationMenuIdArray) {
        List<SysRole> menuRoleList = loginUserInfo.getMenuRoleList();
        //当且仅当MethodPermission注解上含有操作权限id时，才进行权限的控制,否则,直接认为有权限访问
        if (ObjTool.isNotNull(annotationMenuIdArray)) {
            List<String> userMenuRoleIdList = menuRoleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
            if (ObjTool.isNotNull(menuRoleList)) {

                //用户自己的permission菜单set
                List<String> userMenuIdList = sysRoleDao.getUserMenuList(userMenuRoleIdList);
                Set<String> userMenuIdSet = new HashSet<>(userMenuIdList);

                //接口方法上的permission菜单set
                Set<String> annotationMenuIdSet = new HashSet<>(Arrays.asList(annotationMenuIdArray));

                for (String annotationMenuId : annotationMenuIdSet) {
                    if (ObjTool.isNotNull(annotationMenuId) && userMenuIdSet.contains(annotationMenuId)) {
                        //有匹配上的操作菜单permission
                        return true;
                    }
                }
            }
            //用户没有任何菜单权限 || 用户没有匹配上此接口方法上的任何菜单权限
            return false;
        }
        //接口方法上没有使用MethodPermission注解，则判断此接口不需要被拦截
        return true;
    }

    /**
     * 获取用户登录信息(本可以从接口方法入参上直接循环args获取，但有一些接口并未使用CurrentUser，所以此处直接在session中获取)
     * @return LoginUserDTO
     */
    private LoginUserDTO getLoginUserInfo() {
        //获取请求头上的用户名
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String loginUserId = null;
        Cookie[] cookies = request.getCookies();
        if (ObjTool.isNotNull(cookies)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (ObjTool.isNotNull(name) && name.equals("userId")) {
                    loginUserId = cookie.getValue();
                }
            }
        }
        HttpSession session = request.getSession();
        //获取用户登录信息
        if (ObjTool.isNotNull(loginUserId)) {
            Object sessionAttribute = session.getAttribute(USER_INFO + loginUserId);
            if (ObjTool.isNotNull(sessionAttribute) && sessionAttribute instanceof LoginUserDTO) {
                LoginUserDTO loginUserDTO = (LoginUserDTO) sessionAttribute;
                if (ObjTool.isNotNull(loginUserDTO)) {
                    return loginUserDTO;
                }
            }
        }
        throw new UnauthorizedException("用户未登录，请登录后重试");
    }

}