package cn.daenx.myadmin.common.init;

import cn.daenx.myadmin.system.service.SysConfigService;
import cn.daenx.myadmin.system.service.SysDictService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;


/**
 * 项目启动初始化代码
 */
@Configuration
public class initService {
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 初始化字典
     */
    @PostConstruct
    public void initDict() {
        sysDictService.refreshCache();
    }

    /**
     * 初始化参数
     */
    @PostConstruct
    public void initConfig() {
        sysConfigService.refreshCache();
    }
}
