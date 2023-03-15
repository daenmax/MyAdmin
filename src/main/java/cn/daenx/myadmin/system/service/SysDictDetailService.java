package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.vo.SysDictDetailPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<SysDictDetail> getPage(SysDictDetailPageVo vo);
}
