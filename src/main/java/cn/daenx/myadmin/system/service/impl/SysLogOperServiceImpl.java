package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.MyUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysLogOperMapper;
import cn.daenx.myadmin.system.po.SysLogOper;
import cn.daenx.myadmin.system.service.SysLogOperService;

@Service
public class SysLogOperServiceImpl extends ServiceImpl<SysLogOperMapper, SysLogOper> implements SysLogOperService {
    @Resource
    private SysLogOperMapper sysLogOperMapper;

    /**
     * 记录操作日志
     *
     * @param sysLogOper
     */
    @Async
    @Override
    public void saveOper(SysLogOper sysLogOper) {
        if (ObjectUtil.isNotEmpty(sysLogOper.getRequestIp())) {
            sysLogOper.setRequestLocation(MyUtil.getIpLocation(sysLogOper.getRequestIp()));
        }
        sysLogOperMapper.insert(sysLogOper);
    }
}
