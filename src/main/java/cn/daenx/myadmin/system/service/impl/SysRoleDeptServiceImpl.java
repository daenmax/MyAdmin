package cn.daenx.myadmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysRoleDept;
import cn.daenx.myadmin.system.mapper.SysRoleDeptMapper;
import cn.daenx.myadmin.system.service.SysRoleDeptService;

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
