package cn.daenx.myadmin.common.annotation;

import cn.daenx.myadmin.common.constant.enums.MaskedType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏注解
 * 接口响应时，可以实现对字段进行脱敏处理
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Masked {
    MaskedType type();
}
