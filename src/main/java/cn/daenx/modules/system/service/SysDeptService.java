package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.po.SysDept;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptAddDto;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptPageDto;
import cn.daenx.modules.system.domain.vo.sysDept.SysDeptTreeVo;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptUpdDto;
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
    List<Tree<String>> deptTree(SysDeptPageDto vo);
    List<SysDeptTreeVo> deptTreeNew(SysDeptPageDto vo);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysDept> getPage(SysDeptPageDto vo);

    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    List<SysDept> getAll(SysDeptPageDto vo);

    /**
     * 获取所有列表
     * 不翻译leaderUser
     *
     * @param vo
     * @return
     */
    List<SysDept> getAllNoLeaderUser(SysDeptPageDto vo);

    /**
     * 通过父ID获取子成员
     *
     * @param parentId
     * @param keepSelf 是否包含自己
     * @return
     */
    List<SysDept> getListByParentId(String parentId, Boolean keepSelf);

    /**
     * 通过父ID获取子成员
     *
     * @param parentIds
     * @param keepSelf 是否包含自己
     * @return
     */
    List<SysDept> getListByParentIds(List<String> parentIds, Boolean keepSelf);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectDeptListByRoleId(String roleId);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysDept getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysDeptUpdDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysDeptAddDto vo);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 删除列表中的某一个部门及以下部门
     *
     * @param list
     * @param waitRemoveList
     */
    void removeList(List<SysDept> list, List<SysDept> waitRemoveList);

    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    Boolean checkCodeExist(String code, String nowId);

    /**
     * 根据部门编号获取部门
     *
     * @param code
     * @return
     */
    SysDept getSysDeptByCode(String code);
}
