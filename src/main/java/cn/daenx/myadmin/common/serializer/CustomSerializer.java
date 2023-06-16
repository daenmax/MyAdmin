package cn.daenx.myadmin.common.serializer;

import cn.daenx.myadmin.common.annotation.Dict;
import cn.daenx.myadmin.common.annotation.DictDetail;
import cn.daenx.myadmin.common.annotation.Masked;
import cn.daenx.myadmin.common.constant.enums.MaskedType;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.system.domain.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义序列化处理器
 */
@Configuration
public class CustomSerializer extends SerializerAbstract {
    /**
     * 字典翻译处理
     *
     * @param dict
     * @param fieldValue
     * @return
     */
    @Override
    public Object handleDict(Dict dict, String fieldValue) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(dict.dictCode())) {
            List<SysDictDetail> list = SpringUtil.getBean(SysDictDetailService.class).getDictDetailByCodeFromRedis(dict.dictCode());
            for (SysDictDetail sysDictDetail : list) {
                if (sysDictDetail.getValue().equals(fieldValue)) {
                    map.put("cssClass", sysDictDetail.getCssClass());
                    map.put("listClass", sysDictDetail.getListClass());
                    map.put("label", sysDictDetail.getLabel());
                    map.put("value", sysDictDetail.getValue());
                    map.put("status", sysDictDetail.getStatus());
                    map.put("remark", sysDictDetail.getRemark());
                    return map;
                }
            }
        } else {
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
     * 数据脱敏处理
     *
     * @param masked
     * @param fieldValue
     * @return
     */
    @Override
    public Object handleMasker(Masked masked, String fieldValue) {
        return MyUtil.masked(masked.type().getType(), fieldValue);
    }

}
