package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.mapper.SysUserMapper;
import cn.daenx.myadmin.system.service.SysUserService;
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

}
