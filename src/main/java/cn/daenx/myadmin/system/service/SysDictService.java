package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictService extends IService<SysDict> {



    List<SysDict> getSysDictList();
}
