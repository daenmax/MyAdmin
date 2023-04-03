package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDept;
import cn.daenx.myadmin.system.vo.SysDeptPageVo;
import cn.daenx.myadmin.system.vo.SysMenuPageVo;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门树列表
     *
     * @return
     */
    List<Tree<String>> deptTree(SysDeptPageVo vo);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDept> getPage(SysDeptPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysDept> getAll(SysDeptPageVo vo);

    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    List<SysDept> getListByParentId(String parentId, Boolean keepSelf);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectDeptListByRoleId(String roleId);
}
