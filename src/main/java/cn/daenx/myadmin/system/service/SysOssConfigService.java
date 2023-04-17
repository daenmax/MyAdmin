package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysOssConfig;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysOssConfigService extends IService<SysOssConfig> {

    /**
     * 初始化OSS
     */
    void initOssConfig();
}
