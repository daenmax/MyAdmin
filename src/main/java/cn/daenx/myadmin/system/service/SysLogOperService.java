package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysLogOper;
import cn.daenx.myadmin.system.vo.SysLogOperPageVo;
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
    IPage<SysLogOper> getPage(SysLogOperPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysLogOper> getAll(SysLogOperPageVo vo);

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
