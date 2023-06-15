package cn.daenx.myadmin.framework.translate.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译注解
 * 接口在响应时，增加翻译对象
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Translate {

    /**
     * 字典编码
     * 使用系统字典表
     */
    String dictCode();

}
