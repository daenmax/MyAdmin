package cn.daenx.myadmin.common.vo;

import lombok.Data;

/**
 * 分页参数
 */
@Data
public class BasePageVo {
    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 页尺寸
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderByColumn;

    /**
     * 排序方向：desc、asc
     */
    private String isAsc;


}
