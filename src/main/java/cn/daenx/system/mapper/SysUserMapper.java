package cn.daenx.system.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.SysUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    @DataScope(alias = "su", field = "id")
    IPage<SysUserPageDto> getPageWrapper(Page<SysUserPageDto> page, @Param("ew") Wrapper<SysUser> wrapper);

    /**
     * 获取所有列表，用于导出
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "su", field = "id")
    List<SysUserPageDto> getAll(@Param("ew") Wrapper<SysUser> wrapper);

    /**
     * 查询
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "su", field = "id")
    SysUserPageDto getInfo(@Param("ew") Wrapper<SysUser> wrapper);

    /**
     * 获取列表
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "su", field = "id")
    List<SysUser> getUserList(@Param("ew") Wrapper<SysUser> wrapper);

    @Override
    @DataScope(alias = "sys_user", field = "id")
    int update(@Param(Constants.ENTITY) SysUser entity, @Param(Constants.WRAPPER) Wrapper<SysUser> updateWrapper);

    /**
     * 判断是否是超级管理员
     *
     * @param roleCode
     * @param userId
     * @return
     */
    boolean isAdmin(@Param("roleCode") String roleCode, @Param("userId") String userId);

    /**
     * 判断是否有是超级管理员的用户
     *
     * @param roleCode
     * @param userIds
     * @return
     */
    boolean isHasAdmin(@Param("roleCode") String roleCode, @Param("userIds") List<String> userIds);
}
