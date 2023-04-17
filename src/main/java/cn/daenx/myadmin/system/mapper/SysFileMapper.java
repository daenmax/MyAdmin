package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.dto.SysFilePageDto;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
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