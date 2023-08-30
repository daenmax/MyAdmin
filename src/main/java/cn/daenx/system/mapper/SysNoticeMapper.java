package cn.daenx.system.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.system.domain.dto.SysNoticePageDto;
import cn.daenx.system.domain.po.SysNotice;
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
    IPage<SysNoticePageDto> getPageWrapper(Page<SysNoticePageDto> page, @Param("ew") Wrapper<SysNotice> wrapper);
}
