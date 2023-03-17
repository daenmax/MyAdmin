package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.vo.SysDictDetailAddVo;
import cn.daenx.myadmin.system.vo.SysDictDetailPageVo;
import cn.daenx.myadmin.system.vo.SysDictDetailUpdVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictDetailService extends IService<SysDictDetail> {

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode
     * @return
     */
    List<SysDictDetail> getDictDetailByCodeFromRedis(String dictCode);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDictDetail> getPage(SysDictDetailPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysDictDetail> getAll(SysDictDetailPageVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysDictDetailAddVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysDictDetail getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysDictDetailUpdVo vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(String[] ids);
}
