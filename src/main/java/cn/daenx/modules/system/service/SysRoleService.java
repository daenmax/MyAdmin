package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.dto.sysRole.*;
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
     * @param dto
     * @return
     */
    IPage<SysRole> getPage(SysRolePageDto dto);

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    List<SysRole> getAll(SysRolePageDto dto);

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
     * @param dto
     */
    void editInfo(SysRoleUpdDto dto);

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
     * @param dto
     */
    void addInfo(SysRoleAddDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 修改数据权限
     *
     * @param dto
     */
    void dataScope(SysRoleDataScopeUpdDto dto);

    /**
     * 取消授权用户
     *
     * @param dto
     */
    void cancelAuthUser(SysRoleUpdAuthUserDto dto);

    /**
     * 保存授权用户
     *
     * @param dto
     */
    void saveAuthUser(SysRoleUpdAuthUserDto dto);

    /**
     * 修改状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

    /**
     * 判断是否有是超级管理员的角色
     *
     * @param roleCode
     * @param roleIds
     * @return
     */
    boolean isHasAdmin(String roleCode, List<String> roleIds);
}
