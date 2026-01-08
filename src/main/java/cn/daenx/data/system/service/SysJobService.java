package cn.daenx.data.system.service;

import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.data.system.domain.po.SysJob;
import cn.daenx.data.system.domain.vo.SysJobAddVo;
import cn.daenx.data.system.domain.vo.SysJobPageVo;
import cn.daenx.data.system.domain.vo.SysJobUpdVo;
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
     * @param vo
     * @return
     */
    IPage<SysJob> getPage(SysJobPageVo vo);

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
     * @param vo
     */
    void addInfo(SysJobAddVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysJobUpdVo vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdVo vo);

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
