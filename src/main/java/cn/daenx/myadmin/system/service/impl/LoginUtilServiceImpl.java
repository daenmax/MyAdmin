package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUtilServiceImpl implements LoginUtilService {

    /**
     * 下线相关用户
     *
     * @param roleId
     */
    @Override
    @Async
    public void offLineUser(String roleId) {
        List<String> loginTokenList = LoginUtil.getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (LoginUtil.getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = LoginUtil.getLoginUserByToken(token);
            if (loginUserByToken.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
                try {
                    LoginUtil.logoutByToken(token);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }

    /**
     * 下线相关用户
     *
     * @param roleIds
     */
    @Override
    @Async
    public void offLineUser(List<String> roleIds) {
        List<String> loginTokenList = LoginUtil.getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        loginTokenList.parallelStream().forEach(key -> {
            String token = key.replace("Authorization:login:token:", "");
            // 如果已经过期则跳过
            if (LoginUtil.getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            SysLoginUserVo loginUserByToken = LoginUtil.getLoginUserByToken(token);
            if (loginUserByToken.getRoles().stream().anyMatch(r -> roleIds.contains(r.getId()))) {
                try {
                    LoginUtil.logoutByToken(token);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }
}
