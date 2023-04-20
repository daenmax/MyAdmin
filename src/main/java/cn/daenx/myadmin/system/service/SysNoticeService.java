package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.dto.SysNoticePageDto;
import cn.daenx.myadmin.system.po.SysNotice;
import cn.daenx.myadmin.system.vo.SysNoticeAddVo;
import cn.daenx.myadmin.system.vo.SysNoticePageVo;
import cn.daenx.myadmin.system.vo.SysNoticeUpdVo;
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
