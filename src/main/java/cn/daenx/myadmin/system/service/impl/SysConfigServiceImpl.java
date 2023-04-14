package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.vo.SysConfigAddVo;
import cn.daenx.myadmin.system.vo.SysConfigPageVo;
import cn.daenx.myadmin.system.vo.SysConfigUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysConfigMapper;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.service.SysConfigService;

import java.util.List;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;
    @Resource
    private RedisUtil redisUtil;

    private LambdaQueryWrapper<SysConfig> getWrapper(SysConfigPageVo vo) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysConfig::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getKey()), SysConfig::getKey, vo.getKey());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getValue()), SysConfig::getValue, vo.getValue());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), SysConfig::getType, vo.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysConfig::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRemark()), SysConfig::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysConfig::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysConfig> getPage(SysConfigPageVo vo) {
        LambdaQueryWrapper<SysConfig> wrapper = getWrapper(vo);
        Page<SysConfig> sysConfigPage = sysConfigMapper.selectPage(vo.getPage(true), wrapper);
        return sysConfigPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysConfig> getAll(SysConfigPageVo vo) {
        LambdaQueryWrapper<SysConfig> wrapper = getWrapper(vo);
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        return sysConfigs;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysConfig getInfo(String id) {
        return sysConfigMapper.selectById(id);
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param key
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkKeyExist(String key, String nowId) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getKey, key);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysConfig::getId, nowId);
        boolean exists = sysConfigMapper.exists(wrapper);
        return exists;
    }

    /**
     * 修改
     *
     * @param vo
     */
    @Override
    public void editInfo(SysConfigUpdVo vo) {
        if (checkKeyExist(vo.getKey(), vo.getId())) {
            throw new MyException("参数键值已存在");
        }
        LambdaUpdateWrapper<SysConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysConfig::getId, vo.getId());
        wrapper.set(SysConfig::getName, vo.getName());
        wrapper.set(SysConfig::getKey, vo.getKey());
        wrapper.set(SysConfig::getValue, vo.getValue());
        wrapper.set(SysConfig::getType, vo.getType());
        wrapper.set(SysConfig::getStatus, vo.getStatus());
        wrapper.set(SysConfig::getRemark, vo.getRemark());
        int rows = sysConfigMapper.update(new SysConfig(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //刷新redis缓存
        SysConfig info = getInfo(vo.getId());
        redisUtil.del(RedisConstant.CONFIG + info.getKey());
        if (info.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            redisUtil.setValue(RedisConstant.CONFIG + info.getKey(), info, null, null);
        }
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysConfigAddVo vo) {
        if (checkKeyExist(vo.getKey(), null)) {
            throw new MyException("参数键值" + vo.getKey() + "已存在");
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setName(vo.getName());
        sysConfig.setKey(vo.getKey());
        sysConfig.setValue(vo.getValue());
        sysConfig.setType(vo.getType());
        sysConfig.setStatus(vo.getStatus());
        sysConfig.setRemark(vo.getRemark());
        int insert = sysConfigMapper.insert(sysConfig);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        //刷新redis缓存
        SysConfig info = getInfo(sysConfig.getId());
        redisUtil.del(RedisConstant.CONFIG + info.getKey());
        if (info.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            redisUtil.setValue(RedisConstant.CONFIG + info.getKey(), info, null, null);
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        //刷新redis缓存
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysConfig::getId, ids);
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        for (SysConfig sysConfig : sysConfigs) {
            redisUtil.del(RedisConstant.CONFIG + sysConfig.getKey());
        }
        int i = sysConfigMapper.deleteBatchIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 根据参数键名查询参数键值
     * 未查询到或者被禁用了返回""
     *
     * @param key
     * @return
     */
    @Override
    public String getConfigByKey(String key) {
        Object object = redisUtil.getValue(RedisConstant.CONFIG + key);
        if (ObjectUtil.isEmpty(object)) {
            return "";
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return "";
        }
        return sysConfig.getValue();
    }

    /**
     * 刷新参数缓存
     */
    @Override
    public void refreshCache() {
        redisUtil.delBatch(RedisConstant.CONFIG + "*");
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        for (SysConfig sysConfig : sysConfigs) {
            redisUtil.setValue(RedisConstant.CONFIG + sysConfig.getKey(), sysConfig, null, null);
        }
    }
}
