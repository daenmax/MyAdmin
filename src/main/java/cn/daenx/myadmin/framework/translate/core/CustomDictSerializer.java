package cn.daenx.myadmin.framework.translate.core;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.domain.po.SysDictDetail;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Silence
 * @date 2023/6/15 15:31
 */
@Configuration
public class CustomDictSerializer extends DictSerializerAbstract {
    @Override
    public Object getDictContent(String dictCode, String fieldValue) {
        Object object = RedisUtil.getValue(RedisConstant.DICT + dictCode);
        List<SysDictDetail> list = JSON.parseArray(JSON.toJSONString(object), SysDictDetail.class);
        List<SysDictDetail> collect = list.stream().filter(item -> fieldValue.equals(item.getValue())).collect(Collectors.toList());
        Map<String, String> map = new HashMap<>();
        if (collect.size() > 0) {
            SysDictDetail sysDictDetail = collect.get(0);
            map.put("cssClass", sysDictDetail.getCssClass());
            map.put("listClass", sysDictDetail.getListClass());
            map.put("label", sysDictDetail.getLabel());
            map.put("value", sysDictDetail.getValue());
            map.put("status", sysDictDetail.getStatus());
            map.put("remark", sysDictDetail.getRemark());
        }
        return map;
    }

    /**
     * 重写默认后缀
     * 如果不重写，将使用默认的后缀：Dict
     *
     * @return
     */
    @Override
    public String getSuffix() {
        //例如字段名是：dataType，那么添加的翻译对象为：dataTypeDict
        return "Dict";
    }

    @Bean
    @Override
    public Object jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                SimpleModule module = new SimpleModule();
                module.setSerializerModifier(new DictBeanSerializerModifier(new CustomDictSerializer()));
                jacksonObjectMapperBuilder.modules(module);
            }
        };
    }
}
