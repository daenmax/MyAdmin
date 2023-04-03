package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysRoleDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleDeptService extends IService<SysRoleDept> {
    /**
     * 更新角色部门关联信息
     *
     * @param roleId
     * @param deptIds
     */
    void handleRoleDept(String roleId, List<String> deptIds);

}
