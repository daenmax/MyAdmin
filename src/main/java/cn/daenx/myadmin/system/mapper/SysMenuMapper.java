package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysMenu;
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
}
