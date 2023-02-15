package cn.daenx.myadmin.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.mapper.SysMenuMapper;
import cn.daenx.myadmin.system.service.SysMenuService;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}
