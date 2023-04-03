package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysDept;
import cn.daenx.myadmin.system.po.SysMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    List<SysDept> getListByParentId(@Param("parentId") String parentId, @Param("keepSelf") String keepSelf);

    List<SysDept> getDeptListByRoleId(@Param("roleId") String roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);
}
