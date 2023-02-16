package cn.daenx.myadmin.common.exception;

import cn.daenx.myadmin.common.vo.Result;
import cn.dev33.satoken.exception.NotLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String msg = e.getMessage();
        if (msg.contains("Required request body is missing")) {
            return Result.error("请求数据不能为空");
        }
        log.error("请求地址->{},发生未知异常.", request.getRequestURI(), e);
        return Result.error(msg);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址->{},发生系统异常.", request.getRequestURI(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 认证失败或者未登录
     */
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        log.error("请求地址->{},认证失败->{},无法访问", request.getRequestURI(), e.getMessage());
        return Result.error("401", "请先登录再访问");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求地址->{},不支持->{}请求模式", request.getRequestURI(), e.getMethod());
        return Result.error(e.getMessage());
    }

    /**
     * Mybatis异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public Result handleCannotFindDataSourceException(MyBatisSystemException e, HttpServletRequest request) {
        String msg = e.getMessage();
        log.error("请求地址->{}, Mybatis系统异常", request.getRequestURI(), e);
        return Result.error(msg);
    }

    /**
     * 参数校验异常
     * 基于表单提交时,参数校验异常,会抛出:BindException
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
//        log.error(e.getMessage(), e);
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.error(msg);
    }

    /**
     * 参数校验异常
     * 基于json提交时,参数校验异常,会抛出:MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error(e.getMessage(), e);
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(msg);
    }

    /**
     * 处理请求参数格式错误
     *
     * @param e @RequestParam上validate失败后抛出的此异常
     * @return Object 同步返回500，异步返回JSON
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().iterator().next();
        return Result.error(constraintViolation.getMessage());
    }
}
