package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.domain.vo.system.config.*;
import cn.daenx.framework.common.domain.vo.system.config.SysLoginFailInfoConfigVo;
import cn.daenx.framework.common.domain.vo.system.config.SysRegisterDefaultInfoVo;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigUpdDto;
import cn.daenx.modules.system.mapper.SysConfigMapper;
import cn.daenx.modules.system.domain.po.SysConfig;
import cn.daenx.modules.system.service.SysConfigService;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

    private LambdaQueryWrapper<SysConfig> getWrapper(SysConfigPageDto vo) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysConfig::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getKeyVa()), SysConfig::getKeyVa, vo.getKeyVa());
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
    public IPage<SysConfig> getPage(SysConfigPageDto vo) {
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
    public List<SysConfig> getAll(SysConfigPageDto vo) {
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
        wrapper.eq(SysConfig::getKeyVa, key);
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
    public void editInfo(SysConfigUpdDto vo) {
        if (checkKeyExist(vo.getKeyVa(), vo.getId())) {
            throw new MyException("参数键值已存在");
        }
        LambdaUpdateWrapper<SysConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysConfig::getId, vo.getId());
        wrapper.set(SysConfig::getName, vo.getName());
        wrapper.set(SysConfig::getKeyVa, vo.getKeyVa());
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
        CacheUtil.setValue(RedisConstant.CONFIG + info.getKeyVa(), info);
        //刷新邮箱配置队列
        updateSysEmailInfo(info.getKeyVa(), 2);
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysConfigAddDto vo) {
        if (checkKeyExist(vo.getKeyVa(), null)) {
            throw new MyException("参数键值" + vo.getKeyVa() + "已存在");
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setName(vo.getName());
        sysConfig.setKeyVa(vo.getKeyVa());
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
        CacheUtil.setValue(RedisConstant.CONFIG + info.getKeyVa(), info);
        //刷新邮箱配置队列
        updateSysEmailInfo(info.getKeyVa(), 1);
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
            CacheUtil.del(RedisConstant.CONFIG + sysConfig.getKeyVa());
            //刷新邮箱配置队列
            updateSysEmailInfo(sysConfig.getKeyVa(), 3);
        }
        int i = sysConfigMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 根据参数键名查询参数键值
     * 如果参数键名不存在或者未查询到，返回null
     * 如果参数被禁用了，返回空字符串""
     * 可根据此区别来进行你的业务逻辑
     *
     * @param key
     * @return
     */
    @Override
    public String getConfigByKey(String key) {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + key);
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return "";
        }
        return sysConfig.getValue();
    }

    /**
     * 刷新参数缓存
     */
    @Override
    public void refreshCache() {
        CacheUtil.delBatch(RedisConstant.CONFIG + "*");
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        for (SysConfig sysConfig : sysConfigs) {
            CacheUtil.setValue(RedisConstant.CONFIG + sysConfig.getKeyVa(), sysConfig);
            //刷新邮箱配置队列
            updateSysEmailInfo(sysConfig.getKeyVa(), 0);
        }
    }

    /**
     * 获取允许上传的图片后缀
     * 如果未配置则返回默认
     *
     * @return
     */
    @Override
    public SysUploadConfigVo getSysUploadImageSuffixs() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.upload.image");
        if (ObjectUtil.isEmpty(object)) {
            return SystemConstant.UPLOAD_CONFIG_IMAGE;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysUploadConfigVo sysUploadConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysUploadConfigVo.class);
        return sysUploadConfigVo;
    }

    /**
     * 获取允许上传的文件后缀
     * 如果未配置则返回默认
     *
     * @return
     */
    @Override
    public SysUploadConfigVo getSysUploadFileSuffixs() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.upload.file");
        if (ObjectUtil.isEmpty(object)) {
            return SystemConstant.UPLOAD_CONFIG_FILE;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysUploadConfigVo sysUploadConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysUploadConfigVo.class);
        return sysUploadConfigVo;
    }

    /**
     * 获取系统注册默认信息
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    @Override
    public SysRegisterDefaultInfoVo getSysRegisterDefaultInfoVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.register.default.info");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysRegisterDefaultInfoVo sysRegisterDefaultInfoVo = JSONObject.parseObject(sysConfig.getValue(), SysRegisterDefaultInfoVo.class);
        return sysRegisterDefaultInfoVo;
    }

    /**
     * 系统登录错误次数限制信息
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    @Override
    public SysLoginFailInfoConfigVo getSysLoginFailInfoVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.login.fail.info");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysLoginFailInfoConfigVo sysLoginFailInfoConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysLoginFailInfoConfigVo.class);
        return sysLoginFailInfoConfigVo;
    }

    /**
     * 系统验证码配置
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    @Override
    public SysCaptchaConfigVo getSysCaptchaConfigVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.captcha.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysCaptchaConfigVo sysCaptchaConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysCaptchaConfigVo.class);
        return sysCaptchaConfigVo;
    }

    /**
     * 获取系统短信模板ID配置
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    @Override
    public SysSmsTemplateConfigVo getSysSmsTemplateConfigVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.smsTemplate.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysSmsTemplateConfigVo sysSmsTemplateConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysSmsTemplateConfigVo.class);
        return sysSmsTemplateConfigVo;
    }

    /**
     * 获取系统发送限制配置
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    @Override
    public SysSendLimitConfigVo getSysSendLimitConfigVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.sendLimit.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysSendLimitConfigVo sysSendLimitConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysSendLimitConfigVo.class);
        return sysSendLimitConfigVo;
    }

    /**
     * 获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    @Override
    public SysEmailConfigVo getSysEmailConfigVo() {
        Object object = CacheUtil.getValue(RedisConstant.CONFIG + "sys.email.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysEmailConfigVo sysEmailConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysEmailConfigVo.class);
        if (sysEmailConfigVo.getEmails().size() == 0) {
            return null;
        }
        return sysEmailConfigVo;
    }

    /**
     * 处理系统邮箱配置
     *
     * @param key
     * @param type 0=初始化，1=新增，2=修改，3=删除
     */
    @Override
    public void updateSysEmailInfo(String key, Integer type) {
        if (!"sys.email.config".equals(key)) {
            return;
        }
        if (type == 0 || type == 1 || type == 2) {
            CacheUtil.del(SystemConstant.EMAIL_POLL_KEY);
            SysEmailConfigVo sysEmailConfigVo = getSysEmailConfigVo();
            List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable())).collect(Collectors.toList());
            List<String> emailList = MyUtil.joinToList(list, SysEmailConfigVo.Email::getEmail);
            CacheUtil.leftPushAll(SystemConstant.EMAIL_POLL_KEY, emailList);
        } else {
            CacheUtil.del(SystemConstant.EMAIL_POLL_KEY);
        }
    }
}
