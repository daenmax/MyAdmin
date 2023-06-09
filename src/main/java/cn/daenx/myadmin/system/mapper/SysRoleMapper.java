package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.domain.po.SysRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Override
    @DataScope(alias = "sys_role")
    int update(@Param(Constants.ENTITY) SysRole entity, @Param(Constants.WRAPPER) Wrapper<SysRole> updateWrapper);

    @Override
    @DataScope(alias = "sys_role")
    int deleteBatchIds(@Param(Constants.COLL) Collection<?> idList);

    List<SysRole> getSysRoleListByUserId(@Param("userId") String userId);
}
