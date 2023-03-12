package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysDict;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictDetailMapper;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;

@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<SysDictDetail> getDictDetailByCodeFromRedis(String dictCode) {
        Object value = redisUtil.getValue(RedisConstant.DICT + dictCode);
        List<SysDictDetail> list = Convert.convert(ArrayList.class, value);
        return list;
    }

    @Override
    public List<SysDictDetail> getSysDictDetailList() {
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysDictDetail> sysDictList = sysDictDetailMapper.selectList(wrapper);
        return sysDictList;
    }
}
