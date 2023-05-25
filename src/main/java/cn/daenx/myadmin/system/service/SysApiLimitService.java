package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.system.po.SysApiLimit;
import cn.daenx.myadmin.system.vo.SysApiLimitAddVo;
import cn.daenx.myadmin.system.vo.SysApiLimitPageVo;
import cn.daenx.myadmin.system.vo.SysApiLimitUpdVo;
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
    IPage<SysApiLimit> getPage(SysApiLimitPageVo vo);

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
    void addInfo(SysApiLimitAddVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysApiLimitUpdVo vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdVo vo);

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
