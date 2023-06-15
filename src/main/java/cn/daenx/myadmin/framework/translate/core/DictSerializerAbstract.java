package cn.daenx.myadmin.framework.translate.core;


import cn.daenx.myadmin.common.annotation.Dict;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 自定义 Jackson序列化器
 *
 * @author gltqe
 * @date 2023/6/14 15:03
 **/
public abstract class DictSerializerAbstract extends JsonSerializer<Object> {
    //默认后缀，例如字段名是：dataType，那么添加的翻译对象为：dataTypeDict
    private final static String DICT_SUFFIX_NAME = "Dict";

    private Dict dict;

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (dict != null && o != null) {
            // 写入原值
            jsonGenerator.writeObject(o);

            // 获取字典
            Object content = getDictContent(dict, String.valueOf(o));

            // 获取当前字段名字
            String fieldName = jsonGenerator.getOutputContext().getCurrentName();

            // 写入一个新字段
            fieldName = fieldName.concat(getSuffix());
            jsonGenerator.writeObjectField(fieldName, content);
        }
    }

    /**
     * 获取后缀
     *
     * @author Silence
     * @date 2023/6/15 16:07
     **/
    public String getSuffix() {
        return DICT_SUFFIX_NAME;
    }

    /**
     * 设置字典
     *
     * @param dict
     * @author Silence
     * @date 2023/6/15 15:58
     **/
    public void setDict(Dict dict) {
        this.dict = dict;
    }

    /**
     * 获取字典的内容
     *
     * @param dict       注解
     * @param fieldValue 字段值
     * @return: java.lang.Object
     * @author Silence
     * @date 2023/6/15 15:52
     **/
    public abstract Object getDictContent(Dict dict, String fieldValue);

    /**
     * 通过Jackson的 Jackson2ObjectMapperBuilderCustomizer 自定义ObjectMapper对象
     *
     * @return: java.lang.Object
     * @author Silence
     * @date 2023/6/15 15:43
     **/
    public abstract Object jackson2ObjectMapperBuilderCustomizer();
}
