package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysLogOper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysLogOperService extends IService<SysLogOper> {
    /**
     * 记录操作日志
     *
     * @param sysLogOper
     */
    void saveOper(SysLogOper sysLogOper);
}
