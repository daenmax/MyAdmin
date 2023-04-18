package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysOssConfig;
import cn.daenx.myadmin.system.vo.SysOssConfigPageVo;
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
}
