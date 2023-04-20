package cn.daenx.myadmin.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志类型
 */
public enum LogOperTypeEnum {

    OTHER("0", "其他"),

    ADD("1", "新增"),

    REMOVE("2", "删除"),

    EDIT("3", "修改"),

    QUERY("4", "查询"),

    IMPORT("5", "导入"),

    EXPORT("6", "导出"),

    UPLOAD("7", "上传"),

    DOWNLOAD("8", "下载");
    DOWNLOAD("8", "分页");
    DOWNLOAD("8", "下载模板");

    private String code;
    private String desc;

    private static final Map<String, String> map;
    private static final List<String> list;

    static {
        map = new HashMap<>();
        list = new ArrayList<>();
        for (LogOperTypeEnum value : LogOperTypeEnum.values()) {
            map.put(value.getCode(), value.getDesc());
            list.add(value.getCode());
        }
    }

    public static String getDesc(String code) {
        String desc = map.get(code);
        if (desc == null) {
            return "";
        }
        return desc;
    }

    public static boolean hasCode(String code) {
        return list.contains(code);
    }

    LogOperTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    LogOperTypeEnum() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
