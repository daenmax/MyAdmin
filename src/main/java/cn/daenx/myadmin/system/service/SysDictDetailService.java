package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictDetailService extends IService<SysDictDetail>{

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode
     * @return
     */
    List<SysDictDetail> getDictDetailByCodeFromRedis(String dictCode);

    List<SysDictDetail> getSysDictDetailList();
}
