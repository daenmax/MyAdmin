package cn.daenx.myadmin.framework.serializer.annotation;


import java.lang.annotation.*;

/**
 * 字典翻译明细注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictDetail {

    /**
     * 值
     *
     * @return
     */
    String value() default "";

    /**
     * 显示
     *
     * @return
     */
    String label() default "";

}
