package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysLogLogin;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

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

}
