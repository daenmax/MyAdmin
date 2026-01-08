package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysRole.*;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.po.SysRole;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getSysRoleListByUserId(String userId);

    Set<String> getRolePermissionListByUserId(String userId);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysRole> getPage(SysRolePageDto vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysRole> getAll(SysRolePageDto vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysRole getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysRoleUpdDto vo);

    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    Boolean checkRoleExist(String code, String nowId);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysRoleAddDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 修改数据权限
     *
     * @param vo
     */
    void dataScope(SysRoleDataScopeUpdDto vo);

    /**
     * 取消授权用户
     *
     * @param vo
     */
    void cancelAuthUser(SysRoleUpdAuthUserDto vo);

    /**
     * 保存授权用户
     *
     * @param vo
     */
    void saveAuthUser(SysRoleUpdAuthUserDto vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdDto vo);

    /**
     * 判断是否有是超级管理员的角色
     *
     * @param roleCode
     * @param roleIds
     * @return
     */
    boolean isHasAdmin(String roleCode, List<String> roleIds);
}
