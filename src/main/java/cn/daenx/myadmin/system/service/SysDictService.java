package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictService extends IService<SysDict> {


    List<SysDict> getSysDictList();

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDict> getPage(SysDictPageVo vo);
}
