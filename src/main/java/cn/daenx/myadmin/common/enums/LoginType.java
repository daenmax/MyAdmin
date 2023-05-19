package cn.daenx.myadmin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录方式
 */
@Getter
@AllArgsConstructor
public enum LoginType {


    USERNAME("username", "账号密码登录"),
    EMAIL("email", "邮箱验证码登录"),
    PHONE("phone", "短信验证码登录"),
    API("apikey", "开放API登录");


    private String code;
    private String desc;
    private static final Map<String, String> codeMap;
    private static final Map<String, String> descMap;
    private static final List<String> list;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        list = new ArrayList<>();
        for (LoginType value : LoginType.values()) {
            codeMap.put(value.getCode(), value.getDesc());
            descMap.put(value.getDesc(), value.getCode());
            list.add(value.getCode());
        }
    }

    public static String getDesc(String code) {
        String desc = codeMap.get(code);
        if (desc == null) {
            return "";
        }
        return desc;
    }

    public static String getCode(String desc) {
        String code = descMap.get(desc);
        if (code == null) {
            return "";
        }
        return code;
    }

    public static boolean hasCode(String code) {
        return list.contains(code);
    }
}
