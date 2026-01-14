package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionAddDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionPageDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdAuthUserDto;
import cn.daenx.modules.system.domain.dto.sysPosition.SysPositionUpdDto;
import cn.daenx.modules.system.domain.po.SysPosition;
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
     * @param dto
     * @return
     */
    IPage<SysPosition> getPage(SysPositionPageDto dto);

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    List<SysPosition> getAll(SysPositionPageDto dto);

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
     * @param dto
     */
    void editInfo(SysPositionUpdDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysPositionAddDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);


    /**
     * 取消授权用户
     *
     * @param dto
     */
    void cancelAuthUser(SysPositionUpdAuthUserDto dto);

    /**
     * 保存授权用户
     *
     * @param dto
     */
    void saveAuthUser(SysPositionUpdAuthUserDto dto);

}
