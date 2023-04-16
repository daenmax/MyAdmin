package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysOssConfigMapper;
import cn.daenx.myadmin.system.po.SysOssConfig;
import cn.daenx.myadmin.system.service.SysOssConfigService;

@Service
public class SysOssConfigServiceImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfig> implements SysOssConfigService {

    @Resource
    private SysOssConfigMapper sysOssConfigMapper;


    /**
     * 初始化OSS
     */
    @Override
    public void initOssConfig() {
        LambdaQueryWrapper<SysOssConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOssConfig::getInUse, SystemConstant.IN_USE_YES);
        wrapper.eq(SysOssConfig::getStatus, SystemConstant.STATUS_NORMAL);
        SysOssConfig sysOssConfig = sysOssConfigMapper.selectOne(wrapper);
        RedisUtil.del(RedisConstant.OSS);
        if (sysOssConfig != null) {
            RedisUtil.setValue(RedisConstant.OSS, sysOssConfig, null, null);
        }
    }
}
