package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysDict.SysDictAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictUpdDto;
import cn.daenx.modules.system.domain.po.SysDict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictService extends IService<SysDict> {
    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    Boolean checkDictExist(String code, String nowId);


    List<SysDict> getSysDictList();

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysDict> getPage(SysDictPageDto dto);

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    List<SysDict> getAll(SysDictPageDto dto);

    /**
     * +
     * 新增
     *
     * @param dto
     */
    void addInfo(SysDictAddDto dto);

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
     * @param dto
     */
    void editInfo(SysDictUpdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 刷新字典缓存
     */
    void refreshCache();
}
