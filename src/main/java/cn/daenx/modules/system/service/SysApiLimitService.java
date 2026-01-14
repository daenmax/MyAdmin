package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitAddDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitPageDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitUpdDto;
import cn.daenx.modules.system.domain.po.SysApiLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysApiLimitService extends IService<SysApiLimit> {

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysApiLimit> getPage(SysApiLimitPageDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysApiLimit getInfo(String id);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(SysApiLimitAddDto dto);

    /**
     * 修改
     *
     * @param dto
     */
    void editInfo(SysApiLimitUpdDto dto);

    /**
     * 修改状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 检查某个接口的某个限制是否存在，已存在返回true
     *
     * @param apiUri
     * @param limitType
     * @param nowId     排除ID
     * @return
     */
    Boolean checkApiLimitExist(String apiUri, String limitType, String nowId);

    /**
     * 刷新redis缓存
     */
    void refreshApiLimitCache();
}
