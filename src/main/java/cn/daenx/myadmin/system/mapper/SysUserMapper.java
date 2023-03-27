package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.dto.SysUserPageDto;
import cn.daenx.myadmin.system.po.SysUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过ID获取用户信息
     *
     * @param wrapper
     * @return
     */
    SysUserPageDto getUserInfoByUserId(@Param("ew") Wrapper<SysUser> wrapper);

    /**
     * 分页列表_MP自定义SQL
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "su")
    IPage<SysUserPageDto> getPageWrapper(Page<SysUserPageDto> page, @Param("ew") Wrapper<SysUser> wrapper);

    /**
     * 查询
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "su")
    SysUserPageDto getInfo(@Param("ew") Wrapper<SysUser> wrapper);
}
