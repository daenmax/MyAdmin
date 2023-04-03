package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getSysRoleListByUserId(String userId);

    List<SysRole> getSysRoleList();

    Set<String> getRolePermissionListByUserId(String userId);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysRole> getPage(SysRolePageVo vo);

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
    void editInfo(SysRoleUpdVo vo);

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
    void addInfo(SysRoleAddVo vo);

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
    void dataScope(SysRoleDataScopeUpdVo vo);
}
