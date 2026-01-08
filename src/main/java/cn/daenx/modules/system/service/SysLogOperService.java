package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.po.SysLogOper;
import cn.daenx.modules.system.domain.dto.sysLog.SysLogOperPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysLogOperService extends IService<SysLogOper> {
    /**
     * 记录操作日志
     *
     * @param sysLogOper
     */
    void saveOper(SysLogOper sysLogOper);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysLogOper> getPage(SysLogOperPageDto vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysLogOper> getAll(SysLogOperPageDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 清空
     */
    void clean();

}
