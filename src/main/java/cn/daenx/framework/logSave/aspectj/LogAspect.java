package cn.daenx.framework.logSave.aspectj;


import cn.daenx.common.constant.CommonConstant;
import cn.daenx.common.constant.enums.LogOperType;
import cn.daenx.common.utils.MyUtil;
import cn.daenx.common.utils.ServletUtils;
import cn.daenx.common.vo.system.other.SysLogOperVo;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.framework.satoken.utils.LoginUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志aop
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    /**
     * 配置织入点
     */
    @Pointcut("@annotation(cn.daenx.framework.logSave.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 前置通知 记录开始时间戳
     *
     * @param point
     */
    @Before("logPointCut()")
    public void logBefore(JoinPoint point) {
        // 设置开始时间戳
        threadLocal.set(System.currentTimeMillis());
    }

    /**
     * 抛出异常后通知 在方法抛出异常退出时执行的通知
     *
     * @param point
     * @param e
     */
    @AfterThrowing(value = "@annotation(cn.daenx.framework.logSave.annotation.Log)", throwing = "e")
    public void logThrowing(JoinPoint point, Throwable e) {
        saveLog(point, e, null);
    }

    /**
     * 在某连接点正常完成后执行的通知，不包括抛出异常的情况
     *
     * @param point
     * @param result
     */
    @AfterReturning(value = "logPointCut()", returning = "result")
    public void logAfterReturning(JoinPoint point, Object result) {
        saveLog(point, null, result);
    }

    /**
     * 当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
     *
     * @param point
     */
    @After("logPointCut()")
    public void logAfter(JoinPoint point) {
        threadLocal.remove();
    }

    public void saveLog(JoinPoint point, Throwable throwable, Object result) {
        Long start = threadLocal.get();
        SysLogOperVo sysLogOper = new SysLogOperVo();
        // 保存注解参数
        setLogParams(point, sysLogOper, result);
        // 设置开始时间
        sysLogOper.setRequestTime(MyUtil.toLocalDateTime(start));
        // 获取请求方式
        HttpServletRequest request = ServletUtils.getRequest();
        //请求方式
        sysLogOper.setRequestType(request.getMethod());
        // 获取请求url
        sysLogOper.setRequestUrl(request.getRequestURI());
        // 获取请求IP
        sysLogOper.setRequestIp(ServletUtils.getClientIP());
        // 判断响应结果
        if (throwable != null) {
            sysLogOper.setStatus(CommonConstant.STATUS_DISABLE);
            String message = StringUtils.substring(throwable.getMessage(), 0, CommonConstant.SAVE_LOG_LENGTH);
            sysLogOper.setErrorMsg(message);
        } else {
            sysLogOper.setStatus(CommonConstant.STATUS_NORMAL);
        }
        // 执行人ID
        String loginUserId = LoginUtil.getLoginUserId();
        sysLogOper.setCreateId(loginUserId);
        sysLogOper.setUpdateId(loginUserId);
        // 响应时间
        sysLogOper.setResponseTime(LocalDateTime.now());
        // 执行耗时
        sysLogOper.setExecuteTime((int) (System.currentTimeMillis() - start));
        // 入库
        SpringUtil.getApplicationContext().publishEvent(sysLogOper);
    }

    public void setLogParams(JoinPoint point, SysLogOperVo sysLogOper, Object result) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 获取方法名称
        String declaringTypeName = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        sysLogOper.setMethod(declaringTypeName + "." + methodName);
        // 获取注解
        Log annotation = signature.getMethod().getAnnotation(Log.class);
        if (annotation != null) {
            // 获取注解参数
            String name = annotation.name();
            LogOperType type = annotation.type();
            boolean recordParams = annotation.recordParams();
            boolean recordResult = annotation.recordResult();
            sysLogOper.setName(name);
            sysLogOper.setType(type.getCode());
            // 请求参数
            if (recordParams) {
                // 参数名称
                //  String[] parameterNames = signature.getParameterNames();
                // 参数值
                Object[] args = point.getArgs();
                List<Object> list = new ArrayList<>();
                for (Object arg : args) {
                    //屏蔽掉ResponseFacade，因为例如一下导出接口，下载接口，会报错，具体自行百度咯~
                    if (!arg.getClass().equals(ResponseFacade.class)) {
                        list.add(arg);
                    }
                }
                String params = JSONObject.toJSONString(list);
                params = StringUtils.substring(params, 0, CommonConstant.SAVE_LOG_LENGTH);
                sysLogOper.setRequestParams(params);

            }
            // 响应结果
            if (recordResult) {
                if (result != null) {
                    String ret = JSONObject.toJSONString(result);
                    ret = StringUtils.substring(ret, 0, CommonConstant.SAVE_LOG_LENGTH);
                    sysLogOper.setResponseResult(ret);
                }
            }
        }
    }
}
