package cn.daenx.framework.repeatSubmit.aspectj;

import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.utils.ServletUtils;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.repeatSubmit.annotation.RepeatSubmit;
import cn.dev33.satoken.SaManager;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 防重提交
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    private static final ThreadLocal<String> KEY_CACHE = new ThreadLocal<>();

    @Before("@annotation(repeatSubmit)")
    public void doBefore(JoinPoint point, RepeatSubmit repeatSubmit) throws Throwable {
        // 如果注解不为0 则使用注解数值
        long interval = 0;
        if (repeatSubmit.interval() > 0) {
            interval = repeatSubmit.timeUnit().toMillis(repeatSubmit.interval());
        }
        HttpServletRequest request = ServletUtils.getRequest();
        // 获取参数
        String nowParams = argsArrayToString(point.getArgs());

        // 获取请求地址
        String url = request.getRequestURI();

        // 获取用户token
        String token = StringUtils.trimToEmpty(request.getHeader(SaManager.getConfig().getTokenName()));

        // 唯一值 = MD5 ( token + 参数 )
        String submitKey = SecureUtil.md5(token + ":" + nowParams);
        // 唯一标识 = 头 + 请求地址 + 唯一值
        String cacheRepeatKey = RedisConstant.REPEAT_SUBMIT_KEY + url + submitKey;
        String key = (String) CacheUtil.getValue(cacheRepeatKey);
        if (key == null) {
            CacheUtil.setValue(cacheRepeatKey, "", interval, TimeUnit.MILLISECONDS);
            KEY_CACHE.set(cacheRepeatKey);
        } else {
            String message = repeatSubmit.message();
            throw new MyException(message);
        }
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmit)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Object jsonResult) {
        if (jsonResult instanceof Result r) {
            try {
                // 成功则不删除redis数据 保证在有效时间内无法重复提交
                if (r.getCode() == HttpStatus.HTTP_OK) {
                    return;
                }
                CacheUtil.del(KEY_CACHE.get());
            } finally {
                KEY_CACHE.remove();
            }
        }
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(repeatSubmit)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Exception e) {
        CacheUtil.del(KEY_CACHE.get());
        KEY_CACHE.remove();
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (ObjectUtil.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        params.append(JSONUtil.toJsonStr(o)).append(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
