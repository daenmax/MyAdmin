package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.TreeBuildUtils;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.vo.SysDeptPageVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDeptMapper;
import cn.daenx.myadmin.system.po.SysDept;
import cn.daenx.myadmin.system.service.SysDeptService;

import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysDept> getPage(SysDeptPageVo vo) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(vo.getOrderByColumn())) {
            vo.setOrderByColumn(vo.getOrderByColumn() + ",sort");
            vo.setIsAsc(vo.getIsAsc() + ",asc");
        } else {
            vo.setOrderByColumn("sort");
            vo.setIsAsc(vo.getIsAsc() + ",asc");
        }
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDept::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getSummary()), SysDept::getSummary, vo.getSummary());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDept::getStatus, vo.getStatus());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDept::getCreateTime, startTime, endTime);
        Page<SysDept> sysDeptPage = sysDeptMapper.selectPage(vo.getPage(true), wrapper);
        return sysDeptPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysDept> getAll(SysDeptPageVo vo) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if (vo != null) {
            wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDept::getName, vo.getName());
            wrapper.like(ObjectUtil.isNotEmpty(vo.getSummary()), SysDept::getSummary, vo.getSummary());
            wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDept::getStatus, vo.getStatus());
            String startTime = vo.getStartTime();
            String endTime = vo.getEndTime();
            wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDept::getCreateTime, startTime, endTime);
        }
        wrapper.orderByAsc(SysDept::getSort);
        List<SysDept> sysDeptList = sysDeptMapper.selectList(wrapper);
        return sysDeptList;
    }

    /**
     * 获取部门树列表
     *
     * @return
     */
    @Override
    public List<Tree<String>> deptTree() {
        List<SysDept> all = getAll(null);
        List<Tree<String>> trees = buildDeptTreeSelect(all);
        return trees;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param all 部门列表
     * @return 下拉树结构列表
     */
    public List<Tree<String>> buildDeptTreeSelect(List<SysDept> all) {
        if (CollUtil.isEmpty(all)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(all, (dept, tree) ->
                tree.setId(dept.getId())
                        .setParentId(dept.getParentId())
                        .setName(dept.getName())
                        .setWeight(dept.getSort()));
    }

    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    @Override
    public List<SysDept> getListByParentId(String parentId, Boolean keepSelf) {
        return sysDeptMapper.getListByParentId(parentId, keepSelf ? null : "1");
    }
}
