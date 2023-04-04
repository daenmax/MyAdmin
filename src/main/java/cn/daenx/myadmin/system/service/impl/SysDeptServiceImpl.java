package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.utils.TreeBuildUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.mapper.SysRoleDeptMapper;
import cn.daenx.myadmin.system.mapper.SysRoleMapper;
import cn.daenx.myadmin.system.po.*;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.vo.SysDeptPageVo;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDeptMapper;
import cn.daenx.myadmin.system.service.SysDeptService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Resource
    private LoginUtilService loginUtilService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysDept> getPage(SysDeptPageVo vo) {
        LambdaQueryWrapper<SysDept> wrapper = getWrapper(vo);
        Page<SysDept> sysDeptPage = sysDeptMapper.selectPage(vo.getPage(false), wrapper);
        return sysDeptPage;
    }

    private LambdaQueryWrapper<SysDept> getWrapper(SysDeptPageVo vo) {
        SysLoginUserVo loginUser = loginUtilService.getLoginUser();
        List<SysRole> roleList = loginUser.getRoles();
        Map<String, List<SysRole>> roleMap = roleList.stream().collect(Collectors.groupingBy(SysRole::getDataScope));
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDept::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getSummary()), SysDept::getSummary, vo.getSummary());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDept::getStatus, vo.getStatus());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDept::getCreateTime, startTime, endTime);
        wrapper.orderByAsc(SysDept::getSort);
        if (!loginUser.isAdmin() && !roleMap.containsKey(SystemConstant.DATA_SCOPE_ALL)) {
            //不是管理员，也没有全部数据权限
            String deptId = loginUser.getDeptId();
            Set<String> deptSet = new HashSet<>();
            deptSet.add(deptId);
            if (roleMap.containsKey(SystemConstant.DATA_SCOPE_DEPT_DOWN)) {
                //数据权限，1=本部门数据
            }
            if (roleMap.containsKey(SystemConstant.DATA_SCOPE_DEPT_DOWN)) {
                //数据权限，2=本部门及以下数据
                List<SysDept> deptList = sysDeptMapper.getListByParentId(deptId, "1");
                List<String> deptIds = MyUtil.joinToList(deptList, SysDept::getId);
                deptSet.addAll(deptIds);
            }
            if (roleMap.containsKey(SystemConstant.DATA_SCOPE_CUSTOM)) {
                //数据权限，4=自定义权限
                List<SysRole> sysRoleList = roleMap.get(SystemConstant.DATA_SCOPE_CUSTOM);
                List<String> roleIds = sysRoleList.stream().map(SysRole::getId).collect(Collectors.toList());
                LambdaQueryWrapper<SysRoleDept> rdWrapper = new LambdaQueryWrapper<>();
                rdWrapper.select(SysRoleDept::getDeptId).in(SysRoleDept::getRoleId, roleIds);
                List<Object> list = sysRoleDeptMapper.selectObjs(rdWrapper);
                for (Object obj : list) {
                    deptSet.add(String.valueOf(obj));
                }
            }
            wrapper.in(SysDept::getId, deptSet);
        }
        return wrapper;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysDept> getAll(SysDeptPageVo vo) {
        LambdaQueryWrapper<SysDept> wrapper = getWrapper(vo);
        List<SysDept> sysDeptList = sysDeptMapper.selectList(wrapper);
        return sysDeptList;
    }

    /**
     * 获取部门树列表
     *
     * @return
     */
    @Override
    public List<Tree<String>> deptTree(SysDeptPageVo vo) {
        List<SysDept> all = getAll(vo);
        List<Tree<String>> trees = buildDeptTreeSelect(all);
        return trees;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param all 部门列表
     * @return 下拉树结构列表
     */
    public List<Tree<String>> buildDeptTreeSelect(List<SysDept> all) {
        if (CollUtil.isEmpty(all)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(all, (dept, tree) ->
                tree.setId(dept.getId())
                        .setParentId(dept.getParentId())
                        .setName(dept.getName())
                        .setWeight(dept.getSort()));
    }

    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    @Override
    public List<SysDept> getListByParentId(String parentId, Boolean keepSelf) {
        return sysDeptMapper.getListByParentId(parentId, keepSelf ? null : "1");
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<String> selectDeptListByRoleId(String roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        List<SysDept> deptListByRoleId = sysDeptMapper.getDeptListByRoleId(sysRole.getId(), sysRole.getDeptCheckStrictly());
        List<String> strings = MyUtil.joinToList(deptListByRoleId, SysDept::getId);
        return strings;
    }
}
