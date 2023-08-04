package cn.daenx.framework.serializer;

import cn.daenx.common.constant.RedisConstant;
import cn.daenx.common.utils.MyUtil;
import cn.daenx.common.utils.RedisUtil;
import cn.daenx.common.vo.system.other.SysDictDetailVo;
import cn.daenx.framework.serializer.annotation.Dict;
import cn.daenx.framework.serializer.annotation.DictDetail;
import cn.daenx.framework.serializer.annotation.Masked;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
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
            Object object = RedisUtil.getValue(RedisConstant.DICT + dict.dictCode());
            List<SysDictDetailVo> list = JSON.parseArray(JSON.toJSONString(object), SysDictDetailVo.class);
            for (SysDictDetailVo sysDictDetail : list) {
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
