package cn.daenx.modules.system.service;

import cn.daenx.modules.system.domain.dto.sysLog.SysLogLoginPageDto;
import cn.daenx.modules.system.domain.po.SysLogLogin;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysLogLoginService extends IService<SysLogLogin> {
    /**
     * 记录登录日志
     *
     * @param userId
     * @param userName
     * @param status
     * @param remark
     * @param ip
     * @param userAgent
     */
    void saveLogin(String userId, String userName, String status, String remark, String ip, UserAgent userAgent);

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    IPage<SysLogLogin> getPage(SysLogLoginPageDto dto);

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    List<SysLogLogin> getAll(SysLogLoginPageDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 清空
     */
    void clean();
}
