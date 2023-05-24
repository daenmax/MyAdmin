package cn.daenx.myadmin.common.interceptor;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.utils.ServletUtils;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

/**
 * 接口限流拦截器
 */
@Configuration
@Slf4j
public class ApiLimitInterceptor implements HandlerInterceptor {
    @Resource
    private RedisScript<Long> apiLimitScript;
    @Resource
    private LoginUtilService loginUtilService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (ObjectUtil.isNotEmpty(contextPath)) {
            requestURI = requestURI.replaceFirst(contextPath, "");
        }
        String loginUserId = loginUtilService.getLoginUserId();
        String clientIP = ServletUtils.getClientIP();
        String userKey = ObjectUtil.isEmpty(loginUserId) ? clientIP : loginUserId;

        String singKey = RedisConstant.API_LIMIT_SINGLE_KEY + requestURI;
        String wholeKey = RedisConstant.API_LIMIT_WHOLE_KEY + requestURI;

        String singLimitKey = RedisConstant.API_LIMIT_SINGLE_KEY + requestURI + ":" + userKey;
        String wholeLimitKey = RedisConstant.API_LIMIT_WHOLE_LIMITER_KEY + requestURI;
        Long execute = RedisUtil.getRedisTemplate().execute(apiLimitScript, Arrays.asList(singKey, singLimitKey, wholeKey, wholeLimitKey));
        if (execute == null) {
            return true;
        }
        if (SystemConstant.API_LIMIT_SINGLE_LIMIT_EXCEED.equals(execute)) {
            //超出单个用户访问限制
            throw new MyException("请求过于频繁，请稍后再试");
        }
        if (SystemConstant.API_LIMIT_WHOLE_LIMIT_EXCEED.equals(execute)) {
            //超出全部用户访问限制
            throw new MyException("系统繁忙，请稍后再试");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
