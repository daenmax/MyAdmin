package cn.daenx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.system.mapper.SysUserDeptMapper;
import cn.daenx.system.domain.po.SysUserDept;
import cn.daenx.system.service.SysUserDeptService;

@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {
    @Resource
    private SysUserDeptMapper sysUserDeptMapper;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<String> getDeptIdsByUserId(String userId) {
        LambdaQueryWrapper<SysUserDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserDept::getUserId, userId);
        List<SysUserDept> list = sysUserDeptMapper.selectList(wrapper);
        List<String> deptIds = list.stream().map(SysUserDept::getDeptId).collect(Collectors.toList());
        return deptIds;
    }

    /**
     * 更新用户部门关联信息
     *
     * @param userId
     * @param deptIds
     */
    @Override
    public void handleUserDept(String userId, List<String> deptIds) {
        LambdaQueryWrapper<SysUserDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserDept::getUserId, userId);
        sysUserDeptMapper.delete(wrapper);
        if (deptIds != null) {
            List<SysUserDept> list = new ArrayList<>();
            for (String deptId : deptIds) {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setDeptId(deptId);
                sysUserDept.setUserId(userId);
                list.add(sysUserDept);
            }
            saveBatch(list);
        }
    }
}
