package cn.daenx.myadmin.system.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ServerInfoVo {
    /**
     * CPU信息
     */
    private ServerInfoCpu cpu = new ServerInfoCpu();

    /**
     * 內存信息
     */
    private ServerInfoMemory memory = new ServerInfoMemory();

    /**
     * JVM信息
     */
    private ServerInfoJvm jvm = new ServerInfoJvm();

    /**
     * 系统信息
     */
    private ServerInfoSystem system = new ServerInfoSystem();

    /**
     * 磁盘信息
     */
    private List<ServerInfoDisk> disks = new ArrayList<>();

    /**
     * CPU信息
     *
     */
    @Data
    public static class ServerInfoCpu {
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

    /**
     * 磁盘信息
     */
    @Data
    public static class ServerInfoDisk {
        /**
         * 盘符路径
         */
        private String dirName;

        /**
         * 盘符类型
         */
        private String sysTypeName;

        /**
         * 文件类型
         */
        private String typeName;

        /**
         * 总大小
         */
        private String total;

        /**
         * 剩余大小
         */
        private String free;

        /**
         * 已经使用量
         */
        private String used;

        /**
         * 资源的使用率
         */
        private BigDecimal usage;

    }

    /**
     * JVM信息
     */
    @Data
    public static class ServerInfoJvm {
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

    /**
     * 內存信息
     */
    @Data
    public static class ServerInfoMemory {
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

    /**
     * 系统信息
     */
    @Data
    public static class ServerInfoSystem {
        /**
         * 服务器名称
         */
        private String computerName;

        /**
         * 服务器Ip
         */
        private String computerIp;

        /**
         * 项目路径
         */
        private String userDir;

        /**
         * 操作系统
         */
        private String osName;

        /**
         * 系统架构
         */
        private String osArch;

    }
}
