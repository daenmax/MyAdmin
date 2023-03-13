package cn.daenx.myadmin.common.annotation;


import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictDetail {


    String value() default "";
    String label() default "";

}
