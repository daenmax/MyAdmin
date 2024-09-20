package cn.daenx.framework.dictMasked.annotation;

import cn.daenx.framework.dictMasked.enums.MaskedType;

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
    /**
     * 数据类型，0=姓名，1=手机号，2=身份证号码，3=银行卡号，4=电子邮箱，5=地址信息，6=IP地址
     * 请使用MaskedType枚举类
     *
     * @return
     */
    MaskedType type();
}
