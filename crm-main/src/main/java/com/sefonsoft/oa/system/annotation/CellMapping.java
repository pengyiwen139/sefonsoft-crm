package com.sefonsoft.oa.system.annotation;

import java.lang.annotation.*;

/**
 * 用于映射实体类属性和Excel某列的名称
 *
 * @author pengyiwen
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellMapping {

    /**
     * 在excel文件中某列数据的名称
     *
     * @return 名称
     */
    String name() default "";

    /**
     * 字符串的长度
     *
     */
    int length() default 0;

    /**
     * 必须为Integer类型
     * @return
     */
    boolean integerValueFlag() default false;

    boolean doubleValueFlag() default false;
}