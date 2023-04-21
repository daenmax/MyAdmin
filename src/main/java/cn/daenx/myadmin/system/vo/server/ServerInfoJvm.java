package cn.daenx.myadmin.system.vo.server;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JVM信息
 */
@Data
public class ServerInfoJvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private BigDecimal total;

    /**
     * JVM最大可用内存总数(M)
     */
    private BigDecimal max;

    /**
     * JVM空闲内存(M)
     */
    private BigDecimal free;

    /**
     * 已使用(M)
     */
    private BigDecimal used;

    /**
     * 已使用百分比
     */
    private BigDecimal usage;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    /**
     * JDK名称
     */
    private String name;

    /**
     * 启动时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 运行时间
     */
    private String runTime;

    /**
     * 运行参数
     */
    private String inputArgs;
    /**
     * 单位：MB
     */
    private String unit;
}
