package cn.daenx.myadmin.common.annotation;


import cn.daenx.myadmin.common.enums.LogOperTypeEnum;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 操作名称
     **/
    public String name() default "";

    /**
     * 操作类型
     **/
    public LogOperTypeEnum type() default LogOperTypeEnum.OTHER;

    /**
     * 是否记录请求参数
     **/
    public boolean recordParams() default false;

    /**
     * 是否记录响应结果
     **/
    public boolean recordResult() default false;
}
