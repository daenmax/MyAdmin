package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysJob.SysJobLogPageDto;
import cn.daenx.modules.system.domain.po.SysJobLog;
import cn.daenx.modules.system.domain.vo.sysJob.SysJobLogPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysJobLogService extends IService<SysJobLog> {

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysJobLogPageVo> getPage(SysJobLogPageDto dto);

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    List<SysJobLogPageVo> getAll(SysJobLogPageDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysJobLogPageVo getInfo(String id);

    /**
     * 删除
     * 未加数据权限
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 清空日志
     * 未加数据权限
     */
    void clean();

}
