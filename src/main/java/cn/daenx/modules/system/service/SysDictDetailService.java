package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.po.SysDictDetail;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailUpdDto;
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
     * 根据字典编码和键值查询字典详细信息
     *
     * @param dictCode
     * @param value
     * @return
     */
    SysDictDetail getDictDetailValueByCodeFromRedis(String dictCode, String value);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDictDetail> getPage(SysDictDetailPageDto vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysDictDetail> getAll(SysDictDetailPageDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysDictDetailAddDto vo);

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
    void editInfo(SysDictDetailUpdDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
