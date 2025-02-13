package cn.daenx.server.lifecycle;

import cn.daenx.system.service.*;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动后，初始化
 */
@Component
public class AppRunner implements ApplicationRunner {
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SysOssConfigService sysOssConfigService;
    @Resource
    private SysJobService sysJobService;
    @Resource
    private SysApiLimitService sysApiLimitService;

    @Override
    public void run(ApplicationArguments args) {
        //初始化字典
        sysDictService.refreshCache();
        //初始化参数
        sysConfigService.refreshCache();
        //初始化OSS
        sysOssConfigService.initOssConfig();
        //初始化定时任务
        sysJobService.initJob();
        //初始化接口限制策略
        sysApiLimitService.refreshApiLimitCache();
    }

}
