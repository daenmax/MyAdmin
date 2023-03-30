package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRole;
import cn.daenx.myadmin.system.vo.SysRolePageVo;
import cn.daenx.myadmin.system.vo.SysUserPageVo;
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
}
