package cn.daenx.system.mapper;

import cn.daenx.system.domain.po.SysDept;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<SysDept> getDeptListByRoleId(@Param("roleId") String roleId);

    List<SysDept> selectListX(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);
}
