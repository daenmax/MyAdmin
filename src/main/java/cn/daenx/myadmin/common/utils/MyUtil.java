package cn.daenx.myadmin.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MyUtil {

    /**
     * 获取IP地址
     *
     * @param ip
     * @return
     */
    public static String getIpLocation(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            return "内网IP";
        }
        String res = HttpUtil.get("https://ip.useragentinfo.com/json?ip=" + ip);
        if (ObjectUtil.isNotEmpty(res)) {
            JSONObject jsonObject = JSONUtil.parseObj(res);
            if (jsonObject.getInt("code") == 200) {
                return jsonObject.getStr("province") + jsonObject.getStr("city") + " " + jsonObject.getStr("isp");
            }
        }
        res = HttpUtil.get("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&oe=utf8");
        if (ObjectUtil.isNotEmpty(res)) {
            JSONObject jsonObject = JSONUtil.parseObj(res);
            if ("0".equals(jsonObject.getStr("status"))) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject jsonObject1 = data.get(0, JSONObject.class);
                    return jsonObject1.getStr("location");
                }
            }
        }
        return "XXX XXX XXX";
    }

    /**
     * 拼接List<对象>中指定字段
     *
     * @param collection
     * @param function
     * @param flag       拼接符号
     * @param <E>
     * @return
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function, String flag) {
        if (CollUtil.isEmpty(collection)) {
            return "";
        }
        return collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.joining(flag));
    }

}
