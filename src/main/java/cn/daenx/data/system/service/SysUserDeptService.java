package cn.daenx.data.system.service;

import cn.daenx.data.system.domain.po.SysUserDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserDeptService extends IService<SysUserDept> {

    List<String> getDeptIdsByUserId(String userId);

    /**
     * 更新用户部门关联信息
     *
     * @param userId
     * @param deptIds
     */
    void handleUserDept(String userId, List<String> deptIds);

}
