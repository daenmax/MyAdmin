package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeAddDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticePageDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeUpdDto;
import cn.daenx.modules.system.domain.po.SysNotice;
import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysNoticePageVo> getPage(SysNoticePageDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysNoticeAddDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysNotice getInfo(String id);

    /**
     * 修改
     *
     * @param dto
     */
    void editInfo(SysNoticeUpdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
