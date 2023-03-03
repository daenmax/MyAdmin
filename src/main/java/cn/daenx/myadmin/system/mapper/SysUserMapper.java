package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.vo.SysUserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过ID获取用户信息
     *
     * @param userId
     * @return
     */
    SysUserVo getUserInfoByUserId(@Param("userId") String userId);
}
