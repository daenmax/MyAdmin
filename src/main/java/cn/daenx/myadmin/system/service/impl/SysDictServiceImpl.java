package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictMapper;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.service.SysDictService;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
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

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysDict> getPage(SysDictPageVo vo) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDict::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCode()), SysDict::getCode, vo.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDict::getStatus, vo.getStatus());
        Object beginTime = vo.getParams().get("beginTime");
        Object endTime = vo.getParams().get("endTime");
        wrapper.between(ObjectUtil.isNotEmpty(beginTime) && ObjectUtil.isNotEmpty(endTime), SysDict::getCreateTime, beginTime, endTime);
        Page<SysDict> sysDictPage = sysDictMapper.selectPage(vo.getPage(), wrapper);
        return sysDictPage;
    }
}
