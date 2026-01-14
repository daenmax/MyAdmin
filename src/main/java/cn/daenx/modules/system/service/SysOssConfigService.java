package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigUpdDto;
import cn.daenx.modules.system.domain.po.SysOssConfig;
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
     * @param dto
     * @return
     */
    IPage<SysOssConfig> getPage(SysOssConfigPageDto dto);

    /**
     * 获取所有列表
     *
     * @param dto
     * @return
     */
    List<SysOssConfig> getAll(SysOssConfigPageDto dto);

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
     * @param dto
     */
    void editInfo(SysOssConfigUpdDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysOssConfigAddDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 修改配置状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

    /**
     * 修改使用状态
     *
     * @param dto
     */
    void changeInUse(ComStatusUpdDto dto);
}
