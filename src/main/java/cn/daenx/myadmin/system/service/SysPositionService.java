package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPositionService extends IService<SysPosition>{

    List<SysPosition> getSysPositionListByUserId(String userId);

}
