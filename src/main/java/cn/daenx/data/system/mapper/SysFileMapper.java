package cn.daenx.data.system.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.data.system.domain.dto.SysFilePageDto;
import cn.daenx.data.system.domain.po.SysFile;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 文件分页列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "sf")
    IPage<SysFilePageDto> getPageWrapper(Page<SysFilePageDto> page, @Param("ew") Wrapper<SysFile> wrapper);
}
