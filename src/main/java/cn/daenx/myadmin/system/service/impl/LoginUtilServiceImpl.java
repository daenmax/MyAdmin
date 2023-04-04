package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginUtilServiceImpl implements LoginUtilService {
    @Resource
    private RedisUtil redisUtil;
    @Value("${sa-token.timeout}")
    private Long timeOut;

    /**
     * 下线相关用户
     *
     * @param roleId
     */
    @Override
    @Async
    public void offLineUserByRoleId(String roleId) {
        List<String> loginTokenList = LoginUtil.getLoginTokenList();
        if (CollUtil.isEmpty(loginTokenList)) {
            return;
        }
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
    public void offLineUserByRoleId(List<String> roleIds) {
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

    /**
     * 下线相关用户
     *
     * @param userId
     */
    @Override
    public void offLineUserByUserId(String userId) {
        String username = (String) redisUtil.getValue("Authorization:cache:" + userId);
        if (StringUtils.isNotBlank(username)) {
            LoginUtil.logout(username);
            redisUtil.del("Authorization:cache:" + userId);
        }
    }

    /**
     * 记录登录缓存
     *
     * @param userId
     * @param username
     */
    @Override
    public void saveLoginCache(String userId, String username) {
        redisUtil.setValue("Authorization:cache:" + userId, username, timeOut, TimeUnit.SECONDS);
    }
}
