package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.data.system.domain.vo.ServerInfoVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.NumberUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;
import oshi.hardware.CentralProcessor.TickType;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    /**
     * 查询服务监控详细信息
     *
     * @return
     */
    @SaCheckPermission("monitor:server:list")
    @GetMapping("/getInfo")
    public Result server() {
        oshi.SystemInfo si = new oshi.SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        ServerInfoVo serverInfoVo = new ServerInfoVo();
        serverInfoVo.setCpu(getCpu(hal.getProcessor()));
        serverInfoVo.setMemory(getMemory(hal.getMemory()));
        serverInfoVo.setJvm(getJvm());
        serverInfoVo.setSystem(getSystem());
        serverInfoVo.setDisks(getDisks(si.getOperatingSystem()));
        return Result.ok(serverInfoVo);
    }

    private ServerInfoVo.ServerInfoCpu getCpu(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.ordinal()] - prevTicks[CentralProcessor.TickType.NICE.ordinal()];
        long irq = ticks[TickType.IRQ.ordinal()] - prevTicks[TickType.IRQ.ordinal()];
        long softIrq = ticks[TickType.SOFTIRQ.ordinal()] - prevTicks[TickType.SOFTIRQ.ordinal()];
        long steal = ticks[TickType.STEAL.ordinal()] - prevTicks[TickType.STEAL.ordinal()];
        long cSys = ticks[TickType.SYSTEM.ordinal()] - prevTicks[TickType.SYSTEM.ordinal()];
        long user = ticks[TickType.USER.ordinal()] - prevTicks[TickType.USER.ordinal()];
        long ioWait = ticks[TickType.IOWAIT.ordinal()] - prevTicks[TickType.IOWAIT.ordinal()];
        long idle = ticks[TickType.IDLE.ordinal()] - prevTicks[TickType.IDLE.ordinal()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;
        ServerInfoVo.ServerInfoCpu serverInfoCpu = new ServerInfoVo.ServerInfoCpu();
        serverInfoCpu.setCpuNum(processor.getLogicalProcessorCount());
        serverInfoCpu.setSys(NumberUtil.round(NumberUtil.div(cSys * 100, totalCpu), 2));
        serverInfoCpu.setUsed(NumberUtil.round(NumberUtil.div(user * 100, totalCpu), 2));
        serverInfoCpu.setWait(NumberUtil.round(NumberUtil.div(ioWait * 100, totalCpu), 2));
        serverInfoCpu.setFree(NumberUtil.round(NumberUtil.div(idle * 100, totalCpu), 2));
        return serverInfoCpu;
    }

    private ServerInfoVo.ServerInfoMemory getMemory(GlobalMemory memory) {
        ServerInfoVo.ServerInfoMemory serverInfoMemory = new ServerInfoVo.ServerInfoMemory();
        long total = memory.getTotal();
        long free = memory.getAvailable();
        long used = total - free;
        serverInfoMemory.setTotal(NumberUtil.round(NumberUtil.div(total, (1024 * 1024 * 1024)), 2));
        serverInfoMemory.setFree(NumberUtil.round(NumberUtil.div(free, (1024 * 1024 * 1024)), 2));
        serverInfoMemory.setUsed(NumberUtil.round(NumberUtil.div(used, (1024 * 1024 * 1024)), 2));
        serverInfoMemory.setUsage(NumberUtil.round(NumberUtil.div(used * 100, total), 2));
        serverInfoMemory.setUnit("GB");
        return serverInfoMemory;
    }

    private ServerInfoVo.ServerInfoJvm getJvm() {
        ServerInfoVo.ServerInfoJvm serverInfoJvm = new ServerInfoVo.ServerInfoJvm();
        Properties props = System.getProperties();
        long total = Runtime.getRuntime().totalMemory();
        long max = Runtime.getRuntime().maxMemory();
        long free = Runtime.getRuntime().freeMemory();
        long used = total - free;
        serverInfoJvm.setTotal(NumberUtil.round(NumberUtil.div(total, (1024 * 1024)), 2));
        serverInfoJvm.setMax(NumberUtil.round(NumberUtil.div(max, (1024 * 1024)), 2));
        serverInfoJvm.setFree(NumberUtil.round(NumberUtil.div(free, (1024 * 1024)), 2));
        serverInfoJvm.setUsed(NumberUtil.round(NumberUtil.div(used, (1024 * 1024)), 2));
        serverInfoJvm.setUsage(NumberUtil.round(NumberUtil.div(used * 100, total), 2));
        serverInfoJvm.setVersion(props.getProperty("java.version"));
        serverInfoJvm.setHome(props.getProperty("java.home"));
        serverInfoJvm.setName(ManagementFactory.getRuntimeMXBean().getVmName());
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        serverInfoJvm.setStartTime(MyUtil.toLocalDateTime(startTime));
        serverInfoJvm.setRunTime(MyUtil.timeDistance(new Date().getTime(), startTime));
        serverInfoJvm.setInputArgs(ManagementFactory.getRuntimeMXBean().getInputArguments().toString());
        serverInfoJvm.setUnit("MB");
        return serverInfoJvm;
    }

    private ServerInfoVo.ServerInfoSystem getSystem() {
        ServerInfoVo.ServerInfoSystem serverInfoSystem = new ServerInfoVo.ServerInfoSystem();
        Properties props = System.getProperties();
        serverInfoSystem.setComputerName(MyUtil.getHostName());
        serverInfoSystem.setComputerIp(MyUtil.getHostIp());
        serverInfoSystem.setUserDir(props.getProperty("user.dir"));
        serverInfoSystem.setOsName(props.getProperty("os.name"));
        serverInfoSystem.setOsArch(props.getProperty("os.arch"));
        return serverInfoSystem;
    }

    private List<ServerInfoVo.ServerInfoDisk> getDisks(OperatingSystem os) {
        List<ServerInfoVo.ServerInfoDisk> serverInfoDisks = new ArrayList<>();
        List<OSFileStore> fsArray = os.getFileSystem().getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            ServerInfoVo.ServerInfoDisk serverInfoDisk = new ServerInfoVo.ServerInfoDisk();
            serverInfoDisk.setDirName(fs.getMount());
            serverInfoDisk.setSysTypeName(fs.getType());
            serverInfoDisk.setTypeName(fs.getName());
            serverInfoDisk.setTotal(MyUtil.convertFileSize(total));
            serverInfoDisk.setFree(MyUtil.convertFileSize(free));
            serverInfoDisk.setUsed(MyUtil.convertFileSize(used));
            serverInfoDisk.setUsage(NumberUtil.round(NumberUtil.div(used * 100, total), 2));
            serverInfoDisks.add(serverInfoDisk);
        }
        return serverInfoDisks;
    }
}
