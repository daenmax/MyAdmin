package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysUserType;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysUserTypeService extends IService<SysUserType>{


    SysUserType getSysUserTypeById(String id);
}
