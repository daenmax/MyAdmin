package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.service.SysRoleDeptService;
import cn.daenx.myadmin.system.service.SysRoleMenuService;
import cn.daenx.myadmin.system.vo.*;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleDeptService sysRoleDeptService;
    @Resource
    private LoginUtilService loginUtilService;

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
        wrapper.orderByAsc(SysRole::getSort);
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

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    @DataScope(alias = "sys_role")
    public SysRole getInfo(String id) {
        return sysRoleMapper.selectById(id);
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkRoleExist(String code, String nowId) {
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getCode, code);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysRole::getId, nowId);
        boolean exists = sysRoleMapper.exists(wrapper);
        return exists;
    }

    /**
     * 修改
     *
     * @param vo
     */
    @Override
    public void editInfo(SysRoleUpdVo vo) {
        if (SystemConstant.IS_ADMIN_ID.equals(vo.getId())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        if (checkRoleExist(vo.getCode(), vo.getId())) {
            throw new MyException("角色编码已存在");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, vo.getId());
        wrapper.set(SysRole::getName, vo.getName());
        wrapper.set(SysRole::getCode, vo.getCode());
        wrapper.set(SysRole::getSort, vo.getSort());
        wrapper.set(SysRole::getMenuCheckStrictly, vo.getMenuCheckStrictly());
        wrapper.set(SysRole::getDeptCheckStrictly, vo.getDeptCheckStrictly());
        wrapper.set(SysRole::getRemark, vo.getRemark());
        int update = sysRoleMapper.update(null, wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色菜单关联信息
        sysRoleMenuService.handleRoleMenu(vo.getId(), vo.getMenuIds());
        //下线相关用户
        loginUtilService.offLineUser(vo.getId());
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysRoleAddVo vo) {
        if (checkRoleExist(vo.getCode(), null)) {
            throw new MyException("角色编码已存在");
        }
        SysRole sysRole = new SysRole();
        sysRole.setName(vo.getName());
        sysRole.setCode(vo.getCode());
        sysRole.setSort(vo.getSort());
        sysRole.setMenuCheckStrictly(vo.getMenuCheckStrictly());
        sysRole.setDeptCheckStrictly(vo.getDeptCheckStrictly());
        sysRole.setStatus(vo.getStatus());
        sysRole.setRemark(vo.getRemark());
        int insert = sysRoleMapper.insert(sysRole);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        //更新角色菜单关联信息
        sysRoleMenuService.handleRoleMenu(sysRole.getId(), vo.getMenuIds());
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysRoleMapper.deleteBatchIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
        //下线相关用户
        loginUtilService.offLineUser(ids);
    }


    /**
     * 修改数据权限
     *
     * @param vo
     */
    @Override
    public void dataScope(SysRoleDataScopeUpdVo vo) {
        if (SystemConstant.IS_ADMIN_ID.equals(vo.getId())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, vo.getId());
        wrapper.set(SysRole::getDeptCheckStrictly, vo.getDeptCheckStrictly());
        wrapper.set(SysRole::getDataScope, vo.getDataScope());
        int update = sysRoleMapper.update(null, wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色部门关联信息
        sysRoleDeptService.handleRoleDept(vo.getId(), vo.getDeptIds());
        //下线相关用户
        loginUtilService.offLineUser(vo.getId());
    }
}
