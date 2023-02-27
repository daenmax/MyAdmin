package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getSysRoleListByUserId(@Param("userId") String userId);
}
