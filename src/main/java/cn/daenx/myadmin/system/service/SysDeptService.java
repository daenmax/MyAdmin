package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysDept;
import cn.daenx.myadmin.system.vo.SysDeptPageVo;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门树列表
     *
     * @return
     */
    List<Tree<String>> deptTree();

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDept> getPage(SysDeptPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysDept> getAll(SysDeptPageVo vo);

    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    List<SysDept> getListByParentId(String parentId, Boolean keepSelf);
}
