package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysRoleUser;
import cn.daenx.myadmin.system.mapper.SysRoleUserMapper;
import cn.daenx.myadmin.system.service.SysRoleUserService;

import java.util.List;

@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;


}
