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

import java.util.List;

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
        wrapper.eq(SysOssConfig::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysOssConfig> sysOssConfigs = sysOssConfigMapper.selectList(wrapper);
        RedisUtil.delBatch(RedisConstant.OSS + "*");
        RedisUtil.del(RedisConstant.OSS_USE);
        for (SysOssConfig sysOssConfig : sysOssConfigs) {
            RedisUtil.setValue(RedisConstant.OSS + sysOssConfig.getId(), sysOssConfig, null, null);
            if (sysOssConfig.getStatus().equals(SystemConstant.IN_USE_YES)) {
                //正在使用的
                RedisUtil.setValue(RedisConstant.OSS_USE, sysOssConfig, null, null);
            }
        }
    }
}
