package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.dto.SysNoticePageDto;
import cn.daenx.myadmin.system.po.SysNotice;
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