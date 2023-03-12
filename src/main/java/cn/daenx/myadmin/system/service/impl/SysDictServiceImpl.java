package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysDictDetail;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictMapper;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.service.SysDictService;
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService{
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> getSysDictList() {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        return sysDictList;
    }
}
