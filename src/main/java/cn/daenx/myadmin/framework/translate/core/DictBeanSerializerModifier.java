package cn.daenx.myadmin.framework.translate.core;

import cn.daenx.myadmin.framework.translate.annotation.Translate;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;

import java.util.List;

/**
 * java对象序列化修改器
 * 序列化   将java对象转化成json,指响应过程
 * 反序列化 将json转为java对象,指请求过程
 * <p>
 * modifySerializer() 序列化器修改
 * changeProperties() 属性变化
 * updateProperties() 属性更新
 *
 * @author gltqe
 * @date 2023/6/14 15:00
 **/
public class DictBeanSerializerModifier extends BeanSerializerModifier {

    private DictSerializerAbstract dictSerializerAbstract;

    public DictBeanSerializerModifier(DictSerializerAbstract dictSerializerAbstract) {
        this.dictSerializerAbstract = dictSerializerAbstract;
    }

    /**
     * 这个方法在类第一次序列化时会调用一次，之后不会再调用
     *
     * @param config         序列化配置
     * @param beanDesc       Java Bean 对象的描述信息
     * @param beanProperties Java Bean 对象的属性列表
     * @return: java.util.List<com.fasterxml.jackson.databind.ser.BeanPropertyWriter>
     * @author gltqe
     * @date 2023/6/14 13:40
     **/
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanProperty : beanProperties) {
            Translate dictAnnotation = beanProperty.getAnnotation(Translate.class);
            if (dictAnnotation != null) {
                dictSerializerAbstract.setDict(dictAnnotation);
                // 设置自定义的序列化器
                beanProperty.assignSerializer(dictSerializerAbstract);
                // null值序列器
                beanProperty.assignNullSerializer(NullSerializer.instance);
            }
        }
        return beanProperties;
    }
}
