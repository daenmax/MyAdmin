package cn.daenx.myadmin.common.init;

import cn.daenx.myadmin.system.service.SysConfigService;
import cn.daenx.myadmin.system.service.SysDictService;
import cn.daenx.myadmin.system.service.SysJobService;
import cn.daenx.myadmin.system.service.SysOssConfigService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动后，初始化
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SysOssConfigService sysOssConfigService;
    @Resource
    private SysJobService sysJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化字典
        sysDictService.refreshCache();
        //初始化参数
        sysConfigService.refreshCache();
        //初始化OSS
        sysOssConfigService.initOssConfig();
        //初始化定时任务
        sysJobService.initJob();
    }

}
