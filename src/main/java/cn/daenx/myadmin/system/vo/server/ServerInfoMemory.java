package cn.daenx.myadmin.system.vo.server;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 內存信息
 */
@Data
public class ServerInfoMemory {
    /**
     * 内存总量
     */
    private BigDecimal total;

    /**
     * 已用内存
     */
    private BigDecimal used;

    /**
     * 剩余内存
     */
    private BigDecimal free;

    /**
     * 已使用百分比
     */
    private BigDecimal usage;

    /**
     * 单位：GB
     */
    private String unit;
}
