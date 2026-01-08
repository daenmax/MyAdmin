package cn.daenx.modules.system.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.vo.sysFile.SysFilePageVo;
import cn.daenx.modules.system.domain.po.SysFile;
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
    IPage<SysFilePageVo> getPageWrapper(Page<SysFilePageVo> page, @Param("ew") Wrapper<SysFile> wrapper);
}
