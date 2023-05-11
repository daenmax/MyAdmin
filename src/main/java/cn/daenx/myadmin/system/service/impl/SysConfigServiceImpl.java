package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.EmailUtil;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.vo.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
import java.util.stream.Collectors;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

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
        RedisUtil.setValue(RedisConstant.CONFIG + info.getKey(), info);
        //刷新邮箱配置队列
        updateSysEmailInfo(info.getKey(), 2);
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
        RedisUtil.setValue(RedisConstant.CONFIG + info.getKey(), info);
        //刷新邮箱配置队列
        updateSysEmailInfo(info.getKey(), 1);
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
            RedisUtil.del(RedisConstant.CONFIG + sysConfig.getKey());
            //刷新邮箱配置队列
            updateSysEmailInfo(sysConfig.getKey(), 3);
        }
        int i = sysConfigMapper.deleteBatchIds(ids);
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
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + key);
        if (ObjectUtil.isEmpty(object)) {
            return null;
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
        RedisUtil.delBatch(RedisConstant.CONFIG + "*");
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        for (SysConfig sysConfig : sysConfigs) {
            RedisUtil.setValue(RedisConstant.CONFIG + sysConfig.getKey(), sysConfig);
            //刷新邮箱配置队列
            updateSysEmailInfo(sysConfig.getKey(), 0);
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
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.upload.image");
        if (ObjectUtil.isEmpty(object)) {
            return SystemConstant.UPLOAD_CONFIG_IMAGE;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
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
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.upload.file");
        if (ObjectUtil.isEmpty(object)) {
            return SystemConstant.UPLOAD_CONFIG_FILE;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
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
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.register.default.info");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
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
    public SysLoginFailInfoVo getSysLoginFailInfoVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.login.fail.info");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return null;
        }
        SysLoginFailInfoVo sysLoginFailInfoVo = JSONObject.parseObject(sysConfig.getValue(), SysLoginFailInfoVo.class);
        return sysLoginFailInfoVo;
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
            RedisUtil.del(SystemConstant.EMAIL_POLL_KEY);
            SysEmailConfigVo sysEmailConfigVo = EmailUtil.getSysEmailConfigVo();
            List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable())).collect(Collectors.toList());
            List<String> emailList = MyUtil.joinToList(list, SysEmailConfigVo.Email::getEmail);
            RedisUtil.leftPushAll(SystemConstant.EMAIL_POLL_KEY, emailList);
        } else {
            RedisUtil.del(SystemConstant.EMAIL_POLL_KEY);
        }

    }
}
