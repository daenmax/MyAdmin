package cn.daenx.data.system.service;

import cn.daenx.data.system.domain.po.SysLogLogin;
import cn.daenx.data.system.domain.vo.SysLogLoginPageVo;
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
     * @param vo
     * @return
     */
    IPage<SysLogLogin> getPage(SysLogLoginPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysLogLogin> getAll(SysLogLoginPageVo vo);

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
