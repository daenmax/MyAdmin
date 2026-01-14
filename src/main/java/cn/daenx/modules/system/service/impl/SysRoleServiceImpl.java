package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.modules.system.domain.dto.sysRole.*;
import cn.daenx.modules.system.domain.po.SysRole;
import cn.daenx.modules.system.domain.po.SysRoleUser;
import cn.daenx.modules.system.mapper.SysRoleMapper;
import cn.daenx.modules.system.mapper.SysUserMapper;
import cn.daenx.modules.system.service.SysRoleDeptService;
import cn.daenx.modules.system.service.SysRoleMenuService;
import cn.daenx.modules.system.service.SysRoleService;
import cn.daenx.modules.system.service.SysRoleUserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysRole> getSysRoleListByUserId(String userId) {
        return sysRoleMapper.getSysRoleListByUserId(userId);
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
     * @param dto
     * @return
     */
    @Override
    @DataScope(alias = "sys_role")
    public IPage<SysRole> getPage(SysRolePageDto dto) {
        LambdaQueryWrapper<SysRole> wrapper = getWrapper(dto);
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(dto.getPage(false), wrapper);
        return sysRolePage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    @Override
    @DataScope(alias = "sys_role")
    public List<SysRole> getAll(SysRolePageDto dto) {
        LambdaQueryWrapper<SysRole> wrapper = getWrapper(dto);
        List<SysRole> sysRoleList = sysRoleMapper.selectList(wrapper);
        return sysRoleList;
    }

    private LambdaQueryWrapper<SysRole> getWrapper(SysRolePageDto dto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysRole::getSort);
        wrapper.like(ObjectUtil.isNotEmpty(dto.getName()), SysRole::getName, dto.getName());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getCode()), SysRole::getCode, dto.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getDataScope()), SysRole::getDataScope, dto.getDataScope());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysRole::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), SysRole::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysRole::getCreateTime, startTime, endTime);
        return wrapper;
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
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getCode, code);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysRole::getId, nowId);
        boolean exists = sysRoleMapper.exists(wrapper);
        return exists;
    }

    /**
     * 修改
     *
     * @param dto
     */
    @Override
    public void editInfo(SysRoleUpdDto dto) {
        SysRole sysRole = sysRoleMapper.selectById(dto.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        if (checkRoleExist(dto.getCode(), dto.getId())) {
            throw new MyException("角色编码已存在");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, dto.getId());
        wrapper.set(SysRole::getName, dto.getName());
        wrapper.set(SysRole::getCode, dto.getCode());
        wrapper.set(SysRole::getSort, dto.getSort());
        wrapper.set(SysRole::getMenuCheckStrictly, dto.getMenuCheckStrictly());
        wrapper.set(SysRole::getDeptCheckStrictly, dto.getDeptCheckStrictly());
        wrapper.set(SysRole::getRemark, dto.getRemark());
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色菜单关联信息
        sysRoleMenuService.handleRoleMenu(dto.getId(), dto.getMenuIds());
        //下线相关用户
        LoginUtil.logoutByRoleId(dto.getId());
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysRoleAddDto dto) {
        if (checkRoleExist(dto.getCode(), null)) {
            throw new MyException("角色编码已存在");
        }
        SysRole sysRole = new SysRole();
        sysRole.setName(dto.getName());
        sysRole.setCode(dto.getCode());
        sysRole.setSort(dto.getSort());
        sysRole.setMenuCheckStrictly(dto.getMenuCheckStrictly());
        sysRole.setDeptCheckStrictly(dto.getDeptCheckStrictly());
        sysRole.setStatus(dto.getStatus());
        sysRole.setRemark(dto.getRemark());
        int insert = sysRoleMapper.insert(sysRole);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        //更新角色菜单关联信息
        sysRoleMenuService.handleRoleMenu(sysRole.getId(), dto.getMenuIds());
    }

    /**
     * 获取指定角色的用户数量
     *
     * @param id
     * @return
     */
    private Long getPositionUserCount(String id) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getRoleId, id);
        Long count = sysRoleUserService.count(wrapper);
        return count;
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        for (String id : ids) {
            SysRole sysRole = sysRoleMapper.selectById(id);
            if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
                throw new MyException("禁止操作超级管理员角色");
            }
            if (getPositionUserCount(id) > 0) {
                SysRole info = getInfo(id);
                throw new MyException("角色[" + info.getName() + "]已经被分配给用户，请先处理分配关系");
            }
        }
        int i = sysRoleMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
        //下线相关用户
        LoginUtil.logoutByRoleIds(ids);
        for (String id : ids) {
            //更新角色菜单关联信息
            sysRoleMenuService.handleRoleMenu(id, null);
        }
    }


    /**
     * 修改数据权限
     *
     * @param dto
     */
    @Override
    public void dataScope(SysRoleDataScopeUpdDto dto) {
        SysRole sysRole = sysRoleMapper.selectById(dto.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, dto.getId());
        wrapper.set(SysRole::getDeptCheckStrictly, dto.getDeptCheckStrictly());
        wrapper.set(SysRole::getDataScope, dto.getDataScope());
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色部门关联信息
        sysRoleDeptService.handleRoleDept(dto.getId(), dto.getDeptIds());
        //下线相关用户
        LoginUtil.logoutByRoleId(dto.getId());
    }

    /**
     * 取消授权用户
     *
     * @param dto
     */
    @Override
    public void cancelAuthUser(SysRoleUpdAuthUserDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if(dto.getUserIds().contains(loginUser.getId())){
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, dto.getUserIds())) {
            throw new MyException("禁止操作超级管理员");
        }
        for (String userId : dto.getUserIds()) {
            sysRoleUserService.delUserRole(userId, dto.getRoleId());
            LoginUtil.logoutByUserId(userId);
        }
    }

    /**
     * 保存授权用户
     *
     * @param dto
     */
    @Override
    public void saveAuthUser(SysRoleUpdAuthUserDto dto) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if(dto.getUserIds().contains(loginUser.getId())){
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, dto.getUserIds())) {
            throw new MyException("禁止操作超级管理员");
        }
        for (String userId : dto.getUserIds()) {
            sysRoleUserService.addUserRole(userId, dto.getRoleId());
            LoginUtil.logoutByUserId(userId);
        }
    }

    /**
     * 修改状态
     *
     * @param dto
     */
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        SysRole sysRole = sysRoleMapper.selectById(dto.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, dto.getId());
        wrapper.set(SysRole::getStatus, dto.getStatus());
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //下线相关用户
        LoginUtil.logoutByRoleId(dto.getId());
    }

    /**
     * 判断是否有是超级管理员的角色
     *
     * @param roleCode
     * @param roleIds
     * @return
     */
    @Override
    public boolean isHasAdmin(String roleCode, List<String> roleIds) {
        return sysRoleMapper.isHasAdmin(roleCode, roleIds);
    }
}
