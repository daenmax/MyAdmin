package cn.daenx.myadmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysPositionMapper;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.service.SysPositionService;

import java.util.List;

@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

    @Resource
    private SysPositionMapper sysPositionMapper;

    @Override
    public List<SysPosition> getSysPositionListByUserId(String userId) {
        return sysPositionMapper.getSysPositionListByUserId(userId);
    }

    @Override
    public List<SysPosition> getSysPositionList() {
        return sysPositionMapper.selectList(new LambdaQueryWrapper<>());
    }
}
