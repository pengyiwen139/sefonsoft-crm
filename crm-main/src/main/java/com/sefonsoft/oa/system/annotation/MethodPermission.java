package com.sefonsoft.oa.system.annotation;


import java.lang.annotation.*;

/**
 * 在Controller的接口方法上使用此注解，该方法会校验接口是否有权限被访问(仅通过判断有无相应的菜单权限判断)
 * 在属性menuIdArray添加了一个或多个菜单权限id，则当且仅当调用该注解修饰的接口方法的用户含有其中一个菜单权限id，则可使用该接口
 * @author : Peng YiWen
 * @date : 2020/04/06
 */
@Documented
@Target(ElementType.METHOD)          // 可用在方法上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
public @interface MethodPermission {

    String[] menuIdArray() default "";

}