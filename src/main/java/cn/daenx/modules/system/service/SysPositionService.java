package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.po.SysPosition;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionAddDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionPageDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdAuthUserDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPositionService extends IService<SysPosition> {

    List<SysPosition> getSysPositionListByUserId(String userId);


    /**
     * 检查编码是否存在
     *
     * @param code
     * @param nowId
     * @return
     */
    Boolean checkCodeExist(String code, String nowId);

    /**
     * 检查名称是否存在
     *
     * @param name
     * @param nowId
     * @return
     */
    Boolean checkNameExist(String name, String nowId);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysPosition> getPage(SysPositionPageDto vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysPosition> getAll(SysPositionPageDto vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysPosition getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysPositionUpdDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysPositionAddDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);


    /**
     * 取消授权用户
     *
     * @param vo
     */
    void cancelAuthUser(SysPositionUpdAuthUserDto vo);

    /**
     * 保存授权用户
     *
     * @param vo
     */
    void saveAuthUser(SysPositionUpdAuthUserDto vo);

}
