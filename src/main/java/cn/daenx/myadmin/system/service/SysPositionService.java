package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.domain.po.SysPosition;
import cn.daenx.myadmin.system.domain.vo.SysPositionAddVo;
import cn.daenx.myadmin.system.domain.vo.SysPositionPageVo;
import cn.daenx.myadmin.system.domain.vo.SysPositionUpdAuthUserVo;
import cn.daenx.myadmin.system.domain.vo.SysPositionUpdVo;

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
    IPage<SysPosition> getPage(SysPositionPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysPosition> getAll(SysPositionPageVo vo);

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
    void editInfo(SysPositionUpdVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysPositionAddVo vo);

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
    void cancelAuthUser(SysPositionUpdAuthUserVo vo);

    /**
     * 保存授权用户
     *
     * @param vo
     */
    void saveAuthUser(SysPositionUpdAuthUserVo vo);

}
