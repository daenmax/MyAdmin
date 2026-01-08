package cn.daenx.modules.system.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import cn.daenx.modules.system.domain.po.SysNotice;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    /**
     * 分页列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "sn")
    IPage<SysNoticePageVo> getPageWrapper(Page<SysNoticePageVo> page, @Param("ew") Wrapper<SysNotice> wrapper);
}
