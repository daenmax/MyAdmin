package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysConfigService extends IService<SysConfig> {
    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysConfig> getPage(SysConfigPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysConfig> getAll(SysConfigPageVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysConfig getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysConfigUpdVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysConfigAddVo vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 检查是否存在，已存在返回true
     *
     * @param key
     * @param nowId 排除ID
     * @return
     */
    Boolean checkKeyExist(String key, String nowId);

    /**
     * 根据参数键名查询参数键值
     * 如果参数键名不存在或者未查询到，返回null
     * 如果参数被禁用了，返回空字符串""
     * 可根据此区别来进行你的业务逻辑
     *
     * @param key
     * @return
     */
    String getConfigByKey(String key);

    /**
     * 刷新参数缓存
     */
    void refreshCache();

    /**
     * 获取允许上传的图片后缀
     * 如果未配置则返回默认
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    SysUploadConfigVo getSysUploadImageSuffixs();

    /**
     * 获取允许上传的文件后缀
     * 如果未配置则返回默认
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    SysUploadConfigVo getSysUploadFileSuffixs();

    /**
     * 获取允许上传的图片后缀
     * 如果未配置则返回null
     * 如果配置了但是被禁用了，将返回null
     *
     * @return
     */
    SysRegisterDefaultInfoVo getSysRegisterDefaultInfoVo();

}
