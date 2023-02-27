package cn.daenx.myadmin.system.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysUserTypeMapper;
import cn.daenx.myadmin.system.po.SysUserType;
import cn.daenx.myadmin.system.service.SysUserTypeService;
@Service
public class SysUserTypeServiceImpl extends ServiceImpl<SysUserTypeMapper, SysUserType> implements SysUserTypeService{
    @Resource
    private SysUserTypeMapper sysUserTypeMapper;
    @Override
    public SysUserType getSysUserTypeById(String id) {
        SysUserType sysUserType = sysUserTypeMapper.selectById(id);
        return sysUserType;
    }
}
