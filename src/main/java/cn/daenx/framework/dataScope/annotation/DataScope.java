package cn.daenx.framework.dataScope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 表别名
     */
    String alias() default "";

    /**
     * 字段名
     */
    String field() default "create_id";
}
