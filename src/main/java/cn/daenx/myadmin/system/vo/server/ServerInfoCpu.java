package cn.daenx.myadmin.system.vo.server;

import lombok.Data;

import java.math.BigDecimal;

/**
 * CPU信息
 *
 */
@Data
public class ServerInfoCpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU系统使用率
     */
    private BigDecimal sys;

    /**
     * CPU用户使用率
     */
    private BigDecimal used;

    /**
     * CPU当前等待率
     */
    private BigDecimal wait;

    /**
     * CPU当前空闲率
     */
    private BigDecimal free;

}
