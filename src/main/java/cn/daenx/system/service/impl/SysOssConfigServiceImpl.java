package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.oss.utils.OssUtil;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.system.domain.vo.SysOssConfigAddVo;
import cn.daenx.system.domain.vo.SysOssConfigPageVo;
import cn.daenx.system.domain.vo.SysOssConfigUpdVo;
import cn.daenx.system.mapper.SysFileMapper;
import cn.daenx.system.domain.po.SysFile;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.system.mapper.SysOssConfigMapper;
import cn.daenx.system.domain.po.SysOssConfig;
import cn.daenx.system.service.SysOssConfigService;

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
        RedisUtil.delBatch(RedisConstant.OSS + "*");
        RedisUtil.del(RedisConstant.OSS_USE);
        for (SysOssConfig sysOssConfig : sysOssConfigs) {
            RedisUtil.setValue(RedisConstant.OSS + sysOssConfig.getId(), sysOssConfig);
            if (sysOssConfig.getInUse().equals(SystemConstant.IN_USE_YES)) {
                //正在使用的
                RedisUtil.setValue(RedisConstant.OSS_USE, sysOssConfig);
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
     * @param vo
     */
    @Override
    public void editInfo(SysOssConfigUpdVo vo) {
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, vo.getId());
        wrapper.set(SysOssConfig::getName, vo.getName());
        wrapper.set(SysOssConfig::getAccessKey, vo.getAccessKey());
        wrapper.set(SysOssConfig::getSecretKey, vo.getSecretKey());
        wrapper.set(SysOssConfig::getBucketName, vo.getBucketName());
        wrapper.set(SysOssConfig::getPrefix, vo.getPrefix());
        wrapper.set(SysOssConfig::getEndpoint, vo.getEndpoint());
        wrapper.set(SysOssConfig::getDomain, vo.getDomain());
        wrapper.set(SysOssConfig::getIsHttps, vo.getIsHttps());
        wrapper.set(SysOssConfig::getRegion, vo.getRegion());
        wrapper.set(SysOssConfig::getAccessPolicy, vo.getAccessPolicy());
        wrapper.set(SysOssConfig::getStatus, vo.getStatus());
        wrapper.set(SysOssConfig::getRemark, vo.getRemark());
        int rows = sysOssConfigMapper.update(new SysOssConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysOssConfig sysOssConfig = getInfo(vo.getId());
        RedisUtil.setValue(RedisConstant.OSS + vo.getId(), sysOssConfig);
        if (sysOssConfig.getInUse().equals(SystemConstant.IN_USE_YES)) {
            //正在使用的
            RedisUtil.setValue(RedisConstant.OSS_USE, sysOssConfig);
        }
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysOssConfigAddVo vo) {
        SysOssConfig sysOssConfig = new SysOssConfig();
        sysOssConfig.setName(vo.getName());
        sysOssConfig.setAccessKey(vo.getAccessKey());
        sysOssConfig.setSecretKey(vo.getSecretKey());
        sysOssConfig.setBucketName(vo.getBucketName());
        sysOssConfig.setPrefix(vo.getPrefix());
        sysOssConfig.setEndpoint(vo.getEndpoint());
        sysOssConfig.setDomain(vo.getDomain());
        sysOssConfig.setIsHttps(vo.getIsHttps());
        sysOssConfig.setRegion(vo.getRegion());
        sysOssConfig.setAccessPolicy(vo.getAccessPolicy());
        sysOssConfig.setStatus(vo.getStatus());
        sysOssConfig.setRemark(vo.getRemark());
        sysOssConfig.setInUse(SystemConstant.IN_USE_NO);
        int insert = sysOssConfigMapper.insert(sysOssConfig);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        RedisUtil.setValue(RedisConstant.OSS + sysOssConfig.getId(), getInfo(sysOssConfig.getId()));
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysOssConfigMapper.deleteBatchIds(ids);
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
     * @param vo
     */
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, vo.getId());
        wrapper.set(SysOssConfig::getStatus, vo.getStatus());
        if (!vo.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
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
     * @param vo
     */
    @Override
    public void changeInUse(ComStatusUpdVo vo) {
        SysOssConfig info = getInfo(vo.getId());
        if (!info.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            throw new MyException("OSS配置状态非正常，无法启用");
        }
        if (info.getInUse().equals(vo.getStatus())) {
            throw new MyException("已是当前状态");
        }
        LambdaUpdateWrapper<SysOssConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysOssConfig::getId, vo.getId());
        wrapper.set(SysOssConfig::getInUse, vo.getStatus());
        int rows = sysOssConfigMapper.update(new SysOssConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        info.setInUse(vo.getStatus());
        if(vo.getStatus().equals(SystemConstant.IN_USE_YES)){
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
