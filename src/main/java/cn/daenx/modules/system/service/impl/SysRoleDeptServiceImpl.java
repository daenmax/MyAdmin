package cn.daenx.modules.system.service.impl;

import cn.daenx.modules.system.mapper.SysRoleDeptMapper;
import cn.daenx.modules.system.service.SysRoleDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.daenx.modules.system.domain.po.SysRoleDept;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {
    @Resource
    private SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 更新角色部门关联信息
     *
     * @param roleId
     * @param deptIds
     */
    @Override
    public void handleRoleDept(String roleId, List<String> deptIds) {
        LambdaQueryWrapper<SysRoleDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleDept::getRoleId, roleId);
        sysRoleDeptMapper.delete(wrapper);
        if (deptIds != null) {
            List<SysRoleDept> list = new ArrayList<>();
            for (String deptId : deptIds) {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setRoleId(roleId);
                sysRoleDept.setDeptId(deptId);
                list.add(sysRoleDept);
            }
            saveBatch(list);
        }
    }
}
