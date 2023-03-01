package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.utils.ServletUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysLogLoginMapper;
import cn.daenx.myadmin.system.po.SysLogLogin;
import cn.daenx.myadmin.system.service.SysLogLoginService;

@Service
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLogin> implements SysLogLoginService {

    @Resource
    private SysLogLoginMapper sysLogLoginMapper;

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
    @Override
    @Async
    public void saveLogin(String userId, String userName, Integer status, String remark, String ip, UserAgent userAgent) {
        SysLogLogin sysLogLogin = new SysLogLogin();
        sysLogLogin.setUsername(userName);
        if (ObjectUtil.isNotEmpty(ip)) {
            sysLogLogin.setIp(ip);
            sysLogLogin.setLocation(MyUtil.getIpLocation(sysLogLogin.getIp()));
        }
        sysLogLogin.setBrowser(userAgent.getBrowser().getName());
        sysLogLogin.setOs(userAgent.getOs().getName());
        sysLogLogin.setCreateId(userId);
        sysLogLogin.setUpdateId(userId);
        sysLogLogin.setStatus(SystemConstant.LOGIN_SUCCESS);
        sysLogLogin.setRemark(remark);
        sysLogLoginMapper.insert(sysLogLogin);
    }
}
