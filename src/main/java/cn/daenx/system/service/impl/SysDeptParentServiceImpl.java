package cn.daenx.system.service.impl;

import cn.daenx.system.domain.po.SysDept;
import cn.daenx.system.mapper.SysDeptMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.system.mapper.SysDeptParentMapper;
import cn.daenx.system.domain.po.SysDeptParent;
import cn.daenx.system.service.SysDeptParentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptParentServiceImpl extends ServiceImpl<SysDeptParentMapper, SysDeptParent> implements SysDeptParentService {
    @Resource
    private SysDeptParentMapper sysDeptParentMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 重新更新部门层级结构表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAll() {
        List<SysDeptParent> allList = new ArrayList<>();
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        List<SysDept> deptList = sysDeptMapper.selectList(wrapper);
        for (SysDept dept : deptList) {
            List<SysDeptParent> newListTmp = new ArrayList<>();
            newListTmp.add(genSysDeptParent(dept.getId(), dept.getParentId()));
            newListTmp.add(genSysDeptParent(dept.getId(), dept.getId()));
            allList.addAll(genSysDeptParentList(newListTmp, deptList, dept.getId(), dept.getParentId()));
        }
        //删除所有记录
        sysDeptParentMapper.delete(new LambdaQueryWrapper<>());
        //重新添加
        if (allList.size() > 0) {
            saveBatch(allList);
        }
    }

    private List<SysDeptParent> genSysDeptParentList(List<SysDeptParent> newListTmp, List<SysDept> deptList, String deptId, String parentId) {
        if ("0".equals(parentId)) {
            return newListTmp;
        }
        SysDept byParentId = getByParentId(deptList, parentId);
        if (byParentId == null) {
            return newListTmp;
        }
        newListTmp.add(genSysDeptParent(deptId, byParentId.getParentId()));
        return genSysDeptParentList(newListTmp, deptList, deptId, byParentId.getParentId());
    }

    private SysDeptParent genSysDeptParent(String deptId, String parentId) {
        SysDeptParent sysDeptParent = new SysDeptParent();
        sysDeptParent.setDeptId(deptId);
        sysDeptParent.setParentId(parentId);
        return sysDeptParent;
    }

    private SysDept getByParentId(List<SysDept> deptList, String parentId) {
        List<SysDept> collect = deptList.stream().filter(item -> item.getId().equals(parentId)).collect(Collectors.toList());
        if (collect.size() > 0) {
            //其实只会有一个……
            return collect.get(0);
        }
        //其实这种情况不会存在
        return null;
    }

    /**
     * 更新部门层级结构表：新增
     *
     * @param deptId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleInsert(String deptId) {
        List<SysDeptParent> allList = new ArrayList<>();
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        List<SysDept> deptList = sysDeptMapper.selectList(wrapper);
        for (SysDept dept : deptList) {
            if (dept.getId().equals(deptId)) {
                List<SysDeptParent> newListTmp = new ArrayList<>();
                newListTmp.add(genSysDeptParent(dept.getId(), dept.getParentId()));
                newListTmp.add(genSysDeptParent(dept.getId(), dept.getId()));
                allList.addAll(genSysDeptParentList(newListTmp, deptList, dept.getId(), dept.getParentId()));
            }
        }
        if (allList.size() > 0) {
            saveBatch(allList);
        }
    }

    /**
     * 更新部门层级结构表：删除
     *
     * @param deptId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleDelete(String deptId) {
        LambdaUpdateWrapper<SysDeptParent> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysDeptParent::getDeptId, deptId);
        sysDeptParentMapper.delete(wrapper);
    }
}
