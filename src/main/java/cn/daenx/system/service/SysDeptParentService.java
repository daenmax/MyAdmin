package cn.daenx.system.service;

import cn.daenx.system.domain.po.SysDeptParent;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDeptParentService extends IService<SysDeptParent> {
    /**
     * 更新部门层级结构表：全部
     */
    void handleAll();

    /**
     * 更新部门层级结构表：新增
     */
    void handleInsert(String deptId);

    /**
     * 更新部门层级结构表：删除
     */
    void handleDelete(String deptId);

}
