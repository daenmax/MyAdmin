package cn.daenx.myadmin.common.vo;

import lombok.Data;

/**
 * 数据权限实体类
 */
@Data
public class DataScopeParam {

    /**
     * 表的别名
     */
    private String alias;

    /**
     * 字段名
     */
    private String field;

}
