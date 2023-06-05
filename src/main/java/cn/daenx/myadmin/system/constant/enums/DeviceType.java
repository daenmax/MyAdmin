package cn.daenx.myadmin.system.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备类型
 */
@Getter
@AllArgsConstructor
public enum DeviceType {

    /**
     * PC
     */
    PC("PC", "PC"),
    /**
     * APP
     */
    APP("APP", "APP"),
    /**
     * 微信小程序
     */
    WX_APP("WX_APP", "微信小程序"),
    /**
     * 开放API
     */
    API("API", "开放API");


    private String code;
    private String desc;
    private static final Map<String, String> codeMap;
    private static final Map<String, String> descMap;
    private static final List<String> list;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        list = new ArrayList<>();
        for (DeviceType value : DeviceType.values()) {
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
