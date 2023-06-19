package cn.daenx.myadmin.common.serializer;


import cn.daenx.myadmin.common.annotation.Dict;
import cn.daenx.myadmin.common.annotation.Masked;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * 自定义 Jackson序列化器
 **/
public abstract class SerializerAbstract extends JsonSerializer<Object> {
    //字典翻译后缀，例如字段名是：dataType，那么添加的翻译对象为：dataTypeDict
    private final static String DICT_SUFFIX_NAME = "Dict";
    private Dict dict;
    private Masked masked;

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (dict != null && o != null) {
            // 写入原值
            jsonGenerator.writeObject(o);
            // 进行翻译
            Object content = handleDict(dict, String.valueOf(o));
            // 获取当前字段名字
            String fieldName = jsonGenerator.getOutputContext().getCurrentName();
            // 写入一个新字段
            fieldName = fieldName.concat(DICT_SUFFIX_NAME);
            jsonGenerator.writeObjectField(fieldName, content);
        }
        if (masked != null && o != null) {
            // 进行脱敏
            Object content = handleMasker(masked, String.valueOf(o));
            // 写入值
            jsonGenerator.writeObject(content);
        }
    }


    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public void setMasked(Masked masked) {
        this.masked = masked;
    }

    /**
     * 字典翻译处理
     *
     * @param dict
     * @param fieldValue
     * @return
     */
    public abstract Object handleDict(Dict dict, String fieldValue);

    /**
     * 数据脱敏处理
     *
     * @param masked
     * @param fieldValue
     * @return
     */
    public abstract Object handleMasker(Masked masked, String fieldValue);

    /**
     * 通过Jackson的 Jackson2ObjectMapperBuilderCustomizer 自定义ObjectMapper对象
     *
     * @return
     */
    @Bean
    public Object jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                SimpleModule module = new SimpleModule();
                module.setSerializerModifier(new MyBeanSerializerModifier(CustomSerializer.class));
                jacksonObjectMapperBuilder.modules(module);
            }
        };
    }
}
