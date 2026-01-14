package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.oss.utils.OssUtil;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigUpdDto;
import cn.daenx.modules.system.domain.po.SysFile;
import cn.daenx.modules.system.domain.po.SysOssConfig;
import cn.daenx.modules.system.mapper.SysFileMapper;
import cn.daenx.modules.system.mapper.SysOssConfigMapper;
import cn.daenx.modules.system.service.SysOssConfigService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOssConfigServiceImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfig> implements SysOssConfigService {

    @Resource
    private SysOssConfigMapper sysOssConfigMapper;
    @Resource
    private SysFileMapper sysFileMapper;


    /**
     * 初始化OSS
     */
    @Override
    public void initOssConfig() {
        LambdaQueryWrapper<SysOssConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOssConfig::getStatus, CommonConstant.STATUS_NORMAL);
        List<SysOssConfig> sysOssConfigs = sysOssConfigMapper.selectList(wrapper);
        CacheUtil.delBatch(RedisConstant.OSS + "*");
        CacheUtil.del(RedisConstant.OSS_USE);
        for (SysOssConfig sysOssConfig : sysOssConfigs) {
            CacheUtil.setValue(RedisConstant.OSS + sysOssConfig.getId(), sysOssConfig);
            if (sysOssConfig.getInUse().equals(SystemConstant.IN_USE_YES)) {
                //正在使用的
                CacheUtil.setValue(RedisConstant.OSS_USE, sysOssConfig);
            }
        }
    }

    private LambdaQueryWrapper<SysOssConfig> getWrapper(SysOssConfigPageDto dto) {
        LambdaQueryWrapper<SysOssConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getName()), SysOssConfig::getName, dto.getName());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getBucketName()), SysOssConfig::getBucketName, dto.getBucketName());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getPrefix()), SysOssConfig::getPrefix, dto.getPrefix());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getEndpoint()), SysOssConfig::getEndpoint, dto.getEndpoint());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getDomain()), SysOssConfig::getDomain, dto.getDomain());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getIsHttps()), SysOssConfig::getIsHttps, dto.getIsHttps());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRegion()), SysOssConfig::getRegion, dto.getRegion());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getAccessPolicy()), SysOssConfig::getAccessPolicy, dto.getAccessPolicy());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getInUse()), SysOssConfig::getInUse, dto.getInUse());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysOssConfig::getStatus, dto.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getRemark()), SysOssConfig::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysOssConfig::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<SysOssConfig> getPage(SysOssConfigPageDto dto) {
        LambdaQueryWrapper<SysOssConfig> wrapper = getWrapper(dto);
        Page<SysOssConfig> sysOssConfigPage = sysOssConfigMapper.selectPage(dto.getPage(true), wrapper);
        return sysOssConfigPage;
    }

    /**
     * 获取所有列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<SysOssConfig> getAll(SysOssConfigPageDto dto) {
        LambdaQueryWrapper<SysOssConfig> wrapper = getWrapper(dto);
        List<SysOssConfig> sysOssConfigs = sysOssConfigMapper.selectList(wrapper);
        return sysOssConfigs;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysOssConfig getInfo(String id) {
        return sysOssConfigMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @Override
    public void editInfo(SysOssConfigUpdDto dto) {
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, dto.getId());
        wrapper.set(SysOssConfig::getName, dto.getName());
        wrapper.set(SysOssConfig::getAccessKey, dto.getAccessKey());
        wrapper.set(SysOssConfig::getSecretKey, dto.getSecretKey());
        wrapper.set(SysOssConfig::getBucketName, dto.getBucketName());
        wrapper.set(SysOssConfig::getPrefix, dto.getPrefix());
        wrapper.set(SysOssConfig::getEndpoint, dto.getEndpoint());
        wrapper.set(SysOssConfig::getDomain, dto.getDomain());
        wrapper.set(SysOssConfig::getIsHttps, dto.getIsHttps());
        wrapper.set(SysOssConfig::getRegion, dto.getRegion());
        wrapper.set(SysOssConfig::getAccessPolicy, dto.getAccessPolicy());
        wrapper.set(SysOssConfig::getStatus, dto.getStatus());
        wrapper.set(SysOssConfig::getUrlValidAccessTime, dto.getUrlValidAccessTime());
        wrapper.set(SysOssConfig::getUrlValidCacheTime, dto.getUrlValidCacheTime());
        wrapper.set(SysOssConfig::getRemark, dto.getRemark());
        int rows = sysOssConfigMapper.update(new SysOssConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysOssConfig sysOssConfig = getInfo(dto.getId());
        CacheUtil.setValue(RedisConstant.OSS + dto.getId(), sysOssConfig);
        if (sysOssConfig.getInUse().equals(SystemConstant.IN_USE_YES)) {
            //正在使用的
            CacheUtil.setValue(RedisConstant.OSS_USE, sysOssConfig);
        }
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysOssConfigAddDto dto) {
        SysOssConfig sysOssConfig = new SysOssConfig();
        sysOssConfig.setName(dto.getName());
        sysOssConfig.setAccessKey(dto.getAccessKey());
        sysOssConfig.setSecretKey(dto.getSecretKey());
        sysOssConfig.setBucketName(dto.getBucketName());
        sysOssConfig.setPrefix(dto.getPrefix());
        sysOssConfig.setEndpoint(dto.getEndpoint());
        sysOssConfig.setDomain(dto.getDomain());
        sysOssConfig.setIsHttps(dto.getIsHttps());
        sysOssConfig.setRegion(dto.getRegion());
        sysOssConfig.setAccessPolicy(dto.getAccessPolicy());
        sysOssConfig.setStatus(dto.getStatus());
        sysOssConfig.setUrlValidAccessTime(dto.getUrlValidAccessTime());
        sysOssConfig.setUrlValidCacheTime(dto.getUrlValidCacheTime());
        sysOssConfig.setRemark(dto.getRemark());
        sysOssConfig.setInUse(SystemConstant.IN_USE_NO);
        int insert = sysOssConfigMapper.insert(sysOssConfig);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        CacheUtil.setValue(RedisConstant.OSS + sysOssConfig.getId(), getInfo(sysOssConfig.getId()));
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysOssConfigMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
        //删除所有库里的文件，但是不删除云上的
        LambdaUpdateWrapper<SysFile> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(SysFile::getOssId, ids);
        sysFileMapper.delete(wrapper);
        for (String id : ids) {
            //删除已经缓存的oss实例
            OssUtil.removeKey(id);
        }
        //重新初始化数据到redis
        initOssConfig();
    }

    /**
     * 修改配置状态
     *
     * @param dto
     */
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, dto.getId());
        wrapper.set(SysOssConfig::getStatus, dto.getStatus());
        if (!dto.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            //不是启用，那么强制设置使用状态为否
            wrapper.set(SysOssConfig::getInUse, SystemConstant.IN_USE_NO);
        }
        int rows = sysOssConfigMapper.update(new SysOssConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //重新初始化数据到redis
        initOssConfig();
    }

    /**
     * 修改使用状态
     *
     * @param dto
     */
    @Override
    public void changeInUse(ComStatusUpdDto dto) {
        SysOssConfig info = getInfo(dto.getId());
        if (!info.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            throw new MyException("OSS配置状态非正常，无法启用");
        }
        if (info.getInUse().equals(dto.getStatus())) {
            throw new MyException("已是当前状态");
        }
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, dto.getId());
        wrapper.set(SysOssConfig::getInUse, dto.getStatus());
        int rows = sysOssConfigMapper.update(new SysOssConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        info.setInUse(dto.getStatus());
        if(dto.getStatus().equals(SystemConstant.IN_USE_YES)){
            //启用，那么关闭其他配置的使用状态
            LambdaUpdateWrapper<SysOssConfig> wrapper2 = new LambdaUpdateWrapper<>();
            wrapper2.ne(SysOssConfig::getId, info.getId());
            wrapper2.set(SysOssConfig::getInUse, SystemConstant.IN_USE_NO);
            sysOssConfigMapper.update(new SysOssConfig(), wrapper2);
        }
        //重新初始化数据到redis
        initOssConfig();
    }
}
