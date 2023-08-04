package cn.daenx.system.service;

import cn.daenx.common.vo.ComStatusUpdVo;
import cn.daenx.system.domain.po.SysOssConfig;
import cn.daenx.system.domain.vo.SysOssConfigAddVo;
import cn.daenx.system.domain.vo.SysOssConfigPageVo;
import cn.daenx.system.domain.vo.SysOssConfigUpdVo;
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
    IPage<SysOssConfig> getPage(SysOssConfigPageVo vo);

    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    List<SysOssConfig> getAll(SysOssConfigPageVo vo);

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
    void editInfo(SysOssConfigUpdVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysOssConfigAddVo vo);

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
    void changeStatus(ComStatusUpdVo vo);

    /**
     * 修改使用状态
     *
     * @param vo
     */
    void changeInUse(ComStatusUpdVo vo);
}
