package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.vo.SysFilePageVo;
import cn.daenx.myadmin.system.vo.SysOssConfigPageVo;
import cn.daenx.myadmin.system.vo.SysPositionPageVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
            if (sysOssConfig.getInUse().equals(SystemConstant.IN_USE_YES)) {
                //正在使用的
                RedisUtil.setValue(RedisConstant.OSS_USE, sysOssConfig, null, null);
            }
        }
    }

    private LambdaQueryWrapper<SysOssConfig> getWrapper(SysOssConfigPageVo vo) {
        LambdaQueryWrapper<SysOssConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysOssConfig::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getBucketName()), SysOssConfig::getBucketName, vo.getBucketName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getPrefix()), SysOssConfig::getPrefix, vo.getPrefix());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getEndpoint()), SysOssConfig::getEndpoint, vo.getEndpoint());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getDomain()), SysOssConfig::getDomain, vo.getDomain());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getIsHttps()), SysOssConfig::getIsHttps, vo.getIsHttps());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRegion()), SysOssConfig::getRegion, vo.getRegion());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getAccessPolicy()), SysOssConfig::getAccessPolicy, vo.getAccessPolicy());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getInUse()), SysOssConfig::getInUse, vo.getInUse());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysOssConfig::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRemark()), SysOssConfig::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysOssConfig::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysOssConfig> getPage(SysOssConfigPageVo vo) {
        LambdaQueryWrapper<SysOssConfig> wrapper = getWrapper(vo);
        Page<SysOssConfig> sysOssConfigPage = sysOssConfigMapper.selectPage(vo.getPage(true), wrapper);
        return sysOssConfigPage;
    }

    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysOssConfig> getAll(SysOssConfigPageVo vo) {
        LambdaQueryWrapper<SysOssConfig> wrapper = getWrapper(vo);
        List<SysOssConfig> sysOssConfigs = sysOssConfigMapper.selectList(wrapper);
        return sysOssConfigs;
    }
}
