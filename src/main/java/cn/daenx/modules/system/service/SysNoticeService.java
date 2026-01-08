package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import cn.daenx.modules.system.domain.po.SysNotice;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeAddDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticePageDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeUpdDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysNoticePageVo> getPage(SysNoticePageDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysNoticeAddDto vo);

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
     * @param vo
     */
    void editInfo(SysNoticeUpdDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
