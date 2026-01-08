package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.po.SysOssConfig;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigUpdDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysOssConfigService extends IService<SysOssConfig> {

    /**
     * 初始化OSS
     */
    void initOssConfig();

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysOssConfig> getPage(SysOssConfigPageDto vo);

    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    List<SysOssConfig> getAll(SysOssConfigPageDto vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysOssConfig getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysOssConfigUpdDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysOssConfigAddDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 修改配置状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdDto vo);

    /**
     * 修改使用状态
     *
     * @param vo
     */
    void changeInUse(ComStatusUpdDto vo);
}
