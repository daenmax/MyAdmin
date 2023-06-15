package cn.daenx.myadmin.framework.translate.core;

import cn.daenx.myadmin.common.annotation.Dict;
import cn.daenx.myadmin.common.annotation.DictDetail;
import cn.daenx.myadmin.system.domain.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silence
 * @date 2023/6/15 15:31
 */
@Configuration
public class CustomDictSerializer extends DictSerializerAbstract {
    @Override
    public Object getDictContent(Dict dict, String fieldValue) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(dict.dictCode())) {
            List<SysDictDetail> list = SpringUtil.getBean(SysDictDetailService.class).getDictDetailByCodeFromRedis(dict.dictCode());
            for (SysDictDetail sysDictDetail : list) {
                if(sysDictDetail.getValue().equals(fieldValue)){
                    map.put("cssClass", sysDictDetail.getCssClass());
                    map.put("listClass", sysDictDetail.getListClass());
                    map.put("label", sysDictDetail.getLabel());
                    map.put("value", sysDictDetail.getValue());
                    map.put("status", sysDictDetail.getStatus());
                    map.put("remark", sysDictDetail.getRemark());
                    return map;
                }
            }
        }else {
            //根据自定义字典翻译
            DictDetail[] custom = dict.custom();
            for (DictDetail dictDetail : custom) {
                if (dictDetail.value().equals(fieldValue)) {
                    map.put("label", dictDetail.label());
                    map.put("value", dictDetail.value());
                    return map;
                }
            }
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
                module.setSerializerModifier(new DictBeanSerializerModifier(CustomDictSerializer.class));
                jacksonObjectMapperBuilder.modules(module);
            }
        };
    }
}
