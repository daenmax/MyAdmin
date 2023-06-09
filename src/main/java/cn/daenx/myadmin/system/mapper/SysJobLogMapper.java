package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.domain.dto.SysJobLogPageDto;
import cn.daenx.myadmin.system.domain.po.SysJobLog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {
    /**
     * 分页列表_MP自定义SQL
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "sj")
    IPage<SysJobLogPageDto> getPageWrapper(Page<SysJobLogPageDto> page, @Param("ew") Wrapper<SysJobLog> wrapper);

    /**
     * 获取所有列表，用于导出
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "sj")
    List<SysJobLogPageDto> getAll(@Param("ew") Wrapper<SysJobLog> wrapper);
}
