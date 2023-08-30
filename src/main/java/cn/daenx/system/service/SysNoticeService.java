package cn.daenx.system.service;

import cn.daenx.system.domain.dto.SysNoticePageDto;
import cn.daenx.system.domain.po.SysNotice;
import cn.daenx.system.domain.vo.SysNoticeAddVo;
import cn.daenx.system.domain.vo.SysNoticePageVo;
import cn.daenx.system.domain.vo.SysNoticeUpdVo;
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
    IPage<SysNoticePageDto> getPage(SysNoticePageVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysNoticeAddVo vo);

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
    void editInfo(SysNoticeUpdVo vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
