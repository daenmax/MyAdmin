package cn.daenx.modules.system.service;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.system.domain.po.SysApiLimit;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitAddDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitPageDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitUpdDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysApiLimitService extends IService<SysApiLimit> {

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysApiLimit> getPage(SysApiLimitPageDto vo);

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
     * @param vo
     */
    void addInfo(SysApiLimitAddDto vo);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysApiLimitUpdDto vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdDto vo);

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
