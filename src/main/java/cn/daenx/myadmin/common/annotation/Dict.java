package cn.daenx.myadmin.common.annotation;


import java.lang.annotation.*;

/**
 * 字典翻译
 * 导出导入时，与@ExcelProperty一起使用，可以实现翻译功能
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    /**
     * 字典编码
     * 使用系统字典表
     */
    String dictCode() default "";

    /**
     * 使用自定义字典翻译
     */
    DictDetail[] custom();

}
