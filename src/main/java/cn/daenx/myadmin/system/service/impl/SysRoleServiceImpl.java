package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.po.SysRoleUser;
import cn.daenx.myadmin.system.vo.SysRolePageVo;
import cn.daenx.myadmin.system.vo.SysUserPageVo;
import cn.daenx.myadmin.test.po.TestData;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysRoleMapper;
import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.service.SysRoleService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> getSysRoleListByUserId(String userId) {
        return sysRoleMapper.getSysRoleListByUserId(userId);
    }

    @Override
    public List<SysRole> getSysRoleList() {
        return sysRoleMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public Set<String> getRolePermissionListByUserId(String userId) {
        List<SysRole> roleList = sysRoleMapper.getSysRoleListByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : roleList) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getCode().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_role")
    @Override
    public IPage<SysRole> getPage(SysRolePageVo vo) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderBy(true, true, SysRole::getSort);
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysRole::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCode()), SysRole::getCode, vo.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getDataScope()), SysRole::getDataScope, vo.getDataScope());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysRole::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), SysRole::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysRole::getCreateTime, startTime, endTime);
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(vo.getPage(false), wrapper);
        return sysRolePage;
    }
}
