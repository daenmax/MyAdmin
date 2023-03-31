package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.po.SysUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> getMenuPermsByRoleId(@Param("roleId") String roleId);

    List<String> getMenuPermsByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> getMenuTreeByUserId(@Param("userId") String userId);

    /**
     * 获取菜单列表
     *
     * @param wrapper
     * @return
     */
    List<SysMenu> getMenuList(@Param("ew") Wrapper<SysMenu> wrapper);
    List<SysMenu> getMenuListByRoleId(@Param("roleId") String roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);
}
