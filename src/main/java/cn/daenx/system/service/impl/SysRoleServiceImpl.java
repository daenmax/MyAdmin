package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.system.other.SysLoginUserVo;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.daenx.system.domain.po.SysRoleUser;
import cn.daenx.system.domain.vo.*;
import cn.daenx.system.mapper.SysRoleMapper;
import cn.daenx.system.mapper.SysUserMapper;
import cn.daenx.system.service.SysRoleDeptService;
import cn.daenx.system.service.SysRoleMenuService;
import cn.daenx.system.service.SysRoleService;
import cn.daenx.system.service.SysRoleUserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.daenx.system.domain.po.SysRole;

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
     * @param vo
     * @return
     */
    @Override
    @DataScope(alias = "sys_role")
    public IPage<SysRole> getPage(SysRolePageVo vo) {
        LambdaQueryWrapper<SysRole> wrapper = getWrapper(vo);
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(vo.getPage(false), wrapper);
        return sysRolePage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    @DataScope(alias = "sys_role")
    public List<SysRole> getAll(SysRolePageVo vo) {
        LambdaQueryWrapper<SysRole> wrapper = getWrapper(vo);
        List<SysRole> sysRoleList = sysRoleMapper.selectList(wrapper);
        return sysRoleList;
    }

    private LambdaQueryWrapper<SysRole> getWrapper(SysRolePageVo vo) {
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
     * @param vo
     */
    @Override
    public void editInfo(SysRoleUpdVo vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
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
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色菜单关联信息
        sysRoleMenuService.handleRoleMenu(vo.getId(), vo.getMenuIds());
        //下线相关用户
        LoginUtil.logoutByRoleId(vo.getId());
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
        int i = sysRoleMapper.deleteBatchIds(ids);
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
     * @param vo
     */
    @Override
    public void dataScope(SysRoleDataScopeUpdVo vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, vo.getId());
        wrapper.set(SysRole::getDeptCheckStrictly, vo.getDeptCheckStrictly());
        wrapper.set(SysRole::getDataScope, vo.getDataScope());
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //更新角色部门关联信息
        sysRoleDeptService.handleRoleDept(vo.getId(), vo.getDeptIds());
        //下线相关用户
        LoginUtil.logoutByRoleId(vo.getId());
    }

    /**
     * 取消授权用户
     *
     * @param vo
     */
    @Override
    public void cancelAuthUser(SysRoleUpdAuthUserVo vo) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if(vo.getUserIds().contains(loginUser.getId())){
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, vo.getUserIds())) {
            throw new MyException("禁止操作超级管理员");
        }
        for (String userId : vo.getUserIds()) {
            sysRoleUserService.delUserRole(userId, vo.getRoleId());
            LoginUtil.logoutByUserId(userId);
        }
    }

    /**
     * 保存授权用户
     *
     * @param vo
     */
    @Override
    public void saveAuthUser(SysRoleUpdAuthUserVo vo) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        if(vo.getUserIds().contains(loginUser.getId())){
            throw new MyException("禁止操作自己");
        }
        if (!loginUser.getIsAdmin() && sysUserMapper.isHasAdmin(SystemConstant.ROLE_ADMIN, vo.getUserIds())) {
            throw new MyException("禁止操作超级管理员");
        }
        for (String userId : vo.getUserIds()) {
            sysRoleUserService.addUserRole(userId, vo.getRoleId());
            LoginUtil.logoutByUserId(userId);
        }
    }

    /**
     * 修改状态
     *
     * @param vo
     */
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (SystemConstant.ROLE_ADMIN.equals(sysRole.getCode())) {
            throw new MyException("禁止操作超级管理员角色");
        }
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, vo.getId());
        wrapper.set(SysRole::getStatus, vo.getStatus());
        int update = sysRoleMapper.update(new SysRole(), wrapper);
        if (update < 1) {
            throw new MyException("修改失败");
        }
        //下线相关用户
        LoginUtil.logoutByRoleId(vo.getId());
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
