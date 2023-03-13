package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.vo.SysDictAddVo;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.daenx.myadmin.system.vo.SysDictUpdVo;
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

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysDict> getAll(SysDictPageVo vo);

    /**
     * +
     * 新增
     *
     * @param vo
     */
    void addInfo(SysDictAddVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysDict getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysDictUpdVo vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(String[] ids);
}
