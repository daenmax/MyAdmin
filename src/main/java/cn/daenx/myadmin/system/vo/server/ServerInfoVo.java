package cn.daenx.myadmin.system.vo.server;

import cn.daenx.myadmin.system.vo.server.*;
import lombok.Data;

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
}
