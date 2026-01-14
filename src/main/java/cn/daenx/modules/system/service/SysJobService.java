package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobAddDto;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobPageDto;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobUpdDto;
import cn.daenx.modules.system.domain.po.SysJob;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysJobService extends IService<SysJob> {
    /**
     * 初始化定时任务
     */
    void initJob();

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysJob> getPage(SysJobPageDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysJob getInfo(String id);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysJobAddDto dto);

    /**
     * 修改
     *
     * @param dto
     */
    void editInfo(SysJobUpdDto dto);

    /**
     * 修改状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 定时任务立即执行一次
     *
     * @param id
     */
    void run(String id);
}
